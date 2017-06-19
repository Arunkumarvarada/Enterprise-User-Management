package com.java4u.eum.esystem.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SiteUserAdditionRequestDTO {

	private @JsonProperty("userName") String userName;

	private @JsonProperty("password") String password;

	private @JsonProperty("familyName") String familyName;

	private @JsonProperty("middleName") String middleName;

	private @JsonProperty("givenName") String givenName;

	private @JsonProperty("displayName") String displayName;

	private @JsonProperty("nickName") String nickName;

	private @JsonProperty("honorificPrifix") String honorificPrifix;

	private @JsonProperty("role") String role;

	private @JsonProperty("isActive") boolean isActive;

	private @JsonProperty("formattedName") String formattedName;

	private @JsonProperty("emails") List<EmailDTO> emails = new ArrayList<EmailDTO>();

	private @JsonProperty("phoneNumbers") List<PhoneNumberDTO> phoneNumbers = new ArrayList<PhoneNumberDTO>();

	private @JsonProperty("pics") List<PhotoDTO> pics = new ArrayList<PhotoDTO>();

	public SiteUserAdditionRequestDTO() {
		super();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHonorificPrifix() {
		return honorificPrifix;
	}

	public void setHonorificPrifix(String honorificPrifix) {
		this.honorificPrifix = honorificPrifix;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getFormattedName() {
		return formattedName;
	}

	public void setFormattedName(String formattedName) {
		this.formattedName = formattedName;
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

	public List<PhotoDTO> getPics() {
		return pics;
	}

	public void setPics(List<PhotoDTO> pics) {
		this.pics = pics;
	}

	@Override
	public String toString() {
		return "UserAdditionRequestDTO [userName=" + userName + ", password=" + password + ", familyName=" + familyName
				+ ", middleName=" + middleName + ", givenName=" + givenName + ", displayName=" + displayName
				+ ", nickName=" + nickName + ", honorificPrifix=" + honorificPrifix + ", role=" + role + ", isActive="
				+ isActive + ", formattedName=" + formattedName + ", emails=" + emails + ", phoneNumbers="
				+ phoneNumbers + ", pics=" + pics + "]";
	}
	
}
