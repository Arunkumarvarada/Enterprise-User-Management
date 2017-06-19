package com.java4u.eum.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ParkingCompanyLEODTO {

	private @JsonProperty("parkingSpaceId") String parkingSpaceId;

	private @JsonProperty("leoAssigned") boolean leoAssigned;

	public String getParkingSpaceId() {
		return parkingSpaceId;
	}

	public void setParkingSpaceId(String parkingSpaceId) {
		this.parkingSpaceId = parkingSpaceId;
	}

	public boolean isLeoAssigned() {
		return leoAssigned;
	}

	public void setLeoAssigned(boolean leoAssigned) {
		this.leoAssigned = leoAssigned;
	}

	public ParkingCompanyLEODTO(String parkingSpaceId, boolean leoAssigned) {
		super();
		this.parkingSpaceId = parkingSpaceId;
		this.leoAssigned = leoAssigned;
	}

	public ParkingCompanyLEODTO() {
		super();
	}

}
