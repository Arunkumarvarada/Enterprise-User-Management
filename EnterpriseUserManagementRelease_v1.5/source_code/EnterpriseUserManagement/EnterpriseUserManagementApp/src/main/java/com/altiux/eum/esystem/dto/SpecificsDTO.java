package com.altiux.eum.esystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SpecificsDTO {
	private @JsonProperty("demarcated") Boolean demarcated;

	private @JsonProperty("facility") FacilityDTO facility;

	public Boolean getDemarcated() {
		return demarcated;
	}

	public void setDemarcated(Boolean demarcated) {
		this.demarcated = demarcated;
	}

	public FacilityDTO getFacility() {
		return facility;
	}

	public void setFacility(FacilityDTO facility) {
		this.facility = facility;
	}

	public SpecificsDTO(Boolean demarcated, FacilityDTO facility) {
		super();
		this.demarcated = demarcated;
		this.facility = facility;
	}

	public SpecificsDTO() {
		super();
	}

}
