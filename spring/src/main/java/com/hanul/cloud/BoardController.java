package com.hanul.cloud;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController @RequestMapping("/board")
public class BoardController {
	@Autowired @Qualifier("project") SqlSession sql;
	
	@RequestMapping(value="/favorlist", produces = "text/html;charset=utf-8")
	public String favorlist() {
		return new Gson().toJson(sql.selectList("board.favorlist"));
	}

}
