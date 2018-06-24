package ocsf.server;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.filechooser.FileSystemView;

import SQLTools.DBMain;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.util.Duration;
import logic.ActiveExam;
import logic.AesWordDoc;
import logic.Course;
import logic.Exam;
import logic.ExamReport;
import logic.Field;
import logic.Globals;
import logic.Principle;
import logic.Question;
import logic.QuestionInExam;
import logic.SolvedExam;
import logic.Student;
import logic.Teacher;
import logic.TimeChangeRequest;
import logic.User;
import logic.iMessage;

@SuppressWarnings({ "unchecked", "rawtypes", "resource" })
public class AESServer extends AbstractServer {
	
	
	private DBMain sqlcon;
	private HashMap<User,ConnectionToClient> connectedUsers;
	
	/**
	 * easy acces to active exams
	 * HashMap with Key that is the active exams code and Value of the actual ActiveExam
	 */
	private HashMap<String,ActiveExam> activeExams;
	/**
	 * HashMap with Key of ActiveExam and Value that holds an arraylist of students who checked in to this active exam
	 */
	private HashMap<ActiveExam, ArrayList<Student>> studentsInExam;
	/**
	 * This hashmap will hold all the student that are supposed to taki this exam. 
	 * All the students in this course.
	 * the purpose of this hashmap is on each students sovedExam submittion 
	 * It will check if all the students in the course submitted the exam by removing the student frmo the arraylist
	 */
	private HashMap<ActiveExam, ArrayList<Student>> studentsCheckOutFromActiveExam;
	
	private HashMap<ActiveExam, ArrayList<SolvedExam>> studentsSolvedExams;
	
	private  HashMap<SolvedExam,AesWordDoc> solvedExamWordFiles;
	
	
	private HashMap<ActiveExam, TimeChangeRequest> timeChangeRequests;
	
	private HashMap<ActiveExam, Timeline> examTimelines;

	
	String serverDirPath = FileSystemView.getFileSystemView().getHomeDirectory()+"\\ServerFiles";
	
	String studentsExamsPath = serverDirPath+"\\Students_Exams";
	
	String examFilesPath = serverDirPath+"\\Exam_Files";

	/**
	 * constructor that connect to database and  gets the port for connecting clients
	 * @param DBHost - the host address of the database
	 * @param DBUser - the username to connect to in the host
	 * @param DBPass - the password for the user
	 * @param port - the port for the server to listen on
	 */
	public AESServer(String DBHost,String DBUser, String DBPass,int port) {
		super(port);
		sqlcon = new DBMain(DBHost, DBUser, DBPass);
		connectedUsers = new HashMap<>();
		activeExams = new HashMap<>();
		studentsInExam = new HashMap<>();
		studentsCheckOutFromActiveExam=new HashMap<>();
		//wordFiles=new HashMap<ActiveExam,AesWordDoc>();Word Files
		studentsSolvedExams = new HashMap<>();

		timeChangeRequests= new HashMap<>();
		solvedExamWordFiles=new HashMap<>();
		examTimelines = new HashMap<>();
		
		setupServerFolders();
		
		
		try {
			File fileLog = new File(serverDirPath+"\\ServerLog.txt");
			fileLog.createNewFile();
			System.setOut(new PrintStream(fileLog));
			File errorLog = new File(serverDirPath+"\\ServerErrorLog.txt");
			errorLog.createNewFile();
			System.setErr(new PrintStream(errorLog));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		//AddToWordFileList(nivsExam,doc);//Add the word file to the list of word files.
		
	}

	/**
	 * this function will create the server folders to manage students exams and hold all console logs and error logs as well as exams created by teachers for manual exams
	 */
	private void setupServerFolders() {
		
		File theDir = new File(serverDirPath);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
		    System.out.println("creating directory: " + serverDirPath);
		    try{
		        theDir.mkdir();
		    } catch (Exception e) {
				System.err.println("Could not create a directory for server");
			}
		}
		theDir = new File(studentsExamsPath);
		if (!theDir.exists()) {
		    System.out.println("creating directory: " + studentsExamsPath);
		    try{
		        theDir.mkdir();
		    } catch (Exception e) {
				System.err.println("Could not create a directory for server");
			}
		}
		theDir = new File(examFilesPath);
		if (!theDir.exists()) {
		    System.out.println("creating directory: " + examFilesPath);
		    try{
		        theDir.mkdir();
		    } catch (Exception e) {
				System.err.println("Could not create a directory for server");
			}
		}
	}

