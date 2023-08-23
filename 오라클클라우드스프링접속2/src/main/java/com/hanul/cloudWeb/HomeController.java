package com.hanul.cloudWeb;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cloudWeb.member.MemberVO;

@Controller
public class HomeController implements HandlerInterceptor {

	@Autowired
	@Qualifier("project")
	SqlSession sql;

	@RequestMapping({ "/home" })
	public String home() {
		return "index";
	}

	@RequestMapping("/")
	public String login() {
		return "default/login/login";
	}

	@RequestMapping(value = "/loginCheck")
	@ResponseBody
	public int loginCheck(MemberVO vo, Model model, HttpSession session) {
		String returnUrl = "";
		if (session.getAttribute("admin") != null) {
			session.removeAttribute("admin");
		}
		int admin = sql.selectOne("login.loginCheck", vo);
		model.addAttribute("admin", admin);
		if (admin == 1) { // 로그인 성공
			session.setAttribute("admin", admin);
			returnUrl = "redirect:/";
		} else { // 로그인 실패
			returnUrl = "redirect:/";
		}
		return admin;
//		return "redirect:/default/login/loginCheck";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest req) {
		return "default/error/error";
	}

}
