package com.fator3.nudoor.models;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Position {

    private double latitude;
    private double longitude;

    @JsonCreator
    public Position(@JsonProperty("lat") double latitude, @JsonProperty("lon") double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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
        if (!(obj instanceof Position))
            return false;
        final Position latLng = (Position) obj;
        return Objects.equals(getLatitude(), latLng.getLatitude())
                && Objects.equals(getLongitude(), latLng.getLongitude());
    }

}
