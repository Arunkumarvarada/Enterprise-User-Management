package com.altiux.eum.esystem.controller;

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

import com.altiux.commons.errors.ErrorResponse;
import com.altiux.commons.exceptions.InvalidInputException;
import com.altiux.eum.entities.Operations;
import com.altiux.eum.esystem.dao.EnterpriseSystemDAO;
import com.altiux.eum.esystem.dto.EnterpriseSiteAdditionRequestDTO;
import com.altiux.eum.esystem.dto.EnterpriseSiteAdditionResponseDTO;
import com.altiux.eum.esystem.dto.EnterpriseSiteStatusRepsonse;
import com.altiux.eum.esystem.dto.EnterpriseSystemAdditionRequestDTO;
import com.altiux.eum.esystem.dto.EnterpriseSystemAdditionResponseDTO;
import com.altiux.eum.esystem.dto.EnterpriseSystemStatusRepsonse;
import com.altiux.eum.esystem.dto.OperationsDTO;
import com.altiux.eum.esystem.dto.OperationsInputResponseDTO;
import com.altiux.eum.esystem.dto.RolesDTO;
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
@RequestMapping(value = "esystem")
@Api(value = "esystem", description = "API to support add and enterprise system and site Operation")
public class EnterpriseSystemController {

	private static final App_logger logger = LoggerFactory.getLogger(EModuleName.CONTROLLER);

	@Autowired
	private EnterpriseSystemDAO enterpriseSystemDAOBean;

