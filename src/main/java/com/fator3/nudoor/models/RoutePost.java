package com.fator3.nudoor.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RoutePost {

    private TimedLatLng property;
    private String address;
    private String transport;
    private TimedLatLng reference;


	@JsonCreator
    public RoutePost(@JsonProperty("property") TimedLatLng property,
    		@JsonProperty("address") String address, @JsonProperty("transport") String transport, 
    		@JsonProperty("reference") TimedLatLng reference) {
        this.property = property;
        this.address = address;
        this.transport = transport;
        this.reference = reference;
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

	public TimedLatLng getReference() {
		return reference;
	}

	public void setReference(TimedLatLng reference) {
		this.reference = reference;
	}
    

}
