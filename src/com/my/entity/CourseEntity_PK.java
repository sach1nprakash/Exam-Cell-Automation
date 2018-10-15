package com.my.entity;

import java.io.Serializable;

import javax.persistence.Id;

public class CourseEntity_PK implements Serializable{
	private String courseId;
	private Integer semester;
	private String branch;
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public Integer getSemester() {
		return semester;
	}
	public void setSemester(Integer semester) {
		this.semester = semester;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	
	
}
