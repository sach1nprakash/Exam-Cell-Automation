package com.my.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.my.bean.Admin;
import com.my.bean.CoursesRetrivel;
import com.my.bean.HallTicket;
import com.my.bean.Queries;
import com.my.bean.Results;
import com.my.bean.Student;
import com.my.bean.StudentCourse;
import com.my.bean.registeredCourses;
import com.my.dao.StudentDAOImpl;
import com.my.entity.AnnouncmentEntity;
import com.my.entity.CourseEntity;
import com.my.entity.QuoteEntity;
import com.my.resources.Factory;

public class StudentServiceImpl {

	private static Map<String, List<CourseEntity>> courses_map2;

	public Student validateStudentDetails(Student student) throws Exception {

		StudentDAOImpl dao = Factory.createStudentDAO();
		Student studentdata;
		try {

			studentdata = dao.getStudentDetails(student.getStudentId());

			System.out.println("From DAO " + studentdata.getStudentName());
			if (studentdata == null)
				throw new Exception("StudentService.STUDENT_NOT_AVAILABLE");
			else {

				LocalDate UI = LocalDate.of(
						student.getDateOfBirth().get(Calendar.YEAR), student
								.getDateOfBirth().get(Calendar.MONTH) + 1,
						student.getDateOfBirth().get(Calendar.DATE));
				LocalDate DB = LocalDate.of(
						studentdata.getDateOfBirth().get(Calendar.YEAR),
						studentdata.getDateOfBirth().get(Calendar.MONTH) + 1,
						studentdata.getDateOfBirth().get(Calendar.DATE));
				if (!(UI.equals(DB)))
					throw new Exception("StudentService.INCORRECT_PASSWORD");
			}
		} catch (Exception exception) {
			if (!exception.getMessage().contains("DAO")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}
		
		return studentdata;
	}

	public Admin validateAdminDetails(Admin admin) throws Exception {
		String adminId = "admin";
		String password = "pass";

		if (!(admin.getAdminId().equals(adminId))
				|| !(admin.getPassword().equals(password))) {
			throw new Exception("StudentService.INCORRECT_ADMIN_DETAILS");
		}
		return admin;
	}

	public Integer calculateRegistrationFee(registeredCourses rc)
			throws Exception {
		Integer cost = 0;
		Set<String> keys = courses_map2.keySet();
		for (String i : rc.getRegisterCourses()) {
			for (String key : keys) {
				if (key.equals("currentCourses")) {
					for (CourseEntity x : courses_map2.get(key)) {
						if (x.getCourseId().equals(i))
							cost = cost + x.getRegistrationCost();
					}
				} else if (key.equals("backlog"))
					for (CourseEntity x : courses_map2.get(key)) {
						if (x.getCourseId().equals(i))
							cost = cost + x.getRegistrationCost();
					}
			}
		}
		return cost;
	}

	public Map<String, List<CourseEntity>> getListOfCourses(CoursesRetrivel cr)
			throws Exception {

		StudentDAOImpl dao = Factory.createStudentDAO();
		Map<String, List<CourseEntity>> courses_map = null;

		int semester = Integer.parseInt(cr.getSem());
		try {
			System.out.println("In service");
			courses_map = dao.getCourses(semester, cr.getBranch(),
					cr.getStudentId());

		} catch (Exception exception) {
			if (!exception.getMessage().contains("DAO")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}
		courses_map2 = courses_map;
		return courses_map;
	}

	public List<CourseEntity> getSelectedCourses(List<String> selectedCourses)
			throws Exception {

		List<CourseEntity> coursesSelected = new ArrayList<CourseEntity>();

		Set<String> keys = courses_map2.keySet();
		for (String i : selectedCourses) {
			for (String key : keys) {
				if (key.equals("currentCourses")) {
					for (CourseEntity x : courses_map2.get(key)) {
						if (x.getCourseId().equals(i))
							coursesSelected.add(x);
					}
				} else if (key.equals("backlog"))
					for (CourseEntity x : courses_map2.get(key)) {
						if (x.getCourseId().equals(i))
							coursesSelected.add(x);
					}
			}
		}
		for (CourseEntity i : coursesSelected) {
			System.out.println(i.getCourseId() + " " + i.getCourseName());
		}
		return coursesSelected;
	}

	public boolean setCoursestoStudent(registeredCourses rc) throws Exception {
		StudentDAOImpl dao = Factory.createStudentDAO();
		List<CourseEntity> coursesSelected = null;
		Student student = new Student();
		boolean flag;
		try {
			coursesSelected = getSelectedCourses(rc.getRegisterCourses());
			student.setStudentId(rc.getStudentId());
			student.setCourses(coursesSelected);
			student.setCostOfExams(Integer.parseInt(rc.getCost()));
			student.setDdNumber(rc.getDdnumber());
			flag = dao.setCourses(student);
		} catch (Exception exception) {
			if (!exception.getMessage().contains("DAO")) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.error(exception.getMessage(), exception);
			}
			throw exception;
		}

		return flag;
	}

	public List<String> getHallTicketOfStudent(Student student)
			throws Exception {
		StudentDAOImpl dao = Factory.createStudentDAO();
		HallTicket ht = null;
		HallTicket ht1 = null;
		List<String> alldata = new ArrayList<String>();
		try {

			ht = dao.getHallTicket(student);
			ht1 = dao.getHallTicketBacklogs(student);
			System.out.println("after dao in getHalticket of service");
			if (ht.getCourseId().size() != 0 && ht1.getCourseId().size() != 0) {
				for (int i = 0; i < ht1.getCourseId().size(); i++) {
					ht.getCourseId().add(ht1.getCourseId().get(i));
					ht.getDate().add(ht1.getDate().get(i));
					ht.getTime().add(ht1.getTime().get(i));
				}
				for (String i : ht.getCourseId()) {
					System.out.println(i);
				}
				List<Date> dates = new ArrayList<Date>();
				SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

				for (String i : ht.getDate()) {
					System.out.println(i);
					System.out.println(i.substring(0, 10));
					dates.add(formatter2.parse(i.substring(0, 10)));
				}

				int n = ht.getDate().size();
				for (int i = 0; i < n - 1; i++)
					for (int j = 0; j < n - i - 1; j++)
						if (dates.get(j).after(dates.get(j + 1))) {
							// swap temp and arr[i]
							Date temp = dates.get(j);
							dates.set(j, dates.get(j + 1));
							dates.set(j + 1, temp);

							String temp2 = ht.getCourseId().get(j);
							ht.getCourseId()
									.set(j, ht.getCourseId().get(j + 1));
							ht.getCourseId().set(j + 1, temp2);

							String temp3 = ht.getTime().get(j);
							ht.getTime().set(j, ht.getDate().get(j + 1));
							ht.getTime().set(j + 1, temp3);
						}
				ht.getDate().clear();
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				for (Date i : dates) {
					ht.getDate().add(dateFormat.format(i));
					System.out.println(dateFormat.format(i));
				}

				List<StudentCourse> regc = dao.getregisteredCourses(student);
				for (int i = 0; i < ht.getCourseId().size(); i++) {
					boolean flag = false;
					for (StudentCourse j : regc) {
						if (ht.getCourseId().get(i).equals(j.getCourseId()))
							flag = true;
					}
					if (flag == false) {
						ht.getCourseId().remove(i);
						ht.getDate().remove(i);
						ht.getTime().remove(i);
					}
				}
				alldata.addAll(ht.getCourseId());
				alldata.add("-");
				alldata.addAll(ht.getDate());
				alldata.add("-");
				alldata.addAll(ht.getTime());
			}
		} catch (Exception exception) {
		}
		return alldata;
	}

	public void askQuery(Queries query) throws Exception {
		StudentDAOImpl dao = Factory.createStudentDAO();
		try {
			System.out.println(query.getStudentId());
			System.out.println(query.getEmail());
			System.out.println(query.getMesssage());
			dao.askQuery(query);
		} catch (Exception e) {

		}
	}

	// ---------------------------------------------------------------------------------------------
	public List<String> getResultOfStudent(String studentId, Integer sem)
			throws Exception {
		StudentDAOImpl dao = Factory.createStudentDAO();
		List<String> data = new ArrayList<String>();
		List<String> courses = new ArrayList<String>();
		List<String> marks = new ArrayList<String>();
		List<String> grades = new ArrayList<String>();
		List<String> results = new ArrayList<String>();
		
		double sgpa = 0.0;
		String cgpa = null;
		try {
			Results sc = dao.getMarksofStudent(studentId, sem);
			Student student = dao.getStudentDetails(studentId);
			for (int i = 0; i < sc.getCousrseId().size(); i++) {
				courses.add(sc.getCousrseId().get(i));
				marks.add(sc.getMarks().get(i));
				grades.add(sc.getGrade().get(i));
				results.add(sc.getResult().get(i));
				
			}
			data.addAll(courses);
			data.add("-");
			data.addAll(marks);
			data.add("-");
			data.addAll(grades);
			data.add("-");
			data.addAll(results);
			data.add("-");
			data.add("sgpa"+"-"+sc.getSgpa());
			data.add("-");
			data.add("cgpa"+"-"+sc.getCgpa());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public List<String> getAnnouncmentsService() throws Exception{
		List<String> announcments=new ArrayList<String>();
		StudentDAOImpl dao=Factory.createStudentDAO();
		try{
			List<AnnouncmentEntity> aes = dao.getAnnouncments();
			for(int i=0;i<aes.size();i++){
				announcments.add(aes.get(i).getAnnouncment());
			}
			System.out.println(announcments);
		}catch(Exception e){
			
		}return announcments;
	}

	public String getSingleQuote() throws Exception {
		StudentDAOImpl dao=Factory.createStudentDAO();
		String quote=null;
		try{
			List<QuoteEntity> se = dao.getSingleQuote();
			if(se.size() == 1){
				quote=se.get(0).getQuote();
			}else{
				System.out.println("se size greater than 1");
			}
		}catch(Exception e){
			
		}
		return quote;
	}
}
