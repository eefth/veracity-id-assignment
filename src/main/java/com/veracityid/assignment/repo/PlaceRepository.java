package com.veracityid.assignment.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.veracityid.assignment.model.Place;

public interface PlaceRepository extends CrudRepository<Place, Long>{
	
	Optional<Place> findByPlaceId(String placeId);
	
	@Modifying
    @Query("update Place pl set pl.dirty = :dirty where pl.id = :id")
    void updatePlaceDirty(@Param(value = "id") long id, @Param(value = "dirty") Boolean dirty);
	
	@Query("select pl from Place pl where pl.cityLocationLat = :cityLocationLat and pl.cityLocationLng = :cityLocationLng and pl.dirty = :dirty")
	List<Place> findByCityLocationLatAndCityLocationLngAndDirty(@Param("cityLocationLat") String cityLocationLat,
			@Param("cityLocationLng") String cityLocationLng, @Param("dirty") Boolean dirty);

}