	/**
	 * this function handles msgs from clients, it knows what the client requested by the command in the iMessage recieved from him
	 * 
	 * @param msg - an Object that must be instanceof iMessage
	 * @param client - the client who sent this msg
	 */
	@Override protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
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
			case "disconnect":
				disconnectClient(client,o);
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
			case "newTimeChangeRequest":
				newTimeChangeRequest(o);
				break;
			case "studentsInCourse":
				studentsInCourse(client,o);
				break;
			case "timeChangeRequestResponse":
				timeChangeRequestResponse(o);
				break;
			case "getAllExamReport":
				getAllExamReport(client);
				break;
			case "getStudentsCourses":
				getStudentsCourses(client,o);
				break;
			case "getAllTimeChangeRequest":
				getAllTimeChangeRequest(client);
				break;
			case "getQuestionInExam":
				getQuestionInExam(client,o);
				break;
			case "getFieldsCourses":
				getFieldsCourses(client,o);
				break;
			case "getAllStudents":
				getAllStudents(client);
				break;
			case "getAllTeachers":
				getAllTeachers(client);
				break;
			case "getAllQuestions":
				getAllQuestions(client,null);
				break;
			case "getAllExams":
			    getAllExams(client,null);
			    break;
            case "getAllFields":
                getAllFields(client,null);
                break;
			case "getAllCourses":
				getAllCourses(client,null);
				break;
			case "getTeachersQuestions":
				getTeacherQuestions(client,o);
				break;
			case "getTeacherCompletedExams":
				getTeacherCompletedExams(client,o);
				break;
			case "deleteQuestion":
				deleteQuestion(client,o);
				break;
			case "addQuestion":
				addQuestion(client,o);
				break;
			case "editQuestion":
				editQuestion(client,o);
				break;
			case "getTeachersActiveExams":
				getTeachersActiveExams(client,o);
				break;
			case "DeleteExam":
				deleteExam(client,o);
				break;
			case "getTeachersExam":
				getTeachersExams(client,o);
				break;
			case "getStudentsSolvedExams":
				getSolvedExam(client,o);
				break;
			case "LOCKActiveExam":
				lockActiveExams(client,o);
				break;
			case "studentIsInActiveExam":
				studentIsInActiveExam(client,o);
				break;
			case "getTeacherSolvedExams":
				getSolvedExam(client,o);
				break;
			case "updateSolvedExam":
				updateSolvedExam(client,o);
				break;
			case "getAllActiveExams":
				getAllActiveExams(client);
				break;
			case "getActiveExam":
				getActiveExam(client,o);
				break;
			case "CourseQuestions":
				getCourseQuestions(client,o);
				break;
			case "getFieldCourses":
				getFieldCourses(client,o);
				break;
			case "getFieldTeachers":
				getFieldTeachers(client,o);
				break;
			case "addExam":
				addExam(client,o);
				break;
			case "StudentCheckInToExam":
				checkInStudentToActiveExam(client, o);
				break;
			case "GetManualExam"://Word Files
				createManualExam(client,o);
				break;
			case "getcourseExams":
				getcourseExams(client,o);
				break;
			case "FinishedSolvedExam":
				SetFinishedSolvedExam(client,o);
				break;
			case "InitializeActiveExams":
				InitializeActiveExams(o);
				break;
			case "GetStudentsManualExam":
				GetStudentsManualExam(client,o);
				break;
			case "UploadSolvedExam":
				UploadSolvedExam(o);
				break;
			default:
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * when a client disconnects he sends a msg to the server notifying it to log the user out of the server 
	 * @param client - the client sending the msg
	 * @param o - the user that is logged in
	 * @throws IOException - in case it fails to close the connection to the client it receives
	 */
	private void disconnectClient(ConnectionToClient client, Object o) throws IOException {
		client.close();
		if (o instanceof User) {
			logoutFunctionality(o);
		}
		
	}


