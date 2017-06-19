package com.java4u.eum.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserResponseDTO {

	private @JsonProperty("requestId") String requestId;

	private @JsonProperty("parkingCompanyId") String parkingCompanyId;

	private @JsonProperty("parkingSpaceId") String parkingSpaceId;

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

	public String getParkingCompanyId() {
		return parkingCompanyId;
	}

	public void setParkingCompanyId(String parkingCompanyId) {
		this.parkingCompanyId = parkingCompanyId;
	}

	public String getParkingSpaceId() {
		return parkingSpaceId;
	}

	public void setParkingSpaceId(String parkingSpaceId) {
		this.parkingSpaceId = parkingSpaceId;
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

	public UserResponseDTO(String requestId, String parkingCompanyId, String parkingSpaceId, String requesterUserId,
			String requestState, String operationType, String userId) {
		super();
		this.requestId = requestId;
		this.parkingCompanyId = parkingCompanyId;
		this.parkingSpaceId = parkingSpaceId;
		this.requesterUserId = requesterUserId;
		this.requestState = requestState;
		this.operationType = operationType;
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserResponseDTO [requestId=" + requestId + ", parkingCompanyId=" + parkingCompanyId
				+ ", parkingSpaceId=" + parkingSpaceId + ", requesterUserId=" + requesterUserId + ", requestState="
				+ requestState + ", operationType=" + operationType + ", userId=" + userId + "]";
	}
	
	
}
