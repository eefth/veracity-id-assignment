package com.veracityid.assignment.json.output;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NearbyPlacesResponse {
	
	@JsonProperty("results")
	private List<NearbyPlace> results = Collections.emptyList();
 
	public List<NearbyPlace> getResults() {
		return results;
	}
 
	public void setResult(List<NearbyPlace> results) {
		this.results = results;
	}
	
}
