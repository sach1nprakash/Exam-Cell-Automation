package com.my.bean;

import java.util.List;

public class BacklogSchedule {
	private String studentId;
	private List<String> courseId;
	private List<String> date;
	private List<String> timing;
	
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
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
	public List<String> getTiming() {
		return timing;
	}
	public void setTiming(List<String> timing) {
		this.timing = timing;
	}
}