	@ApiOperation(value = "create an Enterprise System ", notes = "create an Enterprise System ", httpMethod = "POST")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal Server error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> addEnterpriseSystem(@ApiParam @RequestBody EnterpriseSystemAdditionRequestDTO requestDTO) {
		EnterpriseSystemAdditionResponseDTO response = null;

		if (StringUtils.isBlank(requestDTO.getEnterpriseSystemId())
				|| StringUtils.isBlank(requestDTO.getEnterpriseSystemId()) || requestDTO == null) {
			logger.debug(this.getClass().getSimpleName(), "addEnterpriseSystem",
					"Invalid Inputs to creat an EnterpriseSystem");
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Invalid Inputs to creat an EnterpriseSystem");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}
		try {

			response = enterpriseSystemDAOBean.addEnterpriseSystem(requestDTO);
			logger.debug(this.getClass().getSimpleName(), "addEnterpriseSystem",
					"enterprise system id: " + response.getEnterpriseSystemId() + " enterprise system name:"
							+ response.getEnterpriseSystemName());
			if (response.getEnterpriseSystemId() == null || response.getEnterpriseSystemName() == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No Enterprise Data Found ");
				logger.debug(this.getClass().getSimpleName(), "addEnterpriseSystem",
						"Response ===== No Enterprise Data Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			Thread.dumpStack();
			logger.error(this.getClass().getSimpleName(), "addEnterpriseSystem()",
					" Enterprise System" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method creation of user ::" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(this.getClass().getSimpleName(), "addEnterpriseSystem()",
				"Response in creation of user ====== Success");
		return new ResponseEntity<EnterpriseSystemAdditionResponseDTO>(response,
				EnterpriseSiteUtils.getHeadersForPostAPI(), HttpStatus.CREATED);
	}

	@ApiOperation(value = "create an Enterprise Site with Admin ", notes = "create an Enterprise Site with Admin", httpMethod = "POST")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal Server error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "/{eSystemId}/esiteWithAdmin", method = RequestMethod.POST)
	public ResponseEntity<?> addEnterpriseSiteWithAdmin(
			@ApiParam(name = "eSystemId", required = true, value = "String") @PathVariable String eSystemId,
			@ApiParam @RequestBody EnterpriseSiteAdditionRequestDTO requestDTO) {
		EnterpriseSiteAdditionResponseDTO response = null;

		if (StringUtils.isBlank(requestDTO.geteSiteId()) || StringUtils.isBlank(requestDTO.geteSiteId())
				|| requestDTO == null) {
			logger.debug(this.getClass().getSimpleName(), "addEnterpriseSite",
					"Invalid Inputs to creat an EnterpriseSite");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid Inputs to creat an EnterpriseSite");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}
		try {
			response = enterpriseSystemDAOBean.addEnterpriseSiteWithAdmin(eSystemId, requestDTO);
			logger.debug(this.getClass().getSimpleName(), "addEnterpriseSite",
					"enterprise system id: " + response + " enterprise system name:" + eSystemId);
			if (response.geteSiteId() == null || response.geteSiteName() == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No Enterprise Data Found ");
				logger.debug(this.getClass().getSimpleName(), "addEnterpriseSite",
						"Response ===== No Enterprise Data Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}
		} catch (InvalidInputException e) {
			logger.error(this.getClass().getSimpleName(), "addEnterpriseSite()", " Enterprise Site " + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1, e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			Thread.dumpStack();
			logger.error(this.getClass().getSimpleName(), "addEnterpriseSite()", " Enterprise System" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method creation of user ::" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(this.getClass().getSimpleName(), "addEnterpriseSite()",
				"Response in creation of user ====== Success");
		return new ResponseEntity<EnterpriseSiteAdditionResponseDTO>(response,
				EnterpriseSiteUtils.getHeadersForPostAPI(), HttpStatus.CREATED);
	}

	@ApiOperation(value = "active Or Deactive System ", notes = "active Or Deactive System ", httpMethod = "POST")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal Server error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "/{enterpriseSystemId}/status/{status}", method = RequestMethod.POST)
	public ResponseEntity<?> activeOrDeactiveSystem(
			@ApiParam(name = "enterpriseSystemId", required = true, value = "String") @PathVariable String enterpriseSystemId,
			@ApiParam(name = "status", required = true, value = "String") @PathVariable String status) {
		EnterpriseSystemStatusRepsonse response = null;

		if (StringUtils.isBlank(enterpriseSystemId) || StringUtils.isBlank(status)) {
			logger.debug(this.getClass().getSimpleName(), "activeOrDeactiveSystem",
					"Invalid Inputs to creat an EnterpriseSite");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid Inputs to creat an EnterpriseSystem");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}
		try {

			response = enterpriseSystemDAOBean.activeOrDectiveSystem(enterpriseSystemId, status);
			logger.debug(this.getClass().getSimpleName(), "activeOrDeactiveSystem",
					"enterprise system id: " + response + " enterprise system name:" + enterpriseSystemId);
			if (response.getEnterpriseSystemId() == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No Enterprise Data Found ");
				logger.debug(this.getClass().getSimpleName(), "activeOrDeactiveSystem",
						"Response ===== No Enterprise Data Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}
		} catch (InvalidInputException e) {
			logger.error(this.getClass().getSimpleName(), "activeOrDeactiveSystem()", " Enterprise Site " + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1, e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			Thread.dumpStack();
			logger.error(this.getClass().getSimpleName(), "activeOrDeactiveSystem()", " Enterprise System" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method creation of user ::" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(this.getClass().getSimpleName(), "activeOrDeactiveSystem()",
				"Response in creation of user ====== Success");
		return new ResponseEntity<EnterpriseSystemStatusRepsonse>(response,
				EnterpriseSiteUtils.getHeadersForPostAPI(), HttpStatus.OK);

	}

	@ApiOperation(value = "active Or Deactive Enterprise Site ", notes = "active Or Deactive Enterprise Site ", httpMethod = "POST")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal Server error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "/esite/{enterpriseSiteId}/status/{status}", method = RequestMethod.POST)
	public ResponseEntity<?> activeOrDeactiveSite(
			@ApiParam(name = "enterpriseSiteId", required = true, value = "String") @PathVariable String enterpriseSiteId,
			@ApiParam(name = "status", required = true, value = "String") @PathVariable String status) {
		EnterpriseSiteStatusRepsonse response = null;

		if (StringUtils.isBlank(enterpriseSiteId) || StringUtils.isBlank(status)) {
			logger.debug(this.getClass().getSimpleName(), "addEnterpriseSite",
					"Invalid Inputs to creat an EnterpriseSite");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid Inputs to creat an EnterpriseSystem");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}
		try {

			response = enterpriseSystemDAOBean.activeOrDectiveSite(enterpriseSiteId, status);
			logger.debug(this.getClass().getSimpleName(), "addEnterpriseSite",
					"enterprise system id: " + response + " enterprise site name:" + enterpriseSiteId);
			if (response.getEnterpriseSiteId() == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No Enterprise Data Found ");
				logger.debug(this.getClass().getSimpleName(), "addEnterpriseSite",
						"Response ===== No Enterprise Data Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}
		} catch (InvalidInputException e) {
			logger.error(this.getClass().getSimpleName(), "addEnterpriseSite()", " Enterprise Site " + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1, e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			Thread.dumpStack();
			logger.error(this.getClass().getSimpleName(), "addEnterpriseSite()", " Enterprise System" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method activate or deactivate ::" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(this.getClass().getSimpleName(), "addEnterpriseSite()",
				"Response in creation of user ====== Success");
		return new ResponseEntity<EnterpriseSiteStatusRepsonse>(response,
				EnterpriseSiteUtils.getHeadersForPostAPI(), HttpStatus.OK);

	}

	@ApiOperation(value = "insert operations ", notes = "insert operations ", httpMethod = "POST")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal Server error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "/operations", method = RequestMethod.POST)
	public ResponseEntity<?> insertOperations(@ApiParam @RequestBody List<OperationsDTO> requestDTO) {

		OperationsInputResponseDTO response = null;

		if (requestDTO==null || requestDTO.size()<1) {
			logger.debug(this.getClass().getSimpleName(), "addEnterpriseSite",
					"Invalid Inputs to creat an EnterpriseSite");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid Inputs to creat an EnterpriseSystem");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}
		try {

			response = enterpriseSystemDAOBean.insertOperations(requestDTO);
			logger.debug(this.getClass().getSimpleName(), "addEnterpriseSite",
					"enterprise system id: " + response + " Response : No Enterprise Data Found");
			if (response == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No Enterprise Data Found ");
				logger.debug(this.getClass().getSimpleName(), "addEnterpriseSite",
						"Response ===== No Enterprise Data Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			Thread.dumpStack();
			logger.error(this.getClass().getSimpleName(), "addEnterpriseSite()", " Enterprise System" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method creation of user ::" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(this.getClass().getSimpleName(), "addEnterpriseSite()",
				"Response in creation of user ====== Success");
		return new ResponseEntity<OperationsInputResponseDTO>(response,EnterpriseSiteUtils.getHeadersForPostAPI(), HttpStatus.OK);

	}

	@ApiOperation(value = "insert Role ", notes = "insert Role ", httpMethod = "POST")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal Server error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "/role", method = RequestMethod.POST)
	public ResponseEntity<?> insertRole(@ApiParam @RequestBody RolesDTO requestDTO) {

		RolesDTO response = null;

		if (requestDTO==null) {
			logger.debug(this.getClass().getSimpleName(), "addEnterpriseSite",
					"Invalid Inputs to creat an EnterpriseSite");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid Inputs to creat an EnterpriseSystem");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}
		try {

			response = enterpriseSystemDAOBean.InsertRoles(requestDTO);
			logger.debug(this.getClass().getSimpleName(), "addEnterpriseSite",
					"enterprise system id: " + response + " Response : No Enterprise Data Found");
			if (response == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No Enterprise Data Found ");
				logger.debug(this.getClass().getSimpleName(), "addEnterpriseSite",
						"Response ===== No Enterprise Data Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			Thread.dumpStack();
			logger.error(this.getClass().getSimpleName(), "addEnterpriseSite()", " Enterprise System" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method creation of user ::" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(this.getClass().getSimpleName(), "addEnterpriseSite()",
				"Response in creation of user ====== Success");
		return new ResponseEntity<RolesDTO>(response,EnterpriseSiteUtils.getHeadersForPostAPI(), HttpStatus.OK);

	}

	@ApiOperation(value = "create an Enterprise Site ", notes = "create an Enterprise Site ", httpMethod = "POST")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal Server error"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 201, message = "Created") })
	@RequestMapping(value = "/{eSystemId}/esite", method = RequestMethod.POST)
	public ResponseEntity<?> addEnterpriseSite(
			@ApiParam(name = "eSystemId", required = true, value = "String") @PathVariable String eSystemId,
			@ApiParam @RequestBody EnterpriseSiteAdditionRequestDTO requestDTO) {
		EnterpriseSiteAdditionResponseDTO response = null;

		if (StringUtils.isBlank(requestDTO.geteSiteId()) || StringUtils.isBlank(requestDTO.geteSiteId())
				|| requestDTO == null) {
			logger.debug(this.getClass().getSimpleName(), "addEnterpriseSite",
					"Invalid Inputs to creat an EnterpriseSite");
			ErrorResponse errResponse = new ErrorResponse(1, "Failure ---- Invalid Inputs to creat an EnterpriseSite");
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.NOT_FOUND);
		}
		try {

			response = enterpriseSystemDAOBean.addEnterpriseSite(eSystemId, requestDTO);
			logger.debug(this.getClass().getSimpleName(), "addEnterpriseSite",
					"enterprise system id: " + response + " enterprise system name:" + eSystemId);
			if (response.geteSiteId() == null || response.geteSiteName() == null) {
				ErrorResponse errResponse = new ErrorResponse(1, "Failure --- No Enterprise Data Found ");
				logger.debug(this.getClass().getSimpleName(), "addEnterpriseSite",
						"Response ===== No Enterprise Data Found!! ");
				return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
						HttpStatus.NO_CONTENT);
			}
		} catch (InvalidInputException e) {
			logger.error(this.getClass().getSimpleName(), "addEnterpriseSite()", " Enterprise Site " + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1, e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			Thread.dumpStack();
			logger.error(this.getClass().getSimpleName(), "addEnterpriseSite()", " Enterprise System" + e.getMessage());
			ErrorResponse errResponse = new ErrorResponse(1,
					"Failure ---- Inside the method creation of user ::" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(errResponse, EnterpriseSiteUtils.getHeadersForGetAPI(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(this.getClass().getSimpleName(), "addEnterpriseSite()",
				"Response in creation of user ====== Success");
		return new ResponseEntity<EnterpriseSiteAdditionResponseDTO>(response,
				EnterpriseSiteUtils.getHeadersForPostAPI(), HttpStatus.OK);
	}

}
