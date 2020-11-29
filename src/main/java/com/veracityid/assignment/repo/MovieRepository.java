package com.veracityid.assignment.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.veracityid.assignment.model.Movie;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    List<Movie> findByTitle(String title);
}
