package com.fator3.nudoor.models;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vividsolutions.jts.geom.Point;

public class LatLng {

    private double latitude;
    private double longitude;

    @JsonCreator
    public LatLng(@JsonProperty("latitude") double latitude,
            @JsonProperty("longitude") double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static LatLng of(final double latitude, final double longitude) {
        return new LatLng(latitude, longitude);
    }

    public static LatLng of(final Point point) {
        return new LatLng(point.getX(), point.getY());
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "latitude: " + latitude + ", longitude: " + longitude;
    }

    public String toUrlString() {
        return latitude + "," + longitude;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLatitude(), getLongitude());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof LatLng))
            return false;
        final LatLng latLng = (LatLng) obj;
        return Objects.equals(getLatitude(), latLng.getLatitude())
                && Objects.equals(getLongitude(), latLng.getLongitude());
    }

}
