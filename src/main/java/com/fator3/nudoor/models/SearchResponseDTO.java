package com.fator3.nudoor.models;

import java.util.List;

import com.fator3.nudoor.property.Property;

public class SearchResponseDTO {
	
	private List<ReferenceDTO> references;
	private List<Property> properties;
	
	public List<ReferenceDTO> getReferences() {
		return references;
	}
	public void setReferences(List<ReferenceDTO> references) {
		this.references = references;
	}
	public List<Property> getProperties() {
		return properties;
	}
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	
	public SearchResponseDTO(List<ReferenceDTO> references, List<Property> properties) {
		super();
		this.references = references;
		this.properties = properties;
	}
	
	
	
}
