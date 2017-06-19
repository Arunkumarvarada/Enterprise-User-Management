package com.java4u.eum.esystem.dao;


import com.java4u.commons.exceptions.InvalidInputException;
import com.java4u.commons.exceptions.UserAlreadyAvailableException;
import com.java4u.commons.exceptions.UserNotFoundException;
import com.java4u.eum.esystem.dto.AllAsstManagersResponseDTO;
import com.java4u.eum.esystem.dto.AllManagersResponseDTO;
import com.java4u.eum.esystem.dto.AllEnterpriseSiteUsersDTO;
import com.java4u.eum.esystem.dto.AllUsersResponseDTO;
import com.java4u.eum.esystem.dto.EnterpriseSitesListDTO;
import com.java4u.eum.esystem.dto.EnterpriseSitesListResponseDTO;
import com.java4u.eum.esystem.dto.UserAdditionRequestDTO;
import com.java4u.eum.esystem.dto.UserAdditionResponseDTO;
import com.java4u.eum.esystem.dto.UserAssigmentRequestDTO;
import com.java4u.eum.esystem.dto.UserAssigmentResponseDTO;
import com.java4u.eum.esystem.dto.UserDeletionRequestDTO;
import com.java4u.eum.esystem.dto.UserDeletionResponseDTO;

public interface UserManagementDAO {

	public void insertRolesandOperations();

	public UserDeletionResponseDTO deleteUser(UserDeletionRequestDTO dto) throws UserNotFoundException, InvalidInputException;

	public UserDeletionResponseDTO deleteUserWithRequestId(UserDeletionRequestDTO dto, String userRequestId) throws UserNotFoundException;
	
	public AllUsersResponseDTO getAllEnterpriseSystemLEOs(String enterpriseSystemId, Integer offset, Integer limit) throws InvalidInputException;

	public AllAsstManagersResponseDTO getAllEnterpriseSiteAsstManagers(String enterpriseSystemId,Integer offset, Integer limit) throws InvalidInputException;
	
	public AllEnterpriseSiteUsersDTO getAllEnterpriseSiteUsers(String enterpriseSiteId, String enterpriseSystemId) throws InvalidInputException;

	public UserAssigmentResponseDTO allotEnterpriseSiteToUser(String systemId,UserAssigmentRequestDTO dto) throws InvalidInputException;

	public EnterpriseSitesListResponseDTO getListofEnterpriseSites(String enterpriseSystemId) throws InvalidInputException;

	public UserAdditionResponseDTO createUserWithUserRequest(UserAdditionRequestDTO requestDTO, String enterpriseSystemId,
			String enterpriseSiteId,String userRequestId, String adminId) throws UserAlreadyAvailableException, InvalidInputException;
	
	public EnterpriseSitesListDTO getEnterpriseSitesAssignedtoUserId(String enterpriseSystemId,String userId) throws InvalidInputException, UserNotFoundException;

	public UserAdditionResponseDTO createUser(UserAdditionRequestDTO requestDTO, String enterpriseSystemId, String adminId)
			throws UserAlreadyAvailableException, InvalidInputException;

	public AllUsersResponseDTO getAllEnterpriseSystemUsers(String enterpriseSystemId, Integer offset, Integer limit)
			throws InvalidInputException;

	public AllManagersResponseDTO getAllEnterpriseSystemManagers(String enterpriseSystemId, Integer offset, Integer limit)
			throws InvalidInputException;
}
