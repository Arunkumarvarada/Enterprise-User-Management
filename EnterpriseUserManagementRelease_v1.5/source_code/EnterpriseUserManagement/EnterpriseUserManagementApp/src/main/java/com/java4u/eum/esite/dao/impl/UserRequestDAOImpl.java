package com.altiux.eum.esite.dao.impl;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.altiux.commons.exceptions.InvalidInputException;
import com.altiux.logger.App_logger;
import com.altiux.logger.EModuleName;
import com.altiux.logger.LoggerFactory;
import com.altiux.eum.entities.EnterpriseSystemUser;
import com.altiux.eum.entities.EnterpriseSite;
import com.altiux.eum.entities.UserRequest;
import com.altiux.eum.util.EnterpriseSystemInputValidator;
import com.altiux.eum.esite.dao.UserRequestDAO;
import com.altiux.eum.esite.dto.UserRequestDTO;
import com.altiux.eum.esite.dto.UserRequestDetailsDTO;

public class UserRequestDAOImpl implements UserRequestDAO {

	private static final App_logger LOGGER = LoggerFactory.getLogger(EModuleName.DATASERVICE);

	private EnterpriseSystemInputValidator enterpriseInputValidator;

	@Override
	public String addUserRequest(UserRequestDTO requestDTO, String enterpriseSiteId) throws InvalidInputException {
		Session session = null;
		EnterpriseSystemUser pcUser = null;
		EnterpriseSite enterpriseSite = null;
		UserRequest request = null;
		session = enterpriseInputValidator.getSessionFactory().openSession();
		if (!isEnterpriseSiteExists(enterpriseSiteId, session)) {
			throw new InvalidInputException("Enterprise Site does not exists with id :  " + enterpriseSiteId);
		}

		try {
			session.beginTransaction();
			pcUser = (EnterpriseSystemUser) session.get(EnterpriseSystemUser.class, requestDTO.getRequesterUserId());
			enterpriseSite = (EnterpriseSite) session.get(EnterpriseSite.class, enterpriseSiteId);

			if (pcUser != null && enterpriseSite != null) {
				request = new UserRequest();
				request.setOperationType(requestDTO.getOperationType());
				request.setEnterpriseSystemUser(pcUser);
				request.setEnterpriseSite(enterpriseSite);
				request.setRequestState("PENDING");
				request.setOperatedUserId(requestDTO.getOperatedUserId());
				request.setCreatedTime(new Timestamp(System.currentTimeMillis()));
				session.save(request);
				session.getTransaction().commit();
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass().getSimpleName(), "addUserRequest", "Exception e " + e.getMessage());
			return null;
		} finally {
			session.close();
		}

		if (pcUser == null || request == null) {
			throw new InvalidInputException("Requester Id not valid");
		}
		return request.getRequestId();
	}

	@Override
	public boolean deleteUserRequest(String enterpriseSiteId, String requestId) throws InvalidInputException {
		Session session = null;
		UserRequest request = null;
		boolean invalidState = false;
		session = enterpriseInputValidator.getSessionFactory().openSession();
		if (!isEnterpriseSiteExists(enterpriseSiteId, session)) {
			throw new InvalidInputException("Enterprise Site does not exists with id :  " + enterpriseSiteId);
		}
		if (!isRequestExists(requestId, session)) {
			throw new InvalidInputException("User Request does not exists with id :  " + requestId);
		}
		try {
			session.beginTransaction();
			request = (UserRequest) session.get(UserRequest.class, requestId);
			if (request != null) {
				if (request.getRequestState().equalsIgnoreCase("PENDING")
						&& request.getEnterpriseSite().geteSiteId().equalsIgnoreCase(enterpriseSiteId)) {
					request.setRequestState("CANCELLED");
					request.setCompletedTime(new Timestamp(System.currentTimeMillis()));
				} else {
					invalidState = true;
				}
				session.getTransaction().commit();
			}
		} catch (Exception e) {
			LOGGER.debug(this.getClass().getSimpleName(), "addUserRequest", "Exception e " + e.getMessage());
			return false;
		} finally {
			session.close();
		}

		if (request == null || invalidState) {
			throw new InvalidInputException("Input data not valid");
		}
		return true;

	}

	@Override
	public List<UserRequestDetailsDTO> getPendingRequests(String EnterpriseSiteId, String type) throws InvalidInputException {
		Session session = null;
		List<UserRequestDetailsDTO> response = new ArrayList<UserRequestDetailsDTO>();
		session = enterpriseInputValidator.getSessionFactory().openSession();
		if (!isEnterpriseSiteExists(EnterpriseSiteId, session)) {
			throw new InvalidInputException("Enterprise Site does not exists with id :  " + EnterpriseSiteId);
		}
		try {
			session.beginTransaction();
			Query query = null;
			if ("Pending".equalsIgnoreCase(type)) {
				query = session.createQuery(
						"from UserRequest ur where ur.enterpriseSite.eSiteId=:eSiteId and ur.requestState='Pending'");
			} else {
				query = session.createQuery("from UserRequest ur where ur.enterpriseSite.eSiteId=:eSiteId");
			}
			query.setParameter("eSiteId", EnterpriseSiteId);
			List<UserRequest> requests = query.list();
			if (requests != null && !requests.isEmpty()) {
				requests.forEach(request -> {
					
					UserRequestDetailsDTO dto = new UserRequestDetailsDTO();
					LOGGER.debug(this.getClass().getSimpleName(), "getPendingRequests", "request.getOperationType() "+request.getOperationType());

					dto.setOperationType(request.getOperationType());
					dto.setEnterpriseSiteId(EnterpriseSiteId);
					dto.setRequestId(request.getRequestId());
					if ("Pending".equalsIgnoreCase(type)) {
						dto.setState("Pending");
					} else {
						dto.setState(request.getRequestState());
					}
					LOGGER.debug(this.getClass().getSimpleName(), "getPendingRequests", "request.getRequestState()="+request.getRequestState() );

					if(request.getCreatedTime()!=null) {
						dto.setCreatedTime(request.getCreatedTime().toString());
					}
					response.add(dto);
				});
			}
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.debug(this.getClass().getSimpleName(), "getPendingRequests", "Exception e " + e.getMessage());
			LOGGER.debug(this.getClass().getSimpleName(), "getPendingRequests", "Exception e " + e.getLocalizedMessage());
			return null;
		} finally {
			session.close();
		}

		return response;
	}

	private boolean isEnterpriseSiteExists(String EnterpriseSiteId, Session session) {
		if (EnterpriseSiteId != null) {
			EnterpriseSite EnterpriseSite = (EnterpriseSite) session.get(EnterpriseSite.class, EnterpriseSiteId);
			if (EnterpriseSite.geteSiteId() != null) {
				return true;
			}
		}
		return false;
	}

	private boolean isRequestExists(String requestId, Session session) {
		if (requestId != null) {
			UserRequest EnterpriseSite = (UserRequest) session.get(UserRequest.class, requestId);
			if (EnterpriseSite.getRequestId() != null) {
				return true;
			}
		}
		return false;
	}

	public void setEnterpriseInputValidator(final EnterpriseSystemInputValidator enterpriseInputValidator) {
		this.enterpriseInputValidator = enterpriseInputValidator;
	}
	
	
}
