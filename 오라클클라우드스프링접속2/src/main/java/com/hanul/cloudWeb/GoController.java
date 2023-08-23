package com.hanul.cloudWeb;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cloudWeb.member.LocationVO;

@Controller
@RequestMapping("/go")
public class GoController {
	@Autowired @Qualifier("project") SqlSession sql;

	@RequestMapping("/home")
	public String home() {
		
		return "go/home";
	}
	
	@RequestMapping("list")
	public String list() {
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
