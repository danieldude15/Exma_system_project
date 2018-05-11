package pSQLTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBMain {
	private static String host="localhost/prototype";
	private static String user="root";
	private static String pass="1234";
	private Connection conn;
	
	public DBMain() throws SQLException {
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
}
