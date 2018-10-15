package com.my.dao;

import java.util.List;
import java.util.Map;

import com.my.bean.Student;
import com.my.entity.CourseEntity;
import com.my.entity.CourseEntity_PK;

public interface StudentDAO {

	public Student getStudentDetails(String studentId) throws Exception ;
	public Map<String, List<CourseEntity>> getCourses(int semester,String branch,String studentId) throws Exception ;
//	public List<CourseEntity> getCoursesSelected(List<String> selectedCourses) throws Exception;
	public boolean setCourses(Student student) throws Exception ;
}
