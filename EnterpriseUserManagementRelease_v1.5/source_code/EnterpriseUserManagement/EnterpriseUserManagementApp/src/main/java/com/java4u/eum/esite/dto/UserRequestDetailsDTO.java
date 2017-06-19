package com.java4u.eum.esite.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserRequestDetailsDTO {

	private @JsonProperty("requestId") String requestId;

	private @JsonProperty("operationType") String operationType;

	private @JsonProperty("requesterUserId") String requesterUserId;

	private @JsonProperty("enterpriseSiteId") String enterpriseSiteId;

	private @JsonProperty("state") String state;

	private @JsonProperty("createdTime") String createdTime;

	public UserRequestDetailsDTO() {
		super();
	}

	public UserRequestDetailsDTO(String operationType, String requesterUserId, String enterpriseSiteId) {
		super();
		this.operationType = operationType;
		this.requesterUserId = requesterUserId;
		this.enterpriseSiteId = enterpriseSiteId;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getEnterpriseSiteId() {
		return enterpriseSiteId;
	}

	public void setEnterpriseSiteId(String enterpriseSiteId) {
		this.enterpriseSiteId = enterpriseSiteId;
	}

}