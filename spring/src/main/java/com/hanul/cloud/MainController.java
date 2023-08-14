package com.hanul.cloud;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.google.gson.Gson;

import cloud.gps.GpsVO;
import cloud.member.FavorVO;
import cloud.member.MemberVO;

@RestController
@RequestMapping("main")
public class MainController {

	@Autowired
	private cloud.common.CommonUtility common;

	@Autowired
	@Qualifier("project")
	SqlSession sql;

	@RequestMapping("/ttt")
	public String ttt() {
		String orgData = "http://192.168.0.87:8080/cloud/imgs/profileImage/ansqudwns12.jpg";
		String newData = orgData.replaceAll("http://192.168.0.87:8080/cloud/imgs/", "");
		System.out.println(common.rootPath + newData);
		File f = new File(common.rootPath + newData);
		if (f.exists()) {
			if (f.delete()) {
				System.out.println("삭제 성공");
			} else {
				System.out.println("?");
			}
		}
		/// profileImage/ansqudwns12.jpg
		return "";
	}

	@RequestMapping("/changeProfile")
	public String changeProfile(HttpServletRequest req) {
		MemberVO vo = new Gson().fromJson(req.getParameter("dto"), MemberVO.class);
		MultipartFile file = ((MultipartRequest) req).getFile("file");
		// 파일저장, 원본파일 삭제, 새로운 파일경로 DB에 업로드
		String newImagePath = common.uploadAndDeletePreviousImage("profileImage", file, req, vo.getMember_profileimg());

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("member_profileimg", newImagePath);
		paramMap.put("member_id", vo.getMember_id());
		sql.update("main.changeProfile", paramMap);

		// System.out.println(req.getLocalAddr() + ":" + req.getContextPath() + "/imgs/"
		// + modulePath);
		// DB에 저장
		MemberVO vo2 = sql.selectOne("main.check", vo.getMember_id());
		return new Gson().toJson(vo2);
	}

	@RequestMapping("/viewpager")
	public List<MemberVO> viewpager(String member_id) {
		List<MemberVO> vo = sql.selectList("main.viewpager", member_id);
		return vo;
	}
	
	@RequestMapping("/favor")
	public List<FavorVO> favor(String member_id) {
		List<FavorVO> vo = sql.selectList("main.favor",member_id);
		return vo;
	}
	
	@RequestMapping("addFriend")
	public String addFriend(String member_id) {
		// 알림으로 보내고 상대방이 확인눌렀을 때 
		return new Gson().toJson(sql.insert("member_id", member_id));
	}
	
	@RequestMapping("addFriendAlarm")
	public String addFriendAlarm(String member_id) {
		return "";
	}
}
