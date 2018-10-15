package com.my.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="messages")
@IdClass(Query_PK.class)
public class QueryEntity {
	@Id
	private String studentId;
	@Id
	private String message;
	private String email;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}	
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getMessage() {
		return message;
	}
	public void setMesssage(String message) {
		this.message = message;
	}
}
