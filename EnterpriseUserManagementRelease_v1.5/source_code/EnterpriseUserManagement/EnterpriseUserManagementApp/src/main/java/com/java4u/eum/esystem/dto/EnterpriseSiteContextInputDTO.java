package com.java4u.eum.esystem.dto;

import java.util.Currency;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EnterpriseSiteContextInputDTO {
	
	private @JsonProperty("countryCode") String countryCode;

	private @JsonProperty("cityCode") String cityCode;

	private @JsonProperty("geoRegion") String geoRegionCode;

	private @JsonProperty("timeZone") String timeZone;
	
	private @JsonProperty("country") String country;
	
	private @JsonProperty("city") String city;
	
	private @JsonProperty("isdCode") Integer isdCode;
	
	private @JsonProperty("stdCode") Integer stdCode;
	
	//private @JsonProperty("currency") Currency currency;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getGeoRegionCode() {
		return geoRegionCode;
	}

	public void setGeoRegionCode(String geoRegionCode) {
		this.geoRegionCode = geoRegionCode;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	

	public EnterpriseSiteContextInputDTO() {
		super();
	}

	public Integer getIsdCode() {
		return isdCode;
	}

	public void setIsdCode(Integer isdCode) {
		this.isdCode = isdCode;
	}

	public Integer getStdCode() {
		return stdCode;
	}

	public void setStdCode(Integer stdCode) {
		this.stdCode = stdCode;
	}

	

	public EnterpriseSiteContextInputDTO(String countryCode, String cityCode, String geoRegionCode, String timeZone,
			String country, String city, Integer isdCode, Integer stdCode) {
		super();
		this.countryCode = countryCode;
		this.cityCode = cityCode;
		this.geoRegionCode = geoRegionCode;
		this.timeZone = timeZone;
		this.country = country;
		this.city = city;
		this.isdCode = isdCode;
		this.stdCode = stdCode;

	}

	@Override
	public String toString() {
		return "EnterpriseSiteContextInputDTO [countryCode=" + countryCode + ", cityCode=" + cityCode
				+ ", geoRegionCode=" + geoRegionCode + ", timeZone=" + timeZone + ", country=" + country + ", city="
				+ city + ", isdCode=" + isdCode + ", stdCode=" + stdCode + "]";
	}

}
