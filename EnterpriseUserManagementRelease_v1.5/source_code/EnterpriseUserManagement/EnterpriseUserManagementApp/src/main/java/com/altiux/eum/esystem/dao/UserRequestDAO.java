package com.altiux.eum.esystem.dao;

import java.util.List;

import com.altiux.commons.exceptions.InvalidInputException;
import com.altiux.eum.esystem.dto.UserRequestDetailsDTO;

public interface UserRequestDAO {

	public boolean rejectUserRequest(String enterpriseSystemId, String requestId) throws InvalidInputException;

	public List<UserRequestDetailsDTO> getPendingRequests(String enterpriseSystemId) throws InvalidInputException;

	public String addUserRequest(String enterpriseSystemId,String enterpriseSiteId) throws InvalidInputException;
	
	
}