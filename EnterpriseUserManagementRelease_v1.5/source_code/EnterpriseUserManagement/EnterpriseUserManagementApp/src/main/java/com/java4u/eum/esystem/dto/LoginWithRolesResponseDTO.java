package com.altiux.eum.esystem.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.altiux.eum.esite.dto.OperationsDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginWithRolesResponseDTO {
	private @JsonProperty("userId") String userId;

	private @JsonProperty("roleId") String roleId;

	private @JsonProperty("roleName") String roleName;

	private @JsonProperty("allowedOperations") List<OperationsDTO> allowedOperations;

	private @JsonProperty("code") Integer code;

	private @JsonProperty("msg") String msg;

	private @JsonProperty("responseCode") HttpStatus responseCode;

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

	public LoginWithRolesResponseDTO() {
		super();
	}

	public LoginWithRolesResponseDTO(String userId, String roleId, String roleName,
			List<OperationsDTO> allowedOperations, Integer code, String msg, HttpStatus responseCode) {
		super();
		this.userId = userId;
		this.roleId = roleId;
		this.roleName = roleName;
		this.allowedOperations = allowedOperations;
		this.code = code;
		this.msg = msg;
		this.responseCode = responseCode;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "LoginWithRolesResponseDTO [userId=" + userId + ", roleId=" + roleId + ", roleName=" + roleName
				+ ", allowedOperations=" + (allowedOperations != null
						? allowedOperations.subList(0, Math.min(allowedOperations.size(), maxLen)) : null)
				+ ", code=" + code + ", msg=" + msg + ", responseCode=" + responseCode + "]";
	}

}
