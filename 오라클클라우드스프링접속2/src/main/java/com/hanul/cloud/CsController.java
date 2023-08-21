package com.hanul.cloud;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cs")
public class CsController {
	
	@RequestMapping("/home")
	public String home(HttpSession session) {
		session.setAttribute("category", "cs");
		return "cs/home";
	}

}
