package com.hanul.cloud;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import cloud.member.EphoneVO;
import cloud.member.GodokVO;
import cloud.member.LocationVO;
import cloud.member.MemberVO;
import cloud.setting.OptionVO;
import net.nurigo.java_sdk.api.Message;

@RestController
@RequestMapping("/godok")
@EnableScheduling
public class GodokController {

	@Autowired
	private cloud.common.CommonUtility common;

	@Autowired
	@Qualifier("project")
	SqlSession sql;

	final String APIKEY = "NCSIGGEIAAPYSXHO";
	final String APISECRET = "Q3WAV69X3RISPNBEJLIQ2YN8OCCHGOAY";

	@Scheduled(fixedRate = 3600000)
	@RequestMapping(value = "/viewLocationList", produces = "text/html;charset=utf-8")
	public String viewLocationList() {
//		sendGodokSms();
		System.out.println("되는지 확인");
		return "확인용";
	}

	@RequestMapping(value = "/viewEphone", produces = "text/html;charset=utf-8")
	public String viewEPhone(String member_id) {
		List<EphoneVO> list = sql.selectList("godok.viewEphone", member_id);
		return new Gson().toJson(list);
	}

	@RequestMapping(value = "/sendGodokMsg", produces = "text/html;charset=utf-8")
	public String sendGodokMsg() {
		sendGodokSms();
		return "";
	}

	@RequestMapping("/viewGodokAlarmList")
	public String viewGodokAlarmList(GodokVO vo) {
		List<GodokVO> list = sql.selectList("godok.viewGodokAlarmList", vo);
		return new Gson().toJson(list);
	}
	
	@RequestMapping("/sendOneGodokMsg")
	public void sendOneGodokMsg(String member_id) {
		Message sms = new Message(APIKEY, APISECRET);
		List<LocationVO> location_list = sql.selectList("godok.viewLocationOne", member_id);
		LocationVO location = location_list.get(0);
		if(location.getOption_godok_alarm().equals("Y")) {
			HashMap<String, String> params = new HashMap();
			params.put("to", location.getEphone_phone());
			params.put("from", "01096024788");
			params.put("type", "SMS"); // SMS, LMS, MMS ...
			MemberVO vo = sql.selectOne("login.checkId", member_id);
			params.put("text", "우동탑 안부알림\n["+vo.getMember_name()+"의 안부를 확인하세요]");
			params.put("app_version", "JAVA SDK v1.2");
			try {
        			JSONObject obj = sms.send(params);
//        			System.out.println(obj.toString());
				HashMap<String, Object> params2 = new HashMap<String, Object>();
				params2.put("member_id", location.getMember_id());
				params2.put("alarm_time", LocalDateTime.now());
				sql.insert("godok.addGodokAlarm", params2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void sendGodokSms() {
		List<LocationVO> location_list = sql.selectList("godok.viewLocationList");
//		List<EphoneVO> ephone_list = sql.selectList("godok.viewEphone", location_list);
		Message sms = new Message(APIKEY, APISECRET);
		if (location_list != null) {
			for (int i = 0; i < location_list.size(); i++) {
				LocationVO location = location_list.get(i);
				if(location.getOption_godok_alarm().equals("Y")) {
					HashMap<String, String> params = new HashMap();
					params.put("to", location.getEphone_phone());
					params.put("from", "01096024788");
					params.put("type", "SMS"); // SMS, LMS, MMS ...
					params.put("text", "안부 문자 담을 곳");
					params.put("app_version", "JAVA SDK v1.2");
					try {
//		        			JSONObject obj = sms.send(params);
//		        			System.out.println(obj.toString());
						System.out.println(location.getEphone_phone() + "번 보냄");
						HashMap<String, Object> params2 = new HashMap<String, Object>();
						params2.put("member_id", location.getMember_id());
						params2.put("alarm_time", LocalDateTime.now());
						sql.insert("godok.addGodokAlarm", params2);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
		}

		}
		sql.update("godok.updateLocationTime");
	}
}
