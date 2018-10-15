package com.my.bean;

import java.util.List;

public class HallTicket {
	private List<String> courseId;
	private List<String> date;
	private List<String> Time;
	
	public List<String> getCourseId() {
		return courseId;
	}
	public void setCourseId(List<String> courseId) {
		this.courseId = courseId;
	}
	public List<String> getDate() {
		return date;
	}
	public void setDate(List<String> date) {
		this.date = date;
	}
	public List<String> getTime() {
		return Time;
	}
	public void setTime(List<String> time) {
		Time = time;
	}
	
	
}
