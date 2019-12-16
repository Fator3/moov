package com.fator3.moov;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.transaction.Transactional;

import org.assertj.core.util.Lists;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fator3.moov.clients.TomtomClient;
import com.fator3.moov.geometry.GeometryUtils;
import com.fator3.moov.models.LatLng;
import com.fator3.moov.models.Leg;
import com.fator3.moov.models.ReachableRangeResponse;
import com.fator3.moov.models.RouteResponse;
import com.fator3.moov.models.TimedLatLng;
import com.fator3.moov.property.PersistentProperty;
import com.fator3.moov.property.PropertyService;
import com.google.common.collect.Iterables;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.WKTReader;

@Ignore
public class ClientIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private TomtomClient tomtomClient;

    @Autowired
    private PropertyService propertyService;

    private WKTReader reader = new WKTReader();

    private static final double FIRST_LATITUDE = 52.50931;
    private static final double FIRST_LONGITUDE = 13.42936;
    private static final double FIRST_LONGITUDE_ADJUSTED = 13.42937;

    private static final double SECOND_LATITUDE = 52.50274;
    private static final double SECOND_LONGITUDE = 13.43872;

    private static final double ORIGIN_X = -23.573588;
    private static final double ORIGIN_Y = -46.724252;

    @Test
    @Transactional
    public void clientShouldConvertReachableToPolygonAndSave() {
        final Point point = createPoint(ORIGIN_X, ORIGIN_Y);
        final ReachableRangeResponse reachableResponse = tomtomClient.getPolygonReachable(point);
        final List<LatLng> boundaries = reachableResponse.getReachableRange().getBoundary();
        boundaries.add(boundaries.get(0));

        final Polygon polygon = GeometryUtils.createPolygon(boundaries, reader);
        final PersistentProperty property = PersistentProperty.of("Butantã", "Rua Augusto Perroni",
                point, polygon);

        propertyService.save(property);
    }

    @Test
    public void clientShouldAdjustCenterAndReturnFiftyBoundaries() {
        final Point point = createPoint(FIRST_LATITUDE, FIRST_LONGITUDE);
        final LatLng expectedCenter = LatLng.of(FIRST_LATITUDE, FIRST_LONGITUDE_ADJUSTED);
        final Integer expectedBoundarySize = 50;
        final ReachableRangeResponse reachableResponse = tomtomClient.getPolygonReachable(point);

        assertThat(reachableResponse.getReachableRange().getCenter()).isEqualTo(expectedCenter);
        assertThat(reachableResponse.getReachableRange().getBoundary().size())
                .isEqualTo(expectedBoundarySize);
    }

    @Test
    public void clientShouldReturnRouteWithRightLegsQuantityAndDistanceInSeconds() {
        final Point first = createPoint(FIRST_LATITUDE, FIRST_LONGITUDE);
        final Point second = createPoint(SECOND_LATITUDE, SECOND_LONGITUDE);
        final List<TimedLatLng> locations = Lists.newArrayList(TimedLatLng.of(first),
                TimedLatLng.of(second), TimedLatLng.of(first));

        final RouteResponse routeResponse = tomtomClient.getRoute(locations);
        final List<Leg> legs = Iterables.getOnlyElement(routeResponse.getRoutes()).getLegs();
        assertThat(legs.size()).isEqualTo(2);

        for (int i = 1; i < locations.size(); i++) {
            final Integer distanceSeconds = legs.get(i - i).getSummary().getTravelTimeInSeconds();
            assertThat(distanceSeconds).isGreaterThan(10);
        }
    }

    private Point createPoint(double x, double y) {
        return GeometryUtils.createPoint(x, y, reader);
    }

}
