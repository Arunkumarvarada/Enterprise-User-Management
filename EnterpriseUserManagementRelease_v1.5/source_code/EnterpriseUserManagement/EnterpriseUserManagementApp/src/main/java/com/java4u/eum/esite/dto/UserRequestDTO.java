package com.altiux.eum.esite.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserRequestDTO {

	private @JsonProperty("operationType") String operationType;
	
	private @JsonProperty("requesterUserId") String requesterUserId;
	
	private @JsonProperty("enterpriseSiteId") String enterpriseSiteId;
	
	private @JsonProperty("operatedUserId") String operatedUserId;
	
	public UserRequestDTO() {
		super();
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getRequesterUserId() {
		return requesterUserId;
	}

	public void setRequesterUserId(String requesterUserId) {
		this.requesterUserId = requesterUserId;
	}

	public String getOperatedUserId() {
		return operatedUserId;
	}

	public void setOperatedUserId(String operatedUserId) {
		this.operatedUserId = operatedUserId;
	}

	public UserRequestDTO(String operationType, String requesterUserId, String enterpriseSiteId,
			String operatedUserId) {
		super();
		this.operationType = operationType;
		this.requesterUserId = requesterUserId;
		this.enterpriseSiteId = enterpriseSiteId;
		this.operatedUserId = operatedUserId;
	}

	public String getEnterpriseSiteId() {
		return enterpriseSiteId;
	}

	public void setEnterpriseSiteId(String enterpriseSiteId) {
		this.enterpriseSiteId = enterpriseSiteId;
	}

	
	
}