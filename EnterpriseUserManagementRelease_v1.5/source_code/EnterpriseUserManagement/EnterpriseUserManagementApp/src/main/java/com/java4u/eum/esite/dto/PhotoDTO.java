package com.altiux.eum.esite.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PhotoDTO {

	private @JsonProperty("photoUrl") String photoUrl;
	private @JsonProperty("photoType") String photoType;

	public PhotoDTO() {
		super();
	}

	public PhotoDTO(String photoUrl, String photoType) {
		super();
		this.photoUrl = photoUrl;
		this.photoType = photoType;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getPhotoType() {
		return photoType;
	}

	public void setPhotoType(String photoType) {
		this.photoType = photoType;
	}

	@Override
	public String toString() {
		return "PhotoDTO [photoUrl=" + photoUrl + ", photoType=" + photoType + "]";
	}

}
