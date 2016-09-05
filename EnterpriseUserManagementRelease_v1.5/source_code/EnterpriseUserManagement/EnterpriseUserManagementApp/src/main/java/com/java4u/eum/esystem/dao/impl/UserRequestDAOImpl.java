package com.altiux.eum.esystem.dao.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import com.altiux.commons.exceptions.InvalidInputException;
import com.altiux.logger.App_logger;
import com.altiux.logger.EModuleName;
import com.altiux.logger.LoggerFactory;
import com.altiux.eum.entities.EnterpriseSite;
import com.altiux.eum.entities.EnterpriseSystem;
import com.altiux.eum.entities.EnterpriseSystemUser;
import com.altiux.eum.entities.UserRequest;
import com.altiux.eum.esystem.dao.UserRequestDAO;
import com.altiux.eum.esystem.dto.UserRequestDetailsDTO;
import com.altiux.eum.util.EnterpriseSystemInputValidator;

public class UserRequestDAOImpl implements UserRequestDAO {

	private static final App_logger logger = LoggerFactory.getLogger(EModuleName.CONTROLLER);

	private EnterpriseSystemInputValidator enterpriseSystemInputValidator;

	public void setParkingSiteInputValidator(EnterpriseSystemInputValidator enterpriseSystemInputValidator) {
		this.enterpriseSystemInputValidator = enterpriseSystemInputValidator;
	}

