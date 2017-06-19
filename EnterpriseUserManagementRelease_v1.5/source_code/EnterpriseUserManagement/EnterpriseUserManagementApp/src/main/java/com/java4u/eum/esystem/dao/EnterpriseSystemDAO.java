package com.java4u.eum.esystem.dao;

import java.util.List;

import com.java4u.commons.exceptions.InvalidInputException;
import com.java4u.eum.entities.Operations;
import com.java4u.eum.esystem.dto.EnterpriseSiteAdditionRequestDTO;
import com.java4u.eum.esystem.dto.EnterpriseSiteAdditionResponseDTO;
import com.java4u.eum.esystem.dto.EnterpriseSiteStatusRepsonse;
import com.java4u.eum.esystem.dto.EnterpriseSystemAdditionRequestDTO;
import com.java4u.eum.esystem.dto.EnterpriseSystemAdditionResponseDTO;
import com.java4u.eum.esystem.dto.EnterpriseSystemStatusRepsonse;
import com.java4u.eum.esystem.dto.OperationsDTO;
import com.java4u.eum.esystem.dto.OperationsInputResponseDTO;
import com.java4u.eum.esystem.dto.RolesDTO;
import com.java4u.eum.esystem.dto.SiteUserAdditionRequestDTO;
import com.java4u.eum.esystem.dto.SiteUserAdditionResponseDTO;

public interface EnterpriseSystemDAO {

	public EnterpriseSystemAdditionResponseDTO addEnterpriseSystem(EnterpriseSystemAdditionRequestDTO dto) throws InvalidInputException;

	public EnterpriseSiteAdditionResponseDTO addEnterpriseSite(String systemId, EnterpriseSiteAdditionRequestDTO dto)
			throws InvalidInputException;

	public EnterpriseSiteStatusRepsonse activeOrDectiveSite(String siteId, String status) throws InvalidInputException;

	public EnterpriseSystemStatusRepsonse activeOrDectiveSystem(String systemId, String status)
			throws InvalidInputException;

	public OperationsInputResponseDTO insertOperations(List<OperationsDTO> operations);

	public RolesDTO InsertRoles(RolesDTO dto);

	public EnterpriseSiteAdditionResponseDTO addEnterpriseSiteWithAdmin(String systemId,
			EnterpriseSiteAdditionRequestDTO dto) throws InvalidInputException;

	public SiteUserAdditionResponseDTO addSiteUser(String siteAdminId,String siteId, SiteUserAdditionRequestDTO dto) 
			throws InvalidInputException;
	
	public boolean deleteSiteUser(String siteAdminId, String siteUserId, String siteId) throws InvalidInputException;
}
