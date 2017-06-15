package com.java4u.eum.entities.idgenerator;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.java4u.eum.entities.EnterpriseSite;

public class EnterpriseSiteIdGenerator implements IdentifierGenerator {


	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {

		String prefix = "ESITE";

		try {

			String queryString = "from EnterpriseSite order by eSiteId desc";
			Query query = ((Session) session).createQuery(queryString);

			@SuppressWarnings("unchecked")
			List<EnterpriseSite> eSites=query.list();

			if(eSites!=null && eSites.size()>0){
				int count = Integer.parseInt(eSites.get(0).geteSiteId().replace(prefix, ""));
				int present=count+1;
				String code = prefix + StringUtils.leftPad("" + present,8,'0');
				System.out.println("Generated Stock Code: " + code);
				return code;
			}
			else
			{
				String code = prefix + StringUtils.leftPad("" + 1,8,'0');
				return code;
			}


		} catch (Exception e) {       
			e.printStackTrace();
		}
		return null;
	}

}
