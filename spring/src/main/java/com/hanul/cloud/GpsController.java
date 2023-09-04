package com.hanul.cloud;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import cloud.gps.GpsDAO;
import cloud.gps.GpsVO;

@RestController
@RequestMapping("/gps")
public class GpsController {
	@Autowired
	private GpsDAO dao;
	@Autowired @Qualifier("project") SqlSession sql;

	@RequestMapping(value = "/senior", produces = "text/html;charset=utf-8")
	public String senior_list(String senior_latitude, String senior_longitude, String zoom_level) {
		List<GpsVO> list = dao.senior_list(senior_latitude, senior_longitude, zoom_level);
		return new Gson().toJson(list);
	}
	
	@RequestMapping(value = "/detail", produces = "text/html;charset=utf-8")
	public String senior_detail(int key) {
		List<GpsVO> list = dao.senior_detail(key);
		return new Gson().toJson(list);
	}
	
	//좋아요 개수 조회
	@RequestMapping(value = "/like_count", produces = "text/html;charset=utf-8")
	public String like_count(int key) {
		
		return sql.selectOne("gps.like_count",key);
	}

	@RequestMapping(value = "/likelist", produces = "text/html;charset=utf-8")
	public String senior_like(String member_id) {
		List<GpsVO> list = dao.senior_like(member_id);
		return new Gson().toJson(list);
	}

	@RequestMapping("/likebtn")
	public void likebtn(int key, String member_id) {
		dao.bmark(key, member_id);
		dao.likebtn(key);
	}

	@RequestMapping("/unlikebtn")
	public void unlikebtn(int key, String member_id) {
		dao.delbmark(key, member_id);
		dao.unlikecnt(key);
	}

	@RequestMapping("/likeyet")
	public String likeyet(int key, String member_id) {
		return dao.likeyet(key, member_id);
	}
	
	@RequestMapping("/search")
	public List<GpsVO> search_result(String keyword) {
		return dao.search_result(keyword);
	}
	
	@RequestMapping("/location")
	public void location(String senior_latitude, String senior_longitude, String member_id) {
		try {
			dao.location(senior_latitude, senior_longitude, member_id);
		} catch (Exception e) {
		}
		
		dao.locationupdate(senior_latitude, senior_longitude, member_id);
	}
	
	
}
