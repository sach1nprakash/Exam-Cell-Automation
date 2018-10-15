package com.my.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.my.bean.BacklogSchedule;
import com.my.bean.Course;
import com.my.bean.Queries;
import com.my.bean.SetSchedule;
import com.my.bean.Student;
import com.my.bean.StudentCourse;
import com.my.entity.AnnouncmentEntity;
import com.my.entity.BacklogEntity;
import com.my.entity.BacklogScheduleEntity;
import com.my.entity.Backlog_PK;
import com.my.entity.CourseEntity;
import com.my.entity.QueryEntity;
import com.my.entity.QuoteEntity;
import com.my.entity.ResultEntity;
import com.my.entity.ScheduleEntity;
import com.my.entity.StudentCourseEntity;
import com.my.entity.StudentCourse_PK;
import com.my.entity.StudentEntity;
import com.my.resources.HibernateUtility;

public class AdminDAOImpl implements AdminDAO {

	private SessionFactory sessionFactory1;

	public AdminDAOImpl() throws Exception {
		sessionFactory1 = HibernateUtility.createSessionFactory();
	}

	public List<Course> getMandatoryCourses(Integer semester, String branch)
			throws Exception {
		Session session = null;
		List<Course> mandatoryCourses = new ArrayList<Course>();
		List<CourseEntity> courseEntities = new ArrayList<CourseEntity>();
		String hql = "from CourseEntity ce where ce.semester='" + semester
				+ "' and ce.branch='" + branch + "'";
		try {
			if (HibernateUtility.createSessionFactory() != null)
				System.out.println("session factory is not null");
			else
				System.out.println("session factory is null");
			System.out.println("in dao");
			session = sessionFactory1.openSession();
			System.out.println("create obj");
			Query q = session.createQuery(hql);
			System.out.println("obj creaed");
			courseEntities = q.list();
			System.out.println("CourseEntitres");

			if (courseEntities != null) {
				System.out.println("not null");
			} else
				System.out.println("courseEntities is null");

			if (courseEntities != null) {
				for (CourseEntity i : courseEntities) {
					Course courses = new Course();
					courses.setCourseId(i.getCourseId());
					courses.setCourseName(i.getCourseName());
					courses.setCourseType(i.getCourseType());
					courses.setBranch(i.getBranch());
					courses.setSemester(i.getSemester());
					mandatoryCourses.add(courses);
					System.out.println(courses.getCourseId() + " added");
				}
			} else
				System.out.println("CourseEntity is empty");

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			session.close();
		}

		return mandatoryCourses;
	}

	public boolean setScheduleMandatoryCousrses(SetSchedule schedule) {

		Session session = null;
		boolean flag = true;
		List<ScheduleEntity> se = new ArrayList<ScheduleEntity>();
		String hql = "from ScheduleEntity";
		try {
			System.out.println("in dao before session");
			session = sessionFactory1.openSession();
			System.out.println("after session");
			List<String> sub = schedule.getSubjects();
			List<String> date = schedule.getDate();
			List<String> time = schedule.getTime();
			for (int i = 0; i < sub.size(); i++) {
				ScheduleEntity scheduleEntity = new ScheduleEntity();
				System.out.println("In dao setschedule" + sub.get(i));
				scheduleEntity.setScheduleId(schedule.getBranch()
						+ schedule.getSem());
				scheduleEntity.setCourseId(sub.get(i));
				scheduleEntity.setDateofexam(date.get(i));
				scheduleEntity.setTiming(time.get(i));
				session.getTransaction().begin();
				System.out.println("sai");
				session.persist(scheduleEntity);
				System.out.println("ssormhsd");
				session.getTransaction().commit();
			}

			Query q = session.createQuery(hql);
			se = q.list();
			if (se != null) {
				for (ScheduleEntity i : se) {
					System.out.println(i.getCourseId() + " "
							+ i.getDateofexam() + " " + i.getTiming());
				}
			} else {
				flag = false;
				System.out.println("se is empty");
			}
		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			session.close();
		}
		return flag;
	}

