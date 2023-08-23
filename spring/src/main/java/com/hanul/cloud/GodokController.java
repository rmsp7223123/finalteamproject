package com.hanul.cloud;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import cloud.member.EphoneVO;
import cloud.member.GodokVO;
import cloud.member.LocationVO;

@RestController
@RequestMapping("/godok")
public class GodokController {

	@Autowired
	private cloud.common.CommonUtility common;

	@Autowired
	@Qualifier("project")
	SqlSession sql;

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
	public String sendGodokMsg(String member_id) {
		List<LocationVO> list =sql.selectList("godok.viewLocationList", member_id);
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < list.size(); i++) {
            LocationVO location = list.get(i);
            String locationTimeString = location.getLocation_time();
            LocalDateTime locationTime = LocalDateTime.parse(locationTimeString, formatter);

            long daysDifference = ChronoUnit.DAYS.between(locationTime, now);
            System.out.println("test");
            if (daysDifference == 3) {
                // 보호자에게 문자 전송
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
