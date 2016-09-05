package com.altiux.eum.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ParkingSpacesListResponseDTO {
	private @JsonProperty("parkingSpacesList") List<ParkingCompanyLEODTO> parkingSpacesList;

	public List<ParkingCompanyLEODTO> getParkingSpacesList() {
		return parkingSpacesList;
	}

	public void setParkingSpacesList(List<ParkingCompanyLEODTO> parkingSpacesList) {
		this.parkingSpacesList = parkingSpacesList;
	}

	public ParkingSpacesListResponseDTO() {
		super();
	}

	public ParkingSpacesListResponseDTO(List<ParkingCompanyLEODTO> parkingSpacesList) {
		super();
		this.parkingSpacesList = parkingSpacesList;
	}

}
