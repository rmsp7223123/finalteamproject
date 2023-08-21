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
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cloudWeb.member.MemberVO;

@Controller
public class HomeController implements HandlerInterceptor{

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

	
	
	
	
	
	
	
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
