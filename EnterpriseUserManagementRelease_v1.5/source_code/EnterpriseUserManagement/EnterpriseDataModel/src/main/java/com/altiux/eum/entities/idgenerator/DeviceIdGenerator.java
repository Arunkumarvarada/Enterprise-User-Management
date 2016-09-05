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

import com.altiux.eum.entities.Device;

public class DeviceIdGenerator implements IdentifierGenerator{

	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {		
		String prefix = "DEV";
		Session newSession =(Session)session;
		try {			
		
			String queryString = "from Device order by deviceId desc";
			Query query = newSession.createQuery(queryString);			
			Device lastDevice = (Device)query.setMaxResults(1).uniqueResult();			
            
			if(lastDevice!=null) {
				int count = Integer.parseInt(lastDevice.getDeviceId().replace(prefix, ""));
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
