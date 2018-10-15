package com.my.entity;

import java.io.Serializable;

public class Query_PK implements Serializable{
	private String studentId;
	private String message;
	
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
