package ocsf.client;

import logic.Globals;
import logic.Principle;
import logic.Student;
import logic.Teacher;
import logic.User;
import logic.iMessage;

public class AESClient extends AbstractClient{
	
	User me;
	private iMessage msg;
	public boolean stopWaiting=false;
	public AESClient(String host, int port) {
		super(host, port);
		// TODO Auto-generated constructor stub
	}


	/**
	 * Handles a message sent from the server to this client.
	 *
	 * @param msg   this will always be an iMessage type of object 
	 */
	protected void handleMessageFromServer(Object ServerMsg){
		if(!(ServerMsg instanceof iMessage)) {
			System.out.println("Server msg not of iMessage type");
			return;
		}
		this.msg = (iMessage) ServerMsg;
		System.out.println("Got msg from Server:" + msg);
		String cmd = new String(msg.getCommand());
		switch(cmd) {
		case "login":
			if (msg.getObj()==null) {
				msg.setCommand("none");
			} else if (msg.getObj() instanceof Teacher) {
				msg.setCommand("Teacher");
			} else if(msg.getObj() instanceof Principle) {
				msg.setCommand("Principle");
			} else if(msg.getObj() instanceof Student) {
				msg.setCommand("Student");
			}
			break;
		case "gotTeachersActiveExams":
			//code here
			break;
		case "setTeachersQuestions":
			//code here
			break;
		case "setTeachersExams":
			//code here
			break;
		case "setTeachersFields":
			//code here
			break;
			
		default:
			
		}
		stopWaiting=true;
		
	}
	protected void connectionClosed() {
		System.out.println("connection Closed!");
	}

	public iMessage getMsg() {
		return msg;
	}
	
	public void waitForResponse() {
		int count=0;
		while(!stopWaiting) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count++;
			if(count>=50) {
				System.out.println("Server Taking Long Time To Respond...");
				//Globals.handleException(new Exception("Waited for tooo long for server response!"));
				break;
			}
		}
	}


	public void cleanMsg() {
		msg=null;
		stopWaiting=false;
	}


	public User getUser() {
		return me;
	}
}
