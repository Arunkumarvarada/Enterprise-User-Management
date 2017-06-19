package com.java4u.eum.esite.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RolesDTO {

	private @JsonProperty("roleId") String roleId;

	private @JsonProperty("roleName") String roleName;

	private @JsonProperty("permittedOperations") List<OperationsDTO> permittedOperations;

	public RolesDTO() {

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

	public List<OperationsDTO> getPermittedOperations() {
		return permittedOperations;
	}

	public void setPermittedOperations(List<OperationsDTO> permittedOperations) {
		this.permittedOperations = permittedOperations;
	}

	public RolesDTO(String roleId, String roleName, List<OperationsDTO> permittedOperations) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.permittedOperations = permittedOperations;
	}

	public RolesDTO(String roleName, List<OperationsDTO> permittedOperations) {
		super();
		this.roleName = roleName;
		this.permittedOperations = permittedOperations;
	}

	@Override
	public String toString() {
		return "RolesDTO [roleId=" + roleId + ", roleName=" + roleName + ", permittedOperations=" + permittedOperations
				+ "]";
	}

}
