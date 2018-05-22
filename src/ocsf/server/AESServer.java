package ocsf.server;

import logic.iMessage;

public class AESServer extends AbstractServer {

	public AESServer(int port) {
		super(port);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		if(!(msg instanceof iMessage) || !client.isAlive()) {
			return;
		}
		iMessage m = (iMessage) msg;
		String cmd = new String(m.getCommand());
		switch(cmd) {
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
