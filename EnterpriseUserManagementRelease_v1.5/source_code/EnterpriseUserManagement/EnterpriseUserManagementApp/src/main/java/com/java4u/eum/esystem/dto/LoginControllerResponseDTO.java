/**
 * 
 */
package com.java4u.eum.esystem.dto;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Ankit.A
 *
 */
@JsonInclude(Include.NON_NULL)
public class LoginControllerResponseDTO {

	private @JsonProperty("enterpriseSiteId") String enterpriseSiteId;

	private @JsonProperty("enterpriseSystemId") String enterpriseSystemId;

	private @JsonProperty("userId") String userId;

	private @JsonProperty("code") Integer code;

	private @JsonProperty("msg") String msg;

	private @JsonProperty("responseCode") HttpStatus responseCode;

	public LoginControllerResponseDTO() {
		super();
	}

	@Override
	public String toString() {
		return "LoginControllerResponseDTO [enterpriseSiteId=" + enterpriseSiteId + ", enterpriseSystemId="
				+ enterpriseSystemId + ", userId=" + userId + ", code=" + code + ", msg=" + msg + ", responseCode="
				+ responseCode + "]";
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public HttpStatus getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(HttpStatus responseCode) {
		this.responseCode = responseCode;
	}

	public String getEnterpriseSiteId() {
		return enterpriseSiteId;
	}

	public void setEnterpriseSiteId(String enterpriseSiteId) {
		this.enterpriseSiteId = enterpriseSiteId;
	}

	public String getEnterpriseSystemId() {
		return enterpriseSystemId;
	}

	public void setEnterpriseSystemId(String enterpriseSystemId) {
		this.enterpriseSystemId = enterpriseSystemId;
	}

	public LoginControllerResponseDTO(String enterpriseSiteId, String enterpriseSystemId, String userId, Integer code,
			String msg, HttpStatus responseCode) {
		super();
		this.enterpriseSiteId = enterpriseSiteId;
		this.enterpriseSystemId = enterpriseSystemId;
		this.userId = userId;
		this.code = code;
		this.msg = msg;
		this.responseCode = responseCode;
	}

}
