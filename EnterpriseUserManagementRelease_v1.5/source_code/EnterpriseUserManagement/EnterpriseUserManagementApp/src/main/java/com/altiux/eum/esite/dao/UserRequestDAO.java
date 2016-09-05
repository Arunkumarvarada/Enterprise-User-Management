package com.altiux.eum.esite.dao;

import java.util.List;

import com.altiux.commons.exceptions.InvalidInputException;
import com.altiux.eum.esite.dto.UserRequestDTO;
import com.altiux.eum.esite.dto.UserRequestDetailsDTO;



/**
 * 
 * @author Renukaradhya.hd
 *
 */
public interface UserRequestDAO {

	String addUserRequest(UserRequestDTO requestDTO, String enterpriseSiteId) throws InvalidInputException;
	
	boolean deleteUserRequest(String enterpriseSiteId, String requestId) throws InvalidInputException;
	
	List<UserRequestDetailsDTO> getPendingRequests(String enterpriseSiteId, String type) throws InvalidInputException;

}
