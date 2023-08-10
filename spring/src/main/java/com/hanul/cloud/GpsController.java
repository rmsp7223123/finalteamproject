package com.hanul.cloud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	@RequestMapping(value = "/senior", produces = "text/html;charset=utf-8")
	public String senior_list(GpsVO vo) {
		List<GpsVO> list = dao.senior_list(vo);
		return new Gson().toJson(list);
	}

	@RequestMapping(value = "/likelist", produces = "text/html;charset=utf-8")
	public String senior_like() {
		List<GpsVO> list = dao.senior_like();
		return new Gson().toJson(list);
	}

	@RequestMapping("/likebtn")
	public void likebtn(int key) {
		dao.bmark(key);
		try {
			dao.addlike(key);
		} catch (Exception e) {
			
		}
		dao.likecnt(key);
	}

	@RequestMapping("/unlikebtn")
	public void unlikebtn(int key) {
		dao.delbmark(key);
		dao.unlikecnt(key);
	}

	@RequestMapping("/likeyet")
	public String likeyet(int key) {
		return dao.likeyet(key);
	}
	
}
