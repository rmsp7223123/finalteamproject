package com.hanul.cloud;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.gson.Gson;

import cloud.gps.GpsVO;
import cloud.member.AlarmVO;
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

	@RequestMapping(value = "/changeProfile", produces = "text/html;charset=utf-8")
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
		List<FavorVO> vo = sql.selectList("main.favor", member_id);
		return vo;
	}

	@RequestMapping("/addFriend")
	public String addFriend(String member_id, String friend_id) {
		// 알림으로 보내고 상대방이 확인눌렀을 때
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("member_id", member_id);
		paramMap.put("friend_id", friend_id);
		return new Gson().toJson(sql.insert("main.addFriend", paramMap));
	}

//	@RequestMapping("/addAlarm")
//	public String addAlarm(MemberVO vo1, AlarmVO vo2) throws FileNotFoundException, IOException {
//		FileInputStream in = new FileInputStream("D:\\Service.json");
//
//		Message message = Message.builder().setToken(
//				"ecyZAdhBRX2cFVXlw-Pq_-:APA91bFAvfxhMIIDqrLY4dofOK5D1Ahz4-ZQjWrO5YK3Ix7MXdio7vc02fJA6_bfTktltesIm8RBrqFwbWzVsBxrrU0_N9GTzvOWquG_u2oraMunORTb7qQi_Yc_KMREYuQt1pEkm4FU")
//				.setNotification(Notification.builder().setTitle("친구추가")
//						.setBody(vo1.getMember_nickname() + "님이 친구신청을 보냈습니다.").build())
//				.build();
//		try {
////			FirebaseApp app = FirebaseApp.initializeApp(new FirebaseOptions.Builder()
////					.setCredentials(GoogleCredentials.fromStream(new FileInputStream(null)))
////					.setProjectId("halmanda-5342f")
//////					.setServiceAccountId("AIzaSyCdpqv25vqsCDAypMIg0FRNBi-76U_s92w")
////					.build());
//			FirebaseOptions options = new FirebaseOptions.Builder()
//					.setCredentials(
//							GoogleCredentials.fromStream(new FileInputStream("‪D:\\finalteamproject\\Service.json")))
//					.setDatabaseUrl("https://halmanda-5342f-default-rtdb.firebaseio.com")
////					.setProjectId("halmanda-5342f")
//					.build();
//			FirebaseApp.initializeApp(options);
//			FirebaseMessaging.getInstance().send(message);
//		} catch (FirebaseMessagingException e) {
//			e.printStackTrace();
//		}
//		// return new Gson().toJson(sql.insert("main.addAlarm", paramMap));
//
//		return "";
//	}

	@RequestMapping("/viewAlarm")
	public List<AlarmVO> viewAlarm(String receive_id) {
		List<AlarmVO> list = sql.selectList("main.viewAlarm", receive_id);
		return list;
	}

	@RequestMapping("/deleteAlarm")
	public String deleteAlarm(String receive_id) {
		return new Gson().toJson(sql.delete("main.deleteAlarm", receive_id));
	}

	@RequestMapping(value = "/addAlarm")
	public String send2(MemberVO vo1, AlarmVO vo2) {
		vo1 = sql.selectOne("main.detail", vo1.getMember_id());
		MemberVO vo3 = sql.selectOne("main.detail", vo2.getReceive_id());
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("member_id", vo1.getMember_id());
		paramMap.put("alarm_content", vo2.getAlarm_content());
		paramMap.put("alarm_time", vo2.getAlarm_time());
		paramMap.put("receive_id", vo2.getReceive_id());
		try {
			FileInputStream refreshToken = new FileInputStream("D:\\Service.json");
			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(refreshToken)).build();

			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
			}

			// 메세지 작성
			Notification noti = Notification.builder().setTitle("친구추가").setBody(vo1.getMember_nickname()+"님이 친구신청을 보냈습니다.").build();
			Message msg = Message.builder().putData("title", "friend").putData("name", "aaa").putData("body", "aaa").putData("check", "addFriend")
					.putData("color", "#f45342").setNotification(noti).setToken(vo3.getMember_phone_id()).build();

			// 메세지를 FirebaseMessaging에 보내기
			String response = FirebaseMessaging.getInstance().send(msg);
			// 결과출력
			System.out.println("Successfully: " + response);

		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
		return new Gson().toJson(sql.insert("main.addAlarm", paramMap));
	}
	
	@RequestMapping("/deleteOneAlarm")
	public String deleteOneAlarm(int alarm_id) {
		return new Gson().toJson(sql.delete("main.deleteOneAlarm", alarm_id));
	}
	
	@RequestMapping(value = "/viewFriendList" , produces = "text/html;charset=utf-8")
	public String viewFriendList(String member_id) {
		return new Gson().toJson(sql.selectList("main.viewFriendList", member_id));
	}
	
	
//	@RequestMapping("MemberDetailList")
}
