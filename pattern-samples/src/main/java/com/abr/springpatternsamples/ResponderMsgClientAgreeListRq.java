package com.abr.springpatternsamples;

import org.springframework.stereotype.Component;

@Component("2")
public class ResponderMsgClientAgreeListRq implements Responder {

	public boolean canRespond(String requestBody) {
		return requestBody.contains("<MsgClientAgreeListRq>");
	}

	public String getResponse() {

		return "will respond with MsgClientAgreeListRs message";
	}

	public String getRqType() {
		return "MsgClientAgreeListRq";

	}

}
