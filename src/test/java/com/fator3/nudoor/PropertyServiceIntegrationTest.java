package com.fator3.nudoor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fator3.nudoor.models.TimedLatLng;
import com.fator3.nudoor.property.PropertyService;

//REMOVABLE
@Ignore
public class PropertyServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private PropertyService propertyService;

    private static final double ORIGIN_X = -23.573588;
    private static final double ORIGIN_Y = -46.724252;
    private static final double FIRST_REFERENCE_X = -23.585400;
    private static final double FIRST_REFERENCE_Y = -46.682815;
    private static final double SECOND_REFERENC_X = -23.561365;
    private static final double SECOND_REFERENCE_Y = -46.673190;

    @Test
    public void clientShouldReturnRouteWithRightLegsQuantityAndDistanceInSeconds() {
        final List<TimedLatLng> responseLocations = propertyService
                .findDistanceInSeconds(makeOrderedReferences());
        for (TimedLatLng latLng : responseLocations) {
            assertThat(latLng.getSecondsToArrive()).isGreaterThan(600); 
        }
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
