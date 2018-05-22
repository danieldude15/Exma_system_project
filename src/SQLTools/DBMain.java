package SQLTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBMain {
	private static String host="localhost/aes";
	private static String user="aesUser";
	private static String pass="QxU&v&HMm0t&";
	private Connection conn;
	
	public DBMain() throws SQLException {
		try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
        	System.out.println("Error connectig to driver");
        	System.exit(1);
        }
	    connect();
	}
	
	private boolean connect() {
		// TODO Auto-generated method stub
		try {
			conn = DriverManager.getConnection("jdbc:mysql://"+host,user,pass);
			return (conn.isValid(20));
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Connection getConn() {
		return conn;
	}

	public boolean reconnect() {
		return connect();
	}
}
