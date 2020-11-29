package com.veracityid.assignment.json.output;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NearbyPlacePhoto {
  
	@JsonProperty("height")
    private String height;
	
	@JsonProperty("width")
    private String width;
	
	@JsonProperty("photo_reference")
    private String photoReference;
	
	@JsonProperty("html_attributions")
    private List<String> htmlAttributions = Collections.emptyList();

	public String getHeight() {
		return height;
	}

	public String getWidth() {
		return width;
	}

	public String getPhotoReference() {
		return photoReference;
	}

	public List<String> getHtmlAttributions() {
		return htmlAttributions;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public void setPhotoReference(String photoReference) {
		this.photoReference = photoReference;
	}

	public void setHtmlAttributions(List<String> htmlAttributions) {
		this.htmlAttributions = htmlAttributions;
	}

	@Override
	public String toString() {
		return "NearbyPlacePhoto [height=" + height + ", width=" + width + ", photoReference=" + photoReference
				+ ", htmlAttributions=" + htmlAttributions + "]";
	}
	
}