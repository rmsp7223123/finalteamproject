package com.hanul.cloud;

import java.util.HashMap;
import java.util.Random;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import cloud.member.MemberVO;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@RestController @RequestMapping("/login")
public class LoginController {
	
	@Autowired @Qualifier("project") SqlSession sql;

	@RequestMapping(value="/sendSms", produces = "text/html;charset=utf-8")
	public String checkEmail(String phoneNumber) {
		
		Random random = new Random();		//랜덤 함수 선언
		int createNum = 0;  			//1자리 난수
		String ranNum = ""; 			//1자리 난수 형변환 변수
		String resultNum = "";  		//결과 난수
		
		for (int i=0; i<6; i++) { 
			createNum = random.nextInt(9);		//0부터 9까지 올 수 있는 1자리 난수 생성
			ranNum =  Integer.toString(createNum);  //1자리 난수를 String으로 형변환
			resultNum += ranNum;			//생성된 난수(문자열)을 원하는 수(letter)만큼 더하며 나열
		}	
		
//		final String APIKEY = "NCSIGGEIAAPYSXHO";
//		final String APISECRET = "Q3WAV69X3RISPNBEJLIQ2YN8OCCHGOAY";
//		
//		Message sms = new Message(APIKEY, APISECRET);
//		
//		HashMap<String, String> params = new HashMap();
//		params.put("to", phoneNumber);
//		params.put("from", "01096024788");
//		params.put("type", "SMS"); //SMS, LMS, MMS ...
//		params.put("text", "할만다 인증번호\n["+resultNum+"]");
//		params.put("app_version", "JAVA SDK v1.2");
		
		try {
//			JSONObject obj = sms.send(params);
//			System.out.println(obj.toString());
//			String result = obj.toString().contains("\"success_count\":1")==true ? resultNum : "실패";
			return resultNum;
		} catch (Exception e) {
			e.printStackTrace();
			return "실패";
		}
	}
	
	@RequestMapping(value="/checkPhone", produces = "text/html;charset=utf-8")
	public String checkPhone(String phoneNumber) {
		MemberVO info = sql.selectOne("login.checkPhone", phoneNumber);
		return info!=null ? new Gson().toJson(info) : "없음";
	}
	
	
}
