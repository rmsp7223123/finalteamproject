package com.hanul.cloudWeb;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cloudWeb.Common.PageVO;
import cloudWeb.member.MemberVO;

@Controller
@RequestMapping("/cu")
public class CuController {
	@Autowired @Qualifier("project") SqlSession sql;
	
	@RequestMapping("/list")
	public String home(HttpSession session, Model model, PageVO page) {
		session.setAttribute("category", "cu");
		
//		model.addAttribute("list", sql.selectList("member.list"));
		

		//총 글의 건수 조회
		page.setTotalList(sql.selectOne("member.totalList", page));
		page.setList( sql.selectList("member.list", page));
		model.addAttribute("page", page);
		return "cu/list";
	}
	
	// 사원정보화면 요청
	@RequestMapping("/info")
	public String info(String id, Model model, PageVO page) {
		// 비지니스로직 : DB에서 해당 사원정보를 조회해와
		// 			  화면에 출력할 수 있도록 Model객체에 담는다
		model.addAttribute("vo", sql.selectOne("member.info", id));
		model.addAttribute("page", page);
		// 프리젠테이션로직 : 응답화면- 정보화면
		return "cu/info";
	}
	
	//공지글정보 삭제처리 요청
	@RequestMapping("/delete")
	public String delete(String id, HttpServletRequest request, PageVO page) throws Exception {
		sql.delete("member.delete", id);
		return "redirect:list?"
				+"curPage="+page.getCurPage()
				+"&search="+page.getSearch()
				+"&keyword="+URLEncoder.encode(page.getKeyword(), "utf-8");
	}
	
	
	//공지글정보 수정 화면 요청
	@RequestMapping("/modify")
	public String modify(String id, Model model, PageVO page) {
		//해당 글읠 정보를 DB에서 조회해와 수정화면에 출력할 수 있도록 Model에 담는다
		model.addAttribute("vo", sql.selectOne("member.info", id));
		model.addAttribute("page", page);
		return "cu/modify";
	}
	
	//공지글정보 수정 저장처리 요청
	@RequestMapping("/update")
	public String update(MemberVO vo, PageVO page, MultipartFile file, HttpServletRequest request) throws Exception {
		//원래 첨부되어져 있던 파일정보를 조회해둔다
//		NoticeVO before = service.notice_info(vo.getId());
		
//		
//		if(file.isEmpty()) {
//			//첨부파일이 없는 경우 : 원래O -> 삭제, 원래O,X ->그대로
//			//원래O 그대로 두는 경우 -> 파일명 이전정보로 담는다
//			if(!vo.getFilename().isEmpty()) {
//				vo.setFilename(before.getFilename());
//				vo.setFilepath(before.getFilepath());
//			}
//		}else {
//			//첨부파일이 있는 경우
//			//원래O,X -> 첨부파일 업로드
//			vo.setFilename(file.getOriginalFilename());
//			vo.setFilepath(common.fileUpload("notice", file, request));
//		}
		
		//화면에서 변경입력한 정보로 DB에 변경저장한 후 응답화면연결 - 정보화면
//		if(service.notice_update(vo)==1) {
//			//물리적 파일 삭제 처리
//			if(file.isEmpty()) {
//				//원래 O 화면에서 삭제한 경우
//				if(vo.getFilename().isEmpty()) {
//					common.deletedFile(before.getFilepath(), request);
//				}
//			}else {
//				//원래 O 바꿔서 첨부한 경우
//				common.deletedFile(before.getFilepath(), request);
//			}
//		}
		
		sql.update("member.update", vo);
		
		return "redirect:info?id="+vo.getMember_id()+"&curPage="+page.getCurPage()+
				"&search="+page.getSearch()+
				"&keyword="+URLEncoder.encode(page.getKeyword(), "utf-8") ;
	}
	
	//아이디 중복확인 처리 요청
	@ResponseBody @RequestMapping("/memberNicknameCheck")
	public boolean memberNicknameCheck(String member_nickname) {
		return sql.selectOne("member.info", member_nickname)==null ? false : true;
	}
	

}
