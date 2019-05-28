package com.java4u.eum.esite.dao;

import com.java4u.commons.exceptions.InvalidInputException;
import com.java4u.commons.exceptions.EnterpriseSiteNotActiveException;
import com.java4u.commons.exceptions.UserNotFoundException;
import com.java4u.eum.esite.dto.LoginCredentialsDTO;
import com.java4u.eum.esite.dto.LoginSuccessDTO;

/**
 * This interface is useful to check the login credentials.It checks in the database in the user table.
 * 
 *
 */
public interface LoginDAO {

  /**
   * This api is useful to verify the user credentials during login.
   * 
   * @param requestDTO
   * @return an instance of type LoginSuccessDTO
   * @throws InvalidInputException 
 * @throws EnterpriseSiteNotActiveException 
 * @throws UserNotFoundException 
   */
  public LoginSuccessDTO login(LoginCredentialsDTO requestDTO) throws InvalidInputException, EnterpriseSiteNotActiveException, UserNotFoundException;
  
}
