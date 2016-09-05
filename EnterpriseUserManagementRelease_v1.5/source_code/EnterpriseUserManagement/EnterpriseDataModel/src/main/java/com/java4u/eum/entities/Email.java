package com.altiux.eum.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class Email implements Serializable {

	@Column(name = "email")
	private String email;

	@Column(name = "type")
	private String type;

	@Column(name = "isPrimary")
	private boolean isPrimary;

	public Email() {
	}

	public Email(String emailId, String type, boolean isPrimary) {
		this.email = emailId;
		this.type = type;
		this.isPrimary = isPrimary;
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
