package com.altiux.eum.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AsstManagerDTO {
	
	private @JsonProperty("userId") String userId;

	private @JsonProperty("userName") String userName;
	
	private @JsonProperty("displayName") String displayName;
	
	private @JsonProperty("emails") List<EmailDTO> emails = new ArrayList<EmailDTO>();
	
	private @JsonProperty("phoneNumbers") List<PhoneNumberDTO> phoneNumbers = new ArrayList<PhoneNumberDTO>();

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public List<EmailDTO> getEmails() {
		return emails;
	}

	public void setEmails(List<EmailDTO> emails) {
		this.emails = emails;
	}

	public List<PhoneNumberDTO> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<PhoneNumberDTO> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public AsstManagerDTO(String userId, String userName, String displayName, List<EmailDTO> emails,
			List<PhoneNumberDTO> phoneNumbers) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.displayName = displayName;
		this.emails = emails;
		this.phoneNumbers = phoneNumbers;
	}

	public AsstManagerDTO() {
		super();
	}

	@Override
	public String toString() {
		return "AsstManagerDTO [userId=" + userId + ", userName=" + userName + ", displayName=" + displayName
				+ ", emails=" + emails + ", phoneNumbers=" + phoneNumbers + "]";
	}
	
	
}
