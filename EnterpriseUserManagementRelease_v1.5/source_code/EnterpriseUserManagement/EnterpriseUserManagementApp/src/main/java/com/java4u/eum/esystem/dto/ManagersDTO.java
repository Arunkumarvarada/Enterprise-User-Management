package com.java4u.eum.esystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class ManagersDTO {

	private @JsonProperty("userId") String userId;
	private @JsonProperty("username") String username;
	private @JsonProperty("firstName") String firstName;
	private @JsonProperty("lastName") String lastName;

	public ManagersDTO(String userId, String username, String firstName, String lastName) {
		super();
		this.userId = userId;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public ManagersDTO() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "ManagersDTO [userId=" + userId + ", username=" + username + ", firstName=" + firstName + ", lastName="
				+ lastName + "]";
	}

}
