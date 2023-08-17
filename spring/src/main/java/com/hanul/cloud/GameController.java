package com.hanul.cloud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import cloud.gps.GpsDAO;
import cloud.gps.GpsVO;

@RestController
@RequestMapping("/game")
public class GameController {
//	@Autowired
//	private GameDAO dao;

//	@RequestMapping(value = "/senior", produces = "text/html;charset=utf-8")
//	public String senior_list(String senior_latitude, String senior_longitude, String zoom_level) {
//		List<GpsVO> list = dao.senior_list(senior_latitude, senior_longitude, zoom_level);
//		return new Gson().toJson(list);
//	}
//
//	@RequestMapping(value = "/likelist", produces = "text/html;charset=utf-8")
//	public String senior_like(String member_id) {
//		List<GpsVO> list = dao.senior_like(member_id);
//		return new Gson().toJson(list);
//	}
//
//	@RequestMapping("/likebtn")
//	public void likebtn(int key, String member_id) {
//		dao.bmark(key, member_id);
//		try {
//			dao.addlike(key);
//		} catch (Exception e) {
//			
//		}
//		dao.likecnt(key);
//	}
//
//	@RequestMapping("/unlikebtn")
//	public void unlikebtn(int key, String member_id) {
//		dao.delbmark(key, member_id);
//		dao.unlikecnt(key);
//	}
//
//	@RequestMapping("/likeyet")
//	public String likeyet(int key, String member_id) {
//		return dao.likeyet(key, member_id);
//	}
//	
//	@RequestMapping("/search")
//	public List<GpsVO> search_result(String keyword) {
//		return dao.search_result(keyword);
//	}
}
