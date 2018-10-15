package com.my.bean;

import java.util.List;

public class Results {
	private String studentId;
	private String sgpa;
	private String cgpa;
	private List<String> courseId;
	private List<String> marks;
	private List<String> grade;
	private List<String> result;
	
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public List<String> getCousrseId() {
		return courseId;
	}
	public void setCousrseId(List<String> cousrseId) {
		this.courseId = cousrseId;
	}
	public List<String> getMarks() {
		return marks;
	}
	public void setMarks(List<String> marks) {
		this.marks = marks;
	}
	public List<String> getGrade() {
		return grade;
	}
	public void setGrade(List<String> grade) {
		this.grade = grade;
	}
	public List<String> getResult() {
		return result;
	}
	public void setResult(List<String> result) {
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
