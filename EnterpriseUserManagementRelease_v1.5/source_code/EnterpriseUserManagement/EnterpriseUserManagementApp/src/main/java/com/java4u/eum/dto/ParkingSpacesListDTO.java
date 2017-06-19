package com.java4u.eum.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ParkingSpacesListDTO {
	private @JsonProperty("parkingSpacesList") List<String> parkingSpacesList;

	public List<String> getParkingSpacesList() {
		return parkingSpacesList;
	}

	public void setParkingSpacesList(List<String> parkingSpacesList) {
		this.parkingSpacesList = parkingSpacesList;
	}

	public ParkingSpacesListDTO(List<String> parkingSpacesList) {
		super();
		this.parkingSpacesList = parkingSpacesList;
	}

	public ParkingSpacesListDTO() {
		super();
	}
	
}
