package ocsf.client;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import logic.*;

import java.io.IOException;

/**
 * This is the subclass of AbstractClient that is changed by the Team to work as expected 
 * and fit the needs of the project. 
 * @author Group-12
 *
 */
public class AESClient extends AbstractClient{
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
	protected void handleMessageFromServer(Object ServerMsg){
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
		default:
			copyServerMsg(ServerMsg);
		}
		synchronized (stopWaiting) {
			stopWaiting=true;	
		}
		
	}

	private void copyServerMsg(Object serverMsg) {
		msg = (iMessage) serverMsg;
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
				e.printStackTrace();
			}
			count++;
			if(count>=500) {
				System.out.println("Server Taking Long Time To Respond...");
				//Globals.handleException(new Exception("Waited for too long for server response!"));
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

	private void handleExamLocked(Object o) {
		// TODO Auto-generated method stub
	}

	private void showFailedAuth() {
		msg.setCommand("failedAuth");
		me = null;
	}

	private void alreadyLoggedIn() {
		msg.setCommand("AlreadyLoggedIn");
		me = null;
	}
	
}
