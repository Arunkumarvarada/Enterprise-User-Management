package com.altiux.eum.esystem.dao;


import com.altiux.commons.exceptions.InvalidInputException;
import com.altiux.commons.exceptions.UserAlreadyAvailableException;
import com.altiux.commons.exceptions.UserNotFoundException;
import com.altiux.eum.esystem.dto.AllAsstManagersResponseDTO;
import com.altiux.eum.esystem.dto.AllManagersResponseDTO;
import com.altiux.eum.esystem.dto.AllEnterpriseSiteUsersDTO;
import com.altiux.eum.esystem.dto.AllUsersResponseDTO;
import com.altiux.eum.esystem.dto.EnterpriseSitesListDTO;
import com.altiux.eum.esystem.dto.EnterpriseSitesListResponseDTO;
import com.altiux.eum.esystem.dto.UserAdditionRequestDTO;
import com.altiux.eum.esystem.dto.UserAdditionResponseDTO;
import com.altiux.eum.esystem.dto.UserAssigmentRequestDTO;
import com.altiux.eum.esystem.dto.UserAssigmentResponseDTO;
import com.altiux.eum.esystem.dto.UserDeletionRequestDTO;
import com.altiux.eum.esystem.dto.UserDeletionResponseDTO;

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
