package com.hanul.cloud;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

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
	public String changeNickName(String member_nickname , String member_id) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("member_nickname", member_nickname);
		paramMap.put("member_id", member_id);
		return new Gson().toJson(sql.update("setting.changeNickname",paramMap));
	}
	
	@RequestMapping("/deleteAccount")
	public String deleteAccount(MemberVO vo) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("member_id", vo.getMember_id());
		paramMap.put("member_pw", vo.getMember_pw());
		return new Gson().toJson(sql.delete("setting.deleteAccount",paramMap));
	}
	
	@RequestMapping("/insertPw")
	public String insertPw(int option_lock_pw, String member_id) {
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

}
