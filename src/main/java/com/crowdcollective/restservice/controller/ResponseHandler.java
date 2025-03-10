package com.crowdcollective.restservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String,  Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status);
        if (responseObj != null) {
            map.put("data", responseObj);
        }

        return new ResponseEntity<>(map, status);
    }
}
