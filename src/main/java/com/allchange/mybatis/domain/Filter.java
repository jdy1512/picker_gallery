package com.allchange.mybatis.domain;

import java.util.HashMap;

public class Filter {
	private String GH_NO;
	private String NATION;
	private String CITY;
	private String RESERVE_DATE;
	private String CHECK_OUTDATE;
	private String GROUP;
	private String MIN_PRICE;
	private String MAX_PRICE;
	private HashMap<String, Object> INTEREST;

	public String getGH_NO() {
		return GH_NO;
	}

	public void setGH_NO(String gH_NO) {
		GH_NO = gH_NO;
	}

	public String getNATION() {
		return NATION;
	}

	public void setNATION(String nATION) {
		NATION = nATION;
	}

	public String getCITY() {
		return CITY;
	}

	public void setCITY(String cITY) {
		CITY = cITY;
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

	public String getGROUP() {
		return GROUP;
	}

	public void setGROUP(String gROUP) {
		GROUP = gROUP;
	}

	public String getMIN_PRICE() {
		return MIN_PRICE;
	}

	public void setMIN_PRICE(String mIN_PRICE) {
		MIN_PRICE = mIN_PRICE;
	}

	public String getMAX_PRICE() {
		return MAX_PRICE;
	}

	public void setMAX_PRICE(String mAX_PRICE) {
		MAX_PRICE = mAX_PRICE;
	}

	public HashMap<String, Object> getINTEREST() {
		return INTEREST;
	}

	public void setINTEREST(HashMap<String, Object> iNTEREST) {
		INTEREST = iNTEREST;
	}

}