package com.fator3.nudoor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fator3.nudoor.clients.TomtomClient;
import com.fator3.nudoor.geometry.GeometryUtils;
import com.fator3.nudoor.models.LatLng;
import com.fator3.nudoor.models.Leg;
import com.fator3.nudoor.models.ReachableRangeResponse;
import com.fator3.nudoor.models.RouteResponse;
import com.fator3.nudoor.models.TimedLatLng;
import com.fator3.nudoor.property.PropertyService;
import com.google.common.collect.Iterables;
import com.vividsolutions.jts.geom.Point;
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

    @Test
    public void clientShouldAdjustCenterAndReturnFiftyBoundaries() {
        final Point point = createPoint(FIRST_LATITUDE, FIRST_LONGITUDE);
        final LatLng expectedCenter = LatLng.of(FIRST_LATITUDE, FIRST_LONGITUDE_ADJUSTED);
        final Integer expectedBoundarySize = 50;
        final ReachableRangeResponse reachableResponse = tomtomClient.getPolygonReachable(point, 15);

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
