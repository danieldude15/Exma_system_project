package ocsf.client;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import logic.CompletedExam;
import logic.Globals;
import logic.Principle;
import logic.Student;
import logic.Teacher;
import logic.User;
import logic.iMessage;

public class AESClient extends AbstractClient{
	
	User me;
	private iMessage msg;
	private Boolean stopWaiting=false;
	
	public AESClient(String host, int port) {
		super(host, port);
		me=null;
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
		Object o = msg.getObj();
		switch(cmd) {
		case "login":
			Login(o);
			break;
		case "loginFailedAuthentication":
			showFailedAuth();
			break;
		case "loggedInAlready":
			alreadyLoggedIn();
			break;
		case "closing Connection":
			closeAESApplication();
		case "TeachersQuestions":
			TeacherQuestions(ServerMsg);
			break;
		case "TeacherFields":
			teacherFields(ServerMsg);
			break;
		case "QuestionCourses":
			questionCourses(ServerMsg);
			break;
		case "FieldsCourses":
			fieldsCourses(ServerMsg);
			break;
		case "TeacherCompletedExams":
			TeacherCompletedExams(ServerMsg);
			break;
		case "AllActiveExams":
			AllActiveExams(ServerMsg);
			break;
			
		case "ActiveExam":
			GetActiveExam(ServerMsg);
			break;
			
		case "TeacherSolvedExams":
			TeacherSolvedExams(ServerMsg);
			break;
		case "StudentSolvedExams":
			StudentSolvedExams(ServerMsg);
			break;
				
			
		default:
			
		}
		synchronized (stopWaiting) {
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stopWaiting=true;	
		}
		
	}


	protected void connectionClosed() {
		System.out.println("connection Closed!");
	}

	public iMessage getMsg() {
		return msg;
	}

	/**
	 * get user connected to server
	 * this can be any kind of user... Student, Teacher or even Principle
	 * @return User connected to Server
	 */
	public User getUser() {
		return me;
	}
	
	/**
	 * Set the User that is connected to server
	 * this should happen when a client logs in as an authenticated user 
	 * @param me - the user currently connected
	 */
	public void setUser(User me) {
		this.me = me;
	}


	/**
	 * this method should be called right after sending a msg to the server
	 * that needs to get a response
	 */
	public iMessage getResponseFromServer() {
		int count=0;
		while(true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count++;
			if(count>=500) {
				System.out.println("Server Taking Long Time To Respond...");
				//Globals.handleException(new Exception("Waited for tooo long for server response!"));
				break;
			}
			synchronized (stopWaiting) {
				if (stopWaiting) {
					stopWaiting=false;
					break;
				}
			}
		}
		return msg;
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

	
	  /**
	   * Hook method called after a connection has been established.
	   * The default implementation does nothing.
	   * It may be overridden by subclasses to do anything they wish.
	   */
	  protected void connectionEstablished() {}

	
	//  ################################################# Team Start Adding Functions From Here ############################
	
	
	
	private void Login(Object o) {
		if (o==null) {
			msg.setCommand("ERROR");
		} else if (o instanceof Teacher) {
			msg.setCommand("Teacher");
			me = new Teacher((Teacher)o);
		} else if(o instanceof Principle) {
			msg.setCommand("Principle");
			me = new Principle((Principle)o);
		} else if(o instanceof Student) {
			msg.setCommand("Student");
			me = new Student((Student)o);
		}
		
	}



	private void showFailedAuth() {
		msg.setCommand("failedAuth");
		me = null;
	}


	private void alreadyLoggedIn() {
		msg.setCommand("AlreadyLoggedIn");
		me = null;
	}
	

	/**
	 * this function handles the recieved course information on a question
	 * this will happen after the client requested to get an arraylist of courses that belong to a question
	 * it copys msg from server to be used by controller
	 * @param serverMsg 
	 */
	private void questionCourses(Object serverMsg) {
		msg = new iMessage(serverMsg);
	}
	
	private void TeacherQuestions(Object serverMsg) {
		msg = new iMessage(serverMsg);
	}

	private void closeAESApplication() {
		Platform.runLater(() -> { 
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Server Disconnected");
			alert.setHeaderText(null);
			alert.setContentText("Server Has Closed Its Connection! Someone Closed The Server!");
			alert.showAndWait();
			Globals.primaryStage.close();
			ClientGlobals.ClientConnectionController.DisconnectFromServer(null);
			System.exit(1);
			});
		
	}

	
	private void fieldsCourses(Object o) {
		msg = new iMessage(o);
	}
	
	private void teacherFields(Object o) {
		msg = new iMessage(o);
	}


	private void AllActiveExams(Object o) {
		msg = new iMessage(o);
	}


	private void GetActiveExam(Object o) {
		msg = new iMessage(o);
	}


	private void StudentSolvedExams(Object o) {
		msg = new iMessage(o);
	}

	@SuppressWarnings("unchecked")
	private void TeacherCompletedExams(Object o) {
		ArrayList<CompletedExam> completedExams = (ArrayList<CompletedExam>) ((iMessage) o).getObj();
		msg = new iMessage(null, o);
				
	}

	private void TeacherSolvedExams(Object o) {
		msg = new iMessage(o);
	}
	

}
