package com.veracityid.assignment.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "photo")
public class PlacePhoto {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	private String height;
	
	private String width;
	
	@Column(unique = true)
	private String photoReference;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;
	
//	private List<String> htmlAttributions = Collections.emptyList();
	
	public PlacePhoto() {
		
	}
	
	public PlacePhoto(String photoReference, String height, String width, Place place) {
		this.photoReference = photoReference;
		this.height = height;
		this.width = width;
		this.place = place;
	}

	public long getId() {
		return id;
	}

	public String getHeight() {
		return height;
	}

	public String getWidth() {
		return width;
	}

	public String getPhotoReference() {
		return photoReference;
	}

	public void setId(long id) {
		this.id = id;
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
	
	
	
}
