package com.hanul.cloud;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import cloud.member.EphoneVO;
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
}
