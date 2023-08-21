package com.example.finalteamproject.gps;

import java.io.Serializable;

public class GpsVO implements Serializable {
	private int key, senior_like_num;
	private String senior_name, senior_roadaddress, senior_numaddress
	, senior_call, senior_latitude, senior_longitude, sido, sigungu, member_id, distance;
	
	
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public int getSenior_like_num() {
		return senior_like_num;
	}
	public void setSenior_like_num(int senior_like_num) {
		this.senior_like_num = senior_like_num;
	}
	public String getSenior_name() {
		return senior_name;
	}
	public void setSenior_name(String senior_name) {
		this.senior_name = senior_name;
	}
	public String getSenior_roadaddress() {
		return senior_roadaddress;
	}
	public void setSenior_roadaddress(String senior_roadaddress) {
		this.senior_roadaddress = senior_roadaddress;
	}
	public String getSenior_numaddress() {
		return senior_numaddress;
	}
	public void setSenior_numaddress(String senior_numaddress) {
		this.senior_numaddress = senior_numaddress;
	}
	public String getSenior_call() {
		return senior_call;
	}
	public void setSenior_call(String senior_call) {
		this.senior_call = senior_call;
	}
	public String getSenior_latitude() {
		return senior_latitude;
	}
	public void setSenior_latitude(String senior_latitude) {
		this.senior_latitude = senior_latitude;
	}
	public String getSenior_longitude() {
		return senior_longitude;
	}
	public void setSenior_longitude(String senior_longitude) {
		this.senior_longitude = senior_longitude;
	}
	public String getSido() {
		return sido;
	}
	public void setSido(String sido) {
		this.sido = sido;
	}
	public String getSigungu() {
		return sigungu;
	}
	public void setSigungu(String sigungu) {
		this.sigungu = sigungu;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	

	
}
