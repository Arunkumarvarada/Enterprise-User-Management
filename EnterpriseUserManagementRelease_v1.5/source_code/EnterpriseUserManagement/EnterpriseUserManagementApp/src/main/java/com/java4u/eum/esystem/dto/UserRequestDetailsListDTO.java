package com.java4u.eum.esystem.dto;

import java.util.ArrayList;
import java.util.List;

public class UserRequestDetailsListDTO {

	private List<UserRequestDetailsDTO> requests = new ArrayList<UserRequestDetailsDTO>();

	public UserRequestDetailsListDTO() {
		super();
	}

	public UserRequestDetailsListDTO(List<UserRequestDetailsDTO> requests) {
		super();
		this.requests = requests;
	}

	public List<UserRequestDetailsDTO> getRequests() {
		return requests;
	}

	public void setRequests(List<UserRequestDetailsDTO> requests) {
		this.requests = requests;
	}
	
}