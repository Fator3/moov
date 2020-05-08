package com.fator3.nudoor.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Route {

    private List<Leg> legs;

    @JsonCreator
    public Route(@JsonProperty("legs") List<Leg> legs) {
        this.legs = legs;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

}
