package ocsf.server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import SQLTools.DBMain;
import logic.*;

public class AESServer extends AbstractServer {
	
	
	private DBMain sqlcon;
	private HashMap<String,ConnectionToClient> connectedUsers;
	private HashMap<String,ActiveExam> activeExams;
	
	public AESServer(int port) {
		super(port);
		sqlcon = new DBMain(ServerGlobals.dbHost, ServerGlobals.dbuser, ServerGlobals.dbpass);
		connectedUsers = new HashMap<String,ConnectionToClient>();
		activeExams=new HashMap<String,ActiveExam>();
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
				logoutFunctionality(o);
				break;
			case "login":
				loginFunctionality(client, o);
				break;
			case "getTeachersFields":
				getTeacherFields(client,o);
				break;
			case "getQuestionCourses":
				getQuestionCourses(client,o);
				break;
			case "getFieldsCourses":
				getFieldsCourses(client,o);
				break;
			case "getTeachersQuestions":
				getTeacherQuestions(client,o);
				break;
			case "getStudentSolvedExams":
				getSolvedExam(client,o);
				break;
			case "getTeacherSolvedExams":
				getSolvedExam(client,o);
				break;
			
			case "getAllActiveExams":
				getAllActiveExams(client);
				break;
		
			case "getActiveExam":
				getActiveExam(client,o);
				break;
				
			default:
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Hook Method executed right before server closes while still listening
	 * this is part of AbstractServer functionality
	 */
	protected void serverClosed() {
		try {
			ServerGlobals.server.sendToAllClients(new iMessage("closing Connection",null));
			sqlcon.getConn().close();
		} catch (SQLException e) {
			Globals.handleException(e); 
		}
	}

	  /**
	   * Hook method called each time a new client connection is
	   * accepted. The default implementation does nothing.
	   * @param client the connection connected to the client.
	   */
	  protected void clientConnected(ConnectionToClient client) {}

	  /**
	   * Hook method called each time a client disconnects.
	   * The default implementation does nothing. The method
	   * may be overridden by subclasses but should remains synchronized.
	   *
	   * @param client the connection with the client.
	   */
	  synchronized protected void clientDisconnected(ConnectionToClient client) {}

	  /**
	   * Hook method called each time an exception is thrown in a
	   * ConnectionToClient thread.
	   * The method may be overridden by subclasses but should remains
	   * synchronized.
	   *
	   * @param client the client that raised the exception.
	   * @param Throwable the exception thrown.
	   */
	  synchronized protected void clientException(ConnectionToClient client, Throwable exception) {}

	  /**
	   * Hook method called when the server stops accepting
	   * connections because an exception has been raised.
	   * The default implementation does nothing.
	   * This method may be overriden by subclasses.
	   *
	   * @param exception the exception raised.
	   */
	  protected void listeningException(Throwable exception) {}

	  /**
	   * Hook method called when the server starts listening for
	   * connections.  The default implementation does nothing.
	   * The method may be overridden by subclasses.
	   */
	  protected void serverStarted() {}

	  /**
	   * Hook method called when the server stops accepting
	   * connections.  The default implementation
	   * does nothing. This method may be overriden by subclasses.
	   */
	  protected void serverStopped() {}


	
	
	
	
	
	
	
	
	
	// ######################################## TEAM Start Adding Functions from here ###################################################

	/**
	 * Get an active exam and add it from active exams Hashmap.
	 * @param ae
	 */
	public void AddActiveExam(ActiveExam ae)
	{
		
		activeExams.put(ae.getCode(), ae);
	}
	/**
	 * Get an active exam and remove it from active exams Hashmap.
	 * @param ae
	 */
	public void RemoveActiveExam(ActiveExam ae)
	{
		activeExams.remove(ae.getCode());
	}
	
	/**
	 * return all available active exams.
	 * @param client
	 * @throws IOException
	 */
	private void getAllActiveExams(ConnectionToClient client) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<ActiveExam> allActiveExams=new ArrayList<ActiveExam>();
		for(String ae: activeExams.keySet())
		{
			allActiveExams.add(activeExams.get(ae));
		}
		iMessage im = new iMessage("AllActiveExams",allActiveExams);
		client.sendToClient(im);
	}

