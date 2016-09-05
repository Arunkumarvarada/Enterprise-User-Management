package com.altiux.eum.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserDeletionResponseDTO {
	private @JsonProperty("parkingCompanyId") String parkingCompanyId;
	private @JsonProperty("adminUserId") String adminUserId;
	private @JsonProperty("userId") String userId;
	private @JsonProperty("response") String response;

	public UserDeletionResponseDTO() {
		super();
	}

	public UserDeletionResponseDTO(String parkingCompanyId, String adminUserId, String userId, String response) {
		super();
		this.parkingCompanyId = parkingCompanyId;
		this.adminUserId = adminUserId;
		this.userId = userId;
		this.response = response;
	}

	public String getParkingCompanyId() {
		return parkingCompanyId;
	}

	public void setParkingCompanyId(String parkingCompanyId) {
		this.parkingCompanyId = parkingCompanyId;
	}

	public String getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "UserDeletionResponseDTO [parkingCompanyId=" + parkingCompanyId + ", adminUserId=" + adminUserId
				+ ", userId=" + userId + ", response=" + response + "]";
	}

}
