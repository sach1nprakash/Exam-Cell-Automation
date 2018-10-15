package com.my.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="courses")
@IdClass(CourseEntity_PK.class)
public class CourseEntity {
	@Id
	private String courseId;
	@Id
	private Integer semester;
	@Id
	private String branch;
	private String courseName;
	private String courseType;
	private Integer registrationCost;
	
	public Integer getRegistrationCost() {
		return registrationCost;
	}
	public void setRegistrationCost(Integer registrationCost) {
		this.registrationCost = registrationCost;
	}
	public String getCourseType() {
		return courseType;
	}
	public void setCourseType(String courseType) {
		this.courseType = courseType;
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
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
}
