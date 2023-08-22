package com.hanul.cloudWeb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

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

	@RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
	public String loginCheck(MemberVO vo, Model model, HttpSession session, HttpServletRequest request) {
		String returnUrl = "";

		if (session.getAttribute("id") != null) {
			session.removeAttribute("id");
		}
		int admin = sql.selectOne("login.loginCheck", vo);
		model.addAttribute("admin", admin);
		session.setAttribute("admin", admin);
		if (admin == 1) {
			String priorUrl = (String) session.getAttribute("url_prior_login");
			if (priorUrl != null) {
				returnUrl = "redirect:" +  priorUrl;
				session.removeAttribute("url_prior_login");
			} else {
				returnUrl = "redirect:/home";
			}
		} else {
			returnUrl = "redirect:default/login/loginCheck";
		}
		return returnUrl;
	}

}
