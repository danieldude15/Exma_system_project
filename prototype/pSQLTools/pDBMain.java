package pSQLTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import pLogic.pQuestion;
import pServer.pServerGlobals;


public class pDBMain {
	private static String host="localhost/prototype";
	private static String user="prototypeuser";
	private static String pass="7wJ1MLuZ!t35";
	private Connection conn;
	PreparedStatement updateAnswer;
	
	public pDBMain() {
		pServerGlobals.debug=true;
		try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Could Not Load Driver");
			alert.setHeaderText(null);
			alert.setContentText("Could Not Load Driver from com.mysql.jdbc.Driver!\nERROR:\n" + ex.toString());

			alert.showAndWait();
        	System.out.println("Error connectig to driver");
        }
	    try {
			conn = DriverManager.getConnection("jdbc:mysql://"+host,user,pass);
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Could Not Connect To Database");
			alert.setHeaderText(null);
			alert.setContentText("Could Not Connect To Database!\nERROR:\n" + e.toString());
			alert.showAndWait();
			e.printStackTrace();
		}
	    try {
			updateAnswer = conn.prepareStatement("UPDATE questions SET correctindex = ? WHERE idquestions = ?");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConn() {
		return conn;
	}
	
	public Vector<pQuestion> getQuestions() throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet uprs = stmt.executeQuery("SELECT * FROM questions");
		ResultSetMetaData rsmd = uprs.getMetaData();
		int columcount = rsmd.getColumnCount();
		Vector<pQuestion> questions = new Vector<pQuestion>();
		
		if (pServerGlobals.debug) {
			System.out.println("GetQuestions results for debug");
		}
		while(uprs.next())
 		{
			 // Print out the values for debug
			if (pServerGlobals.debug) {
				for (int i=1;i<columcount;i++) {
					if (i > 1) System.out.print(",  ");
			        String columnValue = uprs.getString(i);
			        System.out.print(columnValue + " -> " + rsmd.getColumnName(i));
				}
				 System.out.println();
			}
			 String[] answers = new String[]{uprs.getString(4),uprs.getString(5),uprs.getString(6),uprs.getString(7)};
			 questions.add(new pQuestion(uprs.getInt(1),uprs.getInt(2),uprs.getString(3),answers,Integer.parseInt(uprs.getString(8))));
		} 
		return questions;
	}
	
	public void updateCorrectAnswer(int qid, int index) throws SQLException {
		updateAnswer.setInt(1, index);
		updateAnswer.setInt(2, qid);
		updateAnswer.executeUpdate();
	}
}
