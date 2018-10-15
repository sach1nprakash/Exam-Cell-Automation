package com.my.service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.my.bean.BacklogSchedule;
import com.my.bean.Course;
import com.my.bean.Queries;
import com.my.bean.Results;
import com.my.bean.SetSchedule;
import com.my.bean.Student;
import com.my.bean.StudentCourse;
import com.my.dao.AdminDAOImpl;
import com.my.dao.StudentDAOImpl;
import com.my.entity.BacklogEntity;
import com.my.entity.CourseEntity;
import com.my.entity.QuoteEntity;
import com.my.entity.ResultEntity;
import com.my.resources.Factory;

public class AdminServiceImpl implements AdminService {

	public List<String> getMandatoryCourses(Integer semester, String branch)
			throws Exception {
		AdminDAOImpl dao = new AdminDAOImpl();
		List<Course> courses = null;
		List<String> subjects = new ArrayList<String>();
		System.out.println("In service");
		String branch1 = branch.toLowerCase();
		courses = dao.getMandatoryCourses(semester, branch1);
		for (Course i : courses) {
			subjects.add(i.getCourseId());
		}
		return subjects;
	}

	public boolean setMandatoryCourses(SetSchedule ss) throws Exception {
		AdminDAOImpl dao = new AdminDAOImpl();
		System.out.println("in service 2");
		boolean flag1 = false;
		try {
			flag1 = dao.setScheduleMandatoryCousrses(ss);
			System.out.println("comming from dao");
		} catch (Exception e) {

		}
		return flag1;
	}

	public List<String> getrequestsfromdb() throws Exception {
		AdminDAOImpl dao1 = new AdminDAOImpl();
		StudentDAOImpl dao = Factory.createStudentDAO();
		List<String> list = new ArrayList<String>();
		try {
			List<StudentCourse> scb = dao1.getrequests();
			List<Student> students = dao.getAllStudents();
			// sfor(Student j:students){
			// System.out.println("students"+j.getStudentId());
			// }
			// for(StudentCourse i:scb){
			// System.out.println("registeredStudents"+i.getStudentId());
			// }
			int flag=1;

			for (Student j : students) {
				System.out.println("students " + j.getStudentId());
				for (StudentCourse i : scb) {
					System.out
							.println("registeredStudents " + i.getStudentId());
					if (j.getStudentId().equals(i.getStudentId())) {
						System.out.println("student Id's are equal"
								+ j.getStudentId() + " " + i.getStudentId());
						System.out.println("  semseters " + j.getSem() + " "
								+ i.getSemester() + " " + i.getCourseId());
						if (j.getSem() > i.getSemester()) {
							list.add(i.getStudentId() + "-" + i.getCourseId()
									+ "-" + j.getDdNumber() + "-"
									+ j.getCostOfExams());
							System.out.println(i.getStudentId() + "-"
									+ i.getCourseId());
						}
					}
				}
			}
			

		} catch (Exception e) {
		}
		return list;
	}

	public void setBacklogs(BacklogSchedule schedule) throws Exception {
		AdminDAOImpl dao = new AdminDAOImpl();
		try {
			dao.setScheduleBacklogCousrses(schedule);
		} catch (Exception exception) {

		}
	}

	public List<String> getqueries() throws Exception {
		AdminDAOImpl dao = new AdminDAOImpl();
		List<String> list = new ArrayList<String>();
		try {
			List<Queries> qs = dao.getQueries();
			if (qs != null) {
				for (Queries i : qs) {
					String ss = i.getStudentId() + "``" + i.getMesssage()
							+ "``" + i.getEmail();
					list.add(ss);
					System.out.println(ss);
				}
			}
		} catch (Exception e) {

		}
		return list;
	}

	// -------------------------------------------------------------------------------------------------
	public List<String> getCoursesForMarks(String sem) throws Exception {
		AdminDAOImpl dao = new AdminDAOImpl();
		List<String> registeredCourses = new ArrayList<String>();
		try {
			int semester = Integer.parseInt(sem);
			List<StudentCourse> scList = dao.getRegisteredCourses(semester);
			if (scList != null) {
				for (StudentCourse i : scList) {
					registeredCourses.add(i.getStudentId() + "-"
							+ i.getCourseId());
					System.out
							.println(i.getStudentId() + "-" + i.getCourseId());
				}
			}
		} catch (Exception e) {

		}
		return registeredCourses;
	}

