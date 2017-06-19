package com.java4u.eum.esystem.dto;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EnterpriseSitesListDTO {
	private @JsonProperty("enterpriseSitesList") Set<String> enterpriseSitesList;

	public Set<String> getEnterpriseSitesList() {
		return enterpriseSitesList;
	}

	public void setEnterpriseSitesList(Set<String> enterpriseSitesList) {
		this.enterpriseSitesList = enterpriseSitesList;
	}

	
	public EnterpriseSitesListDTO(Set<String> enterpriseSitesList) {
		super();
		this.enterpriseSitesList = enterpriseSitesList;
	}

	public EnterpriseSitesListDTO() {
		super();
	}

}
