package com.my.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="results")
@IdClass(Result_PK.class)
public class ResultEntity {
	@Id
	private String studentId;
	@Id
	private Integer sem;
	@Id
	private String CourseId;
	private Integer marks;
	private String grade;
	private String result;
	private String sgpa;
	private String cgpa;
	
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
	public Integer getMarks() {
		return marks;
	}
	public void setMarks(Integer marks) {
		this.marks = marks;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getSgpa() {
		return sgpa;
	}
	public void setSgpa(String sgpa) {
		this.sgpa = sgpa;
	}
	public String getCgpa() {
		return cgpa;
	}
	public void setCgpa(String cgpa) {
		this.cgpa = cgpa;
	}
}
