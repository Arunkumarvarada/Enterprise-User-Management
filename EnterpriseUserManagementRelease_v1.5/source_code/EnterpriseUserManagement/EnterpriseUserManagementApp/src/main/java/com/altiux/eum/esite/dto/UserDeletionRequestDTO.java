package com.altiux.eum.esite.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserDeletionRequestDTO {

	private @JsonProperty("enterpriseSiteId") String enterpriseSiteId;
	private @JsonProperty("adminUserId") String adminUserId;
	private @JsonProperty("userId") String userId;

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

	public String getEnterpriseSiteId() {
		return enterpriseSiteId;
	}

	public void setEnterpriseSiteId(String enterpriseSiteId) {
		this.enterpriseSiteId = enterpriseSiteId;
	}

	@Override
	public String toString() {
		return "UserDeletionRequestDTO [enterpriseSiteId=" + enterpriseSiteId + ", adminUserId=" + adminUserId
				+ ", userId=" + userId + "]";
	}

	public UserDeletionRequestDTO(String enterpriseSiteId, String adminUserId, String userId) {
		super();
		this.enterpriseSiteId = enterpriseSiteId;
		this.adminUserId = adminUserId;
		this.userId = userId;
	}
	
	

}
