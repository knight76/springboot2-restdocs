package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.model.City;

public interface CityService {
	public List<City> findAll();

	public Boolean add(City city);

	public Optional<City> get(Long cityId);

	public Boolean delete(City city);
}
