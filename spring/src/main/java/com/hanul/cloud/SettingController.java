package com.hanul.cloud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import cloud.member.FavorVO;
import cloud.member.MemberVO;
import cloud.setting.OptionVO;

@RestController
@RequestMapping("setting")
public class SettingController {

	@Autowired
	private cloud.common.CommonUtility common;

	@Autowired
	@Qualifier("project")
	SqlSession sql;

	@RequestMapping("/checkNickname")
	public String checkNickname(String member_nickname) {
		MemberVO vo = sql.selectOne("setting.checkNickname", member_nickname);
		return new Gson().toJson(vo);
	}

	@RequestMapping("/changeNickname")
	public String changeNickName(String member_nickname, String member_id) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("member_nickname", member_nickname);
		paramMap.put("member_id", member_id);
		return new Gson().toJson(sql.update("setting.changeNickname", paramMap));
	}

	@RequestMapping("/deleteAccount")
	public String deleteAccount(MemberVO vo) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("member_id", vo.getMember_id());
		paramMap.put("member_pw", vo.getMember_pw());
		return new Gson().toJson(sql.delete("setting.deleteAccount", paramMap));
	}

	@RequestMapping("/insertPw")
	public String insertPw(String option_lock_pw, String member_id) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("option_lock_pw", option_lock_pw);
		paramMap.put("member_id", member_id);
		return new Gson().toJson(sql.insert("setting.insertPw", paramMap));
	}

	@RequestMapping("/inquirePw")
	public String inquirePw(String member_id) {
		OptionVO vo = sql.selectOne("setting.inquirePw", member_id);
		return new Gson().toJson(vo);
	}

	@RequestMapping("/insertPattern")
	public String insertPattern(String option_lock_pattern_pw, String member_id) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("option_lock_pattern_pw", option_lock_pattern_pw);
		paramMap.put("member_id", member_id);
		return new Gson().toJson(sql.insert("setting.insertPattern", paramMap));
	}

	@RequestMapping("/inquirePattern")
	public String inquirePattern(String member_id) {
		OptionVO vo = sql.selectOne("setting.inquirePattern", member_id);
		return new Gson().toJson(vo);
	}

	@RequestMapping("/deletePw")
	public String deletePw(String member_id) {
		return new Gson().toJson(sql.update("setting.deletePw", member_id));
	}

	@RequestMapping("/deletePattern")
	public String deletePattern(String member_id) {
		return new Gson().toJson(sql.update("setting.deletePattern", member_id));
	}

	@RequestMapping(value = "/checkAlarm", produces = "text/html;charset=utf-8")
	public String detail(String member_id) {
		String alarm = sql.selectOne("setting.checkAlarm", member_id);
		return alarm;
	}
	@RequestMapping("/updatePw")
	public String updatePw (String member_id) {
		return new Gson().toJson(sql.update("setting.updatePw",member_id));
	}
	@RequestMapping("/viewOption")
	public List<OptionVO> viewOption (String member_id) {
		List<OptionVO> option = sql.selectList("setting.viewOption", member_id);
		return option;
	}
	@RequestMapping("/updateAlarm")
	public List<OptionVO> updateAlarm (String member_id) {
		sql.update("setting.updateAlarm", member_id);
		List<OptionVO> option = sql.selectList("setting.viewOption", member_id);
		return option;
	}
	@RequestMapping("/updateFont")
	public String updateFont(OptionVO vo) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("member_id", vo.getMember_id());
		paramMap.put("option_font_size", vo.getOption_font_size());
		return new Gson().toJson(sql.update("setting.updateFont", paramMap));
	}
	@RequestMapping("/updateGodokAlarm")
	public List<OptionVO>  updateGodokAlarm(String member_id) {
		sql.update("setting.updateGodokAlarm", member_id);
		List<OptionVO> option = sql.selectList("setting.viewOption", member_id);
		return option;
	}
	
	//아이디로 관심사 정보 가져오기
	@RequestMapping("/favor")
	public String favor(String member_id) {
		List<FavorVO> list = sql.selectList("setting.favor", member_id);
		return new Gson().toJson(list);
	}
	
	//아이디에 해당하는 관심사 삭제
	@RequestMapping(value="/deleteFavor", produces = "text/html;charset=utf-8")
	public String deleteFavor(String member_id) {
		String result = sql.delete("setting.deleteFavor", member_id)>0 ? "성공" : "실패";
		return result;
	}

	
	

}
