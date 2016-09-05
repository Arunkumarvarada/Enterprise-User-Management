package com.altiux.eum.esite.controller;

import java.util.List;

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

import com.altiux.commons.errors.ErrorResponse;
import com.altiux.commons.exceptions.InvalidInputException;
import com.altiux.eum.esite.dao.UserRequestDAO;
import com.altiux.eum.esite.dao.impl.UserRegexUtil;
import com.altiux.eum.esite.dto.UserRequestDTO;
import com.altiux.eum.esite.dto.UserRequestDetailsDTO;
import com.altiux.eum.esite.dto.UserRequestDetailsListDTO;
import com.altiux.eum.utils.EnterpriseSiteUtils;
import com.altiux.logger.App_logger;
import com.altiux.logger.EModuleName;
import com.altiux.logger.LoggerFactory;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "eSite/{enterpriseSiteId}/userrequest")
@Api(value = "eSite/{enterpriseSiteId}/userrequest", description = "APIs to do Enterprise Site "
		+ " userrequest related operations", position = 10)
public class EnterpriseSiteUserRequestController {

	/* App_logger instance. */
	private static final App_logger LOGGER = LoggerFactory.getLogger(EModuleName.CONTROLLER);

	@Autowired
	private UserRequestDAO userRequestDAO;

	@ApiOperation(value = "Create user request.", notes = "Create user request.", httpMethod = "POST")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal Server error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> createUserRequest(
			@ApiParam(name = "enterpriseSiteId", required = true, value = "String") @PathVariable String enterpriseSiteId,
			@ApiParam @RequestBody UserRequestDTO userDTO) {
		String requestId = null;
		
		LOGGER.debug(this.getClass().getSimpleName(), "createUserRequest()","UserRegexUtil.isAlphaNumeric(enterpriseSiteId):::=== " +UserRegexUtil.isAlphaNumeric(enterpriseSiteId));
		LOGGER.debug(this.getClass().getSimpleName(), "createUserRequest()","StringUtils.isBlank(enterpriseSiteId):::=== " + StringUtils.isBlank(enterpriseSiteId));
		if (StringUtils.isBlank(enterpriseSiteId) || !UserRegexUtil.isAlphaNumeric(enterpriseSiteId)) {
			LOGGER.debug(this.getClass().getSimpleName(), "getUserRoles",
					"Invalid Inputs for getting Roles of the User");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid userID or Enterprisr Site ID");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		}
		
		try {
			requestId = userRequestDAO.addUserRequest(userDTO, enterpriseSiteId);

		} catch (InvalidInputException e) {
			// Invalid Input exception is thrown when one of the input parameter
			// is not valid.
			LOGGER.error(this.getClass().getSimpleName(), "createUserRequest",
					"InvalidInputException thrown while adding a UserRequest = " + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(1006, e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			// Invalid Input exception is thrown when one of the input parameter
			// is not valid.
			LOGGER.error(this.getClass().getSimpleName(), "createUserRequest",
					"Exception thrown while adding a UserRequest = " + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(11000, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (requestId == null) {
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(11000, "Internal Server Error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(requestId, HttpStatus.OK);

	}

	@ApiOperation(value = "Delete user request.", notes = "Create user request.", httpMethod = "DELETE")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal Server error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteUserRequest(
			@ApiParam(name = "enterpriseSiteId", required = true, value = "String") @PathVariable String enterpriseSiteId,
			@ApiParam(name = "id", required = true, value = "String") @PathVariable String id) {
		boolean requestId = false;
		
		LOGGER.debug(this.getClass().getSimpleName(), "deleteUserRequest()","UserRegexUtil.isAlphaNumeric(enterpriseSiteId):::=== " +UserRegexUtil.isAlphaNumeric(enterpriseSiteId));
		LOGGER.debug(this.getClass().getSimpleName(), "deleteUserRequest()","StringUtils.isBlank(enterpriseSiteId):::=== " + StringUtils.isBlank(enterpriseSiteId));
		LOGGER.debug(this.getClass().getSimpleName(), "deleteUserRequest()","UserRegexUtil.isAlphaNumeric(id):::=== " +UserRegexUtil.isAlphaNumeric(id));
		LOGGER.debug(this.getClass().getSimpleName(), "deleteUserRequest()","StringUtils.isBlank(id):::=== " + StringUtils.isBlank(id));
		
		if (StringUtils.isBlank(enterpriseSiteId) || !UserRegexUtil.isAlphaNumeric(enterpriseSiteId)
				|| StringUtils.isBlank(id) || !UserRegexUtil.isAlphaNumeric(id)) {
			LOGGER.debug(this.getClass().getSimpleName(), "deleteUserRequest",
					"Invalid Inputs for getting Roles of the User");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid requestID or Enterprisr Site ID");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		}
		try {
			requestId = userRequestDAO.deleteUserRequest(enterpriseSiteId, id);
		} catch (InvalidInputException e) {
			LOGGER.error(this.getClass().getSimpleName(), "deleteUserRequest",
					"InvalidInputException thrown while deleting a UserRequest = " + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(1006, e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error(this.getClass().getSimpleName(), "deleteUserRequest",
					"Exception thrown while adding a UserRequest = " + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(11000, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (!requestId) {
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(11000, "Internal Server Error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Boolean>(true, HttpStatus.NO_CONTENT);

	}

	@SuppressWarnings("unused")
	@ApiOperation(value = "Get pending user request.", notes = "Get pending user request.", httpMethod = "GET")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal Server error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getPendingUserRequest(
			@ApiParam(name = "enterpriseSiteId", required = true, value = "String") @PathVariable String enterpriseSiteId,
			@QueryParam("type") String type) {
		List<UserRequestDetailsDTO> requests = null;
		LOGGER.debug(this.getClass().getSimpleName(), "getPendingUserRequest()","UserRegexUtil.isAlphaNumeric(enterpriseSiteId):::=== " +UserRegexUtil.isAlphaNumeric(enterpriseSiteId));
		LOGGER.debug(this.getClass().getSimpleName(), "getPendingUserRequest()","StringUtils.isBlank(enterpriseSiteId):::=== " + StringUtils.isBlank(enterpriseSiteId));
		
		if (StringUtils.isBlank(enterpriseSiteId) || !UserRegexUtil.isAlphaNumeric(enterpriseSiteId)) {
			LOGGER.debug(this.getClass().getSimpleName(), "getUserRoles",
					"Invalid Inputs for getting Roles of the User");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid userID or Enterprisr Site ID");
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(1006, "Request type not valid"),
					HttpStatus.BAD_REQUEST);
		}
		try {
			requests = userRequestDAO.getPendingRequests(enterpriseSiteId, type);

		} catch (InvalidInputException e) {
			LOGGER.error(this.getClass().getSimpleName(), "rejectUserRequest",
					"InvalidInputException thrown while rejcting a UserRequest = " + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(1006, e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// Invalid Input exception is thrown when one of the input parameter
			// is not valid.
			LOGGER.error(this.getClass().getSimpleName(), "createUserRequest",
					"Exception thrown while adding a UserRequest = " + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(11000, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (requests == null || requests.isEmpty()) {
			new ResponseEntity<List<UserRequestDetailsDTO>>(requests, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<UserRequestDetailsListDTO>(new UserRequestDetailsListDTO(requests), HttpStatus.OK);

	}

}