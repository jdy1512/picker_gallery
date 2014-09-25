package com.allchange.timer;

import java.util.List;
import java.util.TimerTask;

import com.allchange.dtime.Dtime;
import com.allchange.home.HomeController;
import com.allchange.mybatis.dao.UserDaoImpl;
import com.allchange.mybatis.domain.GB_CheckOut;
import com.allchange.mybatis.domain.GB_CreateAccommodation;

public class CheckoutTimer extends TimerTask {
	private UserDaoImpl userDaoImpl;

	public CheckoutTimer(UserDaoImpl dao) {
		userDaoImpl = dao;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String dTime = Dtime.instance().getGMTDateTime();
		// if (dtime.substring(8, 10).equals("11")) {
		// userDaoImpl.checkout(dtime);
		// userDaoImpl.updateStateFg(date);
		// List<Guest> guestRegId = userDaoImpl.getRegIdCheckoutMember(date);
		// String regId = "";
		// for (int i = 0; i < guestRegId.size(); i++) {
		// regId = guestRegId.get(i).getREG_ID();
		// GCMSender.instance().sendPush(regId, "체크아웃 알림", "체크아웃되었습니다");
		// }
		// }
		if (!HomeController.curHour.equals("11") && dTime.substring(8, 10).equals("11")) {
			HomeController.curHour = dTime.substring(8, 10);
			
			// checkout info change
			GB_CheckOut co = new GB_CheckOut();
			co.setGB_CHECK_OUTDATE(dTime.substring(0, 8));
			co.setGB_LAST_DTIME(dTime);
			co.setGB_LAST_ID("allchange");

			// checkout 대상 id 리스트 추출
			List<GB_CheckOut> checkOutIdList = userDaoImpl.getCheckOutIdList(co);

			// checkout 상태 업데이트
			userDaoImpl.checkOutAccommodation(co);

			// create accommodation log
			userDaoImpl.checkOutCreateAccommodationLog(co);

			// delete accommodation
			userDaoImpl.checkOutDeleteAccommodation(co);

			if(checkOutIdList != null && checkOutIdList.size() > 0){
				for(GB_CheckOut checkOut: checkOutIdList){
					GB_CreateAccommodation ca = new GB_CreateAccommodation();
					ca.setGB_EMAIL_ID(checkOut.getGB_EMAIL_ID());

					// create shout log
					userDaoImpl.createShoutLog(ca);

					// delete shout
					userDaoImpl.deleteShout(ca);
				}
			}
		} else if(HomeController.curHour.equals("11") && dTime.substring(8, 10).equals("12")){
			HomeController.curHour = "";
		}
	}
}
