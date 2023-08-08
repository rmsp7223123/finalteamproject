package com.hanul.cloud;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import cloud.member.FavorBoardVO;

@RestController @RequestMapping("/board")
public class BoardController {
	@Autowired @Qualifier("project") SqlSession sql;

	
	@RequestMapping(value="/list", produces = "text/html;charset=utf-8")
	public String list(String favor) {
		List<FavorBoardVO> list = sql.selectList("board.list", favor);
		return new Gson().toJson(list);
	}
	
	@RequestMapping(value="/rec", produces = "text/html;charset=utf-8")
	public String rec(String id) {
		return sql.selectOne("board.rec", id)==null ? "0" : sql.selectOne("board.rec", id)+"";
	}
	
	@RequestMapping(value="/favor", produces = "text/html;charset=utf-8")
	public String favor(String favor) {
		return sql.selectOne("board.favor", favor)+"";
	}
	
	@RequestMapping(value="/insert", produces = "text/html;charset=utf-8")
	public String insert(FavorBoardVO vo) {
		return sql.insert("board.insert", vo)==1 ? "성공" : "실패";
	}
	


}
