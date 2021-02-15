package com.solactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.solactive.entity.Statistic;
import com.solactive.entity.Tick;
import com.solactive.exception.InvalidTickException;
import com.solactive.service.StatisticsService;

@RestController
public class StatisticsRestController {

	@Autowired
	private StatisticsService service;

	@PostMapping("/ticks")
	public ResponseEntity<Tick> addTick(@RequestBody Tick tick) {
		try {
			service.addTick(tick);
			ResponseEntity<Tick> response = new ResponseEntity<Tick>(tick, HttpStatus.CREATED);
			return response; 
		} 
		catch (InvalidTickException e) {
			return new ResponseEntity<Tick>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/statistics")
	@ResponseStatus(HttpStatus.OK)
	public Statistic getStatistics() {
		return service.getStatistics();
	}
	
	@GetMapping("/statistics/{instrument}")
	@ResponseStatus(HttpStatus.OK)
	public Statistic getTicksByInstrument(@PathVariable final String instrument ) {
		return service.getTicksByInstrument(instrument);
	}
	
}
