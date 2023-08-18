package com.hanul.cloud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import cloud.game.GameDAO;
import cloud.game.GameVO;
import cloud.gps.GpsDAO;
import cloud.gps.GpsVO;

@RestController
@RequestMapping("/game")
public class GameController {
	@Autowired
	private GameDAO dao;

	@RequestMapping("/rank")
	public List<GameVO> game_rank(GameVO vo) {
		return dao.game_rank(vo);
	}

	@RequestMapping("/score")
	public void game_score(GameVO vo) {
		try {
			dao.game_first(vo);
		} catch (Exception e) {

		}
		dao.game_second(vo);
	}
	
	
	
	
//	@RequestMapping("/likebtn")
//	public void likebtn(int key, String member_id) {
//		dao.bmark(key, member_id);
//		try {
//			dao.addlike(key);
//		} catch (Exception e) {
//			
//		}
//		dao.likecnt(key);
//	}
//
//	@RequestMapping("/unlikebtn")
//	public void unlikebtn(int key, String member_id) {
//		dao.delbmark(key, member_id);
//		dao.unlikecnt(key);
//	}
//
//	@RequestMapping("/likeyet")
//	public String likeyet(int key, String member_id) {
//		return dao.likeyet(key, member_id);
//	}
//	
//	@RequestMapping("/search")
//	public List<GpsVO> search_result(String keyword) {
//		return dao.search_result(keyword);
//	}
}
