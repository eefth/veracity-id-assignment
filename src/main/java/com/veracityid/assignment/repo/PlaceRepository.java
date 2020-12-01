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
	
	@Modifying
    @Query("update Place pl set pl.formattedAddress = :formattedAddress, "+
                           " pl.formattedPhoneNumber = :formattedPhoneNumber, "+
                           " pl.internationalPhoneNumber = :internationalPhoneNumber, "+
                           " pl.website = :website"+
                           " where pl.id = :id")
    void updatePlace(@Param(value = "id") long id, @Param(value = "formattedAddress") String formattedAddress, 
    		@Param(value = "formattedPhoneNumber") String formattedPhoneNumber, 
    		@Param(value = "internationalPhoneNumber") String internationalPhoneNumber, @Param(value = "website") String website);
	
	@Query("select pl from Place pl where pl.cityLocationLat = :cityLocationLat and pl.cityLocationLng = :cityLocationLng and pl.priceLevel <= :priceLevel and pl.rating >= :rating and pl.dirty is false")
	List<Place> findByCityLocationLatAndCityLocationLngAndPriceLevelAndRating(@Param("cityLocationLat") String cityLocationLat,
			@Param("cityLocationLng") String cityLocationLng, @Param("priceLevel") Integer priceLevel,
			@Param("rating") Double rating);
	
	
	

}
