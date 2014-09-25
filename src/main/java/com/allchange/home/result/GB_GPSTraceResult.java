package com.allchange.home.result;

import java.util.ArrayList;
import java.util.HashMap;

public class GB_GPSTraceResult {
	private String result;
	private String result_msg;
	private ArrayList<HashMap<String, Object>> trace;

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

	public ArrayList<HashMap<String, Object>> getTrace() {
		return trace;
	}

	public void setTrace(ArrayList<HashMap<String, Object>> trace) {
		this.trace = trace;
	}

}
