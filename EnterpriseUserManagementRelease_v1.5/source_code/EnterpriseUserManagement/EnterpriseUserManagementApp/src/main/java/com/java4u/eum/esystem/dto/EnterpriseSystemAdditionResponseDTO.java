package com.java4u.eum.esystem.dto;

import com.java4u.eum.dto.UserAdditionResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EnterpriseSystemAdditionResponseDTO {

	private @JsonProperty("enterpriseSystemId") String enterpriseSystemId;

	private @JsonProperty("enterpriseSystemName") String enterpriseSystemName;

	private @JsonProperty("user") UserAdditionResponseDTO user;

	private @JsonProperty("address") AddressDTO address;

	private @JsonProperty("logoUrl") String logoUrl;

	public String getEnterpriseSystemId() {
		return enterpriseSystemId;
	}

	public void setEnterpriseSystemId(String enterpriseSystemId) {
		this.enterpriseSystemId = enterpriseSystemId;
	}

	public String getEnterpriseSystemName() {
		return enterpriseSystemName;
	}

	public void setEnterpriseSystemName(String enterpriseSystemName) {
		this.enterpriseSystemName = enterpriseSystemName;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public EnterpriseSystemAdditionResponseDTO() {
		super();
	}

	@Override
	public String toString() {
		return "EnterpriseSystemAdditionResponseDTO [enterpriseSystemId=" + enterpriseSystemId
				+ ", enterpriseSystemName=" + enterpriseSystemName + ", user=" + user + ", address=" + address
				+ ", logoUrl=" + logoUrl + "]";
	}

	public UserAdditionResponseDTO getUser() {
		return user;
	}

	public void setUser(UserAdditionResponseDTO user) {
		this.user = user;
	}
}
