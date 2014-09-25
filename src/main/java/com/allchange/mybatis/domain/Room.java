package com.allchange.mybatis.domain;

public class Room {
	private String EMAIL_ID;
	private String SEX;
	private String RESERVE_DATE;
	private String CHECK_OUTDATE;

	public String getEMAIL_ID() {
		return EMAIL_ID;
	}

	public void setEMAIL_ID(String eMAIL_ID) {
		EMAIL_ID = eMAIL_ID;
	}

	public String getSEX() {
		return SEX;
	}

	public void setSEX(String sEX) {
		SEX = sEX;
	}

	public String getRESERVE_DATE() {
		return RESERVE_DATE;
	}

	public void setRESERVE_DATE(String rESERVE_DATE) {
		RESERVE_DATE = rESERVE_DATE;
	}

	public String getCHECK_OUTDATE() {
		return CHECK_OUTDATE;
	}

	public void setCHECK_OUTDATE(String cHECK_OUTDATE) {
		CHECK_OUTDATE = cHECK_OUTDATE;
	}

}