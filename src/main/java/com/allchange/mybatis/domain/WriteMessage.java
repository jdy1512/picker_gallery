package com.allchange.mybatis.domain;

public class WriteMessage {
	private String SENDER_ID;
	private String MESSAGE;
	private String MESSAGE_DTIME;

	public String getSENDER_ID() {
		return SENDER_ID;
	}

	public void setSENDER_ID(String sENDER_ID) {
		SENDER_ID = sENDER_ID;
	}

	public String getMESSAGE() {
		return MESSAGE;
	}

	public void setMESSAGE(String mESSAGE) {
		MESSAGE = mESSAGE;
	}

	public String getMESSAGE_DTIME() {
		return MESSAGE_DTIME;
	}

	public void setMESSAGE_DTIME(String mESSAGE_DTIME) {
		MESSAGE_DTIME = mESSAGE_DTIME;
	}

}
