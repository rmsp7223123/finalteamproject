package com.hanul.cloud;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.google.gson.Gson;

import cloud.member.CheckVO;
import cloud.member.EphoneVO;
import cloud.member.FavorVO;
import cloud.member.MemberVO;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@RestController @RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private cloud.common.CommonUtility common;
	
	@Autowired @Qualifier("project") SqlSession sql;
	

	final String APIKEY = "NCSIGGEIAAPYSXHO";
	final String APISECRET = "Q3WAV69X3RISPNBEJLIQ2YN8OCCHGOAY";

	@RequestMapping(value="/sendSms", produces = "text/html;charset=utf-8")
	public String sendSms(String phoneNumber) {
		
		Random random = new Random();		//랜덤 함수 선언
		int createNum = 0;  			//1자리 난수
		String ranNum = ""; 			//1자리 난수 형변환 변수
		String resultNum = "";  		//결과 난수
		
		for (int i=0; i<6; i++) { 
			createNum = random.nextInt(9);		//0부터 9까지 올 수 있는 1자리 난수 생성
			ranNum =  Integer.toString(createNum);  //1자리 난수를 String으로 형변환
			resultNum += ranNum;			//생성된 난수(문자열)을 원하는 수(letter)만큼 더하며 나열
		}
		
		Message sms = new Message(APIKEY, APISECRET);
		
		HashMap<String, String> params = new HashMap();
		params.put("to", phoneNumber);
		params.put("from", "01096024788");
		params.put("type", "SMS"); //SMS, LMS, MMS ...
		params.put("text", "할만다 인증번호\n["+resultNum+"]");
		params.put("app_version", "JAVA SDK v1.2");
		
		try {
			JSONObject obj = sms.send(params);
			System.out.println(obj.toString());
			String result = obj.toString().contains("\"success_count\":1")==true ? resultNum : "실패";
			return result;
//			return resultNum;
		} catch (Exception e) {
			e.printStackTrace();
			return "실패";
		}
	}
	
	// sms id 전송
	@RequestMapping(value="/sendId", produces = "text/html;charset=utf-8")
	public String sendId(String phoneNumber, String id) {
		Message sms = new Message(APIKEY, APISECRET);
		HashMap<String, String> params = new HashMap();
		params.put("to", phoneNumber);
		params.put("from", "01096024788");
		params.put("type", "SMS"); //SMS, LMS, MMS ...
		params.put("text", "할만다 아이디\n["+id+"]");
		params.put("app_version", "JAVA SDK v1.2");
		
		try {
			JSONObject obj = sms.send(params);
			System.out.println(obj.toString());
			String result = obj.toString().contains("\"success_count\":1")==true ? "성공" : "실패";
			return result;
//			return "성공";
		} catch (Exception e) {
			e.printStackTrace();
			return "실패";
		}
	}
	// sms pw 전송
	@RequestMapping(value="/sendPw", produces = "text/html;charset=utf-8")
	public String sendPw(String phoneNumber, String pw) {
		Message sms = new Message(APIKEY, APISECRET);
		HashMap<String, String> params = new HashMap();
		params.put("to", phoneNumber);
		params.put("from", "01096024788");
		params.put("type", "SMS"); //SMS, LMS, MMS ...
		params.put("text", "할만다 비밀번호\n["+pw+"]");
		params.put("app_version", "JAVA SDK v1.2");
		
		try {
			JSONObject obj = sms.send(params);
			System.out.println(obj.toString());
			String result = obj.toString().contains("\"success_count\":1")==true ? "성공" : "실패";
			return result;
//			return "성공";
		} catch (Exception e) {
			e.printStackTrace();
			return "실패";
		}
	}
	
	@RequestMapping(value="/checkPhone", produces = "text/html;charset=utf-8")
	public String checkPhone(String phoneNumber) {
		MemberVO vo = sql.selectOne("login.checkPhone", phoneNumber);
		return new Gson().toJson(vo);
	}
		
	

	@RequestMapping(value="/checkId", produces = "text/html;charset=utf-8")
	public String checkId(String member_id) {
		MemberVO vo = sql.selectOne("login.checkId", member_id);
		return new Gson().toJson(vo);
	}
	
	@RequestMapping(value="/checkNickname", produces = "text/html;charset=utf-8")
	public String checkNickname(String nickname) {
		MemberVO vo = sql.selectOne("login.checkNickname", nickname);
		return new Gson().toJson(vo);
	}
	
	@RequestMapping(value="/file", produces = "text/html;charset=utf-8" )
	public String list(HttpServletRequest req) { //req(요청에 대한 모든정보), res
//		System.out.println(req.getLocalAddr());
//		System.out.println(req.getLocalPort());
//		System.out.println(req.getContextPath()+"/img/이름.jpg"); //DB에 저장
		String id =  req.getParameter("member_id");
		MultipartRequest mReq = (MultipartRequest) req; //file정보가 없는 req => 있는 mReq
		MultipartFile file = mReq.getFile("file");
		String newImagePath = common.fileUpload("profileImage", file, req);
		HashMap<String, Object>paramMap = new HashMap<String, Object>();
		paramMap.put("member_id", id);
		paramMap.put("member_profileimg", newImagePath);
		return sql.insert("login.file",paramMap) ==1 ? "성공" : "실패";
		
		
		//파일이 있는 상태의 요청을 받았는지에 따라서 유동적으로 MultipartRequest로 캐스팅
//		if(file!=null) {
//			String fullPath = req.getRealPath("/");
//			String workSpace = fullPath.substring(0, fullPath.indexOf(".metadata"));
//			String projectName = fullPath.substring(fullPath.indexOf("wtpwebapps")+"wtpwebapps".length(), fullPath.length());
//			String fileName = id+".jpg";
//			Path filePath = Paths.get(workSpace, projectName , "src", "main", "resources", "images", "profileImage");
////			File f = filePath.toFile();
//			File f = new File(filePath.toString());
//			File file1 = null;
//			if(f.exists()) {
//				System.out.println("tr");
//				file.transferTo(new File(filePath.toString(), fileName));
//				file1 = new File(filePath.toString()+"/"+fileName);
//			}else {
//				f.mkdir();
//				System.out.println("fr");
//				file.transferTo(new File(filePath.toString(), fileName));
//				file1 = new File(filePath.toString()+"/"+fileName);
//			}
//			if(file1.exists()) {
//				HashMap<Object, Object> map = new HashMap<Object, Object>();
//				map.put("member_profileimg", filePath+"/"+fileName);
//				map.put("member_id", id);
//				return sql.update("login.file", map)==1 ? "성공" : "실패";
//			}else {
//				return "실패";
//			}
//		}else {
//			return "실패";
//		}
	}
	
	@RequestMapping(value="/check", produces = "text/html;charset=utf-8")
	public String check(MemberVO vo) {
		MemberVO result = sql.selectOne("login.check", vo);
		return new Gson().toJson(result = sql.selectOne("login.check", vo)); 
	}
	
	@RequestMapping(value="/join", produces = "text/html;charset=utf-8")
	public String join(MemberVO vo) {
		return sql.insert("login.join", vo)==1 ? "성공" : "실패";
	}
	
	@RequestMapping(value="/favor", produces = "text/html;charset=utf-8")
	public String favor(FavorVO vo) {
		return sql.insert("login.favor", vo)==1 ? "성공" : "실패";
	}
	
	@RequestMapping(value="/godok", produces = "text/html;charset=utf-8")
	public String godok(EphoneVO vo) {
		return sql.insert("login.godok", vo)==1 ? "성공" : "실패";
	}
	
	//전화번호로 아이디 찾기
	@RequestMapping(value="/findId", produces = "text/html;charset=utf-8")
	public String findId(String phoneNumber) {
		String result = sql.selectOne("login.findId", phoneNumber);
		return result;
	}
	
	
	//전화번호, 아이디로 비밀번호 받아오기
	@RequestMapping(value="/findPw", produces = "text/html;charset=utf-8")
	public String findPw(String phoneNumber, String id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("phoneNumber", phoneNumber);
		map.put("id", id);
		String result = sql.selectOne("login.findPw", map);
		return result;
	}
	
	//임시 비밀번호 발급
	@RequestMapping(value="/modifyPw", produces = "text/html;charset=utf-8")
	public String modifyPw(String id) {
		
        String pw = UUID.randomUUID().toString();
        pw = pw.substring( pw.lastIndexOf("-")+1 );
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("pw", pw);
		map.put("id", id);
		return sql.update("login.modifyPw", map)==1 ? pw : null;
	}
	
	//설정에서 비밀번호 변경
	@RequestMapping(value="/settingPw", produces = "text/html;charset=utf-8")
	public String settingPw(String id, String pw) {		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("pw", pw);
		map.put("id", id);
		return sql.update("login.modifyPw", map)==1 ? "성공" : "실패";
	}
	
	//아이디로 프로필사진, 관심사, 보호자정보 유무 확인
	@RequestMapping(value="/checking", produces = "text/html;charset=utf-8")
	public String checking(String member_id) {	
		CheckVO vo = sql.selectOne("login.checking", member_id);
		System.out.println(vo.getFavor());
		return new Gson().toJson(vo);
	}
	
	
	
	
}
