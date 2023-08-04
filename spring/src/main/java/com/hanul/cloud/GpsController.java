package com.hanul.cloud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cloud.gps.GpsDAO;
import cloud.gps.GpsVO;

@RestController @RequestMapping("/gps")
public class GpsController {
@Autowired GpsDAO dao;

	@RequestMapping("/senior")
	public void senior_list() {
		List<GpsVO> list = dao.senior_list();
	}
}
