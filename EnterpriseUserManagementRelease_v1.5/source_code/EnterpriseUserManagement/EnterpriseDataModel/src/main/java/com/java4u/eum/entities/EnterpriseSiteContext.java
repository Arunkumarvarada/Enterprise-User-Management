/**
 * 
 */
package com.altiux.eum.entities;

import java.io.Serializable;
import java.util.Currency;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class EnterpriseSiteContext implements Serializable{

	@Column(name="timeZone")
	private String timeZone;
	
	@Column(name="geoRegion")
	private String geoRegion;
	
	@Column(name="country")
	private String country;
	
	@Column(name="city")
	private String city;
	
	@Column(name="countryCode")
	private String countryCode;
	
	@Column(name="cityCode")
	private String cityCode;
	
	@Column(name="isdCode")
	private Integer isdCode;
	
	@Column(name="stdCode")
	private Integer stdCode;
	
	@Column(name="currency")
	private Currency currency;

	public EnterpriseSiteContext() {
		super();
	}

	public EnterpriseSiteContext(String timeZone, String country, String city,
			String countryCode, String cityCode, Integer isdCode,
			Integer stdCode, Currency currency) {
		super();
		this.timeZone = timeZone;
		this.country = country;
		this.city = city;
		this.countryCode = countryCode;
		this.cityCode = cityCode;
		this.isdCode = isdCode;
		this.stdCode = stdCode;
		this.currency = currency;
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

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "ParkingSpaceContext [timeZone=" + timeZone + ", country="
				+ country + ", city=" + city + ", countryCode=" + countryCode
				+ ", cityCode=" + cityCode + ", isdCode=" + isdCode
				+ ", stdCode=" + stdCode + ", currency=" + currency + "]";
	}

	public String getGeoRegion() {
		return geoRegion;
	}

	public void setGeoRegion(String geoRegion) {
		this.geoRegion = geoRegion;
	}
	
}
