package com.fator3.nudoor.models;

public class ReferenceDTO {

    private String address;
    private Integer time;
    private String transport;
    private TimedLatLng latLon;
    
	public TimedLatLng getLatLon() {
		return latLon;
	}
	public void setLatLon(TimedLatLng latLon) {
		this.latLon = latLon;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public String getTransport() {
		return transport;
	}
	public void setTransport(String transport) {
		this.transport = transport;
	}
    
}
