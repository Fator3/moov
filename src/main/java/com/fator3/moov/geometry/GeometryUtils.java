package com.fator3.moov.geometry;

import java.util.List;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class GeometryUtils {

    public static Point createPoint(double x, double y, WKTReader reader) {
        try {
            return (Point) reader.read("POINT (" + x + " " + y + ")");
        } catch (ParseException e) {
            System.out.println("cannot parse point");
            return null;
        }
    }

    public static Polygon createPolygon(List<Point> points, WKTReader reader) {
        final StringBuffer sb = new StringBuffer();
        sb.append("POLYGON ((");
        for(final Point p : points) {
            sb.append(" ");
            sb.append(p.getX());
            sb.append(" ");
            sb.append(p.getY());
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("))");
        try {
            return (Polygon) reader.read(sb.toString());
        } catch (ParseException e) {
            System.out.println("cannot parse polygon");
            return null;
        }
    }

    public Geometry wktToGeometry(String wellKnownText) throws ParseException {
        return new WKTReader().read(wellKnownText);
    }

}
