/**
 * 
 */
package com.altiux.eum.entities.idgenerator;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.altiux.eum.entities.Operations;


public class OperationIdGenerator implements IdentifierGenerator {
	
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {		
		String prefix = "OP";
		Session newSession =(Session)session;
		try {			
		
			String queryString = "from Operations order by operationId desc";
			Query query = newSession.createQuery(queryString);			
			Operations lastOperation = (Operations)query.setMaxResults(1).uniqueResult();			
            
			if(lastOperation!=null) {
				int count = Integer.parseInt(lastOperation.getOperationId().replace(prefix, ""));
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
