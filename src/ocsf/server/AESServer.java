package ocsf.server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import SQLTools.DBMain;
import logic.*;

public class AESServer extends AbstractServer {
	private DBMain sqlcon;
	public AESServer(int port) {
		super(port);
		sqlcon = new DBMain(ServerGlobals.dbHost, ServerGlobals.dbuser, ServerGlobals.dbpass);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		System.out.println("Got msg from:" + client + "message: " + msg);
		if(!(msg instanceof iMessage)) {
			System.out.println("msg from client is not of type iMessage!");
			return;
		}
		iMessage m = (iMessage) msg;
		String cmd = new String(m.getCommand());
		Object o = m.getObj();
		try {
			switch(cmd) {
			case "login":
				User user = sqlcon.UserLogIn((User)o);
				iMessage result=null;
				String login = "login";
				if (user==null) {
					result = new iMessage(login,null);
				} else if (user instanceof Teacher) {
					result = new iMessage(login,new Teacher((Teacher) user));
				} else if(user instanceof Principle) {
					result = new iMessage(login,new Principle((Principle) user));
				} else if(user instanceof Student) {
					result = new iMessage(login,new Student((Student) user));
				}
				client.sendToClient(result);
				break;
			case "getTeachersActiveExams":
				System.out.println("got msg from client getTeachersActiveExams");
				if (o instanceof Teacher) {
					Teacher t = (Teacher) o;
					sqlcon.getTeachersActiveExams(t);
				}
				break;
			case "getTeachersQuestions":
				//code here
				break;
			case "getTeachersExams":
				//code here
				break;
			case "getTeachersFields":
				//code here
				break;
				
			default:
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void serverClosed() {
		try {
			sqlcon.getConn().close();
		} catch (SQLException e) {
			Globals.handleException(e);
		}
	}

}
