package com.fator3.nudoor.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RouteResponse {

    private String formatVersion;
    private List<Route> routes;

    @JsonCreator
    public RouteResponse(@JsonProperty("formatVersion") String formatVersion,
            @JsonProperty("routes") List<Route> routes) {
        this.formatVersion = formatVersion;
        this.routes = routes;
    }

    public String getFormatVersion() {
        return formatVersion;
    }

    public void setFormatVersion(String formatVersion) {
        this.formatVersion = formatVersion;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

}
