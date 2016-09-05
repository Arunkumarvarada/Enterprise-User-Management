package com.altiux.eum.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserDeletionRequestDTO {

	private @JsonProperty("enterpriseSystemId") String enterpriseSystemId;
	private @JsonProperty("adminUserId") String adminUserId;
	private @JsonProperty("userId") String userId;

	public UserDeletionRequestDTO(String enterpriseSystemId, String adminUserId, String userId) {
		super();
		this.enterpriseSystemId = enterpriseSystemId;
		this.adminUserId = adminUserId;
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserDeletionRequestDTO [enterpriseSystemId=" + enterpriseSystemId + ", adminUserId=" + adminUserId
				+ ", userId=" + userId + "]";
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

	public UserDeletionRequestDTO() {
		super();
	}

}
