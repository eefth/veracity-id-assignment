package com.veracityid.assignment.service;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import com.veracityid.assignment.json.output.NearbyPlace;
import com.veracityid.assignment.json.output.NearbyPlacePhoto;
import com.veracityid.assignment.json.output.NearbyPlacesResponse;
import com.veracityid.assignment.model.Place;
import com.veracityid.assignment.model.PlacePhoto;
import com.veracityid.assignment.repo.PlacePhotoRepository;
import com.veracityid.assignment.repo.PlaceRepository;

@Service
public class PlacesService {
	
	private static final String PLACE_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
			+ "location={location}" + "&radius={radius}" + "&type=restaurant" + "&key={key}";

	private final RestTemplate restTemplateForGoogleApi;
	private final PlaceRepository placeRepository; 
	private final PlacePhotoRepository placePhotoRepository; 
	
	public PlacesService(RestTemplate restTemplateForGoogleApi, PlaceRepository placeRepository, PlacePhotoRepository placePhotoRepository) {
		this.restTemplateForGoogleApi = restTemplateForGoogleApi;
		this.placeRepository = placeRepository;
		this.placePhotoRepository = placePhotoRepository;
	}
	
	@Transactional
	public List<NearbyPlace> getAndSaveNearbyPlaces(String location) {
		
		System.out.println("in getPlacesByName");
		
		String[] latAndLng = location.split(",");
		String key = "AIzaSyAyLAox0bzweP_9opnCd2gQx--wCqO6v7U";
		String radius = "5000";
		
		URI uri = new UriTemplate(PLACE_DETAILS_URL).expand(location, radius, key);
		System.out.println("URI: "+ uri.toString());
		System.out.println(restTemplateForGoogleApi);
		
		NearbyPlacesResponse response = restTemplateForGoogleApi.getForObject(uri,
				NearbyPlacesResponse.class);
		
		System.out.println("json themis: "+ response);
		
		if(response.getResults() != null) {
			printNeabyResults(response.getResults());
			
			// save nearby places found
			response.getResults().stream().forEach(nearbyPlace-> {
				
				Place place = new Place(nearbyPlace.getPlaceId(), nearbyPlace.getIcon(), nearbyPlace.getName(),
						nearbyPlace.getPriceLevel(), nearbyPlace.getRating(), nearbyPlace.getVicinity(),
						nearbyPlace.getTypes().stream().collect(Collectors.joining(", ")), latAndLng[0], latAndLng[1]);

				Set<PlacePhoto> placePhotos = nearbyPlace.getPhotos().stream()
						.map(nearbyPlacePhoto -> {
							return new PlacePhoto(nearbyPlacePhoto.getPhotoReference(), nearbyPlacePhoto.getHeight(), nearbyPlacePhoto.getWidth(), place);
						}).collect(Collectors.toSet());
				
				placeRepository.save(place);
				
				// save photos
				placePhotos.stream().forEach(placePhoto -> placePhotoRepository.save(placePhoto));
				
			});
			
			return response.getResults();
		} else {
			return null;
		}

	}
	
	private void printNeabyResults(List<NearbyPlace> nearbySearchResults) {
		
		nearbySearchResults.stream().forEach(System.out::println);
		
	}
	
}
