package com.abr.springpatternsamples;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResponderRegistry {
	private Map<String, Responder> registeredResponders = new HashMap<>();

	public void registerResponder(String code, Responder responder) {
		registeredResponders.put(code, responder);
	}

	public Responder get(String key) {
		return registeredResponders.get(key);
	}
}
