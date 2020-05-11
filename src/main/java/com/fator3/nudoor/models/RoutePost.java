package com.fator3.nudoor.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RoutePost {

    private TimedLatLng property;
    private String address;
    private String transport;

    @JsonCreator
    public RoutePost(@JsonProperty("property") TimedLatLng property,
    		@JsonProperty("address") String address, @JsonProperty("transport") String transport) {
        this.property = property;
        this.address = address;
        this.transport = transport;
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

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}
    

}
