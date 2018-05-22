package pSQLTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import pLogic.pQuestion;


public class pDBMain {
	private static String host="localhost/prototype";
	private static String pass="1234";
	private static String user="prototypeuser";
	private static String pass="7wJ1MLuZ!t35";
	private Connection conn;
	PreparedStatement updateAnswer;
	
	public pDBMain() throws SQLException {
		try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
        	System.out.println("Error connectig to driver");
        	throw new SQLException();
        }
	    conn = DriverManager.getConnection("jdbc:mysql://"+host,user,pass);
	}
	
	public Connection getConn() {
		return conn;
	}
	
	public Vector<pQuestion> getQuestions() throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet uprs = stmt.executeQuery("SELECT * FROM Questions");
		Vector<pQuestion> questions = new Vector<pQuestion>();
		while(uprs.next())
 		{
			 // Print out the values for debug
			 //System.out.println(uprs.getInt(1)+"  " +uprs.getString(2)+"  " +uprs.getString(3)+"  " +uprs.getString(4)+"  " +uprs.getString(5)+"  " +uprs.getString(6)+"  " +uprs.getString(7)+"  " +uprs.getString(8));
			 String[] answers = new String[]{uprs.getString(4),uprs.getString(5),uprs.getString(6),uprs.getString(7)};
			 questions.add(new pQuestion(uprs.getInt(1),uprs.getInt(2),uprs.getString(3),answers,Integer.parseInt(uprs.getString(8))));
		} 
		return questions;
	}
	
	public void updateCorrectAnswer(int qid, int index) throws SQLException {
		updateAnswer = conn.prepareStatement("UPDATE questions SET correctindex = ? WHERE idquestions = ?");
		updateAnswer.setInt(1, index);
		updateAnswer.setInt(2, qid);
		updateAnswer.executeUpdate();
	}
}
