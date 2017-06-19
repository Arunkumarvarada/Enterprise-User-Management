package com.java4u.eum.esystem.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AllEnterpriseSiteUsersDTO {
	private @JsonProperty("enterpriseSystemId") String enterpriseSystemId;
	private @JsonProperty("enterpriseSiteId") String enterpriseSiteId;
	private @JsonProperty("userResponse") List<UserAdditionResponseDTO> userResponse;

	
	public String getEnterpriseSiteId() {
		return enterpriseSiteId;
	}

	public void setEnterpriseSiteId(String enterpriseSiteId) {
		this.enterpriseSiteId = enterpriseSiteId;
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
		return "AllEnterpriseSiteUsersDTO [enterpriseSystemId=" + enterpriseSystemId + ", enterpriseSiteId="
				+ enterpriseSiteId + ", userResponse=" + userResponse + "]";
	}

	public AllEnterpriseSiteUsersDTO(String enterpriseSystemId, String enterpriseSiteId,
			List<UserAdditionResponseDTO> userResponse) {
		super();
		this.enterpriseSystemId = enterpriseSystemId;
		this.enterpriseSiteId = enterpriseSiteId;
		this.userResponse = userResponse;
	}

	public AllEnterpriseSiteUsersDTO() {
		super();
	}

}
