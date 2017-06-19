package com.java4u.eum.esystem.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EnterpriseSitesListResponseDTO {
	private @JsonProperty("EnterpriseSitesList") List<EnterpriseSystemLEODTO> enterpriseSitesList;


	public List<EnterpriseSystemLEODTO> getEnterpriseSitesList() {
		return enterpriseSitesList;
	}


	public void setEnterpriseSitesList(List<EnterpriseSystemLEODTO> enterpriseSitesList) {
		this.enterpriseSitesList = enterpriseSitesList;
	}


	public EnterpriseSitesListResponseDTO() {
		super();
	}


	public EnterpriseSitesListResponseDTO(List<EnterpriseSystemLEODTO> enterpriseSitesList) {
		super();
		this.enterpriseSitesList = enterpriseSitesList;
	}
	

}
