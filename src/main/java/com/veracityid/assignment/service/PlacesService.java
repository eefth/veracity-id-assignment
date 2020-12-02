package com.veracityid.assignment.service;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import com.veracityid.assignment.exception.PlaceNotFoundException;
import com.veracityid.assignment.json.output.NearbyPlace;
import com.veracityid.assignment.json.output.NearbyPlacesResponse;
import com.veracityid.assignment.json.output.PlaceDetails;
import com.veracityid.assignment.json.output.PlaceDetailsResponse;
import com.veracityid.assignment.model.Place;
import com.veracityid.assignment.model.PlacePhoto;
import com.veracityid.assignment.repo.PlacePhotoRepository;
import com.veracityid.assignment.repo.PlaceRepository;

@Service
public class PlacesService {
	
	private static final Logger LOG = LoggerFactory.getLogger(PlacesService.class);
	
	private static final String FIND_NEARBY_PLACES_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
			+ "location={location}" + "&radius={radius}" + "&type=restaurant" + "&key={key}";
	
	private static final String PLACE_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?"
			+ "place_id={placeId}&fields=name,rating,formatted_phone_number,icon,types,website,formatted_address,photo&key={key}";
	
	private static final String GOOGLE_API_KEY = "AIzaSyAyLAox0bzweP_9opnCd2gQx--wCqO6v7U";
	
	private static final String NEARBY_SEARVH_RADIUS = "5000";
	
	private final RestTemplate restTemplateForGoogleApi;
	private final PlaceRepository placeRepository; 
	private final PlacePhotoRepository placePhotoRepository; 
	
	public PlacesService(RestTemplate restTemplateForGoogleApi, PlaceRepository placeRepository, PlacePhotoRepository placePhotoRepository) {
		this.restTemplateForGoogleApi = restTemplateForGoogleApi;
		this.placeRepository = placeRepository;
		this.placePhotoRepository = placePhotoRepository;
	}
	
	@Transactional
	public List<Place> getAndSaveNearbyPlaces(String location) {
		
		System.out.println("in getAndSaveNearbyPlaces");
		
		String[] latAndLng = location.split(",");
		
		URI uri = new UriTemplate(FIND_NEARBY_PLACES_URL).expand(location, NEARBY_SEARVH_RADIUS, GOOGLE_API_KEY);
		LOG.debug("URI: {}", uri.toString());
		
		NearbyPlacesResponse response = restTemplateForGoogleApi.getForObject(uri,
				NearbyPlacesResponse.class);
		
		if(response.getResults() != null) {
			printNeabyResults(response.getResults());
			
			// override existing matching data
			List<Place> existing = placeRepository.findByCityLocationLatAndCityLocationLngAndDirty(latAndLng[0], latAndLng[1], false);
			LOG.debug("existing dirty places are: {}", existing.size());
			existing.stream().forEach(place -> placeRepository.updatePlaceDirty(place.getId(), true));
			
			// save nearby places found
			List<Place> savedPlaces = response.getResults().stream().map(nearbyPlace-> {
				
				Place place = new Place(nearbyPlace.getPlaceId(), nearbyPlace.getIcon(), nearbyPlace.getName(),
						nearbyPlace.getPriceLevel(), nearbyPlace.getRating(), nearbyPlace.getVicinity(),
						nearbyPlace.getTypes().stream().collect(Collectors.joining(", ")), latAndLng[0], latAndLng[1]);
				
			    place.setTypes2(nearbyPlace.getTypes());
				Place savedPlace = placeRepository.save(place);
				
				Set<PlacePhoto> placePhotos = nearbyPlace.getPhotos().stream()
						.map(nearbyPlacePhoto -> {
							return new PlacePhoto(nearbyPlacePhoto.getPhotoReference(), nearbyPlacePhoto.getHeight(), nearbyPlacePhoto.getWidth(), savedPlace);
						}).collect(Collectors.toSet());
				
				// save photos
				placePhotos.stream().forEach(placePhoto -> placePhotoRepository.save(placePhoto));
				
				return savedPlace;
				
			}).collect(Collectors.toList());
			
			return savedPlaces;
		} else {
			return Collections.emptyList();
		}

	}
	
	public PlaceDetails getPlaceDetails(String id) {
		
		System.out.println("in getPlaceDetails");
		
		Optional<Place> storedPlaceOptional = placeRepository.findById(Long.valueOf(id));
		
		if(storedPlaceOptional.isPresent()) {
		
			URI uri = new UriTemplate(PLACE_DETAILS_URL).expand(storedPlaceOptional.get().getPlaceId(), GOOGLE_API_KEY);
			System.out.println("URI:" + uri.toString());

			PlaceDetailsResponse response = restTemplateForGoogleApi.getForObject(uri, PlaceDetailsResponse.class);
			System.out.println(response.getResult());
			return response.getResult();
		
		} else {
			throw new PlaceNotFoundException("Place wasn't found in local storage");
		}	
		
	}
	
	public void deletePlace(String id) {
		
		System.out.println("in deletePlace");
		
		Optional<Place> optionalPlace = placeRepository.findById(Long.valueOf(id));
				
		// delete the photos of the place from the local storage
		if(optionalPlace.isPresent()) {
			
			List<PlacePhoto> placePhotos = placePhotoRepository.findByPlace(optionalPlace.get());
			placePhotos.stream().forEach(photo -> placePhotoRepository.deleteById(photo.getId()));
			
			System.out.println("place photos deleted");
			
		} else {
			throw new PlaceNotFoundException("Place not found in local storage");
		}
		
		// delete the place from the local storage
		placeRepository.deleteById(Long.valueOf(id));
		
		System.out.println("place deleted");
	}
	
	@Transactional
	public void updatePlace(Place place) {

		placeRepository.updatePlace(place.getId(), place.getFormattedAddress(), place.getFormattedPhoneNumber(),
				place.getInternationalPhoneNumber(), place.getWebsite());

	}

	public List<Place> findPlacesByLocationAndByRatingAndPriceLevel(String location, Double rating,
			Integer priceLevel) {
		return placeRepository.findByCityLocationLatAndCityLocationLngAndPriceLevelAndRating(location.split(",")[0],
				location.split(",")[1], priceLevel, rating);
	}
	
	public List<Place> findPlacesByLocationAndByRatingOrderedByRatingAndPriceLevel(String location) {
		return placeRepository.findByCityLocationLatAndCityLocationLngOrderedByRatingAndPriceLevel(
				location.split(",")[0], location.split(",")[1],
				Sort.by("rating").descending().and(Sort.by("priceLevel").ascending()));
	}
	
	public List<Place> findPlacesByLocationAndType(String location, String type) {
		return placeRepository.findByCityLocationLatAndCityLocationLngAndType(location.split(",")[0], location.split(",")[1], type);
	}
	
	private void printNeabyResults(List<NearbyPlace> nearbySearchResults) {
		
		nearbySearchResults.stream().forEach(System.out::println);
		
	}
	
}
