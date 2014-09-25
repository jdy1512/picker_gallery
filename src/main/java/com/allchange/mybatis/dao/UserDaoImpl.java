package com.allchange.mybatis.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.allchange.mybatis.domain.Booking;
import com.allchange.mybatis.domain.ChkFRB;
import com.allchange.mybatis.domain.ChkRES;
import com.allchange.mybatis.domain.Contact;
import com.allchange.mybatis.domain.ContactGetter;
import com.allchange.mybatis.domain.Detail;
import com.allchange.mybatis.domain.Filter;
import com.allchange.mybatis.domain.GB_CheckOut;
import com.allchange.mybatis.domain.GB_CreateAccommodation;
import com.allchange.mybatis.domain.GB_GPSTrace;
import com.allchange.mybatis.domain.GB_Properties;
import com.allchange.mybatis.domain.GB_Signup;
import com.allchange.mybatis.domain.GB_TrippalCreateRoom;
import com.allchange.mybatis.domain.GB_TrippalDeleteRoom;
import com.allchange.mybatis.domain.GB_TrippalGetLocationList;
import com.allchange.mybatis.domain.GB_TrippalGetPeopleList;
import com.allchange.mybatis.domain.GB_TrippalGetRoom;
import com.allchange.mybatis.domain.GB_TrippalGetShoutList;
import com.allchange.mybatis.domain.GB_TrippalLocationUpdate;
import com.allchange.mybatis.domain.GB_TrippalShout;
import com.allchange.mybatis.domain.GB_Visit;
import com.allchange.mybatis.domain.GHList;
import com.allchange.mybatis.domain.GhSearch;
import com.allchange.mybatis.domain.Guest;
import com.allchange.mybatis.domain.Insert;
import com.allchange.mybatis.domain.Login;
import com.allchange.mybatis.domain.MakeRoom;
import com.allchange.mybatis.domain.Master;
import com.allchange.mybatis.domain.Profile;
import com.allchange.mybatis.domain.Reply;
import com.allchange.mybatis.domain.Reserve;
import com.allchange.mybatis.domain.Reserve_idGetter;
import com.allchange.mybatis.domain.Room;
import com.allchange.mybatis.domain.RoomList;
import com.allchange.mybatis.domain.Rule;
import com.allchange.mybatis.domain.Search;
import com.allchange.mybatis.domain.Signup;
import com.allchange.mybatis.domain.SignupConfirm;
import com.allchange.mybatis.domain.Talk;
import com.allchange.mybatis.domain.UpdateRoom;
import com.allchange.mybatis.domain.WriteLastMessage;
import com.allchange.mybatis.domain.WriteMessage;

public class UserDaoImpl implements UserDao {
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<GB_Properties> getProperties(GB_Properties p) {
		// TODO Auto-generated method stub
		List<GB_Properties> list = null;
		try {
			list = sqlSession.selectList("getProperties", p);
			System.out.println("UserDaoImpl_getProperties_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_getProperties_error");
			e.printStackTrace();
			return null;
		}
		return list;
	}

	@Override
	public List<GB_Visit> getVisit(GB_Visit visit) {
		// TODO Auto-generated method stub
		List<GB_Visit> list = null;
		try {
			list = sqlSession.selectList("getVisit", visit);
			System.out.println("UserDaoImpl_getVisit_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_getVisit_error");
			e.printStackTrace();
			return null;
		}
		return list;
	}

	@Override
	public int insert(Insert in) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.insert("insert", in);
			// System.out.println("UserDaoImpl_insert_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_insert_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public int fbSignup(GB_Signup gb_signup) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.insert("fbSignup", gb_signup);
			System.out.println("UserDaoImpl_fbSignup_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_fbSignup_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public List<GB_CreateAccommodation> checkAccommodation(
			GB_CreateAccommodation ca) {
		// TODO Auto-generated method stub
		List<GB_CreateAccommodation> checkAccommodationResult = null;
		try {
			checkAccommodationResult = sqlSession.selectList(
					"checkAccommodation", ca);
			System.out.println("UserDaoImpl_checkAccommodation_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_checkAccommodation_error");
			e.printStackTrace();
			return null;
		}
		return checkAccommodationResult;
	}

