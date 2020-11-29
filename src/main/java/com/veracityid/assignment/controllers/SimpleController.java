package com.veracityid.assignment.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.veracityid.assignment.model.FormObject;
import com.veracityid.assignment.model.Movie;
import com.veracityid.assignment.model.SelectCity;
import com.veracityid.assignment.repo.MovieRepository;

@Controller
public class SimpleController {
    
	@Value("${spring.application.name}")
    String appName;
    
	private MovieRepository movieRepository;
	
    public SimpleController(MovieRepository movieRepository) {
    	this.movieRepository = movieRepository;
    }
    
    @GetMapping("/")
    public String homePage(Model model) {
        // will be deleted
    	model.addAttribute("appName", appName);
        model.addAttribute("movies", findAllMovvies());
        model.addAttribute("movie", new Movie());
        model.addAttribute("formObject", new FormObject());
        // create cities 
        model.addAttribute("selectCity", new SelectCity(generateCities()));
        
        return "home";
    }
    
    private List<Movie> findAllMovvies(){
    	return (List<Movie>) movieRepository.findAll();
    	
    }
    
    @Controller
    static class FaviconController {
     
        @GetMapping("favicon.ico")
        @ResponseBody
        void returnNoFavicon() {
        }
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
