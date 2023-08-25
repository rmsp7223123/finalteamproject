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
	@RequestMapping("/memchart")
	public String memchart() {
		List<MemberVO> result = sql.selectList("vi.gender");
	    return new Gson().toJson(result);
	}
		

}
