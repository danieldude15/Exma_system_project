package Controllers;

import java.sql.SQLException;

import SQLTools.DBMain;

public class AesSQLException extends SQLException {

	public AesSQLException() {
		// TODO Auto-generated constructor stub
	}

	public AesSQLException(String reason) {
		super(reason);
		// TODO Auto-generated constructor stub
	}

	public AesSQLException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public AesSQLException(String reason, String SQLState) {
		super(reason, SQLState);
		// TODO Auto-generated constructor stub
	}

	public AesSQLException(String reason, Throwable cause) {
		super(reason, cause);
		// TODO Auto-generated constructor stub
	}

	public AesSQLException(String reason, String SQLState, int vendorCode) {
		super(reason, SQLState, vendorCode);
		// TODO Auto-generated constructor stub
	}

	public AesSQLException(String reason, String sqlState, Throwable cause) {
		super(reason, sqlState, cause);
		// TODO Auto-generated constructor stub
	}

	public AesSQLException(String reason, String sqlState, int vendorCode, Throwable cause) {
		super(reason, sqlState, vendorCode, cause);
		// TODO Auto-generated constructor stub
	}
	
	private boolean keepAlive(DBMain sqlcon) {
		return (sqlcon.reconnect());
	}

}
