package com.acceldata.controllers;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acceldata.api.IAccelData;

@RestController
public class AccelDataController {
	
	@Autowired
	IAccelData accelDataService;

	@PostConstruct
	public void initialize() throws IOException {
		accelDataService.initializeCache();
	}
	
	@PreDestroy
	public void updateCache() throws IOException {
		accelDataService.doPeriodicCleanUp();
	}
	
	@PostMapping("/api/create/{key}/{value}")
	public String createData(@PathVariable String key, @PathVariable String value) {
		
		try {
			accelDataService.createOrUpdateEntry(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return "Success";
	}
	
	@PutMapping("/api/update/{key}/{value}")
	public String updateData(@PathVariable String key, @PathVariable String value) {
		
		try {
			accelDataService.createOrUpdateEntry(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return "Success";
	}
	
	@GetMapping("/api/getData/{key}")
	public String getData(@PathVariable String key) {
		
		try {
			String value = accelDataService.readEntry(key);
			if(value != null) {
				return value;
			}
			return "Key not found";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
	}
	
	@DeleteMapping("/api/deleteData/{key}")
	public String deleteData(@PathVariable String key) {
		
		try {
			accelDataService.deleteEntry(key);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return "Success";
	}
}
