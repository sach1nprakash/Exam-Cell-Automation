package com.my.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.my.bean.HallTicket;
import com.my.bean.Queries;
import com.my.bean.Results;
import com.my.bean.Student;
import com.my.bean.StudentCourse;
import com.my.entity.AnnouncmentEntity;
import com.my.entity.BacklogScheduleEntity;
import com.my.entity.CourseEntity;
import com.my.entity.QueryEntity;
import com.my.entity.QuoteEntity;
import com.my.entity.ResultEntity;
import com.my.entity.ScheduleEntity;
import com.my.entity.StudentCourseEntity;
import com.my.entity.StudentEntity;
import com.my.resources.HibernateUtility;

public class StudentDAOImpl implements StudentDAO {
	private SessionFactory sessionFactory;

	public StudentDAOImpl() throws Exception {
		sessionFactory = HibernateUtility.createSessionFactory();
	}

	public Student getStudentDetails(String studentId) throws Exception {

		Session session = null;
		Student student = null;

		try {

			session = sessionFactory.openSession();
			StudentEntity se = (StudentEntity) session.get(StudentEntity.class,
					studentId);
			if (se != null) {
				student = new Student();
				student.setStudentId(se.getStudentId());
				student.setDateOfBirth(se.getDateOfBirth());
				System.out.println(se.getDateOfBirth());
				student.setStudentName(se.getStudentName());
				System.out.println(se.getStudentName());
				student.setBranch(se.getBranch());
				student.setSem(se.getSem());
				student.setEmail(se.getEmail());
				student.setCgpa(se.getCgpa());
			}
		} catch (HibernateException exception) {
			exception.printStackTrace();
			DOMConfigurator.configure("src/resources/log4j.xml");
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(exception.getMessage(), exception);
			throw new Exception("DAO.TECHNICAL_ERROR");
		} catch (Exception exception) {
			exception.printStackTrace();
			DOMConfigurator.configure("src/resources/log4j.xml");
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(exception.getMessage(), exception);
			throw exception;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return student;
	}

	public List<StudentCourse> getregisteredCourses(Student student)
			throws Exception {
		Session session = null;
		List<StudentCourseEntity> sce = new ArrayList<StudentCourseEntity>();
		List<StudentCourse> sc = new ArrayList<StudentCourse>();
		String hql = "from StudentCourseEntity where studentId='"
				+ student.getStudentId() + "'";
		try {
			session = sessionFactory.openSession();
			Query q = session.createQuery(hql);
			sce = q.list();
			if (sce != null) {
				for (StudentCourseEntity i : sce) {
					System.out.println(i.getCourseId());
					StudentCourse scs = new StudentCourse();
					scs.setCourseId(i.getCourseId());
					scs.setBranch(i.getBranch());
					scs.setScheduleStatus(i.getScheduleStatus());
					scs.setSemester(i.getSemester());
					scs.setStudentId(i.getStudentId());
					sc.add(scs);
				}
			} else
				System.out.println("sce is null");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return sc;
	}

	@SuppressWarnings("unchecked")
	public Map<String, List<CourseEntity>> getCourses(int semester,
			String branch, String studentId) throws Exception {

		Session session = null;
		List<CourseEntity> courseEntities = new ArrayList<CourseEntity>();
		StudentEntity studentEntity = null;
		String hql = "from CourseEntity ce where ce.semester='" + semester
				+ "' and ce.branch='" + branch + "'";
		Map<String, List<CourseEntity>> courses_map = new HashMap<String, List<CourseEntity>>();
		try {
			System.out.println("before session");

			session = sessionFactory.openSession();
			System.out.println("after session");
			Query q = session.createQuery(hql);

			courseEntities = q.list();

			if (courseEntities.isEmpty())
				System.out.println("empty");

			studentEntity = (StudentEntity) session.get(StudentEntity.class,
					studentId);
			List<CourseEntity> backlogs = new ArrayList<CourseEntity>();
			backlogs = studentEntity.getBacklogs();

			for (CourseEntity i : backlogs) {
				System.out.println("from DAO,backlogs" + " " + i.getCourseId());
			}

			courses_map.put("currentCourses", courseEntities);
			courses_map.put("backlog", backlogs);

		} catch (HibernateException exception) {
			exception.printStackTrace();
			DOMConfigurator.configure("src/resources/log4j.xml");
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(exception.getMessage(), exception);
			throw new Exception("DAO.TECHNICAL_ERROR");
		} catch (Exception exception) {
			exception.printStackTrace();
			DOMConfigurator.configure("src/resources/log4j.xml");
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(exception.getMessage(), exception);
			throw exception;
		} finally {
			if (session != null)
				session.close();
		}
		return courses_map;
	}

	public List<Student> getAllStudents() throws Exception {
		Session session = null;
		System.out.println("in getAllstudents");
		List<StudentEntity> se = new ArrayList<StudentEntity>();
		List<Student> students = new ArrayList<Student>();
		String hql = "from StudentEntity";
		try {
			session = sessionFactory.openSession();
			Query q = session.createQuery(hql);
			se = q.list();
			if (se != null) {
				for (StudentEntity i : se) {
					Student student = new Student();
					student.setStudentId(i.getStudentId());
					student.setDateOfBirth(i.getDateOfBirth());
					student.setStudentName(i.getStudentName());
					student.setBranch(i.getBranch());
					student.setSem(i.getSem());
					student.setEmail(i.getEmail());
					student.setCostOfExams(i.getCostofexams());
					student.setDdNumber(i.getDdnumber());
					students.add(student);
				}
			} else
				System.out.println("se is null");
		} catch (Exception exception) {
		} finally {
			session.close();
		}
		return students;
	}

	public boolean setCourses(Student student) throws Exception {

		Session session = null;
		boolean flag = true;
		System.out.println("in setCourses");
		try {
			session = sessionFactory.openSession();
			System.out.println("set Courses " + student.getStudentId());
			StudentEntity se = (StudentEntity) session.get(StudentEntity.class,
					student.getStudentId());
			if (se != null) {
				session.getTransaction().begin();
				for (CourseEntity i : student.getCourses()) {
					System.out.println(i.getCourseId() + " " + i.getSemester());
				}

				se.setCourses(student.getCourses());
				se.setCostofexams(student.getCostOfExams());
				se.setDdnumber(student.getDdNumber());
				session.getTransaction().commit();
			} else
				System.out.println("se is null");

			StudentEntity see = (StudentEntity) session.get(
					StudentEntity.class, student.getStudentId());
			if (see.getCourses().isEmpty())
				flag = false;
		} catch (Exception exception) {
			exception.printStackTrace();
			DOMConfigurator.configure("src/log4j.xml");
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(exception.getMessage(), exception);
			throw exception;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return flag;
	}

	public HallTicket getHallTicket(Student student) throws Exception {
		Session session = null;
		System.out.println("in getHall ticket");
		String scheduleId = student.getBranch().toUpperCase()
				+ student.getSem();
		String hql1 = "from ScheduleEntity where scheduleId='" + scheduleId
				+ "'";

		List<ScheduleEntity> se = new ArrayList<ScheduleEntity>();

		HallTicket ht = new HallTicket();
		List<String> courseId = new ArrayList<String>();
		List<String> dates = new ArrayList<String>();
		List<String> timings = new ArrayList<String>();

		try {

			session = sessionFactory.openSession();
			Query q = session.createQuery(hql1);
			se = q.list();

			if (se.size()!=0) {
				for (ScheduleEntity i : se) {
					courseId.add(i.getCourseId().toString());
					System.out.println(i.getCourseId());
					dates.add(i.getDateofexam().toString());
					timings.add(i.getTiming().toString());
				}
			} else
				System.out.println("se is null");

			ht.setCourseId(courseId);
			ht.setDate(dates);
			ht.setTime(timings);
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			session.close();

		}
		return ht;
	}

	public HallTicket getHallTicketBacklogs(Student student) throws Exception {
		Session session = null;
		System.out.println("in getHall ticket of backlogs");
		System.out.println(student.getStudentId());
		String hql2 = "from BacklogScheduleEntity where studentId='"
				+ student.getStudentId() + "'";
		List<BacklogScheduleEntity> bse = new ArrayList<BacklogScheduleEntity>();

		HallTicket ht1 = new HallTicket();
		List<String> courseId = new ArrayList<String>();
		List<String> dates = new ArrayList<String>();
		List<String> timings = new ArrayList<String>();
		try {
			session = sessionFactory.openSession();
			Query q1 = session.createQuery(hql2);
			bse = q1.list();
			if (bse.size()!=0) {
				System.out.println("bse is not null");
				for (BacklogScheduleEntity j : bse) {
					courseId.add(j.getCourseId().toString());
					System.out.println(j.getCourseId());
					dates.add(j.getDates().toString());
					timings.add(j.getTiming().toString());
				}
			} else
				System.out.println("bse is null");

			ht1.setCourseId(courseId);
			ht1.setDate(dates);
			ht1.setTime(timings);
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			session.close();
		}
		return ht1;
	}

	public void askQuery(Queries query) throws Exception {
		Session session = null;
		try {
			System.out.println("Inside query dao");
			session = sessionFactory.openSession();
			QueryEntity qe = new QueryEntity();
			session.getTransaction().begin();
			qe.setStudentId(query.getStudentId());
			qe.setEmail(query.getEmail());
			qe.setMesssage(query.getMesssage());
			session.persist(qe);
			session.getTransaction().commit();
			System.out.println("Exiting query dao");
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			session.close();
		}
	}

// ------------------------------------------------------------------------------------------
	public Results getMarksofStudent(String studentId,Integer sem) throws Exception {
		Session session = null;
		Results result = null;
		String hql = "from ResultEntity where studentId='" + studentId
				+ "' and sem='"+sem+"'";
		List<ResultEntity> sce = new ArrayList<ResultEntity>();
		List<String> resCourseId = new ArrayList<String>();
		List<String> resMarks = new ArrayList<String>();
		List<String> resGrade = new ArrayList<String>();
		List<String> resResult = new ArrayList<String>();
		try {
			session = sessionFactory.openSession();
			Query q = session.createQuery(hql);
			sce = q.list();
			if (sce != null) {
				result = new Results();
				result.setStudentId(studentId);
				
				for (ResultEntity i : sce) {
					resCourseId.add(i.getCourseId());
					resMarks.add(i.getMarks().toString());
					resGrade.add(i.getGrade());
					resResult.add(i.getResult());
					result.setSgpa(i.getSgpa());
					result.setCgpa(i.getCgpa());
				}
				result.setCousrseId(resCourseId);
				result.setMarks(resMarks);
				result.setGrade(resGrade);
				result.setResult(resResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return result;
	}
	
	public List<AnnouncmentEntity> getAnnouncments() throws Exception{
		List<AnnouncmentEntity> aes = new ArrayList<AnnouncmentEntity>();
		String hql="from AnnouncmentEntity";
		Session session=null;
		try{
			session=sessionFactory.openSession();
			Query q = session.createQuery(hql);
			aes = q.list();
			if(aes.size() == 0)
				System.out.println("aes is null");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}return aes;
	}

	public List<QuoteEntity> getSingleQuote() {
		List<QuoteEntity> sqe = new ArrayList<QuoteEntity>();
		String fixed="fixed";
		String hql="from QuoteEntity where status='"+fixed+"'";
		Session session=null;
		try{
			session=sessionFactory.openSession();
			Query q = session.createQuery(hql);
			sqe = q.list();
			if(sqe.size() == 0)
				System.out.println("aes is null");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}return sqe;
	}
}
