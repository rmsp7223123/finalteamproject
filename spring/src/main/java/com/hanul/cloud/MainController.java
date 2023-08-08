package com.hanul.cloud;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

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
	public String changeProfile(MultipartFile file, HttpServletRequest req, String member_id) {
		// 파일저장, 원본파일 삭제, 새로운 파일경로 DB에 업로드
		common.uploadAndDeletePreviousImage("profileImg", file, req, sql.selectOne("main.previusImg", member_id));

		// System.out.println(req.getLocalAddr() + ":" + req.getContextPath() + "/imgs/"
		// + modulePath);
		// DB에 저장
		return "";
	}
}
