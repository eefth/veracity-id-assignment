package com.veracityid.assignment.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.veracityid.assignment.json.output.PlaceDetails;
import com.veracityid.assignment.model.FormObject;
import com.veracityid.assignment.model.Place;
import com.veracityid.assignment.model.SelectCity;
import com.veracityid.assignment.service.PlacesService;

@Controller
public class PlaceController {

	private final PlacesService placesService;
	
	public PlaceController(PlacesService placesService) {
		this.placesService = placesService;
	}
    
    @PostMapping("/findPlaces")
    public String findPlacesNearby(@ModelAttribute SelectCity selectCity, Model model) {
    	
    	System.out.println("in findPlacesNearby - selected city: "+ selectCity.getCity());
    	
    	model.addAttribute("nearbyPlaces", placesService.getAndSaveNearbyPlaces(selectCity.getCity()));
    	
    	FormObject formObject = new FormObject();
    	formObject.setText(selectCity.getCity());
    	model.addAttribute("formObject", formObject);
        model.addAttribute("selectCity", new SelectCity(generateCities()));
        
    	return "home";
    }	
    
    @GetMapping("/viewPlaceDetails/{id}")
    public String editPlace(@PathVariable String id, Model model) {
    	
    	System.out.println("in editPlace - placeId: "+ id);
    	
    	PlaceDetails placeDetails = placesService.getPlaceDetails(id);
    	placeDetails.setId(id);
    	
    	model.addAttribute("placeDetails", placeDetails); 
    	
    	return "editPlace";
    }
    
    @GetMapping("/deletePlace/{id}")
    public ModelAndView deletePlace(@PathVariable String id) {
    	
    	System.out.println("in deletePlace - placeId: "+ id);
    	
    	placesService.deletePlace(id);
    	
    	return new ModelAndView("redirect:/");
    }
    
    @PostMapping("/updatePlace")
    public ModelAndView updatePlace(@ModelAttribute PlaceDetails placeDetails, Model model) {
    	
    	System.out.println("in updatePlace");
    	
    	System.out.println("got placeDetails: "+ placeDetails.toString());
    	
    	Place place = new Place();
    	place.setId(Long.valueOf(placeDetails.getId()));
    	place.setFormattedAddress(placeDetails.getFormattedAddress());
    	place.setFormattedPhoneNumber(placeDetails.getFormattedPhoneNumber());
    	place.setInternationalPhoneNumber(placeDetails.getInternationalPhoneNumber());
    	place.setWebsite(placeDetails.getWebsite().toString());
    	
    	placesService.updatePlace(place);
    	
    	return new ModelAndView("redirect:/");
    }
    
    //http://localhost:8081/findPlacesByLocationAndRatingAndPriceLevel?location=37.9838096,23.7275388&priceLevel=5&rating=2
    @GetMapping("/findPlacesByLocationAndRatingAndPriceLevel")
    public String findPlacesByRatingAndPriceLevel(@RequestParam String location, String rating, String priceLevel, Model model) {
    	
		System.out.println("in findPlacesByRatingAndPriceLevel location: "+ location + ", rating: " + rating + ", priceLevel: "+ priceLevel);
    	
    	model.addAttribute("places", placesService.findPlacesByLocationAndByRatingAndPriceLevel(location, Double.valueOf(rating), Integer.valueOf(priceLevel)));
    	
    	return "searchResults";
    }
    
    // helper methods

    private Map<String, String> generateCities(){
    	Map<String, String> cities = new LinkedHashMap<>();
    	
    	// locations are copied from google maps
    	cities.put("Athens", "37.9838096,23.7275388"); 
    	cities.put("Berlin", "52.5069296,13.1438499");
    	cities.put("Rome", "41.9102415,12.3959146");
    	cities.put("Nice", "43.7032932,7.1827773");
    	
    	return cities;
    }
    	
}
