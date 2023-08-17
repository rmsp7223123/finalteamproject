package com.example.finalteamproject.game;

public class GameVO {
	private int game_rank, game_score;
	private String member_id;
	public int getGame_rank() {
		return game_rank;
	}
	public void setGame_rank(int game_rank) {
		this.game_rank = game_rank;
	}
	public int getGame_score() {
		return game_score;
	}
	public void setGame_score(int game_score) {
		this.game_score = game_score;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	
}
