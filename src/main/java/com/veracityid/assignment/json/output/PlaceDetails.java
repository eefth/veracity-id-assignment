package com.veracityid.assignment.json.output;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceDetails {
	
	private long id;
	
	@JsonProperty("formatted_address")
	private String formattedAddress;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("formatted_phone_number")
	private String formattedPhoneNumber;
	
	@JsonProperty("html_attributions")
	private String[] htmlAttributions;
	
	@JsonProperty("international_phone_number")
	private String internationalPhoneNumber;
	
	@JsonProperty("place_id")
	private String placeId;
	
	@JsonProperty("vicinity")
	private String vicinity;
	
	@JsonProperty("website")
	private URL website;
	
	@JsonProperty("url")
	private URL url;
	
	@JsonProperty("types")
	private List<String> types = Collections.emptyList();
	
	@JsonProperty("rating")
	private Double rating;
	
	@JsonProperty("photos")
	private List<NearbyPlacePhoto> photos = Collections.emptyList();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFormattedAddress() {
		return formattedAddress;
	}

	public String getFormattedPhoneNumber() {
		return formattedPhoneNumber;
	}

	public String[] getHtmlAttributions() {
		return htmlAttributions;
	}

	public String getInternationalPhoneNumber() {
		return internationalPhoneNumber;
	}

	public String getPlaceId() {
		return placeId;
	}

	public String getVicinity() {
		return vicinity;
	}

	public URL getWebsite() {
		return website;
	}

	public URL getUrl() {
		return url;
	}

	public List<String> getTypes() {
		return types;
	}

	public Double getRating() {
		return rating;
	}

	public List<NearbyPlacePhoto> getPhotos() {
		return photos;
	}

	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}

	public void setFormattedPhoneNumber(String formattedPhoneNumber) {
		this.formattedPhoneNumber = formattedPhoneNumber;
	}

	public void setHtmlAttributions(String[] htmlAttributions) {
		this.htmlAttributions = htmlAttributions;
	}

	public void setInternationalPhoneNumber(String internationalPhoneNumber) {
		this.internationalPhoneNumber = internationalPhoneNumber;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	public void setWebsite(URL website) {
		this.website = website;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public void setPhotos(List<NearbyPlacePhoto> photos) {
		this.photos = photos;
	}

	@Override
	public String toString() {
		return "PlaceDetails [formattedAddress=" + formattedAddress + ", name=" + name +", formattedPhoneNumber=" + formattedPhoneNumber
				+ ", htmlAttributions=" + Arrays.toString(htmlAttributions) + ", internationalPhoneNumber="
				+ internationalPhoneNumber + ", placeId=" + placeId + ", vicinity=" + vicinity + ", website=" + website
				+ ", url=" + url + ", types=" + types + ", rating=" + rating + ", photos=" + photos + "]";
	}
	
	
	
}
