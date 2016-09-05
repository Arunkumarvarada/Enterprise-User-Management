package com.altiux.eum.esystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginWithRolesRequestDTO {
	private @JsonProperty("enterpriseSystemId") String enterpriseSystemId;

	private @JsonProperty("userName") String userName;

	private @JsonProperty("password") String password;

	public String getEnterpriseSystemId() {
		return enterpriseSystemId;
	}

	public void setEnterpriseSystemId(String enterpriseSystemId) {
		this.enterpriseSystemId = enterpriseSystemId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LoginWithRolesRequestDTO(String enterpriseSystemId, String userName, String password) {
		super();
		this.enterpriseSystemId = enterpriseSystemId;
		this.userName = userName;
		this.password = password;
	}

	public LoginWithRolesRequestDTO() {
		super();
	}

	@Override
	public String toString() {
		return "LoginWithRolesRequestDTO [enterpriseSystemId=" + enterpriseSystemId + ", userName=" + userName
				+ ", password=" + password + "]";
	}
	
	

}
