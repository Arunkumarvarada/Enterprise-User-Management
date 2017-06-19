/**
 * 
 */
package com.java4u.eum.esystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.java4u.eum.esystem.dao.EnterpriseSystemLoginDAO;
import com.java4u.eum.esystem.dto.LoginControllerRequestDTO;
import com.java4u.eum.esystem.dto.LoginControllerResponseDTO;
import com.java4u.eum.esystem.dto.LoginWithRolesRequestDTO;
import com.java4u.eum.esystem.dto.LoginWithRolesResponseDTO;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;


@Controller
@RequestMapping(value = "eSystem/login")
@Api(value = "eSystem/login", description = "API to support login Operation")
public class EnterpriseSystemLoginController {

	@Autowired
	private EnterpriseSystemLoginDAO eSystem;
	
	@ApiOperation(value = "Enterprise System Login", notes = "This API is used to login a user under the Enterprise System"
			+ " portfolio.", httpMethod = "POST")
	@ApiResponses(value = {@ApiResponse(code = 500, message = "Internal Server error"), @ApiResponse(code = 403, message = "Forbidden"), 
			@ApiResponse(code=404, message="Not Found"), @ApiResponse(code=503, message="Service Unavailable")})
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<LoginControllerResponseDTO>  login(@ApiParam @RequestBody LoginControllerRequestDTO loginRequestDTO) {
		LoginControllerResponseDTO loginResponseDTO = null;
		try{
			loginResponseDTO = eSystem.login(loginRequestDTO);
		}
		catch(Exception ex)
		{
			return new ResponseEntity<LoginControllerResponseDTO>(new LoginControllerResponseDTO(), HttpStatus.BAD_REQUEST);
		}
		if(loginResponseDTO == null) {
	    	return new ResponseEntity<LoginControllerResponseDTO>(loginResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	return new ResponseEntity<LoginControllerResponseDTO>(loginResponseDTO, loginResponseDTO.getResponseCode());
	}
	
	@ApiOperation(value = "Enterprise System Login to fetch Roles Response", notes = "This API is used to Enterprise System Login to fetch Roles Response"
			+ " portfolio.", httpMethod = "POST")
	@ApiResponses(value = {@ApiResponse(code = 500, message = "Internal Server error"), @ApiResponse(code = 403, message = "Forbidden"), 
			@ApiResponse(code=404, message="Not Found"), @ApiResponse(code=503, message="Service Unavailable")})
	@RequestMapping(value = "/loginWithRoles",method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<LoginWithRolesResponseDTO>  loginAndFetchRoles(@ApiParam @RequestBody LoginWithRolesRequestDTO loginRequestDTO) {
		LoginWithRolesResponseDTO loginResponseDTO = null;
		try{
			loginResponseDTO = eSystem.loginWithRolesResponse(loginRequestDTO);
		}
		catch(Exception ex)
		{
			return new ResponseEntity<LoginWithRolesResponseDTO>(loginResponseDTO, loginResponseDTO.getResponseCode());
		}
		if(loginResponseDTO == null) {
	    	return new ResponseEntity<LoginWithRolesResponseDTO>(loginResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	return new ResponseEntity<LoginWithRolesResponseDTO>(loginResponseDTO, loginResponseDTO.getResponseCode());
	}
}
