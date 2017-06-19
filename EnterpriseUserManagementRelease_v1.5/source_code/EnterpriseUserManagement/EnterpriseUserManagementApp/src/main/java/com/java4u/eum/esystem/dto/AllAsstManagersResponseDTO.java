package com.java4u.eum.esystem.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AllAsstManagersResponseDTO {
	private @JsonProperty("enterpriseSystemId") String enterpriseSystemId;

	private @JsonProperty("userResponse") List<UserAdditionResponseDTO> userResponse;
	private @JsonProperty("totalRecords") Integer totalRecords;

	public Integer getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	public AllAsstManagersResponseDTO() {
		super();
	}

	public List<UserAdditionResponseDTO> getUserResponse() {
		return userResponse;
	}

	public void setUserResponse(List<UserAdditionResponseDTO> userResponse) {
		this.userResponse = userResponse;
	}

	public AllAsstManagersResponseDTO(String enterpriseSystemId, List<UserAdditionResponseDTO> userResponse,
			Integer totalRecords) {
		super();
		this.enterpriseSystemId = enterpriseSystemId;
		this.userResponse = userResponse;
		this.totalRecords = totalRecords;
	}

	@Override
	public String toString() {
		return "AllAsstManagersResponseDTO [enterpriseSystemId=" + enterpriseSystemId + ", userResponse=" + userResponse
				+ ", totalRecords=" + totalRecords + "]";
	}

	public String getEnterpriseSystemId() {
		return enterpriseSystemId;
	}

	public void setEnterpriseSystemId(String enterpriseSystemId) {
		this.enterpriseSystemId = enterpriseSystemId;
	}
	

}
