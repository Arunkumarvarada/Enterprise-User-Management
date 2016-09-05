package com.altiux.eum.esite.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AllowedPermissionUpdateResponseDTO {

	private @JsonProperty("userId") String userId;
	private @JsonProperty("enterpriseSiteId") String enterpriseSiteId;
	private @JsonProperty("adminUserId") String adminUserId;
	private @JsonProperty("operationsList") List<OperationsDTO> operationsList;
	private @JsonProperty("response") String response;

	public AllowedPermissionUpdateResponseDTO() {
		super();
	}

	public String getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
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

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getEnterpriseSiteId() {
		return enterpriseSiteId;
	}

	public void setEnterpriseSiteId(String enterpriseSiteId) {
		this.enterpriseSiteId = enterpriseSiteId;
	}

	public AllowedPermissionUpdateResponseDTO(String userId, String enterpriseSiteId, String adminUserId,
			List<OperationsDTO> operationsList, String response) {
		super();
		this.userId = userId;
		this.enterpriseSiteId = enterpriseSiteId;
		this.adminUserId = adminUserId;
		this.operationsList = operationsList;
		this.response = response;
	}

	@Override
	public String toString() {
		return "AllowedPermissionUpdateResponseDTO [userId=" + userId + ", enterpriseSiteId=" + enterpriseSiteId
				+ ", adminUserId=" + adminUserId + ", operationsList=" + operationsList + ", response=" + response
				+ "]";
	}

}
