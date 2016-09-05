package com.altiux.eum.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AllEnterpriseSiteUsersDTO {
	private @JsonProperty("enterpriseSystemId") String enterpriseSystemId;
	private @JsonProperty("parkingSpaceId") String parkingSpaceId;
	private @JsonProperty("userResponse") List<UserAdditionResponseDTO> userResponse;

	public String getParkingSpaceId() {
		return parkingSpaceId;
	}

	public void setParkingSpaceId(String parkingSpaceId) {
		this.parkingSpaceId = parkingSpaceId;
	}

	public List<UserAdditionResponseDTO> getUserResponse() {
		return userResponse;
	}

	public void setUserResponse(List<UserAdditionResponseDTO> userResponse) {
		this.userResponse = userResponse;
	}

	public String getEnterpriseSystemId() {
		return enterpriseSystemId;
	}

	public void setEnterpriseSystemId(String enterpriseSystemId) {
		this.enterpriseSystemId = enterpriseSystemId;
	}

	@Override
	public String toString() {
		return "AllEnterpriseSiteUsersDTO [enterpriseSystemId=" + enterpriseSystemId + ", parkingSpaceId="
				+ parkingSpaceId + ", userResponse=" + userResponse + "]";
	}

	public AllEnterpriseSiteUsersDTO(String enterpriseSystemId, String parkingSpaceId,
			List<UserAdditionResponseDTO> userResponse) {
		super();
		this.enterpriseSystemId = enterpriseSystemId;
		this.parkingSpaceId = parkingSpaceId;
		this.userResponse = userResponse;
	}

	public AllEnterpriseSiteUsersDTO() {
		super();
	}

}
