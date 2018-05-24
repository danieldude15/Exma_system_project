package ocsf.client;

import java.io.IOException;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
			} else if(msg.getObj() instanceof User) {
				msg.setCommand("AlreadyLoggedIn");
			}
			break;
		case "closing Connection":
			Platform.runLater(() -> { 
				Globals.primaryStage.close();
				ClientGlobals.ClientConnectionController.DisconnectFromServer(null);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Server Disconnected");
				alert.setHeaderText(null);
				alert.setContentText("Server Has Closed Its Connection! Someone Closed The Server!");
				alert.showAndWait();
				});
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
	
	/**
	 * this method should be called right after sending a msg to the server
	 * that needs to get a response
	 */
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
	
	/**
	* Hook method called each time an exception is thrown by the
	* client's thread that is waiting for messages from the server.
	* The method may be overridden by subclasses.
	*
	* @param exception the exception raised.
	*/
	protected void connectionException(Exception exception) {
		IOException e = new IOException(exception);
		ClientGlobals.handleIOException(e);
	}

	public void cleanMsg() {
		msg=null;
		stopWaiting=false;
	}


	public User getUser() {
		return me;
	}
}
