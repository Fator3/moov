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
import com.fator3.moov.models.TimedLatLng;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.WKTReader;

@Component
public class PropertyService {

    private static final double ORIGIN_X = -23.573588;
    private static final double ORIGIN_Y = -46.724252;
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

    public void saveTestPolygon() {
        final Point point = GeometryUtils.createPoint(ORIGIN_X, ORIGIN_Y, reader);
        final ReachableRangeResponse reachableResponse = tomtomClient.getPolygonReachable(point);
        final List<LatLng> boundaries = reachableResponse.getReachableRange().getBoundary();
        boundaries.add(boundaries.get(0));

        final Polygon polygon = GeometryUtils.createPolygon(boundaries, reader);
        final PersistentProperty property = PersistentProperty.of("Butantã", "Augusto Perroni 21",
                point, polygon);

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
        final RouteResponse routeResponse = tomtomClient.getRoute(orderedReferences);
        final List<Leg> legs = Iterables.getOnlyElement(routeResponse.getRoutes()).getLegs();

        for (int i = 1; i < orderedReferences.size(); i++) {
            final TimedLatLng location = orderedReferences.get(i);
            final Integer secondsToArrive = legs.get(i - 1).getSummary().getTravelTimeInSeconds();
            location.setSecondsToArrive(secondsToArrive);
        }

        return orderedReferences;
    }

    public List<PersistentProperty> findWithinRange(final List<String> references) {
        List<PersistentProperty> properties = propertyRepository.findAll();
        final List<TimedLatLng> referencesLatLng = geolocationList(references);

        for (final TimedLatLng reference : referencesLatLng) {
            final Point point = GeometryUtils.createPoint(reference.getLatitude(),
                    reference.getLongitude(), reader);
            properties = properties.stream().filter(p -> p.getReachableRange() != null)
                    .filter(p -> p.getReachableRange().contains(point))
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