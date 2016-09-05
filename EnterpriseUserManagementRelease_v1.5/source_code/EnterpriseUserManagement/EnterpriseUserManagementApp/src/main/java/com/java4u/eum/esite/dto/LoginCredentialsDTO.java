package com.altiux.eum.esite.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author Renukaradhya.hd
 *
 */
@ApiModel(value = "A Login Credentials", description = "A Login Credentials", discriminator = "")
@JsonInclude(Include.NON_NULL)
public class LoginCredentialsDTO {

	@ApiModelProperty(value = "Enterprise Site ID", notes = "enterpriseSiteId", required = true, dataType = "String")
	private String enterpriseSiteId;
	@ApiModelProperty(value = "userName of the person", notes = "userName", required = true, dataType = "String")
	private String userName;
	@ApiModelProperty(value = "password of the person", notes = "password", required = true)
	private String password;

	public String getEnterpriseSiteId() {
		return enterpriseSiteId;
	}

	public void setEnterpriseSiteId(String enterpriseSiteId) {
		this.enterpriseSiteId = enterpriseSiteId;
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

	@Override
	public String toString() {
		return "LoginCredentialsDTO [enterpriseSiteId=" + enterpriseSiteId + ", userName=" + userName + ", password="
				+ password + "]";
	}

	public LoginCredentialsDTO(String enterpriseSiteId, String userName, String password) {
		super();
		this.enterpriseSiteId = enterpriseSiteId;
		this.userName = userName;
		this.password = password;
	}

	public LoginCredentialsDTO() {
		super();
	}

}
