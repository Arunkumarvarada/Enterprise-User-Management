package com.java4u.eum.esite.controller;

import java.util.List;

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
import com.java4u.eum.esite.dao.UserManagementDAO;
import com.java4u.eum.esite.dao.impl.UserRegexUtil;
import com.java4u.eum.esite.dto.AllUsersResponseDTO;
import com.java4u.eum.esite.dto.AllowedPermissionUpdateRequestDTO;
import com.java4u.eum.esite.dto.AllowedPermissionUpdateResponseDTO;
import com.java4u.eum.esite.dto.ModuleListDTO;
import com.java4u.eum.esite.dto.OperationsDTO;
import com.java4u.eum.esite.dto.RoleListDTO;
import com.java4u.eum.esite.dto.UserAdditionRequestDTO;
import com.java4u.eum.esite.dto.UserAdditionResponseDTO;
import com.java4u.eum.esite.dto.UserDeletionRequestDTO;
import com.java4u.eum.esite.dto.UserDeletionResponseDTO;
import com.java4u.eum.esite.dto.UserRolesDTO;
import com.java4u.eum.esystem.dao.EnterpriseSystemDAO;
import com.java4u.eum.esystem.dto.EnterpriseSystemAdditionResponseDTO;
import com.java4u.eum.esystem.dto.SiteUserAdditionRequestDTO;
import com.java4u.eum.esystem.dto.SiteUserAdditionResponseDTO;
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
@RequestMapping(value = "eSite/{enterpriseSiteId}/usermanagement")
@Api(value = "eSite/{enterpriseSiteId}/usermanagement", description = "APIs to do"
		+ "Enterprise Site user manangement related operations", position = 16)
public class EnterpriseSiteUserManagementController {

	private static final App_logger logger = LoggerFactory.getLogger(EModuleName.CONTROLLER);

	@Autowired
	private UserManagementDAO userManagementDAOBean;

	@Autowired
	private EnterpriseSystemDAO enterpriseSystemDAOBean;
	
