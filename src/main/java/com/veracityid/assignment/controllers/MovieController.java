package com.veracityid.assignment.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.veracityid.assignment.exception.MovieIdMismatchException;
import com.veracityid.assignment.exception.MovieNotFoundException;
import com.veracityid.assignment.model.FormObject;
import com.veracityid.assignment.model.Movie;
import com.veracityid.assignment.model.SelectCity;
import com.veracityid.assignment.repo.MovieRepository;
import com.veracityid.assignment.service.PlacesService;


@Controller
public class MovieController {

	private MovieRepository movieRepository;
	private final PlacesService placesService;
	
	public MovieController(MovieRepository movieRepository, PlacesService placesService) {
		this.movieRepository = movieRepository;
		this.placesService = placesService;
	}
	
    @GetMapping
    public Iterable findAll() {
        return movieRepository.findAll();
    }
 
    @GetMapping("/title/{movieTitle}")
    public List findByTitle(@PathVariable String movieTitle) {
        return movieRepository.findByTitle(movieTitle);
    }
    
    @GetMapping("/{id}")
    public Movie findOne(@PathVariable Long id) {
        return movieRepository.findById(id)
          .orElseThrow(() -> new MovieNotFoundException("Movie with specified id not found"));
    }
    
    @PostMapping("/addMovie")
    public ModelAndView create(@ModelAttribute Movie movie) {
    	movieRepository.save(movie);
    	return new ModelAndView("redirect:/");
    }
 
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
    	movieRepository.findById(id)
          .orElseThrow(() -> new MovieNotFoundException("Movie with specified id not found"));
    	movieRepository.deleteById(id);
    }
 
    @PutMapping("/{id}")
    public Movie updateBook(@RequestBody Movie movie, @PathVariable Long id) {
        if (movie.getId() != id) {
          throw new MovieIdMismatchException("Mismatched id");
        }
        movieRepository.findById(id)
          .orElseThrow(() -> new MovieNotFoundException("Movie with specified id not found"));
        return movieRepository.save(movie);
    }
    
    @PostMapping("/findPlaces")
    public String findPlacesNearby(@ModelAttribute SelectCity selectCity, Model model) {
    	
    	System.out.println("in findPlaces - selected city: "+ selectCity.getCity());
    	model.addAttribute("nearbyPlaces", placesService.getAndSaveNearbyPlaces(selectCity.getCity()));
    	
    	FormObject formObject = new FormObject();
    	formObject.setText(selectCity.getCity());
    	model.addAttribute("formObject", formObject);
        model.addAttribute("selectCity", new SelectCity(generateCities()));
        
    	return "home";
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
