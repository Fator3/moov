package com.fator3.nudoor.property;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "property")
public class Property {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String status;
	private String agencyReference;
	private String type;
	private String purpose;
	private String address;
	private String complement;
	private String district;
	private String complex;
	private String city;
	private BigDecimal sellPrice;
	private BigDecimal rentalPrice;
	private BigDecimal complexFee;
	private BigDecimal propertyTax;
	private Integer bedrooms;
	private Integer suites;
	private Integer parkingSpaces;
	private Date dateAdded;
	private BigDecimal totalArea;
	private BigDecimal area;
	private String number;
	private String fullAddress;
	private String agency;
	private BigDecimal latitude;
	private BigDecimal longitude;

	@JsonIgnore
	@Column(name = "active")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean active = true;

	@JsonIgnore
	@CreationTimestamp
	@Column(name = "created_timestamp", updatable = false)
	private LocalDateTime createdTimestamp;

	@JsonIgnore
	@UpdateTimestamp
	@Column(name = "updated_timestamp")
	private LocalDateTime updatedTimestamp;

	Property() {
	}

	public Property(String status,
			String agencyReference,
			String type,
			String purpose,
			String address,
			String complement,
			String district,
			String complex,
			String city,
			BigDecimal sellPrice,
			BigDecimal rentalPrice,
			BigDecimal complexFee,
			BigDecimal propertyTax,
			Integer bedrooms,
			Integer suites,
			Integer parkingSpaces,
			Date dateAdded,
			BigDecimal totalArea,
			BigDecimal area,
			String number,
			String fullAddress,
			String agency,
			BigDecimal latitude,
			BigDecimal longitude) {
		this.status = status;
		this.agencyReference = agencyReference;
		this.type = type;
		this.purpose = purpose;
		this.address = address;
		this.complement = complement;
		this.district = district;
		this.complex = complex;
		this.city = city;
		this.sellPrice = sellPrice;
		this.rentalPrice = rentalPrice;
		this.complexFee = complexFee;
		this.propertyTax = propertyTax;
		this.bedrooms = bedrooms;
		this.suites = suites;
		this.parkingSpaces = parkingSpaces;
		this.dateAdded = dateAdded;
		this.totalArea = totalArea;
		this.area = area;
		this.number = number;
		this.fullAddress = fullAddress;
		this.agency = agency;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public static Property of(String status,
			String agencyReference,
			String type,
			String purpose,
			String address,
			String complement,
			String district,
			String complex,
			String city,
			BigDecimal sellPrice,
			BigDecimal rentalPrice,
			BigDecimal complexFee,
			BigDecimal propertyTax,
			Integer bedrooms,
			Integer suites,
			Integer parkingSpaces,
			Date dateAdded,
			BigDecimal totalArea,
			BigDecimal area,
			String number,
			String fullAddress,
			String agency,
			BigDecimal latitude,
			BigDecimal longitude) {
		return new Property(status,
				agencyReference,
				type,
				purpose,
				address,
				complement,
				district,
				complex,
				city,
				sellPrice,
				rentalPrice,
				complexFee,
				propertyTax,
				bedrooms,
				suites,
				parkingSpaces,
				dateAdded,
				totalArea,
				area,
				number,
				fullAddress,
				agency,
				latitude,
				longitude);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAgencyReference() {
		return agencyReference;
	}

	public void setAgencyReference(String agencyReference) {
		this.agencyReference = agencyReference;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getComplex() {
		return complex;
	}

	public void setComplex(String complex) {
		this.complex = complex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public BigDecimal getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}

	public BigDecimal getRentalPrice() {
		return rentalPrice;
	}

	public void setRentalPrice(BigDecimal rentalPrice) {
		this.rentalPrice = rentalPrice;
	}

	public BigDecimal getComplexFee() {
		return complexFee;
	}

	public void setComplexFee(BigDecimal complexFee) {
		this.complexFee = complexFee;
	}

	public BigDecimal getPropertyTax() {
		return propertyTax;
	}

	public void setPropertyTax(BigDecimal propertyTax) {
		this.propertyTax = propertyTax;
	}

	public Integer getBedrooms() {
		return bedrooms;
	}

	public void setBedrooms(Integer bedrooms) {
		this.bedrooms = bedrooms;
	}

	public Integer getSuites() {
		return suites;
	}

	public void setSuites(Integer suites) {
		this.suites = suites;
	}

	public Integer getParkingSpaces() {
		return parkingSpaces;
	}

	public void setParkingSpaces(Integer parkingSpaces) {
		this.parkingSpaces = parkingSpaces;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public BigDecimal getTotalArea() {
		return totalArea;
	}

	public void setTotalArea(BigDecimal totalArea) {
		this.totalArea = totalArea;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public LocalDateTime getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public LocalDateTime getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	public void setUpdatedTimestamp(LocalDateTime updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Property))
			return false;
		final Property card = (Property) obj;
		return Objects.equals(getId(), card.getId());
	}

}
