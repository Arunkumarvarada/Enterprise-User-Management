package com.altiux.eum.esystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class FacilityDTO {
	private @JsonProperty("facilityType") String facilityType;

	private @JsonProperty("geoLocation") GeoLocationDTO geoLocation;

	private @JsonProperty("address") EnterpriseSiteAddressDTO address;

	public String getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	public GeoLocationDTO getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(GeoLocationDTO geoLocation) {
		this.geoLocation = geoLocation;
	}

	public EnterpriseSiteAddressDTO getAddress() {
		return address;
	}

	public void setAddress(EnterpriseSiteAddressDTO address) {
		this.address = address;
	}

	public FacilityDTO() {
		super();
	}

	public FacilityDTO(String facilityType, GeoLocationDTO geoLocation, EnterpriseSiteAddressDTO address) {
		super();
		this.facilityType = facilityType;
		this.geoLocation = geoLocation;
		this.address = address;
	}
	
	
}
