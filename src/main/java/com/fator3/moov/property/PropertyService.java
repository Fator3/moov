package com.fator3.moov.property;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fator3.moov.clients.TomtomClient;
import com.fator3.moov.geometry.GeometryUtils;
import com.fator3.moov.models.Leg;
import com.fator3.moov.models.RouteResponse;
import com.fator3.moov.models.TimedLatLng;
import com.google.common.collect.Iterables;
import com.vividsolutions.jts.geom.Point;
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

    public List<PersistentProperty> findWithin(final Point point) {
        return propertyRepository.findWithin(point);
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

    public List<PersistentProperty> findWithinRange(final List<TimedLatLng> references) {
        List<PersistentProperty> properties = propertyRepository.findAll();

        for (final TimedLatLng reference : references) {
            final Point point = GeometryUtils.createPoint(reference.getLatitude(),
                    reference.getLongitude(), reader);
            properties = properties.stream().filter(p -> p.getReachableRange().contains(point))
                    .collect(Collectors.toList());
        }

        return properties;
    }

}