package pSQLTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import pLogic.Question;


public class DBMain {
	private static String host="localhost/prototype";
	private static String user="root";
	private static String pass="1234";
	private Connection conn;
	PreparedStatement updateAnswer;
	
	public DBMain() throws SQLException {
		try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
        	System.out.println("Error connectig to driver");
        	throw new SQLException();
        }
	    conn = DriverManager.getConnection("jdbc:mysql://"+host,user,pass);
	    updateAnswer = conn.prepareStatement("UPDATE questions SET correctindex = ? WHERE idquestions = ?");
	}
	
	public boolean updateCorrectAnswer(int qid, int index) {
		try {
			updateAnswer.setInt(1, index);
			updateAnswer.setInt(2, qid);
			updateAnswer.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public Vector<Question> getQuestions() {
		try {
			Statement stmt = conn.createStatement();
			ResultSet uprs = stmt.executeQuery("SELECT * FROM Questions");
			Vector<Question> questions = new Vector<Question>();
			
			while(uprs.next())
	 		{
				 // Print out the values for debug
				 //System.out.println(uprs.getInt(1)+"  " +uprs.getString(2)+"  " +uprs.getString(3)+"  " +uprs.getString(4)+"  " +uprs.getString(5)+"  " +uprs.getString(6)+"  " +uprs.getString(7)+"  " +uprs.getString(8));
				 String[] answers = new String[]{uprs.getString(4),uprs.getString(5),uprs.getString(6),uprs.getString(7)};
				 questions.add(new Question(uprs.getInt(1),uprs.getInt(2),uprs.getString(3),answers,Integer.parseInt(uprs.getString(8))));
			} 
			return questions;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
