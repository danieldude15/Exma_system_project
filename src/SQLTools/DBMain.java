package SQLTools;

import com.mysql.jdbc.Statement;
import logic.*;
import ocsf.server.ServerGlobals;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


public class DBMain implements IDBMain{
	private String host;
	private String user;
	private String pass;
	private Connection conn;

	private String getStudentsCourses= new String(""
			+ "SELECT c.courseid, c.coursename, f.fieldid, f.fieldname "
			+ "FROM aes.courses as c, aes.fields as f,aes.student_in_course as sic , aes.users as u "
			+ "WHERE sic.studentid=u.userid and c.fieldid=f.fieldid and c.courseid=sic.courseid and sic.fieldid=f.fieldid and u.userid=?");
	private String teacherFields = new String(
			"SELECT distinct (f.fieldid),f.fieldname "
			+ "FROM aes.teacher_fields as tf,fields as f "
			+ "WHERE tf.fieldid=f.fieldid and tf.teacherid=?");
	private String getStudentsInCourse = new String(""
			+ "SELECT * " + 
			"FROM aes.student_in_course as sic, aes.users as u " + 
			"WHERE sic.studentid=u.userid and u.usertype=0 and sic.fieldid=? and sic.courseid=?");
	private String getAllUsersByType = new String(""
			+ "SELECT * FROM aes.users as u WHERE u.usertype=? ");
	private String getField = new String(""
			+ "SELECT * FROM fields WHERE fieldid=?");
	private String getCourse = new String(""
			+ "SELECT * from aes.courses as c WHERE c.courseid=? and c.fieldid=?");
	private String deleteExamReport = new String(""
			+ "DELETE FROM `aes`.`exams_report` "
			+ "WHERE `examid`=? and `courseid`=? and `fieldid`=? "
			+ "and `autherid`=? and `code`=? and `type`=? and `dateactivated`=?");
	private String questionCourses = new String(""
			+ "SELECT f.fieldid,f.fieldname,qic.courseid,c.coursename "
			+ "FROM aes.fields as f,aes.questions_in_course as qic, aes.courses as c "
			+ "WHERE c.fieldid=f.fieldid and c.courseid=qic.courseid and qic.fieldid=f.fieldid and qic.questionid=? and qic.fieldid=? ");
	private String deleteQuestion = new String(""
			+ "DELETE FROM aes.questions WHERE questionid=? and fieldid=?");
	private String deleteExam = new String(""
			+ "DELETE FROM aes.exams WHERE examid=? and fieldid=?"); 
	private String teachersQuestions = new String(
			"select * from aes.questions as q, aes.fields as f where q.fieldid=f.fieldid and q.teacherid=? " 
			);
	private String getTExamReports = new String(""
			+ "SELECT * FROM aes.exams_report where autherid=?");
	private String getAllExamReports = new String(""
			+ "SELECT * FROM aes.exams_report");
	private String getTeachrsSolvedExams = new String(""
			+ "SELECT  e.examid,e.fieldid,f.fieldname ,e.courseid,c.coursename, e.timeduration, " + 
			"		u.userid,u.username,u.password,u.fullname,se.answers, " + 
			"        se.examreportid,se.score,se.minutescompleted ,se.teacherapproved ,se.teacherschangescorenote " + 
			"FROM aes.users as u, aes.courses as c , aes.fields as f, aes.exams as e, aes.solved_exams as se " + 
			"where e.examid=se.examid and e.fieldid=se.fieldid and e.fieldid=f.fieldid and e.courseid=se.courseid "
			+ "and c.courseid=e.courseid and c.fieldid=e.fieldid and u.userid=se.studentid and e.teacherid=? "
			);
	private String getTeachrsExams = new String(""
			+ "SELECT e.examid , e.timeduration, c.courseid, c.coursename, f.fieldid, f.fieldname  "
			+ "FROM aes.exams as e, aes.courses as c, aes.fields as f "
			+ "WHERE c.courseid=e.courseid and f.fieldid=e.fieldid and c.fieldid=f.fieldid and teacherid=?");
	private String addExamReport = new String(""
			+ "INSERT INTO `aes`.`exams_report` "
			+ "(`examid`, `courseid`, `fieldid`, `autherid`, "
			+ "`activatorid`, `code`, `type`, `dateactivated`, "
			+ "`date_time_locked`, `participated`, `not_in_time_submitters`, "
			+ "`submitted`) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
			"");
	private String getcourseExams= new String(""
			+ "SELECT e.examid ,e.timeduration,e.teacherid,u.fullname,u.password,u.username "
			+" FROM aes.exams as e,aes.users as u "
		+" WHERE e.courseid=? and e.fieldid=? and e.teacherid=u.userid ");
	private String getExam = new String(""
			+ "SELECT e.examid , e.timeduration, c.courseid, c.coursename, f.fieldid, f.fieldname ,e.teacherid "
			+ "FROM aes.exams as e, aes.courses as c, aes.fields as f "
			+ "WHERE c.courseid=e.courseid and f.fieldid=e.fieldid and c.fieldid=f.fieldid and e.examid=? and f.fieldid=? and c.courseid=?");
	private String getFieldTeachers = new String(""
	+ "SELECT * FROM teacher_fields WHERE fieldid = ?");
	private String getSolvedExams = new String(""
			+ "SELECT e.examid,e.fieldid,f.fieldname ,e.courseid,c.coursename, e.timeduration," + 
			"		u.userid,u.username,u.password,u.fullname,se.answers," + 
			"        se.score,se.minutescompleted ,se.teacherapproved " + 
			"        ,se.teacherschangescorenote , u2.userid , " + 
			"        u2.username, u2.password, u2.fullname ,se.teacherquestionnote" +
			" FROM aes.users as u, aes.courses as c , aes.fields as f, " +
			"	aes.exams as e, aes.solved_exams as se , aes.exams_report as ce, aes.users as u2 " +
			" WHERE ce.examid=e.examid and ce.fieldid=e.fieldid and ce.courseid=e.courseid " +
			"	and u2.userid=e.teacherid and ce.autherid=e.teacherid and ce.code=se.code and e.examid=se.examid " +
			"    and e.fieldid=se.fieldid and e.fieldid=f.fieldid and e.courseid=se.courseid " +
			"    and c.courseid=e.courseid and c.fieldid=e.fieldid and u.userid=se.studentid " + 
			"    and se.code=? and se.courseid=? and se.fieldid=? and ce.examid=? and ce.type=? and ce.dateactivated=? and ce.autherid=? ");
	private String addQuestion = new String(""
			+ "INSERT INTO `aes`.`questions` "
			+ "(`questionid`,`question`, `answer1`, `answer2`, `answer3`, `answer4`, `answerindex`, `fieldid`, `teacherid`) "
			+ "VALUES ('0',?, ?, ?, ?, ?, ?, ?, ?);" + 
			"");
	private String editQuestion = new String(""
			+ "UPDATE `aes`.`questions` "
			+ "SET `question`=?, `answer1`=?, `answer2`=?, `answer3`=?, `answer4`=?, `answerindex`=? "
			+ "WHERE `questionid`=? and`fieldid`=?" + 
			"");
	private String addQuestionToCourse = new String(""
			+ "INSERT INTO `aes`.`questions_in_course` "
			+ "(`questionid`, `fieldid`, `courseid`) "
			+ "VALUES (?, ?, ?);\n" 
			);
	private String getStudentsSolvedExams=new String(""
			+ "SELECT  e.examid,e.fieldid,f.fieldname ,e.courseid,c.coursename, e.timeduration, " +
			" u.userid,u.username,u.password,u.fullname,se.answers, " +
			" se.score,se.minutescompleted ,se.teacherapproved ,se.teacherschangescorenote , se.code , se.dateinitiated, se.teacherquestionnote " +
			" FROM aes.users as u, aes.courses as c , aes.fields as f, aes.exams as e, aes.solved_exams as se " +
			" WHERE e.examid=se.examid and e.fieldid=se.fieldid and e.fieldid=f.fieldid and e.courseid=se.courseid " +
			" and c.courseid=e.courseid and c.fieldid=e.fieldid and u.userid=se.studentid and se.studentid=?"
			);
	private String addSolvedExam = new String(""
			+ "INSERT INTO `aes`.`solved_exams` "
			+ "(`examid`, `score`, `teacherapproved`, `answers`, `studentid`, `courseid`, `fieldid`, `teacherschangescorenote`, `minutescompleted`, `code`, `teacherquestionnote`,`dateinitiated`) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" +
			"");
	private String updateSolvedExam = new String(""
			+ "UPDATE `aes`.`solved_exams` "
			+ "SET `score`=?, `teacherapproved`=?, `teacherschangescorenote`=?, `teacherquestionnote`=? "
			+ "WHERE `studentid`=? and `examid`=? and `courseid`=? and`fieldid`=? and `code`=? " +
			"");
	private String getQuestionsInExam= new String(""
			+ "SELECT * " + 
			"FROM aes.questions as q,aes.questions_in_exam as qe, aes.exams as e,aes.users as u, aes.fields as f " + 
			"WHERE e.examid=qe.examid and u.userid=q.teacherid and q.questionid=qe.questionid and f.fieldid=q.fieldid "
			+ "and q.fieldid=qe.fieldid and e.fieldid=q.fieldid and e.courseid=qe.courseid "
			+ "and e.examid=? and e.fieldid=? and e.courseid=?");
	private String CourseQuestions=new String(""
		   +"Select q.fieldid,q.questionid,q.question,q.answer1,q.answer2,q.answer3,q.answer4,q.answerindex, " +
			"			u.fullname,u.password,u.userid,u.username,f.fieldname\n" +
			"			FROM aes.questions as q, aes.questions_in_course as qc,aes.fields as f,aes.courses as c,aes.users as u " +
			"						 where c.courseid=qc.courseid and f.fieldid=c.fieldid and c.fieldid=qc.fieldid  " +
			"			             and  qc.questionid=q.questionid and c.fieldid=q.fieldid " +
			"			             and u.userid=q.teacherid and c.courseid=? and f.fieldid=?");
	private String addexam = new String(""
			+ "INSERT INTO `aes`.`exams` "
			+ "(`examid`,`timeduration`, `fieldid`, `courseid`,`teacherid`) "
			+ "VALUES ('0',?,?,?,?)");
	private String addQuestionInExam = new String(""
			+ "INSERT INTO `aes`.`questions_in_exam` "
			+ "(`questionid`,`examid`,`pointsvalue`,`courseid`,`fieldid`,`innernote`,`studentnote` ) "
			+ "VALUES (?,?,?,?,?,?,?)");
	private String getAllQuestions = new String(""
			+ "SELECT * FROM aes.questions ");
	private String getAllExams = new String(""
			+ "SELECT * FROM aes.exams");
	private String getAllFields = new String(""
			+ "SELECT * FROM aes.fields");
	private String getAllCourses = new String(""
			+ "SELECT * FROM aes.courses");
	private String login = new String(""
			+ "SELECT * FROM aes.users WHERE username=?");
	private String getUserThroughID = new String(""
			+ "SELECT * FROM aes.users WHERE userid = ?");
	
