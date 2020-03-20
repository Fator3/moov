package com.fator3.moov.property;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fator3.moov.clients.TomtomClient;
import com.fator3.moov.geometry.GeometryUtils;
import com.fator3.moov.models.GeolocationResponse;
import com.fator3.moov.models.LatLng;
import com.fator3.moov.models.Leg;
import com.fator3.moov.models.ReachableRangeResponse;
import com.fator3.moov.models.RouteResponse;
import com.fator3.moov.models.SearchParamsDTO;
import com.fator3.moov.models.TimedLatLng;
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

    private WKTReader reader = new WKTReader();

    public void save(final PersistentProperty property) {
        propertyRepository.save(property);
    }

    public List<PersistentProperty> findAll() {
        return propertyRepository.findAll();
    }

    public List<PersistentProperty> findWithin(final Point point) {
        return propertyRepository.findWithin(point);
    }

    public void saveTestPolygon(final double lat, final double lng, final String bairro,
            final String street) {
        final Point point = GeometryUtils.createPoint(lat, lng, reader);
        final ReachableRangeResponse reachableResponse = tomtomClient.getPolygonReachable(point, 15);
        final List<LatLng> boundaries = reachableResponse.getReachableRange().getBoundary();
        boundaries.add(boundaries.get(0));

        final Polygon polygon = GeometryUtils.createPolygon(boundaries, reader);
        final PersistentProperty property = PersistentProperty.of(bairro, street, point, polygon);

        save(property);
    }

    public List<TimedLatLng> findDistanceInSeconds(final List<TimedLatLng> orderedReferences) {
        final RouteResponse routeResponse = tomtomClient.getRoute(orderedReferences);
        final List<Leg> legs = Iterables.getOnlyElement(routeResponse.getRoutes()).getLegs();

        for (int i = 1; i < orderedReferences.size(); i++) {
            final TimedLatLng location = orderedReferences.get(i);
            final Integer secondsToArrive = legs.get(i - 1).getSummary().getTravelTimeInSeconds();
            location.setSecondsToArrive(secondsToArrive);
        }

        return orderedReferences;
    }

    public List<TimedLatLng> findDistanceInSeconds(List<TimedLatLng> orderedReferences,
            final String address) {

        final TimedLatLng addressLocation = Iterables
                .getOnlyElement(geolocationList(Collections.singletonList(address)));

        orderedReferences.add(addressLocation);
        orderedReferences = Lists.reverse(orderedReferences);
        final RouteResponse routeResponse = tomtomClient.getRoute(orderedReferences);
        final List<Leg> legs = Iterables.getOnlyElement(routeResponse.getRoutes()).getLegs();

        for (int i = 1; i < orderedReferences.size(); i++) {
            final TimedLatLng location = orderedReferences.get(i);
            final Integer secondsToArrive = legs.get(i - 1).getSummary().getTravelTimeInSeconds();
            location.setSecondsToArrive(secondsToArrive);
        }

        return orderedReferences;
    }

    public List<PersistentProperty> findWithinRange(final SearchParamsDTO searchParams) {
    	List<String> references = searchParams.getReferences();
    	List<Integer> referencesMinutes = searchParams.getReferencesMinutes();
        List<PersistentProperty> properties = propertyRepository.findAll();
        final List<TimedLatLng> referencesLatLng = geolocationList(references);
        
        
        for(int i = 0 ; i< referencesLatLng.size(); i++){
        	TimedLatLng t = referencesLatLng.get(i);
        	Integer minutes = referencesMinutes.get(i);
            Point point = GeometryUtils.createPoint(t.getLatitude(), t.getLongitude(), reader);
            ReachableRangeResponse reachableResponse = tomtomClient.getPolygonReachable(point, minutes);
            
            List<LatLng> boundaries = reachableResponse.getReachableRange().getBoundary();
            boundaries.add(boundaries.get(0));

            Polygon polygon = GeometryUtils.createPolygon(boundaries, reader);

            
            properties = properties.stream().filter(p -> p.getLocation() != null)
                    .filter(p -> polygon.contains(p.getLocation()))
                    .collect(Collectors.toList());
        }
        return properties;
    }

    public List<TimedLatLng> geolocationList(final List<String> references) {
        final List<TimedLatLng> results = Lists.newArrayList();
        for (String reference : references) {
            final GeolocationResponse result = tomtomClient.getLatLng(reference);
            final double latitude = result.getResults().get(0).getPosition().getLatitude();
            final double longitude = result.getResults().get(0).getPosition().getLongitude();
            results.add(TimedLatLng.of(latitude, longitude));
        }
        return results;
    }

}