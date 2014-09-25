package com.allchange.mybatis.domain;

public class Login {
	private String EMAIL_ID;
	private String EMAIL_PWD;
	private String GUEST_FNM;
	private String GUEST_LNM;
	private String RES_SEQ;
	private String RESERVE_DATE;
	private String REG_ID;

	public String getEMAIL_ID() {
		return EMAIL_ID;
	}

	public void setEMAIL_ID(String eMAIL_ID) {
		EMAIL_ID = eMAIL_ID;
	}

	public String getEMAIL_PWD() {
		return EMAIL_PWD;
	}

	public void setEMAIL_PWD(String eMAIL_PWD) {
		EMAIL_PWD = eMAIL_PWD;
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

	public String getRES_SEQ() {
		return RES_SEQ;
	}

	public void setRES_SEQ(String rES_SEQ) {
		RES_SEQ = rES_SEQ;
	}

	public String getRESERVE_DATE() {
		return RESERVE_DATE;
	}

	public void setRESERVE_DATE(String rESERVE_DATE) {
		RESERVE_DATE = rESERVE_DATE;
	}

	public String getREG_ID() {
		return REG_ID;
	}

	public void setREG_ID(String rEG_ID) {
		REG_ID = rEG_ID;
	}

}