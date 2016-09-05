package com.altiux.eum.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserAdditionResponseDTO {


	private @JsonProperty("userId") String userId;

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

	private @JsonProperty("created") String created;

	private @JsonProperty("lastModifiedTime") String lastModifiedTime;

	public UserAdditionResponseDTO() {
		super();
	}

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

	public void setEmails(List<EmailDTO> list) {
		this.emails = list;
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

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public UserAdditionResponseDTO(String userId, String userName, String password, String familyName,
			String middleName, String givenName, String displayName, String nickName, String honorificPrifix,
			String role, boolean isActive, String formattedName, List<EmailDTO> emails,
			List<PhoneNumberDTO> phoneNumbers, List<PhotoDTO> pics, String created, String lastModifiedTime) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.familyName = familyName;
		this.middleName = middleName;
		this.givenName = givenName;
		this.displayName = displayName;
		this.nickName = nickName;
		this.honorificPrifix = honorificPrifix;
		this.role = role;
		this.isActive = isActive;
		this.formattedName = formattedName;
		this.emails = emails;
		this.phoneNumbers = phoneNumbers;
		this.pics = pics;
		this.created = created;
		this.lastModifiedTime = lastModifiedTime;
	}

	@Override
	public String toString() {
		return "UserAdditionResponseDTO [userId=" + userId + ", userName=" + userName + ", password=" + password
				+ ", familyName=" + familyName + ", middleName=" + middleName + ", givenName=" + givenName
				+ ", displayName=" + displayName + ", nickName=" + nickName + ", honorificPrifix=" + honorificPrifix
				+ ", role=" + role + ", isActive=" + isActive + ", formattedName=" + formattedName + ", emails="
				+ emails + ", phoneNumbers=" + phoneNumbers + ", pics=" + pics + ", created=" + created
				+ ", lastModifiedTime=" + lastModifiedTime + "]";
	}

}
