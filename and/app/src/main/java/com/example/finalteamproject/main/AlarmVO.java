package com.example.finalteamproject.main;

public class AlarmVO {
	String member_id, alarm_content, alarm_time, member_phone_id;
	int alarm_id;

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getAlarm_content() {
		return alarm_content;
	}

	public void setAlarm_content(String alarm_content) {
		this.alarm_content = alarm_content;
	}

	public String getAlarm_time() {
		return alarm_time;
	}

	public void setAlarm_time(String alarm_time) {
		this.alarm_time = alarm_time;
	}

	public String getMember_phone_id() {
		return member_phone_id;
	}

	public void setMember_phone_id(String member_phone_id) {
		this.member_phone_id = member_phone_id;
	}

	public int getAlarm_id() {
		return alarm_id;
	}

	public void setAlarm_id(int alarm_id) {
		this.alarm_id = alarm_id;
	}
}
