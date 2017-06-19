package com.java4u.eum.esystem.controller;

import javax.ws.rs.QueryParam;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.java4u.commons.errors.ErrorResponse;
import com.java4u.commons.exceptions.InvalidInputException;
import com.java4u.commons.exceptions.UserAlreadyAvailableException;
import com.java4u.commons.exceptions.UserNotFoundException;
import com.java4u.eum.esystem.dao.UserManagementDAO;
import com.java4u.eum.esystem.dao.impl.UserRegexUtil;
import com.java4u.eum.esystem.dto.AllAsstManagersResponseDTO;
import com.java4u.eum.esystem.dto.AllEnterpriseSiteUsersDTO;
import com.java4u.eum.esystem.dto.AllManagersResponseDTO;
import com.java4u.eum.esystem.dto.AllUsersResponseDTO;
import com.java4u.eum.esystem.dto.EnterpriseSitesListDTO;
import com.java4u.eum.esystem.dto.EnterpriseSitesListResponseDTO;
import com.java4u.eum.esystem.dto.UserAdditionRequestDTO;
import com.java4u.eum.esystem.dto.UserAdditionResponseDTO;
import com.java4u.eum.esystem.dto.UserAssigmentRequestDTO;
import com.java4u.eum.esystem.dto.UserAssigmentResponseDTO;
import com.java4u.eum.esystem.dto.UserDeletionRequestDTO;
import com.java4u.eum.esystem.dto.UserDeletionResponseDTO;
import com.java4u.eum.utils.EnterpriseSiteUtils;
import com.java4u.logger.App_logger;
import com.java4u.logger.EModuleName;
import com.java4u.logger.LoggerFactory;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "enterpriseSystem/{enterpriseSystemId}/usermanagement")
@Api(value = "enterpriseSystem/{enterpriseSystemId}/usermanagement", description = "APIs to do "
		+ "Enterprise System user manangement related operations", position = 16)
public class EnterpriseSystemUserManagementController {

	private static final App_logger logger = LoggerFactory.getLogger(EModuleName.CONTROLLER);

	@Autowired
	private UserManagementDAO userManagementDAOBean;

