package com.altiux.eum.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserAssigmentRequestDTO {

	private @JsonProperty("operationType") String operationType;

	private @JsonProperty("operatedUserId") String userId;

	private @JsonProperty("enterpriseSiteId") String enterpriseSiteId;

	private @JsonProperty("requestId") String userRequestId;
	
	public String getUserRequestId() {
		return userRequestId;
	}

	public void setUserRequestId(String userRequestId) {
		this.userRequestId = userRequestId;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public UserAssigmentRequestDTO(String operationType, String userId, String enterpriseSiteId, String userRequestId) {
		super();
		this.operationType = operationType;
		this.userId = userId;
		this.enterpriseSiteId = enterpriseSiteId;
		this.userRequestId = userRequestId;
	}

	public String getEnterpriseSiteId() {
		return enterpriseSiteId;
	}

	public void setEnterpriseSiteId(String enterpriseSiteId) {
		this.enterpriseSiteId = enterpriseSiteId;
	}

	public UserAssigmentRequestDTO() {
		super();
	}

}
