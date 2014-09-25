package com.allchange.home.result;

import java.util.ArrayList;
import java.util.HashMap;

public class GHListResult {
	private String result;
	private String result_msg;
	private ArrayList<HashMap<String, Object>> list;

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

	public ArrayList<HashMap<String, Object>> getList() {
		return list;
	}

	public void setList(ArrayList<HashMap<String, Object>> list) {
		this.list = list;
	}

}
