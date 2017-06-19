package com.java4u.eum.esystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserResponseDTO {

	private @JsonProperty("requestId") String requestId;

	private @JsonProperty("enterpriseSystemId") String enterpriseSystemId;

	private @JsonProperty("enterpriseSiteId") String enterpriseSiteId;

	private @JsonProperty("requesterUserId") String requesterUserId;

	private @JsonProperty("requestState") String requestState;

	private @JsonProperty("operationType") String operationType;

	private @JsonProperty("userId") String userId;

	public UserResponseDTO() {

	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRequesterUserId() {
		return requesterUserId;
	}

	public void setRequesterUserId(String requesterUserId) {
		this.requesterUserId = requesterUserId;
	}

	public String getRequestState() {
		return requestState;
	}

	public void setRequestState(String requestState) {
		this.requestState = requestState;
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

	public String getEnterpriseSystemId() {
		return enterpriseSystemId;
	}

	public void setEnterpriseSystemId(String enterpriseSystemId) {
		this.enterpriseSystemId = enterpriseSystemId;
	}

	public String getEnterpriseSiteId() {
		return enterpriseSiteId;
	}

	public void setEnterpriseSiteId(String enterpriseSiteId) {
		this.enterpriseSiteId = enterpriseSiteId;
	}

	public UserResponseDTO(String requestId, String enterpriseSystemId, String enterpriseSiteId, String requesterUserId,
			String requestState, String operationType, String userId) {
		super();
		this.requestId = requestId;
		this.enterpriseSystemId = enterpriseSystemId;
		this.enterpriseSiteId = enterpriseSiteId;
		this.requesterUserId = requesterUserId;
		this.requestState = requestState;
		this.operationType = operationType;
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserResponseDTO [requestId=" + requestId + ", enterpriseSystemId=" + enterpriseSystemId
				+ ", enterpriseSiteId=" + enterpriseSiteId + ", requesterUserId=" + requesterUserId + ", requestState="
				+ requestState + ", operationType=" + operationType + ", userId=" + userId + "]";
	}

}
