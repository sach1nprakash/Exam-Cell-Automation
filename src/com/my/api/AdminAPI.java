package com.my.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.my.bean.Admin;
import com.my.bean.BacklogSchedule;
import com.my.bean.CoursesRetrivel;
import com.my.bean.Results;
import com.my.bean.SetSchedule;
import com.my.bean.Student;
import com.my.dao.AdminDAOImpl;
import com.my.resources.AppConfig;
import com.my.resources.Factory;
import com.my.resources.JSONParser;
import com.my.service.AdminServiceImpl;
import com.my.service.StudentServiceImpl;

@Path("Admin")
public class AdminAPI {

	@Path("validate")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateAdmin(String dataRecieved) throws Exception {
		StudentServiceImpl service;
		Admin admin = new Admin();

		Response response = null;
		try {

			service = Factory.createStudentService();
			admin = JSONParser.fromJson(dataRecieved, Admin.class);

			admin = service.validateAdminDetails(admin);
			String successMessage = this.SuccessMessage(admin);
			admin.setMessage(successMessage);

			String msg = JSONParser.toJson(admin);
			response = Response.status(Status.OK).entity(msg).build();

		}

		catch (Exception e) {

			String errorMessage = AppConfig.PROPERTIES.getProperty(e
					.getMessage());

			admin.setMessage(errorMessage);

			String returnString = JSONParser.toJson(admin);

			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();

		}

		finally {

		}

		return response;
	}

	

	@Path("courselist")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getcourses(String dataRecieved) throws Exception {

		System.out.println(dataRecieved);
		Response response;
		CoursesRetrivel cr = new CoursesRetrivel();
		cr = JSONParser.fromJson(dataRecieved, CoursesRetrivel.class);
		System.out.println(cr.getSem() + " " + cr.getBranch());
		AdminServiceImpl service = new AdminServiceImpl();
		Integer semester = Integer.parseInt(cr.getSem());
		List<String> subjects = service.getMandatoryCourses(semester,
				cr.getBranch());
		String msg=null;
		if(subjects.size()!=0)
			msg = JSONParser.toJson(subjects);
		else{
			List<String> subject=new ArrayList<String>();
			subject.add("Not yet Listed");
			msg = JSONParser.toJson(subject);
		}
		response = Response.status(Status.OK).entity(msg).build();
		return response;
	}

	@Path("setschedule")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setSchedule(String dataRecieved) throws Exception {
		Response response = null;
		System.out.println(dataRecieved);
		SetSchedule ss1 = new SetSchedule();
		System.out.println("class obj");
		ss1 = JSONParser.fromJson(dataRecieved, SetSchedule.class);
		System.out.println("ajhdbasfknhaslfha");
		for (String i : ss1.getSubjects()) {
			System.out.println(i);
		}
		for (String i : ss1.getDate()) {
			System.out.println(i);
		}
		for (String i : ss1.getTime()) {
			System.out.println(i);
		}

		AdminServiceImpl service = new AdminServiceImpl();
		boolean flag=service.setMandatoryCourses(ss1);
		String successMessage = this.SuccessMessage1(flag);
		SetSchedule ss2 = new SetSchedule();
		System.out.println(successMessage);
		ss2.setMessage(successMessage);
		String msg = JSONParser.toJson(ss2);
		response = Response.status(Status.OK).entity(msg).build();
		return response;
	}
	
	
	@Path("fetchreq")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchRequests() throws Exception {
		
		List<String> reqs= null;
		/*Map<String,String> reqs_final= new HashMap<String,String>();*/
		Response response=null;
		try
		{
			reqs=Factory.createAdminService().getrequestsfromdb();
			System.out.println("From api,before toJson"+reqs);
			String msg=null;
			if(reqs.size()!=0)
				msg = JSONParser.toJson(reqs);
			else{
				List<String> req=new ArrayList<String>();
				req.add("Students Not registered yet");
				msg = JSONParser.toJson(req);
			}
			System.out.println("From api,after toJson"+msg);
			
			System.out.println(msg);
			response = Response.status(Status.OK).entity(msg).build();
			return response;
		}
		catch(Exception e)
		{
		}
		return response;
	}
	
	
	@Path("setbacklogschedule")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setBacklogSchedule(String dataRecieved) throws Exception {
		Response response = null;
		System.out.println(dataRecieved);
		BacklogSchedule bs=new BacklogSchedule();
		System.out.println("class obj");
		bs = JSONParser.fromJson(dataRecieved, BacklogSchedule.class);
		System.out.println("ajhdbasfknhaslfha");
		for (String i : bs.getCourseId()) {
			System.out.println(i);
		}
		for (String i : bs.getDate()) {
			System.out.println(i);
		}
		
		for (String i : bs.getTiming()) {
			System.out.println(i);
		}

		AdminServiceImpl service = new AdminServiceImpl();
		service.setBacklogs(bs);

		return response;
	}
	
