package com.java4u.eum.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AllUsersResponseDTO {
	private @JsonProperty("parkingCompanyId") String parkingCompanyId;
	private @JsonProperty("userResponse") List<UserAdditionResponseDTO> userResponse;
	private @JsonProperty("totalRecords") Integer totalRecords;

	public String getParkingCompanyId() {
		return parkingCompanyId;
	}

	public void setParkingCompanyId(String parkingCompanyId) {
		this.parkingCompanyId = parkingCompanyId;
	}

	public List<UserAdditionResponseDTO> getUserResponse() {
		return userResponse;
	}

	public void setUserResponse(List<UserAdditionResponseDTO> userResponse) {
		this.userResponse = userResponse;
	}

	public Integer getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	public AllUsersResponseDTO() {
		super();
	}

	public AllUsersResponseDTO(String parkingCompanyId, List<UserAdditionResponseDTO> userResponse,
			Integer totalRecords) {
		super();
		this.parkingCompanyId = parkingCompanyId;
		this.userResponse = userResponse;
		this.totalRecords = totalRecords;
	}

	@Override
	public String toString() {
		return "AllUsersResponseDTO [parkingCompanyId=" + parkingCompanyId + ", userResponse=" + userResponse
				+ ", totalRecords=" + totalRecords + "]";
	}
	
}
