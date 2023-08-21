package com.hanul.cloudWeb;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cu")
public class CuController {
	
	@RequestMapping("/home")
	public String home(HttpSession session) {
		session.setAttribute("category", "cu");
		return "cu/home";
	}

}
