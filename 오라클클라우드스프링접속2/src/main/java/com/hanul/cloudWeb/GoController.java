package com.hanul.cloudWeb;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/go")
public class GoController {
	
	@RequestMapping("/home")
	public String home(HttpSession session, Model model, HttpServletRequest request, LocalDateTime localDateTime) {
		session.setAttribute("category", "go");
		return "go/home";
	}

}