	@Override
	public int createAccommodation(GB_CreateAccommodation ca) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.insert("createAccommodation", ca);
			System.out.println("UserDaoImpl_createAccommodation_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_createAccommodation_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public int createAccommodationLog(GB_CreateAccommodation ca) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.insert("createAccommodationLog", ca);
			System.out.println("UserDaoImpl_createAccommodationLog_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_createAccommodationLog_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public int createShoutLog(GB_CreateAccommodation ca) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.insert("createShoutLog", ca);
			System.out.println("UserDaoImpl_createShoutLog_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_createShoutLog_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public int deleteShout(GB_CreateAccommodation ca) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.delete("deleteShout", ca);
			System.out.println("UserDaoImpl_deleteShout_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_deleteShout_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public int updateAccommodation(GB_CreateAccommodation ca) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.insert("updateAccommodation", ca);
			System.out.println("UserDaoImpl_updateAccommodation_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_updateAccommodation_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public int updateAccommodationLastDTime(GB_CreateAccommodation ca) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.insert("updateAccommodationLastDTime", ca);
			System.out
					.println("UserDaoImpl_updateAccommodationLastDTime_success");
		} catch (Exception e) {
			System.err
					.println("UserDaoImpl_updateAccommodationLastDTime_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public List<GB_TrippalGetPeopleList> trippalGetPeopleList(
			GB_TrippalGetPeopleList tgp) {
		// TODO Auto-generated method stub
		List<GB_TrippalGetPeopleList> trippalGetPeopleListResult = null;
		try {
			trippalGetPeopleListResult = sqlSession.selectList(
					"trippalGetPeopleList", tgp);
			System.out.println("UserDaoImpl_trippalGetPeopleList_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_trippalGetPeopleList_error");
			e.printStackTrace();
			return null;
		}
		return trippalGetPeopleListResult;
	}

	@Override
	public List<GB_TrippalGetLocationList> trippalGetLocationList(
			GB_TrippalGetLocationList tgl) {
		// TODO Auto-generated method stub
		List<GB_TrippalGetLocationList> trippalGetLocationListResult = null;
		try {
			trippalGetLocationListResult = sqlSession.selectList(
					"trippalGetLocationList", tgl);
			System.out.println("UserDaoImpl_trippalGetLocationList_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_trippalGetLocationList_error");
			e.printStackTrace();
			return null;
		}
		return trippalGetLocationListResult;
	}

	@Override
	public List<GB_TrippalGetShoutList> trippalGetShoutList(
			GB_TrippalGetShoutList tgs) {
		// TODO Auto-generated method stub
		List<GB_TrippalGetShoutList> trippalGetShoutListResult = null;
		try {
			trippalGetShoutListResult = sqlSession.selectList(
					"trippalGetShoutList", tgs);
			System.out.println("UserDaoImpl_trippalGetShoutList_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_trippalGetShoutList_error");
			e.printStackTrace();
			return null;
		}
		return trippalGetShoutListResult;
	}

	@Override
	public int trippalShout(GB_TrippalShout ts) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.insert("trippalShout", ts);
			System.out.println("UserDaoImpl_trippalShout_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_trippalShout_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public int trippalLocationUpdate(GB_TrippalLocationUpdate tlu) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.update("trippalLocationUpdate", tlu);
			System.out.println("UserDaoImpl_trippalLocationUpdate_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_trippalLocationUpdate_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public int trippalCreateRoom(GB_TrippalCreateRoom tcr) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.insert("trippalCreateRoom", tcr);
			System.out.println("UserDaoImpl_trippalCreateRoom_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_trippalCreateRoom_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public int trippalDeleteRoom(GB_TrippalDeleteRoom tdr) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.delete("trippalDeleteRoom", tdr);
			System.out.println("UserDaoImpl_trippalDeleteRoom_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_trippalDeleteRoom_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public int trippalJoinRoom(GB_TrippalCreateRoom tcr) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.insert("trippalJoinRoom", tcr);
			System.out.println("UserDaoImpl_trippalJoinRoom_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_trippalJoinRoom_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public List<GB_TrippalGetRoom> trippalGetRoom(GB_TrippalGetRoom tgr) {
		// TODO Auto-generated method stub
		List<GB_TrippalGetRoom> trippalGetRoomResult = null;
		try {
			trippalGetRoomResult = sqlSession.selectList("trippalGetRoom", tgr);
			System.out.println("UserDaoImpl_trippalGetRoom_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_trippalGetRoom_error");
			e.printStackTrace();
			return null;
		}
		return trippalGetRoomResult;
	}

	@Override
	public int writeGPSTrace(GB_GPSTrace gt) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.insert("writeGPSTrace", gt);
			System.out.println("UserDaoImpl_writeGPSTrace_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_writeGPSTrace_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public List<GB_GPSTrace> getGPSTrace() {
		// TODO Auto-generated method stub
		List<GB_GPSTrace> list = null;
		try {
			list = sqlSession.selectList("getGPSTrace");
			System.out.println("UserDaoImpl_getGPSTrace_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_getGPSTrace_error");
			e.printStackTrace();
			return null;
		}
		return list;
	}

	@Override
	public int checkOutAccommodation(GB_CheckOut co) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.update("checkOutAccommodation", co);
			System.out.println("UserDaoImpl_checkOutAccommodation_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_checkOutAccommodation_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public int checkOutCreateAccommodationLog(GB_CheckOut co) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.insert("checkOutCreateAccommodationLog", co);
			System.out
					.println("UserDaoImpl_checkOutCreateAccommodationLog_success");
		} catch (Exception e) {
			System.err
					.println("UserDaoImpl_checkOutCreateAccommodationLog_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public List<GB_CheckOut> getCheckOutIdList(GB_CheckOut co) {
		// TODO Auto-generated method stub
		List<GB_CheckOut> getCheckOutIdListResult = null;
		try {
			getCheckOutIdListResult = sqlSession.selectList(
					"getCheckOutIdList", co);
			System.out.println("UserDaoImpl_getCheckOutIdList_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_getCheckOutIdList_error");
			e.printStackTrace();
			return null;
		}
		return getCheckOutIdListResult;
	}

	@Override
	public int checkOutDeleteAccommodation(GB_CheckOut co) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.delete("checkOutDeleteAccommodation", co);
			System.out
					.println("UserDaoImpl_checkOutDeleteAccommodation_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_checkOutDeleteAccommodation_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public int writeMessage(WriteMessage write) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.insert("writeMessage", write);
			System.out.println("UserDaoImpl_writeMessage_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_writeMessage_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public int writeLastMessage(WriteLastMessage write) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.insert("writeLastMessage", write);
			System.out.println("UserDaoImpl_writeLastMessage_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_writeLastMessage_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public int updateLastMessage(WriteLastMessage write) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.insert("updateLastMessage", write);
			System.out.println("UserDaoImpl_updateLastMessage_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_updateLastMessage_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public List<WriteLastMessage> writeLastMessageChk(WriteLastMessage write) {
		// TODO Auto-generated method stub
		List<WriteLastMessage> writeLastMessageChk = null;
		try {
			writeLastMessageChk = sqlSession.selectList("writeLastMessageChk",
					write);
			System.out.println("UserDaoImpl_writeLastMessageChk_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_writeLastMessageChk_error");
			e.printStackTrace();
			return null;
		}
		return writeLastMessageChk;
	}

	@Override
	public List<UpdateRoom> updateRoom(UpdateRoom updateRoom) {
		// TODO Auto-generated method stub
		List<UpdateRoom> messageList = null;
		try {
			messageList = sqlSession.selectList("updateRoom", updateRoom);
			System.out.println("UserDaoImpl_updateRoom_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_updateRoom_error");
			e.printStackTrace();
			return null;
		}
		return messageList;
	}

	@Override
	public int makeRoom(MakeRoom makeRoom) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.insert("makeRoom", makeRoom);
			System.out.println("UserDaoImpl_makeRoom_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_makeRoom_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public List<MakeRoom> getRoomNo(MakeRoom makeRoom) {
		// TODO Auto-generated method stub
		List<MakeRoom> getRoomNo = null;
		try {
			getRoomNo = sqlSession.selectList("getRoomNo", makeRoom);
			System.out.println("UserDaoImpl_getRoomNo_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_getRoomNo_error");
			e.printStackTrace();
			return null;
		}
		return getRoomNo;
	}

	@Override
	public List<MakeRoom> getRoomUserList(MakeRoom makeRoom) {
		// TODO Auto-generated method stub
		List<MakeRoom> getRoomUserList = null;
		try {
			getRoomUserList = sqlSession
					.selectList("getRoomUserList", makeRoom);
			System.out.println("UserDaoImpl_getRoomUserList_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_getRoomUserList_error");
			e.printStackTrace();
			return null;
		}
		return getRoomUserList;
	}

	@Override
	public List<RoomList> getRoomList(String EMAIL_ID) {
		// TODO Auto-generated method stub
		List<RoomList> roomList = null;
		try {
			roomList = sqlSession.selectList("getRoomList", EMAIL_ID);
			System.out.println("UserDaoImpl_getRoomList_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_getRoomList_error");
			e.printStackTrace();
			return null;
		}
		return roomList;
	}

	@Override
	public List<GhSearch> ghSearch(GhSearch ghSearch) {
		// TODO Auto-generated method stub
		List<GhSearch> list = null;
		try {
			list = sqlSession.selectList("ghSearch", ghSearch);
			System.out.println("UserDaoImpl_ghSearch_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_ghSearch_error");
			e.printStackTrace();
			return null;
		}
		return list;
	}

	@Override
	public List<Guest> getRegId(String ID) {
		// TODO Auto-generated method stub
		List<Guest> user = null;
		try {
			user = sqlSession.selectList("getRegId", ID);
			System.out.println("UserDaoImpl_getRegId_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_getRegId_error");
			e.printStackTrace();
			return null;
		}
		return user;
	}

	@Override
	public List<Guest> getRegIdCheckoutMember(String DATE) {
		// TODO Auto-generated method stub
		List<Guest> user = null;
		try {
			user = sqlSession.selectList("getRegIdCheckoutMember", DATE);
			System.out.println("UserDaoImpl_getRegIdCheckoutMember_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_getRegIdCheckoutMember_error");
			e.printStackTrace();
			return null;
		}
		return user;
	}

	@Override
	public int updateStateFg(String DATE) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.update("updateStateFg", DATE);
			System.out.println("UserDaoImpl_updateStateFg_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_updateStateFg_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public int checkout(String DTIME) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.update("checkout", DTIME);
			System.out.println("UserDaoImpl_checkout_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_checkout_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public int signup(Signup signup) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.insert("signup", signup);
			System.out.println("UserDaoImpl_signup_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_signup_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public List<SignupConfirm> signupConfirm(SignupConfirm signupConfirm) {
		// TODO Auto-generated method stub
		List<SignupConfirm> signupConfirmList = null;
		try {
			signupConfirmList = sqlSession.selectList("signupConfirm",
					signupConfirm);
			System.out.println("UserDaoImpl_signupConfirm_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_signupConfirm_error");
			e.printStackTrace();
			return null;
		}
		return signupConfirmList;
	}

	@Override
	public List<Login> login(Login login) {
		// TODO Auto-generated method stub
		List<Login> user = null;
		try {
			user = sqlSession.selectList("login", login);
			System.out.println("UserDaoImpl_login_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_login_error");
			e.printStackTrace();
			return null;
		}
		return user;
	}

	@Override
	public int setRegid(Login login) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.update("setRegid", login);
			System.out.println("UserDaoImpl_setRegid_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_setRegid_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public List<ChkFRB> chkFRB(Reserve reserve) {
		// TODO Auto-generated method stub
		List<ChkFRB> chkFRB = null;
		try {
			chkFRB = sqlSession.selectList("chkFRB", reserve);
			System.out.println("UserDaoImpl_chkFRB_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_chkFRB_error");
			e.printStackTrace();
			return null;
		}
		return chkFRB;
	}

	@Override
	public List<ChkFRB> chkResFRB(Reserve reserve) {
		// TODO Auto-generated method stub
		List<ChkFRB> chkResFRB = null;
		try {
			chkResFRB = sqlSession.selectList("chkResFRB", reserve);
			System.out.println("UserDaoImpl_chkResFRB_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_chkResFRB_error");
			e.printStackTrace();
			return null;
		}
		return chkResFRB;
	}

	@Override
	public int checkinWalk(Reserve reserve) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.insert("checkinWalk", reserve);
			System.out.println("UserDaoImpl_checkinWalk_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_checkinWalk_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public int checkinRes(Reserve reserve) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.update("checkinRes", reserve);
			System.out.println("UserDaoImpl_checkinRes_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_checkinRes_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public List<Reserve> chkResDate(Reserve reserve) {
		// TODO Auto-generated method stub
		List<Reserve> chkResDate = null;
		try {
			chkResDate = sqlSession.selectList("chkResDate", reserve);
			System.out.println("UserDaoImpl_chkResDate_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_chkResDate_error");
			e.printStackTrace();
			return null;
		}
		return chkResDate;
	}

	@Override
	public List<Reserve> chkResId(Reserve reserve) {
		// TODO Auto-generated method stub
		List<Reserve> chkResId = null;
		try {
			chkResId = sqlSession.selectList("chkResId", reserve);
			System.out.println("UserDaoImpl_chkResId_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_chkResId_error");
			e.printStackTrace();
			return null;
		}
		return chkResId;
	}

	@Override
	public List<Reserve_idGetter> getReserveId(Reserve reserve) {
		// TODO Auto-generated method stub
		List<Reserve_idGetter> reserve_idGetter = null;
		try {
			reserve_idGetter = sqlSession.selectList("getReserveId", reserve);
			System.out.println("UserDaoImpl_getReserveId_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_getReserveId_error");
			e.printStackTrace();
			return null;
		}
		return reserve_idGetter;
	}

	@Override
	public List<Rule> rule(Reserve reserve) {
		// TODO Auto-generated method stub
		List<Rule> rule = null;
		try {
			rule = sqlSession.selectList("rule", reserve);
			System.out.println("UserDaoImpl_rule_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_rule_error");
			e.printStackTrace();
			return null;
		}
		return rule;
	}

	@Override
	public List<Room> room(Reserve reserve) {
		// TODO Auto-generated method stub
		List<Room> room = null;
		try {
			room = sqlSession.selectList("room", reserve);
			System.out.println("UserDaoImpl_room_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_room_error");
			e.printStackTrace();
			return null;
		}
		return room;
	}

	@Override
	public List<Profile> profile(String EMAIL_ID) {
		// TODO Auto-generated method stub
		List<Profile> profile = null;
		try {
			profile = sqlSession.selectList("profile", EMAIL_ID);
			System.out.println("UserDaoImpl_profile_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_profile_error");
			e.printStackTrace();
			return null;
		}
		return profile;
	}

	@Override
	public int profileUpdate(Profile profile) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.update("profileUpdate", profile);
			System.out.println("UserDaoImpl_profileUpdate_success");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("UserDaoImpl_profileUpdate_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public List<Reserve> chkRES(ChkRES mChkRES) {
		// TODO Auto-generated method stub
		List<Reserve> reserveResult = null;
		try {
			reserveResult = sqlSession.selectList("chkRES", mChkRES);
			System.out.println("UserDaoImpl_chkRES_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_chkRES_error");
			e.printStackTrace();
			return null;
		}
		return reserveResult;
	}

	@Override
	public List<Talk> talk(Reserve reserve) {
		// TODO Auto-generated method stub
		List<Talk> talk = null;
		try {
			talk = sqlSession.selectList("talk", reserve);
			System.out.println("UserDaoImpl_talk_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_talk_error");
			e.printStackTrace();
			return null;
		}
		return talk;
	}

	@Override
	public int reply(Reply reply) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.insert("reply", reply);
			System.out.println("UserDaoImpl_reply_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_reply_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public List<Search> search() {
		// TODO Auto-generated method stub
		List<Search> search = null;
		try {
			search = sqlSession.selectList("search");
			System.out.println("UserDaoImpl_search_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_search_error");
			e.printStackTrace();
			return null;
		}
		return search;
	}

	@Override
	public List<GHList> ghList(Filter filter) {
		// TODO Auto-generated method stub
		List<GHList> ghList = null;
		try {
			ghList = sqlSession.selectList("ghList", filter);
			System.out.println("UserDaoImpl_ghList_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_ghList_error");
			e.printStackTrace();
			return null;
		}
		return ghList;
	}

	@Override
	public List<GHList> ghListInterest(Filter filter) {
		// TODO Auto-generated method stub
		List<GHList> ghListInterest = null;
		try {
			ghListInterest = sqlSession.selectList("ghListInterest", filter);
			System.out.println("UserDaoImpl_ghListInterest_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_ghListInterest_error");
			e.printStackTrace();
			return null;
		}
		return ghListInterest;
	}

	@Override
	public List<Detail> detail(Detail detail) {
		// TODO Auto-generated method stub
		List<Detail> detailList = null;
		try {
			detailList = sqlSession.selectList("detail", detail);
			System.out.println("UserDaoImpl_detail_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_detail_error");
			e.printStackTrace();
			return null;
		}
		return detailList;
	}

	@Override
	public List<Booking> ghBooking(Filter filter) {
		// TODO Auto-generated method stub
		List<Booking> booking = null;
		try {
			booking = sqlSession.selectList("ghBooking", filter);
			System.out.println("UserDaoImpl_ghBooking_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_ghBooking_error");
			e.printStackTrace();
			return null;
		}
		return booking;
	}

	@Override
	public List<Booking> ghBookingInterest(Filter filter) {
		// TODO Auto-generated method stub
		List<Booking> booking = null;
		try {
			booking = sqlSession.selectList("ghBookingInterest", filter);
			System.out.println("UserDaoImpl_ghBookingInterest_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_ghBookingInterest_error");
			e.printStackTrace();
			return null;
		}
		return booking;
	}

	@Override
	public int bookingContact(Contact contact) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = sqlSession.insert("bookingContact", contact);
			System.out.println("UserDaoImpl_bookingContact_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_bookingContact_error");
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	@Override
	public List<Contact> chkContact(Contact contact) {
		// TODO Auto-generated method stub
		List<Contact> contactList = null;
		try {
			contactList = sqlSession.selectList("chkContact", contact);
			System.out.println("UserDaoImpl_chkContact_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_chkContact_error");
			e.printStackTrace();
			return null;
		}
		return contactList;
	}

	@Override
	public List<ContactGetter> contactGetter(Contact contact) {
		// TODO Auto-generated method stub
		List<ContactGetter> contactGetter = null;
		try {
			contactGetter = sqlSession.selectList("contactGetter", contact);
			System.out.println("UserDaoImpl_contactGetter_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_contactGetter_error");
			e.printStackTrace();
			return null;
		}
		return contactGetter;
	}

	@Override
	public List<Master> getMasterRegId(Master master) {
		// TODO Auto-generated method stub
		List<Master> masterResult = null;
		try {
			masterResult = sqlSession.selectList("getMasterRegId", master);
			System.out.println("UserDaoImpl_getMasterRegId_success");
		} catch (Exception e) {
			System.err.println("UserDaoImpl_getMasterRegId_error");
			e.printStackTrace();
			return null;
		}
		return masterResult;
	}

}
