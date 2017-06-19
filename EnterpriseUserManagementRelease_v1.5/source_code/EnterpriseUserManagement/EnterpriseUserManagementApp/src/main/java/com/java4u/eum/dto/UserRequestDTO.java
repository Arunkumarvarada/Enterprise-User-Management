package com.java4u.eum.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserRequestDTO {

private @JsonProperty("operationType") String operationType;
	
	private @JsonProperty("requesterUserId") String requesterUserId;
	
	private @JsonProperty("parkingSpaceId") String parkingSpaceId;
	
	public UserRequestDTO() {
		super();
	}

	public UserRequestDTO(String operationType, String requesterUserId,
			String parkingSpaceId) {
		super();
		this.operationType = operationType;
		this.requesterUserId = requesterUserId;
		this.parkingSpaceId = parkingSpaceId;
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

	public String getParkingSpaceId() {
		return parkingSpaceId;
	}

	public void setParkingSpaceId(String parkingSpaceId) {
		this.parkingSpaceId = parkingSpaceId;
	}
	
}
