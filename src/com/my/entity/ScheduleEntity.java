package com.my.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="schedule")
@IdClass(Schedule_PK.class)
public class ScheduleEntity {
	@Id
	private String scheduleId;
	@Id
	private String courseId;
	private String dateofexam;
	private String timing;
	
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getDateofexam() {
		return dateofexam;
	}
	public void setDateofexam(String dateofexam) {
		this.dateofexam = dateofexam;
	}
	
	public String getTiming() {
		return timing;
	}
	public void setTiming(String timing) {
		this.timing = timing;
	}
}
