package pSQLTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class pDBMain {
	private static String host="localhost/prototype";
	private static String user="protorypeUser";
	private static String pass="1234";
	private Connection conn;
	
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
}
