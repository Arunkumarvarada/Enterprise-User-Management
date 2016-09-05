package com.altiux.eum.esite.dao;

import java.util.List;

import com.altiux.commons.exceptions.InvalidInputException;
import com.altiux.commons.exceptions.UserAlreadyAvailableException;
import com.altiux.commons.exceptions.UserNotFoundException;
import com.altiux.eum.esite.dto.AllUsersResponseDTO;
import com.altiux.eum.esite.dto.OperationsDTO;
import com.altiux.eum.esite.dto.UserAdditionRequestDTO;
import com.altiux.eum.esite.dto.UserAdditionResponseDTO;
import com.altiux.eum.esite.dto.UserDeletionRequestDTO;
import com.altiux.eum.esite.dto.UserDeletionResponseDTO;
import com.altiux.eum.esite.dto.UserRolesDTO;

public interface UserManagementDAO {

	public void insertRolesandOperations();

	public List<String> getAllRoles(String enterpriseSiteId);

	public List<String> getAllOperations(String enterpriseSiteId ) throws InvalidInputException;

	public UserAdditionResponseDTO createUser(UserAdditionRequestDTO requestDTO, String enterpriseSiteId)
			throws UserAlreadyAvailableException, InvalidInputException;

	public UserDeletionResponseDTO deleteUser(UserDeletionRequestDTO dto) throws UserNotFoundException, InvalidInputException;

	public List<OperationsDTO> addOrUpdatePermissions(String enterpriseId,String userId, String adminUserId,
			List<OperationsDTO> allowedPermissions) throws InvalidInputException;

	public AllUsersResponseDTO getAllUsers(String enterpriseSiteId, String adminUserId)throws InvalidInputException;

	public UserRolesDTO getRolesOfUser(String userId, String psId) throws InvalidInputException;

	public boolean deleteSiteUser(String enterpriseSiteId);

	
}
