package com.fator3.nudoor.property;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.fator3.nudoor.clients.TomtomClient;
import com.fator3.nudoor.geometry.GeometryUtils;
import com.fator3.nudoor.models.GeolocationResponse;
import com.fator3.nudoor.models.LatLng;
import com.fator3.nudoor.models.LeadMessageDTO;
import com.fator3.nudoor.models.Leg;
import com.fator3.nudoor.models.ReachableRangeResponse;
import com.fator3.nudoor.models.ReferenceDTO;
import com.fator3.nudoor.models.ResponseDTO;
import com.fator3.nudoor.models.RouteResponse;
import com.fator3.nudoor.models.SearchParamsDTO;
import com.fator3.nudoor.models.SearchResponseDTO;
import com.fator3.nudoor.models.TimedLatLng;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.WKTReader;

@Component
public class PropertyService {

	@Autowired
	private PropertyRepository propertyRepository;
	@Autowired
	private TomtomClient tomtomClient;
	@Autowired
    public JavaMailSender emailSender;
	@Autowired
	private CalculateReferenceRange calculateRefenceRange;

	@Value("${spring.mail.username}")
    private String nudoorAddress;
	

	private final static String[] homeTypes = {
			"Casa",
			"Sobrado",
			"Sítio"}; 
	
	private final static String[] apartmentTypes = {"Apartamento Duplex",
			"Apartamento",
			"Cobertura",
			"Studio",
			"Kitnet",
			"Loft",
			"Apartamento Garden"
			}; 
	
	public void save(final Property property) {
		propertyRepository.save(property);
	}

	public List<Property> findAll() {
		return propertyRepository.findAll();
	}

	public List<TimedLatLng> findDistanceInSeconds(final List<TimedLatLng> orderedReferences, String transport) {
		final RouteResponse routeResponse = tomtomClient.getRoute(orderedReferences, transport);
		final List<Leg> legs = Iterables.getOnlyElement(routeResponse.getRoutes()).getLegs();

		for (int i = 1; i < orderedReferences.size(); i++) {
			final TimedLatLng location = orderedReferences.get(i);
			final Integer secondsToArrive = legs.get(i - 1).getSummary().getTravelTimeInSeconds();
			location.setSecondsToArrive(secondsToArrive);
		}

		return orderedReferences;
	}

	public List<TimedLatLng> findDistanceInSeconds(List<TimedLatLng> orderedReferences, final String address,
			final String transport, final TimedLatLng reference) {
				
		orderedReferences.add(reference); 
		orderedReferences = Lists.reverse(orderedReferences);
		final RouteResponse routeResponse = tomtomClient.getRoute(orderedReferences, transport);
		final List<Leg> legs = Iterables.getOnlyElement(routeResponse.getRoutes()).getLegs();

		for (int i = 1; i < orderedReferences.size(); i++) {
			final TimedLatLng location = orderedReferences.get(i);
			final Integer secondsToArrive = legs.get(i - 1).getSummary().getTravelTimeInSeconds();
			location.setSecondsToArrive(secondsToArrive);
		}

		return orderedReferences;
	}
	

	public SearchResponseDTO findWithinRange(final SearchParamsDTO searchParams) {
		
		List<Property> properties = getPropertiesFromRepo(searchParams);
		
		properties = calculateRefenceRange.getPropertiesFromReference(searchParams, properties);
		
		return new SearchResponseDTO(searchParams.getReferences(), properties);
	
	}
	
	public List<Property> getPropertiesFromRepo(final SearchParamsDTO searchParams) {
		return propertyRepository.findAll()
		.stream().filter(p -> p.getLatitude() != null && p.getLongitude() != null)
		.filter(p -> searchParams.getCity() == null || searchParams.getCity().isEmpty() || p.getCity().equals(searchParams.getCity().split(" - ")[0]))
		.filter(p -> searchParams.getType() == null  || searchParams.getType().isEmpty() || isPropertyType(p, searchParams.getType()))
		.collect(Collectors.toList());
	}
	
	private boolean isPropertyType(Property property, String type) {
		switch (type) {
		case "Comercial":
			return property.getPurpose().equals("Comercial");
		case "Casa":
			return Arrays.stream(homeTypes).anyMatch(property.getType()::equals);
		case "Apartamento":
			return Arrays.stream(apartmentTypes).anyMatch(property.getType()::equals);
		default:
			return false;
		}
	}

	

	public List<Property> listNRandom(final Integer limit) {
		return propertyRepository.listNRandom(limit);
	}

	public ResponseDTO sendEmail(final LeadMessageDTO leadMessage) {
		SimpleMailMessage message = new SimpleMailMessage(); 
		message.setFrom(nudoorAddress);
        message.setTo(nudoorAddress); 
        message.setSubject("[NuDoor] Novo lead");
        
        String text = "Nome: "+leadMessage.getName() + 
        		         "\nEmail: "+leadMessage.getEmail() +
        		         "\nTelefone: "+leadMessage.getPhone() +
        		         "\n\n\nMensagem:\n\n"+leadMessage.getMessage();
        
        message.setText(text);
        try {
        	emailSender.send(message);
        }catch (MailException e) {
        	System.out.println(e);
        	return ResponseDTO.of("Erro ao enviar o email! Tente novamente mais tarde.", 500);
        }
        
        return ResponseDTO.of("Email enviado com sucesso!", 200);
	}
}