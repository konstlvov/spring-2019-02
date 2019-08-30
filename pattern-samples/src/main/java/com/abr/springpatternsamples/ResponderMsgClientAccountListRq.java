package com.abr.springpatternsamples;

import org.springframework.stereotype.Component;

@Component("1")
public class ResponderMsgClientAccountListRq implements Responder {

	public boolean canRespond(String requestBody) {
		return requestBody.contains("<MsgClientAccountListRq>");
	}

	public String getResponse() {
		return "will respond with MsgClientAccountListRs message";
	}

	public String getRqType() {
		return "MsgClientAccountListRq";
	}
}
