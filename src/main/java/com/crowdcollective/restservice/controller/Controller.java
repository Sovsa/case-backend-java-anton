package com.crowdcollective.restservice.controller;

import com.crowdcollective.restservice.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	@GetMapping("/ping")
	public Greeting ping(){
		return new Greeting("ping", "pong");
	}
}
