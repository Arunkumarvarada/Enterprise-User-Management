/**
 * 
 */
package com.altiux.eum.dto;

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

	private @JsonProperty("psId") String psId;
	
	private @JsonProperty("pcId") String pcId;
	
	private @JsonProperty("userId") String userId;
		
	private @JsonProperty("code") Integer code;
	
	private @JsonProperty("msg") String msg;
	
	private @JsonProperty("responseCode") HttpStatus responseCode;

	/**
	 * 
	 */
	public LoginControllerResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param psId
	 * @param pcId
	 * @param userId
	 * @param code
	 * @param msg
	 * @param responseCode
	 */
	public LoginControllerResponseDTO(String psId, String pcId, String userId,
			Integer code, String msg, HttpStatus responseCode) {
		super();
		this.psId = psId;
		this.pcId = pcId;
		this.userId = userId;
		this.code = code;
		this.msg = msg;
		this.responseCode = responseCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoginControllerResponseDTO [psId=" + psId + ", pcId=" + pcId
				+ ", userId=" + userId + ", code=" + code + ", msg=" + msg
				+ ", responseCode=" + responseCode + "]";
	}

	/**
	 * @return the psId
	 */
	public String getPsId() {
		return psId;
	}

	/**
	 * @param psId the psId to set
	 */
	public void setPsId(String psId) {
		this.psId = psId;
	}

	/**
	 * @return the pcId
	 */
	public String getPcId() {
		return pcId;
	}

	/**
	 * @param pcId the pcId to set
	 */
	public void setPcId(String pcId) {
		this.pcId = pcId;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the responseCode
	 */
	public HttpStatus getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(HttpStatus responseCode) {
		this.responseCode = responseCode;
	}
	
	
}
