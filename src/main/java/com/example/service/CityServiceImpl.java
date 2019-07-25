package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.model.City;

@Service
public class CityServiceImpl implements CityService {

	List<City> cities = new ArrayList<City>();

	@Override
	public List<City> findAll() {
		if (CollectionUtils.isEmpty(cities)) {
			cities.add(City.of(1L, "Bratislava", 432000));
			cities.add(City.of(2L, "Budapest", 1759000));
			cities.add(City.of(3L, "Prague", 1280000));
			cities.add(City.of(4L, "Warsaw", 1748000));
			cities.add(City.of(5L, "Los Angeles", 3971000));
			cities.add(City.of(6L, "New York", 8550000));
			cities.add(City.of(7L, "Edinburgh", 464000));
			cities.add(City.of(8L, "Berlin", 3671000));
		}
		return cities;
	}

	@Override
	public Boolean add(City city) {
		cities.add(city);
		return Boolean.TRUE;
	}

	@Override
	public Optional<City> get(Long cityId) {
		if (cityId < 0) {
			return Optional.empty();
		}

		List<City> result = cities.stream()
		                          .filter(city -> city.getId() == cityId)
		                          .collect(Collectors.toList());
		if (CollectionUtils.isEmpty(result)) {
			return Optional.empty();
		}
		return Optional.of(result.get(0));
	}

	@Override
	public Boolean delete(City city) {
		return cities.remove(city);
	}

}
