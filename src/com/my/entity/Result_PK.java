package com.my.entity;

import java.io.Serializable;

public class Result_PK implements Serializable{
	private String studentId;
	private Integer sem;
	private String CourseId;
	
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public Integer getSem() {
		return sem;
	}
	public void setSem(Integer sem) {
		this.sem = sem;
	}
	public String getCourseId() {
		return CourseId;
	}
	public void setCourseId(String courseId) {
		CourseId = courseId;
	}
	
}
