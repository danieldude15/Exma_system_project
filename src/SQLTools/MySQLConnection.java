package SQLTools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Logic.*;


public class MySQLConnection {
	private static Connection conn;
	
	public static void main(String[] args) {
		MySQLConnection mysql = new MySQLConnection();
		
	}
	
	public MySQLConnection() {
		if (!setConn()) {
			System.out.println("SHIT!");
		}
	}
	
	public static ResultSet runGeneralQuary(String quary) {

		try 
		{
			Statement stmt = getConn().createStatement();
			return stmt.executeQuery(quary);
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
	
	public static boolean addQuestionQuary(Question e) {
		
		
		return false;
	}

	public static Connection getConn() {
		return conn;
	}

	public boolean setConn() {
		try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
        	System.out.println("Error connectig to driver");
        }
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/test","root","1234");
            System.out.println("SQL connection succeed");
            return true;
        } catch (SQLException ex) {/* handle any errors*/
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
 	    }
        return false;
	}

}