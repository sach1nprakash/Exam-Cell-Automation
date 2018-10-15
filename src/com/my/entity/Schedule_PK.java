package com.my.entity;

import java.io.Serializable;

public class Schedule_PK implements Serializable{
	private String scheduleId;
	public String getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	private String courseId;

	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
}
