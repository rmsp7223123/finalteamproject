package com.hanul.cloudWeb;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cloudWeb.member.MemberVO;

@Controller
public class HomeController {

	@Autowired
	@Qualifier("project")
	SqlSession sql;

	@RequestMapping({ "/home" })
	public String home(HttpSession session) {
		session.setAttribute("category", null);
		return "home";
	}

	@RequestMapping("/")
	public String login() {
		return "default/login/login";
	}

	@RequestMapping(value = "/loginCheck")
	public String loginCheck(MemberVO vo, Model model) {
		int admin = sql.selectOne("login.loginCheck", vo);
		model.addAttribute("admin", admin);
		return "default/login/loginCheck";
	}
	
	

}
