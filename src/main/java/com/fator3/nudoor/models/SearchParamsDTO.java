package com.fator3.nudoor.models;

import java.util.List;

public class SearchParamsDTO {

    private Boolean isRent;
    private String type;
    private String city;
    private List<ReferenceDTO> references;
    
	public Boolean getIsRent() {
		return isRent;
	}
	public void setIsRent(Boolean isRent) {
		this.isRent = isRent;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public List<ReferenceDTO> getReferences() {
		return references;
	}
	public void setReferences(List<ReferenceDTO> references) {
		this.references = references;
	}
}
