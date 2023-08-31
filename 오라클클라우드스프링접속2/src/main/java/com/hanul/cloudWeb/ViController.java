package com.hanul.cloudWeb;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import cloudWeb.visual.MemberVO;
import cloudWeb.visual.SeniorVO;


@Controller
@RequestMapping("/vi")
public class ViController {
	@Autowired @Qualifier("project") SqlSession sql;
	
	@RequestMapping("/member")
	public String home(HttpSession session) {
		session.setAttribute("category", "vi");
		return "vi/member";
	}
	
	@ResponseBody
	@RequestMapping("/genderchart")
	public String genderchart() {
		List<MemberVO> result = sql.selectList("vi.gender");
	    return new Gson().toJson(result);
	}
	
	@ResponseBody
	@RequestMapping("/agechart")
	public String agechart() {
		List<MemberVO> result = sql.selectList("vi.age");
	    return new Gson().toJson(result);
	}
		
	
	
	@RequestMapping("/senior")
	public String senior(HttpSession session) {
		session.setAttribute("category", "vi");
		return "vi/senior";
	}
	
	@ResponseBody
	@RequestMapping("/pop")
	public String pop() {
		List<MemberVO> result = sql.selectList("vi.pop");
	    return new Gson().toJson(result);
	}
	
	@ResponseBody
	@RequestMapping("/region")
	public String region() {
		List<MemberVO> result = sql.selectList("vi.region");
	    return new Gson().toJson(result);
	}
	
	@ResponseBody
	@RequestMapping("/count")
	public String count() {
		List<SeniorVO> result = sql.selectList("vi.count");
	    return new Gson().toJson(result);
	}
	
	
	
//	@RequestMapping("/geo")
//	public String geo(HttpSession session) {
//		session.setAttribute("category", "vi");
//		return "vi/geo";
//	}
	
	@RequestMapping("/geo")
	public String geo() {
		return "vi/geo";
	}

}
