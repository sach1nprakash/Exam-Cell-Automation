package com.my.bean;

import java.util.List;

public class registeredCourses {
	private String studentId;
	private List<String> registerCourses;
	private String cost;
	private String ddnumber;
	private String message;
	
	
	
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getDdnumber() {
		return ddnumber;
	}
	public void setDdnumber(String ddnumber) {
		this.ddnumber = ddnumber;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public List<String> getRegisterCourses() {
		return registerCourses;
	}
	public void setRegisterCourses(List<String> registerCourses) {
		this.registerCourses = registerCourses;
	}
}
