package com.java4u.eum.esystem.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.java4u.commons.exceptions.InvalidInputException;
import com.java4u.eum.dto.UserAdditionResponseDTO;
import com.java4u.eum.entities.Address;
import com.java4u.eum.entities.Email;
import com.java4u.eum.entities.EnterpriseSite;
import com.java4u.eum.entities.EnterpriseSiteAddress;
import com.java4u.eum.entities.EnterpriseSiteContext;
import com.java4u.eum.entities.EnterpriseSystem;
import com.java4u.eum.entities.EnterpriseSystemUser;
import com.java4u.eum.entities.Facility;
import com.java4u.eum.entities.GeoLocation;
import com.java4u.eum.entities.Operations;
import com.java4u.eum.entities.PhoneNumber;
import com.java4u.eum.entities.Photo;
import com.java4u.eum.entities.Roles;
import com.java4u.eum.entities.SiteUser;
import com.java4u.eum.entities.Specifics;
import com.java4u.eum.entities.User;
import com.java4u.eum.entities.UserRequest;
import com.java4u.eum.esystem.dao.EnterpriseSystemDAO;
import com.java4u.eum.esystem.dto.AddressDTO;
import com.java4u.eum.esystem.dto.EmailDTO;
import com.java4u.eum.esystem.dto.EnterpriseSiteAdditionRequestDTO;
import com.java4u.eum.esystem.dto.EnterpriseSiteAdditionResponseDTO;
import com.java4u.eum.esystem.dto.EnterpriseSiteAddressDTO;
import com.java4u.eum.esystem.dto.EnterpriseSiteContextInputDTO;
import com.java4u.eum.esystem.dto.EnterpriseSiteStatusRepsonse;
import com.java4u.eum.esystem.dto.EnterpriseSystemAdditionRequestDTO;
import com.java4u.eum.esystem.dto.EnterpriseSystemAdditionResponseDTO;
import com.java4u.eum.esystem.dto.EnterpriseSystemStatusRepsonse;
import com.java4u.eum.esystem.dto.FacilityDTO;
import com.java4u.eum.esystem.dto.GeoLocationDTO;
import com.java4u.eum.esystem.dto.OperationsDTO;
import com.java4u.eum.esystem.dto.OperationsInputResponseDTO;
import com.java4u.eum.esystem.dto.PhoneNumberDTO;
import com.java4u.eum.esystem.dto.PhotoDTO;
import com.java4u.eum.esystem.dto.RolesDTO;
import com.java4u.eum.esystem.dto.SiteUserAdditionRequestDTO;
import com.java4u.eum.esystem.dto.SiteUserAdditionResponseDTO;
import com.java4u.eum.esystem.dto.SpecificsDTO;
import com.java4u.eum.esystem.dto.UserAdditionRequestDTO;
import com.java4u.eum.util.EnterpriseSystemInputValidator;
import com.java4u.logger.App_logger;
import com.java4u.logger.EModuleName;
import com.java4u.logger.LoggerFactory;

import antlr.StringUtils;

public class EnterpriseSystemDAOImpl implements EnterpriseSystemDAO {

	private static final App_logger logger = LoggerFactory.getLogger(EModuleName.CONTROLLER);

	private EnterpriseSystemInputValidator enterpriseSystemInputValidator;

	public void setParkingSiteInputValidator(EnterpriseSystemInputValidator enterpriseSystemInputValidator) {
		this.enterpriseSystemInputValidator = enterpriseSystemInputValidator;
	}

