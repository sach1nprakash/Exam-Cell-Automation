package com.my.bean;

import java.util.List;

public class SetSchedule {
	private List<String> subjects;
	private List<String> date;
	private List<String> time;
	private String sem;
	private String branch;
	private String message;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<String> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}
	public List<String> getDate() {
		return date;
	}
	public void setDate(List<String> date) {
		this.date = date;
	}
	public List<String> getTime() {
		return time;
	}
	public void setTime(List<String> time) {
		this.time = time;
	}
	public String getSem() {
		return sem;
	}
	public void setSem(String sem) {
		this.sem = sem;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
}
