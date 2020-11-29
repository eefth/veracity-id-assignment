package com.veracityid.assignment.model;

import java.util.Map;

public class SelectCity {
	
	private String city;
	
 	private Map<String, String> cities;
	
	public SelectCity(Map<String, String> cities) {
		this.cities = cities;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Map<String, String> getCities() {
		return cities;
	}

	public void setCities(Map<String, String> cities) {
		this.cities = cities;
	}
	
}