	@Override
	public EnterpriseSystemAdditionResponseDTO addEnterpriseSystem(EnterpriseSystemAdditionRequestDTO dto) throws InvalidInputException{
		Session session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		if(isUserNameExists(session,dto.getUser().getUserName())){
			throw new InvalidInputException("User Already exists with the same userName"+ dto.getUser().getUserName());
		}
		try {
			session.beginTransaction();
			// Addition of object
			EnterpriseSystem system = new EnterpriseSystem();
			Address address = converAdressDTOtoAddress(dto.getAddress());
			if (address != null) {
				system.setAddress(address);
			}
			system.seteSystemId(dto.getEnterpriseSystemId());
			system.setCreated(getCurrentTimeStamp());
			system.setLastModified(getCurrentTimeStamp());
			system.setLogoUrl(dto.getLogoUrl());
			system.seteSystemName(dto.getEnterpriseSystemName());
			session.saveOrUpdate(system);

			// Add user
			EnterpriseSystemUser pcUser = new EnterpriseSystemUser();
			pcUser.setActive(dto.getUser().isActive());
			pcUser.setCreated(getCurrentTimeStamp());
			pcUser.setDisplayName(dto.getUser().getDisplayName());
			List<Email> emails = getEmails(dto.getUser().getEmails());
			List<PhoneNumber> phone = getPhoneNumbers(dto.getUser().getPhoneNumbers());
			List<Photo> pics = getPics(dto.getUser().getPics());
			pcUser.setEmails(emails);
			pcUser.setPhoneNumbers(phone);
			pcUser.setPics(pics);
			pcUser.setLastModifiedTime(getCurrentTimeStamp());
			pcUser.setFamilyName(dto.getUser().getFamilyName());
			pcUser.setFormattedName(dto.getUser().getFormattedName());
			pcUser.setGivenName(dto.getUser().getGivenName());
			pcUser.setHonorificPrifix(dto.getUser().getHonorificPrifix());
			pcUser.setMiddleName(dto.getUser().getMiddleName());
			pcUser.setNickName(dto.getUser().getNickName());
			pcUser.setPassword(dto.getUser().getPassword());
			pcUser.setUserName(dto.getUser().getUserName());
			pcUser.seteSystem(system);
			Roles role = getRole(session, "EnterpriseSuperAdmin");
			pcUser.setRole("EnterpriseSuperAdmin");
			pcUser.setRoles(role);
			List<Operations> operations = role.getOperations();
			System.out.println(" Size of Operations: " + operations.size());
			for (Operations operation : operations) {
				System.out.println(" Operations ID add:::  " + operation.getOperationId());
				pcUser.addPermittedOperations(operation.getOperationId());
			}
			session.save(pcUser);

			// creating new UserRequest
			UserRequest userRequest = new UserRequest();
			userRequest.setCompletedTime(getCurrentTimeStamp());
			userRequest.setOperatedUserId(pcUser.getUserId());
			userRequest.setEnterpriseSystemUser(pcUser);
			userRequest.setOperationType("ADD");
			userRequest.setRequestState("COMPLETED");
			userRequest.setCreatedTime(getCurrentTimeStamp());
			session.save(userRequest);
			System.out.println(pcUser.getUserId());
			EnterpriseSystemAdditionResponseDTO response = convertToEnterpriseSystemAdditionResponseDTO(
					system.geteSystemId(), pcUser.getUserId(), dto);
			session.getTransaction().commit();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		} finally {
			session.flush();
			session.close();
		}
	}

	private boolean isUserNameExists(Session session, String userName) {
		if(userName!=null){
			EnterpriseSystemUser user= (EnterpriseSystemUser) session.get(EnterpriseSystemUser.class, userName);
			if(user!=null){
				return true;
			}
		}
		return false;
	}

