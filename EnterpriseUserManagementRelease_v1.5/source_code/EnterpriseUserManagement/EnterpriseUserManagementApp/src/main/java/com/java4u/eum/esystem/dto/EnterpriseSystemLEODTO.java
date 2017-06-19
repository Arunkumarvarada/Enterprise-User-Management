package com.java4u.eum.esystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EnterpriseSystemLEODTO {

	private @JsonProperty("enterpriseSystemId") String enterpriseSystemId;

	private @JsonProperty("leoAssigned") boolean leoAssigned;

	public String getEnterpriseSystemId() {
		return enterpriseSystemId;
	}

	public void setEnterpriseSystemId(String enterpriseSystemId) {
		this.enterpriseSystemId = enterpriseSystemId;
	}

	public boolean isLeoAssigned() {
		return leoAssigned;
	}

	public void setLeoAssigned(boolean leoAssigned) {
		this.leoAssigned = leoAssigned;
	}

	public EnterpriseSystemLEODTO(String enterpriseSystemId, boolean leoAssigned) {
		super();
		this.enterpriseSystemId = enterpriseSystemId;
		this.leoAssigned = leoAssigned;
	}

	public EnterpriseSystemLEODTO() {
		super();
	}

}
