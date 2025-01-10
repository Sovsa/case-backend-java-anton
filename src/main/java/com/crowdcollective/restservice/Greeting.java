package com.crowdcollective.restservice;

public class Greeting {

	private final String sender;
	private final String message;
	
	public Greeting(String sender, String message) {
		this.sender = sender;
		this.message = message;
	}
	
	public String getSender() {
		return sender;
	}
	
	public String getMessage() {
		return message;
	}
}
