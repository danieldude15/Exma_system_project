package SQLTools;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.layout.ConstraintsBase;
import logic.ActiveExam;
import logic.Course;
import logic.Exam;
import logic.Field;
import logic.Globals;
import logic.Principle;
import logic.Question;
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
	PreparedStatement prst;
	ResultSet rs;
	private String teacherFields = new String(
			"SELECT distinct (f.fieldid),f.fieldname "
			+ "FROM aes.teacher_fields as tf,fields as f "
			+ "WHERE tf.fieldid=f.fieldid and tf.teacherid=?");
	private String questionCourses = new String(""
			+ "SELECT f.fieldid,f.fieldname,qic.courseid,c.coursename "
			+ "FROM aes.fields as f,aes.questions_in_course as qic, aes.courses as c "
			+ "WHERE c.courseid=qic.courseid and qic.fieldid=f.fieldid and qic.questionid=? and qic.fieldid=?");
	private String teachersQuestions = new String(
			"select * from questions as q, fields as f where q.fieldid=f.fieldid and q.teacherid=?" 
			);
	private String teachersSolvedExam;
	private String studentSolvedExam;
	private String allActiveExams="SELECT * FROM activated_exams";
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
	 * gets all teachers active exams
	 * still needs to be implemented will not return anything yet
	 * @param t
	 * @return ArrayList of ActiveExams of this Teacher
	 */
	public ArrayList<ActiveExam> getTeachersActiveExams(Teacher t) {
		return null;
	}

	public User UserLogIn(User u) {
		try {
			prst = conn.prepareStatement(login);
			prst.setString(1,u.getUserName());
			System.out.println("SQL:"+prst);
			if (prst.execute()) {
				rs = prst.getResultSet();
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
			prst = conn.prepareStatement(teacherFields);
			prst.setInt(1, o.getID());
			System.out.println("SQL:"+prst);
			if (prst.execute()) {
				System.out.println(rs);
				rs = prst.getResultSet();
				while (rs.next()) {
					int fieldsid = rs.getInt(1);
					String fieldName = rs.getString(2);
					fields.add(new Field(fieldsid,fieldName));
				}
				return fields;
			}
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
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
			rs = statement.executeQuery();
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
			prst = conn.prepareStatement(teachersQuestions);
			prst.setInt(1,t.getID());
			System.out.println("SQL:" + prst);
			rs = prst.executeQuery();
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

	//Itzik's method..not finished!
	public ArrayList<SolvedExam> getTeacherSolvedExams(Teacher o) {
		// TODO Auto-generated method stub
		return null;
	}
	//Itzik's method..not finished!
	public ArrayList<SolvedExam> getStudentSolvedExams(Student o) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//Itzik's method..not finished!
	public ArrayList<ActiveExam> getAllActiveExams()  {
		// TODO Auto-generated method stub
		ArrayList <ActiveExam> result=new ArrayList<ActiveExam>();
		ActiveExam activeExam;
		String sqlQuery=new String("select * from activated_exams");
		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sqlQuery);
			rs = statement.executeQuery();
			while(rs.next()) {
				//activeExam=new ActiveExam(rs.getString(5),rs.getString(6),rs.getDate(6));
				//activeExam=new ActiveExam(Exam e,Teacher activator, boolean currentlyactive);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
		
		
		
	}


}