	@ApiOperation(value = "get list of users", notes = "get list of users", httpMethod = "GET")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "InternalServer error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<?> getAllEnterpriseSystemUsers(
			@ApiParam(name = "enterpriseSystemId", required = true, value = "String") @PathVariable String enterpriseSystemId,
			@QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit) {
		AllUsersResponseDTO response = null;
		logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSystemUsers()",
				"Inside the method getAllEnterpriseSystemUsers, enterprise site ID : " + enterpriseSystemId);

		logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSystemUsers()",
				"UserRegexUtil.isAlphaNumeric(enterpriseSystemId):::=== "
						+ UserRegexUtil.isAlphaNumeric(enterpriseSystemId));
		logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSystemUsers()",
				"StringUtils.isBlank(enterpriseSystemId):::=== " + StringUtils.isBlank(enterpriseSystemId));

		if (StringUtils.isBlank(enterpriseSystemId) || !UserRegexUtil.isAlphaNumeric(enterpriseSystemId)) {
			logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSystemUsers",
					"Invalid Inputs to getAllEnterpriseSystemUsers");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid Enterprise System ID");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}

		try {
			response = userManagementDAOBean.getAllEnterpriseSystemUsers(enterpriseSystemId, offset, limit);
			if (response == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No User Data Found!! ");
				logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSystemUsers",
						"Response ===== No User Data Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}
			logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSystemUsers",
					"roles available for user are returned successfully!!");
		} catch (InvalidInputException e) {
			logger.error(this.getClass().getSimpleName(), "getAllEnterpriseSystemUsers()",
					"exception while fetching all the Enterprise System users" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method exception while fetching all the Enterprise System users"
							+ e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName(), "getAllEnterpriseSystemUsers()",
					"exception while fetching Permitted Operationd for Roles" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method get roles for the User" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSystemUsers",
				"Response in getting user roles ====== Success ");
		return new ResponseEntity<AllUsersResponseDTO>(response, EnterpriseSiteUtils.getHeadersForPostAPI(),
				HttpStatus.OK);
	}

	@ApiOperation(value = "get list of managers", notes = "get list of usersbased onthe entperpriseSiteId", httpMethod = "GET")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "InternalServer error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "/managers", method = RequestMethod.GET)
	public ResponseEntity<?> getAllEnterpriseSystemManagers(
			@ApiParam(name = "enterpriseSystemId", required = true, value = "String") @PathVariable String enterpriseSystemId,
			@QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit) {
		AllManagersResponseDTO response = null;
		logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSystemManagers()",
				"Inside the method getAllEnterpriseSystemUsers, enterprise site ID : " + enterpriseSystemId);
		logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSystemManagers()",
				"UserRegexUtil.isAlphaNumeric(enterpriseSystemId):::=== "
						+ UserRegexUtil.isAlphaNumeric(enterpriseSystemId));
		logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSystemManagers()",
				"StringUtils.isBlank(enterpriseSystemId):::=== " + StringUtils.isBlank(enterpriseSystemId));

		if (StringUtils.isBlank(enterpriseSystemId) || !UserRegexUtil.isAlphaNumeric(enterpriseSystemId)) {
			logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSystemManagers",
					"Invalid Inputs to getAllEnterpriseSystemManagers");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid Enterprise System ID");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}

		try {
			response = userManagementDAOBean.getAllEnterpriseSystemManagers(enterpriseSystemId, offset, limit);
			if (response == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No User Data Found!! ");
				logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSystemManagers",
						"Response ===== No User Data Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}
			logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSystemManagers",
					"roles available for user are returned successfully!!");
		} catch (InvalidInputException e) {
			logger.error(this.getClass().getSimpleName(), "getAllEnterpriseSystemManagers()",
					"exception while fetching all the Enterprise System managers" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method exception while fetching all the Enterprise System managers"
							+ e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName(), "getAllEnterpriseSystemManagers()",
					"exception while fetching Permitted Operationd for Roles" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method get roles for the User" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSystemManagers",
				"Response in getting user roles ====== Success ");
		return new ResponseEntity<AllManagersResponseDTO>(response, EnterpriseSiteUtils.getHeadersForPostAPI(),
				HttpStatus.OK);
	}

	@ApiOperation(value = "create users for Enterprise System ", notes = "create users for Enterprise System ", httpMethod = "POST")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal Server error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "/user/admin/{adminId}", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(
			@ApiParam(name = "enterpriseSystemId", required = true, value = "String") @PathVariable String enterpriseSystemId,
			@ApiParam(name = "adminId", required = true, value = "String") @PathVariable String adminId,
			@ApiParam @RequestBody UserAdditionRequestDTO requestDTO) {
		UserAdditionResponseDTO response = null;
		logger.debug(this.getClass().getSimpleName(), "createUser()",
				"Inside the method get roles for the User, enterprise site ID : " + enterpriseSystemId
						+ " admin user ID : " + adminId);
		logger.debug(this.getClass().getSimpleName(), "createUser()",
				"StringUtils.isBlank(adminId):::=== " + StringUtils.isBlank(adminId));
		logger.debug(this.getClass().getSimpleName(), "createUser()",
				"StringUtils.isBlank(enterpriseSystemId):::=== " + StringUtils.isBlank(enterpriseSystemId));
		logger.debug(this.getClass().getSimpleName(), "createUser()",
				"(requestDTO == null):::=== " + (requestDTO == null));
		logger.debug(this.getClass().getSimpleName(), "createUser()",
				"UserRegexUtil.isAlphaNumeric(adminId):::=== " + UserRegexUtil.isAlphaNumeric(adminId));
		logger.debug(this.getClass().getSimpleName(), "createUser()",
				"StringUtils.isBlank(requestDTO.getUserName()):::=== " + StringUtils.isBlank(requestDTO.getUserName()));
		logger.debug(this.getClass().getSimpleName(), "createUser()",
				"StringUtils.isBlank(requestDTO.getPassword()):::=== " + StringUtils.isBlank(requestDTO.getPassword()));
		logger.debug(this.getClass().getSimpleName(), "createUser()",
				"UserRegexUtil.isAlphaNumeric(enterpriseSystemId):::=== "
						+ UserRegexUtil.isAlphaNumeric(enterpriseSystemId));
		if (StringUtils.isBlank(adminId) || StringUtils.isBlank(enterpriseSystemId) || requestDTO == null
				|| !UserRegexUtil.isAlphaNumeric(adminId) || !UserRegexUtil.isAlphaNumeric(enterpriseSystemId)
				|| StringUtils.isBlank(requestDTO.getUserName()) || StringUtils.isBlank(requestDTO.getPassword())) {
			logger.debug(this.getClass().getSimpleName(), "createUser", "Invalid Inputs to creat an User");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid Inputs to creat an User");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}
		try {
			response = userManagementDAOBean.createUser(requestDTO, enterpriseSystemId, adminId);
			logger.debug(this.getClass().getSimpleName(), "getUserRoles",
					"roles available for user: " + response.getUserId() + " roleID:" + response.getRole());
			if (response.getUserId() == null || response.getRole() == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No User Data Found!! ");
				logger.debug(this.getClass().getSimpleName(), "getUserRoles", "Response ===== No User Data Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}
		} catch (InvalidInputException e) {
			logger.error(this.getClass().getSimpleName(), "createUser()", " user creation " + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1, e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		} catch (UserAlreadyAvailableException e) {
			logger.error(this.getClass().getSimpleName(), "createUser()", " user creation " + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1, e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			Thread.dumpStack();
			logger.error(this.getClass().getSimpleName(), "createUser()", " user creation " + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method creation of user ::" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(this.getClass().getSimpleName(), "createUser()", "Response in creation of user ====== Success");
		return new ResponseEntity<UserAdditionResponseDTO>(response, EnterpriseSiteUtils.getHeadersForPostAPI(),
				HttpStatus.OK);
	}

	@ApiOperation(value = "create users for Enterprise System ", notes = "create users for Enterprise System ", httpMethod = "POST")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal Server error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "/entperpriseSite/{entperpriseSiteId}/user/admin/{adminId}/request/{urId}", method = RequestMethod.POST)
	public ResponseEntity<?> createUserWithUserRequest(
			@ApiParam(name = "enterpriseSystemId", required = true, value = "String") @PathVariable String enterpriseSystemId,
			@ApiParam(name = "adminId", required = true, value = "String") @PathVariable String adminId,
			@ApiParam(name = "entperpriseSiteId", required = true, value = "String") @PathVariable String entperpriseSiteId,
			@ApiParam(name = "urId", required = true, value = "String") @PathVariable String urId,
			@ApiParam @RequestBody UserAdditionRequestDTO requestDTO) {
		UserAdditionResponseDTO response = null;

		logger.debug(this.getClass().getSimpleName(), "createUserWithUserRequest()",
				"Inside the method get roles for the User, Enterprise System ID : " + enterpriseSystemId
						+ " admin user ID : " + adminId);
		logger.debug(this.getClass().getSimpleName(), "createUserWithUserRequest()",
				"StringUtils.isBlank(adminId):::=== " + StringUtils.isBlank(adminId));
		logger.debug(this.getClass().getSimpleName(), "createUserWithUserRequest()",
				"StringUtils.isBlank(enterpriseSystemId):::=== " + StringUtils.isBlank(enterpriseSystemId));
		logger.debug(this.getClass().getSimpleName(), "createUserWithUserRequest()",
				"(requestDTO == null):::=== " + (requestDTO == null));
		logger.debug(this.getClass().getSimpleName(), "createUserWithUserRequest()",
				"UserRegexUtil.isAlphaNumeric(adminId):::=== " + UserRegexUtil.isAlphaNumeric(adminId));
		logger.debug(this.getClass().getSimpleName(), "createUserWithUserRequest()",
				"StringUtils.isBlank(requestDTO.getUserName()):::=== " + StringUtils.isBlank(requestDTO.getUserName()));
		logger.debug(this.getClass().getSimpleName(), "createUserWithUserRequest()",
				"StringUtils.isBlank(requestDTO.getPassword()):::=== " + StringUtils.isBlank(requestDTO.getPassword()));
		logger.debug(this.getClass().getSimpleName(), "createUserWithUserRequest()",
				"UserRegexUtil.isAlphaNumeric(enterpriseSystemId):::=== "
						+ UserRegexUtil.isAlphaNumeric(enterpriseSystemId));
		logger.debug(this.getClass().getSimpleName(), "createUserWithUserRequest()",
				"UserRegexUtil.isAlphaNumeric(entperpriseSiteId):::=== "
						+ UserRegexUtil.isAlphaNumeric(entperpriseSiteId));
		logger.debug(this.getClass().getSimpleName(), "createUserWithUserRequest()",
				"StringUtils.isBlank(entperpriseSiteId):::=== " + StringUtils.isBlank(entperpriseSiteId));
		logger.debug(this.getClass().getSimpleName(), "createUserWithUserRequest()",
				"UserRegexUtil.isAlphaNumeric(urId):::=== " + UserRegexUtil.isAlphaNumeric(urId));
		logger.debug(this.getClass().getSimpleName(), "createUserWithUserRequest()",
				"StringUtils.isBlank(urId):::=== " + StringUtils.isBlank(urId));

		if (StringUtils.isBlank(adminId) || StringUtils.isBlank(enterpriseSystemId) || requestDTO == null
				|| !UserRegexUtil.isAlphaNumeric(adminId) || !UserRegexUtil.isAlphaNumeric(enterpriseSystemId)
				|| StringUtils.isBlank(requestDTO.getUserName()) || StringUtils.isBlank(requestDTO.getPassword())
				|| StringUtils.isBlank(urId) || StringUtils.isBlank(entperpriseSiteId)
				|| !UserRegexUtil.isAlphaNumeric(urId) || !UserRegexUtil.isAlphaNumeric(entperpriseSiteId)) {
			logger.debug(this.getClass().getSimpleName(), "getUserRoles", "Invalid Inputs to creat an User");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid userID or enterprise site ID");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}
		try {
			response = userManagementDAOBean.createUserWithUserRequest(requestDTO, enterpriseSystemId,
					entperpriseSiteId, urId, adminId);
			logger.debug(this.getClass().getSimpleName(), "createUserWithUserRequest",
					"roles available for user: " + response.getUserId() + " roleID:" + response.getRole());
			if (response.getUserId() == null || response.getRole() == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No User Data Found!! ");
				logger.debug(this.getClass().getSimpleName(), "getUserRoles", "Response ===== No User Data Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}
		} catch (InvalidInputException e) {
			logger.error(this.getClass().getSimpleName(), "createUserWithUserRequest()",
					" user creation " + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1, e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		} catch (UserAlreadyAvailableException e) {
			logger.error(this.getClass().getSimpleName(), "createUserWithUserRequest()",
					"user creation" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1, e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			Thread.dumpStack();
			logger.error(this.getClass().getSimpleName(), "createUserWithUserRequest()",
					"user creation" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method creation of user ::" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(this.getClass().getSimpleName(), "createUserWithUserRequest",
				"Response in user creation ====== Success");
		return new ResponseEntity<UserAdditionResponseDTO>(response, EnterpriseSiteUtils.getHeadersForPostAPI(),
				HttpStatus.OK);
	}

	@ApiOperation(value = "delete user for Enterprise System ", notes = "delete user for Enterprise System", httpMethod = "DELETE")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal Server error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "/user/{userId}/admin/{adminId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(
			@ApiParam(name = "enterpriseSystemId", required = true, value = "String") @PathVariable String enterpriseSystemId,
			@ApiParam(name = "adminId", required = true, value = "String") @PathVariable String adminId,
			@ApiParam(name = "userId", required = true, value = "String") @PathVariable String userId) {
		UserDeletionResponseDTO response = null;
		logger.debug(this.getClass().getSimpleName(), "deleteUser()",
				"Inside the method get roles for the User, enterprise site ID:" + enterpriseSystemId
						+ " admin user ID : " + adminId);

		logger.debug(this.getClass().getSimpleName(), "deleteUser()",
				"UserRegexUtil.isAlphaNumeric(adminId):::=== " + UserRegexUtil.isAlphaNumeric(adminId));
		logger.debug(this.getClass().getSimpleName(), "deleteUser()",
				"StringUtils.isBlank(adminId):::=== " + StringUtils.isBlank(adminId));
		logger.debug(this.getClass().getSimpleName(), "deleteUser()",
				"UserRegexUtil.isAlphaNumeric(enterpriseSystemId):::=== "
						+ UserRegexUtil.isAlphaNumeric(enterpriseSystemId));
		logger.debug(this.getClass().getSimpleName(), "deleteUser()",
				"StringUtils.isBlank(enterpriseSystemId):::=== " + StringUtils.isBlank(enterpriseSystemId));
		logger.debug(this.getClass().getSimpleName(), "deleteUser()",
				"UserRegexUtil.isAlphaNumeric(userId):::=== " + UserRegexUtil.isAlphaNumeric(userId));
		logger.debug(this.getClass().getSimpleName(), "deleteUser()",
				"StringUtils.isBlank(userId):::=== " + StringUtils.isBlank(userId));

		if (StringUtils.isBlank(adminId) || StringUtils.isBlank(enterpriseSystemId)
				|| StringUtils.isBlank(enterpriseSystemId) || !UserRegexUtil.isAlphaNumeric(userId)
				|| !UserRegexUtil.isAlphaNumeric(adminId) || !UserRegexUtil.isAlphaNumeric(enterpriseSystemId)) {
			logger.debug(this.getClass().getSimpleName(), "getUserRoles", "Invalid Inputs for deleting the User");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid userID or enterprise site ID");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}
		try {
			UserDeletionRequestDTO dto = new UserDeletionRequestDTO();
			dto.setAdminUserId(adminId);
			dto.setEnterpriseSystemId(enterpriseSystemId);
			dto.setUserId(userId);
			response = userManagementDAOBean.deleteUser(dto);
			logger.debug(this.getClass().getSimpleName(), "getUserRoles",
					"delete the user: " + response.getUserId() + "response: " + response.getResponse());
			if (response.getUserId() == null || response.getResponse() == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No User Data Found!! ");
				logger.debug(this.getClass().getSimpleName(), "getUserRoles", "Response ===== No User Data Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}

		} catch (InvalidInputException e) {
			logger.error(this.getClass().getSimpleName(), "deleteUser()", "delete the User  :::" + "User ID Not Found");
			ErrorResponse errResponse = new ErrorResponse(1, "Invalid Inputs are provided");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		} catch (UserNotFoundException e) {
			logger.error(this.getClass().getSimpleName(), "deleteUser()", "delete the User  :::" + "User ID Not Found");
			ErrorResponse errResponse = new ErrorResponse(1, "User ID Not Found");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName(), "deleteUser()", "delete the User :::" + "User ID Not Found");
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method delete existing User  :::" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(this.getClass().getSimpleName(), "getUserRoles", "Response in getting user roles ====== Success ");
		return new ResponseEntity<UserDeletionResponseDTO>(response, EnterpriseSiteUtils.getHeadersForPostAPI(),
				HttpStatus.NO_CONTENT);

	}

	@ApiOperation(value = "get list of law enforcement officers", notes = "get list of law enforcement officers", httpMethod = "GET")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "InternalServer error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "/leos", method = RequestMethod.GET)
	public ResponseEntity<?> getAllLawEnforcementOfficers(
			@ApiParam(name = "enterpriseSystemId", required = true, value = "String") @PathVariable String enterpriseSystemId,
			@QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit) {
		AllUsersResponseDTO response = null;
		logger.debug(this.getClass().getSimpleName(), "getAllLawEnforcementOfficers()",
				"Inside the method getAllLawEnforcementOfficers, Enterprise System ID : " + enterpriseSystemId);

		logger.debug(this.getClass().getSimpleName(), "getAllLawEnforcementOfficers()",
				"UserRegexUtil.isAlphaNumeric(enterpriseSystemId):::=== "
						+ UserRegexUtil.isAlphaNumeric(enterpriseSystemId));
		logger.debug(this.getClass().getSimpleName(), "getAllLawEnforcementOfficers()",
				"StringUtils.isBlank(enterpriseSystemId):::=== " + StringUtils.isBlank(enterpriseSystemId));

		if (StringUtils.isBlank(enterpriseSystemId) || !UserRegexUtil.isAlphaNumeric(enterpriseSystemId)) {
			logger.debug(this.getClass().getSimpleName(), "getUserRoles",
					"Invalid Inputs to getAllEnterpriseSystemUsers");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid Enterprise System ID");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}

		try {
			response = userManagementDAOBean.getAllEnterpriseSystemLEOs(enterpriseSystemId, offset, limit);
			if (response == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No User Data Found!! ");
				logger.debug(this.getClass().getSimpleName(), "getAllLawEnforcementOfficers",
						"Response ===== No User Data Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}
			logger.debug(this.getClass().getSimpleName(), "getAllLawEnforcementOfficers",
					"getting all LEOs are returned successfully!!");
		} catch (InvalidInputException e) {
			logger.error(this.getClass().getSimpleName(), "getAllLawEnforcementOfficers()",
					"delete the User  :::" + "User ID Not Found");
			ErrorResponse errResponse = new ErrorResponse(1, "Invalid Inputs are provided");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName(), "getAllLawEnforcementOfficers()",
					"exception while getting all LEOs" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- getting all LEOs" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(this.getClass().getSimpleName(), "getAllLawEnforcementOfficers",
				"Response in getting all LEOs ====== Success ");
		return new ResponseEntity<AllUsersResponseDTO>(response, EnterpriseSiteUtils.getHeadersForPostAPI(),
				HttpStatus.OK);

	}

	@ApiOperation(value = "get list of enterprise sites users based on enterprise sites id", notes = "get list of enterprise sites users based on enterprise sites id", httpMethod = "GET")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "InternalServer error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "/users/{eSiteId}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllEnterpriseSiteUsersBasedonEnterpriseSite(
			@ApiParam(name = "enterpriseSystemId", required = true, value = "String") @PathVariable String enterpriseSystemId,
			@ApiParam(name = "eSiteId", required = true, value = "String") @PathVariable String eSiteId,
			@QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit) {
		AllEnterpriseSiteUsersDTO response = null;
		logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSiteUsersBasedonEnterpriseSite()",
				"Inside the method getAllEnterpriseSystemUsers, enterprise site ID : " + enterpriseSystemId);

		logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSiteUsersBasedonEnterpriseSite()",
				"UserRegexUtil.isAlphaNumeric(enterpriseSystemId):::=== "
						+ UserRegexUtil.isAlphaNumeric(enterpriseSystemId));
		logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSiteUsersBasedonEnterpriseSite()",
				"StringUtils.isBlank(enterpriseSystemId):::=== " + StringUtils.isBlank(enterpriseSystemId));

		logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSiteUsersBasedonEnterpriseSite()",
				"UserRegexUtil.isAlphaNumeric(psId):::=== " + UserRegexUtil.isAlphaNumeric(eSiteId));
		logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSiteUsersBasedonEnterpriseSite()",
				"StringUtils.isBlank(psId):::=== " + StringUtils.isBlank(eSiteId));

		if (StringUtils.isBlank(enterpriseSystemId) || StringUtils.isBlank(eSiteId)
				|| !UserRegexUtil.isAlphaNumeric(eSiteId) || !UserRegexUtil.isAlphaNumeric(enterpriseSystemId)) {
			logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSiteUsersBasedonEnterpriseSite()",
					"Invalid Inputs to getAllEnterpriseSystemUsers");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid Enterprise System ID");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}

		try {
			response = userManagementDAOBean.getAllEnterpriseSiteUsers(eSiteId, enterpriseSystemId);
			if (response == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No User Data Found!! ");
				logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSiteUsersBasedonEnterpriseSite",
						"Response ===== No User Data Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}
			logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSiteUsersBasedonEnterpriseSite()",
					"roles available for user are returned successfully!!");
		} catch (InvalidInputException e) {
			logger.error(this.getClass().getSimpleName(), "getAllLawEnforcementOfficers()",
					"delete the User  :::" + "User ID Not Found");
			ErrorResponse errResponse = new ErrorResponse(1, "Invalid Inputs are provided");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName(), "getAllEnterpriseSiteUsersBasedonEnterpriseSite()",
					"exception while fetching Permitted Operationd for Roles" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method get roles for the User" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSiteUsersBasedonEnterpriseSite()",
				"Response in getting user roles ====== Success ");
		return new ResponseEntity<AllEnterpriseSiteUsersDTO>(response, EnterpriseSiteUtils.getHeadersForPostAPI(),
				HttpStatus.OK);
	}

	@ApiOperation(value = "assign or deassign enterprise site to user", notes = "assign or deassign enterprise site to user ", httpMethod = "POST")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal Server error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "/allocate", method = RequestMethod.POST)
	public ResponseEntity<?> allocateEnterpriseSiteToUser(
			@ApiParam(name = "enterpriseSystemId", required = true, value = "String") @PathVariable String enterpriseSystemId,
			@ApiParam @RequestBody UserAssigmentRequestDTO dto) {
		UserAssigmentResponseDTO response = null;
		logger.debug(this.getClass().getSimpleName(), "allocateEnterpriseSiteToUser()",
				"Inside the method allocateEnterpriseSiteToUser, enterprise site ID : " + dto.getEnterpriseSiteId());

		logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSiteUsersBasedonEnterpriseSite()",
				"UserRegexUtil.isAlphaNumeric(entperpriseSiteId):::=== "
						+ UserRegexUtil.isAlphaNumeric(dto.getEnterpriseSiteId()));
		if (StringUtils.isBlank(dto.getEnterpriseSiteId()) || StringUtils.isBlank(dto.getUserId())
				|| StringUtils.isBlank(enterpriseSystemId) || StringUtils.isBlank(dto.getOperationType())
				|| !UserRegexUtil.isAlphaNumeric(enterpriseSystemId)
				|| !UserRegexUtil.isAlphaNumeric(dto.getEnterpriseSiteId())
				|| !UserRegexUtil.isAlphaNumeric(dto.getUserId())) {
			logger.debug(this.getClass().getSimpleName(), "allocateEnterpriseSiteToUser",
					"Invalid Inputs to allocateEnterpriseSiteToUser");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid Assigment Parameters");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		}
		try {
			response = userManagementDAOBean.allotEnterpriseSiteToUser(enterpriseSystemId,dto);
			if (response == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No User Data Found!! ");
				logger.debug(this.getClass().getSimpleName(), "allocateEnterpriseSiteToUser",
						"Response ===== No User Data Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}

			logger.debug(this.getClass().getSimpleName(), "allocateEnterpriseSiteToUser",
					"allocation enterprise site to user successfully!!");
		} catch (InvalidInputException e) {
			logger.error(this.getClass().getSimpleName(), "allocateEnterpriseSiteToUser()",
					"delete the User  :::" + "User ID Not Found");
			ErrorResponse errResponse = new ErrorResponse(1, "Invalid Inputs are provided");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName(), "allocateEnterpriseSiteToUser()",
					"exception whileallocation enterprise site to user" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the allocation enterprise site to user" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (response.getResponse().equalsIgnoreCase("FAILURE")) {
			logger.debug(this.getClass().getSimpleName(), "allocateEnterpriseSiteToUser",
					"Response in assigning values ====== Failure ");
			return new ResponseEntity<UserAssigmentResponseDTO>(response, EnterpriseSiteUtils.getHeadersForPostAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			logger.debug(this.getClass().getSimpleName(), "allocateEnterpriseSiteToUser",
					"Response in assigning values ====== Success ");
			return new ResponseEntity<UserAssigmentResponseDTO>(response, EnterpriseSiteUtils.getHeadersForPostAPI(),
					HttpStatus.OK);
		}

	}

	@ApiOperation(value = "get list of enterprise sites", notes = "get list of enterprise sites", httpMethod = "GET")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "InternalServer error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "/enterpriseSites", method = RequestMethod.GET)
	public ResponseEntity<?> getListofEnterpriseSites(
			@ApiParam(name = "enterpriseSystemId", required = true, value = "String") @PathVariable String enterpriseSystemId) {
		EnterpriseSitesListResponseDTO response = null;
		logger.debug(this.getClass().getSimpleName(), "getListofEnterpriseSites()",
				"Inside the method getListofEnterpriseSites, enterprise site ID : " + enterpriseSystemId);
		logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSiteUsersBasedonEnterpriseSite()",
				"UserRegexUtil.isAlphaNumeric(enterpriseSystemId):::=== "
						+ UserRegexUtil.isAlphaNumeric(enterpriseSystemId));
		logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSiteUsersBasedonEnterpriseSite()",
				"StringUtils.isBlank(enterpriseSystemId):::=== " + StringUtils.isBlank(enterpriseSystemId));

		if (StringUtils.isBlank(enterpriseSystemId) || !UserRegexUtil.isAlphaNumeric(enterpriseSystemId)) {
			logger.debug(this.getClass().getSimpleName(), "getUserRoles", "Invalid Inputs to getListofEnterpriseSites");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid Enterprise System ID");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}

		try {
			response = userManagementDAOBean.getListofEnterpriseSites(enterpriseSystemId);
			if (response == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No User Data Found!! ");
				logger.debug(this.getClass().getSimpleName(), "getUserRoles", "Response ===== No User Data Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}
			logger.debug(this.getClass().getSimpleName(), "getListofEnterpriseSites",
					"roles available for user are returned successfully!!");
		} catch (InvalidInputException e) {
			logger.error(this.getClass().getSimpleName(), "allocateEnterpriseSiteToUser()",
					"delete the User  :::" + "User ID Not Found");
			ErrorResponse errResponse = new ErrorResponse(1, "Invalid Inputs are provided");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName(), "getListofEnterpriseSites()",
					"exception while getting enterprise sites" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method get enterprise sites" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(this.getClass().getSimpleName(), "getListofEnterpriseSites",
				"Response in getting enterprise sites ====== Success ");
		return new ResponseEntity<EnterpriseSitesListResponseDTO>(response, EnterpriseSiteUtils.getHeadersForPostAPI(),
				HttpStatus.OK);

	}

	@ApiOperation(value = "get list of assistant managers", notes = "get list of assistant managers", httpMethod = "GET")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "InternalServer error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "/asstmanager", method = RequestMethod.GET)
	public ResponseEntity<?> getAsstManagers(
			@ApiParam(name = "enterpriseSystemId", required = true, value = "String") @PathVariable String enterpriseSystemId,
			@QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit) {
		AllAsstManagersResponseDTO response = null;
		logger.debug(this.getClass().getSimpleName(), "getAsstManagers()",
				"Inside the method getAllLawEnforcementOfficers, Enterprise System ID : " + enterpriseSystemId);

		logger.debug(this.getClass().getSimpleName(), "getAsstManagers()",
				"UserRegexUtil.isAlphaNumeric(enterpriseSystemId):::=== "
						+ UserRegexUtil.isAlphaNumeric(enterpriseSystemId));
		logger.debug(this.getClass().getSimpleName(), "getAsstManagers()",
				"StringUtils.isBlank(enterpriseSystemId):::=== " + StringUtils.isBlank(enterpriseSystemId));

		if (StringUtils.isBlank(enterpriseSystemId) || !UserRegexUtil.isAlphaNumeric(enterpriseSystemId)) {
			logger.debug(this.getClass().getSimpleName(), "getAsstManagers",
					"Invalid Inputs to getAllEnterpriseSystemUsers");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid Enterprise System ID");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		}

		try {
			response = userManagementDAOBean.getAllEnterpriseSiteAsstManagers(enterpriseSystemId, offset, limit);
			if (response == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No User Data Found!! ");
				logger.debug(this.getClass().getSimpleName(), "getUserRoles", "Response ===== No User Data Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}
			logger.debug(this.getClass().getSimpleName(), "getUserRoles",
					"roles available for user are returned successfully!!");
		} catch (InvalidInputException e) {
			logger.error(this.getClass().getSimpleName(), "allocateEnterpriseSiteToUser()",
					"delete the User  :::" + "User ID Not Found");
			ErrorResponse errResponse = new ErrorResponse(1, "Invalid Inputs are provided");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName(), "getAllUsers()",
					"exception while fetching Permitted Operationd for Roles" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method get roles for the User" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(this.getClass().getSimpleName(), "getUserRoles", "Response in getting user roles ====== Success ");
		return new ResponseEntity<AllAsstManagersResponseDTO>(response, EnterpriseSiteUtils.getHeadersForPostAPI(),
				HttpStatus.OK);

	}

	@ApiOperation(value = "get list of enterprise sites belongs to user", notes = "get list of enterprise sites belongs to user", httpMethod = "GET")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "InternalServer error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "/user/{userId}/EnterpriseSites", method = RequestMethod.GET)
	public ResponseEntity<?> getListofEnterpriseSitesAssignedToUser(
			@ApiParam(name = "enterpriseSystemId", required = true, value = "String") @PathVariable String enterpriseSystemId,
			@ApiParam(name = "userId", required = true, value = "String") @PathVariable String userId) {
		EnterpriseSitesListDTO response = null;
		logger.debug(this.getClass().getSimpleName(), "getListofEnterpriseSitesAssignedToUser()",
				"Inside the method getListofEnterpriseSites, enterprise site ID : " + enterpriseSystemId);
		logger.debug(this.getClass().getSimpleName(), "getListofEnterpriseSitesAssignedToUser()",
				"UserRegexUtil.isAlphaNumeric(enterpriseSystemId):::=== "
						+ UserRegexUtil.isAlphaNumeric(enterpriseSystemId));
		logger.debug(this.getClass().getSimpleName(), "getListofEnterpriseSitesAssignedToUser()",
				"StringUtils.isBlank(enterpriseSystemId):::=== " + StringUtils.isBlank(enterpriseSystemId));
		logger.debug(this.getClass().getSimpleName(), "getListofEnterpriseSitesAssignedToUser()",
				"UserRegexUtil.isAlphaNumeric(userId):::=== " + UserRegexUtil.isAlphaNumeric(userId));
		logger.debug(this.getClass().getSimpleName(), "getListofEnterpriseSitesAssignedToUser()",
				"StringUtils.isBlank(userId):::=== " + StringUtils.isBlank(userId));

		if (StringUtils.isBlank(enterpriseSystemId) || StringUtils.isBlank(userId)
				|| !UserRegexUtil.isAlphaNumeric(userId) || !UserRegexUtil.isAlphaNumeric(enterpriseSystemId)) {
			logger.debug(this.getClass().getSimpleName(), "getListofEnterpriseSitesAssignedToUser",
					"Invalid Inputs to getListofEnterpriseSitesAssignedToUser");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid Input ID(s)");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}
		try {
			response = userManagementDAOBean.getEnterpriseSitesAssignedtoUserId(enterpriseSystemId, userId);
			if (response == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No User Data Found!! ");
				logger.debug(this.getClass().getSimpleName(), "getUserRoles", "Response ===== No User Data Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}
			logger.debug(this.getClass().getSimpleName(), "getListofEnterpriseSites",
					"roles available for user are returned successfully!!");
		} catch (InvalidInputException e) {
			logger.error(this.getClass().getSimpleName(), "allocateEnterpriseSiteToUser()",
					"delete the User  :::" + "User ID Not Found");
			ErrorResponse errResponse = new ErrorResponse(1, "Invalid Inputs are provided");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			logger.error(this.getClass().getSimpleName(), "getListofEnterpriseSites()",
					"exception while getting enterprise sites" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method get enterprise sites" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(this.getClass().getSimpleName(), "getListofEnterpriseSites",
				"Response in getting enterprise sites ====== Success ");
		return new ResponseEntity<EnterpriseSitesListDTO>(response, EnterpriseSiteUtils.getHeadersForPostAPI(),
				HttpStatus.OK);

	}

}
