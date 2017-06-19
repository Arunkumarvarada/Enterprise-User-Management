package com.java4u.eum.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PhoneNumberDTO {

	private @JsonProperty("phoneNumber") String phoneNumber;
	private @JsonProperty("type") String type;

	public PhoneNumberDTO() {
		super();
	}

	public PhoneNumberDTO(String phoneNumber, String type) {
		super();
		this.phoneNumber = phoneNumber;
		this.type = type;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "PhoneNumberDTO [phoneNumber=" + phoneNumber + ", type=" + type + "]";
	}

}
