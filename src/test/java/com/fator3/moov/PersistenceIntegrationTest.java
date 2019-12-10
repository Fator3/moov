package com.fator3.moov;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.transaction.Transactional;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fator3.moov.geometry.GeometryUtils;
import com.fator3.moov.models.LatLng;
import com.fator3.moov.property.PersistentProperty;
import com.fator3.moov.property.PropertyService;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

@Transactional
public class PersistenceIntegrationTest extends AbstractIntegrationTest {

    private static final String NUMBER = "100";
    private static final String STREET = "street";

    @Autowired
    private PropertyService propertyService;
    private WKTReader reader = new WKTReader();

    @Test
    public void shouldPersistSomeProperty() throws ParseException {
        final Point location = createPoint(3, 5);
        final Polygon polygon = createPolygon(makeSquare());
        final PersistentProperty property = PersistentProperty.of(STREET, NUMBER, location,
                polygon);

        propertyService.save(property);
    }

    @Test
    public void shouldFindPointInsertedBefore() throws ParseException {
        shouldPersistSomeProperty();
        final Point locationOutside = createPoint(0, 6);
        final Point locationInside = createPoint(1.0, 1.0);
        final List<PersistentProperty> outside = propertyService.findWithin(locationOutside);
        final List<PersistentProperty> inside = propertyService.findWithin(locationInside);

        assertThat(outside.size()).isEqualTo(0);
        assertThat(inside.size()).isGreaterThan(1);
    }

    private List<LatLng> makeSquare() {
        final Point location1 = createPoint(0f, 0f);
        final Point location2 = createPoint(0f, 5f);
        final Point location3 = createPoint(5f, 5f);
        final Point location4 = createPoint(5f, 0f);
        final Point location5 = createPoint(0f, 0f);
        final List<LatLng> points = Lists.newArrayList(LatLng.of(location1), LatLng.of(location2),
                LatLng.of(location3), LatLng.of(location4), LatLng.of(location5));

        return points;
    }

    private Point createPoint(double x, double y) {
        return GeometryUtils.createPoint(x, y, reader);
    }

    private Polygon createPolygon(List<LatLng> points) {
        return GeometryUtils.createPolygon(points, reader);
    }

}
