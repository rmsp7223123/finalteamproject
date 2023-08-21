package com.hanul.cloud;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vi")
public class ViController {
	
	@RequestMapping("/home")
	public String home(HttpSession session) {
		session.setAttribute("category", "vi");
		return "vi/home";
	}

}
