package SQLTools;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.omg.IOP.TAG_MULTIPLE_COMPONENTS;

import com.mysql.jdbc.Statement;

import Controllers.QuestionController;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.ConstraintsBase;
import logic.ActiveExam;
import logic.CompletedExam;
import logic.Course;
import logic.Exam;
import logic.Field;
import logic.Globals;
import logic.Principle;
import logic.Question;
import logic.QuestionInExam;
import logic.SolvedExam;
import logic.Student;
import logic.Teacher;
import logic.User;
import ocsf.server.ServerGlobals;


public class DBMain {
	private String host;
	private String user;
	private String pass;
	private Connection conn;
	private String teacherFields = new String(
			"SELECT distinct (f.fieldid),f.fieldname "
			+ "FROM aes.teacher_fields as tf,fields as f "
			+ "WHERE tf.fieldid=f.fieldid and tf.teacherid=?");
	private String questionCourses = new String(""
			+ "SELECT f.fieldid,f.fieldname,qic.courseid,c.coursename "
			+ "FROM aes.fields as f,aes.questions_in_course as qic, aes.courses as c "
			+ "WHERE c.courseid=qic.courseid and qic.fieldid=f.fieldid and qic.questionid=? and qic.fieldid=?");
	private String deleteQuestion = new String(""
			+ "DELETE FROM aes.questions WHERE questionid=? and fieldid=?");
	private String teachersQuestions = new String(
			"select * from aes.questions as q, aes.fields as f where q.fieldid=f.fieldid and q.teacherid=?" 
			);
	private String getTcompletedExams = new String(""
			+ "SELECT * FROM aes.completed_exams where teacherid=?");
	private String getTeachrsSolvedExams = new String(""
			+ "SELECT  e.examid,e.fieldid,f.fieldname ,e.courseid,c.coursename, e.timeduration,\n" + 
			"		u.userid,u.username,u.password,u.fullname,se.answers,\n" + 
			"        se.examreportid,se.score,se.minutescompleted ,se.teacherapproved ,se.teacherschangescorenote \n" + 
			"FROM aes.users as u, aes.courses as c , aes.fields as f, aes.exams as e, aes.solved_exams as se \n" + 
			"where e.examid=se.examid and e.fieldid=se.fieldid and e.fieldid=f.fieldid and e.courseid=se.courseid "
			+ "and c.courseid=e.courseid and c.fieldid=e.fieldid and u.userid=se.studentid and e.teacherid=?;"
			);
	private String getSolvedExams = new String(""
			+ "SELECT e.examid,e.fieldid,f.fieldname ,e.courseid,c.coursename, e.timeduration," + 
			"		u.userid,u.username,u.password,u.fullname,se.answers," + 
			"        se.examreportid,se.score,se.minutescompleted ,se.teacherapproved " + 
			"        ,se.teacherschangescorenote , u2.userid , " + 
			"        u2.username, u2.password, u2.fullname " + 
			"FROM aes.users as u, aes.courses as c , aes.fields as f, \n" + 
			"	aes.exams as e, aes.solved_exams as se , aes.completed_exams as ce, aes.users as u2\n" + 
			"where ce.examid=e.examid and ce.fieldid=e.fieldid and ce.courseid=e.courseid \n" + 
			"	and u2.userid=e.teacherid and ce.teacherid=e.teacherid and ce.code=se.code and e.examid=se.examid \n" + 
			"    and e.fieldid=se.fieldid and e.fieldid=f.fieldid and e.courseid=se.courseid \n" + 
			"    and c.courseid=e.courseid and c.fieldid=e.fieldid and u.userid=se.studentid " + 
			"    and se.code=? and se.courseid=? and se.fieldid=? and ce.examid=? and ce.type=? and ce.dateactivated=? and ce.teacherid=?");
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
			+ "SELECT  e.examid,e.fieldid,f.fieldname ,e.courseid,c.coursename, e.timeduration,\n" + 
			"		u.userid,u.username,u.password,u.fullname,se.answers,\n" + 
			"        se.examreportid,se.score,se.minutescompleted ,se.teacherapproved ,se.teacherschangescorenote \n" + 
			"FROM aes.users as u, aes.courses as c , aes.fields as f, aes.exams as e, aes.solved_exams as se \n" + 
			"where e.examid=se.examid and e.fieldid=se.fieldid and e.fieldid=f.fieldid and e.courseid=se.courseid "
			+ "and c.courseid=e.courseid and c.fieldid=e.fieldid and u.userid=se.studentid and se.studentid=?;"
			);
	
	private String getQuestionsInExam= new String(""
			+ "SELECT * " + 
			"FROM aes.questions as q,aes.questions_in_exam as qe, aes.exams as e,aes.users as u, aes.fields as f\n" + 
			"WHERE u.userid=q.teacherid and q.questionid=qe.questionid and f.fieldid=q.fieldid "
			+ "and q.fieldid=qe.fieldid and e.fieldid=q.fieldid and e.courseid=qe.courseid "
			+ "and e.examid=? and e.fieldid=? and e.courseid=?");
	
	private String QuestionsInCourse="Select * FROM aes.questions as q, aes.questions_in_course as qc,aes.courses as c,aes.users as u"
			+ " where c.courseid=qc.courseid and q.questionid=qc.questionid and u.userid=q.teacherid and c.fieldid=q.fieldid and c.courseid=?";
	/*Do not delete me, maybe you will need me later :)
	private String getStudentsWhoSolvedExam="select u.userid,u.username,u.password,u.fullname from users as u,solved_exams as se where u.userid=se.studentid and se.examid=?";
	private String getUser="select * from users where userid=?";
	private String getCourse="select * from courses where courseid=?";
	private String getField="select * from fields where fieldid=?";
	private String teachersSolvedExams="select * from solved_exams as se, exams as e where se.examid=e.examid and e.teacherid=?";
	private String getstudentSolvedExams="select * from solved_exams as se where se.studentid=?";
	/*/
	
	private String FieldCourses="Select c.courseid,c.coursename"+" FROM aes.courses as c,aes.fields as f "
			+ " where  c.fieldid=f.fieldid and f.fieldid=?";
	
	
	private String login = "SELECT * FROM aes.users WHERE username=?";
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

	/**
	 * gets all teachers Completed Exams
	 * still needs to be implemented will not return anything yet
	 * @param t
	 * @return ArrayList of ActiveExams of this Teacher
	 */
	public ArrayList<CompletedExam> getTeachersCompletedExams(Teacher t) {
		ArrayList<CompletedExam> completedExams = new ArrayList<CompletedExam>();
		try {
			PreparedStatement prst = conn.prepareStatement(getTcompletedExams);
			prst.setInt(1, t.getID());
			System.out.println("SQL:"+prst);
			if (prst.execute()) {
				ResultSet rs = prst.getResultSet();
				System.out.println(rs);
				while (rs.next()) {
					int examid = rs.getInt(1);
					int courseid = rs.getInt(2);
					int fieldid = rs.getInt(3);
					String code = rs.getString(5);
					int type = rs.getInt(6);
					String dayActivated = rs.getString(7);
					ArrayList<SolvedExam> sExams = getSolvedExams(examid, courseid, fieldid, t.getID(), code, type, dayActivated);
					completedExams.add(new CompletedExam(code, type, dayActivated, new Teacher(t) , sExams));
				}
				return completedExams;
			}
		} catch (Exception e) {
			ServerGlobals.handleSQLException(new SQLException(e));
		}
		return null;
	}

	public User UserLogIn(User u) {
		try {
			PreparedStatement prst = conn.prepareStatement(login);
			prst.setString(1,u.getUserName());
			System.out.println("SQL:"+prst);
			if (prst.execute()) {
				ResultSet rs = prst.getResultSet();
				System.out.println(rs);
				if (rs.next()) {
					int userid = rs.getInt(1);
					String username = rs.getString(2);
					String password = rs.getString(3);
					String fullname = rs.getString(4);
					int type = rs.getInt(5);
					if (!password.equals(u.getPassword())) return null;
					switch(type) {
					case 0:
						return new Student(userid,username,password,fullname);
					case 1:
						return new Teacher(userid,username,password,fullname);
					case 2:
						return new Principle(userid,username,password,fullname);
					}
					
				}
			}
			return null;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}

	public ArrayList<Field> getTeacherFields(Teacher o) {
		ArrayList<Field> fields = new ArrayList<Field>();
		try {
			PreparedStatement prst = conn.prepareStatement(teacherFields);
			prst.setInt(1, o.getID());
			System.out.println("SQL:"+prst);
			if (prst.execute()) {
				ResultSet rs = prst.getResultSet();
				System.out.println(rs);
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
			System.out.println("SQL:" + statement);
			ResultSet rs = statement.executeQuery();
			System.out.println(rs);
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

	public ArrayList<Question> getTeachersQuestions(Teacher o) {
		Teacher t = (Teacher) o;
		try {
			PreparedStatement prst = conn.prepareStatement(teachersQuestions);
			prst.setInt(1,t.getID());
			System.out.println("SQL:" + prst);
			ResultSet rs = prst.executeQuery();
			System.out.println(rs);
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
			System.out.println(rs);
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
	 * 
	 * @param examid
	 * @param couseid
	 * @param fieldid
	 * @param teacherid
	 * @param code
	 * @return
	 */
	public ArrayList<SolvedExam> getSolvedExams(int examid, int courseid, int fieldid, int teacherid, String code, int type, String date) {
		try {
			PreparedStatement prst = conn.prepareStatement(getSolvedExams);
			prst.setString(1, code);
			prst.setInt(2, courseid);
			prst.setInt(3, fieldid);
			prst.setInt(4, examid);
			prst.setInt(5, type);
			prst.setString(6, date);
			prst.setInt(7, teacherid);
			System.out.println("SQL:" + prst);
			ResultSet rs = prst.executeQuery();
			System.out.println(rs);
			ArrayList<QuestionInExam> questions = new ArrayList<>();
			ArrayList<SolvedExam> result = new ArrayList<>();
			while(rs.next()) {		
				int eid = rs.getInt(1);
				Course course = new Course(rs.getInt(4), rs.getString(5), new Field(rs.getInt(2),rs.getString(3)));
				int duration = rs.getInt(6);
				int score = rs.getInt(13);
				int tApprove = rs.getInt(15);
				boolean teacherApprove;
				if (tApprove==1)teacherApprove = true;
				else teacherApprove = false;
				String studentAnswers = rs.getString(11);
				String teacherNote = rs.getString(16);
				int reportit = rs.getInt(12);
				int timeCompletedMinutes =rs.getInt(14);
				Student student = new Student(rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10));
				Teacher teacher = new Teacher(rs.getInt(17), rs.getString(18), rs.getString(19), rs.getString(20));
				questions = getQuestionsInExam(Exam.examIdToString(examid,course.getId(),rs.getInt(2)));
				result.add(new SolvedExam(eid, course, duration, teacher, score, 
						teacherApprove, parseStudentsAnswers(studentAnswers,questions), reportit , 
						student, teacherNote, timeCompletedMinutes));
			}
			return result;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}
	
	/**
	 * This method gets all teachers solved Exams from database
	 * @param o is the Teacher we want to get its active exams of
	 * @return the arraylist of the solved exams
	 */
	public ArrayList<SolvedExam> getTeacherSolvedExams(Teacher o) {
		try {
			PreparedStatement prst = conn.prepareStatement(getTeachrsSolvedExams);
			prst.setInt(1, o.getID());
			System.out.println("SQL:" + prst);
			ResultSet rs = prst.executeQuery();
			System.out.println(rs);
			ArrayList<QuestionInExam> questions = new ArrayList<>();
			ArrayList<SolvedExam> result = new ArrayList<>();
			while(rs.next()) {		
				int examid = rs.getInt(1);
				Course course = new Course(rs.getInt(4), rs.getString(5), new Field(rs.getInt(2),rs.getString(3)));
				int duration = rs.getInt(6);
				int score = rs.getInt(13);
				int tApprove = rs.getInt(15);
				boolean teacherApprove;
				if (tApprove==1)teacherApprove = true;
				else teacherApprove = false;
				String studentAnswers = rs.getString(11);
				String teacherNote = rs.getString(16);
				int reportit = rs.getInt(12);
				int timeCompletedMinutes =rs.getInt(14);
				Student student = new Student(rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10));
				questions = getQuestionsInExam(Exam.examIdToString(examid,course.getId(),rs.getInt(2)));
				result.add(new SolvedExam(examid, course, duration, new Teacher(o), score, 
						teacherApprove, parseStudentsAnswers(studentAnswers,questions), reportit , 
						student, teacherNote, timeCompletedMinutes));
			}
			return result;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}
	
	/**
	 * this function deletes a question from database!
	 * @param q
	 * @return
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
	 * This function will convert answers from database to objects by organizing them
	 * For this to work the answers must be kept in the database in a cetain way
	 * answers will appear as a long string and look like following:
	 * q<questionId>a<answerIndex>q<questionId>a<answerIndex>q<questionId>a<answerIndex>/ ... 
	 * before every answer there is the letter 'a'
	 * also before every question there is the letter 'q'
	 * @param string of answers kept in database in described matter
	 * @param questions the arraylist of questionsInExam with their points assigned to them
	 * @return an organized HashMap of questions in exam including the question and the students answer
	 */
	private HashMap<QuestionInExam, Integer> parseStudentsAnswers(String string, ArrayList<QuestionInExam> questions) {
		if (string==null || string.equals("")) return null;
		HashMap<String, Integer> answers = new HashMap<>();
		HashMap<QuestionInExam, Integer> result = new HashMap<>();
		String[] temp = string.split("q");
		String[] splitedAnswers = Arrays.copyOfRange(temp,1,temp.length);
		for(String questionIDAndAnswer : splitedAnswers) {
			String[] arr = questionIDAndAnswer.split("a");
			answers.put(arr[0], Integer.parseInt(arr[1]));
		}
		for(QuestionInExam q: questions) {
			result.put(q,answers.get(q.questionIDToString()));
		}
		return result;
	}
	
	
	public ArrayList<SolvedExam> getStudentsSolvedExams(Student s) 
	{
		try {
			PreparedStatement prst = conn.prepareStatement(getStudentsSolvedExams);
			prst.setInt(1, s.getID());
			System.out.println("SQL:" + prst);
			ResultSet rs = prst.executeQuery();
			System.out.println(rs);
			ArrayList<QuestionInExam> questions = new ArrayList<>();
			ArrayList<SolvedExam> result = new ArrayList<>();
			while(rs.next()) {		
				int examid = rs.getInt(1);
				Course course = new Course(rs.getInt(4), rs.getString(5), new Field(rs.getInt(2),rs.getString(3)));
				int duration = rs.getInt(6);
				int score = rs.getInt(13);
				int tApprove = rs.getInt(15);
				boolean teacherApprove;
				if (tApprove==1)teacherApprove = true;
				else teacherApprove = false;
				String studentAnswers = rs.getString(11);
				String teacherNote = rs.getString(16);
				int reportit = rs.getInt(12);
				int timeCompletedMinutes =rs.getInt(14);
				Teacher teacher = new Teacher(rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10));
				questions = getQuestionsInExam(Exam.examIdToString(examid,course.getId(),rs.getInt(2)));
				result.add(new SolvedExam(examid, course, duration, teacher, score, 
						teacherApprove, parseStudentsAnswers(studentAnswers,questions), reportit , 
						new Student(s), teacherNote, timeCompletedMinutes));
			}
			return result;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}
	
	/**
	 * get Questions in exam by giving examid
	 * @param examid
	 * @return
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
			System.out.println(rs);
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
				int teacherId = rs.getInt(9);
				int pointsValue = rs.getInt(12);
				String innerNote = rs.getString(15);
				String viewableNote = rs.getString(16);
				String fieldName = rs.getString(28);
				QuestionInExam question = new QuestionInExam(
						questionid, new Teacher(rs.getInt(22),rs.getString(23),rs.getString(24),rs.getString(25)), questionString, answers, 
						new Field(fieldsid,fieldName), answerIndex,
						getQuestionCourses(Question.questionIDToString(questionid, fieldsid)),
						0,innerNote,viewableNote);
				result.add(question);
			}
			return result;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}

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
						System.out.println("FAIL!!!!! rollback issue!!!");
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
	
	public ArrayList<Question> getCourseQuestions(Course o) {
		Course c = (Course) o;
		try {
			PreparedStatement prst = conn.prepareStatement(QuestionsInCourse);
			prst.setInt(1,c.getId());
			System.out.println("SQL:" + prst);
			ResultSet rs = prst.executeQuery();
			System.out.println(rs);
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
				int Tid = rs.getInt(9);
				String Tname= rs.getString(12);
				String Password= rs.getString(13);
				String fullname= rs.getString(14);
				String fieldName = rs.getString(11);
				Question question = new Question(questionid,new Teacher(Tid,Tname,Password,fullname), questionString, answers, new Field(fieldsid,fieldName), answerIndex,getQuestionCourses(Question.questionIDToString(questionid, fieldsid)));
				result.add(question);
			}
			return result;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}

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
//FieldCourses
	public ArrayList<Course> getFieldCourses(Field o) {
		Field f = (Field) o;
		try {
			PreparedStatement prst = conn.prepareStatement(FieldCourses);
			prst.setInt(1,f.getID());
			System.out.println("SQL:" + prst);
			ResultSet rs = prst.executeQuery();
			System.out.println(rs);
			ArrayList<Course> result = new ArrayList<>();
			while(rs.next()) {
				int Courseid = rs.getInt(1);
				String Coursename = rs.getString(2);
				
				Course Course = new Course(Courseid,Coursename,f);
				result.add(Course);
			}
			return result;
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
		
	}

}

