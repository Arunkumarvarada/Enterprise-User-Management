package com.java4u.eum.entities.idgenerator;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.java4u.eum.entities.User;


public class UserIdGenerator implements IdentifierGenerator{


	public Serializable generate(SessionImplementor session, Object object)	throws HibernateException {

		String prefix = "USR";
		Session newSession =(Session)session;
		try {

			String queryString = "from User order by userId desc";
			Query query = newSession.createQuery(queryString);

			User lastUserId = (User) query.setMaxResults(1).uniqueResult();
            
			if(lastUserId!=null) {
				int count = Integer.parseInt(lastUserId.getUserId().replace(prefix, ""));
				int present=count+1;
				String code = prefix + StringUtils.leftPad("" + present,8,'0');
				return code;
			} else	{
				String code = prefix + StringUtils.leftPad("" + 1,8,'0');
				return code;
			}


		} catch (Exception e) {       
			e.printStackTrace();
		} 
		return null;
	}

}
