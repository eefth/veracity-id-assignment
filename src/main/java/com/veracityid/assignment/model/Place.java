package com.veracityid.assignment.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "place")
public class Place{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String placeId;
	
	private String icon;
	
	private String name;
	
	private Integer priceLevel;
	
	private Double rating;
	
	private String vicinity;
	
	private String types;
	
	private String cityLocationLat;
	
	private String cityLocationLng;
	
	@OneToMany(mappedBy = "place", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private Set<PlacePhoto> photos;
	
	public Place() {
		
	}
	
	public Place(String placeId, String icon, String name, Integer priceLevel, Double rating, String vicinity,
			String types, String cityLocationLat, String cityLocationLng) {
		this.placeId = placeId;
		this.icon = icon;
		this.name = name;
		this.priceLevel = priceLevel;
		this.rating = rating;
		this.vicinity = vicinity;
		this.types = types;
		this.cityLocationLat = cityLocationLat;
		this.cityLocationLng = cityLocationLng;
	}

	public long getId() {
		return id;
	}

	public String getPlaceId() {
		return placeId;
	}

	public String getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}

	public Set<PlacePhoto> getPhotos() {
		return photos;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPriceLevel() {
		return priceLevel;
	}

	public Double getRating() {
		return rating;
	}

	public void setPriceLevel(Integer priceLevel) {
		this.priceLevel = priceLevel;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getVicinity() {
		return vicinity;
	}

	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getCityLocationLat() {
		return cityLocationLat;
	}

	public String getCityLocationLng() {
		return cityLocationLng;
	}

	public void setCityLocationLat(String cityLocationLat) {
		this.cityLocationLat = cityLocationLat;
	}

	public void setCityLocationLng(String cityLocationLng) {
		this.cityLocationLng = cityLocationLng;
	}

	public void setPhotos(Set<PlacePhoto> photos) {
		this.photos = photos;
	}
	
}