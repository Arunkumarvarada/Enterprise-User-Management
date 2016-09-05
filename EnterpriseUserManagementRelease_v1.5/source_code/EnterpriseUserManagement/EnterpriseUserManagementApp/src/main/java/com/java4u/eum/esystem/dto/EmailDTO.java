package com.altiux.eum.esystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmailDTO {

	private @JsonProperty("email") String email;
	private @JsonProperty("type") String type;
	private @JsonProperty("isPrimary") boolean isPrimary;

	public EmailDTO(String email, String type, boolean isPrimary) {
		super();
		this.email = email;
		this.type = type;
		this.isPrimary = isPrimary;
	}

	public EmailDTO() {
		super();
	}

	@Override
	public String toString() {
		return "EmailDTO [email=" + email + ", type=" + type + ", isPrimary=" + isPrimary + "]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

}
