package com.java4u.eum.esystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserDeletionResponseDTO {
	private @JsonProperty("enterpriseSystemId") String enterpriseSystemId;
	private @JsonProperty("adminUserId") String adminUserId;
	private @JsonProperty("userId") String userId;
	private @JsonProperty("response") String response;

	public UserDeletionResponseDTO() {
		super();
	}

	public String getEnterpriseSystemId() {
		return enterpriseSystemId;
	}

	public void setEnterpriseSystemId(String enterpriseSystemId) {
		this.enterpriseSystemId = enterpriseSystemId;
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

	public UserDeletionResponseDTO(String enterpriseSystemId, String adminUserId, String userId, String response) {
		super();
		this.enterpriseSystemId = enterpriseSystemId;
		this.adminUserId = adminUserId;
		this.userId = userId;
		this.response = response;
	}

	@Override
	public String toString() {
		return "UserDeletionResponseDTO [enterpriseSystemId=" + enterpriseSystemId + ", adminUserId=" + adminUserId
				+ ", userId=" + userId + ", response=" + response + "]";
	}

}
