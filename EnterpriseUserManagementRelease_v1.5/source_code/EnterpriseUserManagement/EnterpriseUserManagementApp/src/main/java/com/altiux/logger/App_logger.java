package com.altiux.logger;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

/* 
 *  Log4j Wrapper
 *  
 *  @author Neha Garg
 */
public class App_logger {
	
	/*private static enum LEVEL
	{
		DEBUG,
		INFO,
		ERROR,
		TRACE,
		WARN,
		OFF
	}*/

	public enum Configuration_Type {
		xml,property
	}
	
	private static Configuration_Type type = Configuration_Type.property;
	private Logger logger;
	
	static{
		String filename = "../../log4j.properties";
		String name = LoggerFactory.class.getClassLoader().getResource(filename).getPath();
		if (type.equals(Configuration_Type.xml)) {
			DOMConfigurator.configureAndWatch(name,180000);
		} else if (type.equals(Configuration_Type.property)) {
			PropertyConfigurator.configureAndWatch(name,180000);
		} else {
			System.out.println("Wrong Configuration file Type");
		}
	}
	
	
	
	public App_logger(String name) {
		logger = Logger.getLogger(name);
	}
	
	public void warn(String className,String method, String message) {
		logger.warn(className+" | "+ method +" | "+message);
	}
	
	public void debug(String className,String method, String message) {
		logger.debug(className+" | "+ method +" | "+message);
	}

	public void info(String className,String method, String message) {
		logger.info(className+" | "+ method +" | "+message);
	}

	public void error(String className,String method, String message) {
		logger.error(className+" | "+ method +" | "+message);
	}

	/*public void enable(LEVEL level) {
		logger.setLevel(Level.toLevel(level.toString()));
	}

	public void disable() {
		logger.setLevel(Level.OFF);
	}*/
	
}
