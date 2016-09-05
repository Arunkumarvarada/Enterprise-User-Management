package com.altiux.eum.esite.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author Ashwini.s
 *
 */
@ApiModel(value = "A Login Credntials success", description = "This is a test model that represents a Login credntials", discriminator = "")
@JsonInclude(Include.NON_NULL)
public class LoginSuccessDTO {

	@ApiModelProperty(value = "ID of the Service Provider", notes = "ID of the Service Provider", required = true, dataType = "String")
	private String serviceProviderId;
	@ApiModelProperty(value = "ID of the Enterprise System", notes = "ID of the Enterprise System", required = true, dataType = "String")
	private String entrpriseSystemId;
	@ApiModelProperty(value = "ID of the Enterprise Site", notes = "ID of the Enterprise Site", required = true, dataType = "String")
	private String entrpriseSiteId;
	@ApiModelProperty(value = "ID of the User", notes = "ID of the User", required = true, dataType = "String")
	private String userId;

	public LoginSuccessDTO() {

	}

	public String getServiceProviderId() {
		return serviceProviderId;
	}

	public void setServiceProviderId(String serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public LoginSuccessDTO(String serviceProviderId, String entrpriseSystemId, String entrpriseSiteId, String userId) {
		super();
		this.serviceProviderId = serviceProviderId;
		this.entrpriseSystemId = entrpriseSystemId;
		this.entrpriseSiteId = entrpriseSiteId;
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "LoginSuccessDTO [serviceProviderId=" + serviceProviderId + ", entrpriseSystemId=" + entrpriseSystemId
				+ ", entrpriseSiteId=" + entrpriseSiteId + ", userId=" + userId + "]";
	}

	public String getEntrpriseSystemId() {
		return entrpriseSystemId;
	}

	public void setEntrpriseSystemId(String entrpriseSystemId) {
		this.entrpriseSystemId = entrpriseSystemId;
	}

	public String getEntrpriseSiteId() {
		return entrpriseSiteId;
	}

	public void setEntrpriseSiteId(String entrpriseSiteId) {
		this.entrpriseSiteId = entrpriseSiteId;
	}

}
