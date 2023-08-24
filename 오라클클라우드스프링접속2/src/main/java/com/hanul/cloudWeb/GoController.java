package com.hanul.cloudWeb;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cloudWeb.Common.PageVO;
import cloudWeb.godok.CalendarEvents;
import cloudWeb.godok.GodokVO;

@Controller
@RequestMapping("/go")
public class GoController {
	@Autowired @Qualifier("project") SqlSession sql;
	private List<CalendarEvents> eventsList;

	@RequestMapping("/home")
	public String home() {
		
		return "go/home";
	}
	
	@RequestMapping("/list")
	public String list(PageVO page, Model model) {
		page.setTotalList(sql.selectOne("godok.totalViewGodokAlarmList", page));
		page.setList( sql.selectList("godok.viewGodokAlarmList", page));
		model.addAttribute("page", page);
		return "modal/go/list";
	}
	
	@RequestMapping("/addEvents")
	@ResponseBody
	public List<CalendarEvents> addEvents() {
		List<GodokVO> alarmList =  sql.selectList("godok.viewGodokAlarmList");
		eventsList = new ArrayList<>();
		for (GodokVO alarm : alarmList) {
		    CalendarEvents event = new CalendarEvents();
		    event.setId(String.valueOf(alarm.getAlarm_id())); // 예시, 이벤트 고유 ID
		    event.setTitle(alarm.getMember_name());
		    event.setStart(alarm.getAlarm_time()); // 예시, 이벤트 시작 시간
		    event.setColor("blue"); // 예시, 이벤트 색상
		    event.setTextColor("white"); // 예시, 텍스트 색상
		    eventsList.add(event);
		}
		return eventsList;
	}

}
