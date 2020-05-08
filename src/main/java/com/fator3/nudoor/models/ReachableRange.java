package com.fator3.nudoor.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReachableRange {

    private LatLng center;
    private List<LatLng> boundary;

    @JsonCreator
    public ReachableRange(@JsonProperty("center") LatLng center,
            @JsonProperty("boundary") List<LatLng> boundary) {
        this.center = center;
        this.boundary = boundary;
    }

    public static ReachableRange of(final LatLng center, final List<LatLng> boundary) {
        return new ReachableRange(center, boundary);
    }

    public LatLng getCenter() {
        return center;
    }

    public void setCenter(LatLng center) {
        this.center = center;
    }

    public List<LatLng> getBoundary() {
        return boundary;
    }

    public void setBoundary(List<LatLng> boundary) {
        this.boundary = boundary;
    }
    
}
