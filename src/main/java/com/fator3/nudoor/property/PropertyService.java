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

	@Value("${spring.mail.username}")
    private String nudoorAddress;
	
	
	private WKTReader reader = new WKTReader();

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
			final String transport) {

		final TimedLatLng addressLocation = getReferenceLatLong(address);

		orderedReferences.add(addressLocation);
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

	public List<Property> findWithinRange(final SearchParamsDTO searchParams) {
		List<Property> properties = propertyRepository.findAll();

		for (ReferenceDTO reference : searchParams.getReferences()) {
			TimedLatLng t = getReferenceLatLong(reference.getAddress());

			Point point = GeometryUtils.createPoint(t.getLatitude(), t.getLongitude(), reader);
			ReachableRangeResponse reachableResponse = tomtomClient.getPolygonReachable(point, reference.getTime(),
					reference.getTransport());

			List<LatLng> boundaries = reachableResponse.getReachableRange().getBoundary();
			boundaries.add(boundaries.get(0));

			Polygon polygon = GeometryUtils.createPolygon(boundaries, reader);

			properties = properties.stream().filter(p -> p.getLatitude() != null && p.getLongitude() != null)
					.filter(p -> searchParams.getCity() == null || searchParams.getCity().isEmpty() || p.getCity().equals(searchParams.getCity().split(" - ")[0]))
					.filter(p -> polygon.contains(GeometryUtils.createPoint(p.getLatitude().doubleValue(),
							p.getLongitude().doubleValue(), reader)))
					.filter(p -> isPropertyType(p, searchParams.getType()))
					.collect(Collectors.toList());
		}
		return properties;
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

	public TimedLatLng getReferenceLatLong(final String address) {
		final GeolocationResponse result = tomtomClient.getLatLng(address);
		final double latitude = result.getResults().get(0).getPosition().getLatitude();
		final double longitude = result.getResults().get(0).getPosition().getLongitude();
		return TimedLatLng.of(latitude, longitude);
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