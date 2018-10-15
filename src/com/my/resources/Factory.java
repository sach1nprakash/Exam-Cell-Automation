package com.my.resources;

import org.apache.log4j.Logger;

import com.my.dao.AdminDAOImpl;
import com.my.dao.StudentDAOImpl;
import com.my.service.AdminServiceImpl;
import com.my.service.StudentServiceImpl;

public class Factory {
	public static StudentServiceImpl createStudentService() {
		Logger logger=Logger.getLogger(Factory.class);
		logger.info("FactoryService Method: createStudentService()");
		return new StudentServiceImpl();
	}
	public static AdminServiceImpl createAdminService(){
		Logger logger=Logger.getLogger(Factory.class);
		logger.info("FactoryService Method: createAdminService()");
		return new AdminServiceImpl();
	}
	public static StudentDAOImpl createStudentDAO() throws Exception {
		Logger logger=Logger.getLogger(Factory.class);
		logger.info("FactoryDAO Method: createStudentDAO()");
		return new StudentDAOImpl();
	}
	public static AdminDAOImpl creatAdminDAO() throws Exception {
		Logger logger=Logger.getLogger(Factory.class);
		logger.info("FactoryDAO Method: creatAdminDAO()");
		return new AdminDAOImpl();
	}
}
