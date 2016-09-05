package com.altiux.eum.esystem.dto;

import com.altiux.eum.dto.UserAdditionResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EnterpriseSiteAdditionResponseDTO {

	private @JsonProperty("eSiteId") String eSiteId;

	private @JsonProperty("eSiteName") String eSiteName;

	private @JsonProperty("specifics") SpecificsDTO specifics;
	
	private @JsonProperty("user") UserAdditionResponseDTO user;

	public UserAdditionResponseDTO getUser() {
		return user;
	}

	public void setUser(UserAdditionResponseDTO user) {
		this.user = user;
	}

	private @JsonProperty("context") EnterpriseSiteContextInputDTO enterpriseSiteContext;

	public EnterpriseSiteAdditionResponseDTO() {
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
