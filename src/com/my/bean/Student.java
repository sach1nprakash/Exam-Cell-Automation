package com.my.bean;

import java.util.Calendar;
import java.util.List;

import com.my.entity.CourseEntity;

public class Student {
	
	private String studentId;
	private Calendar dateOfBirth;
	private String studentName;
	private String branch;
	private Integer sem;
	private String email;
	private String message;
	//to be added in database
	private Integer costOfExams;
	private String ddNumber;
	private String cgpa;
	// these two
	private List<CourseEntity> backlogs;
	private List<CourseEntity> courses;
	
	


	public Integer getCostOfExams() {
		return costOfExams;
	}
	public void setCostOfExams(Integer costOfExams) {
		this.costOfExams = costOfExams;
	}
	public String getDdNumber() {
		return ddNumber;
	}
	public void setDdNumber(String ddNumber) {
		this.ddNumber = ddNumber;
	}
	public List<CourseEntity> getBacklogs() {
		return backlogs;
	}
	public void setBacklogs(List<CourseEntity> backlogs) {
		this.backlogs = backlogs;
	}
	public List<CourseEntity> getCourses() {
		return courses;
	}
	public void setCourses(List<CourseEntity> courses) {
		this.courses = courses;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public Integer getSem() {
		return sem;
	}
	public void setSem(Integer sem) {
		this.sem = sem;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public Calendar getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Calendar dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		/*this.dateOfBirth.set(Calendar.HOUR_OF_DAY,0);
		this.dateOfBirth.set(Calendar.MINUTE,0);
		this.dateOfBirth.set(Calendar.SECOND,0);
		this.dateOfBirth.set(Calendar.MILLISECOND,0);
*/	}
	public String getCgpa() {
		return cgpa;
	}
	public void setCgpa(String cgpa) {
		this.cgpa = cgpa;
	}
}
