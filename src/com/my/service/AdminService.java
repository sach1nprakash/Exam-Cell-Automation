package com.my.service;

import java.util.List;

import com.my.bean.Course;

public interface AdminService {
	public List<String> getMandatoryCourses(Integer semester,String branch) throws Exception;
}
