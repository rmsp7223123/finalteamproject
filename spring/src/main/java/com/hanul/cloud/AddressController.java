package com.hanul.cloud;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AddressController {
	
	
//	@Autowired @Qualifier("project") SqlSession sql;
	
	//주소 api 호출
	@RequestMapping(value="/address", produces = "text/html;charset=utf-8")
	public String home() {
		return "/home";
	}
	
	
}
