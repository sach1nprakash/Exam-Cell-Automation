package com.my.service;

import java.util.List;
import java.util.Map;

import com.my.bean.Admin;
import com.my.bean.CoursesRetrivel;
import com.my.bean.Student;
import com.my.bean.registeredCourses;
import com.my.entity.CourseEntity;
import com.my.entity.CourseEntity_PK;

public interface StudentService {

	public boolean validateStudentDetails(Student student) throws Exception;
	public Admin validateAdminDetails(Admin admin) throws Exception;
	public Map<String, List<CourseEntity>> getListOfCourses(CoursesRetrivel cr) throws Exception;
	public List<CourseEntity> getSelectedCourses(List<String> selectedCourses) throws Exception;
	public boolean setCoursestoStudent(registeredCourses rc) throws Exception;
}