	/**
	 * Get an active exam code and return an active exam.
	 * @param client
	 * @param Object
	 * @throws IOException
	 */
	private void getActiveExam(ConnectionToClient client,Object o) throws IOException {
		String examCode=(String)o;
		for(String ae: activeExams.keySet())
		{
			if(ae.equals(examCode))
			{
				iMessage im = new iMessage("ActiveExam",activeExams.get(ae));
				client.sendToClient(im);
			}
		}
	}
	
	private void getQuestionCourses(ConnectionToClient client, Object o) throws IOException {
		ArrayList<Course> courses = sqlcon.getQuestionCourses(o);
		iMessage im = new iMessage("QuestionCourses",courses);
		client.sendToClient(im);
	}

	private void getFieldsCourses(ConnectionToClient client, Object o) throws IOException {
		ArrayList<Course> courses = sqlcon.getFieldsCourses(o);
		iMessage im = new iMessage("FieldsCourses",courses);
		client.sendToClient(im);
	}

	private void getTeacherFields(ConnectionToClient client, Object o) throws IOException {
		ArrayList<Field> fields = sqlcon.getTeacherFields((Teacher)o);
		iMessage im = new iMessage("TeacherFields", fields);
		client.sendToClient(im);
	}


	private void logoutFunctionality(Object o) {
		if(connectedUsers.remove(((User)o).getUserName())!=null)
			System.out.println("Logged out User: "+ o );
	}
	
	public void logOutAllUsers() {
		connectedUsers.clear();
	}
	
	private void getTeacherQuestions(ConnectionToClient client, Object o) throws IOException {
		ArrayList<Question> questions = sqlcon.getTeachersQuestions((Teacher)o);
		iMessage im = new iMessage("TeachersQuestions", questions);
		client.sendToClient(im);
	}

/**
 * Send to DBMain a request to pull object solved exams from database.
 * @param client
 * @param o
 * @throws IOException
 */
	private void getSolvedExam(ConnectionToClient client, Object o) throws IOException {
		// TODO Auto-generated method stub
		iMessage im;
		if(o instanceof Student)
		{
			ArrayList<SolvedExam> studentSolvedExams = sqlcon.getStudentSolvedExams((Student)o);
			im = new iMessage("StudentSolvedExams", studentSolvedExams);
		}
		else
		{
			ArrayList<SolvedExam> teacherSolvedExams = sqlcon.getTeacherSolvedExams((Teacher)o);
			im = new iMessage("TeacherSolvedExams", teacherSolvedExams);
		}
		client.sendToClient(im);
	}

	private void loginFunctionality(ConnectionToClient client,Object o) throws IOException {
		User user = (User) o;
		iMessage result=null;
		String login = "login";
		if (connectedUsers.get(user.getUserName())!=null && connectedUsers.get(user.getUserName()).isAlive()) {
			//sending back same user to indicate user is already logged in!
			result = new iMessage("loggedInAlready",o);
		} else {
			user = sqlcon.UserLogIn((User)o);
			if (user==null) {
				//user login authentication failed
				result = new iMessage("loginFailedAuthentication",null);
			} else {
				if (user instanceof Teacher) {
					result = new iMessage(login,new Teacher((Teacher) user));
				} else if(user instanceof Principle) {
					result = new iMessage(login,new Principle((Principle) user));
				} else if(user instanceof Student) {
					result = new iMessage(login,new Student((Student) user));
				}
				connectedUsers.put(user.getUserName(), client);
			}
		}
		client.sendToClient(result);
	}

	
}
