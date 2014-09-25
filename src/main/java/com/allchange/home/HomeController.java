package com.allchange.home;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.allchange.dtime.Dtime;
import com.allchange.gcm.GCMSender;
import com.allchange.home.result.BookingResult;
import com.allchange.home.result.CheckinResult;
import com.allchange.home.result.ContactResult;
import com.allchange.home.result.DefaultResult;
import com.allchange.home.result.DetailResult;
import com.allchange.home.result.GB_FacebookResult;
import com.allchange.home.result.GB_GPSTraceResult;
import com.allchange.home.result.GB_PropertiesResult;
import com.allchange.home.result.GB_TrippalGetLocationListResult;
import com.allchange.home.result.GB_TrippalGetPeopleListResult;
import com.allchange.home.result.GB_TrippalGetRoomResult;
import com.allchange.home.result.GB_TrippalGetShoutListResult;
import com.allchange.home.result.GB_VisitResult;
import com.allchange.home.result.GHListResult;
import com.allchange.home.result.ListResult;
import com.allchange.home.result.LoginResult;
import com.allchange.home.result.MakeRoomResult;
import com.allchange.home.result.ProfileResult;
import com.allchange.home.result.RoomListResult;
import com.allchange.home.result.RoomResult;
import com.allchange.home.result.RuleResult;
import com.allchange.home.result.SearchResult;
import com.allchange.home.result.TalkResult;
import com.allchange.home.result.UpdateRoomResult;
import com.allchange.mybatis.dao.UserDaoImpl;
import com.allchange.mybatis.domain.Booking;
import com.allchange.mybatis.domain.ChkFRB;
import com.allchange.mybatis.domain.ChkRES;
import com.allchange.mybatis.domain.Contact;
import com.allchange.mybatis.domain.ContactGetter;
import com.allchange.mybatis.domain.Detail;
import com.allchange.mybatis.domain.Filter;
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
import com.allchange.timer.CheckoutTimer;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Resource(name = "userDao")
	private UserDaoImpl userDaoImpl;
	private static final String SERVER_URL = "http://ec2-54-186-141-25.us-west-2.compute.amazonaws.com/allchange/";
	private static final String PROFILE_IMG_PATH = "uploads/user/";
	private static final String GUESTHOUSE_IMG_PATH = "uploads/guesthouse/";

	private static final int SESSION_MAX_INTERVAL = 60 * 60;

	private static final String apiKey = "223694251160718";
	private static final String secret = "5df0dbf254496b9cc30dae13b0ac7cba";

	public static String curHour;

	public HomeController() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(1000 * 60);
					curHour = "";
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				new Timer().schedule(new CheckoutTimer(userDaoImpl), 0, 1000 * 10);
			}
		}).start();
	}

	public String errorCheckInParams(HashMap<String, Object> params,
			String[] mAttr, String[] subAttr) {
		if (params.size() > 0) {
			for (int i = 0; i < mAttr.length; i++) {
				if (!params.containsKey(mAttr[i])) {
					return "Attribute mismatch";
				} else {
					if (params.get(mAttr[i]) == null) {
						return mAttr[i] + " is null";
					}
				}
			}
			for (int i = 0; i < subAttr.length; i++) {
				if (params.containsKey(subAttr[i])) {
					if (params.get(subAttr[i]) == null) {
						return subAttr[i] + " is null";
					}
				}
			}
		} else if (mAttr.length > 0) {
			return "Param is null";
		}
		return null;
	}

	@RequestMapping("/")
	public ModelAndView root() {
		String message = "ALLCHANGE SOFT";
		return new ModelAndView("home", "message", message);
		// return req.getSession().getServletContext().getRealPath("/");
	}

	// @RequestMapping(value = "/insert", method = RequestMethod.GET)
	// public ModelAndView insert() {
	// String message = "ALLCHANGE SOFT";
	// new Thread(new Runnable() {
	// public void run() {
	// BufferedReader br = null;
	// try {
	// File file = new File("C:/Users/wleodud/Desktop", "한국.txt");
	// FileReader fr = new FileReader(file);
	// br = new BufferedReader(fr);
	// String str = null;
	// String temp = "";
	// while ((str = br.readLine()) != null) {
	// String[] split = str.split("&!#@&");
	// temp += split[0];
	// if (split.length == 1) {
	// temp += " ";
	// continue;
	// }
	// split[0] = temp;
	// temp = "";
	// if (split.length != 5) {
	// continue;
	// }
	// com.allchange.mybatis.domain.Insert in = new
	// com.allchange.mybatis.domain.Insert();
	// in.setGH_NM(split[0]);
	// in.setGH_LATITUDE(split[1]);
	// in.setGH_LONGITUDE(split[2]);
	// in.setGH_OWNER_CD(split[3]);
	// in.setGH_ROOM_CD(split[4]);
	// in.setGH_CHANNEL("airbnb");
	// userDaoImpl.insert(in);
	// System.out.println("name : " + split[0] + " , lat : "
	// + split[1] + " , lon : " + split[2]
	// + " , owner : " + split[3] + " , room : "
	// + split[4]);
	// str = null;
	// }
	//
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// if (br != null) {
	// try {
	// br.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }
	// }).start();
	// return new ModelAndView("home", "message", message);
	// }

	@RequestMapping(value = "/properties", method = RequestMethod.GET)
	@ResponseBody
	public GB_PropertiesResult getProperties(HttpServletRequest req) {
		GB_PropertiesResult pr_result = new GB_PropertiesResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			pr_result.setResult("fail");
			pr_result.setResult_msg("session null");
			return pr_result;
		}

		GB_Properties p = new GB_Properties();
		p.setEMAIL_ID((String) session.getAttribute("user_id"));

		List<GB_Properties> getPropertiesResult = userDaoImpl.getProperties(p);

		if (getPropertiesResult != null && getPropertiesResult.size() > 0) {
			pr_result.setUser_email(getPropertiesResult.get(0).getEMAIL_ID());
			pr_result.setUser_firstname(getPropertiesResult.get(0)
					.getGUEST_FNM());
			pr_result.setUser_lastname(getPropertiesResult.get(0)
					.getGUEST_LNM());
			pr_result.setUser_nationality(getPropertiesResult.get(0)
					.getNATIONALITY());
			pr_result.setUser_city(getPropertiesResult.get(0).getCITY());
			pr_result.setUser_birthday(getPropertiesResult.get(0)
					.getBIRTH_DAY());
			pr_result.setUser_sex(getPropertiesResult.get(0).getSEX());
			pr_result.setUser_profile_img_path(getPropertiesResult.get(0)
					.getPROFILE_IMG_PATH());
			pr_result.setGb_name(getPropertiesResult.get(0).getGB_NM());
			pr_result.setGb_address(getPropertiesResult.get(0).getGB_ADDRESS());
			pr_result.setGb_for(getPropertiesResult.get(0).getGB_FOR());
			pr_result.setGb_with(getPropertiesResult.get(0).getGB_WITH());
			pr_result.setGb_seeking(getPropertiesResult.get(0).getGB_SEEKING());
			pr_result.setGb_check_indate(getPropertiesResult.get(0)
					.getGB_CHECK_INDATE());
			pr_result.setGb_check_outdate(getPropertiesResult.get(0)
					.getGB_CHECK_OUTDATE());
			pr_result.setGb_latitude(getPropertiesResult.get(0)
					.getGB_LATITUDE());
			pr_result.setGb_longitude(getPropertiesResult.get(0)
					.getGB_LONGITUDE());
			pr_result.setGb_lat_start(getPropertiesResult.get(0)
					.getGB_LAT_START());
			pr_result.setGb_lon_start(getPropertiesResult.get(0)
					.getGB_LON_START());
			pr_result.setGb_lat_end(getPropertiesResult.get(0).getGB_LAT_END());
			pr_result.setGb_lon_end(getPropertiesResult.get(0).getGB_LON_END());
			pr_result.setGb_channel(getPropertiesResult.get(0).getGB_CHANNEL());
			pr_result.setResult("success");
			pr_result.setResult_msg("success");
			return pr_result;
		} else if (getPropertiesResult != null
				&& getPropertiesResult.size() == 0) {
			pr_result.setResult("fail");
			pr_result.setResult_msg("not found");
			return pr_result;
		} else {
			pr_result.setResult("fail");
			pr_result.setResult_msg("unknown error");
			return pr_result;
		}
	}// getProperties

	@RequestMapping(value = "/visit", method = RequestMethod.POST)
	@ResponseBody
	public GB_VisitResult getVisit(
			@RequestParam HashMap<String, Object> params, HttpServletRequest req) {
		GB_VisitResult v_result = new GB_VisitResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			v_result.setResult("fail");
			v_result.setResult_msg("session null");
			return v_result;
		}
		String[] mAttribute = { "user_email_id" };
		String[] subAttribute = {};
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			v_result.setResult("fail");
			v_result.setResult_msg(chkResult);
			return v_result;
		}

		GB_Visit visit = new GB_Visit();
		visit.setGB_EMAIL_ID((String) params.get("user_email_id"));

		List<GB_Visit> getVisitResult = userDaoImpl.getVisit(visit);

		if (getVisitResult != null && getVisitResult.size() > 0) {
			v_result.setUser_accommodation(getVisitResult.get(0).getGB_NM());
			v_result.setUser_check_indate(getVisitResult.get(0)
					.getGB_CHECK_INDATE());
			v_result.setUser_check_outdate(getVisitResult.get(0)
					.getGB_CHECK_OUTDATE());
			v_result.setUser_visit_for(getVisitResult.get(0).getGB_FOR());
			v_result.setUser_visit_with(getVisitResult.get(0).getGB_WITH());
			v_result.setUser_visit_seeking(getVisitResult.get(0)
					.getGB_SEEKING());
			v_result.setUser_channel(getVisitResult.get(0).getGB_CHANNEL());
			v_result.setResult("success");
			v_result.setResult_msg("success");
			return v_result;
		} else if (getVisitResult != null && getVisitResult.size() == 0) {
			v_result.setResult("fail");
			v_result.setResult_msg("not found");
			return v_result;
		} else {
			v_result.setResult("fail");
			v_result.setResult_msg("unknown error");
			return v_result;
		}
	}// getVisit

	@RequestMapping(value = "/facebook", method = RequestMethod.POST)
	@ResponseBody
	public GB_FacebookResult facebook(
			@RequestParam HashMap<String, Object> params, HttpServletRequest req) {
		GB_FacebookResult f_result = new GB_FacebookResult();
		HttpSession session = req.getSession();
		String[] mAttribute = { "token" };
		String[] subAttribute = {};
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			f_result.setResult("fail");
			f_result.setResult_msg(chkResult);
			return f_result;
		}
		System.out.println("token : " + (String) params.get("token"));
		try {
			ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
			registry.addConnectionFactory(new FacebookConnectionFactory(apiKey,
					secret));
			// access token received from Facebook
			String accessToken = (String) params.get("token");
			// after OAuth authorization
			Facebook facebook = new FacebookTemplate(accessToken);
			FacebookProfile profile = facebook.userOperations()
					.getUserProfile();
			String user_id = profile.getEmail();
			String user_code = profile.getId();
			String user_fnm = profile.getFirstName();
			String user_lnm = profile.getLastName();
			// 기타정보
			// String user_email = profile.getEmail();
			System.out.println("user_id : " + user_id + " , user_lnm :"
					+ user_lnm + " , user_fnm : " + user_fnm);

			// 가입확인
			SignupConfirm signupConfirm = new SignupConfirm();
			signupConfirm.setEMAIL_ID(user_id);
			List<SignupConfirm> signupConfirmResult = userDaoImpl
					.signupConfirm(signupConfirm);
			if (signupConfirmResult.size() == 0) {
				// 신규가입
				GB_Signup signup = new GB_Signup();
				signup.setEMAIL_ID(user_id);
				signup.setGUEST_FNM(user_fnm);
				signup.setGUEST_LNM(user_lnm);
				signup.setE_MAIL(user_id);
				signup.setHP_NO("");
				signup.setDEVICE_TYPE("");
				signup.setREG_ID("");
				signup.setJOIN_DATE(Dtime.instance().getGMTDate());
				signup.setCREATE_DTIME(Dtime.instance().getGMTDateTime());
				signup.setCREATE_ID(user_id);
				signup.setLAST_DTIME(Dtime.instance().getGMTDateTime());
				signup.setLAST_ID(user_id);

				signup.setEMAIL_FG("0");
				signup.setSMS_FG("0");
				signup.setRECEIPT_FG("0");
				signup.setPRIVACY_POLICY("0");

				signup.setGOOGLE_CODE("");
				signup.setFACEBOOK_CODE(user_code);
				signup.setSEX("");
				signup.setBIRTH_DAY("");
				signup.setNATIONALITY("");
				signup.setCITY("");
				signup.setPROFILE_IMG_PATH("http://graph.facebook.com/"
						+ user_code + "/picture?width=200&height=200");

				int signupResult = userDaoImpl.fbSignup(signup);

				if (signupResult == 1) {
					f_result.setUser_email(user_id);
					f_result.setResult("success");
					f_result.setResult_msg("signup");
				} else {
					f_result.setResult("fail");
					f_result.setResult_msg("unauthorization");
					return f_result;
				}
			} else {
				// 기존 가입자
				f_result.setUser_email(user_id);
				f_result.setResult("success");
				f_result.setResult_msg("join");
			}
			System.out
					.println("=================================================");
			System.out.println("세션 새로생성");
			session.setAttribute("user_id", user_id);
			session.setAttribute("user_firstname", user_fnm);
			session.setAttribute("user_lastname", user_lnm);
			session.setMaxInactiveInterval(SESSION_MAX_INTERVAL);
			System.out.println("session.getId() : " + session.getId());
			System.out.println("session.getAttribute('user_id') : "
					+ session.getAttribute("user_id"));
			System.out
					.println("=================================================");
		} catch (Exception e) {
			f_result.setResult("fail");
			f_result.setResult_msg("can not access to facebook");
			e.printStackTrace();
		}
		return f_result;
	}// facebook

	@RequestMapping(value = "/google", method = RequestMethod.POST)
	@ResponseBody
	public DefaultResult google(@RequestParam HashMap<String, Object> params,
			HttpServletRequest req) {
		DefaultResult d_result = new DefaultResult();
		HttpSession session = req.getSession();
		String[] mAttribute = { "email_id", "code", "firstname", "lastname",
				"sex", "profile_img_path" };
		String[] subAttribute = {};
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			d_result.setResult("fail");
			d_result.setResult_msg(chkResult);
			return d_result;
		}
		String user_id = (String) params.get("email_id");
		String user_code = (String) params.get("code");
		String user_fnm = (String) params.get("firstname");
		String user_lnm = (String) params.get("lastname");
		String user_sex = (String) params.get("sex");
		String user_profile_img_path = (String) params.get("profile_img_path");
		try {
			// 가입확인
			SignupConfirm signupConfirm = new SignupConfirm();
			signupConfirm.setEMAIL_ID(user_id);
			List<SignupConfirm> signupConfirmResult = userDaoImpl
					.signupConfirm(signupConfirm);
			if (signupConfirmResult.size() == 0) {
				// 신규가입
				GB_Signup signup = new GB_Signup();
				signup.setEMAIL_ID(user_id);
				signup.setGUEST_FNM(user_fnm);
				signup.setGUEST_LNM(user_lnm);
				signup.setE_MAIL(user_id);
				signup.setHP_NO("");
				signup.setDEVICE_TYPE("");
				signup.setREG_ID("");
				signup.setJOIN_DATE(Dtime.instance().getGMTDate());
				signup.setCREATE_DTIME(Dtime.instance().getGMTDateTime());
				signup.setCREATE_ID(user_id);
				signup.setLAST_DTIME(Dtime.instance().getGMTDateTime());
				signup.setLAST_ID(user_id);

				signup.setEMAIL_FG("0");
				signup.setSMS_FG("0");
				signup.setRECEIPT_FG("0");
				signup.setPRIVACY_POLICY("0");

				signup.setGOOGLE_CODE(user_code);
				signup.setFACEBOOK_CODE("");
				signup.setSEX(user_sex);
				signup.setBIRTH_DAY("");
				signup.setNATIONALITY("");
				signup.setCITY("");
				signup.setPROFILE_IMG_PATH(user_profile_img_path);

				int signupResult = userDaoImpl.fbSignup(signup);

				if (signupResult == 1) {
					d_result.setResult("success");
					d_result.setResult_msg("authorization");
				} else {
					d_result.setResult("fail");
					d_result.setResult_msg("unauthorization");
					return d_result;
				}
			} else {
				// 기존 가입자
				d_result.setResult("success");
				d_result.setResult_msg("join");
			}
			System.out
					.println("=================================================");
			System.out.println("세션 새로생성");
			session.setAttribute("user_id", user_id);
			session.setAttribute("user_firstname", user_fnm);
			session.setAttribute("user_lastname", user_lnm);
			session.setMaxInactiveInterval(SESSION_MAX_INTERVAL);
			System.out.println("session.getId() : " + session.getId());
			System.out.println("session.getAttribute('user_id') : "
					+ session.getAttribute("user_id"));
			System.out
					.println("=================================================");
		} catch (Exception e) {
			d_result.setResult("fail");
			d_result.setResult_msg("can not access to google");
			e.printStackTrace();
		}
		return d_result;
	}// google

	@RequestMapping(value = "/accommodation/create", method = RequestMethod.POST)
	@ResponseBody
	public DefaultResult createAccommodation(
			@RequestParam HashMap<String, Object> params, HttpServletRequest req) {
		DefaultResult d_result = new DefaultResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			d_result.setResult("fail");
			d_result.setResult_msg("session null");
			return d_result;
		}
		String[] mAttribute = { "gb_name", "gb_address", "gb_for", "gb_with",
				"gb_seeking", "gb_check_indate", "gb_check_outdate",
				"gb_latitude", "gb_longitude", "gb_lat_start", "gb_lon_start",
				"gb_lat_end", "gb_lon_end", "gb_channel" };
		String[] subAttribute = {};
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			d_result.setResult("fail");
			d_result.setResult_msg(chkResult);
			return d_result;
		}
		String dTime = Dtime.instance().getGMTDateTime();

		GB_CreateAccommodation ca = new GB_CreateAccommodation();
		ca.setGB_NM((String) params.get("gb_name"));
		ca.setGB_EMAIL_ID((String) session.getAttribute("user_id"));
		ca.setGB_ADDRESS((String) params.get("gb_address"));
		ca.setGB_FOR((String) params.get("gb_for"));
		ca.setGB_WITH((String) params.get("gb_with"));
		ca.setGB_SEEKING((String) params.get("gb_seeking"));
		ca.setGB_CHECK_INDATE((String) params.get("gb_check_indate"));
		ca.setGB_CHECK_OUTDATE((String) params.get("gb_check_outdate"));
		ca.setGB_LATITUDE((String) params.get("gb_latitude"));
		ca.setGB_LONGITUDE((String) params.get("gb_longitude"));
		ca.setGB_LAT_START((String) params.get("gb_lat_start"));
		ca.setGB_LON_START((String) params.get("gb_lon_start"));
		ca.setGB_LAT_END((String) params.get("gb_lat_end"));
		ca.setGB_LON_END((String) params.get("gb_lon_end"));
		ca.setGB_CREATE_DTIME(dTime);
		ca.setGB_CREATE_ID((String) session.getAttribute("user_id"));
		ca.setGB_LAST_DTIME(dTime);
		ca.setGB_LAST_ID((String) session.getAttribute("user_id"));
		ca.setGB_CHANNEL((String) params.get("gb_channel"));

		List<GB_CreateAccommodation> checkAccommodationResult = userDaoImpl
				.checkAccommodation(ca);

		int result = 0;
		if (checkAccommodationResult != null
				&& checkAccommodationResult.size() > 0) { // already exist,
															// create log and
															// accommodation
															// update
			// update last time
			userDaoImpl.updateAccommodationLastDTime(ca);

			// create log
			userDaoImpl.createAccommodationLog(ca);

			// update accommodation
			result = userDaoImpl.updateAccommodation(ca);

			// create shout log
			userDaoImpl.createShoutLog(ca);

			// delete shout
			userDaoImpl.deleteShout(ca);
			if (result == 0) {
				d_result.setResult("fail");
				d_result.setResult_msg("can not update accommodation");
				return d_result;
			}
		} else {
			// create accommodation
			result = userDaoImpl.createAccommodation(ca);
		}
		if (result == 1) {
			d_result.setResult("success");
			d_result.setResult_msg("success");
			return d_result;
		} else {
			d_result.setResult("fail");
			d_result.setResult_msg("can not create accommodation");
			return d_result;
		}
	}// accommodation-create

	@RequestMapping(value = "/trippal/room", method = RequestMethod.GET)
	@ResponseBody
	public GB_TrippalGetRoomResult trippalGetRoom(HttpServletRequest req) {
		GB_TrippalGetRoomResult tgr_result = new GB_TrippalGetRoomResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			tgr_result.setResult("fail");
			tgr_result.setResult_msg("session null");
			return tgr_result;
		}

		GB_TrippalGetRoom tgr = new GB_TrippalGetRoom();
		String email_id = (String) session.getAttribute("user_id");
		tgr.setEMAIL_ID(email_id);

		List<GB_TrippalGetRoom> trippalGetRoomResult = userDaoImpl
				.trippalGetRoom(tgr);

		if (trippalGetRoomResult != null && trippalGetRoomResult.size() > 0) {

			String curRoom_no = "";
			ArrayList<HashMap<String, Object>> room = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> param = null;
			ArrayList<HashMap<String, Object>> people = null;
			for (int i = 0; i < trippalGetRoomResult.size(); i++) {
				if (i != 0
						&& !curRoom_no.equals(trippalGetRoomResult.get(i)
								.getROOM_NO())) {
					param.put("people", people);
					room.add(param);
				}
				if (!curRoom_no
						.equals(trippalGetRoomResult.get(i).getROOM_NO())) {
					param = new HashMap<String, Object>();
					people = new ArrayList<HashMap<String, Object>>();
					curRoom_no = trippalGetRoomResult.get(i).getROOM_NO();
				}
				if (email_id.equals(trippalGetRoomResult.get(i).getEMAIL_ID())) {
					param.put("room_no", trippalGetRoomResult.get(i)
							.getROOM_NO());
					param.put("room_join_dtime", trippalGetRoomResult.get(i)
							.getJOIN_DTIME());
				}

				HashMap<String, Object> subParam = new HashMap<String, Object>();
				subParam.put("email_id", trippalGetRoomResult.get(i)
						.getEMAIL_ID());
				subParam.put("firstname", trippalGetRoomResult.get(i)
						.getGUEST_FNM());
				subParam.put("lastname", trippalGetRoomResult.get(i)
						.getGUEST_LNM());
				subParam.put("profile_img_path", trippalGetRoomResult.get(i)
						.getPROFILE_IMG_PATH());
				people.add(subParam);
			}

			tgr_result.setRoom(room);
			tgr_result.setResult("success");
			tgr_result.setResult_msg("success");
			return tgr_result;
		} else if (trippalGetRoomResult != null
				&& trippalGetRoomResult.size() == 0) {
			tgr_result.setResult("fail");
			tgr_result.setResult_msg("not found");
			return tgr_result;
		} else {
			tgr_result.setResult("fail");
			tgr_result.setResult_msg("unknown error");
			return tgr_result;
		}
	}// trippal/room

	@RequestMapping(value = "/trippal/room/create", method = RequestMethod.POST)
	@ResponseBody
	public DefaultResult trippalCreateRoom(
			@RequestParam HashMap<String, Object> params, HttpServletRequest req) {
		DefaultResult d_result = new DefaultResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			d_result.setResult("fail");
			d_result.setResult_msg("session null");
			return d_result;
		}
		String[] mAttribute = { "member_email_id1" };
		String[] subAttribute = {};
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			d_result.setResult("fail");
			d_result.setResult_msg(chkResult);
			return d_result;
		}

		int result = 0;
		GB_TrippalCreateRoom tcr = new GB_TrippalCreateRoom();
		tcr.setJOIN_DTIME(Dtime.instance().getGMTDateTime());
		tcr.setEMAIL_ID((String) session.getAttribute("user_id"));

		for (int i = 0; i < params.size() + 1; i++) {
			if (i == 0) {
				tcr.setMEMBER_EMAIL_ID((String) session.getAttribute("user_id"));
				// 룸 생성
				result = userDaoImpl.trippalCreateRoom(tcr);

				if (result == 0) {
					d_result.setResult("fail");
					d_result.setResult_msg("can not create room");
					return d_result;
				}
			} else {
				tcr.setMEMBER_EMAIL_ID((String) params.get("member_email_id"
						+ i));
				// 룸 참여
				result = userDaoImpl.trippalJoinRoom(tcr);

				if (result == 0) {
					d_result.setResult("fail");
					d_result.setResult_msg("can not join room");
					return d_result;
				}
			}
		}

		if (result == 1) {
			d_result.setResult("success");
			d_result.setResult_msg("success");
			return d_result;
		} else {
			d_result.setResult("fail");
			d_result.setResult_msg("unknown error");
			return d_result;
		}
	}// trippal/room/create

	@RequestMapping(value = "/trippal/room/delete", method = RequestMethod.POST)
	@ResponseBody
	public DefaultResult trippalDeleteRoom(
			@RequestParam HashMap<String, Object> params, HttpServletRequest req) {
		DefaultResult d_result = new DefaultResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			d_result.setResult("fail");
			d_result.setResult_msg("session null");
			return d_result;
		}
		String[] mAttribute = { "room_no" };
		String[] subAttribute = {};
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			d_result.setResult("fail");
			d_result.setResult_msg(chkResult);
			return d_result;
		}

		GB_TrippalDeleteRoom tdr = new GB_TrippalDeleteRoom();
		tdr.setROOM_NO((String) params.get("room_no"));
		tdr.setMEMBER_EMAIL_ID((String) session.getAttribute("user_id"));

		int result = userDaoImpl.trippalDeleteRoom(tdr);

		if (result == 1) {
			d_result.setResult("success");
			d_result.setResult_msg("success");
			return d_result;
		} else {
			d_result.setResult("fail");
			d_result.setResult_msg("check your room_no");
			return d_result;
		}
	}// trippal/room/delete

	@RequestMapping(value = "/trippal/people", method = RequestMethod.POST)
	@ResponseBody
	public GB_TrippalGetPeopleListResult trippalGetPeopleList(
			@RequestParam HashMap<String, Object> params, HttpServletRequest req) {
		GB_TrippalGetPeopleListResult tgp_result = new GB_TrippalGetPeopleListResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			tgp_result.setResult("fail");
			tgp_result.setResult_msg("session null");
			return tgp_result;
		}
		String[] mAttribute = { "selected_date" };
		String[] subAttribute = {};
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			tgp_result.setResult("fail");
			tgp_result.setResult_msg(chkResult);
			return tgp_result;
		}

		GB_TrippalGetPeopleList tgp = new GB_TrippalGetPeopleList();
		tgp.setEMAIL_ID((String) session.getAttribute("user_id"));
		tgp.setSELECTED_DATE((String) params.get("selected_date"));

		List<GB_TrippalGetPeopleList> trippalGetPeopleListResult = userDaoImpl
				.trippalGetPeopleList(tgp);

		if (trippalGetPeopleListResult != null
				&& trippalGetPeopleListResult.size() > 0) {

			ArrayList<HashMap<String, Object>> trippal = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < trippalGetPeopleListResult.size(); i++) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("email_id", trippalGetPeopleListResult.get(i)
						.getEMAIL_ID());
				param.put("firstname", trippalGetPeopleListResult.get(i)
						.getGUEST_FNM());
				param.put("lastname", trippalGetPeopleListResult.get(i)
						.getGUEST_LNM());
				param.put("profile_img_path", trippalGetPeopleListResult.get(i)
						.getPROFILE_IMG_PATH());
				param.put("gb_for", trippalGetPeopleListResult.get(i)
						.getGB_FOR());
				param.put("gb_with", trippalGetPeopleListResult.get(i)
						.getGB_WITH());
				param.put("gb_seeking", trippalGetPeopleListResult.get(i)
						.getGB_SEEKING());
				param.put("gb_latitude", trippalGetPeopleListResult.get(i)
						.getGB_LATITUDE());
				param.put("gb_longitude", trippalGetPeopleListResult.get(i)
						.getGB_LONGITUDE());
				trippal.add(param);
			}

			tgp_result.setTrippal(trippal);
			tgp_result.setResult("success");
			tgp_result.setResult_msg("success");
			return tgp_result;
		} else if (trippalGetPeopleListResult != null
				&& trippalGetPeopleListResult.size() == 0) {
			tgp_result.setResult("fail");
			tgp_result.setResult_msg("not founds");
			return tgp_result;
		} else {
			tgp_result.setResult("fail");
			tgp_result.setResult_msg("unknown error");
			return tgp_result;
		}
	}// trippal/people

	@RequestMapping(value = "/trippal/location", method = RequestMethod.GET)
	@ResponseBody
	public GB_TrippalGetLocationListResult trippalGetLocationList(
			HttpServletRequest req) {
		GB_TrippalGetLocationListResult tgl_result = new GB_TrippalGetLocationListResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			tgl_result.setResult("fail");
			tgl_result.setResult_msg("session null");
			return tgl_result;
		}

		GB_TrippalGetLocationList tgl = new GB_TrippalGetLocationList();
		tgl.setGB_EMAIL_ID((String) session.getAttribute("user_id"));

		List<GB_TrippalGetLocationList> trippalGetLocationListResult = userDaoImpl
				.trippalGetLocationList(tgl);

		if (trippalGetLocationListResult != null
				&& trippalGetLocationListResult.size() > 0) {

			ArrayList<HashMap<String, Object>> location = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < trippalGetLocationListResult.size(); i++) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("email_id", trippalGetLocationListResult.get(i)
						.getGB_EMAIL_ID());
				param.put("latitude", trippalGetLocationListResult.get(i)
						.getGB_LATITUDE());
				param.put("longitude", trippalGetLocationListResult.get(i)
						.getGB_LONGITUDE());
				location.add(param);
			}

			tgl_result.setLocation(location);
			tgl_result.setResult("success");
			tgl_result.setResult_msg("success");
			return tgl_result;
		} else if (trippalGetLocationListResult != null
				&& trippalGetLocationListResult.size() == 0) {
			tgl_result.setResult("fail");
			tgl_result.setResult_msg("not founds");
			return tgl_result;
		} else {
			tgl_result.setResult("fail");
			tgl_result.setResult_msg("unknown error");
			return tgl_result;
		}
	}// trippal/location

	@RequestMapping(value = "/trippal/location/update", method = RequestMethod.POST)
	@ResponseBody
	public DefaultResult trippalLocationUpdate(
			@RequestParam HashMap<String, Object> params, HttpServletRequest req) {
		DefaultResult d_result = new DefaultResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			d_result.setResult("fail");
			d_result.setResult_msg("session null");
			return d_result;
		}
		String[] mAttribute = { "lat_start", "lon_start", "lat_end", "lon_end" };
		String[] subAttribute = {};
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			d_result.setResult("fail");
			d_result.setResult_msg(chkResult);
			return d_result;
		}

		GB_TrippalLocationUpdate tlu = new GB_TrippalLocationUpdate();
		tlu.setGB_EMAIL_ID((String) session.getAttribute("user_id"));
		tlu.setGB_LAT_START((String) params.get("lat_start"));
		tlu.setGB_LON_START((String) params.get("lon_start"));
		tlu.setGB_LAT_END((String) params.get("lat_end"));
		tlu.setGB_LON_END((String) params.get("lon_end"));

		int result = userDaoImpl.trippalLocationUpdate(tlu);

		if (result == 1) {
			d_result.setResult("success");
			d_result.setResult_msg("success");
			return d_result;
		} else {
			d_result.setResult("fail");
			d_result.setResult_msg("unknown error");
			return d_result;
		}
	}// trippal/location/update

	@RequestMapping(value = "/trippal/shout", method = RequestMethod.POST)
	@ResponseBody
	public GB_TrippalGetShoutListResult trippalGetShoutList(
			@RequestParam HashMap<String, Object> params, HttpServletRequest req) {
		GB_TrippalGetShoutListResult tgs_result = new GB_TrippalGetShoutListResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			tgs_result.setResult("fail");
			tgs_result.setResult_msg("session null");
			return tgs_result;
		}
		String[] mAttribute = {};
		String[] subAttribute = { "message_dtime" };
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			tgs_result.setResult("fail");
			tgs_result.setResult_msg(chkResult);
			return tgs_result;
		}

		GB_TrippalGetShoutList tgs = new GB_TrippalGetShoutList();
		tgs.setEMAIL_ID((String) session.getAttribute("user_id"));
		if (params.containsKey("message_dtime")) {
			tgs.setMESSAGE_DTIME((String) params.get("message_dtime"));
		} else {
			tgs.setMESSAGE_DTIME("0");
		}

		List<GB_TrippalGetShoutList> trippalGetShoutListResult = userDaoImpl
				.trippalGetShoutList(tgs);

		if (trippalGetShoutListResult != null
				&& trippalGetShoutListResult.size() > 0) {

			ArrayList<HashMap<String, Object>> shout = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < trippalGetShoutListResult.size(); i++) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("email_id", trippalGetShoutListResult.get(i)
						.getSENDER_ID());
				param.put("firstname", trippalGetShoutListResult.get(i)
						.getGUEST_FNM());
				param.put("lastname", trippalGetShoutListResult.get(i)
						.getGUEST_LNM());
				param.put("last_message", trippalGetShoutListResult.get(i)
						.getMESSAGE());
				param.put("last_message_dtime", trippalGetShoutListResult
						.get(i).getMESSAGE_DTIME());
				param.put("profile_img_path", trippalGetShoutListResult.get(i)
						.getPROFILE_IMG_PATH());
				shout.add(param);
			}

			tgs_result.setShout(shout);
			tgs_result.setResult("success");
			tgs_result.setResult_msg("success");
			return tgs_result;
		} else if (trippalGetShoutListResult != null
				&& trippalGetShoutListResult.size() == 0) {
			tgs_result.setResult("fail");
			tgs_result.setResult_msg("not founds");
			return tgs_result;
		} else {
			tgs_result.setResult("fail");
			tgs_result.setResult_msg("unknown error");
			return tgs_result;
		}
	}// trippal/shout

	@RequestMapping(value = "/trippal/shout/insert", method = RequestMethod.POST)
	@ResponseBody
	public DefaultResult trippalShout(
			@RequestParam HashMap<String, Object> params, HttpServletRequest req) {
		DefaultResult d_result = new DefaultResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			d_result.setResult("fail");
			d_result.setResult_msg("session null");
			return d_result;
		}
		String[] mAttribute = { "message" };
		String[] subAttribute = {};
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			d_result.setResult("fail");
			d_result.setResult_msg(chkResult);
			return d_result;
		}

		GB_TrippalShout ts = new GB_TrippalShout();
		ts.setEMAIL_ID((String) session.getAttribute("user_id"));
		ts.setMESSAGE((String) params.get("message"));
		ts.setMESSAGE_DTIME(Dtime.instance().getGMTDateTime());

		int result = userDaoImpl.trippalShout(ts);

		if (result == 1) {

			d_result.setResult("success");
			d_result.setResult_msg("success");
			return d_result;
		} else {
			d_result.setResult("fail");
			d_result.setResult_msg("unknown error");
			return d_result;
		}
	}// trippal/shout/insert

	@RequestMapping(value = "/gps/trace", method = RequestMethod.POST)
	@ResponseBody
	public DefaultResult writeGPSTrace(
			@RequestParam HashMap<String, Object> params) {
		DefaultResult d_result = new DefaultResult();
		String[] mAttribute = { "email_id", "latitude", "longitude" };
		String[] subAttribute = {};
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			d_result.setResult("fail");
			d_result.setResult_msg(chkResult);
			return d_result;
		}
		String user_id = (String) params.get("email_id");
		String dTime = Dtime.instance().getGMTDateTime();

		GB_GPSTrace gt = new GB_GPSTrace();
		gt.setGB_EMAIL_ID(user_id);
		gt.setGB_LATITUDE((String) params.get("latitude"));
		gt.setGB_LONGITUDE((String) params.get("longitude"));
		gt.setCREATE_DTIME(dTime);
		gt.setCREATE_ID(user_id);
		gt.setLAST_DTIME(dTime);
		gt.setLAST_ID(user_id);

		int result = userDaoImpl.writeGPSTrace(gt);

		if (result == 1) {
			d_result.setResult("success");
			d_result.setResult_msg("success");
			return d_result;
		} else {
			d_result.setResult("fail");
			d_result.setResult_msg("can not trace on gps");
			return d_result;
		}
	}// gps/trace

	@RequestMapping(value = "/gps/trace/show", method = RequestMethod.POST)
	@ResponseBody
	public GB_GPSTraceResult getGPSTrace(
			@RequestParam HashMap<String, Object> params) {
		GB_GPSTraceResult tr_result = new GB_GPSTraceResult();
		String[] mAttribute = { "selected_time" };
		String[] subAttribute = {};
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			tr_result.setResult("fail");
			tr_result.setResult_msg(chkResult);
			return tr_result;
		}

		List<GB_GPSTrace> getGPSTraceResult = userDaoImpl.getGPSTrace();

		if (getGPSTraceResult != null && getGPSTraceResult.size() > 0) {
			ArrayList<HashMap<String, Object>> trace = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < getGPSTraceResult.size(); ++i) {
				try {
					HashMap<String, Object> param = new HashMap<String, Object>();
					String[] lat_list = getGPSTraceResult.get(i)
							.getGB_LATITUDE().split(",");
					String[] lon_list = getGPSTraceResult.get(i)
							.getGB_LONGITUDE().split(",");
					String lat = "";
					String lon = "";
					for (int j = 0; j < 6; j++) {
						lat += lat_list[Integer.valueOf((String) params
								.get("selected_time") + j)];
						lon += lon_list[Integer.valueOf((String) params
								.get("selected_time") + j)];
						if (j != 5) {
							lat += ",";
							lon += ",";
						}
					}
					if (lat.equals("0,0,0,0,0,0") || lon.equals("0,0,0,0,0,0")) {
						continue;
					}
					param.put("latitude", lat);
					param.put("longitude", lon);
					trace.add(param);
				} catch (NumberFormatException nfe) {
					tr_result.setResult("fail");
					tr_result.setResult_msg("check to selected_time");
					return tr_result;
				}
			}
			tr_result.setTrace(trace);
			tr_result.setResult("success");
			tr_result.setResult_msg("success");
			return tr_result;
		} else if (getGPSTraceResult != null && getGPSTraceResult.size() == 0) {
			tr_result.setResult("fail");
			tr_result.setResult_msg("not founds");
			return tr_result;
		} else {
			tr_result.setResult("fail");
			tr_result.setResult_msg("unknown error");
			return tr_result;
		}
	}// gps/trace/show

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody
	public SearchResult ghSearch(@RequestParam HashMap<String, Object> params,
			HttpServletRequest req) {
		SearchResult s_result = new SearchResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			s_result.setResult("fail");
			s_result.setResult_msg("session null");
			return s_result;
		}
		String[] mAttribute = {};
		String[] subAttribute = { "search_word", "search_locate" };
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			s_result.setResult("fail");
			s_result.setResult_msg(chkResult);
			return s_result;
		}
		String word = "";
		String locate = "";
		if (params.containsKey("search_word")) {
			word = (String) params.get("search_word");
		}
		if (params.containsKey("search_locate")) {
			locate = (String) params.get("search_locate");
		}
		System.out.println("word : " + word + " , locate : " + locate);
		GhSearch ghSearch = new GhSearch();
		ghSearch.setGH_WORD(word);
		ghSearch.setGH_LOCATE(locate);

		List<GhSearch> searchList = userDaoImpl.ghSearch(ghSearch);
		if (searchList != null && searchList.size() > 0) {
			ArrayList<HashMap<String, Object>> search_list = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < searchList.size(); ++i) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("gh_no", searchList.get(i).getGH_NO());
				param.put("search_word", searchList.get(i).getGH_WORD());
				param.put("search_locate", searchList.get(i).getGH_LOCATE());
				search_list.add(param);
			}
			s_result.setSearch_list(search_list);
			s_result.setResult("success");
			s_result.setResult_msg("success");
			return s_result;
		} else {
			s_result.setResult("fail");
			s_result.setResult_msg("not founds");
			return s_result;
		}
	}// search

	@RequestMapping(value = "/room", method = RequestMethod.GET)
	@ResponseBody
	public RoomListResult getRoomList(HttpServletRequest req) {
		RoomListResult r_result = new RoomListResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			r_result.setResult("fail");
			r_result.setResult_msg("session null");
			return r_result;
		}

		List<RoomList> roomList = userDaoImpl.getRoomList((String) session
				.getAttribute("user_id"));

		if (roomList != null && roomList.size() > 0) {
			ArrayList<HashMap<String, Object>> room_list = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < roomList.size(); ++i) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("room_no", roomList.get(i).getROOM_NO());
				param.put("gh_name", roomList.get(i).getGH_WORD());
				room_list.add(param);
			}
			r_result.setRoom_list(room_list);
			r_result.setResult("success");
			r_result.setResult_msg("success");
			return r_result;
		} else {
			r_result.setResult("fail");
			r_result.setResult_msg("not founds");
			return r_result;
		}
	}// room

	@RequestMapping(value = "/room/make", method = RequestMethod.POST)
	@ResponseBody
	public MakeRoomResult makeRoom(
			@RequestParam HashMap<String, Object> params, HttpServletRequest req) {
		MakeRoomResult m_result = new MakeRoomResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			m_result.setResult("fail");
			m_result.setResult_msg("session null");
			return m_result;
		}
		String[] mAttribute = {};
		String[] subAttribute = { "room_no", "gh_no", "check_indate",
				"check_outdate" };
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			m_result.setResult("fail");
			m_result.setResult_msg(chkResult);
			return m_result;
		}
		String gh_no = "";
		String check_indate = "";
		String check_outdate = "";
		if (params.containsKey("gh_no")) {
			gh_no = (String) params.get("gh_no");
		}
		if (params.containsKey("check_indate")) {
			check_indate = (String) params.get("check_indate");
		}
		if (params.containsKey("check_outdate")) {
			check_outdate = (String) params.get("check_outdate");
		}

		MakeRoom makeRoom = new MakeRoom();
		makeRoom.setGH_NO(gh_no);
		makeRoom.setSENDER_ID((String) session.getAttribute("user_id"));
		makeRoom.setCHECK_INDATE(check_indate);
		makeRoom.setCHECK_OUTDATE(check_outdate);

		List<MakeRoom> makeRoomList = null;
		// 룸 바로 접속
		if (params.containsKey("room_no")) {
			// room number setting
			makeRoom.setROOM_NO((String) params.get("room_no"));
			// 유저리스트 email, fnm, lnm, l_msg, l_msg_time, profile_img 추출
			makeRoomList = userDaoImpl.getRoomUserList(makeRoom);

		} else { // 룸 생성 후 접속
			// 룸 생성 파라미터(gh_no, check_indate, check_outdate)
			int makeRoomResult = userDaoImpl.makeRoom(makeRoom);
			if (makeRoomResult == 0) {
				m_result.setResult("fail");
				m_result.setResult_msg("can not make room");
				return m_result;
			}

			// 룸 넘버 추출
			List<MakeRoom> roomNoList = userDaoImpl.getRoomNo(makeRoom);
			if (roomNoList == null || roomNoList.size() == 0) {
				m_result.setResult("fail");
				m_result.setResult_msg("you are successfuly make room, but not find room number");
				return m_result;
			}

			// room number setting
			makeRoom.setROOM_NO(roomNoList.get(0).getROOM_NO());
			// 유저리스트 email, fnm, lnm, l_msg, l_msg_time, profile_img 추출
			makeRoomList = userDaoImpl.getRoomUserList(makeRoom);
		}

		if (makeRoomList != null && makeRoomList.size() > 0) {
			ArrayList<HashMap<String, Object>> user_list = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < makeRoomList.size(); ++i) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("user_email", makeRoomList.get(i).getSENDER_ID());
				param.put("user_firstname", makeRoomList.get(i).getGUEST_FNM());
				param.put("user_lastname", makeRoomList.get(i).getGUEST_LNM());
				param.put("last_message", makeRoomList.get(i).getLAST_MESSAGE());
				param.put("last_message_dtime", makeRoomList.get(i)
						.getLAST_MESSAGE_DTIME());
				param.put("user_profile_img", makeRoomList.get(i)
						.getPROFILE_IMG_PATH());
				user_list.add(param);
			}
			m_result.setUser_list(user_list);
			m_result.setResult("success");
			m_result.setResult_msg("success");
			return m_result;
		} else {
			m_result.setResult("fail");
			m_result.setResult_msg("not founds");
			return m_result;
		}
	}// makeRoom

	@RequestMapping(value = "/room/update", method = RequestMethod.POST)
	@ResponseBody
	public UpdateRoomResult updateRoom(
			@RequestParam HashMap<String, Object> params, HttpServletRequest req) {
		UpdateRoomResult u_result = new UpdateRoomResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			u_result.setResult("fail");
			u_result.setResult_msg("session null");
			return u_result;
		}
		String[] mAttribute = { "room_no" };
		String[] subAttribute = {};
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			u_result.setResult("fail");
			u_result.setResult_msg(chkResult);
			return u_result;
		}

		UpdateRoom updateRoom = new UpdateRoom();
		updateRoom.setROOM_NO((String) params.get("room_no"));

		List<UpdateRoom> messageList = userDaoImpl.updateRoom(updateRoom);

		if (messageList != null && messageList.size() > 0) {
			ArrayList<HashMap<String, Object>> message_list = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < messageList.size(); ++i) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("sender_email", messageList.get(i).getSENDER_ID());
				param.put("message", messageList.get(i).getMESSAGE());
				param.put("message_dtime", messageList.get(i)
						.getMESSAGE_DTIME());
				message_list.add(param);
			}
			u_result.setMessage_list(message_list);
			u_result.setResult("success");
			u_result.setResult_msg("success");
			return u_result;
		} else {
			u_result.setResult("fail");
			u_result.setResult_msg("not founds");
			return u_result;
		}
	}// updateRoom

	@RequestMapping(value = "/room/write", method = RequestMethod.POST)
	@ResponseBody
	public DefaultResult writeMessage(
			@RequestParam HashMap<String, Object> params, HttpServletRequest req) {
		DefaultResult d_result = new DefaultResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			d_result.setResult("fail");
			d_result.setResult_msg("session null");
			return d_result;
		}
		String[] mAttribute = { "sender_email", "message", "message_dtime" };
		String[] subAttribute = {};
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			d_result.setResult("fail");
			d_result.setResult_msg(chkResult);
			return d_result;
		}

		WriteMessage writeMessage = new WriteMessage();
		writeMessage.setSENDER_ID((String) params.get("sender_email"));
		writeMessage.setMESSAGE((String) params.get("message"));
		writeMessage.setMESSAGE_DTIME((String) params.get("message_dtime"));

		WriteLastMessage writeLastMessage = new WriteLastMessage();
		writeLastMessage.setSENDER_ID((String) params.get("sender_email"));
		writeLastMessage.setLAST_MESSAGE((String) params.get("message"));
		writeLastMessage.setLAST_MESSAGE_DTIME((String) params
				.get("message_dtime"));
		// message write
		int result = userDaoImpl.writeMessage(writeMessage);

		// last message chk
		List<WriteLastMessage> writeChk = userDaoImpl
				.writeLastMessageChk(writeLastMessage);

		if (writeChk != null && writeChk.size() > 0) {
			// last message update
			result = userDaoImpl.updateLastMessage(writeLastMessage);
		} else {
			// last message write
			result = userDaoImpl.writeLastMessage(writeLastMessage);
		}

		if (result == 1) {
			d_result.setResult("success");
			d_result.setResult_msg("success");
			return d_result;
		} else {
			d_result.setResult("fail");
			d_result.setResult_msg("db connection fail");
			return d_result;
		}
	}// writeMessage

	@RequestMapping(value = "/user/signup", headers = "content-type=multipart/*", method = RequestMethod.POST)
	public @ResponseBody
	DefaultResult signup(
			@RequestParam HashMap<String, Object> params,
			@RequestParam(value = "user_profile_img", required = false) MultipartFile user_profile_img,
			HttpServletRequest req) {
		DefaultResult d_result = new DefaultResult();
		HttpSession session = req.getSession();
		String[] mAttribute = { "user_id", "user_pwd", "user_firstname",
				"user_lastname", "user_phone", "user_device_type",
				"user_reg_id" };
		String[] subAttribute = { "user_facebook_id", "user_sex", "user_birth",
				"user_location" };
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			d_result.setResult("fail");
			d_result.setResult_msg(chkResult);
			return d_result;
		}

		Signup signup = new Signup();
		signup.setEMAIL_ID((String) params.get("user_id"));
		signup.setEMAIL_PWD((String) params.get("user_pwd"));
		signup.setGUEST_FNM((String) params.get("user_firstname"));
		signup.setGUEST_LNM((String) params.get("user_lastname"));
		signup.setE_MAIL((String) params.get("user_id"));
		signup.setHP_NO((String) params.get("user_phone"));
		signup.setDEVICE_TYPE((String) params.get("user_device_type"));
		signup.setREG_ID((String) params.get("user_reg_id"));
		signup.setJOIN_DATE(Dtime.instance().getGMTDate());
		signup.setCREATE_DTIME(Dtime.instance().getGMTDateTime());
		signup.setCREATE_ID((String) params.get("user_id"));
		signup.setLAST_DTIME(Dtime.instance().getGMTDateTime());
		signup.setLAST_ID((String) params.get("user_id"));
		signup.setEMAIL_FG("0");
		signup.setSMS_FG("0");
		signup.setRECEIPT_FG("0");
		signup.setFACEBOOK_CODE("");
		signup.setSEX("");
		signup.setBIRTH_DAY("");
		signup.setNATIONALITY("");
		signup.setCITY("");
		signup.setPROFILE_IMG_PATH(SERVER_URL + PROFILE_IMG_PATH
				+ (String) params.get("user_id") + "/profile.png");
		if (params.containsKey("user_facebook_id")) {
			signup.setFACEBOOK_CODE((String) params.get("user_facebook_id"));
		}
		if (params.containsKey("user_sex")) {
			signup.setSEX((String) params.get("user_sex"));
		}
		if (params.containsKey("user_birth")) {
			signup.setBIRTH_DAY((String) params.get("user_birth"));
		}
		if (params.containsKey("user_location")) {
			String[] args = ((String) params.get("user_location")).split(", ");
			signup.setCITY(args[0]);
			signup.setNATIONALITY(args[1]);
		}

		// 이미지 처리
		if (user_profile_img != null) {
			File dir = new File(session.getServletContext().getRealPath("/")
					+ PROFILE_IMG_PATH + (String) params.get("user_id"));
			if (!dir.isDirectory()) {
				dir.mkdirs();
			}
			File f = new File(dir, "profile.png");
			try {
				user_profile_img.transferTo(f);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		int result = userDaoImpl.signup(signup);
		if (result == 1) {
			d_result.setResult("success");
			d_result.setResult_msg("success");
			System.out
					.println("=================================================");
			System.out.println("세션 새로생성");
			session.setAttribute("user_id", signup.getEMAIL_ID());
			// session.setAttribute("user_pwd", signup.getEMAIL_PWD());
			session.setAttribute("user_firstname", signup.getGUEST_FNM());
			session.setAttribute("user_lastname", signup.getGUEST_LNM());
			// session.setAttribute("user_phone", signup.getHP_NO());
			// session.setAttribute("user_device_type",
			// signup.getDEVICE_TYPE());
			// session.setAttribute("user_reg_id", signup.getREG_ID());
			session.setMaxInactiveInterval(SESSION_MAX_INTERVAL);
			System.out.println("session.getId() : " + session.getId());
			System.out.println("session.getAttribute('user_id') : "
					+ session.getAttribute("user_id"));
			System.out
					.println("=================================================");
			return d_result;
		} else {
			d_result.setResult("fail");
			d_result.setResult_msg("db connection fail");
			return d_result;
		}
	}// signup

	// @RequestMapping(value = "/user/signup/confirm", method =
	// RequestMethod.POST)
	// public @ResponseBody
	// DefaultResult signupConfirm(@RequestParam HashMap<String, Object> params)
	// {
	// DefaultResult d_result = new DefaultResult();
	// String[] mAttribute = { "user_id" };
	// String[] subAttribute = {};
	// String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
	// if (chkResult != null) {
	// d_result.setResult("fail");
	// d_result.setResult_msg(chkResult);
	// return d_result;
	// }
	//
	// SignupConfirm signupConfirm = new SignupConfirm();
	// signupConfirm.setEMAIL_ID((String) params.get("user_id"));
	// int result = userDaoImpl.signupConfirm(signupConfirm);
	// if (result == 1) {
	// d_result.setResult("success");
	// d_result.setResult_msg("unoccupied");
	// return d_result;
	// } else {
	// d_result.setResult("fail");
	// d_result.setResult_msg("occupied");
	// return d_result;
	// }
	// }// signupConfirm

	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public @ResponseBody
	LoginResult login(@RequestParam HashMap<String, Object> params,
			HttpServletRequest req) {
		LoginResult l_result = new LoginResult();
		HttpSession session = req.getSession();
		String[] mAttribute = { "user_id", "user_pwd" };
		String[] subAttribute = {};
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			l_result.setResult("fail");
			l_result.setResult_msg(chkResult);
			return l_result;
		}

		Login login = new Login();
		login.setEMAIL_ID((String) params.get("user_id"));
		login.setEMAIL_PWD((String) params.get("user_pwd"));
		List<Login> loginList = userDaoImpl.login(login);

		if (loginList.size() > 0) {
			ArrayList<HashMap<String, Object>> reserve_list = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < loginList.size(); i++) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("reserve_id", loginList.get(i).getRES_SEQ());
				param.put("reserve_date", loginList.get(i).getRESERVE_DATE());
				reserve_list.add(param);
			}
			l_result.setReserve_list(reserve_list);
			System.out
					.println("=================================================");
			if (session.isNew()) {
				System.out.println("세션 새로생성");
			} else {
				System.out.println("세션 로그인");
			}
			session.setAttribute("user_id", loginList.get(0).getEMAIL_ID());
			// session.setAttribute("user_pwd", guest.get(0).getEMAIL_PWD());
			session.setAttribute("user_firstname", loginList.get(0)
					.getGUEST_FNM());
			session.setAttribute("user_lastname", loginList.get(0)
					.getGUEST_LNM());
			// session.setAttribute("user_phone", guest.get(0).getHP_NO());
			// session.setAttribute("user_device_type", guest.get(0)
			// .getDEVICE_TYPE());
			// session.setAttribute("user_reg_id", guest.get(0).getREG_ID());
			session.setMaxInactiveInterval(SESSION_MAX_INTERVAL);
			System.out.println("session.getId() : " + session.getId());
			System.out.println("session.getAttribute('user_id') : "
					+ session.getAttribute("user_id"));
			System.out
					.println("=================================================");
			l_result.setResult("success");
			l_result.setResult_msg("authorized");
			return l_result;
		} else {
			l_result.setResult("fail");
			l_result.setResult_msg("unauthorized");
			return l_result;
		}
	}// login

	@RequestMapping(value = "/rent/checkin", method = RequestMethod.POST)
	public @ResponseBody
	CheckinResult checkin(@RequestParam HashMap<String, Object> params,
			HttpServletRequest req) {
		CheckinResult c_result = new CheckinResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			c_result.setResult("fail");
			c_result.setResult_msg("session null");
			return c_result;
		}
		String[] mAttribute = { "gh_no", "floor_cd", "room_cd", "bed_cd" };
		String[] subAttribute = { "reserve_id" };
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			c_result.setResult("fail");
			c_result.setResult_msg(chkResult);
			return c_result;
		}

		ChkRES mChkRES = new ChkRES();
		mChkRES.setRES_SEQ((String) params.get("reserve_id"));
		mChkRES.setEMAIL_ID((String) session.getAttribute("user_id"));

		String userId = (String) session.getAttribute("user_id");
		int result = 0;
		String dateTime = Dtime.instance().getGMTDateTime();
		String date = Dtime.instance().getGMTDate();
		List<Reserve_idGetter> reserve_idGetter = null;

		Reserve reserve = new Reserve();
		reserve.setGH_NO((String) params.get("gh_no"));
		reserve.setFLOOR_CD((String) params.get("floor_cd"));
		reserve.setROOM_CD((String) params.get("room_cd"));
		reserve.setBED_CD((String) params.get("bed_cd"));
		reserve.setRESERVE_DATE(date);

		reserve.setSTATE_FG("2");
		reserve.setCHECK_IN_NO(userId);
		reserve.setCHECK_IN_DTIME(dateTime);
		if ((String) params.get("reserve_id") == null) { // WALK-IN - insert
			reserve.setRESERVE_TYPE("0");
			reserve.setRESERVE_NO(userId);
			reserve.setRESERVE_DTIME(dateTime);

			List<ChkFRB> chkResFRB = userDaoImpl.chkResFRB(reserve);
			if (chkResFRB.get(0).getCHK().equals("full")) {
				c_result.setResult("fail");
				c_result.setResult_msg("not your room");
				return c_result;
			}

			List<Reserve> chkResResult = userDaoImpl.chkResDate(reserve);
			if (chkResResult.size() > 0) {
				c_result.setResult("fail");
				c_result.setResult_msg("already checked");
				return c_result;
			}

			result = userDaoImpl.checkinWalk(reserve);
		} else { // App Reservation - update
			reserve.setRESERVE_TYPE("1");
			reserve.setRES_SEQ((String) params.get("reserve_id"));

			List<Reserve> reserveResult = userDaoImpl.chkRES(mChkRES);
			if (reserveResult.size() == 0) {
				c_result.setResult("fail");
				c_result.setResult_msg("not your reserve_id");
				return c_result;
			}

			List<ChkFRB> chkFRB = userDaoImpl.chkFRB(reserve);
			if (chkFRB.size() == 0) {
				c_result.setResult("fail");
				c_result.setResult_msg("not your room");
				return c_result;
			}

			List<Reserve> chkResResult = userDaoImpl.chkResDate(reserve);
			if (chkResResult.size() > 0) {
				c_result.setResult("fail");
				c_result.setResult_msg("already checked");
				return c_result;
			}

			result = userDaoImpl.checkinRes(reserve);
		}
		if (result == 1) {
			// 업주에 GCM message 전송
			Master master = new Master();
			master.setGH_NO((String) params.get("gh_no"));
			List<Master> masterRegId = userDaoImpl.getMasterRegId(master);
			if (masterRegId.size() > 0) {
				// gcm message to master
				GCMSender.instance().sendPush(masterRegId.get(0).getREG_ID(),
						"체크인 알림", "체크인한 고객이 있습니다");
			}

			// reserve_id 가져오기 getter (db접속)
			reserve_idGetter = userDaoImpl.getReserveId(reserve);
			c_result.setResult("success");
			c_result.setResult_msg("success");
			if ((String) params.get("reserve_id") == null
					&& reserve_idGetter != null) {
				c_result.setReserve_id(reserve_idGetter.get(0).getRES_SEQ());
			}
			return c_result;
		} else {
			c_result.setResult("fail");
			c_result.setResult_msg("db connection fail");
			return c_result;
		}
	}// checkin

	@RequestMapping(value = "/rent/checkin/rule", method = RequestMethod.POST)
	public @ResponseBody
	RuleResult rule(@RequestParam HashMap<String, Object> params,
			HttpServletRequest req) {
		RuleResult r_result = new RuleResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			r_result.setResult("fail");
			r_result.setResult_msg("session null");
			return r_result;
		}
		String[] mAttribute = { "reserve_id" };
		String[] subAttribute = {};
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			r_result.setResult("fail");
			r_result.setResult_msg(chkResult);
			return r_result;
		}

		Reserve reserve = new Reserve();
		reserve.setRES_SEQ((String) params.get("reserve_id"));
		List<Rule> rule = userDaoImpl.rule(reserve);

		if (rule.size() > 0) {
			r_result.setResult("success");
			r_result.setGh_name(rule.get(0).getGH_NM());
			r_result.setRule(rule.get(0).getRULE());
			return r_result;
		} else {
			r_result.setResult("fail");
			r_result.setResult_msg("db connection fail");
			return r_result;
		}
	}// rule

	@RequestMapping(value = "/info/room", method = RequestMethod.POST)
	public @ResponseBody
	RoomResult room(@RequestParam HashMap<String, Object> params,
			HttpServletRequest req) {
		RoomResult r_result = new RoomResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			r_result.setResult("fail");
			r_result.setResult_msg("session null");
			return r_result;
		}
		String[] mAttribute = { "reserve_id" };
		String[] subAttribute = {};
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			r_result.setResult("fail");
			r_result.setResult_msg(chkResult);
			return r_result;
		}

		ChkRES mChkRES = new ChkRES();
		mChkRES.setRES_SEQ((String) params.get("reserve_id"));
		mChkRES.setEMAIL_ID((String) session.getAttribute("user_id"));
		List<Reserve> reserveResult = userDaoImpl.chkRES(mChkRES);
		if (reserveResult.size() == 0) {
			r_result.setResult("fail");
			r_result.setResult_msg("not your reserve_id");
			return r_result;
		}

		Reserve reserve = new Reserve();
		reserve.setRES_SEQ((String) params.get("reserve_id"));
		reserve.setCUR_DATE(Dtime.instance().getGMTDate());
		List<Room> room = userDaoImpl.room(reserve);
		if (room.size() > 0) {
			int male_cnt = 0, female_cnt = 0;
			String user_check_indate = "", user_check_outdate = "";
			for (int i = 0; i < room.size(); i++) {
				if (room.get(i).getEMAIL_ID()
						.equals(session.getAttribute("user_id"))) {
					user_check_indate = room.get(i).getRESERVE_DATE();
					user_check_outdate = room.get(i).getCHECK_OUTDATE();
				}
				if (room.get(i).getSEX().equals("0")) {
					male_cnt++;
				} else {
					female_cnt++;
				}
			}
			r_result.setResult("success");
			r_result.setMale_no("" + male_cnt);
			r_result.setFemale_no("" + female_cnt);
			r_result.setUser_check_indate(user_check_indate);
			r_result.setUser_check_outdate(user_check_outdate);
			return r_result;
		} else {
			r_result.setResult("fail");
			r_result.setResult_msg("db connection fail");
			return r_result;
		}
	}// room

	@RequestMapping(value = "/info/profile", method = RequestMethod.GET)
	@ResponseBody
	public ProfileResult profile(HttpServletRequest req) {
		ProfileResult p_result = new ProfileResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			p_result.setResult("fail");
			p_result.setResult_msg("session null");
			return p_result;
		}
		List<Profile> profile = userDaoImpl.profile((String) session
				.getAttribute("user_id"));
		if (profile.size() > 0) {
			p_result.setResult("success");
			p_result.setUser_profile_img_path(profile.get(0)
					.getPROFILE_IMG_PATH());
			p_result.setUser_text(profile.get(0).getINFO());
			String[] fg = new String[6];
			fg[0] = profile.get(0).getVISIT_FG1();
			fg[1] = profile.get(0).getVISIT_FG2();
			fg[2] = profile.get(0).getVISIT_FG3();
			fg[3] = profile.get(0).getVISIT_FG4();
			fg[4] = profile.get(0).getVISIT_FG5();
			fg[5] = profile.get(0).getVISIT_FG6();
			HashMap<String, Object> visit_fg = new HashMap<String, Object>();
			for (int i = 0; i < fg.length; i++) {
				if (fg[i] != null) {
					visit_fg.put(fg[i], "Y");
				}
			}
			p_result.setVisit_fg(visit_fg);
			p_result.setUser_sex(profile.get(0).getSEX());
			p_result.setUser_firstname(profile.get(0).getGUEST_FNM());
			p_result.setUser_lastname(profile.get(0).getGUEST_LNM());
			p_result.setUser_nationality(profile.get(0).getNATIONALITY());
			p_result.setUser_city(profile.get(0).getCITY());
			p_result.setUser_birth(profile.get(0).getBIRTH_DAY());
			p_result.setUser_email(profile.get(0).getE_MAIL());
			return p_result;
		} else {
			p_result.setResult("fail");
			p_result.setResult_msg("db connection fail");
			return p_result;
		}
	}

	@RequestMapping(value = "/upload")
	@ResponseBody
	public DefaultResult upload(
			@RequestParam HashMap<String, Object> params,
			@RequestParam(value = "main") MultipartFile main,
			@RequestParam(value = "detail1", required = false) MultipartFile detail1,
			@RequestParam(value = "detail2", required = false) MultipartFile detail2,
			@RequestParam(value = "detail3", required = false) MultipartFile detail3,
			@RequestParam(value = "detail4", required = false) MultipartFile detail4,
			@RequestParam(value = "detail5", required = false) MultipartFile detail5,
			@RequestParam(value = "detail6", required = false) MultipartFile detail6,
			@RequestParam(value = "detail7", required = false) MultipartFile detail7,
			@RequestParam(value = "detail8", required = false) MultipartFile detail8,
			@RequestParam(value = "detail9", required = false) MultipartFile detail9,
			@RequestParam(value = "detail10", required = false) MultipartFile detail10,
			HttpServletRequest req) {
		DefaultResult d_result = new DefaultResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			d_result.setResult("fail");
			d_result.setResult_msg("session null");
			return d_result;
		}
		String[] mAttribute = { "gh_no" };
		String[] subAttribute = {};
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			return null;
		}
		d_result.setResult("success");
		d_result.setResult_msg("success");

		// 이미지 처리
		if (main != null) {
			// String rootPath = "C:/Users/wleodud/Desktop/testRoot/";
			// File dir = new File(rootPath + GUESTHOUSE_IMG_PATH
			// + (String) params.get("gh_no"));
			File dir = new File(session.getServletContext().getRealPath("/")
					+ GUESTHOUSE_IMG_PATH + (String) params.get("gh_no"));
			if (!dir.isDirectory()) {
				dir.mkdirs();
			}
			File f = new File(dir, "main.png");
			try {
				main.transferTo(f);
				if (detail1 != null) {
					f = new File(dir, "detail1.png");
					detail1.transferTo(f);
				}
				if (detail2 != null) {
					f = new File(dir, "detail2.png");
					detail2.transferTo(f);
				}
				if (detail3 != null) {
					f = new File(dir, "detail3.png");
					detail3.transferTo(f);
				}
				if (detail4 != null) {
					f = new File(dir, "detail4.png");
					detail4.transferTo(f);
				}
				if (detail5 != null) {
					f = new File(dir, "detail5.png");
					detail5.transferTo(f);
				}
				if (detail6 != null) {
					f = new File(dir, "detail6.png");
					detail6.transferTo(f);
				}
				if (detail7 != null) {
					f = new File(dir, "detail7.png");
					detail7.transferTo(f);
				}
				if (detail8 != null) {
					f = new File(dir, "detail8.png");
					detail8.transferTo(f);
				}
				if (detail9 != null) {
					f = new File(dir, "detail9.png");
					detail9.transferTo(f);
				}
				if (detail10 != null) {
					f = new File(dir, "detail10.png");
					detail10.transferTo(f);
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
				d_result.setResult("fail");
				d_result.setResult_msg("IllegalStateException");
			} catch (IOException e) {
				e.printStackTrace();
				d_result.setResult("fail");
				d_result.setResult_msg("IOException");
			}
		}

		return d_result;
	}

	@RequestMapping("/uploadForm")
	public ModelAndView uploadForm() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("uploadForm");
		return mv;
	}

	@RequestMapping(value = "/info/profile/insert", headers = "content-type=multipart/*", method = RequestMethod.POST)
	public @ResponseBody
	DefaultResult profileInsert(
			@RequestParam HashMap<String, Object> params,
			@RequestParam(value = "user_profile_img", required = false) MultipartFile user_profile_img,
			HttpServletRequest req) {
		DefaultResult d_result = new DefaultResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			d_result.setResult("fail");
			d_result.setResult_msg("session null");
			return d_result;
		}
		String[] mAttribute = { "user_text", "festival", "eatout", "business",
				"shopping", "sightseeing", "hangout", "user_sex",
				"user_firstname", "user_lastname", "user_nationality",
				"user_city", "user_birth", "user_email" };
		String[] subAttribute = { "user_profile_img" };
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			d_result.setResult("fail");
			d_result.setResult_msg(chkResult);
			return d_result;
		}

		int result = 0;
		String user_id = (String) session.getAttribute("user_id");

		Profile profile = new Profile();
		profile.setEMAIL_ID(user_id);
		profile.setINFO((String) params.get("user_text"));
		String[] visit_fg = new String[6];
		visit_fg[0] = (String) params.get("festival");
		visit_fg[1] = (String) params.get("eatout");
		visit_fg[2] = (String) params.get("business");
		visit_fg[3] = (String) params.get("shopping");
		visit_fg[4] = (String) params.get("sightseeing");
		visit_fg[5] = (String) params.get("hangout");
		for (int i = 0; i < visit_fg.length; i++) {
			if (visit_fg[i].equals("Y")) {
				switch (i) {
				case 0:
					profile.setVISIT_FG1("festival");
					break;
				case 1:
					profile.setVISIT_FG2("eatout");
					break;
				case 2:
					profile.setVISIT_FG3("business");
					break;
				case 3:
					profile.setVISIT_FG4("shopping");
					break;
				case 4:
					profile.setVISIT_FG5("sightseeing");
					break;
				case 5:
					profile.setVISIT_FG6("hangout");
					break;
				}
			} else if (visit_fg[i].equals("N")) {
				switch (i) {
				case 0:
					profile.setVISIT_FG1("");
					break;
				case 1:
					profile.setVISIT_FG2("");
					break;
				case 2:
					profile.setVISIT_FG3("");
					break;
				case 3:
					profile.setVISIT_FG4("");
					break;
				case 4:
					profile.setVISIT_FG5("");
					break;
				case 5:
					profile.setVISIT_FG6("");
					break;
				}
			}
		}
		profile.setSEX((String) params.get("user_sex"));
		profile.setGUEST_FNM((String) params.get("user_firstname"));
		// session.setAttribute("user_firstname", user_firstname);
		profile.setGUEST_LNM((String) params.get("user_lastname"));
		// session.setAttribute("user_lastname", user_lastname);
		profile.setNATIONALITY((String) params.get("user_nationality"));
		profile.setCITY((String) params.get("user_city"));
		profile.setBIRTH_DAY((String) params.get("user_birth"));
		profile.setE_MAIL((String) params.get("user_email"));
		profile.setPROFILE_IMG_PATH(SERVER_URL + PROFILE_IMG_PATH + user_id
				+ "/profile.png");

		// 이미지 처리
		if (user_profile_img.getSize() > 1000) {
			File dir = new File(session.getServletContext().getRealPath("/")
					+ PROFILE_IMG_PATH + user_id);
			if (!dir.isDirectory()) {
				dir.mkdirs();
			}
			File f = new File(dir, "profile.png");
			try {
				user_profile_img.transferTo(f);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		result = userDaoImpl.profileUpdate(profile);
		if (result == 1) {
			d_result.setResult("success");
			d_result.setResult_msg("success");
			return d_result;
		} else {
			d_result.setResult("fail");
			d_result.setResult_msg("db connection fail");
			return d_result;
		}
	}// profileInsert

	@RequestMapping(value = "/info/room/talk", method = RequestMethod.POST)
	public @ResponseBody
	TalkResult roomTalk(@RequestParam HashMap<String, Object> params,
			HttpServletRequest req) {
		TalkResult t_result = new TalkResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			t_result.setResult("fail");
			t_result.setResult_msg("session null");
			return t_result;
		}
		String[] mAttribute = { "reserve_id" };
		String[] subAttribute = {};
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			t_result.setResult("fail");
			t_result.setResult_msg(chkResult);
			return t_result;
		}

		ChkRES mChkRES = new ChkRES();
		mChkRES.setRES_SEQ((String) params.get("reserve_id"));
		mChkRES.setEMAIL_ID((String) session.getAttribute("user_id"));
		List<Reserve> reserveResult = userDaoImpl.chkRES(mChkRES);
		if (reserveResult.size() == 0) {
			t_result.setResult("fail");
			t_result.setResult_msg("not your reserve_id");
			return t_result;
		}

		Reserve res = new Reserve();
		res.setRES_SEQ((String) params.get("reserve_id"));
		res.setCUR_DATE(Dtime.instance().getGMTDate());
		List<Talk> talk = userDaoImpl.talk(res);

		ArrayList<HashMap<String, Object>> info = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> mParams = null;
		ArrayList<HashMap<String, Object>> replys = null;
		String chkId = "0";
		for (int i = 0; i < talk.size(); i++) {
			if (!talk.get(i).getRES_SEQ().equals(chkId)) {
				mParams = new HashMap<String, Object>();
				replys = new ArrayList<HashMap<String, Object>>();
				mParams.put("res_seq", talk.get(i).getRES_SEQ());
				mParams.put("user_id", talk.get(i).getEMAIL_ID());
				mParams.put("user_profile_img_path", talk.get(i)
						.getPROFILE_IMG_PATH());
				mParams.put("user_check_indate", talk.get(i).getRESERVE_DATE());
				mParams.put("user_check_outdate", talk.get(i)
						.getCHECK_OUTDATE());
				mParams.put("user_firstname", talk.get(i).getGUEST_FNM());
				mParams.put("user_lastname", talk.get(i).getGUEST_LNM());
				mParams.put("user_nationality", talk.get(i).getNATIONALITY());
				mParams.put("user_text", talk.get(i).getINFO());
				chkId = talk.get(i).getRES_SEQ();
			}
			if (talk.get(i).getrID() != null) {
				HashMap<String, Object> reply = new HashMap<String, Object>();
				reply.put("reply_user_profile_img_path", talk.get(i).getrPATH());
				reply.put("reply_user_firstname", talk.get(i).getrFNM());
				reply.put("reply_user_lastname", talk.get(i).getrLNM());
				reply.put("reply_user_text", talk.get(i).getREPLY_TEXT());
				reply.put("reply_dtime", talk.get(i).getREPLY_DTIME());
				replys.add(reply);
			}
			if (i == talk.size() - 1
					|| !talk.get(i + 1).getRES_SEQ().equals(chkId)) {
				mParams.put("reply", replys);
				info.add(mParams);
			}
		}

		Reserve reserve = new Reserve();
		reserve.setRES_SEQ((String) params.get("reserve_id"));
		reserve.setCUR_DATE(Dtime.instance().getGMTDate());
		List<Room> room = userDaoImpl.room(reserve);
		int male_cnt = 0, female_cnt = 0;
		String user_check_indate = "", user_check_outdate = "";
		for (int i = 0; i < room.size(); i++) {
			if (room.get(i).getEMAIL_ID()
					.equals(session.getAttribute("user_id"))) {
				user_check_indate = room.get(i).getRESERVE_DATE();
				user_check_outdate = room.get(i).getCHECK_OUTDATE();
			}
			if (room.get(i).getSEX().equals("0")) {
				male_cnt++;
			} else {
				female_cnt++;
			}
		}
		if (talk.size() > 0) {
			t_result.setResult("success");
			t_result.setMale_no("" + male_cnt);
			t_result.setFemale_no("" + female_cnt);
			t_result.setUser_check_indate(user_check_indate);
			t_result.setUser_check_outdate(user_check_outdate);
			t_result.setInfo(info);
			return t_result;
		} else {
			t_result.setResult("fail");
			t_result.setResult_msg("db connection fail");
			return t_result;
		}
	}// roomTalk

	@RequestMapping(value = "/info/room/talk/reply", method = RequestMethod.POST)
	public @ResponseBody
	DefaultResult roomTalkReply(@RequestParam HashMap<String, Object> params,
			HttpServletRequest req) {
		DefaultResult d_result = new DefaultResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			d_result.setResult("fail");
			d_result.setResult_msg("session null");
			return d_result;
		}
		String[] mAttribute = { "target_reserve_id", "reserve_id",
				"reply_user_text" };
		String[] subAttribute = {};
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			d_result.setResult("fail");
			d_result.setResult_msg(chkResult);
			return d_result;
		}

		ChkRES mChkRES = new ChkRES();
		mChkRES.setRES_SEQ((String) params.get("reserve_id"));
		mChkRES.setEMAIL_ID((String) session.getAttribute("user_id"));
		List<Reserve> reserveResult = userDaoImpl.chkRES(mChkRES);
		if (reserveResult.size() == 0) {
			d_result.setResult("fail");
			d_result.setResult_msg("not your reserve_id");
			return d_result;
		}

		int result = 0;

		Reply reply = new Reply();
		reply.setTARGET_RES_SEQ((String) params.get("target_reserve_id"));
		reply.setREPLY_RES_SEQ((String) params.get("reserve_id"));
		reply.setREPLY_TEXT((String) params.get("reply_user_text"));
		reply.setREPLY_DTIME(Dtime.instance().getGMTDateTime());

		result = userDaoImpl.reply(reply);

		if (result == 1) {
			d_result.setResult("success");
			d_result.setResult_msg("success");
			return d_result;
		} else {
			d_result.setResult("fail");
			d_result.setResult_msg("db connection fail");
			return d_result;
		}
	}// roomTalkReply

	@RequestMapping(value = "/gh/search", method = RequestMethod.GET)
	@ResponseBody
	public ListResult search(HttpServletRequest req) {
		ListResult l_result = new ListResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			l_result.setResult("fail");
			l_result.setResult_msg("session null");
			return l_result;
		}
		List<Search> search = userDaoImpl.search();

		if (search.size() > 0) {
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < search.size(); i++) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("city", search.get(i).getCITY());
				list.add(param);
			}
			l_result.setResult("success");
			l_result.setList(list);
			return l_result;
		} else {
			l_result.setResult("fail");
			l_result.setResult_msg("db connection fail");
			return l_result;
		}
	}// search

	@RequestMapping(value = "/gh/list", method = RequestMethod.POST)
	public @ResponseBody
	GHListResult ghList(@RequestParam HashMap<String, Object> params,
			HttpServletRequest req) {
		GHListResult ghl_result = new GHListResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			ghl_result.setResult("fail");
			ghl_result.setResult_msg("session null");
			return ghl_result;
		}
		String[] mAttribute = { "nation", "city", "user_check_indate",
				"user_check_outdate", "user_group", "min_rent_price",
				"max_rent_price" };
		String[] subAttribute = { "parking", "washer", "barbeque",
				"rentalbike", "creditcard", "petallowed", "domitory", "pickup",
				"breakfast", "onlyfemale", "fishing", "grouptour",
				"foreignerfriendly", "tourprogram", "culturetour" };
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			ghl_result.setResult("fail");
			ghl_result.setResult_msg(chkResult);
			return ghl_result;
		}
		Filter filter = new Filter();
		filter.setNATION((String) params.get("nation"));
		filter.setCITY((String) params.get("city"));
		filter.setRESERVE_DATE((String) params.get("user_check_indate"));
		filter.setCHECK_OUTDATE((String) params.get("user_check_outdate"));
		filter.setGROUP((String) params.get("user_group"));
		filter.setMIN_PRICE((String) params.get("min_rent_price"));
		filter.setMAX_PRICE((String) params.get("max_rent_price"));

		HashMap<String, Object> interest = new HashMap<String, Object>();
		int chkSubLen = 0;
		for (int i = 0; i < subAttribute.length; i++) {
			if (params.containsKey(subAttribute[i])) {
				interest.put(subAttribute[i], subAttribute[i]);
				chkSubLen++;
			} else {
				interest.put(subAttribute[i], "N");
			}
		}
		filter.setINTEREST(interest);
		List<GHList> ghList = null;
		if (chkSubLen == 0) {
			ghList = userDaoImpl.ghList(filter);
		} else {
			ghList = userDaoImpl.ghListInterest(filter);
		}

		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		if (ghList.size() > 0) {
			for (int i = 0; i < ghList.size(); i++) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("gh_no", ghList.get(i).getGH_NO());
				param.put("gh_name", ghList.get(i).getGH_NM());
				param.put("gh_address", ghList.get(i).getADDRESS());
				param.put("gh_rent_price", ghList.get(i).getRENT_PRICE());
				param.put("gh_latitude", ghList.get(i).getLATITUDE());
				param.put("gh_longitude", ghList.get(i).getLONGITUDE());
				param.put("gh_main_img_path", ghList.get(i).getMPATH());
				param.put("gh_detail_img_path1", ghList.get(i).getDPATH1());
				param.put("gh_detail_img_path2", ghList.get(i).getDPATH2());
				param.put("gh_detail_img_path3", ghList.get(i).getDPATH3());
				param.put("gh_detail_img_path4", ghList.get(i).getDPATH4());
				param.put("gh_detail_img_path5", ghList.get(i).getDPATH5());
				param.put("gh_detail_img_path6", ghList.get(i).getDPATH6());
				param.put("gh_detail_img_path7", ghList.get(i).getDPATH7());
				param.put("gh_detail_img_path8", ghList.get(i).getDPATH8());
				param.put("gh_detail_img_path9", ghList.get(i).getDPATH9());
				param.put("gh_detail_img_path10", ghList.get(i).getDPATH10());
				list.add(param);
			}
			ghl_result.setList(list);
			ghl_result.setResult("success");
			ghl_result.setResult_msg("success");
			return ghl_result;
		} else {
			ghl_result.setResult("fail");
			ghl_result.setResult_msg("not founds");
			return ghl_result;
		}
	}// ghList

	@RequestMapping(value = "/gh/detail", method = RequestMethod.POST)
	public @ResponseBody
	DetailResult ghDetail(@RequestParam HashMap<String, Object> params,
			HttpServletRequest req) {
		DetailResult detail_result = new DetailResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			detail_result.setResult("fail");
			detail_result.setResult_msg("session null");
			return detail_result;
		}
		String[] mAttribute = { "gh_no" };
		String[] subAttribute = {};
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			detail_result.setResult("fail");
			detail_result.setResult_msg(chkResult);
			return detail_result;
		}

		Detail detail = new Detail();
		detail.setGH_NO((String) params.get("gh_no"));
		List<Detail> detailList = userDaoImpl.detail(detail);

		if (detailList.size() > 0) {
			detail_result.setGh_no(detailList.get(0).getGH_NO());
			detail_result.setGh_name(detailList.get(0).getGH_NM());
			detail_result.setGh_rent_price(detailList.get(0).getRENT_PRICE());
			detail_result.setGh_main_img_path(detailList.get(0)
					.getMAIN_IMG_PATH());
			detail_result.setGh_detail_img_path1(detailList.get(0)
					.getDETAIL_IMG_PATH1());
			detail_result.setGh_detail_img_path2(detailList.get(0)
					.getDETAIL_IMG_PATH2());
			detail_result.setGh_detail_img_path3(detailList.get(0)
					.getDETAIL_IMG_PATH3());
			detail_result.setGh_detail_img_path4(detailList.get(0)
					.getDETAIL_IMG_PATH4());
			detail_result.setGh_detail_img_path5(detailList.get(0)
					.getDETAIL_IMG_PATH5());
			detail_result.setGh_detail_img_path6(detailList.get(0)
					.getDETAIL_IMG_PATH6());
			detail_result.setGh_detail_img_path7(detailList.get(0)
					.getDETAIL_IMG_PATH7());
			detail_result.setGh_detail_img_path8(detailList.get(0)
					.getDETAIL_IMG_PATH8());
			detail_result.setGh_detail_img_path9(detailList.get(0)
					.getDETAIL_IMG_PATH9());
			detail_result.setGh_detail_img_path10(detailList.get(0)
					.getDETAIL_IMG_PATH10());
			detail_result.setGh_score(detailList.get(0).getSCORE());
			detail_result.setGh_house_type(detailList.get(0).getHOUSE_TYPE());
			detail_result.setGh_room_cnt(detailList.get(0).getROOM_CNT());
			detail_result.setGh_bath_cnt(detailList.get(0).getRESTROOM_CNT());
			detail_result.setGh_accomodate_cnt(detailList.get(0).getBED_CNT());
			detail_result.setGh_communityspace_cnt(detailList.get(0)
					.getCOMMUNITYSPACE());
			detail_result.setGh_introdution(detailList.get(0).getINTRODUCE());
			detail_result.setGh_tel(detailList.get(0).getTEL_NO());
			detail_result.setGh_latitude(detailList.get(0).getLATITUDE());
			detail_result.setGh_longitude(detailList.get(0).getLONGITUDE());
			String bedType = detailList.get(0).getBED_TYPE();
			String chkBedType = bedType;
			ArrayList<HashMap<String, Object>> review = new ArrayList<HashMap<String, Object>>();
			String chkId = "";
			String supportNm = detailList.get(0).getSUPPORT_NM();
			boolean supportFg = false;
			for (int i = 0; i < detailList.size(); i++) {
				if (!chkBedType.equals(detailList.get(i).getBED_TYPE())) {
					chkBedType = detailList.get(i).getBED_TYPE();
					bedType += "," + detailList.get(i).getBED_TYPE();
				}
				if (!chkId.equals(detailList.get(i).getPROFILE_IMG_PATH() + ""
						+ detailList.get(i).getADD_DATE())) {
					HashMap<String, Object> param = new HashMap<String, Object>();
					param.put("review_user_firstname", detailList.get(i)
							.getGUEST_FNM());
					param.put("review_user_lastname", detailList.get(i)
							.getGUEST_LNM());
					param.put("review_user_profile_img_path", detailList.get(i)
							.getPROFILE_IMG_PATH());
					param.put("review_date", detailList.get(i).getADD_DATE());
					param.put("review_text", detailList.get(i).getREVIEW());
					review.add(param);
					chkId = detailList.get(i).getPROFILE_IMG_PATH() + ""
							+ detailList.get(i).getADD_DATE();
					if (i != 0) {
						supportFg = true;
					}
				} else if (!supportFg) {
					supportNm += "," + detailList.get(i).getSUPPORT_NM();
				}
			}
			detail_result.setGh_bed_type(bedType);
			detail_result.setGh_advanced_filter(supportNm);
			detail_result.setReview(review);

			detail_result.setResult("success");
			detail_result.setResult_msg("success");
			return detail_result;
		} else {
			detail_result.setResult("fail");
			detail_result.setResult_msg("db connection fail");
			return detail_result;
		}
	}// ghDetail

	@RequestMapping(value = "/gh/booking", method = RequestMethod.POST)
	public @ResponseBody
	BookingResult ghBooking(@RequestParam HashMap<String, Object> params,
			HttpServletRequest req) {
		BookingResult b_result = new BookingResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			b_result.setResult("fail");
			b_result.setResult_msg("session null");
			return b_result;
		}
		String[] mAttribute = { "gh_no", "nation", "city", "user_check_indate",
				"user_check_outdate", "user_group", "min_rent_price",
				"max_rent_price" };
		String[] subAttribute = { "parking", "washer", "barbeque",
				"rentalbike", "creditcard", "petallowed", "domitory", "pickup",
				"breakfast", "onlyfemale", "fishing", "grouptour",
				"foreignerfriendly", "tourprogram", "culturetour" };
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			b_result.setResult("fail");
			b_result.setResult_msg(chkResult);
			return b_result;
		}
		Filter filter = new Filter();
		filter.setGH_NO((String) params.get("gh_no"));
		filter.setNATION((String) params.get("nation"));
		filter.setCITY((String) params.get("city"));
		filter.setRESERVE_DATE((String) params.get("user_check_indate"));
		filter.setCHECK_OUTDATE((String) params.get("user_check_outdate"));
		filter.setGROUP((String) params.get("user_group"));
		filter.setMIN_PRICE((String) params.get("min_rent_price"));
		filter.setMAX_PRICE((String) params.get("max_rent_price"));

		HashMap<String, Object> interest = new HashMap<String, Object>();
		int chkSubLen = 0;
		for (int i = 0; i < subAttribute.length; i++) {
			if (params.containsKey(subAttribute[i])) {
				interest.put(subAttribute[i], subAttribute[i]);
				chkSubLen++;
			} else {
				interest.put(subAttribute[i], "N");
			}
		}
		filter.setINTEREST(interest);
		List<Booking> booking = null;
		if (chkSubLen == 0) {
			booking = userDaoImpl.ghBooking(filter);
		} else {
			booking = userDaoImpl.ghBookingInterest(filter);
		}

		if (booking.size() > 0) {
			b_result.setGh_no(booking.get(0).getGH_NO());
			b_result.setGh_name(booking.get(0).getGH_NM());
			b_result.setGh_address(booking.get(0).getADDRESS());

			ArrayList<HashMap<String, Object>> floor = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> param = null;
			ArrayList<HashMap<String, Object>> room = null;
			String chkFloor = "";
			for (int i = 0; i < booking.size(); i++) {
				if (!booking.get(i).getFLOOR_CD().equals(chkFloor)) {
					param = new HashMap<String, Object>();
					room = new ArrayList<HashMap<String, Object>>();
					param.put("floor_cd", booking.get(i).getFLOOR_CD());
					param.put("floor_name", booking.get(i).getFLOOR_NM());

					chkFloor = booking.get(i).getFLOOR_CD();
				}
				HashMap<String, Object> rParam = new HashMap<String, Object>();
				rParam.put("room_cd", booking.get(i).getROOM_CD());
				rParam.put("room_name", booking.get(i).getROOM_NM());
				rParam.put("rent_price", booking.get(i).getRENT_PRICE());
				room.add(rParam);
				if (i == booking.size() - 1
						|| !booking.get(i + 1).getFLOOR_CD().equals(chkFloor)) {
					param.put("room", room);
					floor.add(param);
				}
			}
			b_result.setFloor(floor);
			b_result.setResult("success");
			b_result.setResult_msg("success");
			return b_result;
		} else {
			b_result.setResult("fail");
			b_result.setResult_msg("not found");
			return b_result;
		}
	}// ghBooking

	@RequestMapping(value = "/gh/booking/contact", method = RequestMethod.POST)
	public @ResponseBody
	ContactResult ghBookingContact(
			@RequestParam HashMap<String, Object> params, HttpServletRequest req) {
		ContactResult c_result = new ContactResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			c_result.setResult("fail");
			c_result.setResult_msg("session null");
			return c_result;
		}
		String[] mAttribute = { "gh_no", "user_check_indate",
				"user_check_outdate", "rent_price", "user_group", "floor_cd",
				"room_cd" };
		String[] subAttribute = {};
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			c_result.setResult("fail");
			c_result.setResult_msg(chkResult);
			return c_result;
		}
		String dateTime = Dtime.instance().getGMTDateTime();
		Contact contact = new Contact();
		contact.setGH_NO((String) params.get("gh_no"));
		contact.setRESERVE_DATE((String) params.get("user_check_indate"));
		contact.setCHECK_OUTDATE((String) params.get("user_check_outdate"));
		contact.setUSER_GROUP((String) params.get("user_group"));
		contact.setFLOOR_CD((String) params.get("floor_cd"));
		contact.setROOM_CD((String) params.get("room_cd"));
		contact.setSTATE_FG("0");
		contact.setRENT_PRICE((String) params.get("rent_price"));
		contact.setRESERVE_NO((String) session.getAttribute("user_id"));
		contact.setRESERVE_DTIME(dateTime);
		contact.setRESERVE_TYPE("1");

		List<Contact> contactList = userDaoImpl.chkContact(contact);
		if (contactList.size() > 0
				&& contactList.get(0).getCONTACT().equals("1")) {
			try {
				int contactResult = 0;
				for (int i = 0; i < Integer.parseInt(contact.getUSER_GROUP()); i++) {
					contactResult = userDaoImpl.bookingContact(contact);
					if (i + 1 == Integer.parseInt(contact.getUSER_GROUP())
							|| contactResult == 0) {
						break;
					}
					contactResult = 0;
				}

				if (contactResult == 1) {
					// reserve_id 가져오기 getter (db접속)
					List<ContactGetter> contactGetter = userDaoImpl
							.contactGetter(contact);
					c_result.setGh_reserve_id(contactGetter.get(0).getRES_SEQ());
					c_result.setGh_rent_price(contactGetter.get(0)
							.getRENT_PRICE());
					c_result.setGh_bank_name(contactGetter.get(0).getBANK_NM());
					c_result.setGh_acct_cd(contactGetter.get(0).getACCT_CD());
					c_result.setGh_acct_name(contactGetter.get(0).getACCT_NM());
					c_result.setGh_reserve_dtime(contactGetter.get(0)
							.getRESERVE_DTIME());
					c_result.setGh_tel(contactGetter.get(0).getTEL_NO());

					// 업주에 GCM message 전송
					Master master = new Master();
					master.setGH_NO((String) params.get("gh_no"));
					List<Master> masterRegId = userDaoImpl
							.getMasterRegId(master);
					if (masterRegId.size() > 0) {
						// gcm message to master
						GCMSender.instance().sendPush(
								masterRegId.get(0).getREG_ID(), "예약 알림",
								"예약한 고객이 있습니다");
					}

					c_result.setResult("success");
					c_result.setResult_msg("success");
					return c_result;
				} else {
					c_result.setResult("fail");
					c_result.setResult_msg("reservation fail");
					return c_result;
				}
			} catch (Exception e) {
				e.printStackTrace();
				c_result.setResult("fail");
				c_result.setResult_msg("unknown...");
				return c_result;
			}
		} else {
			c_result.setResult("fail");
			c_result.setResult_msg("already full reservation");
			return c_result;
		}
	}// ghBookingContact

	@RequestMapping(value = "/push/regid", method = RequestMethod.POST)
	public @ResponseBody
	DefaultResult setRegid(@RequestParam HashMap<String, Object> params,
			HttpServletRequest req) {
		DefaultResult d_result = new DefaultResult();
		HttpSession session = req.getSession(false);
		if (session == null) {
			d_result.setResult("fail");
			d_result.setResult_msg("session null");
			return d_result;
		}
		String[] mAttribute = { "user_reg_id" };
		String[] subAttribute = {};
		String chkResult = errorCheckInParams(params, mAttribute, subAttribute);
		if (chkResult != null) {
			d_result.setResult("fail");
			d_result.setResult_msg(chkResult);
			return d_result;
		}
		Login login = new Login();
		login.setEMAIL_ID((String) session.getAttribute("user_id"));
		login.setREG_ID((String) params.get("user_reg_id"));

		int result = userDaoImpl.setRegid(login);
		if (result == 1) {
			d_result.setResult("success");
			d_result.setResult_msg("success");
			return d_result;
		} else {
			d_result.setResult("fail");
			d_result.setResult_msg("db connection fail");
			return d_result;
		}
	}// setRegid
}
