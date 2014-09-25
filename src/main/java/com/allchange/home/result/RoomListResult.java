package com.allchange.home.result;

import java.util.ArrayList;
import java.util.HashMap;

public class RoomListResult {
	private String result;
	private String result_msg;
	private ArrayList<HashMap<String, Object>> room_list;

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

	public ArrayList<HashMap<String, Object>> getRoom_list() {
		return room_list;
	}

	public void setRoom_list(ArrayList<HashMap<String, Object>> room_list) {
		this.room_list = room_list;
	}

}
