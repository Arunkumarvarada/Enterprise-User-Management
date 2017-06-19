package com.java4u.eum.esite.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserDeletionResponseDTO {
	private @JsonProperty("enterpriseSiteId") String enterpriseSiteId;
	private @JsonProperty("adminUserId") String adminUserId;
	private @JsonProperty("userId") String userId;
	private @JsonProperty("response") String response;

	public UserDeletionResponseDTO() {
		super();
	}

	public UserDeletionResponseDTO(String enterpriseSiteId, String adminUserId, String userId, String response) {
		super();
		this.enterpriseSiteId = enterpriseSiteId;
		this.adminUserId = adminUserId;
		this.userId = userId;
		this.response = response;
	}

	public String getEnterpriseSiteId() {
		return enterpriseSiteId;
	}

	public void setEnterpriseSiteId(String enterpriseSiteId) {
		this.enterpriseSiteId = enterpriseSiteId;
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
		return "UserDeletionResponseDTO [enterpriseSiteId=" + enterpriseSiteId + ", adminUserId=" + adminUserId
				+ ", userId=" + userId + ", response=" + response + "]";
	}

}
