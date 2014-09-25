package com.allchange.home.result;

import java.util.ArrayList;
import java.util.HashMap;

public class UpdateRoomResult {
	private String result;
	private String result_msg;
	private ArrayList<HashMap<String, Object>> message_list;

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

	public ArrayList<HashMap<String, Object>> getMessage_list() {
		return message_list;
	}

	public void setMessage_list(ArrayList<HashMap<String, Object>> message_list) {
		this.message_list = message_list;
	}

}
