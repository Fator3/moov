package com.fator3.nudoor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fator3.nudoor.clients.TomtomClient;
import com.fator3.nudoor.models.Leg;
import com.fator3.nudoor.models.Route;
import com.fator3.nudoor.models.RouteResponse;
import com.fator3.nudoor.models.Summary;
import com.fator3.nudoor.models.TimedLatLng;
import com.fator3.nudoor.property.Property;

import io.restassured.RestAssured;

@Ignore
public class PropertyIntegrationTest extends AbstractIntegrationTest {

    private static final int TRAVEL_TIME = 10;
    @MockBean
    private TomtomClient tomtomClient;
    @Mock
    private RouteResponse routeResponse;
    @Mock
    private Route route;
    @Mock
    private Leg leg;
    @Mock
    private Summary summary;

    private static final String FILTER_URL = "/property/filter";
    private static final String TIME_URL = "/property/time";

    private static final double INSIDE_POINT_X = -23.573814;
    private static final double INSIDE_POINT_Y = -46.731789;
    private static final double OTHER_INSIDE_POINT_X = -23.571896;
    private static final double OTHER_INSIDE_POINT_Y = -46.706737;

    private static final double ORIGIN_X = -23.573588;
    private static final double ORIGIN_Y = -46.724252;
    private static final double FIRST_REFERENCE_X = -23.585400;
    private static final double FIRST_REFERENCE_Y = -46.682815;
    private static final double SECOND_REFERENC_X = -23.561365;
    private static final double SECOND_REFERENCE_Y = -46.673190;

    @Test
    public void filterShouldReturnTwo() {
        final TimedLatLng insideReference = TimedLatLng.of(INSIDE_POINT_X, INSIDE_POINT_Y);
        final TimedLatLng otherInsideReference = TimedLatLng.of(OTHER_INSIDE_POINT_X,
                OTHER_INSIDE_POINT_Y);
        final List<TimedLatLng> references = Lists.newArrayList(insideReference,
                otherInsideReference);

        final List<Property> filterResult = Arrays.asList(RestAssured.given()
                .contentType("application/json").body(references).when().post(FILTER_URL).then()
                .statusCode(200).and().extract().response().as(Property[].class));

        assertThat(filterResult.size()).isEqualTo(0);
    }

    @Test
    public void distanceOfReferencesShouldReturnSecondsToArriveExceptForOrigin() {
        when(tomtomClient.getRoute(any())).thenReturn(routeResponse);
        when(routeResponse.getRoutes()).thenReturn(Collections.singletonList(route));
        when(route.getLegs()).thenReturn(Collections.singletonList(leg));
        when(leg.getSummary()).thenReturn(summary);
        when(summary.getTravelTimeInSeconds()).thenReturn(TRAVEL_TIME);

        final List<TimedLatLng> distanceResults = Arrays.asList(RestAssured.given()
                .contentType("application/json").body(makeOrderedReferences()).when().post(TIME_URL)
                .then().statusCode(200).and().extract().response().as(TimedLatLng[].class));

        assertThat(distanceResults.get(0).getSecondsToArrive()).isEqualTo(null);
        assertThat(distanceResults.get(1).getSecondsToArrive()).isEqualTo(TRAVEL_TIME);
        assertThat(distanceResults.get(2).getSecondsToArrive()).isEqualTo(TRAVEL_TIME);
        assertThat(distanceResults.get(3).getSecondsToArrive()).isEqualTo(TRAVEL_TIME);
    }

    public List<TimedLatLng> makeOrderedReferences() {
        final TimedLatLng origin = TimedLatLng.of(ORIGIN_X, ORIGIN_Y);
        final TimedLatLng firstReference = TimedLatLng.of(FIRST_REFERENCE_X, FIRST_REFERENCE_Y);
        final TimedLatLng secondReference = TimedLatLng.of(SECOND_REFERENC_X, SECOND_REFERENCE_Y);
        final List<TimedLatLng> references = Lists.newArrayList(origin, firstReference,
                secondReference, origin);

        return references;
    }

}
