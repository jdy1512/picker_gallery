package com.allchange.home.result;

import java.util.ArrayList;
import java.util.HashMap;

public class BookingResult {

	private String result;
	private String result_msg;
	private String gh_no;
	private String gh_name;
	private String gh_address;
	private ArrayList<HashMap<String, Object>> floor;

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

	public String getGh_no() {
		return gh_no;
	}

	public void setGh_no(String gh_no) {
		this.gh_no = gh_no;
	}

	public String getGh_name() {
		return gh_name;
	}

	public void setGh_name(String gh_name) {
		this.gh_name = gh_name;
	}

	public String getGh_address() {
		return gh_address;
	}

	public void setGh_address(String gh_address) {
		this.gh_address = gh_address;
	}

	public ArrayList<HashMap<String, Object>> getFloor() {
		return floor;
	}

	public void setFloor(ArrayList<HashMap<String, Object>> floor) {
		this.floor = floor;
	}

}