	public List<StudentCourse> getrequests() {
		Session session = null;
		String hql = "from StudentCourseEntity where scheduleStatus='null'";
		List<StudentCourseEntity> sce = new ArrayList<StudentCourseEntity>();
		List<StudentCourse> scb = new ArrayList<StudentCourse>();
		try {
			System.out.println("in dao");
			session = sessionFactory1.openSession();
			Query q = session.createQuery(hql);
			sce = q.list();
			if (sce == null)
				System.out.println("sce is null");
			else {
				for (StudentCourseEntity i : sce) {
					StudentCourse sc = new StudentCourse();
					sc.setStudentId(i.getStudentId());
					sc.setBranch(i.getBranch());
					sc.setCourseId(i.getCourseId());
					sc.setSemester(i.getSemester());
					sc.setScheduleStatus(i.getScheduleStatus());
					scb.add(sc);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return scb;
	}

	public List<BacklogEntity> getbacklogsofstudent(String studentId) {
		SessionFactory sessionFactory = null;
		Session session = null;
		String hql = "from BacklogEntity where studentId='" + studentId + "'";
		List<BacklogEntity> sce = new ArrayList<BacklogEntity>();

		try {

			session = sessionFactory1.openSession();

			Query q = session.createQuery(hql);
			sce = q.list();
			if (sce.size() != 0) {
				System.out.println("sce is not null");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return sce;
	}

	public void setScheduleBacklogCousrses(BacklogSchedule schedule) {

		Session session = null;
		boolean flag = true;
		List<BacklogScheduleEntity> se = new ArrayList<BacklogScheduleEntity>();
		String hql = "from BacklogScheduleEntity";
		try {

			session = sessionFactory1.openSession();
			List<String> sub = schedule.getCourseId();
			List<String> date = schedule.getDate();
			List<String> time = schedule.getTiming();
			for (int i = 0; i < sub.size(); i++) {
				BacklogScheduleEntity scheduleEntity = new BacklogScheduleEntity();
				System.out.println("In dao setschedule" + sub.get(i));
				scheduleEntity.setStudentId(schedule.getStudentId());
				;
				scheduleEntity.setCourseId(sub.get(i));
				scheduleEntity.setDates(date.get(i));
				scheduleEntity.setTiming(time.get(i));
				session.getTransaction().begin();
				System.out.println("sai");
				session.persist(scheduleEntity);
				System.out.println("ssormhsd");
				session.getTransaction().commit();
			}
			Query q = session.createQuery(hql);
			se = q.list();
			if (se != null) {
				for (BacklogScheduleEntity i : se) {
					System.out.println(i.getStudentId() + " " + i.getCourseId()
							+ " " + i.getDates() + " " + i.getTiming());
				}
			} else {
				flag = false;
				System.out.println("se is empty");
			}
			String hql2 = "from StudentCourseEntity where studentId='"
					+ schedule.getStudentId() + "'";
			Query q1 = session.createQuery(hql2);
			List<StudentCourseEntity> sce = new ArrayList<StudentCourseEntity>();
			sce = q1.list();
			if (sce != null) {
				session.getTransaction().begin();
				for (StudentCourseEntity i : sce) {
					System.out.println(i.getStudentId() + "---"
							+ i.getCourseId());
					i.setScheduleStatus("approved");
				}
				session.getTransaction().commit();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			session.close();
		}
	}

	public List<Queries> getQueries() {
		Session session = null;
		List<QueryEntity> qe = new ArrayList<QueryEntity>();
		List<Queries> qs = new ArrayList<Queries>();
		String hql = "from QueryEntity";
		try {
			System.out.println("in dao getQueries");
			session = sessionFactory1.openSession();
			Query q = session.createQuery(hql);
			qe = q.list();
			if (qe != null) {
				for (QueryEntity i : qe) {
					Queries qes = new Queries();
					qes.setStudentId(i.getStudentId());
					qes.setMesssage(i.getMessage());
					qes.setEmail(i.getEmail());
					qs.add(qes);
				}
			}
			for (Queries i : qs) {
				System.out.println(i.getStudentId() + "  " + i.getMesssage());
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return qs;
	}

	// -----------------------------------------------------------------------------------------------

	public List<StudentCourse> getRegisteredCourses(int sem) throws Exception {
		Session session = null;
		String hql = "from StudentCourseEntity where scheduleStatus='approved'";
		String hql2 = "from StudentEntity where sem='" + sem + "'";
		List<StudentCourseEntity> sce = new ArrayList<StudentCourseEntity>();
		List<StudentCourse> scb = new ArrayList<StudentCourse>();
		List<StudentEntity> entityList = new ArrayList<StudentEntity>();
		try {
			System.out.println("in dao");
			session = sessionFactory1.openSession();
			Query q = session.createQuery(hql);
			sce = q.list();

			Query q1 = session.createQuery(hql2);
			entityList = q1.list();

			if (entityList != null && sce != null) {
				for (StudentEntity j : entityList) {
					for (StudentCourseEntity i : sce) {
						if (j.getStudentId().equals(i.getStudentId())) {
							StudentCourse sc = new StudentCourse();
							sc.setStudentId(i.getStudentId());
							sc.setBranch(i.getBranch());
							sc.setCourseId(i.getCourseId());
							sc.setSemester(i.getSemester());
							sc.setScheduleStatus(i.getScheduleStatus());
							scb.add(sc);
						}
					}
				}
			} else
				System.out.println("sce is null");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return scb;
	}

	// check in db if works or not
	public void setMarksForRcourses(List<ResultEntity> sc) throws Exception {
		Session session = null;
		System.out.println("in setMarksForRcourses dao");
		try {
			session = sessionFactory1.openSession();
			session.getTransaction().begin();
			for (ResultEntity i : sc) {
				session.persist(i);
			}
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}


	public void setCgpaOfStudent(Student se) throws Exception {
		Session session = null;
		boolean flag = true;
		System.out.println("in setCourses");
		try {
			session = sessionFactory1.openSession();
			StudentEntity se1 = (StudentEntity) session.get(
					StudentEntity.class, se.getStudentId());
			session.getTransaction().begin();
			se1.setCgpa(se.getCgpa());
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void deleteFromBacklog(BacklogEntity ce) throws Exception {
		Session session = null;
		try {
			System.out.println("in deleteFromBacklog :"+ce.getCourseId());
			session = sessionFactory1.openSession();
			Backlog_PK scpk = new Backlog_PK();
			scpk.setStudentId(ce.getStudentId());
			scpk.setCourseId(ce.getCourseId());
			BacklogEntity sce = (BacklogEntity) session.get(
					BacklogEntity.class, scpk);

			if (sce != null) {
				session.getTransaction().begin();
				session.delete(sce);
				session.getTransaction().commit();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}

	}

	public void addtoBacklogs(StudentCourse ce) throws Exception{
		Session session=null;
		try{
			System.out.println("in addtoBacklogs :"+ce.getCourseId());
			session = sessionFactory1.openSession();
			BacklogEntity be = new BacklogEntity();
			be.setStudentId(ce.getStudentId());
			be.setCourseId(ce.getCourseId());
			be.setBranch(ce.getBranch());
			be.setSemester(ce.getSemester());
			session.getTransaction().begin();
			session.persist(be);
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{session.close();}
	}
	
	public void makeAnnouncment(String announcement) throws Exception{
		Session session=null;
		try{
			System.out.println("in dao makeAnnouncment");
			session = sessionFactory1.openSession();
			AnnouncmentEntity ae = new AnnouncmentEntity();
			ae.setAnnouncment(announcement);
			session.getTransaction().begin();
			session.persist(ae);
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
	}

	public List<QuoteEntity> getQuotes() {
		Session session = null;
		String hql="from QuoteEntity";
		List<QuoteEntity> qe = new ArrayList<QuoteEntity>();
		try{
			session=sessionFactory1.openSession();
			Query q= session.createQuery(hql);
			qe = q.list();
			if(qe.size()!=0){
				for(QuoteEntity i:qe){
					System.out.println(i);
				}
			}
		}catch(Exception e){
			
		}finally{
			session.close();
		}
		return qe;
	}

	public void setQuotesDao(String message) {
		Session session=null;
		List<QuoteEntity> sqe = new ArrayList<QuoteEntity>();
		String fixed="fixed";
		String hql="from QuoteEntity where status='"+fixed+"'";
		try{
			System.out.println("in dao single quote");
			System.out.println(message);
			session = sessionFactory1.openSession();
			Query q=session.createQuery(hql);
			sqe=q.list();
			session.getTransaction().begin();
			if(sqe.size()!=0){
				for(QuoteEntity i:sqe){
					i.setStatus("not accepted");
				}
			}
			QuoteEntity qe=(QuoteEntity)session.get(QuoteEntity.class, message);
			qe.setStatus("fixed");
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		
	}
	
}
