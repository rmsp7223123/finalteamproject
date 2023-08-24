package com.hanul.cloud;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import cloud.member.EphoneVO;
import cloud.member.GodokVO;
import cloud.member.LocationVO;
import net.nurigo.java_sdk.api.Message;

@RestController
@RequestMapping("/godok")
public class GodokController {

	@Autowired
	private cloud.common.CommonUtility common;

	@Autowired
	@Qualifier("project")
	SqlSession sql;
	
	final String APIKEY = "NCSIGGEIAAPYSXHO";
	final String APISECRET = "Q3WAV69X3RISPNBEJLIQ2YN8OCCHGOAY";

	@RequestMapping(value = "/viewLocationList", produces = "text/html;charset=utf-8")
	public String viewLocationList(String member_id) {
		List<LocationVO> list = sql.selectList("godok.viewLocationList", member_id);
		return new Gson().toJson(list);
	}

	@RequestMapping(value = "/viewEphone", produces = "text/html;charset=utf-8")
	public String viewEPhone(String member_id) {
		List<EphoneVO> list = sql.selectList("godok.viewEphone", member_id);
		return new Gson().toJson(list);
	}
	
	@RequestMapping("/sendGodokMsg")
	public String sendGodokMsg(LocationVO vo) {
		List<LocationVO> location_list =sql.selectList("godok.viewLocationList", vo);
		List<EphoneVO> ephone_list = sql.selectList("godok.viewEphone", vo);
		Message sms = new Message(APIKEY, APISECRET);
        for (int i = 0; i < location_list.size(); i++) {
            LocationVO location = location_list.get(i);
            EphoneVO ephone = ephone_list.get(i);
    		HashMap<String, String> params = new HashMap();
    		params.put("to", ephone.getEphone_phone());
    		params.put("from", "01096024788");
    		params.put("type", "SMS"); //SMS, LMS, MMS ...
    		params.put("text", "고독사 문자 내용 담을곳");
    		params.put("app_version", "JAVA SDK v1.2");
    		
    		try {
//    			JSONObject obj = sms.send(params);
//    			System.out.println(obj.toString()) ;
    			return "";
    		} catch (Exception e) {
    			e.printStackTrace();
    			return "실패";
    		}
            
        }	
		return "";
	}
	
	@RequestMapping("viewGodokAlarmList")
	public String viewGodokAlarmList(GodokVO vo) {
		List<GodokVO> list = sql.selectList("godok.viewGodokAlarmList", vo);
		return new Gson().toJson(list);
	}
}
