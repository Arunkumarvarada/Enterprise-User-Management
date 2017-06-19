/**
 * 
 */
package com.java4u.eum.esystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Ankit.A
 *
 */
@JsonInclude(Include.NON_NULL)
public class LoginControllerRequestDTO {

	private @JsonProperty("enterpriseSystemId") String enterpriseSystemId;

	private @JsonProperty("userName") String userName;

	private @JsonProperty("password") String password;

	/**
	 * 
	 */
	public LoginControllerRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param userName
	 * @param password
	 */
	public LoginControllerRequestDTO(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoginControllerRequestDTO [userName=" + userName + ", password=" + password + "]";
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEnterpriseSystemId() {
		return enterpriseSystemId;
	}

	public void setEnterpriseSystemId(String enterpriseSystemId) {
		this.enterpriseSystemId = enterpriseSystemId;
	}

}
