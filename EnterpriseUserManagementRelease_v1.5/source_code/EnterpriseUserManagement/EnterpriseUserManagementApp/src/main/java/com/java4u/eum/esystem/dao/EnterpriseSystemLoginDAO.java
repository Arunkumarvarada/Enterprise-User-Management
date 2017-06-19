/**
 * 
 */
package com.java4u.eum.esystem.dao;

import com.java4u.eum.esystem.dto.LoginControllerRequestDTO;
import com.java4u.eum.esystem.dto.LoginControllerResponseDTO;
import com.java4u.eum.esystem.dto.LoginWithRolesRequestDTO;
import com.java4u.eum.esystem.dto.LoginWithRolesResponseDTO;


public interface EnterpriseSystemLoginDAO {
	

	public LoginControllerResponseDTO login(LoginControllerRequestDTO requestDTO);
	
	public LoginWithRolesResponseDTO loginWithRolesResponse(LoginWithRolesRequestDTO requestDTO);
}
