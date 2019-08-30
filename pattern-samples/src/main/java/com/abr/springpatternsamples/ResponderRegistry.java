package com.abr.springpatternsamples;

import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

	public Responder getByRequestBody(String requestBody) throws NotImplementedException {
		for (Map.Entry<String, Responder> e: registeredResponders.entrySet()) {
			if (e.getValue().canRespond(requestBody)) {
				return e.getValue();
			}
		}
		System.err.println("For request " + requestBody + " responder is not implemented yet");
		throw new NotImplementedException();
	}
}
