package com.hanul.cloud;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;

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

import cloud.member.EphoneVO;
import cloud.member.FavorVO;
import cloud.member.MemberVO;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@RestController @RequestMapping("/login")
public class LoginController {
	
	@Autowired @Qualifier("project") SqlSession sql;

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
		
		final String APIKEY = "NCSIGGEIAAPYSXHO";
		final String APISECRET = "Q3WAV69X3RISPNBEJLIQ2YN8OCCHGOAY";
		
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
	
	@RequestMapping(value="/checkPhone", produces = "text/html;charset=utf-8")
	public String checkPhone(String phoneNumber) {
		return new Gson().toJson(sql.selectOne("login.checkPhone", phoneNumber));
	}
	

	@RequestMapping(value="/checkId", produces = "text/html;charset=utf-8")
	public String checkId(String member_id) {
		return new Gson().toJson(sql.selectOne("login.checkId", member_id));
	}
	
	@RequestMapping(value="/checkNickname", produces = "text/html;charset=utf-8")
	public String checkNickname(String nickname) {
		return new Gson().toJson(sql.selectOne("login.checkNickname", nickname));
	}
	
	@RequestMapping(value="/file", produces = "text/html;charset=utf-8" )
	public String list(HttpServletRequest req) throws IllegalStateException, IOException { //req(요청에 대한 모든정보), res
		System.out.println(req.getLocalAddr());
		System.out.println(req.getLocalPort());
		System.out.println(req.getContextPath()+"/img/이름.jpg"); //DB에 저장
		String id =  req.getParameter("member_id");
		MultipartRequest mReq = (MultipartRequest) req; //file정보가 없는 req => 있는 mReq
		MultipartFile file = mReq.getFile("file");
		//파일이 있는 상태의 요청을 받았는지에 따라서 유동적으로 MultipartRequest로 캐스팅
		if(file!=null) {
			String fullPath = req.getRealPath("/");
			String workSpace = fullPath.substring(0, fullPath.indexOf(".metadata"));
			String projectName = fullPath.substring(fullPath.indexOf("wtpwebapps")+"wtpwebapps".length(), fullPath.length());
			String fileName = id+".jpg";
			Path filePath = Paths.get(workSpace, projectName , "src", "main", "resources", "images", "profileImage");
//			File f = filePath.toFile();
			File f = new File(filePath.toString());
			File file1 = null;
			if(f.exists()) {
				System.out.println("tr");
				file.transferTo(new File(filePath.toString(), fileName));
				file1 = new File(filePath.toString()+"/"+fileName);
			}else {
				f.mkdir();
				System.out.println("fr");
				file.transferTo(new File(filePath.toString(), fileName));
				file1 = new File(filePath.toString()+"/"+fileName);
			}
			if(file1.exists()) {
				HashMap<Object, Object> map = new HashMap<Object, Object>();
				map.put("member_profileimg", filePath+"/"+fileName);
				map.put("member_id", id);
				return sql.update("login.file", map)==1 ? "성공" : "실패";
			}else {
				return "실패";
			}
		}else {
			return "실패";
		}
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
	
	
}
