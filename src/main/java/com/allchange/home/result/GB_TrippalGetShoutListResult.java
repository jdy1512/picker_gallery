package com.allchange.home.result;

import java.util.ArrayList;
import java.util.HashMap;

public class GB_TrippalGetShoutListResult {
	private String result;
	private String result_msg;
	private ArrayList<HashMap<String, Object>> shout;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResult_msg() {
		return result_msg;
	}

	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}

	public ArrayList<HashMap<String, Object>> getShout() {
		return shout;
	}

	public void setShout(ArrayList<HashMap<String, Object>> shout) {
		this.shout = shout;
	}

}
