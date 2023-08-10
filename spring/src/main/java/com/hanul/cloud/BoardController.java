package com.hanul.cloud;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import cloud.member.FavorBoardCommentVO;
import cloud.member.FavorBoardVO;

@RestController @RequestMapping("/board")
public class BoardController {
	@Autowired @Qualifier("project") SqlSession sql;
	
	//게시물 검색
	@RequestMapping(value="/list", produces = "text/html;charset=utf-8")
	public String list(String context, String favor, String align) {		
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("context", context);
		paramMap.put("favor", favor);
		paramMap.put("align", align);
		List<FavorBoardVO> list = sql.selectList("board.list", paramMap);
		return new Gson().toJson(list);
	}
	
	//추천수 불러오기
	@RequestMapping(value="/rec", produces = "text/html;charset=utf-8")
	public String rec(String id) {
		return sql.selectOne("board.rec", id)==null ? "0" : sql.selectOne("board.rec", id)+"";
	}
	
	//관심사 이름으로 키값 불러오기
	@RequestMapping(value="/favor", produces = "text/html;charset=utf-8")
	public String favor(String favor) {
		return sql.selectOne("board.favor", favor)+"";
	}
	
	//게시물 번호로 관심사 이름 불러오기
	@RequestMapping(value="/favorName", produces = "text/html;charset=utf-8")
	public String favorName(String id) {
		String result = sql.selectOne("board.favorName", id);
		return  result;
	}
	
	//새 게시글 작성
	@RequestMapping(value="/insert", produces = "text/html;charset=utf-8")
	public String insert(FavorBoardVO vo) {
		return sql.insert("board.insert", vo)==1 ? "성공" : "실패";
	}
	
	//게시글 아이디로 게시글 불러오기
	@RequestMapping(value="/select", produces = "text/html;charset=utf-8")
	public String select(int fav_board_id) {
		FavorBoardVO vo = sql.selectOne("board.select", fav_board_id);
		return new Gson().toJson(vo);
	}
	
	//게시글 아이디로 게시글 관심사 이름 불러오기
	@RequestMapping(value="/selectFavor", produces = "text/html;charset=utf-8")
	public String selectFavor(int fav_board_id) {
		String result = sql.selectOne("board.selectFavor", fav_board_id);
		return result;
	}
	
	//게시글 아이디로 게시글 댓글 수 불러오기
	@RequestMapping(value="/commentCnt", produces = "text/html;charset=utf-8")
	public String commentCnt(int fav_board_id) {
		String result = (String) (sql.selectOne("board.commentCnt", fav_board_id)==null ? "0" : sql.selectOne("board.commentCnt", fav_board_id));
		return result;
	}
	
	//회원이 게시글에 좋아요를 눌렀는지 확인
	@RequestMapping(value="/checkRec", produces = "text/html;charset=utf-8")
	public String checkRec(int fav_board_id, String member_id_like) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("fav_board_id", fav_board_id+"");
		map.put("member_id_like", member_id_like);
		String result = sql.selectOne("board.checkRec", map)==null ? "없음" : "있음";
		return result;
	}
	
	//좋아요 insert
	@RequestMapping(value="/insertRec", produces = "text/html;charset=utf-8")
	public String insertRec(int fav_board_id, String member_id_like) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("fav_board_id", fav_board_id+"");
		map.put("member_id_like", member_id_like);
		String result = sql.insert("board.insertRec", map)==1 ? "성공" : "실패";
		return result;
	}
	
	//좋아요 delete
	@RequestMapping(value="/deleteRec", produces = "text/html;charset=utf-8")
	public String deleteRec(int fav_board_id, String member_id_like) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("fav_board_id", fav_board_id+"");
		map.put("member_id_like", member_id_like);
		String result = sql.delete("board.deleteRec", map)==1 ? "성공" : "실패";
		return result;
	}
	
	//게시물 조회수 증가
	@RequestMapping(value="/viewCnt", produces = "text/html;charset=utf-8")
	public String viewCnt(int id) {
		String result = sql.update("board.viewCnt", id)==1 ? "성공" : "실패";
		return result;
	}
	
	//게시물 삭제
	@RequestMapping(value="/deleteBoard", produces = "text/html;charset=utf-8")
	public String deleteBoard(int id) {
		String result = sql.delete("board.deleteBoard", id)==1 ? "성공" : "실패";
		return result;
	}
	
	//게시물 수정
	@RequestMapping(value="/modify", produces = "text/html;charset=utf-8")
	public String modify(FavorBoardVO vo) {
		String result = sql.insert("board.modify", vo)== 1 ? "성공" : "실패";
		return result ;
	}
	
	//댓글 목록 가져오기
	@RequestMapping(value="/commentList", produces = "text/html;charset=utf-8")
	public String commentList(int fav_board_id, String align) {		
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("fav_board_id", fav_board_id+"");
		paramMap.put("align", align);
		List<FavorBoardVO> list = sql.selectList("board.commentList", paramMap);
		return new Gson().toJson(list);
	}	
	
	//댓글 삭제
	@RequestMapping(value="/deleteComment", produces = "text/html;charset=utf-8")
	public String deleteComment(int id) {
		String result = sql.delete("board.deleteComment", id)==1 ? "성공" : "실패";
		return result;
	}
	
	//작성자 아이디로 닉네임 가져오기
	@RequestMapping(value="/nickname", produces = "text/html;charset=utf-8")
	public String nickname(String id) {
		String result = sql.selectOne("board.nickname", id);
		return result;
	}
	
	//댓글 등록하기
	@RequestMapping(value="/insertComment", produces = "text/html;charset=utf-8")
	public String insertComment(FavorBoardCommentVO vo) {
		String result = sql.insert("board.insertComment", vo)==1 ? "성공" : "실패";
		return result;
	}
	
	//댓글 수정하기
	@RequestMapping(value="/updateComment", produces = "text/html;charset=utf-8")
	public String updateCommnet(String fav_board_comment_id, String fav_board_comment_content) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("fav_board_id", fav_board_comment_id);
		paramMap.put("align", fav_board_comment_content);
		String result = sql.update("board.updateComment", paramMap)==1 ? "성공" : "실패";
		return result;
	}
	
	
	
	
	

}
