package com.allchange.mybatis.domain;

public class GB_TrippalGetShoutList {
	private String EMAIL_ID;
	private String SENDER_ID;
	private String GUEST_FNM;
	private String GUEST_LNM;
	private String MESSAGE;
	private String MESSAGE_DTIME;
	private String PROFILE_IMG_PATH;

	public String getEMAIL_ID() {
		return EMAIL_ID;
	}

	public void setEMAIL_ID(String eMAIL_ID) {
		EMAIL_ID = eMAIL_ID;
	}

	public String getSENDER_ID() {
		return SENDER_ID;
	}

	public void setSENDER_ID(String sENDER_ID) {
		SENDER_ID = sENDER_ID;
	}

	public String getGUEST_FNM() {
		return GUEST_FNM;
	}

	public void setGUEST_FNM(String gUEST_FNM) {
		GUEST_FNM = gUEST_FNM;
	}

	public String getGUEST_LNM() {
		return GUEST_LNM;
	}

	public void setGUEST_LNM(String gUEST_LNM) {
		GUEST_LNM = gUEST_LNM;
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

	public String getPROFILE_IMG_PATH() {
		return PROFILE_IMG_PATH;
	}

	public void setPROFILE_IMG_PATH(String pROFILE_IMG_PATH) {
		PROFILE_IMG_PATH = pROFILE_IMG_PATH;
	}

}