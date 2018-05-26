package SQLTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import logic.ActiveExam;
import logic.Course;
import logic.Field;
import logic.Globals;
import logic.Principle;
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
	private String tableA = "";
	private String tableB= "";
	private String columnA= "";
	private String columnB= "";
	private String getActiveEams = new String(
			"SELECT * FROM "
			+ "(SELECT * FROM aes.activated_exams WHERE teacherid=?) B "
			+ "INNER JOIN aes.teachers ON teachers.teacherid=B.teacherid") ;
	private String teacherFields = new String(
			"SELECT distinct (f.fieldid),f.fieldname "
			+ "FROM aes.teacher_fields as tf,fields as f "
			+ "WHERE tf.fieldid=f.fieldid and tf.teacherid=?;;");
	private String getAllUsers = new String(
			"Select * FROM aes.users"
			);
	private String getAllTeachers = new String(
			"Select * FROM aes.users,aes.teachers WHERE userid=teacherid"
			);
	private String getAllStudents = new String(
			"Select * FROM aes.users,aes.students WHERE userid=teacherid"
			);
	private String getAllPrinciples = new String(
			"Select * FROM aes.users,aes.principles WHERE userid=teacherid"
			);
	private String login;
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
	
	private void setLogin() {
		login = new String(
				"SELECT * " + 
				"FROM aes."+tableA+" , aes.users " + 
				"WHERE "+columnA+"=userid AND username=?");
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
		try {
			prst = conn.prepareStatement(getActiveEams);
			prst.setInt(1, t.getID());
			System.out.println("SQL:\t"+prst);
			if (prst.execute())
				rs = prst.getResultSet();
			ArrayList<ActiveExam> ae = new ArrayList<ActiveExam>();
			while (rs.next()) {
				System.out.println(rs);
				int examid = rs.getInt(1);
				int courseid = rs.getInt(2);
				int fieldid = rs.getInt(3);
				String code = rs.getString(4);
				int type = rs.getInt(5);
				int active = rs.getInt(6);
				int studentCount = rs.getInt(7);
				int zerocount = rs.getInt(8);
				//ae.add(new ActiveExam(code,type,));
			}
		} catch (SQLException e) {
			ServerGlobals.handleSQLException(e);
		}
		return null;
	}

	public User UserLogIn(User u) {
		String type = null;
		try {
			tableA= "students";
			columnA = "studentid";
			setLogin();
			prst = conn.prepareStatement(login);
			prst.setString(1,u.getUserName());
			System.out.println("SQL:"+prst);
			if (prst.execute()) {
				rs = prst.getResultSet();
				System.out.println(rs);
				if (rs.next()) {
					int userid = rs.getInt(2);
					String username = rs.getString(3);
					String password = rs.getString(4);
					String fullname = rs.getString(5);
					if (!password.equals(u.getPassword())) return null;
					return new Student(userid,username,password,fullname);
				}
			}
			tableA= "teachers";
			columnA = "teacherid";
			setLogin();
			prst = conn.prepareStatement(login);
			prst.setString(1,u.getUserName());
			if (prst.execute()) {
				rs = prst.getResultSet();
				if (rs.next()) {
					int userid = rs.getInt(2);
					String username = rs.getString(3);
					String password = rs.getString(4);
					String fullname = rs.getString(5);
					if (!password.equals(u.getPassword())) return null;
					return new Teacher(userid,username,password,fullname);
				}
			}
			tableA= "principles";
			columnA = "principleid";
			setLogin();
			prst = conn.prepareStatement(login);
			prst.setString(1,u.getUserName());
			System.out.println("SQL:"+prst);
			if (prst.execute()) {
				rs = prst.getResultSet();
				System.out.println(rs);
				if (rs.next()) {
					int userid = rs.getInt(2);
					String username = rs.getString(3);
					String password = rs.getString(4);
					String fullname = rs.getString(5);
					if (!password.equals(u.getPassword())) return null;
					return new Principle(userid,username,password,fullname);
				}
			}
			System.out.println(type);
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
				if (rs.next()) {
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
		String sqlQuery = new String("select distinct (courseid),coursename,fieldid,fieldname from courses as c, fields as f where c.coursefieldid=f.fieldid and (");
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
}