	@Path("getqueries")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQueries() throws Exception {
		Response response=null;
		List<String> query=null;
		try{
			AdminServiceImpl service = new AdminServiceImpl();
			query=service.getqueries();
			String msg=null;
			if(query.size()!=0)
				msg = JSONParser.toJson(query);
			else{
				List<String> req=new ArrayList<String>();
				req.add("NO Queries Asked");
				msg = JSONParser.toJson(req);
			}
			System.out.println(msg);
			response = Response.status(Status.OK).entity(msg).build();
		}catch(Exception e){
			
		}return response;
	}
	
//-------------------------------------------------------------------------------------------------	
	@Path("getregcourses")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getCoursesFromRegistered(String dataRecieved) throws Exception {
		Response response=null;
		List<String> courses=null;
		try{
			System.out.println("in gescourses api");
			AdminServiceImpl service = new AdminServiceImpl();
			CoursesRetrivel cr=JSONParser.fromJson(dataRecieved, CoursesRetrivel.class);
			System.out.println(cr.getSem());
			courses=service.getCoursesForMarks(cr.getSem());
			String msg=null;
			if(courses.size()!=0)
				msg = JSONParser.toJson(courses);
			else{
				List<String> req=new ArrayList<String>();
				req.add("NO students registerd for this semster");
				msg = JSONParser.toJson(req);
			}
			
			System.out.println(msg);
			response = Response.status(Status.OK).entity(msg).build();
		}catch(Exception e){
			
		}return response;
	}
	
	@Path("submitresult")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setMarksForStudents(String datarecieved) throws Exception{
		Response response=null;
		Results result=null;
		try{
			System.out.println("In setMarks api : "+datarecieved);
			AdminServiceImpl service = new AdminServiceImpl();
			result=JSONParser.fromJson(datarecieved, Results.class);
			service.setMarksForCourses(result);
		}catch(Exception e){
			
		}
		return response;
	}
	
	
	@Path("storeannouncement")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setAnnouncments(String datarecieved) throws Exception{
		Response response=null;
		try{
			AdminServiceImpl service = new AdminServiceImpl();
			CoursesRetrivel cr=JSONParser.fromJson(datarecieved, CoursesRetrivel.class);
			System.out.println(cr.getMessage());
			service.makAnnouncments(cr.getMessage());
		}catch(Exception e){
			
		}return response;
	}
	
	@Path("getquotes")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQuotes(String datarecieved) throws Exception{
		Response response = null;
		try{
			AdminServiceImpl service = new AdminServiceImpl();
			List<String> quotes=service.getQuotes();
			String msg=JSONParser.toJson(quotes);
			System.out.println(msg);
			response=Response.status(Status.OK).entity(msg).build();
		}catch(Exception e){
			
		}return response;
	}
	
	@Path("setquote")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setSingleQuote(String datarecieved) throws Exception{
		Response response = null;
		try{
			AdminServiceImpl service = new AdminServiceImpl();
			CoursesRetrivel cr=JSONParser.fromJson(datarecieved, CoursesRetrivel.class);
			service.setQuotes(cr.getMessage());
		}catch(Exception e){
			
		}return response;
	}
//--------------------------------------------------------------------------------------------------	
	private String SuccessMessage(Admin admin) {
		String message = null;
		message = AppConfig.PROPERTIES
				.getProperty("StudentBean.LOGIN_SUCCESS1");

		return message;
	}
	
	private String SuccessMessage1(boolean flag){
		String message = null;
		if(flag==true)
			message = AppConfig.PROPERTIES
				.getProperty("ADMINAPI.SCHEDULE_CREATED");
		else
			message = AppConfig.PROPERTIES
			.getProperty("ADMINAPI.SCHEDULE_NOT_CREATED");

		return message;
	}

}
