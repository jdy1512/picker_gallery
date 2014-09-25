package com.allchange.mybatis.domain;

public class Reply {
	private String TARGET_RES_SEQ;
	private String REPLY_RES_SEQ;
	private String REPLY_TEXT;
	private String REPLY_DTIME;

	public String getTARGET_RES_SEQ() {
		return TARGET_RES_SEQ;
	}

	public void setTARGET_RES_SEQ(String tARGET_RES_SEQ) {
		TARGET_RES_SEQ = tARGET_RES_SEQ;
	}

	public String getREPLY_RES_SEQ() {
		return REPLY_RES_SEQ;
	}

	public void setREPLY_RES_SEQ(String rEPLY_RES_SEQ) {
		REPLY_RES_SEQ = rEPLY_RES_SEQ;
	}

	public String getREPLY_TEXT() {
		return REPLY_TEXT;
	}

	public void setREPLY_TEXT(String rEPLY_TEXT) {
		REPLY_TEXT = rEPLY_TEXT;
	}

	public String getREPLY_DTIME() {
		return REPLY_DTIME;
	}

	public void setREPLY_DTIME(String rEPLY_DTIME) {
		REPLY_DTIME = rEPLY_DTIME;
	}

}