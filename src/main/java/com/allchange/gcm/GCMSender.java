package com.allchange.gcm;

import java.io.IOException;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class GCMSender {
	private static GCMSender gcmSender;

	public static GCMSender instance() {
		if (gcmSender == null) {
			gcmSender = new GCMSender();
		}
		return gcmSender;
	}

	public void sendPush(String regId, String type, String message) {
		Sender sender = new Sender("AIzaSyCH8yehY9bevZ6XZQE7vQpl3Q6xlHPvCB0");
		Message msg = new Message.Builder().addData("type", type)
				.addData("message", message).build();

		// 푸시 전송. 파라미터는 푸시 내용, 보낼 단말의 id, 마지막은 잘 모르겠음
		Result result = null;
		try {
			result = sender.send(msg, regId, 5);
			// 결과 처리
			if (result.getMessageId() != null) {
				// 푸시 전송 성공
				System.err.println("push success");
				System.err.println("message : " + message);
			} else {
				String error = result.getErrorCodeName(); // 에러 내용 받기
				// 에러 처리
				if (Constants.ERROR_INTERNAL_SERVER_ERROR.equals(error)) {
					System.err.println("google server error");
				}
			}
		} catch (IOException e) {
			System.err.println("push error");
			e.printStackTrace();
		}
	}
}
