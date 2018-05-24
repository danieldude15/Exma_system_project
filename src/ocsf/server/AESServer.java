package ocsf.server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import SQLTools.DBMain;
import logic.*;

public class AESServer extends AbstractServer {
	private DBMain sqlcon;
	HashMap<String,User> connectedUsers;
	public AESServer(int port) {
		super(port);
		sqlcon = new DBMain(ServerGlobals.dbHost, ServerGlobals.dbuser, ServerGlobals.dbpass);
		connectedUsers = new HashMap<String,User>();
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
			case "logout":
				User logoutUser = (User) o;
				connectedUsers.remove(logoutUser.getUserName());
				break;
			case "login":
				User user = (User) o;
				iMessage result=null;
				String login = "login";
				if (connectedUsers.get(user.getUserName())!=null) {
					result = new iMessage("login",o);
				} else {
					user = sqlcon.UserLogIn((User)o);
					if (user==null) {
						result = new iMessage(login,null);
					} else {
						if (user instanceof Teacher) {
							result = new iMessage(login,new Teacher((Teacher) user));
						} else if(user instanceof Principle) {
							result = new iMessage(login,new Principle((Principle) user));
						} else if(user instanceof Student) {
							result = new iMessage(login,new Student((Student) user));
						}
						connectedUsers.put(user.getUserName(), user);
					}
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
