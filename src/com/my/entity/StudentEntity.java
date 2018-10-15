package com.my.entity;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.my.bean.Course;



	@Entity
	@Table(name="student")
	public class StudentEntity {
		@Id
		private String studentId;
		@Temporal(TemporalType.DATE)
		private Calendar dateOfBirth;
		private String studentName;
		private String branch;
		private Integer sem;
		private String email;
		private Integer costofexams;
		private String ddnumber;
		private String cgpa;
		
		@ManyToMany(cascade=CascadeType.ALL)
		@JoinTable(name="studentcourse",
		joinColumns=@JoinColumn(name="studentId", referencedColumnName="studentId"),
		inverseJoinColumns={
			@JoinColumn(name="courseId", referencedColumnName="courseId"),
			@JoinColumn(name="branch", referencedColumnName="branch"),
			@JoinColumn(name="semester", referencedColumnName="semester")
		}
		)
		private List<CourseEntity> courses;
		
		
		public List<CourseEntity> getCourses() {
			return courses;
		}
		public void setCourses(List<CourseEntity> courses) {
			this.courses = courses;
		}
		
		@ManyToMany(cascade=CascadeType.ALL)
		@JoinTable(name="backlogs",
		joinColumns=@JoinColumn(name="studentId", referencedColumnName="studentId"),
		inverseJoinColumns={
			@JoinColumn(name="courseId", referencedColumnName="courseId"),
			@JoinColumn(name="branch", referencedColumnName="branch"),
			@JoinColumn(name="semester", referencedColumnName="semester")
		}
				)
		private List<CourseEntity> backlogs;
		
		public List<CourseEntity> getBacklogs() {
			return backlogs;
		}
		public void setBacklogs(List<CourseEntity> backlogs) {
			this.backlogs = backlogs;
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
		}
		public Integer getCostofexams() {
			return costofexams;
		}
		public void setCostofexams(Integer costofexams) {
			this.costofexams = costofexams;
		}
		public String getDdnumber() {
			return ddnumber;
		}
		public void setDdnumber(String ddnumber) {
			this.ddnumber = ddnumber;
		}
		public String getCgpa() {
			return cgpa;
		}
		public void setCgpa(String cgpa) {
			this.cgpa = cgpa;
		}	
}
