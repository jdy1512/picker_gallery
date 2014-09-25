package com.allchange.mybatis.domain;

public class WriteLastMessage {
	private String SENDER_ID;
	private String LAST_MESSAGE;
	private String LAST_MESSAGE_DTIME;

	public String getSENDER_ID() {
		return SENDER_ID;
	}

	public void setSENDER_ID(String sENDER_ID) {
		SENDER_ID = sENDER_ID;
	}

	public String getLAST_MESSAGE() {
		return LAST_MESSAGE;
	}

	public void setLAST_MESSAGE(String lAST_MESSAGE) {
		LAST_MESSAGE = lAST_MESSAGE;
	}

	public String getLAST_MESSAGE_DTIME() {
		return LAST_MESSAGE_DTIME;
	}

	public void setLAST_MESSAGE_DTIME(String lAST_MESSAGE_DTIME) {
		LAST_MESSAGE_DTIME = lAST_MESSAGE_DTIME;
	}

}
