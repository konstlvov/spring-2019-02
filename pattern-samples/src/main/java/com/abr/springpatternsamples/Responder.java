package com.abr.springpatternsamples;

import org.springframework.beans.factory.annotation.Autowired;

public interface Responder {
	public String getResponse();
	public String getRqType();
	public boolean canRespond(String requestBody);

	@Autowired // setter injection
	default void registerMyself(ResponderRegistry reg) {
		reg.registerResponder(getRqType(), this);
	}
}
