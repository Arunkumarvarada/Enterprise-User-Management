package com.java4u.eum.esystem.dao;

import java.util.List;

import com.java4u.commons.exceptions.InvalidInputException;
import com.java4u.eum.esystem.dto.UserRequestDetailsDTO;

public interface UserRequestDAO {

	public boolean rejectUserRequest(String enterpriseSystemId, String requestId) throws InvalidInputException;

	public List<UserRequestDetailsDTO> getPendingRequests(String enterpriseSystemId) throws InvalidInputException;

	public String addUserRequest(String enterpriseSystemId,String enterpriseSiteId) throws InvalidInputException;
	
	
}