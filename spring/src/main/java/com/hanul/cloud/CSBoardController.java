package com.hanul.cloud;

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

import cloud.member.CSBoardCommentVO;
import cloud.member.CSBoardVO;
import cloud.member.FavorBoardCommentVO;
import cloud.member.FavorBoardVO;

@RestController @RequestMapping("/csboard")
public class CSBoardController {
	@Autowired @Qualifier("project") SqlSession sql;
	
	@Autowired
	private cloud.common.CommonUtility common;
	
	
	//게시물 검색
	@RequestMapping(value="/list", produces = "text/html;charset=utf-8")
	public String list(String context, String align) {		
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("context", context);
		paramMap.put("align", align);
		List<CSBoardVO> list = sql.selectList("csboard.list", paramMap);
		return new Gson().toJson(list);
	}
	
	//아이디로 게시물 정보 가져오기
	@RequestMapping(value="/info", produces = "text/html;charset=utf-8")
	public String info(int id) {
		CSBoardVO vo = sql.selectOne("csboard.info", id);
		return new Gson().toJson(vo);
	}
	
	//게시글 수정하기
	@RequestMapping(value="/modify", produces = "text/html;charset=utf-8")
	public String modify(CSBoardVO vo) {
		return sql.update("csboard.modify", vo)==1 ? "성공" : "실패";
	}
	
	//게시글 삭제하기
	@RequestMapping(value="/delete", produces = "text/html;charset=utf-8")
	public String delete(int id) {
		return sql.delete("csboard.delete", id)==1 ? "성공" : "실패";
	}
	
	//게시글 아이디로 댓글 불러오기
	@RequestMapping(value="/comment", produces = "text/html;charset=utf-8")
	public String comment(int id) {
		CSBoardCommentVO vo = sql.selectOne("csboard.comment", id);
		return new Gson().toJson(vo);
	}
	
	
	//게시글 작성
	@RequestMapping(value="/insert", produces = "text/html;charset=utf-8")
	public String insert(CSBoardVO vo) {
		return sql.insert("csboard.insert", vo)==1 ? "성공" : "실패";
	}
	
	//조회수 갱신
	@RequestMapping(value="/viewCnt", produces = "text/html;charset=utf-8")
	public String viewCnt(int id) {
		return sql.update("csboard.viewCnt", id)==1 ? "성공" : "실패";
	}
	
	//댓글 삭제
	@RequestMapping(value="/deleteComment", produces = "text/html;charset=utf-8")
	public String deleteComment(int id) {
		return sql.delete("csboard.deleteComment", id)==1 ? "성공" : "실패";
	}
	
	//댓글 등록
	@RequestMapping(value="/insertComment", produces = "text/html;charset=utf-8")
	public String insertComment(CSBoardCommentVO vo) {
		return sql.insert("csboard.insertComment", vo)==1 ? "성공" : "실패";
	}
	
	
	//댓글 등록이 된 게시글인지 아이디로 확인
	@RequestMapping(value="/commentCheck", produces = "text/html;charset=utf-8")
	public String commentCheck(int id) {
		return sql.selectOne("csboard.commentCheck", id)!=null ? "있음" : "없음";
	}
	
	
	
	
	
	
	
	

}

