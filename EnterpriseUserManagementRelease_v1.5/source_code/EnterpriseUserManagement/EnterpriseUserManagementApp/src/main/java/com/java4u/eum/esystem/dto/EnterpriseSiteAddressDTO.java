package com.altiux.eum.esystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EnterpriseSiteAddressDTO {
	private @JsonProperty("streetAddress") String streetAddress;

	private @JsonProperty("locality") String locality;

	private @JsonProperty("postalCode") String postalCode;

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public EnterpriseSiteAddressDTO(String streetAddress, String locality, String postalCode) {
		super();
		this.streetAddress = streetAddress;
		this.locality = locality;
		this.postalCode = postalCode;
	}

	public EnterpriseSiteAddressDTO() {
		super();
	}

}
