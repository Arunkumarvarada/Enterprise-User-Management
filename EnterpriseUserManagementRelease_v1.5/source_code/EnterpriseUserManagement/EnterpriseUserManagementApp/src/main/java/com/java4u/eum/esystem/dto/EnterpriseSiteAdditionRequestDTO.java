package com.altiux.eum.esystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EnterpriseSiteAdditionRequestDTO {
	
	private @JsonProperty("eSiteId") String eSiteId; 
	
	private @JsonProperty("eSiteName") String eSiteName;

	private @JsonProperty("specifics") SpecificsDTO specifics;
	
	private @JsonProperty("user") UserAdditionRequestDTO user;

	public UserAdditionRequestDTO getUser() {
		return user;
	}

	public void setUser(UserAdditionRequestDTO user) {
		this.user = user;
	}

	private @JsonProperty("context") EnterpriseSiteContextInputDTO enterpriseSiteContext;

	public EnterpriseSiteAdditionRequestDTO() {
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
