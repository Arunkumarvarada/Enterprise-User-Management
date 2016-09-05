package com.altiux.eum.esystem.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EnterpriseSystemStatusRepsonse {
	private @JsonProperty("enterpriseSystemId") String enterpriseSystemId;

	private @JsonProperty("active") boolean active;

	public String getEnterpriseSystemId() {
		return enterpriseSystemId;
	}

	public void setEnterpriseSystemId(String enterpriseSystemId) {
		this.enterpriseSystemId = enterpriseSystemId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public EnterpriseSystemStatusRepsonse() {
		super();
	}

	public EnterpriseSystemStatusRepsonse(String enterpriseSystemId, boolean active) {
		super();
		this.enterpriseSystemId = enterpriseSystemId;
		this.active = active;
	}

}
