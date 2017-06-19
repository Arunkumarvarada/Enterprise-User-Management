package com.java4u.eum.esite.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserRolesDTO {

	private @JsonProperty("userId") String userId;

	private @JsonProperty("roleId") String roleId;

	private @JsonProperty("roleName") String roleName;

	private @JsonProperty("allowedOperations") List<OperationsDTO> allowedOperations;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<OperationsDTO> getAllowedOperations() {
		return allowedOperations;
	}

	public void setAllowedOperations(List<OperationsDTO> allowedOperations) {
		this.allowedOperations = allowedOperations;
	}

	public UserRolesDTO(String userId, String roleId, String roleName, List<OperationsDTO> allowedOperations) {
		super();
		this.userId = userId;
		this.roleId = roleId;
		this.roleName = roleName;
		this.allowedOperations = allowedOperations;
	}

	public UserRolesDTO() {
		super();
	}

}