	public void setMarksForCourses(Results result) throws Exception {
		AdminDAOImpl dao = new AdminDAOImpl();
		StudentDAOImpl dao1 = new StudentDAOImpl();
		List<ResultEntity> rel = new ArrayList<ResultEntity>();
		float sgpa = 0;
		float cgpa;
		try {
			Student se = dao1.getStudentDetails(result.getStudentId());
			System.out.println(se.getSem() + "  students semester");
			System.out.println("courseId :" + result.getCousrseId());
			System.out.println("marks :" + result.getMarks());
			System.out.println("grades :" + result.getGrade());
			System.out.println("result :" + result.getResult());
			cgpa=Float.parseFloat(se.getCgpa());
			for (String i : result.getMarks()) {
				sgpa = sgpa + Float.parseFloat(i);
				System.out.println(sgpa);
			}
			System.out.println(sgpa);
			System.out.println("size of list :"+result.getMarks().size());
			int total=result.getMarks().size()*100;
			System.out.println("total :"+total);
			sgpa = (sgpa/total)*10;
			Float sg=sgpa;
			System.out.println("sg :"+sg);
			System.out.println("sgpa :"+sgpa);
			System.out.println("cgpa :"+cgpa);
			System.out.println(cgpa*(se.getSem()-1)+sgpa);
			cgpa = (cgpa*(se.getSem()-1)+sgpa)/se.getSem();
			System.out.println(cgpa);
			Float cg=cgpa;
			System.out.println(cg);
			se.setCgpa(cg.toString());
			for (int i = 0; i < result.getCousrseId().size(); i++) {
				ResultEntity re = new ResultEntity();
				re.setStudentId(result.getStudentId());
				re.setCourseId(result.getCousrseId().get(i));
				re.setMarks(Integer.parseInt(result.getMarks().get(i)));
				re.setGrade(result.getGrade().get(i));
				re.setResult(result.getResult().get(i));
				re.setSem(se.getSem());
				re.setSgpa(sg.toString());
				re.setCgpa(cg.toString());
				rel.add(re);
			}

			System.out.println("dao.setMarksForRcourses()");
			dao.setMarksForRcourses(rel);
			System.out.println("dao.setCgpaOfStudent()");
			dao.setCgpaOfStudent(se);
			System.out.println("updateBackLogs()");
			updateBackLogs(result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateBackLogs(Results result) throws Exception {
		StudentDAOImpl dao = new StudentDAOImpl();
		AdminDAOImpl dao1 = new AdminDAOImpl();
		List<BacklogEntity> backlogs = null;
		List<StudentCourse> courses = null;
		int number = 0;
		try {
			System.out.println("in updateBackLogs");
			Student student = new Student();
			student.setStudentId(result.getStudentId());
			// Student student = dao.getStudentDetails(result.getStudentId());
			System.out.println("dsjhvbav");
			backlogs = dao1.getbacklogsofstudent(result.getStudentId());
			courses = dao.getregisteredCourses(student);

			for (int i = 0; i < result.getCousrseId().size(); i++) {
				if (Integer.parseInt(result.getMarks().get(i)) > 40) {
					System.out.println(result.getMarks().get(i));
					int flag = 1;
					BacklogEntity ce = null;
					for (BacklogEntity j : backlogs) {
						if (result.getCousrseId().get(i)
								.equals(j.getCourseId())) {
							System.out.println("greater than 40 :"
									+ result.getCousrseId().get(i) + " "
									+ j.getCourseId());
							flag = 0;
							ce = j;
						}
					}
					if (flag == 0) {
						// student.getBacklogs().remove(ce);
						System.out.println(ce.getCourseId());
						dao1.deleteFromBacklog(ce);
					}
				} else {
					int flag = 1;
					for (BacklogEntity j : backlogs) {
						if (result.getCousrseId().get(i)
								.equals(j.getCourseId())) {
							flag = 0;
							System.out.println("less than 40 :"
									+ result.getCousrseId().get(i) + " "
									+ j.getCourseId());
						}
					}
					if (flag == 1) {
						StudentCourse ce = null;
						for (StudentCourse j : courses) {
							if (result.getCousrseId().get(i)
									.equals(j.getCourseId())) {
								ce = j;
							}
						}
						// student.getBacklogs().add(ce);
						dao1.addtoBacklogs(ce);
					}
				}
				number = number + 1;
			}
			// if (number == result.getCousrseId().size())
			// dao1.setBacklogsforStudents(student);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void makAnnouncments(String announcement) throws Exception {
		AdminDAOImpl dao = new AdminDAOImpl();
		try {
			 Date currentDate = new Date();
			 String dateToStr = DateFormat.getInstance().format(currentDate);
			 String data = announcement+"``"+dateToStr.substring(0, 6);
			dao.makeAnnouncment(data);
		} catch (Exception e) {

		}
	}

	public List<String> getQuotes() throws Exception {
		List<String> quotes=new ArrayList<String>();
		AdminDAOImpl dao = new AdminDAOImpl();
		try {
			List<QuoteEntity> qe=dao.getQuotes();
			for(int i=0;i<qe.size();i++){
				quotes.add(qe.get(i).getQuote());
			}
			System.out.println(quotes);
		} catch (Exception e) {

		}
		return quotes;
	}

	public void setQuotes(String message) throws Exception {
		AdminDAOImpl dao = new AdminDAOImpl();
		try {
			dao.setQuotesDao(message);
		}catch (Exception e) {

		}
		
	}

}