	/*Do not delete me, maybe you will need me later :)
		private String getStudentsWhoSolvedExam="select u.userid,u.username,u.password,u.fullname from users as u,solved_exams as se where u.userid=se.studentid and se.examid=?";
		private String getUser="select * from users where userid=?";
		private String teachersSolvedExams="select * from solved_exams as se, exams as e where se.examid=e.examid and e.teacherid=?";
		private String getstudentSolvedExams="select * from solved_exams as se where se.studentid=?";
	/*/
	
	/**
	 * creating a Database Class creates a connection to an SQLServer
	 * @param h the host address - could be a domain name or ip address (ex. "localhost/schema" or "192.168.1.15/schema")
	 * @param u username of the user that has access to the database host
	 * @param p password of the username.
	 */
	public DBMain(String h,String u,String p) {
		 try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			Globals.handleException(e);
		} catch (IllegalAccessException e) {
			Globals.handleException(e);
		} catch (ClassNotFoundException e) {
			Globals.handleException(e);
		}
		host=h;
		user=u;
		pass=p;
	    connect();
	}
	
	/**
	 * try to make a connection to database 
	 * @return true if connection is valid otherwise returns false
	 */
	private boolean connect() {
		// TODO Auto-generated method stub
		try {
			conn = DriverManager.getConnection("jdbc:mysql://"+host,user,pass);
			return true;
		} catch (SQLException e) {
			Globals.handleException(e);
		}
		return false;
	}

	/**
	 * get connection 
	 * 
	 * @return Connection type variable that holds the connection to database
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * try to reconnect to database in case there is no valid connection
	 * @return true if connection is valid otherwise returns false
	 */
	public boolean reconnect() {
		return connect();
	}

	// #########################  USER HANDELING  ###########################################
	
	public User getUserByUserName(String userName) {
		try {
			PreparedStatement prst = conn.prepareStatement(login);
			prst.setString(1,userName);
			System.out.println("SQL:"+prst);
			if (prst.execute()) {
				ResultSet rs = prst.getResultSet();
				if (rs.next()) {
					int userid = rs.getInt(1);
					String username = rs.getString(2);
					String password = rs.getString(3);
					String fullname = rs.getString(4);
					int type = rs.getInt(5);
					switch(type) {
					case 0:
						return new Student(userid,username,password,fullname);
					case 1:
						return new Teacher(userid,username,password,fullname);
					case 2:
						return new Principal(userid,username,password,fullname);
					}

				}
			}
			return null;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}

	/**
	 * method fetches a user from the database ( needs to be cast as necessary )
	 * @param id - ID of the user
	 * @return	User of the specified ID
	 */
	public User getUserById(int id) {
		try {
			PreparedStatement prst = conn.prepareStatement(getUserThroughID);
			prst.setInt(1,id);
			System.out.println("SQL:"+prst);
			if (prst.execute()) {
				ResultSet rs = prst.getResultSet();
				if (rs.next()) {
					int userid = rs.getInt(1);
					String username = rs.getString(2);
					String password = rs.getString(3);
					String fullname = rs.getString(4);
					int type = rs.getInt(5);
					switch(type) {
						case 0:
							return new Student(userid,username,password,fullname);
						case 1:
							return new Teacher(userid,username,password,fullname);
						case 2:
							return new Principal(userid,username,password,fullname);
					}
				}
			}
			return null;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}
/**
 * return all user whit the same type
 * @param type - int
 * @return ArrayList<User>
 */
	public ArrayList<User> GetAllUsersByType(int type) {
		try {
			PreparedStatement prst = conn.prepareStatement(getAllUsersByType);
			prst.setInt(1,type);
			System.out.println("SQL:"+prst);
			ArrayList<User> result = new ArrayList<>();
			if (prst.execute()) {
				//studentid, courseid, fieldid, userid, username, password, fullname, usertype
				ResultSet rs = prst.getResultSet();
				while (rs.next()) {
					int userid = rs.getInt(1);
					String username = rs.getString(2);
					String password = rs.getString(3);
					String fullname = rs.getString(4);
					if (type==0)
						result.add(new Student(userid,username,password,fullname));
					else if (type==1)
						result.add(new Teacher(userid,username,password,fullname));
					else if (type==2)
						result.add(new Principal(userid,username,password,fullname));
					else return null;
				}
			}
			return result;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}

/**
 * return all user in specific course
 * @param course - Course
 * @return ArrayList<Student>
 */

	public ArrayList<Student> GetAllStudentsInCourse(Course course) {
		try {
			PreparedStatement prst = conn.prepareStatement(getStudentsInCourse);
			prst.setInt(2,course.getId());
			prst.setInt(1, course.getField().getID());
			System.out.println("SQL:"+prst);
			ArrayList<Student> result = new ArrayList<>();
			if (prst.execute()) {
				//studentid, courseid, fieldid, userid, username, password, fullname, usertype
				ResultSet rs = prst.getResultSet();
				while (rs.next()) {
					int userid = rs.getInt(1);
					String username = rs.getString(5);
					String password = rs.getString(6);
					String fullname = rs.getString(7);
					result.add(new Student(userid,username,password,fullname));
				}
			}
			return result;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;	
	}
	
	// ######################### COURSE FIELD HANDELING  ####################################
/**
 * return all the fields
 * @return ArrayList<Field>
 */
	public ArrayList<Field> getAllFields(){
		try {
			PreparedStatement prst = conn.prepareStatement(getAllFields);
			//System.out.println("SQL:" + prst);
			ResultSet rs = prst.executeQuery();
			ArrayList<Field> fields = new ArrayList<>();
			while(rs.next()) {
				int fieldID = rs.getInt(1);
				String fieldName= rs.getString(2);
				fields.add(new Field(fieldID,fieldName));
			}
			return fields;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}
/**
 * return all the courses
 * @return ArrayList<Course>
 */
	public ArrayList<Course> getAllCourses(){
		try {
			PreparedStatement prst = conn.prepareStatement(getAllCourses);
			//System.out.println("SQL:" + prst);
			ResultSet rs = prst.executeQuery();
			ArrayList<Course> courses = new ArrayList<>();
			while(rs.next()) {
				int courseID = rs.getInt(1);
				String courseName = rs.getString(2);
				Field field = new Field(rs.getInt(3),getFieldName(rs.getInt(3)));
				courses.add(new Course(courseID,courseName,field));
			}
			return courses;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}
/**
 * return all the fields of specific teacher
 * @param o - Teacher
 * @return ArrayList<Field>
 */
	public ArrayList<Field> getTeacherFields(Teacher o) {
		ArrayList<Field> fields = new ArrayList<Field>();
		try {
			PreparedStatement prst = conn.prepareStatement(teacherFields);
			prst.setInt(1, o.getID());
			//System.out.println("SQL:"+prst);
			if (prst.execute()) {
				ResultSet rs = prst.getResultSet();
				
				while (rs.next()) {
					int fieldsid = rs.getInt(1);
					String fieldName = rs.getString(2);
					fields.add(new Field(fieldsid,fieldName));
				}
				return fields;
			}
		} catch (Exception e) {
			ServerGlobals.handleSQLException(new SQLException(e));
		}
		return null;
	}
/**
 * return all the courses of all the fields we send
 * @param o -Object (ArrayList<Field>)
 * @return ArrayList<Course>
 */
	@SuppressWarnings("unchecked")
	public ArrayList<Course> getFieldsCourses(Object o) {
		ArrayList<Field> fields = (ArrayList<Field>) o;
		String sqlQuery = new String("select distinct (courseid),coursename,f.fieldid,fieldname from courses as c, fields as f where c.fieldid=f.fieldid and (");
		for(Field f:fields) {
			sqlQuery = sqlQuery+"f.fieldid="+f.getID()+" or ";
		}
		sqlQuery = sqlQuery + "0)";
		try {
			PreparedStatement statement = conn.prepareStatement(sqlQuery);
			//System.out.println("SQL:" + statement);
			ResultSet rs = statement.executeQuery();
			
			ArrayList<Course> result = new ArrayList<>();
			while(rs.next()) {
				int courseid = rs.getInt(1);
				String coursename = rs.getString(2);
				int fieldsid = rs.getInt(3);
				String fieldName = rs.getString(4);
				result.add(new Course(courseid,coursename,new Field(fieldsid,fieldName)));
			}
			return result;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}

	/**
	 * method fetches the field's name from database using the fieldID
	 * @param fieldID - the ID of the required field
	 * @return the name of the field as String
	 */
	public String getFieldName(int fieldID){
		try {
			PreparedStatement prst = conn.prepareStatement(getField);
			prst.setInt(1,fieldID);
			//System.out.println("SQL:" + prst);
			ResultSet rs = prst.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getString(2));
				return rs.getString(2);
			}
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}
/**
 * return the name of course by id course and id field
 * @param courseID - int
 * @param fieldID - int
 * @return CourseName - String
 */
	public String getCourseName(int courseID,int fieldID){
		try {
			PreparedStatement prst = conn.prepareStatement(getCourse);
			prst.setInt(1,courseID);
			prst.setInt(2,fieldID);
			//System.out.println("SQL:" + prst);
			ResultSet rs = prst.executeQuery();
			
			while(rs.next()) {
				return rs.getString(2);
			}
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}

	/**
	 * getFieldCourses returns all field courses
	 */
	public ArrayList<Course> getFieldCourses(Field o) {
		ArrayList<Field> fields = new ArrayList<>();
		fields.add(o);
		return getFieldsCourses(fields);
	}

	/**
	 *
	 * @param field - from which teachers are expected
	 * @return
	 */
	public ArrayList<Teacher> getFieldTeachers(Field field){
		try {
			PreparedStatement prst = conn.prepareStatement(getFieldTeachers);
			prst.setInt(1, field.getID());
			System.out.println("SQL:" + prst);
			ResultSet rs = prst.executeQuery();
			
			ArrayList<Teacher> result = new ArrayList<>();
			while(rs.next()) {
				int teacherid = rs.getInt(1);
				result.add(((Teacher)getUserById(teacherid)));
			}
			return result;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}

	//  #############################   EXAM HANDELING    ##################################
/**
 * return all the exam from the data base
 * @return ArrayList<Exam>
 */
	public ArrayList<Exam> getAllExams() {
		try {
			PreparedStatement prst = conn.prepareStatement(getAllExams);
			System.out.println("SQL:" + prst);
			ResultSet rs = prst.executeQuery();
			ArrayList<QuestionInExam> questions;
			ArrayList<Exam> result = new ArrayList<>();
			while(rs.next()) {
				int examid = rs.getInt(1);
				int duration = rs.getInt(2);
				int teacherId = rs.getInt(5);
				Course course = new Course(rs.getInt(4), getCourseName(rs.getInt(4),rs.getInt(3)), new Field(rs.getInt(3),getFieldName(rs.getInt(3))));
				questions = getQuestionsInExam(Exam.examIdToString(examid,course.getId(),course.getField().getID()));
				result.add(new Exam(examid, course, duration, (Teacher)getUserById(teacherId), questions));
			}
			return result;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}
	
	/**
	 * gets all teachers Completed Exams
	 * still needs to be implemented will not return anything yet
	 * @param t
	 * @return ArrayList of ActiveExams of this Teacher
	 */
	public ArrayList<ExamReport> getTeachersCompletedExams(Teacher t) {
		ArrayList<ExamReport> completedExams = new ArrayList<ExamReport>();
		try {
			PreparedStatement prst = conn.prepareStatement(getTExamReports);
			prst.setInt(1, t.getID());
			System.out.println("SQL:"+prst);
			if (prst.execute()) {
				ResultSet rs = prst.getResultSet();
				
				/*
				 * 1 examid, 2 courseid, 3 fieldid, 
				 * 4 autherid, 5 activatorid, 6 code, 
				 * 7 type, 8 date_time_activated, 9 date_time_locked, 
				 * 10 participated, 11 not_in_time_submitters, 12 submitted
				 */
				while (rs.next()) {
					int examid = rs.getInt(1);
					int courseid = rs.getInt(2);
					int fieldid = rs.getInt(3);
					Teacher activator = (Teacher) getUserById(rs.getInt(5));
					String code = rs.getString(6);
					int type = rs.getInt(7);
					Date dayActivated = rs.getDate(8);
					Date timeLocked = rs.getDate(9);
					int participated = rs.getInt(10);
					int submitted = rs.getInt(12);
					int notInTime = rs.getInt(11);
					ArrayList<SolvedExam> sExams = getSolvedExams(examid, courseid, fieldid, t.getID(), code, type, dayActivated);
					Exam  e = getExam(Exam.examIdToString(examid, courseid, fieldid));
					completedExams.add(new ExamReport(code, type, dayActivated, e, activator, sExams, 
							participated, submitted, notInTime, timeLocked));
				}
				return completedExams;
			}
		} catch (Exception e) {
			ServerGlobals.handleSQLException(new SQLException(e));
		}
		return null;
	}

/**
 * return all the exam reports from the data base
 * @return  ArrayList<ExamReport>
 */
	public ArrayList<ExamReport> getAllExamReports() {
		ArrayList<ExamReport> completedExams = new ArrayList<ExamReport>();
		try {
			PreparedStatement prst = conn.prepareStatement(getAllExamReports);
			System.out.println("SQL:"+prst);
			if (prst.execute()) {
				ResultSet rs = prst.getResultSet();
				/*
				 * 1 examid, 2 courseid, 3 fieldid,
				 * 4 autherid, 5 activatorid, 6 code,
				 * 7 type, 8 date_time_activated, 9 date_time_locked,
				 * 10 participated, 11 not_in_time_submitters, 12 submitted,
				 * 13 median, 14 exam_avg, 15 deviation
				 */
				while (rs.next()) {
					int examid = rs.getInt(1);
					int courseid = rs.getInt(2);
					int fieldid = rs.getInt(3);
					Teacher activator = (Teacher) getUserById(rs.getInt(5));
					String code = rs.getString(6);
					int type = rs.getInt(7);
					Date dayActivated = rs.getDate(8);
					Date timeLocked = rs.getDate(9);
					int participated = rs.getInt(10);
					int submitted = rs.getInt(12);
					int notInTime = rs.getInt(11);
					ArrayList<SolvedExam> sExams = getSolvedExams(examid, courseid, fieldid, rs.getInt(4), code, type, dayActivated);
					Exam  e = getExam(Exam.examIdToString(examid, courseid, fieldid));
					completedExams.add(new ExamReport(code, type, dayActivated, e, activator, sExams, 
							participated, submitted, notInTime, timeLocked));
				}
				return completedExams;
			}
		} catch (Exception e) {
			ServerGlobals.handleSQLException(new SQLException(e));
		}
		return null;
	}
	
	/**
	 * this methods returns a solved exam by parameters
	 * @param examid - ID of the Exam
	 * @param courseid - ID of the Course
	 * @param fieldid - ID of the Field
	 * @param teacherid - ID of the Teacher
	 * @param code - Code of the Exam.
	 * @return ArrayList of the Solved Exams
	 */
	public ArrayList<SolvedExam> getSolvedExams(int examid, int courseid, int fieldid, int teacherid, String code, int type, Date date) {
		try {
			PreparedStatement prst = conn.prepareStatement(getSolvedExams);
			prst.setString(1, code);
			prst.setInt(2, courseid);
			prst.setInt(3, fieldid);
			prst.setInt(4, examid);
			prst.setInt(5, type);
			prst.setDate(6, date);
			prst.setInt(7, teacherid);
			System.out.println("SQL:" + prst);
			ResultSet rs = prst.executeQuery();
			
			ArrayList<QuestionInExam> questions = new ArrayList<>();
			ArrayList<SolvedExam> result = new ArrayList<>();
			while(rs.next()) {		
				//int eid = rs.getInt(1);
				Course course = new Course(rs.getInt(4), rs.getString(5), new Field(rs.getInt(2),rs.getString(3)));
				//int duration = rs.getInt(6);
				int score = rs.getInt(12);
				int tApprove = rs.getInt(14);
				boolean teacherApprove;
				if (tApprove==1)teacherApprove = true;
				else teacherApprove = false;
				String studentAnswers = rs.getString(11);
				String teacherNote = rs.getString(15);
				//int reportid = rs.getInt(12);z
				int timeCompletedMinutes =rs.getInt(13);
				Student student = new Student(rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10));
				Teacher teacher = new Teacher(rs.getInt(16), rs.getString(17), rs.getString(18), rs.getString(19));
				questions = getQuestionsInExam(Exam.examIdToString(examid,course.getId(),rs.getInt(2)));
				HashMap<QuestionInExam,String> teacherQuestionsNotes = SolvedExam.parseTeacherNotes(rs.getString(20),questions);
				HashMap<QuestionInExam,Integer> studentsAnswers = SolvedExam.parseStudentsAnswers(studentAnswers,questions);
				Exam e = getExam(Exam.examIdToString(examid,courseid,fieldid));
				SolvedExam solvedExam = new SolvedExam(
						score, teacherApprove, studentsAnswers,
						student, teacherNote, teacherQuestionsNotes,
						timeCompletedMinutes,code,type,date,teacher,e);
				result.add(solvedExam);
			}
			return result;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}

	/**
	 * Gets Exam from database using an SQL query.
	 * @param examIdString - ID of the Exam to be fetched.
	 * @return an Exam
	 */
	public Exam getExam(String examIdString) {
		try {
			int[] examid = Exam.parseId(examIdString);
			PreparedStatement prst = conn.prepareStatement(getExam);
			prst.setInt(1, examid[2]);//examid
			prst.setInt(2, examid[0]);//fieldid
			prst.setInt(3, examid[1]);//courseid
			System.out.println("SQL:" + prst);
			ResultSet rs = prst.executeQuery();
			ArrayList<QuestionInExam> questions = new ArrayList<>();
			if (rs.next()) {
				int id = rs.getInt(1);
				int duration = rs.getInt(2);
				Course course = new Course(rs.getInt(3), rs.getString(4), new Field(rs.getInt(5),rs.getString(6)));
				questions = getQuestionsInExam(Exam.examIdToString(id,course.getId(),course.getField().getID()));
				return new Exam(id, course, duration, (Teacher)getUserById(rs.getInt(7)), questions);
			}
			return null;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}
	
	/**
	 * This method gets all teachers solved Exams from database
	 * TIBI note: this method is not used and is not maintained by anyone! 
	 * if you need it... 
	 * fix it.. it should be easy and fast to fix
	 * @param o is the Teacher we want to get its active exams of
	 * @return the arraylist of the solved exams
	 */
	public ArrayList<SolvedExam> getTeacherSolvedExams(Teacher o) {
		try {
			
			PreparedStatement prst = conn.prepareStatement(getTeachrsSolvedExams);
			prst.setInt(1, o.getID());
			System.out.println("SQL:" + prst);
			ResultSet rs = prst.executeQuery();
			
			ArrayList<QuestionInExam> questions = new ArrayList<>();
			ArrayList<SolvedExam> result = new ArrayList<>();
			while(rs.next()) {		
				int examid = rs.getInt(1);
				Course course = new Course(rs.getInt(4), rs.getString(5), new Field(rs.getInt(2),rs.getString(3)));
				int score = rs.getInt(12);
				int tApprove = rs.getInt(14);
				boolean teacherApprove;
				if (tApprove==1)teacherApprove = true;
				else teacherApprove = false;
				String studentAnswers = rs.getString(11);
				String teacherNote = rs.getString(15);
				int timeCompletedMinutes =rs.getInt(13);
				Student student = new Student(rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10));
				questions = getQuestionsInExam(Exam.examIdToString(examid,course.getId(),rs.getInt(2)));
				Exam e = getExam(Exam.examIdToString(examid,course.getId(),course.getField().getID()));
				String code = rs.getString(16);
				HashMap<QuestionInExam,String> teacherQuestionsNotes = SolvedExam.parseTeacherNotes(rs.getString(18),questions);
				HashMap<QuestionInExam,Integer> studentsAnswers = SolvedExam.parseStudentsAnswers(studentAnswers,questions);
				SolvedExam solvedExam = new SolvedExam(
						score, teacherApprove, studentsAnswers,
						student, teacherNote, teacherQuestionsNotes,
						timeCompletedMinutes,code,1,new Date(new java.util.Date().getTime()),o,e);
				result.add(solvedExam);
			}
			return result;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}


	/**
	 * Get all student's solved exams from database.
	 * @param s
	 * @return ArrayList of student's solved exams.
	 */
	public ArrayList<SolvedExam> getStudentsSolvedExams(Student s) 
	{
		try {
			PreparedStatement prst = conn.prepareStatement(getStudentsSolvedExams);
			prst.setInt(1, s.getID());
			System.out.println("SQL:" + prst);
			ResultSet rs = prst.executeQuery();
			
			ArrayList<QuestionInExam> questions = new ArrayList<>();
			ArrayList<SolvedExam> result = new ArrayList<>();
			while(rs.next()) {		
				/**
				 *  1 examid,  2 fieldid,  3 fieldname, 
				 *  4 courseid, 5 coursename, 6 timeduration, 
				 *  7 userid, 8 username, 9 password, 
				 *  10 fullname, 11 answers, 12 score, 
				 *  13 minutescompleted, 14 teacherapproved, 15 teacherschangescorenote, 
				 *  16 code, 17 dateinitiated, 18 teacherquestionnote
				 */
				int examid = rs.getInt(1);
				Course course = new Course(rs.getInt(4), rs.getString(5), new Field(rs.getInt(2),rs.getString(3)));
				int score = rs.getInt(12);
				int tApprove = rs.getInt(14);
				boolean teacherApprove;
				if (tApprove==1)teacherApprove = true;
				else teacherApprove = false;
				String studentAnswers = rs.getString(11);
				String teacherNote = rs.getString(15);
				int timeCompletedMinutes =rs.getInt(13);
				Teacher teacher = new Teacher(rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10));
				questions = getQuestionsInExam(Exam.examIdToString(examid,course.getId(),course.getField().getID()));
				Exam e = getExam(Exam.examIdToString(examid,course.getId(),course.getField().getID()));
				String code = rs.getString(16);
				Date dateinitiated = rs.getDate(17);
				HashMap<QuestionInExam,String> teacherQuestionsNotes = SolvedExam.parseTeacherNotes(rs.getString(18),questions);
				HashMap<QuestionInExam,Integer> studentsAnswers = SolvedExam.parseStudentsAnswers(studentAnswers,questions);
				SolvedExam solvedExam = new SolvedExam(
						score, teacherApprove, studentsAnswers,
						s, teacherNote, teacherQuestionsNotes,
						timeCompletedMinutes,code,1,dateinitiated,teacher,e);
				result.add(solvedExam);
			}
			return result;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}

	/**
	 *
	 * @param o
	 * @return
	 */
	public ArrayList<Exam> getTeachersExams(Teacher o) {
		try {
			PreparedStatement prst = conn.prepareStatement(getTeachrsExams);
			prst.setInt(1, o.getID());
			System.out.println("SQL:" + prst);
			ResultSet rs = prst.executeQuery();
			ArrayList<QuestionInExam> questions = new ArrayList<>();
			ArrayList<Exam> result = new ArrayList<>();
			while(rs.next()) {		
				int examid = rs.getInt(1);
				int duration = rs.getInt(2);
				Course course = new Course(rs.getInt(3), rs.getString(4), new Field(rs.getInt(5),rs.getString(6)));
				questions = getQuestionsInExam(Exam.examIdToString(examid,course.getId(),course.getField().getID()));
				result.add(new Exam(examid, course, duration, o, questions));
			}
			return result;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}
/**
 * This method  add exam to data base
 * @param e-exam
 * @return confirmation integer.
 */
	public int addexam(Exam e) {
		try {
			PreparedStatement prst = conn.prepareStatement(addexam,Statement.RETURN_GENERATED_KEYS);
			//(int iD, Course course, int duration, Teacher author, ArrayList<QuestionInExam> questionsInExam)

			prst.setInt(1, e.getDuration());
			prst.setInt(2, e.getField().getID());
			prst.setInt(3, e.getCourse().getId());
			prst.setInt(4, (e.getAuthor().getID()));
			System.out.println("SQL:" + prst);
			int worked = prst.executeUpdate();
			if (worked==1) {
				ResultSet rs = prst.getGeneratedKeys();
				rs.next();
				int examid = rs.getInt(1);
				prst = conn.prepareStatement(addQuestionInExam);
				for(QuestionInExam q: e.getQuestionsInExam()) {
					prst.setInt(1, q.getID());
					prst.setInt(2, examid);
					prst.setInt(3, q.getPointsValue());
					prst.setInt(4, e.getCourse().getId());
					prst.setInt(5, q.getField().getID());
					prst.setString(6, q.getInnerNote());
					prst.setString(7, q.getStudentNote());
					if(prst.executeUpdate()==0) {
						deleteQuestion(q);
						return 0;
					} else worked++;
				}
			}
			return worked;
		} catch (Exception x) {
			x.printStackTrace();
		}
		return 0;
	}
	/**
	 * This method  delete exam from data base
	 * @param e-exam
	 * @return confirmation integer.
	 */
	public int deleteExam(Exam e) {
		try {
			PreparedStatement prst = conn.prepareStatement(deleteExam);
			prst.setInt(1, e.getID());
			prst.setInt(2, e.getField().getID());
			System.out.println("SQL:" + prst);
			return prst.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * Inserts a Solved Exam into the database using SQL query.
	 * @param se - SolvedExam to be inserted into the database.
	 * @return confirmation integer.
	 */
	public Integer InsertSolvedExam(SolvedExam se) {
		try {
			PreparedStatement prst = conn.prepareStatement(addSolvedExam,Statement.RETURN_GENERATED_KEYS);
			//`examid`, `score`, `teacherapproved`, 
			//`answers`, `studentid`, `courseid`, 
			//`fieldid`, `teacherschangescorenote`, `minutescompleted`, 
			//`code`, `teacherquestionnote`,`dateinitiated`
			prst.setInt(1, se.getExam().getID());
			prst.setInt(2, se.getScore());
			if (se.isTeacherApproved()) {
				prst.setInt(3, 1);
			} else {
				prst.setInt(3, 0);
			}

			prst.setString(4, SolvedExam.studentAnswersToString(se.getStudentsAnswers()));
			prst.setInt(5, se.getStudent().getID());
			prst.setInt(6, se.getCourse().getId());
			prst.setInt(7, se.getField().getID());
			prst.setString(8, se.getTeachersScoreChangeNote());
			prst.setInt(9, se.getCompletedTimeInMinutes());
			prst.setString(10, se.getCode());
			prst.setString(11, SolvedExam.teachersNotesToString(se.getQuestionNoteOnHash()));
			prst.setDate(12, se.getDate());
			System.out.println("SQL:" + prst);
			return prst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * Update solved exam in data base
	 * @param se - SolvedExam
	 * @return mount of lines hoe get effect by insert solved exam
	 */
	public Integer UpdateSolvedExam(SolvedExam se) {
		try {
			PreparedStatement prst = conn.prepareStatement(updateSolvedExam);
			//`score`=?, `teacherapproved`=?, `teacherschangescorenote`=?, `teacherquestionnote`=?
			//`studentid`=? and `examid`=? and `courseid`=? and`fieldid`=? and `code`=?
			prst.setInt(1, se.getScore());
			if (se.isTeacherApproved()) {
				prst.setInt(2, 1);
			} else {
				prst.setInt(2, 0);
			}
			prst.setString(3, se.getTeachersScoreChangeNote());
			prst.setString(4, SolvedExam.teachersNotesToString(se.getQuestionNoteOnHash()));
			prst.setInt(5, se.getStudent().getID());
			prst.setInt(6, se.getExam().getID());
			prst.setInt(7, se.getCourse().getId());
			prst.setInt(8, se.getField().getID());
			prst.setString(9, se.getCode());
			System.out.println("SQL:" + prst);
			return prst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
/**
 * return all the exam of specific course
 * @param o - Course
 * @return ArrayList<Exam>
 */
	public ArrayList<Exam> getcourseExams(Course o) {
		try {
		PreparedStatement prst = conn.prepareStatement(getcourseExams);
		prst.setInt(1, o.getId());
		prst.setInt(2, o.getField().getID());
		System.out.println("SQL:" + prst);
		ResultSet rs = prst.executeQuery();
		ArrayList<QuestionInExam> questions = new ArrayList<>();
		ArrayList<Exam> result = new ArrayList<>();
		while(rs.next()) {		
			int examid = rs.getInt(1);
			int duration = rs.getInt(2);
			int authorid = rs.getInt(3); 
			String fullname= rs.getString(4);
			String Password= rs.getString(5);
			String Tname = rs.getString(6);
			
			Teacher author=new Teacher(authorid,fullname,Password,Tname);
			questions = getQuestionsInExam(Exam.examIdToString(examid,o.getId(),o.getField().getID()));
			result.add(new Exam(examid, o, duration,author, questions));
		}
		return result;
		}
	     catch (SQLException e) {
		ServerGlobals.handleSQLException(e);
	}
	return null;
}
	/**
	 *
	 * @param eReport - ExamReport
	 * @return  mount of lines who get effect by insert completed exam
	 */
	public int insertCompletedExam(ExamReport eReport) {
		/*
		 * `examid`, `courseid`, `fieldid`, `autherid`,
		   `activatorid`, `code`, `type`, `dateactivated`,
		   `date_time_locked`, `participated`, `not_in_time_submitters`,
		   `submitted`
		 */
		try {
			int linesEfected =0;
			PreparedStatement prst = conn.prepareStatement(addExamReport);
			prst.setInt(1, eReport.getID());
			prst.setInt(2, eReport.getCourse().getId());
			prst.setInt(3, eReport.getField().getID());
			prst.setInt(4, eReport.getAuthor().getID());
			prst.setInt(5, eReport.getActivator().getID());
			prst.setString(6, eReport.getCode());
			prst.setInt(7, eReport.getType());
			prst.setDate(8, eReport.getDate());
			prst.setDate(9, eReport.getTimeLocked());
			prst.setInt(10, eReport.getParticipatingStudent());
			prst.setInt(11, eReport.getNotInTimeStudents());
			prst.setInt(12, eReport.getSubmittedStudents());
			System.out.println("SQL:" + prst);
			linesEfected = prst.executeUpdate();
			for(SolvedExam se : eReport.getSolvedExams()) {
				if(InsertSolvedExam(se)==1)
					linesEfected++;
				else {
					deleteExamReport(eReport);
					return 0;
				}
			}
			return linesEfected;
		} catch (Exception e) {
			e.printStackTrace();
		}
			return 0;
	}

    /**
     * Deletes given ExamReport from the database using an SQL query.
     * @param eReport - ExamReport to be deleted.
     * @return confirmation integer.
     */
	private int deleteExamReport(ExamReport eReport) {
		try {
			PreparedStatement prst = conn.prepareStatement(deleteExamReport);
			prst.setInt(1, eReport.getID());
			prst.setInt(2, eReport.getCourse().getId());
			prst.setInt(3, eReport.getField().getID());
			prst.setInt(4, eReport.getAuthor().getID());
			prst.setString(5, eReport.getCode());
			prst.setInt(6, eReport.getType());
			prst.setDate(7, eReport.getDate());
			System.out.println("SQL:" + prst);
			return prst.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	// ##############################   QUESTION HANDELING     ####################################
	
	/**
	 * method fetches all question that exist in the database
	 * @return ArrayList<Question> - array list containing all the questions in the database
	 */
	public ArrayList<Question> getAllQuestions(){
		try {
			PreparedStatement prst = conn.prepareStatement(getAllQuestions);
			System.out.println("SQL:" + prst);
			ResultSet rs = prst.executeQuery();
			
			ArrayList<Question> result = new ArrayList<>();
			while(rs.next()) {
				int questionid = rs.getInt(1);
				String questionString = rs.getString(2);
				String answerA = rs.getString(3);
				String answerB = rs.getString(4);
				String answerC = rs.getString(5);
				String answerD = rs.getString(6);
				String answers[] = new String[]{answerA,answerB,answerC,answerD};
				int answerIndex = rs.getInt(7);
				int fieldsid = rs.getInt(8);
				String teacherId = rs.getString(9);
				Question question = new Question(questionid,(Teacher)getUserById(Integer.parseInt(teacherId)), questionString, answers, new Field(fieldsid,getFieldName(fieldsid)), answerIndex,getQuestionCourses(Question.questionIDToString(questionid, fieldsid)));
				result.add(question);
			}
			return result;

		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}


    /**
     * Gets Questions which the specified Teacher wrote.
     * @param o - Teacher whose questions the user wants to fetch.
     * @return ArrayList of Questions.
     */
	public ArrayList<Question> getTeachersQuestions(Teacher o) {
		Teacher t = (Teacher) o;
		try {
			PreparedStatement prst = conn.prepareStatement(teachersQuestions);
			prst.setInt(1,t.getID());
			System.out.println("SQL:" + prst);
			ResultSet rs = prst.executeQuery();
			
			ArrayList<Question> result = new ArrayList<>();
			while(rs.next()) {
				int questionid = rs.getInt(1);
				String questionString = rs.getString(2);
				String answerA = rs.getString(3);
				String answerB = rs.getString(4);
				String answerC = rs.getString(5);
				String answerD = rs.getString(6);
				String answers[] = new String[]{answerA,answerB,answerC,answerD};
				int answerIndex = rs.getInt(7);
				int fieldsid = rs.getInt(8);
				String fieldName = rs.getString(11);
				Question question = new Question(questionid, new Teacher(t), questionString, answers, new Field(fieldsid,fieldName), answerIndex,getQuestionCourses(Question.questionIDToString(questionid, fieldsid)));
				result.add(question);
			}
			return result;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}

    /**
     * Gets Courses associated with the given Question.
     * @param o - Question which courses the user wants to fetch.
     * @return ArrayList of Courses.
     */
	public ArrayList<Course> getQuestionCourses(Object o) {
		try {
			PreparedStatement prst = conn.prepareStatement(questionCourses);
			if (o instanceof Question) {
				Question question = (Question) o;
				prst.setInt(1,question.getID());
				prst.setInt(2, question.getField().getID());
			} else if (o instanceof String) {
				String id = (String)o; 
				prst.setInt(1, Question.parseID(id)[1]);
				prst.setInt(2, Question.parseID(id)[0]);
			} else return null;
			System.out.println("SQL:" + prst);
			ResultSet rs = prst.executeQuery();
			
			ArrayList<Course> result = new ArrayList<>();
			while(rs.next()) {
				int fieldid = rs.getInt(1);
				String fieldName = rs.getString(2);
				int courseid = rs.getInt(3);
				String courseName = rs.getString(4);
				result.add(new Course(courseid,courseName, new Field(fieldid,fieldName)));
			}
			return result;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}

	/**
	 * This method  get all question in this course
	 * @param o -course
	 * @return ArrayList<Question>
	 */
	public ArrayList<Question> CourseQuestions(Course o) {
		Course c = (Course) o;
		try {
			PreparedStatement prst = conn.prepareStatement(CourseQuestions);
			prst.setInt(1,c.getId());
			prst.setInt(2,c.getField().getID());
			System.out.println("SQL:" + prst);
			ResultSet rs = prst.executeQuery();
			
			ArrayList<Question> result = new ArrayList<>();
			while(rs.next()) {
				int questionid = rs.getInt(2);
				String questionString = rs.getString(3);
				String answerA = rs.getString(4);
				String answerB = rs.getString(5);
				String answerC = rs.getString(6);
				String answerD = rs.getString(7);
				String answers[] = new String[]{answerA,answerB,answerC,answerD};
				int answerIndex = rs.getInt(8);
				int fieldsid = rs.getInt(1);
				int Tid = rs.getInt(11);
				String Tname= rs.getString(12);
				String Password= rs.getString(10);
				String fullname= rs.getString(9);
				String fieldName = rs.getString(13);
				Question question = new Question(questionid,new Teacher(Tid,Tname,Password,fullname), questionString, answers, new Field(fieldsid,fieldName), answerIndex,getQuestionCourses(Question.questionIDToString(questionid, fieldsid)));
				result.add(question);
			}
			return result;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}


	/**
	 * get Questions in exam by giving examid
	 * @param examid -String
	 * @return ArrayList<QuestionInExam>
	 */
	public ArrayList<QuestionInExam> getQuestionsInExam(String examid) {
		int eid = Exam.parseId(examid)[2];
		int courseid = Exam.parseId(examid)[1];
		int fieldid = Exam.parseId(examid)[0];
		try {
			PreparedStatement prst = conn.prepareStatement(getQuestionsInExam);
			prst.setInt(1,eid);
			prst.setInt(2,fieldid);
			prst.setInt(3,courseid);
			System.out.println("SQL:" + prst);
			ResultSet rs = prst.executeQuery();
			
			ArrayList<QuestionInExam> result = new ArrayList<>();
			while(rs.next()) {
				int questionid = rs.getInt(1);
				String questionString = rs.getString(2);
				String answerA = rs.getString(3);
				String answerB = rs.getString(4);
				String answerC = rs.getString(5);
				String answerD = rs.getString(6);
				String answers[] = new String[]{answerA,answerB,answerC,answerD};
				int answerIndex = rs.getInt(7);
				int fieldsid = rs.getInt(8);
				//int teacherId = rs.getInt(9);
				int pointsValue = rs.getInt(12);
				String innerNote = rs.getString(15);
				String viewableNote = rs.getString(16);
				String fieldName = rs.getString(28);
				QuestionInExam question = new QuestionInExam(
						questionid, new Teacher(rs.getInt(22),rs.getString(23),rs.getString(24),rs.getString(25)), questionString, answers, 
						new Field(fieldsid,fieldName), answerIndex,
						getQuestionCourses(Question.questionIDToString(questionid, fieldsid)),
						pointsValue,innerNote,viewableNote);
				result.add(question);
			}
			return result;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}

	/**
	 * this function deletes a question from database!
	 * @param q - Question
	 * @return mount of lines who get effect by delete question
	 */
	public int deleteQuestion(Question q) {
		try {
			PreparedStatement prst = conn.prepareStatement(deleteQuestion);
			prst.setInt(1, q.getID());
			prst.setInt(2, q.getField().getID());
			System.out.println("SQL:" + prst);
			return prst.executeUpdate();
		} catch (SQLException e) {
			
		}
		return 0;
	}

    /**
     * Adds a new Question into the database using an SQL query.
     * @param q - Question to be added
     * @return confirmation integer.
     */
	public int addQuestion(Question q) {
		try {
			PreparedStatement prst = conn.prepareStatement(addQuestion,Statement.RETURN_GENERATED_KEYS);
			//('0',`question`, `answer1`, `answer2`, `answer3`, `answer4`, `answerindex`, `fieldid`, `teacherid`)
			prst.setString(1, q.getQuestionString());
			prst.setString(2, q.getAnswer(1));
			prst.setString(3, q.getAnswer(2));
			prst.setString(4, q.getAnswer(3));
			prst.setString(5, q.getAnswer(4));
			prst.setInt(6, q.getCorrectAnswerIndex());
			prst.setInt(7, q.getField().getID());
			prst.setInt(8, q.getAuthor().getID());
			System.out.println("SQL:" + prst);
			int worked = prst.executeUpdate();
			if (worked==1) {
				ResultSet rs = prst.getGeneratedKeys();
				rs.next();
				int questionid = rs.getInt(1);
				prst = conn.prepareStatement(addQuestionToCourse);
				for(Course c: q.getCourses()) {
					prst.setInt(1, questionid);
					prst.setInt(2, q.getField().getID());
					prst.setInt(3, c.getId());
					if(prst.executeUpdate()==0) {
						deleteQuestion(q);
						return 0;
					} else worked++;
				}
			}
			return worked;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

    /**
     * Edits an existing Question in the database using an SQL query.
     * @param q - Question to be edited.
     * @return confirmation integer.
     */
	public int editQuestion(Question q) {
		try {
			PreparedStatement prst = conn.prepareStatement(editQuestion);
			/* SET `question`=?, `answer1`=?, `answer2`=?, `answer3`=?, `answer4`=?, `answerindex`=? "
			 * WHERE `questionid`=? and`fieldid`=?" + 
			*/
			prst.setString(1, q.getQuestionString());
			prst.setString(2, q.getAnswer(1));
			prst.setString(3, q.getAnswer(2));
			prst.setString(4, q.getAnswer(3));
			prst.setString(5, q.getAnswer(4));
			prst.setInt(6, q.getCorrectAnswerIndex());
			prst.setInt(7, q.getID());
			prst.setInt(8, q.getField().getID());
			System.out.println("SQL:" + prst);
			return prst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return 0;
	}

    /**
     * Gets Courses which the student takes from the database using an SQL query.
     * @param o - Studnet, courses of which to be fetched
     * @return ArrayList of Courses
     */
	public ArrayList<Course> getStudentsCourses(User o) {
		try {
			PreparedStatement statement = conn.prepareStatement(getStudentsCourses);
			statement.setInt(1, o.getID());
			System.out.println("SQL:" + statement);
			ResultSet rs = statement.executeQuery();
			ArrayList<Course> result = new ArrayList<>();
			while(rs.next()) {
				int courseid = rs.getInt(1);
				String coursename = rs.getString(2);
				int fieldsid = rs.getInt(3);
				String fieldName = rs.getString(4);
				result.add(new Course(courseid,coursename,new Field(fieldsid,fieldName)));
			}
			return result;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}




}




