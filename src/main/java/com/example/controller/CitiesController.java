package com.example.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exception.NotFoundException;
import com.example.model.City;
import com.example.service.CityService;

@RestController
@RequestMapping(path="/api/1",
		consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
		produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CitiesController {

	@Autowired
	private CityService cityService;

	@GetMapping("/cities")
	public List<City> getCities() {
		List<City> cities = cityService.findAll();
		return cities;
	}

	@PostMapping("/city")
	public Map<String, Boolean> addCity(@Valid @RequestBody City city) {
		Boolean result = cityService.add(city);
		Map<String, Boolean> response = new HashMap<>();
		response.put("added", result);
		return response;
	}

	@GetMapping("/city/{id}")
	public City getCity(@PathVariable(value = "id") Long cityId)
			throws NotFoundException {
		City city = cityService.get(cityId)
		                       .orElseThrow(() -> new NotFoundException("City not found for this id :: " + cityId));
		return city;
	}

	@DeleteMapping("/city/{id}")
	public Map<String, Boolean> deleteCity(@PathVariable(value = "id") Long cityId)
			throws NotFoundException {
		City city = cityService.get(cityId)
		             .orElseThrow(() -> new NotFoundException("City not found for this id :: " + cityId));
		cityService.delete(city);
		Map <String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}