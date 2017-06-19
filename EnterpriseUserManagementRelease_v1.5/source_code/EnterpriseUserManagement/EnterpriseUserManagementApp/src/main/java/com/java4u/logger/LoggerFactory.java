package com.java4u.logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Arun
 *
 */
public class LoggerFactory {
	
	private static Map<EModuleName,App_logger> loggers = new HashMap<EModuleName,App_logger>();

	public static App_logger getLogger(EModuleName moduleName){
		if(loggers.isEmpty() || !loggers.containsKey(moduleName))
		{
			loggers.put(moduleName,new App_logger(moduleName.toString())); 
		}
		return loggers.get(moduleName);
	}	
}
