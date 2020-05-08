package com.fator3.nudoor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.transaction.Transactional;

import org.assertj.core.util.Lists;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fator3.nudoor.geometry.GeometryUtils;
import com.fator3.nudoor.models.LatLng;
import com.fator3.nudoor.property.Property;
import com.fator3.nudoor.property.PropertyService;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

@Ignore
@Transactional
public class PersistenceIntegrationTest extends AbstractIntegrationTest {

    private static final String NUMBER = "100";
    private static final String STREET = "street";

    @Autowired
    private PropertyService propertyService;
    private WKTReader reader = new WKTReader();



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
