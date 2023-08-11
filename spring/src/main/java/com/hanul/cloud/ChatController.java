package com.hanul.cloud;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

@RestController
public class ChatController {
	
	@Autowired
	private cloud.common.CommonUtility common;

	@Autowired
	@Qualifier("project")
	SqlSession sql;
	
	
	@RequestMapping("/changeImage")
	public void changeImage(HttpServletRequest req) {
		MultipartFile file = ((MultipartRequest) req).getFile("file");
		common.fileUpload("chatImage", file, req);
	}
}
