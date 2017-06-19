package com.java4u.eum.esystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EnterpriseSystemAdditionRequestDTO {

	private @JsonProperty("enterpriseSystemId") String enterpriseSystemId;

	private @JsonProperty("enterpriseSystemName") String enterpriseSystemName;

	private @JsonProperty("user") UserAdditionRequestDTO user;

	private @JsonProperty("address") AddressDTO address;

	public String getEnterpriseSystemId() {
		return enterpriseSystemId;
	}

	public void setEnterpriseSystemId(String enterpriseSystemId) {
		this.enterpriseSystemId = enterpriseSystemId;
	}

	private @JsonProperty("logoUrl") String logoUrl;

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

	public EnterpriseSystemAdditionRequestDTO() {
		super();
	}

	public UserAdditionRequestDTO getUser() {
		return user;
	}

	public void setUser(UserAdditionRequestDTO user) {
		this.user = user;
	}

	public EnterpriseSystemAdditionRequestDTO(String enterpriseSystemId, String enterpriseSystemName,
			UserAdditionRequestDTO user, AddressDTO address, String logoUrl) {
		super();
		this.enterpriseSystemId = enterpriseSystemId;
		this.enterpriseSystemName = enterpriseSystemName;
		this.user = user;
		this.address = address;
		this.logoUrl = logoUrl;
	}

}
