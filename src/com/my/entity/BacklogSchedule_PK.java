package com.my.entity;

import java.io.Serializable;

import javax.persistence.Id;

public class BacklogSchedule_PK implements Serializable{

	private String studentId;
	private String courseId;
	
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

}