	@ApiOperation(value = "get roles of given userId ", notes = "get roles of given userId", httpMethod = "GET")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal Server error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "/roles/user/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getUserRoles(
			@ApiParam(name = "enterpriseSiteId", required = true, value = "String") @PathVariable String enterpriseSiteId,
			@ApiParam(name = "userId", required = true, value = "String") @PathVariable String userId) {
		UserRolesDTO response = null;
		logger.debug(this.getClass().getSimpleName(), "getUserRoles()",
				"Inside the method get roles for the User, Enterprise Site ID : " + enterpriseSiteId + " user ID : "
						+ userId);
		logger.debug(this.getClass().getSimpleName(), "getUserRoles()",
				"UserRegexUtil.isAlphaNumeric(enterpriseSiteId):::=== "
						+ UserRegexUtil.isAlphaNumeric(enterpriseSiteId));
		logger.debug(this.getClass().getSimpleName(), "getUserRoles()",
				"StringUtils.isBlank(enterpriseSiteId):::=== " + StringUtils.isBlank(enterpriseSiteId));
		logger.debug(this.getClass().getSimpleName(), "getUserRoles()",
				"UserRegexUtil.isAlphaNumeric(userId):::=== " + UserRegexUtil.isAlphaNumeric(userId));
		logger.debug(this.getClass().getSimpleName(), "getUserRoles()",
				"StringUtils.isBlank(userId):::=== " + StringUtils.isBlank(userId));

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(enterpriseSiteId)
				|| !UserRegexUtil.isAlphaNumeric(userId) || !UserRegexUtil.isAlphaNumeric(enterpriseSiteId)) {
			logger.debug(this.getClass().getSimpleName(), "getUserRoles",
					"Invalid Inputs for getting Roles of the User");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid userID or Enterprise Site ID");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}
		try {
			response = userManagementDAOBean.getRolesOfUser(userId, enterpriseSiteId);
			logger.debug(this.getClass().getSimpleName(), "getUserRoles",
					"roles available for user: " + response.getUserId() + " roleID:" + response.getRoleId());
			if (response.getUserId() == null || response.getRoleId() == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No User Data Found!! ");
				logger.debug(this.getClass().getSimpleName(), "getUserRoles", "Response ===== No User Data Found!! ");

				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}
		} catch (InvalidInputException e) {
			logger.error(this.getClass().getSimpleName(), "getUserRoles()", "roles for the User" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method get roles for the User" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName(), "getUserRoles()", "roles for the User" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method get roles for the User" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(this.getClass().getSimpleName(), "getUserRoles", "Response in getting user roles ====== Success ");
		return new ResponseEntity<UserRolesDTO>(response, EnterpriseSiteUtils.getHeadersForGetAPI(), HttpStatus.OK);
	}

	@ApiOperation(value = "update the allowed permissions ", notes = "update the allowed permissions", httpMethod = "POST")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "InternalServer error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })

	@RequestMapping(value = "/allowedpersmissions", method = RequestMethod.POST)
	public ResponseEntity<?> addAllowedPersmissionsToUser(
			@ApiParam(name = "enterpriseSiteId", required = true, value = "String") @PathVariable String enterpriseSiteId,
			@ApiParam @RequestBody AllowedPermissionUpdateRequestDTO requestDTO) {
		AllowedPermissionUpdateResponseDTO response = new AllowedPermissionUpdateResponseDTO();
		logger.debug(this.getClass().getSimpleName(), "addAllowedPersmissionsToUser()",
				"Inside the method get roles for the User, Enterprise Site ID : " + enterpriseSiteId
						+ " admin user ID : " + requestDTO.getUserId());
		logger.debug(this.getClass().getSimpleName(), "addAllowedPersmissionsToUser()",
				"UserRegexUtil.isAlphaNumeric(enterpriseSiteId):::=== "
						+ UserRegexUtil.isAlphaNumeric(enterpriseSiteId));
		logger.debug(this.getClass().getSimpleName(), "addAllowedPersmissionsToUser()",
				"StringUtils.isBlank(enterpriseSiteId):::=== " + StringUtils.isBlank(enterpriseSiteId));
		logger.debug(this.getClass().getSimpleName(), "addAllowedPersmissionsToUser()",
				"UserRegexUtil.isAlphaNumeric(userid):::=== " + UserRegexUtil.isAlphaNumeric(requestDTO.getUserId()));
		logger.debug(this.getClass().getSimpleName(), "addAllowedPersmissionsToUser()",
				"StringUtils.isBlank(userid):::=== " + StringUtils.isBlank(requestDTO.getUserId()));
		logger.debug(this.getClass().getSimpleName(), "addAllowedPersmissionsToUser()",
				"UserRegexUtil.isAlphaNumeric(adminId):::=== "
						+ UserRegexUtil.isAlphaNumeric(requestDTO.getAdminUserId()));
		logger.debug(this.getClass().getSimpleName(), "addAllowedPersmissionsToUser()",
				"StringUtils.isBlank(adminId):::=== " + StringUtils.isBlank(requestDTO.getAdminUserId()));
		logger.debug(this.getClass().getSimpleName(), "addAllowedPersmissionsToUser()",
				"requestDTO.getOperationsList().size() == 0:::=== " + (requestDTO.getOperationsList().size() == 0));

		if (StringUtils.isBlank(enterpriseSiteId) || StringUtils.isBlank(requestDTO.getUserId())
				|| StringUtils.isBlank(requestDTO.getAdminUserId()) || (requestDTO.getOperationsList().size() == 0)
				|| !UserRegexUtil.isAlphaNumeric(enterpriseSiteId)
				|| !UserRegexUtil.isAlphaNumeric(requestDTO.getUserId())
				|| !UserRegexUtil.isAlphaNumeric(requestDTO.getAdminUserId())) {
			logger.debug(this.getClass().getSimpleName(), "addAllowedPersmissionsToUser",
					"Invalid Inputs for getting Roles of the User");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid userID or Enterprise Site ID");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}

		try {
			List<OperationsDTO> dto = userManagementDAOBean.addOrUpdatePermissions(enterpriseSiteId,
					requestDTO.getUserId(), requestDTO.getAdminUserId(), requestDTO.getOperationsList());
			if (dto != null) {
				response.setOperationsList(dto);
			}
			if (dto == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No User Data Found!! ");
				logger.debug(this.getClass().getSimpleName(), "getUserRoles", "Response ===== No User Data Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}
			response.setEnterpriseSiteId(enterpriseSiteId);
			response.setUserId(requestDTO.getUserId());
			response.setResponse("Successul in Updating Roles!!");
			response.setAdminUserId(requestDTO.getAdminUserId());
			logger.debug(this.getClass().getSimpleName(), "getUserRoles",
					"roles available for user are returned successfully!!");
		} catch (InvalidInputException e) {
			logger.error(this.getClass().getSimpleName(), "addAllowedPersmissionsToUser()",
					"roles for the User" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method get roles for the User" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName(), "addAllowedPersmissionsToUser()",
					"exception while updating operations for the user" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method get roles for the User" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(this.getClass().getSimpleName(), "addAllowedPersmissionsToUser",
				"Response in getting user roles ====== Success ");
		return new ResponseEntity<AllowedPermissionUpdateResponseDTO>(response,
				EnterpriseSiteUtils.getHeadersForPostAPI(), HttpStatus.OK);

	}

	@ApiOperation(value = "get list of users", notes = "get list of usersbased onthe enterpriseSiteId", httpMethod = "GET")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "InternalServer error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "/users/{adminId}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUsers(
			@ApiParam(name = "enterpriseSiteId", required = true, value = "String") @PathVariable String enterpriseSiteId,
			@ApiParam(name = "adminId", required = true, value = "String") @PathVariable String adminId) {
		AllUsersResponseDTO response = null;
		System.out.println(adminId);
		System.out.println(enterpriseSiteId);
		logger.debug(this.getClass().getSimpleName(), "getAllUsers()",
				"Inside the method get roles for the User, Enterprise Site ID : " + enterpriseSiteId
						+ " admin user ID : " + adminId);

		logger.debug(this.getClass().getSimpleName(), "getAllUsers()",
				"UserRegexUtil.isAlphaNumeric(enterpriseSiteId):::=== "
						+ UserRegexUtil.isAlphaNumeric(enterpriseSiteId));
		logger.debug(this.getClass().getSimpleName(), "getAllUsers()",
				"StringUtils.isBlank(enterpriseSiteId):::=== " + StringUtils.isBlank(enterpriseSiteId));
		logger.debug(this.getClass().getSimpleName(), "getAllUsers()",
				"UserRegexUtil.isAlphaNumeric(enterpriseSiteId):::=== " + UserRegexUtil.isAlphaNumeric(adminId));
		logger.debug(this.getClass().getSimpleName(), "getAllUsers()",
				"StringUtils.isBlank(enterpriseSiteId):::=== " + StringUtils.isBlank(adminId));

		if (StringUtils.isBlank(adminId) || StringUtils.isBlank(enterpriseSiteId)
				|| !UserRegexUtil.isAlphaNumeric(enterpriseSiteId) || !UserRegexUtil.isAlphaNumeric(adminId)) {
			logger.debug(this.getClass().getSimpleName(), "getUserRoles",
					"Invalid Inputs for getting Roles of the User");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid userID or Enterprise Site ID");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}
		try {
			response = userManagementDAOBean.getAllUsers(enterpriseSiteId, adminId);
			if (response == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No User Data Found!! ");
				logger.debug(this.getClass().getSimpleName(), "getUserRoles", "Response ===== No User Data Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}
			logger.debug(this.getClass().getSimpleName(), "getUserRoles",
					"roles available for user are returned successfully!!");
		} catch (InvalidInputException e) {
			logger.error(this.getClass().getSimpleName(), "deleteUser()", "roles for the User" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method get roles for the User" + e.getMessage());
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
		return new ResponseEntity<AllUsersResponseDTO>(response, EnterpriseSiteUtils.getHeadersForPostAPI(),
				HttpStatus.OK);
	}

	@ApiOperation(value = "get list of moduleNames", notes = "get list of moduleNames", httpMethod = "GET")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "InternalServer error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "/modules", method = RequestMethod.GET)
	public ResponseEntity<?> getModuleNames(
			@ApiParam(name = "enterpriseSiteId", required = true, value = "String") @PathVariable String enterpriseSiteId) {
		List<String> response = null;
		logger.debug(this.getClass().getSimpleName(), "getModuleNames()",
				"Inside the method get roles for the User, Enterprise Site ID : " + enterpriseSiteId);

		if (StringUtils.isBlank(enterpriseSiteId) || !UserRegexUtil.isAlphaNumeric(enterpriseSiteId)) {
			logger.debug(this.getClass().getSimpleName(), "getUserRoles",
					"Invalid Inputs for getting of the Module Names");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid userID or Enterprise Site ID");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}

		try {
			response = userManagementDAOBean.getAllOperations(enterpriseSiteId);
			if (response == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- NO Modules Found!! ");
				logger.debug(this.getClass().getSimpleName(), "getUserRoles", "Failure --- NO Modules Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NOT_FOUND);
			}
			logger.debug(this.getClass().getSimpleName(), "getUserRoles",
					"Module Names available for given inputs and are returned successfully!!");
		} catch (InvalidInputException e) {
			logger.error(this.getClass().getSimpleName(), "deleteUser()", "roles for the User" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method get roles for the User" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName(), "getModuleNames()",
					"exception while fetching Permitted Operationd for Roles" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method get roles for the User" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.debug(this.getClass().getSimpleName(), "getUserRoles",
				"Response in getting module Names ====== Success ");
		return new ResponseEntity<ModuleListDTO>(new ModuleListDTO(response),
				EnterpriseSiteUtils.getHeadersForPostAPI(), HttpStatus.OK);
	}

	@ApiOperation(value = "get list of roleNames", notes = "get list of roleNames", httpMethod = "GET")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "InternalServer error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	public ResponseEntity<?> getRoleNames(
			@ApiParam(name = "enterpriseSiteId", required = true, value = "String") @PathVariable String enterpriseSiteId) {
		List<String> response = null;
		logger.debug(this.getClass().getSimpleName(), "getRoleNames()",
				"Inside the method get roles for the User, Enterprise Site ID : " + enterpriseSiteId);
		logger.debug(this.getClass().getSimpleName(), "getRoleNames()",
				"UserRegexUtil.isAlphaNumeric(enterpriseSiteId):::=== "
						+ UserRegexUtil.isAlphaNumeric(enterpriseSiteId));
		logger.debug(this.getClass().getSimpleName(), "getRoleNames()",
				"StringUtils.isBlank(enterpriseSiteId):::=== " + StringUtils.isBlank(enterpriseSiteId));

		if (StringUtils.isBlank(enterpriseSiteId) || !UserRegexUtil.isAlphaNumeric(enterpriseSiteId)) {
			logger.debug(this.getClass().getSimpleName(), "getUserRoles", "Invalid Inputs for getting Roles");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Enterprise Site ID Invalid");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		}

		try {
			response = userManagementDAOBean.getAllRoles(enterpriseSiteId);
			if (response == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No Roles Data Found!! ");
				logger.debug(this.getClass().getSimpleName(), "getUserRoles", "Response ===== No Roles Data Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}
			logger.debug(this.getClass().getSimpleName(), "getUserRoles",
					"roles available for user are returned successfully!!");
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName(), "getRoleNames()",
					"exception while fetching Permitted Operationd for Roles" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method get roles for the User" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(this.getClass().getSimpleName(), "getUserRoles", "Response in getting user roles ====== Success ");
		return new ResponseEntity<RoleListDTO>(new RoleListDTO(response), EnterpriseSiteUtils.getHeadersForPostAPI(),
				HttpStatus.OK);
	}

	@ApiOperation(value = "delete site user", notes = "delete site user", httpMethod = "DELETE")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "InternalServer error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "/SiteUser/Admin/{siteAdminId}/siteuser/{siteUserId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteSiteUser(
			@ApiParam(name = "enterpriseSiteId", required = true, value = "String") @PathVariable String enterpriseSiteId,
			@ApiParam(name = "siteUserId", required = true, value = "String") @PathVariable String siteUserId,
			@ApiParam(name = "siteAdminId", required = true, value = "String") @PathVariable String siteAdminId){
		UserDeletionResponseDTO response= new UserDeletionResponseDTO();
		logger.debug(this.getClass().getSimpleName(), "deleteSiteUser()",
				"Inside the method get roles for the User, Enterprise Site ID : " + enterpriseSiteId);
		logger.debug(this.getClass().getSimpleName(), "deleteSiteUser()",
				"UserRegexUtil.isAlphaNumeric(enterpriseSiteId):::=== "
						+ UserRegexUtil.isAlphaNumeric(enterpriseSiteId));
		logger.debug(this.getClass().getSimpleName(), "deleteSiteUser()",
				"StringUtils.isBlank(enterpriseSiteId):::=== " + StringUtils.isBlank(enterpriseSiteId));

		if (StringUtils.isBlank(enterpriseSiteId) || !UserRegexUtil.isAlphaNumeric(enterpriseSiteId)) {
			logger.debug(this.getClass().getSimpleName(), "getUserRoles", "Invalid Inputs for getting Roles");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Enterprise Site ID Invalid");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		}

		try {
			boolean isDeleted=false;
			isDeleted = enterpriseSystemDAOBean.deleteSiteUser(siteAdminId, siteUserId, enterpriseSiteId);
			if (isDeleted == false) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No Roles Data Found!! ");
				logger.debug(this.getClass().getSimpleName(), "deleteSiteUser", "Response ===== No Roles Data Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}
			logger.debug(this.getClass().getSimpleName(), "deleteSiteUser",
					"roles available for user are returned successfully!!");
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName(), "deleteSiteUser()",
					"exception while fetching Permitted Operationd for Roles" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method get roles for the User" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(this.getClass().getSimpleName(), "deleteSiteUser", "Response in getting user roles ====== Success ");
		return new ResponseEntity<UserDeletionResponseDTO>(response, EnterpriseSiteUtils.getHeadersForPostAPI(),
				HttpStatus.NO_CONTENT);
				
		
	}

	@ApiOperation(value = "add site user", notes = "add site user", httpMethod = "POST")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "InternalServer error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "/SiteUser/Admin/{siteAdminId}", method = RequestMethod.POST)
	public ResponseEntity<?> addSiteUser(
			@ApiParam(name = "enterpriseSiteId", required = true, value = "String") @PathVariable String enterpriseSiteId,
			@ApiParam(name = "siteAdminId", required = true, value = "String") @PathVariable String siteAdminId,
			@ApiParam @RequestBody SiteUserAdditionRequestDTO  requestDTO){

		SiteUserAdditionResponseDTO response= null;

		if (StringUtils.isBlank(enterpriseSiteId)
				|| StringUtils.isBlank(siteAdminId) || requestDTO == null) {
			logger.debug(this.getClass().getSimpleName(), "addSiteUser",
					"Invalid Inputs to creat an Site user");
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Invalid Inputs to creat an site user");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}
		try {

			response = enterpriseSystemDAOBean.addSiteUser(siteAdminId, enterpriseSiteId, requestDTO);
			logger.debug(this.getClass().getSimpleName(), "addSiteUser",
					 "user id: " + response.getUserId() + " enterprise site Id:"
							+enterpriseSiteId );
			if (response.getUserId() == null || response == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No Enterprise Data Found ");
				logger.debug(this.getClass().getSimpleName(), "addSiteUser",
						"Response ===== No Enterprise Data Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			Thread.dumpStack();
			logger.error(this.getClass().getSimpleName(), "addSiteUser()",
					" Enterprise System" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method creation of user ::" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(this.getClass().getSimpleName(), "addSiteUser()",
				"Response in creation of user ====== Success");
		return new ResponseEntity<SiteUserAdditionResponseDTO>(response,
				EnterpriseSiteUtils.getHeadersForPostAPI(), HttpStatus.CREATED);
		
	}
}
