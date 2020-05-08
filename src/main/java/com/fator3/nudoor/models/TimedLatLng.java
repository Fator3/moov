package com.fator3.nudoor.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vividsolutions.jts.geom.Point;

public class TimedLatLng extends LatLng {

    private Integer secondsToArrive;

    @JsonCreator
    public TimedLatLng(@JsonProperty("latitude") double latitude,
            @JsonProperty("longitude") double longitude,
            @JsonProperty("secondsToArrive") Integer secondsToArrive) {
        super(latitude, longitude);
        this.secondsToArrive = secondsToArrive;
    }

    public static TimedLatLng of(final double latitude, final double longitude,
            final Integer secondsToArrive) {
        return new TimedLatLng(latitude, longitude, secondsToArrive);
    }

    public static TimedLatLng of(final double latitude, final double longitude) {
        return new TimedLatLng(latitude, longitude, null);
    }

    public static TimedLatLng of(final Point point) {
        return new TimedLatLng(point.getX(), point.getY(), null);
    }

    public Integer getSecondsToArrive() {
        return secondsToArrive;
    }

    public void setSecondsToArrive(Integer secondsToArrive) {
        this.secondsToArrive = secondsToArrive;
    }

}
