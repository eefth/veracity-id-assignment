package com.veracityid.assignment.repo;

import org.springframework.data.repository.CrudRepository;

import com.veracityid.assignment.model.Place;

public interface PlaceRepository extends CrudRepository<Place, Long>{
	
	Place findByPlaceId(String placeId);
	
}
