package com.veracityid.assignment.json.output;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NearbyPlace {
	
	@JsonProperty("place_id")
	private String placeId;
	
	@JsonProperty("icon")
	private String icon;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("price_level")
	private Integer priceLevel;
	
	@JsonProperty("rating")
	private Double rating;
	
	@JsonProperty("vicinity")
	private String vicinity;
	
	@JsonProperty("photos")
	private List<NearbyPlacePhoto> photos = Collections.emptyList();
	
	@JsonProperty("types")
	private List<String> types = Collections.emptyList();
	
	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<NearbyPlacePhoto> getPhotos() {
		return photos;
	}

	public void setPhotos(List<NearbyPlacePhoto> photos) {
		this.photos = photos;
	}
	
	public List<String> getTypes() {
		return types;
	}

	public void setTtpes(List<String> types) {
		this.types = types;
	}
	
	public Integer getPriceLevel() {
		return priceLevel;
	}

	public Double getRating() {
		return rating;
	}

	public String getVicinity() {
		return vicinity;
	}

	public void setPriceLevel(Integer priceLevel) {
		this.priceLevel = priceLevel;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("NearbyPlace [placeId=" + placeId + ", icon=" + icon + ", name=" + name + ", photos=" + photos
				+ ", priceLevel=" + priceLevel + ", rating=" + rating + ", vicinity=" + vicinity);
		if (types != null) {
			sb.append(", Types: [");
			String commaSeparatedTypes = types.stream().collect(Collectors.joining(", "));
			sb.append(commaSeparatedTypes);
			sb.append("] ]");
		} else {
			sb.append("]");
		}
		return sb.toString();
	}
	
}
