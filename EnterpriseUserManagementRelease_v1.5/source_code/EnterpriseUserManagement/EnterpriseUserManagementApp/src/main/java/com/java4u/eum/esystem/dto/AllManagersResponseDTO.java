package com.java4u.eum.esystem.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AllManagersResponseDTO {

	private @JsonProperty("enterpriseSystemId") String enterpriseSystemId;
	private @JsonProperty("userResponse") List<ManagersDTO> userResponse;

	public List<ManagersDTO> getUserResponse() {
		return userResponse;
	}

	public void setUserResponse(List<ManagersDTO> userResponse) {
		this.userResponse = userResponse;
	}

	public AllManagersResponseDTO(String enterpriseSystemId, List<ManagersDTO> userResponse) {
		super();
		this.enterpriseSystemId = enterpriseSystemId;
		this.userResponse = userResponse;
	}

	public AllManagersResponseDTO() {
		super();
	}

	@Override
	public String toString() {
		return "AllUsersResponseDTO [enterpriseSystemId=" + enterpriseSystemId + ", userResponse=" + userResponse + "]";
	}

	public String getEnterpriseSystemId() {
		return enterpriseSystemId;
	}

	public void setEnterpriseSystemId(String enterpriseSystemId) {
		this.enterpriseSystemId = enterpriseSystemId;
	}

}