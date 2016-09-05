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

import com.altiux.eum.entities.Gateway;

public class GatewayIdGenerator implements IdentifierGenerator {
	
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {		
		String prefix = "GATE";
		Session newSession =(Session)session;
		try {			
		
			String queryString = "from Gateway order by gatewayId desc";
			Query query = newSession.createQuery(queryString);			
			Gateway lastGateway = (Gateway)query.setMaxResults(1).uniqueResult();			
            
			if(lastGateway!=null) {
				int count = Integer.parseInt(lastGateway.getGatewayId().replace(prefix, ""));
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
