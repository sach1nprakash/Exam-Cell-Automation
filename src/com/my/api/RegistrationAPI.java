package com.my.api;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.my.bean.CoursesRetrivel;
import com.my.bean.registeredCourses;
import com.my.entity.CourseEntity;
import com.my.resources.AppConfig;
import com.my.resources.Factory;
import com.my.resources.HibernateUtility;
import com.my.resources.JSONParser;
import com.my.service.StudentServiceImpl;

@Path("register")
public class RegistrationAPI {
	@Path("listofcourses")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response getCoursesList(String dataRecieved) throws Exception{
		List<CourseEntity> sem_courses= new ArrayList<CourseEntity>();
		List<CourseEntity> stu_backlogs= new ArrayList<CourseEntity>();

		Map<String,String> courses_final = new HashMap<String,String>();
		
		Map<String,List<CourseEntity>> courses = null;
		Response response=null;
		try
		{
			System.out.println(dataRecieved);
			StudentServiceImpl service = Factory.createStudentService();
			CoursesRetrivel cr= new CoursesRetrivel();
			cr=JSONParser.fromJson(dataRecieved,CoursesRetrivel.class);		
			courses= service.getListOfCourses(cr);
			
			Set<String> keys = courses.keySet();
			for (String key : keys) {
				if(key.equals("currentCourses")){
					sem_courses=courses.get(key);
				}
				else if(key.equals("backlog"))
					stu_backlogs=courses.get(key);
			}
			
			for(CourseEntity c:sem_courses)
			{
				System.out.println(c.getCourseType());
				courses_final.put(c.getCourseId(),c.getCourseType());
			}
			
			for(CourseEntity ce:stu_backlogs)
			{
				System.out.println(ce.getCourseType());
				courses_final.put(ce.getCourseId(),"backlog");
			}
			String msg=null;
			if(courses_final.size()!=0){
				msg=JSONParser.toJson(courses_final);
			}else{
				Map<String,String> cf = new HashMap<String,String>();
				cf.put("Not Yet Listed","empty");
				msg=JSONParser.toJson(cf);
			}
			System.out.println(msg);
			response=Response.status(Status.OK).entity(msg).build();
			
		}
		catch(Exception exception){
			exception.printStackTrace();
		}
		
		return response;
	}
	
	
	
	@Path("registerCourses")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerCourses(String dataRecieved) throws Exception{
		Response response=null;
		registeredCourses cj=null;
		
		try
		{
			System.out.println("IN register");
			System.out.println(dataRecieved);
			StudentServiceImpl service = Factory.createStudentService();
			cj= new registeredCourses();
			cj=JSONParser.fromJson(dataRecieved,registeredCourses.class);		
			System.out.println("studentData "+cj.getStudentId());
			System.out.println("registered Courses "+cj.getRegisterCourses());
			System.out.println("cost "+cj.getCost());
			System.out.println("ddnumber "+cj.getDdnumber());
			
			boolean flag=service.setCoursestoStudent(cj);
			if(flag==true){
				System.out.println("Courses registered");
			}else
				System.out.println("not registered");
			cj=new registeredCourses();
			String successMessage = this.SuccessMessage(flag);
			cj.setMessage(successMessage);
			
			String msg=JSONParser.toJson(cj);
			System.out.println(msg);
			response=Response.status(Status.OK).entity(msg).build();
			
		}
		catch(Exception exception){
			String errorMessage = AppConfig.PROPERTIES.getProperty(exception.getMessage());
			cj.setMessage(errorMessage);
			String returnString = JSONParser.toJson(cj);
			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}finally{
			//HibernateUtility.closeSessionFactory();
		}
		return response;
	}
	
	
	
	@Path("fetchcost")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response calculateCost(String dataRecieved) throws Exception{
		Response response=null;
		registeredCourses cj=null;
		
		try
		{
			System.out.println("IN register");
			System.out.println(dataRecieved);
			StudentServiceImpl service = Factory.createStudentService();
			cj= new registeredCourses();
			cj=JSONParser.fromJson(dataRecieved,registeredCourses.class);		
			System.out.println("studentData "+cj.getStudentId());
			System.out.println("registered Courses "+cj.getRegisterCourses());
			
			Integer cost=service.calculateRegistrationFee(cj);
			String msseg=cost.toString();
			cj=new registeredCourses();
			cj.setMessage(msseg);
			String msg=JSONParser.toJson(cj);
			System.out.println(msg);
			response=Response.status(Status.OK).entity(msg).build();
			
		}
		catch(Exception exception){
			String errorMessage = AppConfig.PROPERTIES.getProperty(exception.getMessage());
			cj.setMessage(errorMessage);
			String returnString = JSONParser.toJson(cj);
			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}finally{
			//HibernateUtility.closeSessionFactory();
		}
		return response;
	}
	
	
	private String SuccessMessage(boolean flag) {
		String message = null;
		if(flag==true)
			message = AppConfig.PROPERTIES
				.getProperty("REGISTRTION.REGISTERED_SUCCESSFULLY");
		else if(flag==false)
			message = AppConfig.PROPERTIES
			.getProperty("REGISTRTION.NOT_REGISTERED");
		return message;
	}
}
