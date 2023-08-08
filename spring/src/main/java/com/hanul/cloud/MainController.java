package com.hanul.cloud;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import cloud.member.MemberVO;

@RestController
@RequestMapping("main")
public class MainController {

	@Autowired
	@Qualifier("project")
	SqlSession sql;

	@RequestMapping("/test")
	public String profileImg(String member_id) {
		String vo = sql.selectOne("main.profileImg", member_id);
		return new Gson().toJson(vo);
	}
}
