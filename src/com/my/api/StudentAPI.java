package com.my.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.my.bean.Course;
import com.my.bean.CoursesRetrivel;
import com.my.bean.HallTicket;
import com.my.bean.Queries;
import com.my.bean.Student;
import com.my.resources.AppConfig;
import com.my.resources.Factory;
import com.my.resources.HibernateUtility;
import com.my.resources.JSONParser;
import com.my.service.StudentServiceImpl;

@Path("Student")
public class StudentAPI {

	@Path("validate")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateStudent(String dataRecieved) throws Exception {

		StudentServiceImpl service;
		Student student = new Student();

		Response response = null;
		try {

			System.out.println("Inside api");
			service = Factory.createStudentService();
			student = JSONParser.fromJson(dataRecieved, Student.class);
			System.out.println(dataRecieved);
			student = service.validateStudentDetails(student);
			String successMessage = this.SuccessMessage(student);
			student.setMessage(successMessage);
			String msg = JSONParser.toJson(student);
			System.out.println("student data: " + msg);
			response = Response.status(Status.OK).entity(msg).build();
			System.out.println("final");
		}

		catch (Exception e) {

			String errorMessage = AppConfig.PROPERTIES.getProperty(e
					.getMessage());

			student.setMessage(errorMessage);

			String returnString = JSONParser.toJson(student);

			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();

		}

		finally {
			if (HibernateUtility.createSessionFactory() != null) {
				// HibernateUtility.closeSessionFactory();
			}
		}

		return response;
	}

	@Path("gethtdata")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response hallTicketcourseId(String dataRecieved) throws Exception {
		Response response = null;
		Student student = new Student();
		try {
			System.out.println(dataRecieved);
			student = JSONParser.fromJson(dataRecieved, Student.class);
			System.out.println("in API" + student.getStudentId() + " "
					+ student.getBranch() + " " + student.getSem());
			StudentServiceImpl service = Factory.createStudentService();

			List<String> ht = service.getHallTicketOfStudent(student);
			String msg = null;
			if (ht.size() != 0) {
				msg = JSONParser.toJson(ht);
			} else {
				List<String> ht1 = new ArrayList<String>();
				ht1.add("Courses not yet Approved");
				msg = JSONParser.toJson(ht1);
			}
			System.out.println(msg);
			response = Response.status(Status.OK).entity(msg).build();
		} catch (Exception exception) {
		}
		return response;
	}

	@Path("toadmin")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response askQuery(String dataRecieved) throws Exception {
		Response response = null;
		Queries query = new Queries();

		StudentServiceImpl service = Factory.createStudentService();
		try {
			System.out.println("in ask query api");
			System.out.println(dataRecieved);
			query = JSONParser.fromJson(dataRecieved, Queries.class);
			System.out.println(query.getStudentId() + "     "
					+ query.getMesssage());
			service.askQuery(query);
			System.out.println("after si");
			String success = this.SuccessMessage2();
			query.setMesssage(success);
			String msg = JSONParser.toJson(query);
			System.out.println(msg);
			response = Response.status(Status.OK).entity(msg).build();
			System.out.println("Exiting api");
		} catch (Exception e) {

		}
		return response;
	}

	// -------------------------------------------------------------------------------------------
	@Path("getres")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResults(String dataRecieved) throws Exception {
		Response response = null;
		StudentServiceImpl service = Factory.createStudentService();
		try {
			CoursesRetrivel cr = JSONParser.fromJson(dataRecieved,
					CoursesRetrivel.class);
			System.out.println("student Id :" + cr.getStudentId());
			System.out.println("student sem :" + cr.getSem());
			List<String> data = service.getResultOfStudent(cr.getStudentId(),
					Integer.parseInt(cr.getSem()));
			System.out.println(data);
			String msg=null;
			if(data.size()>7){
				msg = JSONParser.toJson(data);
			}else{
				List<String> data1=new ArrayList<String>();
				data1.add("result not yet published");
				msg = JSONParser.toJson(data1);
			}
			
			System.out.println(msg);
			response = Response.status(Status.OK).entity(msg).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Path("fetchannouncements")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAnnouncements() throws Exception {
		Response response = null;
		StudentServiceImpl service = Factory.createStudentService();
		try {
			List<String> data = service.getAnnouncmentsService();
			String msg = JSONParser.toJson(data);
			System.out.println(msg);
			response = Response.status(Status.OK).entity(msg).build();
		} catch (Exception e) {

		}
		return response;
	}

	@Path("getquote")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQuote() throws Exception {
		Response response = null;
		StudentServiceImpl service = Factory.createStudentService();
		CoursesRetrivel cr = new CoursesRetrivel();
		try {
			System.out.println("Getting quotes...");
			String data = service.getSingleQuote();
			System.out.println("data" + data);
			cr.setMessage(data);
			String msg = JSONParser.toJson(cr);
			System.out.println(msg);
			response = Response.status(Status.OK).entity(msg).build();
		} catch (Exception e) {

		}
		return response;
	}

	private String SuccessMessage(Student student) {
		String message = null;
		message = AppConfig.PROPERTIES
				.getProperty("StudentBean.LOGIN_SUCCESS1");
		message += student.getStudentId();
		return message;
	}

	private String SuccessMessage2() {
		String message = null;
		message = AppConfig.PROPERTIES.getProperty("STUDENTAPI.MESSAGE_SENT");
		return message;
	}
}
