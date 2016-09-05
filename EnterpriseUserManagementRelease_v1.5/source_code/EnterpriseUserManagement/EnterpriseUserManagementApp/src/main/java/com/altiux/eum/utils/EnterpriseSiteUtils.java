package com.altiux.eum.utils;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class EnterpriseSiteUtils {

	private static MultiValueMap<String, String> headersForGetMethod;
	
	private static MultiValueMap<String, String> headersForPostMethod;
	
	static {
		headersForGetMethod = new LinkedMultiValueMap<String, String>();
		headersForGetMethod.add("Access-Control-Allow-Origin", "*");
		headersForGetMethod.add("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE, OPTIONS");
		headersForGetMethod.add("Access-Control-Allow-Headers", "X-Requested-With");
		headersForGetMethod.add("Access-Control-Max-Age", "3600");
		
		headersForPostMethod = new LinkedMultiValueMap<String, String>();
		headersForPostMethod.add("Access-Control-Allow-Origin", "*");
		headersForPostMethod.add("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE, OPTIONS");
		headersForPostMethod.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Accept");
		headersForPostMethod.add("Access-Control-Max-Age", "3600");
	}
	
	/**
	 * 
	 * @return
	 */
	public static MultiValueMap<String, String> getHeadersForGetAPI() {
		return headersForGetMethod;
	}
	
	/**
	 * 
	 * @return
	 */
	public static MultiValueMap<String, String> getHeadersForPostAPI() {
		return headersForPostMethod;
	}
}
