package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class HelloWorldController {

	@RequestMapping(path = "/helloworld/json")
	public ResponseEntity helloJson(@Valid @RequestParam String name) {
		String result = String.format("Welcome, Hello World, " + name);
		Map<String, Object> response = new HashMap<>();
		response.put("result", result);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(path = "/helloworld")
	public String hello(@Valid @RequestParam String name) {
		String result = String.format("Welcome, Hello World, " + name);
		return result;
	}
}