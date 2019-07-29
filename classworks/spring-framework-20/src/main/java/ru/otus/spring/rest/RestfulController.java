package ru.otus.spring.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RestfulController {
	@RequestMapping("/greeting")
	public Map<String, Object> greeting() {
		Map<String, Object> m = new HashMap<>();
		m.put("id", "1");
		m.put("content", "Hello world!");
		return m;
	}

}
