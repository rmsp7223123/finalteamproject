package com.example.finalteamproject.main;


public class CalendarVO {
	private String member_id, calendar_content, calendar_date, calendar_importance;
	private int calendar_id;

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getCalendar_content() {
		return calendar_content;
	}

	public void setCalendar_content(String calendar_content) {
		this.calendar_content = calendar_content;
	}

	public String getCalendar_date() {
		return calendar_date;
	}

	public void setCalendar_date(String calendar_date) {
		this.calendar_date = calendar_date;
	}

	public String getCalendar_importance() {
		return calendar_importance;
	}

	public void setCalendar_importance(String calendar_importance) {
		this.calendar_importance = calendar_importance;
	}

	public int getCalendar_id() {
		return calendar_id;
	}

	public void setCalendar_id(int calendar_id) {
		this.calendar_id = calendar_id;
	}
}
