package com.altiux.eum.esite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.altiux.commons.errors.ErrorResponse;
import com.altiux.commons.exceptions.InvalidInputException;
import com.altiux.commons.exceptions.EnterpriseSiteNotActiveException;
import com.altiux.commons.exceptions.UserNotFoundException;
import com.altiux.eum.esite.dao.LoginDAO;
import com.altiux.eum.esite.dto.LoginCredentialsDTO;
import com.altiux.eum.esite.dto.LoginSuccessDTO;
import com.altiux.eum.utils.EnterpriseSiteUtils;
import com.altiux.logger.App_logger;
import com.altiux.logger.EModuleName;
import com.altiux.logger.LoggerFactory;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;


@Controller
@RequestMapping(value = "eSite/login")
@Api(value = "eSite/login", description = "API to support login Operation", position = 1)
public class EnterpriseSiteLoginController {
	/* App_logger instance. */
	private App_logger logger = LoggerFactory.getLogger(EModuleName.CONTROLLER);

	@Autowired
	private LoginDAO loginDao;

	/**
	 * 
	 * @param loginRequest
	 * @return LoginSuccessDTO
	 */
	@ApiOperation(value = "login", notes = "This api is used to login to the account if the account is existing and credentials are correct", httpMethod = "POST")
	@ApiResponses(value = {@ApiResponse(code = 500, message = "Internal Server error"), @ApiResponse(code = 401, message = "UnAuthorized")})
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = {"application/json"})
	public ResponseEntity<?>  login(@ApiParam  @RequestBody LoginCredentialsDTO loginRequest) {
		logger.debug("LoginController","login", "inside the login() method");
		LoginSuccessDTO loginResponse = null;
		try {
			loginResponse=loginDao.login(loginRequest);
		} catch (InvalidInputException e) {
			logger.error("LoginController", "login", "InvalidInputException = "+e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(5001, e.getMessage()),EnterpriseSiteUtils.getHeadersForPostAPI(), HttpStatus.BAD_REQUEST);
		} catch (UserNotFoundException e) {
			logger.error("LoginController", "login", "InvalidInputException = "+e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(5002, e.getMessage()),EnterpriseSiteUtils.getHeadersForPostAPI(), HttpStatus.NOT_FOUND);
		} catch (EnterpriseSiteNotActiveException e) {
			logger.error("LoginController", "login", "InvalidInputException = "+e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(5003, e.getMessage()),EnterpriseSiteUtils.getHeadersForPostAPI(), HttpStatus.FORBIDDEN);
		}  catch(Exception e){
			logger.error("LoginController", "login", "Exception = "+e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(5004, e.getMessage()),EnterpriseSiteUtils.getHeadersForPostAPI(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(loginResponse == null) {
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(5005, "Some Issue Occurred"),EnterpriseSiteUtils.getHeadersForPostAPI(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.debug("LoginController", "login", "Logged in succesfully");
		return new ResponseEntity<LoginSuccessDTO>(loginResponse, EnterpriseSiteUtils.getHeadersForPostAPI(), HttpStatus.OK);
	}
}
