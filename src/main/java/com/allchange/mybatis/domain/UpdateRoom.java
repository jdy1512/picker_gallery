package com.allchange.mybatis.domain;

public class UpdateRoom {
	private String ROOM_NO;
	private String SENDER_ID;
	private String MESSAGE;
	private String MESSAGE_DTIME;

	public String getROOM_NO() {
		return ROOM_NO;
	}

	public void setROOM_NO(String rOOM_NO) {
		ROOM_NO = rOOM_NO;
	}

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
