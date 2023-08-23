package com.hanul.cloudWeb;

import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cloudWeb.Common.PageVO;
import cloudWeb.csboard.CSBoardCommentVO;
import cloudWeb.csboard.CSBoardVO;
import cloudWeb.member.MemberVO;

@Controller
@RequestMapping("/cs")
public class CsController {
	@Autowired
	@Qualifier("project")
	SqlSession sql;

	@RequestMapping("/list")
	public String home(HttpSession session, PageVO page, Model model) {
		page.setTotalList(sql.selectOne("csboard.totalList", page));
		page.setList(sql.selectList("csboard.list", page));
		model.addAttribute("page", page);
		session.setAttribute("category", "cs");
		return "cs/list";
	}

	// 선택한 방명록 정보 화면 요청
	@RequestMapping(value = "/info", method = { RequestMethod.POST, RequestMethod.GET })
	public String info(Model model, int csboard_id, PageVO page) {
		// 선택한 방명록 글 정보를 DB에서 조회해와 화면에 출력할 수 있도록 Model에 담기
		model.addAttribute("list", sql.selectList("csboard.commentList", csboard_id));
		model.addAttribute("vo", sql.selectOne("csboard.info", csboard_id));
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("lf", "\n");
		model.addAttribute("page", page);
		return "cs/info";
	}

	// 공지글정보 삭제처리 요청
	@RequestMapping("/delete")
	public String delete(String csboard_id, HttpServletRequest request, PageVO page) throws Exception {
		sql.delete("csboard.delete", csboard_id);
		return "redirect:list?" + "curPage=" + page.getCurPage() + "&search=" + page.getSearch() + "&keyword="
				+ URLEncoder.encode(page.getKeyword(), "utf-8");
	}

	// 공지글정보 수정 화면 요청
	@RequestMapping("/modify")
	public String modify(String csboard_id, Model model, PageVO page) {
		// 해당 글읠 정보를 DB에서 조회해와 수정화면에 출력할 수 있도록 Model에 담는다
		model.addAttribute("vo", sql.selectOne("csboard.info", csboard_id));
		model.addAttribute("page", page);
		return "cs/modify";
	}

	// 공지글정보 수정 저장처리 요청
	@RequestMapping("/update")
	public String update(CSBoardVO vo, PageVO page, HttpServletRequest request) throws Exception {
		sql.update("csboard.update", vo);

		return "redirect:info?csboard_id=" + vo.getCsboard_id() + "&curPage=" + page.getCurPage() + "&search="
				+ page.getSearch() + "&keyword=" + URLEncoder.encode(page.getKeyword(), "utf-8");
	}

	// 댓글 목록 조회
	@RequestMapping("/comment/list/{csboard_id}")
	public String comment_list(@PathVariable int csboard_id, Model model) {
		// 해당 방명록 글에 대한 댓글목록을 DB에서 조회, 댓글목록 화면에 출력
		model.addAttribute("list", sql.selectList("csboard.commentList", csboard_id));
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("lf", "\n");
		return "cs/comment/comment_list";
	}
	
	//댓글 등록처리
	@ResponseBody @RequestMapping("/comment/register")
	public boolean comment_register(CSBoardCommentVO vo) {
		return sql.insert("commentRegister", vo) == 1 ? true : false;
	}
	
	//댓글정보수정처리
	@ResponseBody @RequestMapping("/comment/update")
		public HashMap<String, String> comment_update(@RequestBody CSBoardCommentVO vo) {
		HashMap<String, String> map = new HashMap<String, String>();
		if(sql.update("csboard.commentUpdate", vo) == 1) {
			map.put("message", "댓글 수정 성공");
			map.put("content", vo.getCsboard_comment_content());
		}else {
			map.put("message", "댓글 수정 실패");
		}
		return map;
	}
	
	//댓글정보삭제처리
	@ResponseBody @RequestMapping("/comment/delete")
	public boolean comment_delete(int csboard_comment_id) {
		//해당 댓글정보를 DB에서 삭제
		return sql.delete("csboard.commentDelete", csboard_comment_id)==1 ? true : false;
	}

}
