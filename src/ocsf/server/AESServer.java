package ocsf.server;


import SQLTools.DBMain;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.util.Duration;
import logic.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings({ "unchecked", "rawtypes" })
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
	
	/*Word Files
	 // HashMap with Key - ActiveExam and Value holds the Word files (Only for manual).
	 
	private static HashMap<ActiveExam,AesWordDoc> wordFiles;
	
	private static HashMap<SolvedExam,AesWordDoc> solvedExamWordFiles;
	/*/
	
	private HashMap<ActiveExam, TimeChangeRequest> timeChangeRequests;
	
	private HashMap<ActiveExam, Timeline> examTimelines;

	public AESServer(String DBHost,String DBUser, String DBPass,int port) {
		super(port);
		sqlcon = new DBMain(DBHost, DBUser, DBPass);
		connectedUsers = new HashMap<User,ConnectionToClient>();
		activeExams = new HashMap<String,ActiveExam>();
		studentsInExam = new HashMap<ActiveExam,ArrayList<Student>>();
		studentsCheckOutFromActiveExam=new HashMap<ActiveExam,ArrayList<Student>>();
		//wordFiles=new HashMap<ActiveExam,AesWordDoc>();Word Files
		studentsSolvedExams = new HashMap<>();
		timeChangeRequests= new HashMap<>();
		//solvedExamWordFiles=new HashMap<>();
		examTimelines = new HashMap<>();

		/**
		 * Added a virtual temporary Active Exam to Server!
		 */
		Teacher teacher = new Teacher(302218136, "daniel", "tibi", "Daniel Tibi");
		ActiveExam tibisExam = new ActiveExam("ac13", 1, new Date(new java.util.Date().getTime()),
				sqlcon.getExam("010101"),teacher);
		InitializeActiveExams(tibisExam);
		
		/**
		 * Added a virtual temporary Active Exam to Server!
		 */
		teacher = new Teacher(204360317, "niv", "mizrahi", "Niv Mizrahi");
		ActiveExam nivsExam = new ActiveExam("d34i", 0, new Date(new java.util.Date().getTime()),sqlcon.getExam("030103"),teacher);
		InitializeActiveExams(nivsExam);
		
		
		//AddToWordFileList(nivsExam,doc);//Add the word file to the list of word files.
		
	}

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
			case "getQuestionInExam":
				getQuestionInExam(client,o);
				break;
			case "getFieldsCourses":
				getFieldsCourses(client,o);
				break;
			case "getAllQuestions":
				getAllQuestions(client,null);
				break;
			case"getAllExams":
			    getAllExams(client,null);
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
			case "addExam":
				addExam(client,o);
				break;
			case "StudentCheckInToExam":
				checkInStudentToActiveExam(client, o);
				break;
			/*case "GetManualExam"://Word Files
				GetManuelExam(client,o);
				break;/*/
			case "getcourseExams":
				getcourseExams(client,o);
				break;
			case "FinishedSolvedExam":
				SetFinishedSolvedExam(client,o);
				break;
			case "InitializeActiveExams":
				InitializeActiveExams(o);
				break;
			/*case "CreateDocFile":
				CreateDocFile(client,o);
				break;/*/
			/*case "SystemCheckExam":
				SystemCheckSolvedExam(client,o);
				break;/*/
				
			default:
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
		System.out.println("Client: " + client + " has diconnected from the server.");
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
		System.err.println("Client: " + client + " has thrown an exception:\n" + exception.getStackTrace());
	}

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
	protected void serverStarted() {
		System.out.println("Server Started.");
	}

	/**
	 * Hook method called when the server stops accepting
	 * connections.  The default implementation
	 * does nothing. This method may be overriden by subclasses.
	 */
	protected void serverStopped() {
		System.out.println("Server Stoped.");
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
				Student student = new Student(0, s.getUserName(), s.getPassword(), null);
				try {
					if (connectedUsers.get(student)!=null) {
						connectedUsers.get(student).sendToClient(msg);
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
			System.out.println(ae + "not found in studentsInExam HashMap!");
		}
	}
	
	private void setActiveExamTimeline(ActiveExam ae, TimeChangeRequest tcr) {
		Long examTime = (long) ae.getDuration()*60;
		if(tcr!=null) {
			examTimelines.get(ae).stop();
			examTimelines.remove(ae);
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
		iMessage im = new iMessage("ActiveExam",activeExams.get((String)o));
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
		user = new User(0, user.getUserName(), user.getPassword(), null);
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
					Student s = new Student(0, u.getUserName(), u.getPassword(), null);
					connectedUsers.get(s).sendToClient(msg);
				}
			} 
			timeChangeRequests.remove(tc.getActiveExam());
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
	 * Send to DBMain a request to pull object solved exams from database.
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
		if (connectedUsers.get(user)!=null && connectedUsers.get(user).isAlive()) {
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
				connectedUsers.put((User)o, client);
			}
		}
		client.sendToClient(result);
	}
	/**
	 * When teacher activate an exam he add it to the ActiveExams lists.
	 * @param ae
	 */
	private void InitializeActiveExams( Object o)
	{
		ActiveExam ae=(ActiveExam)o;
		studentsInExam.put(ae, new ArrayList<Student>());	
		studentsSolvedExams.put(ae, new ArrayList<SolvedExam>());
		activeExams.put(ae.getCode(), ae);
		studentsCheckOutFromActiveExam.put(ae, sqlcon.GetAllStudentsInCourse(ae.getCourse()));
		setActiveExamTimeline(ae,null);
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
	 * @param o
	 * @throws IOException 
	 */
	private void checkInStudentToActiveExam(ConnectionToClient client,Object obj) throws IOException {
		Object[] o = (Object[])obj;
		ActiveExam ae = (ActiveExam) o[1];
		Student s = (Student) o[0];
		if(!isInActiveExam(s, ae)) {
			if(studentsInExam.get((ActiveExam)o[1])!=null) {
				studentsInExam.get((ActiveExam)o[1]).add((Student)o[0]);
				client.sendToClient(new iMessage("StudentCheckedInToExam",true));
			}
		} else {
			client.sendToClient(new iMessage("StudentCantCheckedInToExam",false));
		}

		
	}
	
	/*Word Files
	/**
	 * Create a Document file when the teacher activate a manual exam.
	 * @param active
	 */
	/*/
	private void CreateWordFile(ActiveExam activeExam)
	{
		//Create document
				AesWordDoc doc=new AesWordDoc();
						
				//Create title paragraph
				XWPFParagraph titleParagraph=doc.createParagraph();
				titleParagraph.setAlignment(ParagraphAlignment.CENTER);
				XWPFRun runTitleParagraph=titleParagraph.createRun();
				runTitleParagraph.setBold(true);
				runTitleParagraph.setItalic(true);
				runTitleParagraph.setColor("00FF00");
				runTitleParagraph.setText(activeExam.getExam().getCourse().getName());
				runTitleParagraph.addBreak();
				runTitleParagraph.addBreak();
						
				//Create exam details paragraph
				XWPFParagraph examDetailsParagraph=doc.createParagraph();
				examDetailsParagraph.setAlignment(ParagraphAlignment.RIGHT);
				XWPFRun runOnExamDetailsParagraph=examDetailsParagraph.createRun();
				runOnExamDetailsParagraph.setText("Field: "+activeExam.getExam().getField().getName());
				runOnExamDetailsParagraph.addBreak();
				runOnExamDetailsParagraph.setText("Date: "+activeExam.getDate());
				runOnExamDetailsParagraph.addBreak();
						
				//Create question+answers paragraph
				XWPFParagraph questionsParagraph=doc.createParagraph();
				questionsParagraph.setAlignment(ParagraphAlignment.RIGHT);
				XWPFRun runOnquestionsParagraph=questionsParagraph.createRun();
				int questionIndex=1;
				ArrayList<QuestionInExam> questionsInExam=activeExam.getExam().getQuestionsInExam();
				for(QuestionInExam qie:questionsInExam)//Sets all questions with their info on screen.
				{
					if(qie.getStudentNote()!=null)
					{
						runOnquestionsParagraph.setText(qie.getStudentNote());
						runOnquestionsParagraph.addBreak();
					}
					runOnquestionsParagraph.setText(questionIndex+". "+qie.getQuestionString()+" ("+qie.getPointsValue()+" Points)");
					runOnquestionsParagraph.addBreak();
					for(int i=1;i<5;i++)
					{
						runOnquestionsParagraph.setText((char)(i+96)+". "+qie.getAnswer(i));
						runOnquestionsParagraph.addBreak();
					}
				}
				runOnquestionsParagraph.addBreak();
				runOnquestionsParagraph.addBreak();
						
				//Create good luck paragraph
				XWPFParagraph GoodLuckParagraph=doc.createParagraph();
				XWPFRun runOnGoodLuckParagraph=GoodLuckParagraph.createRun();
				runOnGoodLuckParagraph.setText("Good Luck!");
				
				AddToWordFileList(activeExam,doc);

	}
	
		/**
		 * Add Document file exam to the list of word file exams(export as word file in the StudentSolvesExamFrame).
		 * @param active
		 * @param doc
		 */
		/*WordFiles
		 private void AddToWordFileList(ActiveExam active, AesWordDoc doc) {
			wordFiles.put(active, doc);
		}/*/
	
	/*Word Files
	/**
	 * Send to client a Manual Exam word File.
	 * @param client
	 * @param o
	 * @throws IOException
	 
	private void GetManuelExam(ConnectionToClient client, Object o) throws IOException {
		// TODO Auto-generated method stub
		//System.out.print(wordFiles.containsKey((String)o));
		iMessage im = new iMessage("ManuelExam",wordFiles.get((ActiveExam)o));
		//System.out.println("sdfds");
		client.sendToClient(im);
		
		
		
		/*FileOutputStream out = new FileOutputStream(new File("manual"));
		wordFiles.get((ActiveExam)o).write(out);
		out.close();
		
	
		
	}
	/*/
	
	private boolean isInActiveExam(Student s,ActiveExam ae) {
		return studentsInExam.get(ae).contains(s);
	}
	
	public void GenerateActiveExamReport(ActiveExam ae) {
		ArrayList<SolvedExam> solvedExams = studentsSolvedExams.get(ae);
		int participated = studentsInExam.get(ae).size();
		int submitted = studentsSolvedExams.get(ae).size();
		int notInTime = participated-submitted;
		Date lockDate = new Date(new java.util.Date().getTime()); //now
		ExamReport eReport = new ExamReport(ae.getCode(), ae.getType(), ae.getDate(), ae, ae.getActivator(), solvedExams, participated, submitted, notInTime, lockDate);
		if (solvedExams.size()==0) {
			System.out.println("No one submitted an exam for:" +ae.getCode() + " so a report creation was skiped");
		} else if (sqlcon.insertCompletedExam(eReport)>0) {
			System.out.println("Exam:" +ae.getCode() +" was inserted into database.");
		} else {
			System.out.println("Somthing went wrong");
		}
		activeExams.remove(ae.getCode()); 
		studentsInExam.remove(ae); 
		studentsCheckOutFromActiveExam.remove(ae); 
		studentsSolvedExams.remove(ae); 
		//wordFiles.remove(ae);
		timeChangeRequests.remove(ae);
	}


		/*
		public void SystemCheckSolvedExam(ConnectionToClient client, Object obj) throws IOException {
			// TODO Auto-generated method stub
			Object[] o = (Object[])obj;
			ActiveExam activeExam=(ActiveExam) o[0];
			boolean inTime=(boolean) o[1];
			HashMap<QuestionInExam,ToggleGroup> questionWithAnswers=(HashMap<QuestionInExam, ToggleGroup>) o[2];
			int score=0;
			Object[] studentAnsersAndScoreForExam=new Object[2];
			RadioButton r=new RadioButton();
			HashMap<QuestionInExam,Integer> studentAnswers=new HashMap<QuestionInExam,Integer>();
			if(inTime)//Student submit the exam before that the time is over.
			{
				
				if(activeExam.getType()==1)//Active exam is computerize so we save student answer and check his exam.
				{
					for (QuestionInExam qie : questionWithAnswers.keySet())//Runs all over questions. 
					{
						if(questionWithAnswers.get(qie).getSelectedToggle()==null)//Student didn't choose any answer.
							studentAnswers.put(qie,0);
							
						else//Student choose answer.
						{
							r=(RadioButton) questionWithAnswers.get(qie).getSelectedToggle();
							for(int i=1;i<5;i++)//Runs all over question's answers.
							{
								if(r.getText().equals(qie.getAnswer(i)))//Student answer equal to answer in index i(1-4).
								{
									studentAnswers.put(qie,i);//Insert the question and student's index of answer to HashMap.
									if(qie.getCorrectAnswerIndex()==i)//Student's answer is correct(he gets all points from the question).
										score+=qie.getPointsValue();
									break;
								}
							}
						}
					}
				}
				else//Active exam is manual so we fabricate student's answers and score.
				{
					ArrayList<QuestionInExam> questionsInExam=activeExam.getExam().getQuestionsInExam();
					for(QuestionInExam qie:questionsInExam)//Sets all questions with their info on screen.
					{
						studentAnswers.put(qie, 0);
					}
					score=-1;
				}
			}
			else//Student did not have time to submit his exam
			{
				for (QuestionInExam qie : questionWithAnswers.keySet())//Runs all over questions. 
				{
					if(questionWithAnswers.get(qie).getSelectedToggle()==null)//Student didn't choose any answer.
						studentAnswers.put(qie,0);
						
					else//Student choose answer.
					{
						r=(RadioButton) questionWithAnswers.get(qie).getSelectedToggle();
						for(int i=1;i<5;i++)//Runs all over question's answers.
						{
							if(r.getText().equals(qie.getAnswer(i)))//Student answer equal to answer in index i(1-4).
							{
								studentAnswers.put(qie,i);//Insert the question and student's index of answer to HashMap.
								break;
							}
						}
					}
					score=0;//His grade is zero if he didn't submit his exam on time.
				}
			}
			studentAnsersAndScoreForExam[0]=studentAnswers;
			studentAnsersAndScoreForExam[1]=score;
			iMessage im = new iMessage("SystemCheckSucceed",studentAnsersAndScoreForExam);
			client.sendToClient(im);
			
			//return studentAnsersAndScoreForExam;

		}
/*/

	
	/**
	 * Add solved exam to the list so we can generate all solved exams to report later, 
	 * and remove the student from the CheckOut list which her purpose is to see if all students have submitted their exam.
	 * @param obj
	 * @throws IOException 
	 */
	public void SetFinishedSolvedExam(ConnectionToClient client,Object obj) throws IOException
	{
		Object[] o = (Object[])obj;
		ActiveExam e=(ActiveExam) o[0];
		SolvedExam solved=(SolvedExam) o[1];
		Student student=(Student) o[2];
		studentsSolvedExams.get(e).add(solved);
		studentsCheckOutFromActiveExam.get(e).remove(student);
		if(studentsCheckOutFromActiveExam.get(e).isEmpty())//If all students have submitted the exam.
			GenerateActiveExamReport(e);
		//AesWordDoc doc=(AesWordDoc) o[3];
		//if(doc!=null)//If it was a manual exam we add it to the list of manual solved exam.
			//solvedExamWordFiles.put(solved, doc);
		client.sendToClient(new iMessage("SolvedExamSubmittedSuccessfuly",null));
	}

}

