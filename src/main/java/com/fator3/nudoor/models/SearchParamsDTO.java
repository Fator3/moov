package com.fator3.nudoor.models;

import java.util.List;

public class SearchParamsDTO {

    private Integer rooms;
    private Double value;
    private List<String> references;
    private List<Integer> referencesMinutes;
    
	public Integer getRooms() {
		return rooms;
	}
	public void setRooms(Integer rooms) {
		this.rooms = rooms;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public List<String> getReferences() {
		return references;
	}
	public void setReferences(List<String> references) {
		this.references = references;
	}
	public List<Integer> getReferencesMinutes() {
		return referencesMinutes;
	}
	public void setReferencesMinutes(List<Integer> referencesMinutes) {
		this.referencesMinutes = referencesMinutes;
	}

    

}
