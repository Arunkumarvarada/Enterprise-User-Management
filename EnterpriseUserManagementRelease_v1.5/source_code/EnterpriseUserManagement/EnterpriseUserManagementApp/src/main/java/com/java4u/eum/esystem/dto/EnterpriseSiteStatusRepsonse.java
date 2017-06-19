package com.java4u.eum.esystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EnterpriseSiteStatusRepsonse {
	private @JsonProperty("enterpriseSiteId") String enterpriseSiteId;

	private @JsonProperty("active") boolean active;

	public String getEnterpriseSiteId() {
		return enterpriseSiteId;
	}

	public void setEnterpriseSiteId(String enterpriseSiteId) {
		this.enterpriseSiteId = enterpriseSiteId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public EnterpriseSiteStatusRepsonse(String enterpriseSiteId, boolean active) {
		super();
		this.enterpriseSiteId = enterpriseSiteId;
		this.active = active;
	}

	public EnterpriseSiteStatusRepsonse() {
		super();
	}

}
