package com.java4u.eum.esite.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RoleListDTO {

	private List<String> roles = new ArrayList<String>();


	public RoleListDTO(List<String> roles) {
		super();
		this.roles = roles;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public RoleListDTO() {
		super();
	}

}