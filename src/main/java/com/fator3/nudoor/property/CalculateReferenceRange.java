package com.fator3.nudoor.property;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fator3.nudoor.clients.TomtomClient;
import com.fator3.nudoor.geometry.GeometryUtils;
import com.fator3.nudoor.models.GeolocationResponse;
import com.fator3.nudoor.models.LatLng;
import com.fator3.nudoor.models.ReachableRangeResponse;
import com.fator3.nudoor.models.ReferenceDTO;
import com.fator3.nudoor.models.SearchParamsDTO;
import com.fator3.nudoor.models.TimedLatLng;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.WKTReader;

@Component
public class CalculateReferenceRange {
	
	@Autowired
	private TomtomClient tomtomClient;
	
	@Value("${spring.mail.username}")
    private String nudoorAddress;
	
	private WKTReader reader = new WKTReader();
	
	
	private List<LatLng> getBoundariesFromReachableRange(ReachableRangeResponse reachableResponse) {
		List<LatLng> boundaries = reachableResponse.getReachableRange().getBoundary();
		boundaries.add(boundaries.get(0));
		return boundaries;
	}
	
	private ReachableRangeResponse getReachableRangeFromLatLonReference(TimedLatLng coordinates, ReferenceDTO reference) {
		Point point = GeometryUtils.createPoint(coordinates.getLatitude(), coordinates.getLongitude(), reader);
		
		return tomtomClient.getPolygonReachable(point, reference.getTime(),
				reference.getTransport());
	}
	
	
	private List<Property> getPropertiesFromPolygon(List<Property> properties, Polygon polygon) {
		return properties.stream()
		.filter(p -> polygon.contains(GeometryUtils.createPoint(p.getLatitude().doubleValue(),
				p.getLongitude().doubleValue(), reader)))
		.collect(Collectors.toList());
	}
	
	
	private TimedLatLng getReferenceLatLong(final String address) {
		final GeolocationResponse result = tomtomClient.getLatLng(address);
		final double latitude = result.getResults().get(0).getPosition().getLatitude();
		final double longitude = result.getResults().get(0).getPosition().getLongitude();
		return TimedLatLng.of(latitude, longitude);
	}
	
	
	public List<Property> getPropertiesFromReference(SearchParamsDTO searchParams, List<Property> properties) {

		int index = 0;
		
		for (ReferenceDTO reference : searchParams.getReferences()) {
			TimedLatLng t = getReferenceLatLong(reference.getAddress());
			searchParams.getReferences().get(index).setLatLon(t);

			ReachableRangeResponse reachableResponse = getReachableRangeFromLatLonReference(t, reference);

			List<LatLng> boundaries = getBoundariesFromReachableRange(reachableResponse);

			Polygon polygon = GeometryUtils.createPolygon(boundaries, reader);

			properties = getPropertiesFromPolygon(properties, polygon);
			index++;
		}
		
		return properties;
	}
	
}
