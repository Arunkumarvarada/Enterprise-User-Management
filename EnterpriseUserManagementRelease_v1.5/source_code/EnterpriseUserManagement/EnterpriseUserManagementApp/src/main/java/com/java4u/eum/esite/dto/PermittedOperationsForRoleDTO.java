package com.altiux.eum.esite.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PermittedOperationsForRoleDTO {
	private @JsonProperty("operatorRole") List<OperationsDTO> operatorRole;
	private @JsonProperty("managerRole") List<OperationsDTO> managerRole;
	private @JsonProperty("adminRole") List<OperationsDTO> adminRole;
	private @JsonProperty("allRoles") List<OperationsDTO> allRoles;

	public PermittedOperationsForRoleDTO() {
		super();
	}

	public PermittedOperationsForRoleDTO(List<OperationsDTO> operatorRole, List<OperationsDTO> managerRole,
			List<OperationsDTO> adminRole, List<OperationsDTO> allRoles) {
		super();
		this.operatorRole = operatorRole;
		this.managerRole = managerRole;
		this.adminRole = adminRole;
		this.allRoles = allRoles;
	}

	public List<OperationsDTO> getOperatorRole() {
		return operatorRole;
	}

	public void setOperatorRole(List<OperationsDTO> operatorRole) {
		this.operatorRole = operatorRole;
	}

	public List<OperationsDTO> getManagerRole() {
		return managerRole;
	}

	public void setManagerRole(List<OperationsDTO> managerRole) {
		this.managerRole = managerRole;
	}

	public List<OperationsDTO> getAdminRole() {
		return adminRole;
	}

	public void setAdminRole(List<OperationsDTO> adminRole) {
		this.adminRole = adminRole;
	}

	public List<OperationsDTO> getAllRoles() {
		return allRoles;
	}

	public void setAllRoles(List<OperationsDTO> allRoles) {
		this.allRoles = allRoles;
	}

	@Override
	public String toString() {
		return "PermittedOperationsForRoleDTO [operatorRole=" + operatorRole + ", managerRole=" + managerRole
				+ ", adminRole=" + adminRole + ", allRoles=" + allRoles + "]";
	}

}
