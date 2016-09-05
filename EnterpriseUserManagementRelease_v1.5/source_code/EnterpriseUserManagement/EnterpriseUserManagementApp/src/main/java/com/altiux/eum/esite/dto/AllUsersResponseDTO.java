package com.altiux.eum.esite.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AllUsersResponseDTO {
	private @JsonProperty("enterpriseSiteId") String enterpriseSiteId;
	private @JsonProperty("userResponse") List<UserAdditionResponseDTO> userResponse;
	private @JsonProperty("adminUserId") String adminUserId;

	public AllUsersResponseDTO(String enterpriseSiteId, List<UserAdditionResponseDTO> userResponse,
			String adminUserId) {
		this.enterpriseSiteId = enterpriseSiteId;
		this.userResponse = userResponse;
		this.adminUserId = adminUserId;
	}

	public AllUsersResponseDTO() {
		super();
	}

	public List<UserAdditionResponseDTO> getUserResponse() {
		return userResponse;
	}

	public void setUserResponse(List<UserAdditionResponseDTO> userResponse) {
		this.userResponse = userResponse;
	}

	public String getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getEnterpriseSiteId() {
		return enterpriseSiteId;
	}

	public void setEnterpriseSiteId(String enterpriseSiteId) {
		this.enterpriseSiteId = enterpriseSiteId;
	}

	@Override
	public String toString() {
		return "AllUsersResponse [enterpriseSiteId=" + enterpriseSiteId + ", userResponse=" + userResponse
				+ ", adminUserId=" + adminUserId + "]";
	}

}
