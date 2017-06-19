package com.java4u.eum.esystem.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;

import com.java4u.logger.App_logger;
import com.java4u.logger.EModuleName;
import com.java4u.logger.LoggerFactory;
import com.java4u.eum.entities.EnterpriseSystemUser;
import com.java4u.eum.entities.Operations;
import com.java4u.eum.entities.User;
import com.java4u.eum.esystem.dto.OperationsDTO;
import com.java4u.eum.esystem.dao.EnterpriseSystemLoginDAO;
import com.java4u.eum.esystem.dto.LoginControllerRequestDTO;
import com.java4u.eum.esystem.dto.LoginControllerResponseDTO;
import com.java4u.eum.esystem.dto.LoginWithRolesRequestDTO;
import com.java4u.eum.esystem.dto.LoginWithRolesResponseDTO;
import com.java4u.eum.util.EnterpriseSystemInputValidator;

public class EnterpriseSystemLoginDAOImpl implements EnterpriseSystemLoginDAO {

	private EnterpriseSystemInputValidator enterpriseSystemInputValidator;

	private static final App_logger LOGGER = LoggerFactory.getLogger(EModuleName.DATASERVICE);

	@Override
	public LoginControllerResponseDTO login(LoginControllerRequestDTO requestDTO) {

		LoginControllerResponseDTO response = null;
		Session session = null;
		try {
			session = enterpriseSystemInputValidator.getSessionFactory().openSession();
			session.beginTransaction();

			String userName = requestDTO.getUserName();
			String password = requestDTO.getPassword();
			Boolean active = true;

			Query preQuery = session.createQuery(
					"from EnterpriseSystemUser u where u.userName=:userName and u.eSystem.eSystemId=:eSystemId");
			preQuery.setParameter("userName", userName);
			preQuery.setParameter("eSystemId", requestDTO.getEnterpriseSystemId());

			EnterpriseSystemUser preUser = (EnterpriseSystemUser) preQuery.uniqueResult();
			if (preUser != null) {

				if (preUser.getUserName().equals(userName)) {

					Query query = session.createQuery(
							"from EnterpriseSystemUser u where u.userName=:userName and u.password=:password and u.eSystem.eSystemId=:eSystemId");
					query.setParameter("userName", userName);
					query.setParameter("password", password);
					query.setParameter("eSystemId", requestDTO.getEnterpriseSystemId());

					EnterpriseSystemUser user = (EnterpriseSystemUser) query.uniqueResult();

					if (user != null) {
						if (user.getPassword().equals(password)) {
							String userId = user.getUserId();
							EnterpriseSystemUser pcUser = (EnterpriseSystemUser) session.get(EnterpriseSystemUser.class,
									userId);

							response = getUserDetails(pcUser);
							return response;
						} else {
							response = getInValidUserDetails("Invalid Password", HttpStatus.OK);
							return response;
						}
					} else {
						response = getInValidUserDetails("Invalid Password", HttpStatus.OK);
						return response;
					}
				} else {
					response = getInValidUserDetails("Invalid User", HttpStatus.NOT_FOUND);
					return response;
				}
			} else {
				response = getInValidUserDetails("Invalid User", HttpStatus.NOT_FOUND);
				return response;
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}

	}

	private LoginControllerResponseDTO getInValidUserDetails(String msg, HttpStatus httpStatus) {
		LoginControllerResponseDTO response = new LoginControllerResponseDTO();

		response.setEnterpriseSystemId(null);
		response.setEnterpriseSiteId(null);
		response.setUserId(null);
		if (httpStatus == HttpStatus.OK)
			response.setCode(6001);
		else if (httpStatus == HttpStatus.NOT_FOUND)
			response.setCode(6002);
		response.setMsg(msg);
		response.setResponseCode(httpStatus);

		return response;
	}

	private LoginControllerResponseDTO getUserDetails(EnterpriseSystemUser pcUser) {

		LoginControllerResponseDTO response = new LoginControllerResponseDTO();

		response.setUserId(pcUser.getUserId());
		response.setEnterpriseSystemId(pcUser.geteSystem().geteSystemId());
		// response.setPsId(pcUser.getpCompany().getPsProviderId());
		response.setCode(6000);
		response.setMsg("Valid User");
		response.setResponseCode(HttpStatus.OK);

		return response;

	}

	public void setParkingCompanyInputValidator(EnterpriseSystemInputValidator enterpriseSystemInputValidator) {
		this.enterpriseSystemInputValidator = enterpriseSystemInputValidator;
	}

	public LoginWithRolesResponseDTO loginWithRolesResponse(LoginWithRolesRequestDTO requestDTO) {
		LoginWithRolesResponseDTO response = new LoginWithRolesResponseDTO();
		List<OperationsDTO> operationsList = null;
		Session session = null;
		try {
			session = enterpriseSystemInputValidator.getSessionFactory().openSession();
			session.beginTransaction();

			String userName = requestDTO.getUserName();
			String password = requestDTO.getPassword();

			Query preQuery = session.createQuery(
					"from EnterpriseSystemUser u where u.userName=:userName and u.eSystem.eSystemId=:eSystemId");
			preQuery.setParameter("userName", userName);
			preQuery.setParameter("eSystemId", requestDTO.getEnterpriseSystemId());

			EnterpriseSystemUser preUser = (EnterpriseSystemUser) preQuery.uniqueResult();
			if (preUser != null) {

				if (preUser.getUserName().equals(userName)) {

					Query query = session.createQuery(
							"from EnterpriseSystemUser u where u.userName=:userName and u.password=:password and u.eSystem.eSystemId=:eSystemId");
					query.setParameter("userName", userName);
					query.setParameter("password", password);
					query.setParameter("eSystemId", requestDTO.getEnterpriseSystemId());

					EnterpriseSystemUser user = (EnterpriseSystemUser) query.uniqueResult();

					if (user != null) {
						if (user.getPassword().equals(password)) {
							String userId = user.getUserId();
							EnterpriseSystemUser pcUser = (EnterpriseSystemUser) session.get(EnterpriseSystemUser.class,
									userId);
							// to get Roles of a user
							Criteria cr = session.createCriteria(User.class);
							cr.add(Restrictions.eq("userId", userId));
							User userResponse = (User) cr.uniqueResult();
							LOGGER.debug(this.getClass().getSimpleName(), "getRolesOfUser()", "user = " + userResponse);

							if (userResponse != null) {
								List<String> permittedOperationList = userResponse.getPermittedOperations();
								permittedOperationList = new ArrayList(new LinkedHashSet(permittedOperationList));
								List<Operations> operations = getOperationsFromDB(session);
								operationsList = getUpdatedResponse(permittedOperationList, operations);
								response.setAllowedOperations(operationsList);
								response.setRoleId(userResponse.getRoles().getRoleId());
								response.setRoleName(userResponse.getRole());
								response.setUserId(userId);
								response.setCode(6000);
								response.setMsg("Valid User");
								response.setResponseCode(HttpStatus.OK);
							}
							return response;
						} else {
							response = getInValidUserWithRolesDetails("Invalid Password", HttpStatus.OK);
							return response;
						}
					} else {
						response = getInValidUserWithRolesDetails("Invalid Password", HttpStatus.OK);
						return response;
					}
				} else {
					response = getInValidUserWithRolesDetails("Invalid User", HttpStatus.NOT_FOUND);
					return response;
				}
			} else {
				response = getInValidUserWithRolesDetails("Invalid User", HttpStatus.NOT_FOUND);
				return response;
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}

	}

	private LoginWithRolesResponseDTO getInValidUserWithRolesDetails(String msg, HttpStatus httpStatus){
		LoginWithRolesResponseDTO response= new LoginWithRolesResponseDTO();
		response.setAllowedOperations(null);
		response.setRoleId(null);
		response.setRoleName(null);
		response.setUserId(null);
		if (httpStatus == HttpStatus.OK)
			response.setCode(6001);
		else if (httpStatus == HttpStatus.NOT_FOUND)
			response.setCode(6002);
		response.setMsg(msg);
		response.setResponseCode(httpStatus);
		return response;
	}

	@SuppressWarnings("unchecked")
	private List<Operations> getOperationsFromDB(Session session) {
		List<Operations> operationList = null;
		try {
			Criteria cr = session.createCriteria(Operations.class);
			operationList = (List<Operations>) cr.list();
			if(operationList!=null){
				return operationList;
			}
			return operationList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<OperationsDTO> getUpdatedResponse(List<String> permittedOperationList,
			List<Operations> operationsList) {
		List<OperationsDTO> responseList = new ArrayList<>();
		List<Operations> newList = new ArrayList<Operations>();
		for (String str : permittedOperationList) {
			int index = convertStringtoInteger(str);
			Operations operation = operationsList.get(index);
			newList.add(operation);
		}
		responseList = convertToOperationsDTO(newList);
		responseList = new ArrayList(new LinkedHashSet(responseList));
		System.out.println(responseList.toString());
		return responseList;
	}

	private int convertStringtoInteger(String str) {
		str = str.replaceAll("\\D+", "");
		int i = Integer.parseInt(str);
		return i - 1;
	}

	private List<OperationsDTO> convertToOperationsDTO(List<Operations> operations) {
		List<OperationsDTO> operationsListDTO = new ArrayList<>();
		for (Operations operation : operations) {
			OperationsDTO dto = new OperationsDTO();
			dto.setExecutePermission(operation.getExecutePermission());
			dto.setOperationId(operation.getOperationId());
			dto.setModuleOperationName(operation.getModuleOperationName());
			dto.setReadPermission(operation.getReadPermission());
			dto.setWritePermission(operation.getWritePermission());
			operationsListDTO.add(dto);
		}
		return operationsListDTO;
	}

}
