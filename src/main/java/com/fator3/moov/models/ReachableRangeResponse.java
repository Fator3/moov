package com.fator3.moov.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReachableRangeResponse {

    private String formatVersion;
    private ReachableRange reachableRange;

    @JsonCreator
    public ReachableRangeResponse(@JsonProperty("formatVersion") String formatVersion,
            @JsonProperty("reachableRange") ReachableRange reachableRange) {
        this.formatVersion = formatVersion;
        this.reachableRange = reachableRange;
    }

    public String getFormatVersion() {
        return formatVersion;
    }

    public void setFormatVersion(String formatVersion) {
        this.formatVersion = formatVersion;
    }

    public ReachableRange getReachableRange() {
        return reachableRange;
    }

    public void setReachableRange(ReachableRange reachableRange) {
        this.reachableRange = reachableRange;
    }

}
