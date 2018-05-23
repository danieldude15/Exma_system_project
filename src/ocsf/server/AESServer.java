package ocsf.server;

import SQLTools.DBMain;
import logic.*;

public class AESServer extends AbstractServer {
	private DBMain sqlcon;
	public AESServer(int port) {
		super(port);
		DBMain sqlcon = new DBMain(ServerGlobals.dbHost, ServerGlobals.dbuser, ServerGlobals.dbpass);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		if(!(msg instanceof iMessage) || !client.isAlive()) {
			return;
		}
		iMessage m = (iMessage) msg;
		String cmd = new String(m.getCommand());
		Object o = m.getObj();
		switch(cmd) {
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
	
	}

}
