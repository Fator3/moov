package com.fator3.moov.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RoutePost {

    private TimedLatLng property;
    private String address;

    @JsonCreator
    public RoutePost(@JsonProperty("property") TimedLatLng property,
            @JsonProperty("address") String address) {
        this.property = property;
        this.address = address;
    }

    public TimedLatLng getProperty() {
        return property;
    }

    public void setProperty(TimedLatLng property) {
        this.property = property;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
