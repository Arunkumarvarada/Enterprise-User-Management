package com.altiux.eum.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AllManagersResponseDTO {

	private @JsonProperty("parkingCompanyId") String parkingCompanyId;
	private @JsonProperty("userResponse") List<ManagersDTO> userResponse;

	public String getParkingCompanyId() {
		return parkingCompanyId;
	}

	public void setParkingCompanyId(String parkingCompanyId) {
		this.parkingCompanyId = parkingCompanyId;
	}

	public List<ManagersDTO> getUserResponse() {
		return userResponse;
	}

	public void setUserResponse(List<ManagersDTO> userResponse) {
		this.userResponse = userResponse;
	}

	public AllManagersResponseDTO(String parkingCompanyId, List<ManagersDTO> userResponse) {
		super();
		this.parkingCompanyId = parkingCompanyId;
		this.userResponse = userResponse;
	}

	public AllManagersResponseDTO() {
		super();
	}

	@Override
	public String toString() {
		return "AllUsersResponseDTO [parkingCompanyId=" + parkingCompanyId + ", userResponse=" + userResponse + "]";
	}

	
}