package ocsf.client;

import java.io.IOException;

import GUI.PrincipalMainFrame;
import GUI.StudentSolvesExamFrame;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import logic.Globals;
import logic.Principal;
import logic.Student;
import logic.Teacher;
import logic.User;
import logic.iMessage;

/**
 * This is the subclass of AbstractClient that is changed by the Team to work as expected 
 * and fit the needs of the project. 
 * @author Group-12
 *
 */
public class AESClient extends AbstractClient implements IAESClient{
	/**
	 * when signed in this "me" will hold the User information that is logged in
	 */
	User me;
	/**
	 * iMessae object used to pass information from server
	 */
	private iMessage msg;
	/**
	 * shared variable that can be updated in different threads.
	 */
	private Boolean stopWaiting=false;
	
	/**
	 * Constructor
	 * @param host - host to connect to (Server IP/Domain)
	 * @param port - port to connect to
	 */
	public AESClient(String host, int port) {
		super(host, port);
		me=null;
	}

	/**
	 * Handles a message sent from the server to this client.
	 *
	 * @param ServerMsg this will always be an iMessage type of object
	 */
	public void handleMessageFromServer(Object ServerMsg){
		this.msg = (iMessage) ServerMsg;
		System.out.println("Got msg from Server:" + msg);
		if(!(ServerMsg instanceof iMessage)) {
			System.out.println("Server msg not of iMessage type");
			return;
		}
		String cmd = new String(msg.getCommand());
		Object o = msg.getObj();
		//cases with return isntead of break are cases that make sure we dont update the stopwaiting 
		//because the user did not wait for these msgs
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
			return;
		case "ExamLocked":
			handleExamLocked(o);
			return;	
		case "newTimeChangeRequest":
			newTimeChangeRequest(o);
			break;
		case "studentUpdateExamTime":
			studentUpdateExamTime(o);
			break;
		default:
			copyServerMsg(ServerMsg);
		}
		synchronized (stopWaiting) {
			stopWaiting=true;	
		}
		
	}

	/**
	 * this method will copy the msg received from the server to a local variable that will later be used by whoever requested the msg
	 * @param serverMsg - the msg recieved from the server
	 */
	private void copyServerMsg(Object serverMsg) {
		msg = (iMessage) serverMsg;
	}

	/**
	 * simply logs the fact that the connection has been closed
	 */
	protected void connectionClosed() {
		System.out.println("connection Closed!");
	}

	/**
	 * this returns the iMessage the client currently holds
	 * @return
	 */
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
				e.printStackTrace();
			}
			count++;
			if(count>=500) {
				System.out.println("Server Taking Long Time To Respond... returning null");
				return new iMessage("Server Taking Long Time To Respond... returning null", null);
			}
			if(!isConnected()) {
				System.out.println("Lost Connection with Server! returning null");
				return new iMessage("Lost Connection with Server! returning null", null);
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

	/**
	 * this will be called if the server sent a msg that its closing and we should disconnect from it.
	 */
	private void closeAESApplication() {
		Platform.runLater(() -> { 
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Server Disconnected");
			alert.setHeaderText(null);
			alert.setContentText("Server Has Closed Its Connection! Someone Closed The Server!");
			alert.showAndWait();
			Globals.mainContainer.setScreen(ClientGlobals.ClientConnectionScreenID);
			ClientGlobals.ClientConnectionController.DisconnectFromServer(null);
			});
	}
	  
	//  ################################################# Team Start Adding Functions From Here ############################
	
	/**
	 * will set the information of the logged in user to the correct permissions by user type
	 * 
	 * @param o - the user that logged in successfully
	 */
	private void Login(Object o) {
		if (o==null) {
			msg.setCommand("ERROR");
		} else if (o instanceof Teacher) {
			msg.setCommand("Teacher");
			me = new Teacher((Teacher)o);
		} else if(o instanceof Principal) {
			msg.setCommand("Principle");
			me = new Principal((Principal)o);
		} else if(o instanceof Student) {
			msg.setCommand("Student");
			me = new Student((Student)o);
		}
		
	}

	
	/**
	 * Send message to the principle in case that there is a new time change request for an active exam.
	 * @param o
	 */
	private void newTimeChangeRequest(Object o) {
		PrincipalMainFrame principalMainFrame = (PrincipalMainFrame) Globals.mainContainer.getController(ClientGlobals.PrincipalMainID);
		principalMainFrame.setNewRequestArrived(true);
	}
	

	/**
	 * Send message to the students in case that the principle approved time change request(to update new time).
	 * @param o
	 */
	private void studentUpdateExamTime(Object o) {
		StudentSolvesExamFrame sef = (StudentSolvesExamFrame) Globals.mainContainer.getController(ClientGlobals.StudentSolvesExamID);
		if (o instanceof Long)
			sef.updateExamTime((Long)o);
	}
	
	/**
	 * Send message to the students in case that the teacher locked the exam(to lock their exams).
	 * @param o
	 */
	private void handleExamLocked(Object o) {
		System.err.println("Locking Studetns Exam");
		StudentSolvesExamFrame sef = (StudentSolvesExamFrame) Globals.mainContainer.getController(ClientGlobals.StudentSolvesExamID);
		sef.lockExam();
	}

	/**
	 * in case a login failed the command will be set the string msg to notify the controller that tried to log in that it failed
	 */
	private void showFailedAuth() {
		msg.setCommand("failedAuth");
		me = null;
	}

	/**
	 * alreadyLoggedIn function will notify the controller that this user is aleady logged in and will not allow him to connect
	 */
	private void alreadyLoggedIn() {
		msg.setCommand("AlreadyLoggedIn");
		me = null;
	}


	
}
