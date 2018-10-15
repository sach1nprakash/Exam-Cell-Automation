package com.my.bean;

public class StudentCourse {
	private String studentId;
	private String courseId;
	private Integer semester;
	private String branch;
	private String scheduleStatus;


	public String getScheduleStatus() {
		return scheduleStatus;
	}
	
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

	public void setScheduleStatus(String scheduleStatus) {
		this.scheduleStatus = scheduleStatus;		
	}

}
