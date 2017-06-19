package com.java4u.eum.esite.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AllowedPermissionUpdateRequestDTO {
	
	private @JsonProperty("userId") String userId;
	private @JsonProperty("operationsList") List<OperationsDTO> operationsList;
	private @JsonProperty("adminUserId") String adminUserId;

	public AllowedPermissionUpdateRequestDTO() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<OperationsDTO> getOperationsList() {
		return operationsList;
	}

	public void setOperationsList(List<OperationsDTO> operationsList) {
		this.operationsList = operationsList;
	}

	public String getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}

	public AllowedPermissionUpdateRequestDTO(String userId, List<OperationsDTO> operationsList,
			String adminUserId) {
		super();
		this.userId = userId;
		this.operationsList = operationsList;
		this.adminUserId = adminUserId;
	}

	@Override
	public String toString() {
		return "AllowedPermissionUpdateRequestDTO [userId=" + userId + ", operationsList=" + operationsList
				+ ", adminUserId=" + adminUserId + "]";
	}



}