	@Override
	public boolean rejectUserRequest(String parkingCompanyId, String requestId) throws InvalidInputException {
		Session session = null;
		UserRequest request = null;
		boolean invalidState = false;
		session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		if (!isRequestExists(requestId, session)) {
			throw new InvalidInputException("request id does not exists with the id: " + requestId);
		}

		if (!isEnterpriseSystemExists(parkingCompanyId, session)) {
			throw new InvalidInputException("Parking Company Doesnt exist" + requestId);
		}

		try {
			session.beginTransaction();
			request = (UserRequest) session.get(UserRequest.class, requestId);
			if (request != null) {
				if (request.getRequestState().equalsIgnoreCase("PENDING")) {
					request.setRequestState("REJECTED");
					request.setCompletedTime(new Timestamp(System.currentTimeMillis()));
				} else {
					invalidState = true;
				}
				session.getTransaction().commit();
			}

		} catch (Exception e) {
			logger.debug(this.getClass().getSimpleName(), "addUserRequest", "Exception e " + e.getMessage());
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
	@Transactional
	public List<UserRequestDetailsDTO> getPendingRequests(String enterpriseSystemId) throws InvalidInputException {
		Session session = null;
		List<UserRequestDetailsDTO> response = new ArrayList<UserRequestDetailsDTO>();
		session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		if (!isEnterpriseSystemExists(enterpriseSystemId, session)) {
			throw new InvalidInputException("Enterprise System does not exists with id :  " + enterpriseSystemId);
		}
		try {
			session.beginTransaction();
			List<String> parkingSpaces = getAllEnterpriseSiteIds(session, enterpriseSystemId);
			if (parkingSpaces.size() > 0 && !parkingSpaces.isEmpty()) {
				for (String parkingSpaceId : parkingSpaces) {
					List<UserRequestDetailsDTO> result = getUserRequestDetailsBasedOnPS(session, parkingSpaceId);
					response.addAll(result);
				}
			}
			session.getTransaction().commit();

		} catch (Exception e) {
			logger.debug(this.getClass().getSimpleName(), "addUserRequest", "Exception e " + e.getMessage());
			return null;
		} finally {
			session.close();
		}

		return response;
	}

	private List<UserRequestDetailsDTO> getUserRequestDetailsBasedOnPS(Session session, String enterpriseSiteId) {
		List<UserRequestDetailsDTO> response = new ArrayList<UserRequestDetailsDTO>();
		try {

			Query query = session.createQuery(
					"from UserRequest ur where ur.enterpriseSite.eSiteId=:eSiteId and ur.requestState='Pending'");
			query.setParameter("eSiteId", enterpriseSiteId);
			List<UserRequest> requests = query.list();
			if (requests != null && !requests.isEmpty()) {
				requests.forEach(request -> {
					UserRequestDetailsDTO dto = new UserRequestDetailsDTO();
					dto.setOperationType(request.getOperationType());
					dto.setEnterpriseSiteId(enterpriseSiteId);
					dto.setRequestId(request.getRequestId());
					dto.setState("PENDING");
					dto.setOperatedUserId(request.getOperatedUserId());
					dto.setRequesterUserId(request.getEnterpriseSystemUser().getUserId());
					dto.setCreatedTime(getStringFormatTimeStamp(request.getCreatedTime()));
					response.add(dto);
				});
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	private String getStringFormatTimeStamp(Timestamp created) {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").format(created);
		return timeStamp;
	}

	private List<String> getAllEnterpriseSiteIds(Session session, String enterpriseSystemId) {
		List<String> esites = new ArrayList<String>();
		EnterpriseSystem enterpriseSystem = (EnterpriseSystem) session.get(EnterpriseSystem.class, enterpriseSystemId);
		if (enterpriseSystem != null) {
			List<EnterpriseSite> esiteList = enterpriseSystem.geteSites();
			for (EnterpriseSite eSite : esiteList) {
				esites.add(eSite.geteSiteId());
			}
		}
		return esites;
	}

	@Override
	@Transactional
	public String addUserRequest(String enterpriseSystemId,String enterpriseSiteId) throws InvalidInputException {
		Session session = null;
		EnterpriseSystemUser systemUser = null;
		EnterpriseSite enterpriseSite = null;
		UserRequest request = null;
		session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		if (!isEnterpriseSystemExists(enterpriseSystemId, session)) {
			throw new InvalidInputException("Enterprise System doesnot exists with id :  " + enterpriseSiteId);
		}
		if (!isEnterpriseSiteExists(enterpriseSiteId, session)) {
			throw new InvalidInputException("Enterprise Site doesnot exists with id :  " + enterpriseSiteId);
		}
		try {

			session.beginTransaction();
			systemUser = (EnterpriseSystemUser) session.get(EnterpriseSystemUser.class, "USR00000001");
			enterpriseSite = (EnterpriseSite) session.get(EnterpriseSite.class, enterpriseSiteId);

			if (systemUser != null && enterpriseSite != null) {
				request = new UserRequest();
				request.setOperationType("ADD");
				request.setEnterpriseSystemUser(systemUser);
				request.setRequestState("PENDING");
				request.setCreatedTime(new Timestamp(System.currentTimeMillis()));
				request.setCompletedTime(new Timestamp(System.currentTimeMillis()));
				request.setEnterpriseSite(enterpriseSite);
				request.setOperatedUserId("");
				session.save(request);
				session.getTransaction().commit();
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(this.getClass().getSimpleName(), "addUserRequest", "Exception e " + e.getMessage());
			return null;
		} finally {
			session.close();
		}

		if (systemUser == null || request == null) {
			throw new InvalidInputException("Requester Id not valid");
		}
		return request.getRequestId();
	}

	private boolean isEnterpriseSystemExists(String enterpriseSystemId, Session session) {
		if (enterpriseSystemId != null) {
			EnterpriseSystem enterpriseSystem = (EnterpriseSystem) session.get(EnterpriseSystem.class,
					enterpriseSystemId);
			if (enterpriseSystem.geteSystemId() != null) {
				return true;
			}
		}
		return false;
	}

	private boolean isEnterpriseSiteExists(String eSiteId, Session session) {
		if (eSiteId != null) {
			EnterpriseSite eSite = (EnterpriseSite) session.get(EnterpriseSite.class, eSiteId);
			if (eSite.geteSiteId() != null) {
				return true;
			}
		}
		return false;
	}

	private boolean isRequestExists(String requestId, Session session) {
		if (requestId != null) {
			UserRequest parkingSpace = (UserRequest) session.get(UserRequest.class, requestId);
			if (parkingSpace.getRequestId() != null) {
				return true;
			}
		}
		return false;
	}
}
