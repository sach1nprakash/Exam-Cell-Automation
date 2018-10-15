package com.my.dao;

import java.util.List;

import com.my.bean.Course;

public interface AdminDAO {
	public List<Course> getMandatoryCourses(Integer semester,String branch) throws Exception;
}
