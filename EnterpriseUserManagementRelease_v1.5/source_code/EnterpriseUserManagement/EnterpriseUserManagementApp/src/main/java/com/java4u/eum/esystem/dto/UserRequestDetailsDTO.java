package com.altiux.eum.esystem.dto;

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

	private @JsonProperty("operatedUserId") String operatedUserId;

	private @JsonProperty("createdTime") String createdTime;

	public UserRequestDetailsDTO() {
		super();
	}

	public String getOperationType() {
		return operationType;
	}

	public String getOperatedUserId() {
		return operatedUserId;
	}

	public void setOperatedUserId(String operatedUserId) {
		this.operatedUserId = operatedUserId;
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

	@Override
	public String toString() {
		return "UserRequestDetailsDTO [requestId=" + requestId + ", operationType=" + operationType
				+ ", requesterUserId=" + requesterUserId + ", enterpriseSiteId=" + enterpriseSiteId + ", state=" + state
				+ ", operatedUserId=" + operatedUserId + ", createdTime=" + createdTime + "]";
	}

	public UserRequestDetailsDTO(String requestId, String operationType, String requesterUserId,
			String enterpriseSiteId, String state, String operatedUserId, String createdTime) {
		super();
		this.requestId = requestId;
		this.operationType = operationType;
		this.requesterUserId = requesterUserId;
		this.enterpriseSiteId = enterpriseSiteId;
		this.state = state;
		this.operatedUserId = operatedUserId;
		this.createdTime = createdTime;
	}

}