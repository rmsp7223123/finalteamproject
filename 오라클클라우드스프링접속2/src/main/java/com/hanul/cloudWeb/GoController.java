package com.hanul.cloudWeb;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/go")
public class GoController {

	@RequestMapping("/home")
	public String home(HttpSession session, Model model, HttpServletRequest request, LocalDateTime localDateTime) {
		session.setAttribute("category", "go");
		return "go/home";
	}

	@RequestMapping("/events")
	@ResponseBody
	public String getEvents() {
		String events = "[{\"title\": \"Event 1\", \"start\": \"2023-08-01\"}, "
				+ "{\"title\": \"Event 2\", \"start\": \"2023-08-05\"}]";
		return events;
	}

}