	private Roles getRole(Session session, String roleName) {
		try {
			Criteria cr = session.createCriteria(Roles.class);
			cr.add(Restrictions.eq("roleName", roleName));
			Roles role = (Roles) cr.uniqueResult();
			if (role != null) {
				return role;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<Email> getEmails(List<EmailDTO> list) {
		List<Email> response = new ArrayList<>();
		for (EmailDTO email : list) {
			Email emailResp = new Email();
			emailResp.setEmail(email.getEmail());
			emailResp.setType(email.getType());
			response.add(emailResp);
		}
		return response;
	}

	private List<PhoneNumber> getPhoneNumbers(List<PhoneNumberDTO> phoneNumberDto) {
		List<PhoneNumber> response = new ArrayList<>();
		for (PhoneNumberDTO phone : phoneNumberDto) {
			PhoneNumber phoneNumberResp = new PhoneNumber();
			phoneNumberResp.setPhoneNumber(phone.getPhoneNumber());
			phoneNumberResp.setType(phone.getType());
			response.add(phoneNumberResp);
		}
		return response;
	}

	private List<Photo> getPics(List<PhotoDTO> photoDto) {
		List<Photo> response = new ArrayList<>();
		for (PhotoDTO photo : photoDto) {
			Photo photoResp = new Photo();
			photoResp.setPhotoType(photo.getPhotoType());
			photoResp.setPhotoUrl(photo.getPhotoUrl());
			response.add(photoResp);
		}
		return response;
	}

	private EnterpriseSystemAdditionResponseDTO convertToEnterpriseSystemAdditionResponseDTO(String eSystemId,
			String userId, EnterpriseSystemAdditionRequestDTO dto) {
		EnterpriseSystemAdditionResponseDTO response = new EnterpriseSystemAdditionResponseDTO();
		response.setEnterpriseSystemName(dto.getEnterpriseSystemName());
		response.setEnterpriseSystemId(eSystemId);
		response.setAddress(dto.getAddress());
		response.setLogoUrl(dto.getLogoUrl());
		System.out.println(userId);
		UserAdditionResponseDTO user = convertUserAdditionResponseDTO(userId, dto.getUser());
		response.setUser(user);
		return response;
	}

	private UserAdditionResponseDTO convertUserAdditionResponseDTO(String userId, UserAdditionRequestDTO user) {
		UserAdditionResponseDTO response = new UserAdditionResponseDTO();
		response.setActive(user.isActive());
		response.setCreated(getCurrentTimeStamp().toString());
		response.setDisplayName(user.getDisplayName());
		response.setFamilyName(user.getFamilyName());
		response.setFormattedName(user.getFormattedName());
		response.setGivenName(user.getGivenName());
		response.setHonorificPrifix(user.getHonorificPrifix());
		response.setLastModifiedTime(getCurrentTimeStamp().toString());
		response.setMiddleName(user.getMiddleName());
		response.setNickName(user.getNickName());
		response.setPassword("");
		response.setRole(user.getRole());
		response.setUserId(userId);
		response.setUserName(user.getUserName());
		return response;
	}

	private Address converAdressDTOtoAddress(AddressDTO dto) {
		Address address = new Address();
		if (dto != null) {
			address.setCountry(dto.getCountry());
			address.setLocality(dto.getLocality());
			address.setPostalCode(dto.getPostalCode());
			address.setRegion(dto.getRegion());
			address.setStreetAddress(dto.getStreetAddress());
			return address;
		}
		return null;
	}

	private Timestamp getCurrentTimeStamp() {
		java.util.Date date = new java.util.Date();
		return new Timestamp(date.getTime());
	}

	public EnterpriseSiteAdditionResponseDTO addEnterpriseSiteWithAdmin(String systemId,
			EnterpriseSiteAdditionRequestDTO dto) throws InvalidInputException {
		EnterpriseSiteAdditionResponseDTO response = null;
		Session session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		if (!isEnterpriseSystemExists(systemId, session)) {
			throw new InvalidInputException("Enterprise System doesn't exists with id: " + systemId);
		}
		if(isUserNameExists(session,dto.getUser().getUserName()))
		{
			throw new InvalidInputException("User Name doesn't exists with name: " + dto.getUser().getUserName());
		}
		try {
			session.beginTransaction();
			EnterpriseSite site = new EnterpriseSite();
			site.seteSiteId(dto.geteSiteId());
			site.setActive(true);
			site.setCreated(getCurrentTimeStamp());
			EnterpriseSiteContext enterpriseSiteContext = convertToEnterpriseSiteContext(
					dto.getEnterpriseSiteContext());
			site.setEnterpriseSiteContext(enterpriseSiteContext);
			site.seteSiteName(dto.geteSiteName());
			EnterpriseSystem system = (EnterpriseSystem) session.get(EnterpriseSystem.class, systemId);
			site.seteSystem(system);
			system.addEnterpriseSite(site);
			site.setLastmodifiedTime(getCurrentTimeStamp());
			Specifics specifics = convertToSpecifics(dto.getSpecifics());
			site.setSpecifics(specifics);
			session.save(site);

			// Add user
			EnterpriseSystemUser pcUser = new EnterpriseSystemUser();
			pcUser.setActive(dto.getUser().isActive());
			pcUser.setCreated(getCurrentTimeStamp());
			pcUser.setDisplayName(dto.getUser().getDisplayName());
			List<Email> emails = getEmails(dto.getUser().getEmails());
			List<PhoneNumber> phone = getPhoneNumbers(dto.getUser().getPhoneNumbers());
			List<Photo> pics = getPics(dto.getUser().getPics());
			pcUser.setEmails(emails);
			pcUser.setPhoneNumbers(phone);
			pcUser.setPics(pics);
			pcUser.setLastModifiedTime(getCurrentTimeStamp());
			pcUser.setFamilyName(dto.getUser().getFamilyName());
			pcUser.setFormattedName(dto.getUser().getFormattedName());
			pcUser.setGivenName(dto.getUser().getGivenName());
			pcUser.setHonorificPrifix(dto.getUser().getHonorificPrifix());
			pcUser.setMiddleName(dto.getUser().getMiddleName());
			pcUser.setNickName(dto.getUser().getNickName());
			pcUser.setPassword(dto.getUser().getPassword());
			pcUser.setUserName(dto.getUser().getUserName());
			pcUser.seteSystem(system);
			Roles role = getRole(session, "SiteSuperAdmin");
			pcUser.setRole("SiteSuperAdmin");
			pcUser.setRoles(role);
			List<Operations> operations = role.getOperations();
			System.out.println(" Size of Operations: " + operations.size());
			for (Operations operation : operations) {
				System.out.println(" Operations ID add:::  " + operation.getOperationId());
				pcUser.addPermittedOperations(operation.getOperationId());
			}
			pcUser.addEnterpriseSite(site);
			site.addUser(pcUser);
			session.save(pcUser);

			// creating new UserRequest
			UserRequest userRequest = new UserRequest();
			userRequest.setCompletedTime(getCurrentTimeStamp());
			userRequest.setOperatedUserId(pcUser.getUserId());
			userRequest.setEnterpriseSystemUser(pcUser);
			userRequest.setOperationType("ADD");
			userRequest.setRequestState("COMPLETED");
			userRequest.setCreatedTime(getCurrentTimeStamp());
			session.save(userRequest);
			session.getTransaction().commit();
			response = convertToEnterpriseSiteAdditionResponseDTO(pcUser.getUserId(), dto);
			return response;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return null;
	}

	private EnterpriseSiteAdditionResponseDTO convertToEnterpriseSiteAdditionResponseDTO(String userId,
			EnterpriseSiteAdditionRequestDTO dto) {
		EnterpriseSiteAdditionResponseDTO response = new EnterpriseSiteAdditionResponseDTO();
		response.setEnterpriseSiteContext(dto.getEnterpriseSiteContext());
		response.seteSiteId(dto.geteSiteId());
		response.seteSiteName(dto.geteSiteName());
		response.setSpecifics(dto.getSpecifics());
		if(userId!=null && userId!=""){
		UserAdditionResponseDTO user = convertUserAdditionResponseDTO(userId, dto.getUser());
		response.setUser(user);
		}
		return response;
	}

	private Specifics convertToSpecifics(SpecificsDTO specifics) {
		Specifics specs = new Specifics();
		if (specifics != null) {
			specs.setDemarcated(specifics.getDemarcated());
			Facility facility = convertToFacility(specifics.getFacility());
			specs.setFacility(facility);
		}
		return specs;
	}

	private Facility convertToFacility(FacilityDTO facility) {
		Facility response = new Facility();
		if (facility != null) {
			EnterpriseSiteAddress address = convertToEnterpriseSiteAddress(facility.getAddress());
			response.setAddress(address);
			GeoLocation geoLocation = convertToGeoLocation(facility.getGeoLocation());
			response.setGeoLocation(geoLocation);
			response.setType(facility.getFacilityType());
		}
		return response;
	}

	private GeoLocation convertToGeoLocation(GeoLocationDTO geoLocation) {
		GeoLocation location = new GeoLocation();
		if (geoLocation != null) {
			location.setAltitude(geoLocation.getAltitude());
			location.setLaltitude(geoLocation.getLatitude());
			location.setLongitutde(geoLocation.getLongitude());
		}
		return location;
	}

	private EnterpriseSiteAddress convertToEnterpriseSiteAddress(EnterpriseSiteAddressDTO address) {
		EnterpriseSiteAddress response = new EnterpriseSiteAddress();
		if (address != null) {
			response.setLocality(address.getLocality());
			response.setPostalCode(address.getPostalCode());
			response.setStreetAddress(address.getStreetAddress());
		}
		return response;
	}

	private EnterpriseSiteContext convertToEnterpriseSiteContext(EnterpriseSiteContextInputDTO contextDto) {
		EnterpriseSiteContext context = new EnterpriseSiteContext();
		if (contextDto != null) {
			context.setCityCode(contextDto.getCityCode());
			context.setCountryCode(contextDto.getCountryCode());
			context.setGeoRegion(contextDto.getGeoRegionCode());
			context.setCity(contextDto.getCity());
			context.setCountry(contextDto.getCountry());
			context.setTimeZone(contextDto.getTimeZone());
			context.setStdCode(contextDto.getStdCode());
			context.setIsdCode(contextDto.getIsdCode());

		}
		return context;
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

	@Override
	public EnterpriseSiteStatusRepsonse activeOrDectiveSite(String siteId, String status) throws InvalidInputException {
		EnterpriseSiteStatusRepsonse response = new EnterpriseSiteStatusRepsonse();
		Session session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			if (!isEnterpriseSiteExists(siteId, session)) {
				throw new InvalidInputException("Enterprise Site doesn't exists with id: " + siteId);
			}
			System.out.println(status);
			System.out.println(!status.equalsIgnoreCase("ACTIVATE"));
			System.out.println(!status.equalsIgnoreCase("DEACTIVATE"));
			System.out.println(!status.equalsIgnoreCase("ACTIVATE") && !status.equalsIgnoreCase("DEACTIVATE"));

			if (!status.equalsIgnoreCase("ACTIVATE") && !status.equalsIgnoreCase("DEACTIVATE")) {
				throw new InvalidInputException("status input should either ACTIVATE or DEACTIVATE :" + status);
			}
			EnterpriseSite site = (EnterpriseSite) session.get(EnterpriseSite.class, siteId);
			if (status.equalsIgnoreCase("ACTIVATE")) {
				site.setActive(true);
			} else if (status.equalsIgnoreCase("DEACTIVATE")) {
				site.setActive(false);
			}
			session.saveOrUpdate(site);

			session.getTransaction().commit();
			EnterpriseSite site1 = (EnterpriseSite) session.get(EnterpriseSite.class, siteId);
			response.setActive(site1.getActive());
			response.setEnterpriseSiteId(siteId);
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.flush();
			session.close();
		}
		return response;
	}

	@Override
	public EnterpriseSystemStatusRepsonse activeOrDectiveSystem(String systemId, String status)
			throws InvalidInputException {
		EnterpriseSystemStatusRepsonse response = new EnterpriseSystemStatusRepsonse();
		Session session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			if (!isEnterpriseSystemExists(systemId, session)) {
				throw new InvalidInputException("Enterprise System doesn't exists with id: " + systemId);
			}
			System.out.println(status);
			System.out.println(!status.equalsIgnoreCase("ACTIVATE"));
			System.out.println(!status.equalsIgnoreCase("DEACTIVATE"));
			System.out.println(!status.equalsIgnoreCase("ACTIVATE") && !status.equalsIgnoreCase("DEACTIVATE"));

			if (!status.equalsIgnoreCase("ACTIVATE") && !status.equalsIgnoreCase("DEACTIVATE")) {
				throw new InvalidInputException("status input should either ACTIVATE or DEACTIVATE :" + status);
			}
			EnterpriseSystem system = (EnterpriseSystem) session.get(EnterpriseSystem.class, systemId);
			if (status.equalsIgnoreCase("ACTIVATE")) {
				system.setActive(true);
			} else if (status.equalsIgnoreCase("DEACTIVATE")) {
				system.setActive(false);
			}
			session.saveOrUpdate(system);
			session.getTransaction().commit();
			EnterpriseSystem system1 = (EnterpriseSystem) session.get(EnterpriseSystem.class, systemId);
			response.setActive(system1.getActive());
			response.setEnterpriseSystemId(systemId);
			return response;
		} catch (Exception e) {
			session.getTransaction().commit();
			e.printStackTrace();
			return null;
		} finally {
			session.flush();
			session.close();
		}
	}

	private boolean isEnterpriseSiteExists(String enterpriseSiteId, Session session) {
		if (enterpriseSiteId != null) {
			EnterpriseSite eSite = (EnterpriseSite) session.get(EnterpriseSite.class, enterpriseSiteId);
			if (eSite.geteSiteId() != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public OperationsInputResponseDTO insertOperations(List<OperationsDTO> operationsDto) {
		OperationsInputResponseDTO result = new OperationsInputResponseDTO();
		Session session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			List<Operations> operationList = null;
			for (OperationsDTO dto : operationsDto) {
				Operations operation = new Operations();
				operation.setExecutePermission(dto.getExecutePermission());
				operation.setModuleOperationName(dto.getModuleOperationName());
				operation.setReadPermission(dto.getReadPermission());
				operation.setWritePermission(dto.getWritePermission());
				session.persist(operation);
			}
			operationList = getOperationsFromDB(session);
			result.setTotalNumberOfOperationsInserted(operationList.size());
			result.setNoOfOperationsInserted(operationsDto.size());
			result.setInsertionStatus("SUCCESS in Insertion of Operations");
			session.beginTransaction().commit();
			return result;
		} catch (Exception e) {
			session.getTransaction().commit();
			e.printStackTrace();
			return null;
		} finally {
			session.flush();
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	private List<Operations> getOperationsFromDB(Session session) {
		List<Operations> operationList = null;
		try {
			Criteria cr = session.createCriteria(Operations.class);
			operationList = (List<Operations>) cr.list();
			System.out.println(operationList.toString());
			return operationList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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

	private List<Operations> convertToOperations(List<OperationsDTO> operations) {
		List<Operations> operationsListDTO = new ArrayList<>();
		for (OperationsDTO operation : operations) {
			Operations dto = new Operations();
			dto.setExecutePermission(operation.getExecutePermission());
			dto.setOperationId(operation.getOperationId());
			dto.setModuleOperationName(operation.getModuleOperationName());
			dto.setReadPermission(operation.getReadPermission());
			dto.setWritePermission(operation.getWritePermission());
			operationsListDTO.add(dto);
		}
		return operationsListDTO;
	}

	@Override
	public RolesDTO InsertRoles(RolesDTO dto) {
		RolesDTO response = null;
		Session session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Roles role = new Roles();
			role.setRoleName(dto.getRoleName());
			List<Operations> operations = convertToOperations(dto.getPermittedOperations());
			for (Operations operation : operations) {
				operation.addRole(role);
				session.save(operation);
			}
			role.setOperations(operations);
			session.save(role);
			System.out.println(role.getRoleId());
			session.getTransaction().commit();
			response = convertTORolesRepsonseDTO(role.getRoleId(), session);
			return response;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.flush();
			session.close();
		}
	}

	private RolesDTO convertTORolesRepsonseDTO(String roleId, Session session) {
		RolesDTO dto = new RolesDTO();
		Roles role = (Roles) session.get(Roles.class, roleId);
		dto.setRoleId(roleId);
		List<OperationsDTO> operations = convertToOperationsDTO(role.getOperations());
		dto.setPermittedOperations(operations);
		dto.setRoleName(role.getRoleName());
		return dto;
	}

	@Override
	public EnterpriseSiteAdditionResponseDTO addEnterpriseSite(String systemId, EnterpriseSiteAdditionRequestDTO dto)
			throws InvalidInputException {
		EnterpriseSiteAdditionResponseDTO response = null;
		Session session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			if (!isEnterpriseSystemExists(systemId, session)) {
				throw new InvalidInputException("Enterprise System doesn't exists with id: " + systemId);
			}
			EnterpriseSite site = new EnterpriseSite();
			site.seteSiteId(dto.geteSiteId());
			site.setActive(true);
			site.setCreated(getCurrentTimeStamp());
			EnterpriseSiteContext enterpriseSiteContext = convertToEnterpriseSiteContext(
					dto.getEnterpriseSiteContext());
			site.setEnterpriseSiteContext(enterpriseSiteContext);
			site.seteSiteName(dto.geteSiteName());
			EnterpriseSystem system = (EnterpriseSystem) session.get(EnterpriseSystem.class, systemId);
			site.seteSystem(system);
			system.addEnterpriseSite(site);
			site.setLastmodifiedTime(getCurrentTimeStamp());
			Specifics specifics = convertToSpecifics(dto.getSpecifics());
			site.setSpecifics(specifics);
			session.save(site);
			session.getTransaction().commit();
			response = convertToEnterpriseSiteAdditionResponseDTO("", dto);
			return response;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return null;
	}
	
	public SiteUserAdditionResponseDTO addSiteUser(String siteAdminId,String siteId, SiteUserAdditionRequestDTO dto) throws InvalidInputException {
		Session session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		if (!isEnterpriseSiteExists(siteId, session)) {
			throw new InvalidInputException("Enterprise Site doesn't exists with id: " + siteId);
		}
		if(!isSiteAdmin(session,siteAdminId, siteId)){
			throw new InvalidInputException(" Invalid Site Admin doesn't exists with id: " + siteAdminId);
		}
		try {
			session.beginTransaction();
			// Add user
			SiteUser pcUser = new SiteUser();
			pcUser.setActive(dto.isActive());
			pcUser.setCreated(getCurrentTimeStamp());
			pcUser.setDisplayName(dto.getDisplayName());
			List<Email> emails = getEmails(dto.getEmails());
			List<PhoneNumber> phone = getPhoneNumbers(dto.getPhoneNumbers());
			List<Photo> pics = getPics(dto.getPics());
			pcUser.setEmails(emails);
			pcUser.setPhoneNumbers(phone);
			pcUser.setPics(pics);
			pcUser.setLastModifiedTime(getCurrentTimeStamp());
			pcUser.setFamilyName(dto.getFamilyName());
			pcUser.setFormattedName(dto.getFormattedName());
			pcUser.setGivenName(dto.getGivenName());
			pcUser.setHonorificPrifix(dto.getHonorificPrifix());
			pcUser.setMiddleName(dto.getMiddleName());
			pcUser.setNickName(dto.getNickName());
			pcUser.setPassword(dto.getPassword());
			pcUser.setUserName(dto.getUserName());
			EnterpriseSite site=(EnterpriseSite) session.get(EnterpriseSite.class, siteId);
			pcUser.setEnterpriseSite(site);
			Roles role = getRole(session, "SiteUser");
			pcUser.setRole("SiteUser");
			pcUser.setRoles(role);
			List<Operations> operations = role.getOperations();
			System.out.println(" Size of Operations: " + operations.size());
			for (Operations operation : operations) {
				System.out.println(" Operations ID add:::  " + operation.getOperationId());
				pcUser.addPermittedOperations(operation.getOperationId());
			}
			session.save(pcUser);
			SiteUserAdditionResponseDTO response= convertSiteUserAdditionResponse(dto,pcUser.getSiteUserId());
			session.getTransaction().commit();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return null;
	}

	private SiteUserAdditionResponseDTO convertSiteUserAdditionResponse(SiteUserAdditionRequestDTO user, String userId) {
		SiteUserAdditionResponseDTO response = new SiteUserAdditionResponseDTO();
		response.setActive(user.isActive());
		response.setCreated(getCurrentTimeStamp().toString());
		response.setDisplayName(user.getDisplayName());
		response.setFamilyName(user.getFamilyName());
		response.setFormattedName(user.getFormattedName());
		response.setGivenName(user.getGivenName());
		response.setHonorificPrifix(user.getHonorificPrifix());
		response.setLastModifiedTime(getCurrentTimeStamp().toString());
		response.setMiddleName(user.getMiddleName());
		response.setNickName(user.getNickName());
		response.setPassword("");
		response.setRole(user.getRole());
		response.setUserId(userId);
		response.setUserName(user.getUserName());
		return response;
	}

	public boolean deleteSiteUser(String siteAdminId, String siteUserId, String siteId) throws InvalidInputException{
		Session session= enterpriseSystemInputValidator.getSessionFactory().openSession();
		if (!isEnterpriseSiteExists(siteId, session)) {
			throw new InvalidInputException("Enterprise Site doesn't exists with id: " + siteId);
		}
		if(!isSiteAdmin(session,siteAdminId, siteId)){
			throw new InvalidInputException(" Invalid Site Admin doesn't exists with id: " + siteAdminId);
		}
		if(!isSiteUser(session,siteUserId)){
			throw new InvalidInputException(" Invalid Site user doesn't exists with id: " + siteAdminId);
		}
		try {
			session.beginTransaction();
			SiteUser user = getSiteUser(siteUserId, session);
			if (user != null && user instanceof SiteUser) {
				SiteUser pcUser = (SiteUser) user;
				session.delete(pcUser);
				String userId = user.getSiteUserId();
				session.getTransaction().commit();
			}
		
			return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return false;
	}

	private SiteUser getSiteUser(String siteUserId, Session session) {
		SiteUser user=(SiteUser) session.get(SiteUser.class, siteUserId);
		if(user!=null){
			return user;
		}
		return null;

	}

	private boolean isSiteUser(Session session, String userId) {
		SiteUser user=(SiteUser) session.get(SiteUser.class, userId);
		if(user!=null && user.getSiteUserId().equals(userId)){
			return true;
		}
		return false;
	}

	private boolean isSiteAdmin(Session session, String siteAdminId, String siteId) {
		EnterpriseSystemUser user= (EnterpriseSystemUser) session.get(EnterpriseSystemUser.class, siteAdminId);
		EnterpriseSite site=(EnterpriseSite)session.get(EnterpriseSite.class,siteId);
		if(user.getEnterpriseSites().contains(site) || user.getRole().equalsIgnoreCase("SiteSuperAdmin")){
			return true;
		}
		return false;
	}

}