	/**
	* Hook Method executed right before server closes while still listening
	* this is part of AbstractServer functionality
	*/
	protected void serverClosed() {
		System.err.println("The server is about to close all its connections\nnotifying clients about disconnection and disconnecting from database");
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
	protected void clientConnected(ConnectionToClient client) {
		System.out.println("Client: "+client+" Has Connected to Server.");
	}

	/**
	* Hook method called each time a client disconnects.
	* The default implementation does nothing. The method
	* may be overridden by subclasses but should remains synchronized.
	*
	* @param client the connection with the client.
	*/
	synchronized protected void clientDisconnected(ConnectionToClient client) {
		System.err.println("Client: " + client + " has diconnected from the server.");
	}

	/**
	* Hook method called each time an exception is thrown in a
	* ConnectionToClient thread.
	* The method may be overridden by subclasses but should remains
	* synchronized.
	*
	* @param client the client that raised the exception.
	* @param exception the exception thrown.
	*/
	synchronized protected void clientException(ConnectionToClient client, Throwable exception) {
		System.err.println("A client has thrown an exception: (following exception couse client to disconnect)\n");
		exception.printStackTrace();
		System.err.println("End of client exception");
	}

	/**
	* Hook method called when the server stops accepting
	* connections because an exception has been raised.
	* The default implementation does nothing.
	* This method may be overriden by subclasses.
	*
	* @param exception the exception raised.
	*/
	protected void listeningException(Throwable exception) {
		System.err.println("the server stops accepting connections because an exception has been raised!");
		exception.printStackTrace();
	}

	 /**
	 * Hook method called when the server starts listening for
	 * connections.  The default implementation does nothing.
	 * The method may be overridden by subclasses.
	 */
	protected void serverStarted() {
		System.err.println("Server Started.");
	}

	/**
	 * Hook method called when the server stops accepting
	 * connections.  The default implementation
	 * does nothing. This method may be overriden by subclasses.
	 */
	protected void serverStopped() {
		System.err.println("Server Stoped.");
	}
	
	// ######################################## TEAM Start Adding Functions from here ###################################################


	/**
	 * Get an active exam and remove it from active exams Hashmap.
	 * @param ae
	 */

	public void lockActiveExam(ActiveExam ae)
	{
		if (studentsInExam.get(ae)!=null) {
			iMessage msg = new iMessage("ExamLocked", ae);
			for(Student s: studentsInExam.get(ae)) {
				try {
					if (connectedUsers.get(s)!=null && studentsCheckOutFromActiveExam.get(ae).contains(s)) {
						connectedUsers.get(s).sendToClient(msg);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//telling the server to wait 7 seconds for all students to send their solved exam to the server
			Timeline timeline = new Timeline();
			examTimelines.put(ae, timeline);
	        timeline.setCycleCount(Timeline.INDEFINITE);
	        timeline.getKeyFrames().add(
	                new KeyFrame(Duration.seconds(10),
	                  new EventHandler() {
	                    // KeyFrame event handler
	                    public void handle(Event event) {
	                    	System.out.println("Server generating Exam report:" + ae.getCode() );
	                    	timeline.stop();
	                    	GenerateActiveExamReport(ae);
	                    }
	                  }));
	        timeline.playFromStart();
		} else {
			System.err.println(ae + "not found in studentsInExam HashMap!");
		}
	}
	
	private void setActiveExamTimeline(ActiveExam ae) {
		Long examTime = (long) ae.getDuration()*60;
		TimeChangeRequest tcr = timeChangeRequests.get(ae);
		if(tcr!=null) {
			Timeline tm = examTimelines.remove(ae);
			if(tm!=null) tm.stop();
			examTime = tcr.getNewTime()-(((new Date(new java.util.Date().getTime()).getTime()-ae.getDate().getTime()))/60000);
		}
		Timeline timeline = new Timeline();
		examTimelines.put(ae, timeline);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(examTime),
                  new EventHandler() {
                    // KeyFrame event handler
                    public void handle(Event event) {
                    	System.out.println("Server Locking Active Exam:" + ae.getCode() );
                            timeline.stop();
                            lockActiveExam(ae);
                    }
                  }));
        timeline.playFromStart();
		
	}

	/**
	 * return all available active exams.
	 * @param client
	 * @throws IOException
	 */
	private void getAllActiveExams(ConnectionToClient client) throws IOException {
		ArrayList<ActiveExam> allActiveExams=new ArrayList<ActiveExam>();
		for(String ae: activeExams.keySet())
		{
			allActiveExams.add(activeExams.get(ae));
		}
		iMessage im = new iMessage("AllActiveExams",allActiveExams);
		client.sendToClient(im);
	}

	/**
	 * Get an active exam code and sends to client that active exam if exist, otherwise sends null.
	 * @param client
	 * @param o
	 * @throws IOException
	 */
	private void getActiveExam(ConnectionToClient client,Object o) throws IOException {
		ActiveExam ae = activeExams.get((String)o);
		iMessage im = new iMessage("ActiveExam",ae);
		client.sendToClient(im);
	}

	
	private void getTeachersActiveExams(ConnectionToClient client,Object o) throws IOException {
		ArrayList<ActiveExam> ac = new ArrayList<>();
		for(String activeExamCode: activeExams.keySet()) {
			if(activeExams.get(activeExamCode).getActivator().equals((Teacher)o)) {
				ac.add(activeExams.get(activeExamCode));
			}
		}
		iMessage im = new iMessage("TeachersActiveExams",ac);
		client.sendToClient(im);
	}
	
	private void lockActiveExams(ConnectionToClient client, Object o) throws IOException {
		lockActiveExam((ActiveExam) o);
		client.sendToClient(new iMessage("lockedExam", null));
	}
	
	private void getQuestionCourses(ConnectionToClient client, Object o) throws IOException {
		ArrayList<Course> courses = sqlcon.getQuestionCourses(o);
		iMessage im = new iMessage("QuestionCourses",courses);
		client.sendToClient(im);
	}


	private void studentsInCourse(ConnectionToClient client, Object o) throws IOException {
		ArrayList<Student> students = sqlcon.GetAllStudentsInCourse((Course)o);
		iMessage im = new iMessage("studentsInCourse",students);
		client.sendToClient(im);
	}
	
	private void getTeachersExams(ConnectionToClient client, Object o) throws IOException {
		ArrayList<Exam> exams = sqlcon.getTeachersExams((Teacher) o);
		iMessage im = new iMessage("TeachersExams",exams);
		client.sendToClient(im);
	}
	
	private void getFieldsCourses(ConnectionToClient client, Object o) throws IOException {
		ArrayList<Course> courses = sqlcon.getFieldsCourses(o);
		iMessage im = new iMessage("FieldsCourses",courses);
		client.sendToClient(im);
	}
	
	private void getAllTimeChangeRequest(ConnectionToClient client) throws IOException {
		ArrayList<TimeChangeRequest> result = new ArrayList<>();
		for (ActiveExam activeExam : timeChangeRequests.keySet()) {
			result.add(timeChangeRequests.get(activeExam));
		}
		iMessage im = new iMessage("allTimeChangeRequests",result);
		client.sendToClient(im);
	}

	private void studentIsInActiveExam(ConnectionToClient client, Object obj) throws IOException {
		Object[] o=(Object[])obj;
		
		Student s = (Student) o[0];
		ActiveExam ae = (ActiveExam) o[1];
		iMessage im;
		if (studentsCheckOutFromActiveExam.get(ae)!=null && studentsCheckOutFromActiveExam.get(ae).contains(s))
			im = new iMessage("studentInExam",true);
		else 
			im = new iMessage("studentInExam",false);
		client.sendToClient(im);
	}
	private void updateSolvedExam(ConnectionToClient client, Object o) throws IOException {
		Integer updatestatus = sqlcon.UpdateSolvedExam((SolvedExam)o);
		iMessage im = new iMessage("solvedExamupdated",updatestatus);
		client.sendToClient(im);
	}
	
	private void getQuestionInExam(ConnectionToClient client, Object o) throws IOException {
		ArrayList<QuestionInExam> questions = sqlcon.getQuestionsInExam((String)o);
		iMessage im = new iMessage("TeachersQuestions", questions);
		client.sendToClient(im);
	}



	private void getStudentsCourses(ConnectionToClient client, Object o) throws IOException {
		ArrayList<Course> courses = sqlcon.getStudentsCourses((User)o);
		iMessage im = new iMessage("studentsCourses",courses);
		client.sendToClient(im);
	}
	
	private void getTeacherCompletedExams(ConnectionToClient client, Object o) throws IOException {
		ArrayList<ExamReport> completedExams = sqlcon.getTeachersCompletedExams((Teacher) o);
		iMessage im = new iMessage("TeacherCompletedExams", completedExams);
		client.sendToClient(im);
	}
	
	private void deleteQuestion(ConnectionToClient client, Object o) throws IOException {
		int effectedRowCount = sqlcon.deleteQuestion((Question) o);
		iMessage im = new iMessage("deletedQuestion", new Integer(effectedRowCount));
		client.sendToClient(im);
		
	}
	
	private void addQuestion(ConnectionToClient client, Object o) throws IOException {
		int effectedRowCount = sqlcon.addQuestion((Question) o);
		iMessage im = new iMessage("addedQuestion", new Integer(effectedRowCount));
		client.sendToClient(im);
	}
	
	private void addExam(ConnectionToClient client, Object o) throws IOException {
		int effectedRowCount = sqlcon.addexam((Exam) o);
		iMessage im = new iMessage("addExam", new Integer(effectedRowCount));
		client.sendToClient(im);
	}
	
	private void getAllStudents(ConnectionToClient client) throws IOException {
		ArrayList<User> students = sqlcon.GetAllUsersByType(0);
		iMessage im = new iMessage("allStudents",students);
		client.sendToClient(im);
	}

	/**
	 * This method is in use when the student asks a manual exam(he gets it from the system).
	 * @param client
	 * @param o
	 * @throws IOException
	 */
	private void GetStudentsManualExam(ConnectionToClient client, Object o) throws IOException {
		SolvedExam se = (SolvedExam)o;
		String filePath = studentsExamsPath+"\\"+se.getStudent().getID()+"\\"+se.examIdToString()+".doc";
		
		File examFile = new File(filePath);
		AesWordDoc myExamFileDes;
		myExamFileDes = new AesWordDoc(filePath);

		// if the directory does not exist, create it
		if (examFile.exists()) {
			myExamFileDes.setSize((int) examFile.length());
			myExamFileDes.initArray((int) examFile.length());
			
			try {
				FileInputStream fis = new FileInputStream(examFile);
				BufferedInputStream bis = new BufferedInputStream(fis);
				bis.read(myExamFileDes.getMybytearray(),0,(int) examFile.length());
			} catch (IOException e) {
				System.err.println("Could Not read from the Exam File Buffer");
				e.printStackTrace();
			}
	    } else {
	    	System.err.println("Could not find students exam file:" + examFile.getAbsolutePath() + " sending empty file");
			myExamFileDes.setSize((int) examFile.length());
			myExamFileDes.initArray((int) examFile.length());
	    }
		iMessage msg = new iMessage("yourExamFile", myExamFileDes);
		client.sendToClient(msg);
	}
	
	private void getAllTeachers(ConnectionToClient client) throws IOException {
		ArrayList<User> teachers = sqlcon.GetAllUsersByType(1);
		iMessage im = new iMessage("allTeachers",teachers);
		client.sendToClient(im);
	}

	private void editQuestion(ConnectionToClient client, Object o) throws IOException {
		int effectedRowCount = sqlcon.editQuestion((Question) o);
		iMessage im = new iMessage("editedQuestion", new Integer(effectedRowCount));
		client.sendToClient(im);
	}

	private void deleteExam(ConnectionToClient client, Object o) throws IOException {
		int effectedRowCount = sqlcon.deleteExam((Exam) o);
		iMessage im = new iMessage("deletedExam", new Integer(effectedRowCount));
		client.sendToClient(im);
	}
	
	private void getTeacherFields(ConnectionToClient client, Object o) throws IOException {
		ArrayList<Field> fields = sqlcon.getTeacherFields((Teacher)o);
		iMessage im = new iMessage("TeacherFields", fields);
		client.sendToClient(im);
	}

	private void logoutFunctionality(Object o) {
		User user = (User) o;
		if(connectedUsers.remove(user)!=null)
			System.out.println("Logged out User: "+ o );
	}
	
	public void clearHashes() {
		connectedUsers.clear();
		if (studentsInExam!=null) {
			for(ActiveExam a: studentsInExam.keySet()) {
				studentsInExam.get(a).clear();
			}
		}
		if (studentsSolvedExams!=null) {
			for(ActiveExam a: studentsSolvedExams.keySet()) {
				studentsSolvedExams.get(a).clear();
			}
		}
	}
	
	private void getCourseQuestions(ConnectionToClient client, Object o) throws IOException {
		ArrayList<Question> questions = sqlcon.CourseQuestions((Course)o);
		iMessage im = new iMessage("CourseQuestions", questions);
		client.sendToClient(im);
	}
	
	private void getFieldCourses(ConnectionToClient client, Object o) throws IOException {
		ArrayList<Course> Courses = sqlcon.getFieldCourses((Field)o);
		iMessage im = new iMessage("FieldCourses",Courses);
		client.sendToClient(im);
	}

	private void getFieldTeachers(ConnectionToClient client, Object o) throws IOException{
		ArrayList<Teacher> teachers = sqlcon.getFieldTeachers((Field)o);
		iMessage im = new iMessage("FieldTeachers",teachers);
		client.sendToClient(im);
	}
	
	private void getcourseExams(ConnectionToClient client, Object o) throws IOException {
		ArrayList<Exam> exams = sqlcon.getcourseExams((Course) o);
		iMessage im = new iMessage("TeachersExams",exams);
		client.sendToClient(im);
	}
	
	private void timeChangeRequestResponse(Object o) throws IOException {
		if (o instanceof TimeChangeRequest) {
			TimeChangeRequest tc = (TimeChangeRequest) o;
			if(tc.getStatus()) {
				iMessage msg = new iMessage("studentUpdateExamTime", tc.getNewTime());
				for(Student u : studentsInExam.get(tc.getActiveExam())) {
					if(studentsCheckOutFromActiveExam.get(tc.getActiveExam()).contains(u)) {
						if (connectedUsers.get(u)!=null) connectedUsers.get(u).sendToClient(msg);
					}
				}
				updateActiveExamHashmaps(tc.getActiveExam().getCode(),tc.getNewTime());
				setActiveExamTimeline(tc.getActiveExam());
			}
			timeChangeRequests.remove(tc.getActiveExam());
		}
		
	}
	
	private void updateActiveExamHashmaps(String code,Long newTime) {
		ActiveExam ae = activeExams.get(code);
		Object obj;
		if(ae!=null) {
			int totalNewTime = activeExams.get(code).getDuration()+(int)(long)newTime;
			ActiveExam newAE = new ActiveExam(activeExams.get(code));
			newAE.setDuration(totalNewTime);
			if(activeExams.containsKey(code)) {
				activeExams.remove(code);
				activeExams.put(code, newAE);
			}			
			if(studentsInExam.containsKey(ae)) {
				obj = studentsInExam.remove(ae);
				studentsInExam.put(newAE, (ArrayList<Student>) obj);
			}
			if(studentsCheckOutFromActiveExam.containsKey(ae)) {
				obj = studentsCheckOutFromActiveExam.remove(ae);
				studentsCheckOutFromActiveExam.put(newAE, (ArrayList<Student>) obj);
			}
			if(studentsSolvedExams.containsKey(ae)) {
				obj = studentsSolvedExams.remove(ae);
				studentsSolvedExams.put(newAE, (ArrayList<SolvedExam>) obj);
			}
			if(examTimelines.containsKey(ae)) {
				obj = examTimelines.remove(ae);
				examTimelines.put(newAE, (Timeline) obj);
			}
		}		
	}

	private void getTeacherQuestions(ConnectionToClient client, Object o) throws IOException {
		ArrayList<Question> questions = sqlcon.getTeachersQuestions((Teacher)o);
		iMessage im = new iMessage("TeachersQuestions", questions);
		client.sendToClient(im);
	}

    /**
     * Method retrieves all written exam templates from the database
     * @param client - the user currently connected ( used by the Principal )
     * @param o - parameter for iMessage ( retrieving from a known table - null )
     * @throws IOException - exception thrown if object construction in the database encounters a problem
     */
	private void getAllQuestions(ConnectionToClient client, Object o) throws IOException {
		ArrayList<Question> questions = sqlcon.getAllQuestions();
		iMessage rtrnmsg = new iMessage("AllQuestions", questions);
		client.sendToClient(rtrnmsg);
	}

	private void getAllExamReport(ConnectionToClient client) throws IOException {
		ArrayList<ExamReport> reports = sqlcon.getAllExamReports();
		iMessage rtrnmsg = new iMessage("AllExamReports", reports);
		client.sendToClient(rtrnmsg);
	}

    /**
     * Method retrieves all written questions from the database
     * @param client - the user currently connected ( used by the Principal )
     * @param o - parameter for iMessage ( retrieving from a known table - null )
     * @throws IOException - exception thrown if object construction in the database encounters a problem
     */
    private void getAllExams(ConnectionToClient client, Object o) throws IOException {
	    ArrayList<Exam> exams = sqlcon.getAllExams();
	    iMessage rtrnmsg = new iMessage("AllExams",exams);
	    client.sendToClient(rtrnmsg);
    }

    /**
     * Method retrieves all fields from the database
     * @param client - the user currently connected ( used by the Principal )
     * @param o - parameter for iMessage ( retrieving from a known table - null )
     * @throws IOException - exception thrown if object construction in the database encounters a problem
     */
    private void getAllFields(ConnectionToClient client, Object o) throws IOException {
        ArrayList<Field> fields = sqlcon.getAllFields();
        iMessage rtrnmsg = new iMessage("AllFields", fields);
        client.sendToClient(rtrnmsg);
    }

	/**
	 * Method retrieves all courses from the database
	 * @param client - the user currently connected ( used by the Principal )
	 * @param o - parameter for iMessage ( retrieving from a known table - null )
	 * @throws IOException - exception thrown if object construction in the database encounters a problem
	 */
	private void getAllCourses(ConnectionToClient client, Object o) throws IOException{
		ArrayList<Course> courses = sqlcon.getAllCourses();
		iMessage rtrnmsg = new iMessage("AllCourses", courses);
		client.sendToClient(rtrnmsg);
    }

    /**
	 * Send to DBMain a request to pull solved exams from database.
	 * @param client
	 * @param o
	 * @throws IOException
	 */
	private void getSolvedExam(ConnectionToClient client, Object o) throws IOException {
		iMessage im;
		if(o instanceof Student) {
			ArrayList<SolvedExam> studentSolvedExams = sqlcon.getStudentsSolvedExams((Student)o);
			im = new iMessage("StudentsSolvedExams", studentSolvedExams);
		}
		else {
			ArrayList<SolvedExam> teacherSolvedExams = sqlcon.getTeacherSolvedExams((Teacher)o);
			im = new iMessage("TeacherSolvedExams", teacherSolvedExams);
		}
		client.sendToClient(im);
	}

	private void loginFunctionality(ConnectionToClient client,Object o) throws IOException {
		User user = (User) o;
		iMessage result=null;
		String login = "login";
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
			if (connectedUsers.get(user)!=null && connectedUsers.get(user).isAlive()) {
				//sending back same user to indicate user is already logged in!
				result = new iMessage("loggedInAlready",o);
			} else {
				connectedUsers.put(user, client);
			}
		}
		client.sendToClient(result);
	}
	/**
	 * When teacher activate an exam he add it to the ActiveExams lists.
	 * @param o
	 */
	private void InitializeActiveExams( Object o)
	{
		if(o instanceof ActiveExam) {
			ActiveExam ae=(ActiveExam)o;
			studentsInExam.put(ae, new ArrayList<Student>());
			studentsSolvedExams.put(ae, new ArrayList<SolvedExam>());
			activeExams.put(ae.getCode(), ae);
			studentsCheckOutFromActiveExam.put(ae, sqlcon.GetAllStudentsInCourse(ae.getCourse()));
			setActiveExamTimeline(ae);
		}
	}
	

	private void newTimeChangeRequest(Object o) throws IOException {
		if (o instanceof TimeChangeRequest) {
			TimeChangeRequest tc = (TimeChangeRequest) o;
			if (activeExams.get(tc.getActiveExam().getCode())!=null) {
				timeChangeRequests.put(tc.getActiveExam(), tc);
				for(User u: connectedUsers.keySet()) {
					if(u instanceof Principle) {
						connectedUsers.get(u).sendToClient(new iMessage("newTimeChangeRequest", tc));
						break;
					}
				}
			}
		}
	}


	/**
	 * Get an object[2] when object[0]=ActiveExam,object[1]=Student and add the student to the list.
	 * In other words Student is check in to the active exam.
	 * @param client
	 * @param obj
	 * @throws IOException 
	 */
	private void checkInStudentToActiveExam(ConnectionToClient client,Object obj) throws IOException {
		Object[] o = (Object[])obj;
		ActiveExam ae = (ActiveExam) o[1];
		Student s = (Student) o[0];
		System.out.println("checking in student to active exam:" + ae.getExam().toString() + " Duration:" +ae.getDuration());
		if(!isInActiveExam(s, ae)) {
			if(studentsInExam.get(ae)!=null) {
				studentsInExam.get(ae).add(s);
				client.sendToClient(new iMessage("StudentCheckedInToExam",true));
			}
		} else {
			client.sendToClient(new iMessage("StudentCantCheckedInToExam",false));
		}

		
	}
	
	/**
	 *  Send to client a Manual Exam word File.
	 * @param client
	 * @param o
	 * @throws IOException
	 */

	private void createManualExam(ConnectionToClient client, Object o) throws IOException {
		ActiveExam ae = (ActiveExam)o;
		AesWordDoc myExamFileDes = new AesWordDoc(ae.examIdToString()+".doc");
		myExamFileDes.CreateWordFile(ae, examFilesPath+"\\"+ae.examIdToString()+".doc");
		
		File examFile = new File(examFilesPath+"\\"+ae.examIdToString()+".doc");
		myExamFileDes.setSize((int) examFile.length());
		myExamFileDes.initArray((int) examFile.length());
		
		FileInputStream fis = new FileInputStream(examFile);
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		bis.read(myExamFileDes.getMybytearray(),0,(int) examFile.length());
		iMessage msg = new iMessage("yourExamFile", myExamFileDes);
		client.sendToClient(msg);
		
		try {
			if(examFile.delete())
				System.out.println("file was deleted!");
			else 
				System.err.println("File could not be deleted");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
 		
	}
	
	/**
	 * Return true if the student is in active exam at the moment, otherwise return false.
	 * @param s
	 * @param ae
	 * @return
	 */
	private boolean isInActiveExam(Student s,ActiveExam ae) {
		if (studentsInExam.get(ae)==null) return false;
		else return studentsInExam.get(ae).contains(s);
	}
	
	public void GenerateActiveExamReport(ActiveExam ae) {
		//handeling student who did not participate at all
		int notInTime = 0;
		int submitted = studentsSolvedExams.get(ae).size();
		for(Student s: studentsCheckOutFromActiveExam.get(ae)) {
			studentsSolvedExams.get(ae).add(new SolvedExam(0, false, new HashMap<>(), s, "", new HashMap<>(), 0, ae.getCode(), ae.getType(), ae.getDate(), ae.getActivator(), ae));
			notInTime++;
		}
		ArrayList<SolvedExam> solvedExams = studentsSolvedExams.get(ae);
		int participated = studentsInExam.get(ae).size();
		Date lockDate = new Date(new java.util.Date().getTime()); //now
		ExamReport eReport = new ExamReport(ae.getCode(), ae.getType(), ae.getDate(), ae, ae.getActivator(), solvedExams, participated, submitted, notInTime, lockDate);
		if (solvedExams.size()==0) {
			System.out.println("No one submitted an exam for:" +ae.getCode() + " so a report creation was skiped");
		} else if (sqlcon.insertCompletedExam(eReport)>0) {
			System.out.println("Exam:" +ae.getCode() +" was inserted into database.");
		} else {
			System.err.println("there were solved exams in hash map but could not insert the examReport into database - Somthing went wrong");
		}
		activeExams.remove(ae.getCode()); 
		studentsInExam.remove(ae); 
		studentsCheckOutFromActiveExam.remove(ae); 
		studentsSolvedExams.remove(ae); 
		//wordFiles.remove(ae);
		timeChangeRequests.remove(ae);
	}

	
	/**
	 * Add solved exam to the list so we can generate all solved exams to report later, 
	 * and remove the student from the CheckOut list which her purpose is to see if all students have submitted their exam.
	 * @param obj
	 * @throws IOException 
	 */
	public void SetFinishedSolvedExam(ConnectionToClient client,Object obj) throws IOException
	{
		System.out.println("Checking out student to remember he already submitted his exam");
		Object[] o = (Object[])obj;
		ActiveExam e=(ActiveExam) o[0];
		SolvedExam solved=(SolvedExam) o[1];
		Student student=(Student) o[2];
		ArrayList<SolvedExam> studentExams = studentsSolvedExams.get(e);
		if(studentExams==null) {
			System.err.println("Did not find the active exam:" + e.toString());
			return;
		}
		java.util.Date now = new java.util.Date();
		int timeCompleted = (int) (((now.getTime()-e.getDate().getTime())/1000)/60);
		solved.setCompletedTimeInMinutes(timeCompleted);
		studentExams.add(solved);
		studentsSolvedExams.put(e, studentExams);
		ArrayList<Student> checkedout = studentsCheckOutFromActiveExam.get(e);
		if(!checkedout.remove(student))
			System.err.println("Coulnd not remove:" +student + " from checkout students");
		if(studentsCheckOutFromActiveExam.get(e).isEmpty())//If all students have submitted the exam.
			GenerateActiveExamReport(e);
		//AesWordDoc doc=(AesWordDoc) o[3];
		//if(doc!=null)//If it was a manual exam we add it to the list of manual solved exam.
			//solvedExamWordFiles.put(solved, doc);
		client.sendToClient(new iMessage("SolvedExamSubmittedSuccessfuly",null));
	}


	private void UploadSolvedExam(Object obj) throws IOException {
		System.out.println("Student Uploaded Solved Exam");
		Object[] o=(Object[])obj;
		
		AesWordDoc file=(AesWordDoc) o[0];
		SolvedExam solvedExam=(SolvedExam) o[1];
		String savePath = studentsExamsPath+"/"+solvedExam.getStudent().getID();
		File theDir = new File(savePath);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
		    System.out.println("creating directory: " + savePath);
		    boolean result = false;
		    try{
		        theDir.mkdir();
		        result = true;
		    } 
		    catch(SecurityException se){
		        System.err.println("could not create Dir!");
		    }        
		    if(result) {    
		        System.out.println("DIR created now saving file in dir:"+savePath);  
		        File newFile = new File(savePath+"/"+solvedExam.examIdToString()+".doc");
				FileOutputStream out = new FileOutputStream(newFile);
				out.write(file.getMybytearray());
				out.close();
				
				solvedExamWordFiles.put(solvedExam, file);
				System.out.println("Students Exam File was Created and is in:"+newFile.getPath());
		    } else {
		    	System.err.println("Failed to create Diractory and upload file to server!");
		    }
		}
	}

}