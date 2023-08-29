package com.example.finalteamproject.common;


import com.example.finalteamproject.main.FavorVO;

import java.util.ArrayList;

public class MemberVO {
	String member_id, member_pw, member_name, member_nickname, 
		   member_gender, member_phone, member_profileimg, member_phone_id;
	int member_age, member_manner_score, member_admin;



	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getMember_pw() {
		return member_pw;
	}

	public void setMember_pw(String member_pw) {
		this.member_pw = member_pw;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	public String getMember_nickname() {
		return member_nickname;
	}

	public void setMember_nickname(String member_nickname) {
		this.member_nickname = member_nickname;
	}

	public String getMember_gender() {
		return member_gender;
	}

	public void setMember_gender(String member_gender) {
		this.member_gender = member_gender;
	}

	public String getMember_phone() {
		return member_phone;
	}

	public void setMember_phone(String member_phone) {
		this.member_phone = member_phone;
	}

	public String getMember_profileimg() {
		return member_profileimg;
	}

	public void setMember_profileimg(String member_profileimg) {
		this.member_profileimg = member_profileimg;
	}

	public String getMember_phone_id() {
		return member_phone_id;
	}

	public void setMember_phone_id(String member_phone_id) {
		this.member_phone_id = member_phone_id;
	}

	public int getMember_age() {
		return member_age;
	}

	public void setMember_age(int member_age) {
		this.member_age = member_age;
	}

	public int getMember_manner_score() {
		return member_manner_score;
	}

	public void setMember_manner_score(int member_manner_score) {
		this.member_manner_score = member_manner_score;
	}

	public int getMember_admin() {
		return member_admin;
	}

	public void setMember_admin(int member_admin) {
		this.member_admin = member_admin;
	}
}
