package com.java4u.eum.esystem.controller;

import java.util.List;

import javax.ws.rs.QueryParam;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.java4u.commons.errors.ErrorResponse;
import com.java4u.commons.exceptions.InvalidInputException;
import com.java4u.eum.esystem.dao.UserRequestDAO;
import com.java4u.eum.esystem.dao.impl.UserRegexUtil;
import com.java4u.eum.esystem.dto.UserRequestDetailsDTO;
import com.java4u.eum.esystem.dto.UserRequestDetailsListDTO;
import com.java4u.eum.utils.EnterpriseSiteUtils;
import com.java4u.logger.App_logger;
import com.java4u.logger.EModuleName;
import com.java4u.logger.LoggerFactory;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

/**
 * This Class is a Controller class to handle all operation on the user requests
 * in the Enterprise Site.
 * 
 */
@RestController
@RequestMapping(value = "eSystem/{enterpriseSystemId}/userrequest")
@Api(value = "eSystem/{enterpriseSystemId}/userrequest", description = "APIs to do Enterprise System "
		+ " userrequest related operations", position = 10)
public class EnterprirseSystemUserRequestController {
	/* App_logger instance. */
	private static final App_logger LOGGER = LoggerFactory.getLogger(EModuleName.CONTROLLER);

	@Autowired
	private UserRequestDAO userRequestDAO;

	@ApiOperation(value = "reject user request.", notes = "reject user request.", httpMethod = "DELETE")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal Server error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> rejectUserRequest(
			@ApiParam(name = "enterpriseSystemId", required = true, value = "String") @PathVariable String enterpriseSystemId,
			@ApiParam(name = "id", required = true, value = "String") @PathVariable String id) {
		boolean requestId = false;

		LOGGER.debug(this.getClass().getSimpleName(), "rejectUserRequest",
				"result1=====" + UserRegexUtil.isAlphaNumeric(id));
		LOGGER.debug(this.getClass().getSimpleName(), "rejectUserRequest", "result2=====" + StringUtils.isBlank(id));

		if (StringUtils.isBlank(id) || !UserRegexUtil.isAlphaNumeric(id)) {
			LOGGER.debug(this.getClass().getSimpleName(), "rejectUserRequest", "Invalid Inputs to rejectUserRequest");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid  inputs ID(s)");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}
		try {
			requestId = userRequestDAO.rejectUserRequest(enterpriseSystemId, id);
		} catch (InvalidInputException e) {
			// Invalid Input exception is thrown when one of the input parameter
			// is not valid.
			LOGGER.error(this.getClass().getSimpleName(), "rejectUserRequest",
					"InvalidInputException thrown while rejcting a UserRequest = " + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(1006, e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			// Invalid Input exception is thrown when one of the input parameter
			// is not valid.
			LOGGER.error(this.getClass().getSimpleName(), "createUserRequest",
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

	@ApiOperation(value = "create user request.", notes = "create user request.", httpMethod = "GET")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal Server error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	@RequestMapping(value = "/request/{enterpriseSiteId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> createUserRequest(
			@ApiParam(name = "enterpriseSystemId", required = true, value = "String") @PathVariable String enterpriseSystemId,
			@ApiParam(name = "enterpriseSiteId", required = true, value = "String") @PathVariable String enterpriseSiteId) {
		String requestId = null;
		LOGGER.debug(this.getClass().getSimpleName(), "createUserRequest",
				"UserRegexUtil.isAlphaNumeric(enterpriseSystemId)=====" + UserRegexUtil.isAlphaNumeric(enterpriseSystemId));
		LOGGER.debug(this.getClass().getSimpleName(), "createUserRequest",
				"StringUtils.isBlank(enterpriseSystemId)=====" + StringUtils.isBlank(enterpriseSystemId));
		LOGGER.debug(this.getClass().getSimpleName(), "createUserRequest",
				"UserRegexUtil.isAlphaNumeric(enterpriseSiteId)=====" + UserRegexUtil.isAlphaNumeric(enterpriseSiteId));
		LOGGER.debug(this.getClass().getSimpleName(), "createUserRequest",
				"StringUtils.isBlank(parkingSpaceId)=====" + StringUtils.isBlank(enterpriseSiteId));
		if (StringUtils.isBlank(enterpriseSiteId) || !UserRegexUtil.isAlphaNumeric(enterpriseSiteId)
				|| StringUtils.isBlank(enterpriseSystemId) || !UserRegexUtil.isAlphaNumeric(enterpriseSystemId)) {
			LOGGER.debug(this.getClass().getSimpleName(), "rejectUserRequest", "Invalid Inputs to rejectUserRequest");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid  inputs ID(s)");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}
		try {
			requestId = userRequestDAO.addUserRequest(enterpriseSystemId,enterpriseSiteId);

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

	@ApiOperation(value = "Get pending user request.", notes = "Get pending user request.", httpMethod = "GET")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal Server error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getPendingUserRequest(
			@ApiParam(name = "enterpriseSystemId", required = true, value = "String") @PathVariable String enterpriseSystemId,
			@QueryParam("type") String type) {
		List<UserRequestDetailsDTO> requests = null;
		LOGGER.debug(this.getClass().getSimpleName(), "getPendingUserRequest",
				"UserRegexUtil.isAlphaNumeric(enterpriseSystemId)=====" + UserRegexUtil.isAlphaNumeric(enterpriseSystemId));
		LOGGER.debug(this.getClass().getSimpleName(), "getPendingUserRequest",
				"StringUtils.isBlank(enterpriseSystemId)=====" + StringUtils.isBlank(enterpriseSystemId));
		if (StringUtils.isBlank(enterpriseSystemId) || !UserRegexUtil.isAlphaNumeric(enterpriseSystemId)) {
			LOGGER.debug(this.getClass().getSimpleName(), "getPendingUserRequest",
					"Invalid Inputs to rejectUserRequest");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid  inputs ID(s)");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}
		try {
			if (type != null && type.equalsIgnoreCase("Pending")) {
				requests = userRequestDAO.getPendingRequests(enterpriseSystemId);
			} else {
				return new ResponseEntity<ErrorResponse>(new ErrorResponse(1006, "Request type not valid"),
						HttpStatus.BAD_REQUEST);
			}

		} catch (InvalidInputException e) {
			LOGGER.error(this.getClass().getSimpleName(), "getPendingUserRequest",
					"InvalidInputException thrown while rejcting a UserRequest = " + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(1006, e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			LOGGER.error(this.getClass().getSimpleName(), "getPendingUserRequest",
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
