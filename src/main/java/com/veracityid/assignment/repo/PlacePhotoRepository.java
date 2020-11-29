package com.veracityid.assignment.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.veracityid.assignment.model.Place;
import com.veracityid.assignment.model.PlacePhoto;

public interface PlacePhotoRepository extends CrudRepository<PlacePhoto, Long>{
	
	List<PlacePhoto> findByPlace(Place place);
	
}
