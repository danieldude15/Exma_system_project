package prototype;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBMain {
	private String user;
	private String pass;
	private String host;
	private Connection conn;
	PreparedStatement updateAnswer;
	
	public DBMain(String user, String pass, String host) throws SQLException {
		this.user=user;
		this.pass=pass;
		this.host=host;
	    conn = DriverManager.getConnection("jdbc:mysql://localhost/test","root","1234");
	    updateAnswer = conn.prepareStatement("UPDATE Questions SET possibleAnswer = ? WHERE QID = ?");
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
	
	public Question[] getQuestions() {
		try {
			Statement stmt = conn.createStatement();
			ResultSet uprs = stmt.executeQuery("SELECT * FROM Questions");
			Question[] questions = new Question[uprs.getFetchSize()];
			int i=0;
			
			while(uprs.next())
	 		{
				 // Print out the values for debug
				 System.out.println(uprs.getString(1)+"  " +uprs.getString(2)+"  " +uprs.getString(3)+"  " +uprs.getString(4)+"  " +uprs.getString(5)+"  " +uprs.getString(6)+"  " +uprs.getString(7)+"  " +uprs.getString(8));
				 String[] answers = new String[]{uprs.getString(3),uprs.getString(4),uprs.getString(5),uprs.getString(6)};
				 questions[i]= new Question(uprs.getInt(1),uprs.getInt(2),uprs.getString(8),answers);
				 i++;
				 
			} 
			return questions;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
