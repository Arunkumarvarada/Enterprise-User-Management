package com.java4u.eum.esystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnterpriseSiteAdditionRequestNonAdminDTO {

	private @JsonProperty("eSiteId") String eSiteId; 
	
	private @JsonProperty("eSiteName") String eSiteName;

	private @JsonProperty("specifics") SpecificsDTO specifics;
	
	private @JsonProperty("context") EnterpriseSiteContextInputDTO enterpriseSiteContext;

	public EnterpriseSiteAdditionRequestNonAdminDTO() {
		super();
	}

	public String geteSiteName() {
		return eSiteName;
	}

	public void seteSiteName(String eSiteName) {
		this.eSiteName = eSiteName;
	}

	public SpecificsDTO getSpecifics() {
		return specifics;
	}

	public void setSpecifics(SpecificsDTO specifics) {
		this.specifics = specifics;
	}

	public EnterpriseSiteContextInputDTO getEnterpriseSiteContext() {
		return enterpriseSiteContext;
	}

	public void setEnterpriseSiteContext(EnterpriseSiteContextInputDTO enterpriseSiteContext) {
		this.enterpriseSiteContext = enterpriseSiteContext;
	}

	public String geteSiteId() {
		return eSiteId;
	}

	public void seteSiteId(String eSiteId) {
		this.eSiteId = eSiteId;
	}

	
}
