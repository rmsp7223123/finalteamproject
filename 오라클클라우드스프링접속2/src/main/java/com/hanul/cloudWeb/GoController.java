package com.hanul.cloudWeb;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cloudWeb.Common.PageVO;
import cloudWeb.member.GodokVO;

@Controller
@RequestMapping("/go")
public class GoController {
	@Autowired @Qualifier("project") SqlSession sql;

	@RequestMapping("/home")
	public String home() {
		
		return "go/home";
	}
	
	@RequestMapping("/list")
	public String list(PageVO page, Model model) {
		page.setTotalList(sql.selectOne("godok.totalViewGodokAlarmList", page));
		page.setList( sql.selectList("godok.viewGodokAlarmList", page));
		model.addAttribute("page", page);
		return "go/list";
	}

	@RequestMapping("/events")
	@ResponseBody
	public String getEvents() {
		String events = "[{\"title\": \"Event 1\", \"start\": \"2023-08-01\"}, "
				+ "{\"title\": \"Event 2\", \"start\": \"2023-08-05\"}]";
		return events;
	}

}
