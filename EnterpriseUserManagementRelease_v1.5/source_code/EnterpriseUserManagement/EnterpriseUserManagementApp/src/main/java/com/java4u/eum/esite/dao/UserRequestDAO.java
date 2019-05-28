package com.java4u.eum.esite.dao;

import java.util.List;

import com.java4u.commons.exceptions.InvalidInputException;
import com.java4u.eum.esite.dto.UserRequestDTO;
import com.java4u.eum.esite.dto.UserRequestDetailsDTO;

public interface UserRequestDAO {

	String addUserRequest(UserRequestDTO requestDTO, String enterpriseSiteId) throws InvalidInputException;
	
	boolean deleteUserRequest(String enterpriseSiteId, String requestId) throws InvalidInputException;
	
	List<UserRequestDetailsDTO> getPendingRequests(String enterpriseSiteId, String type) throws InvalidInputException;

}
