package com.fator3.nudoor.models;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Summary {

    private Double lengthInMeters;
    private Integer travelTimeInSeconds;
    private Integer trafficDelayInSeconds;
    private ZonedDateTime departureTime;
    private ZonedDateTime arrivalTime;
    private Integer noTrafficTravelTimeInSeconds;
    private Integer historicTrafficTravelTimeInSeconds;
    private Integer liveTrafficIncidentsTravelTimeInSeconds;
    private Double fuelConsumptionInLiters;

    @JsonCreator
    public Summary(@JsonProperty("lengthInMeters") Double lengthInMeters,
            @JsonProperty("travelTimeInSeconds") Integer travelTimeInSeconds,
            @JsonProperty("trafficDelayInSeconds") Integer trafficDelayInSeconds,
            @JsonProperty("departureTime") ZonedDateTime departureTime,
            @JsonProperty("arrivalTime") ZonedDateTime arrivalTime,
            @JsonProperty("noTrafficTravelTimeInSeconds") Integer noTrafficTravelTimeInSeconds,
            @JsonProperty("historicTrafficTravelTimeInSeconds") Integer historicTrafficTravelTimeInSeconds,
            @JsonProperty("liveTrafficIncidentsTravelTimeInSeconds") Integer liveTrafficIncidentsTravelTimeInSeconds,
            @JsonProperty("fuelConsumptionInLiters") Double fuelConsumptionInLiters) {
        this.lengthInMeters = lengthInMeters;
        this.travelTimeInSeconds = travelTimeInSeconds;
        this.trafficDelayInSeconds = trafficDelayInSeconds;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.noTrafficTravelTimeInSeconds = noTrafficTravelTimeInSeconds;
        this.historicTrafficTravelTimeInSeconds = historicTrafficTravelTimeInSeconds;
        this.liveTrafficIncidentsTravelTimeInSeconds = liveTrafficIncidentsTravelTimeInSeconds;
        this.fuelConsumptionInLiters = fuelConsumptionInLiters;
    }

    public Double getLengthInMeters() {
        return lengthInMeters;
    }

    public void setLengthInMeters(Double lengthInMeters) {
        this.lengthInMeters = lengthInMeters;
    }

    public Integer getTravelTimeInSeconds() {
        return travelTimeInSeconds;
    }

    public void setTravelTimeInSeconds(Integer travelTimeInSeconds) {
        this.travelTimeInSeconds = travelTimeInSeconds;
    }

    public Integer getTrafficDelayInSeconds() {
        return trafficDelayInSeconds;
    }

    public void setTrafficDelayInSeconds(Integer trafficDelayInSeconds) {
        this.trafficDelayInSeconds = trafficDelayInSeconds;
    }

    public ZonedDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(ZonedDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public ZonedDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(ZonedDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getNoTrafficTravelTimeInSeconds() {
        return noTrafficTravelTimeInSeconds;
    }

    public void setNoTrafficTravelTimeInSeconds(Integer noTrafficTravelTimeInSeconds) {
        this.noTrafficTravelTimeInSeconds = noTrafficTravelTimeInSeconds;
    }

    public Integer getHistoricTrafficTravelTimeInSeconds() {
        return historicTrafficTravelTimeInSeconds;
    }

    public void setHistoricTrafficTravelTimeInSeconds(Integer historicTrafficTravelTimeInSeconds) {
        this.historicTrafficTravelTimeInSeconds = historicTrafficTravelTimeInSeconds;
    }

    public Integer getLiveTrafficIncidentsTravelTimeInSeconds() {
        return liveTrafficIncidentsTravelTimeInSeconds;
    }

    public void setLiveTrafficIncidentsTravelTimeInSeconds(
            Integer liveTrafficIncidentsTravelTimeInSeconds) {
        this.liveTrafficIncidentsTravelTimeInSeconds = liveTrafficIncidentsTravelTimeInSeconds;
    }

    public Double getFuelConsumptionInLiters() {
        return fuelConsumptionInLiters;
    }

    public void setFuelConsumptionInLiters(Double fuelConsumptionInLiters) {
        this.fuelConsumptionInLiters = fuelConsumptionInLiters;
    }

}
