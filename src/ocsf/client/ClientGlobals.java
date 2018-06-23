package ocsf.client;

import java.io.IOException;

import GUI.ClientFrame;

public class ClientGlobals {
	
	public static AESClient client = null;
	public static boolean kill = false;
	public static String[] initialArgs =null;
	public static ClientFrame ClientConnectionController;
	
	/*
	 * Screen Controllers ID's and Paths!
	 */
	
	/*		Students Screens Start		*/
	
	public static final String StudentMainID = "Student Main";
	public static final String StudentMainPath = "/resources/fxml/StudentMain.fxml";
	
	public static final String StudentStartExamID = "Student Start Exam";
	public static final String StudentStartExamPath = "/resources/fxml/StudentStartExam.fxml";

	public static final String StudentViewExamID = "Student Exam View";
	public static final String StudentViewExamPath = "/resources/fxml/StudentExamView.fxml";

	public static final String StudentSolvesExamID = "Student Solves Exam";
	public static final String StudentSolvesExamPath = "/resources/fxml/StudentSolvesExam.fxml";

	/*		Students Screens end		*/
	
	/*		Principal Screens Start		*/

	public static final String PrincipalMainID = "Principal Main";
	public static final String PrincipalMainPath = "/resources/fxml/PrincipalMain.fxml";

	public static final String PrincipalReportsID = "Principal Reports";
	public static final String PrincipalReportsPath = "/resources/fxml/PrincipalReports.fxml";

	public static final String PrincipalViewDataID = "Principal View Data";
	public static final String PrincipalViewDataPath = "/resources/fxml/PrincipalViewData.fxml";

	public static final String PrincipalViewQuestionID = "Principal View Question";
	public static final String PrincipalViewQuestionPath ="/resources/fxml/PrincipalViewQuestion.fxml";

	public static final String PrincipalViewFieldID = "Principal View Field";
	public static final String PrincipalViewFieldPath = "/resources/fxml/PrincipalViewField.fxml";
	
	/*		Principal Screens End		*/
	
	/*		General Screens	Start		*/
	
	public static final String LogInID = "Log-In";
	public static final String LogInPath = "/resources/fxml/Login.fxml";
	
	public static final String ViewReportID = "Report View";
	public static final String ViewReportPath = "/resources/fxml/ReportView.fxml";
	
	public static final String ViewPlainExamID = "View Exam";
	public static final String ViewPlainExamPath = "/resources/fxml/PrincipalViewExam.fxml";
	
	public static final String ClientConnectionScreenID = "Client Configuration";
	public static final String ClientConnectionScreenPath = "/resources/fxml/ClientGui.fxml";	

	public static final String ConfigfileName = "ConnectionConfig.txt";
	
	/*		General Screens	end			*/
	
	/*		Teacher Screens Start		*/
	
	public static final String TeacherMainID = "Teacher Main";
	public static final String TeacherMainPath = "/resources/fxml/TeacherMain.fxml";
	
	public static final String TeacherManageQuestionsID = "Teacher Manage Questions";
	public static final String TeacherManageQuestionsPath = "/resources/fxml/TeacherManageQuestions.fxml";
	
	public static final String TeacherEditAddQuestionID = "Teacher Edit/Add Question";
	public static final String TeacherEditAddQuestionPath = "/resources/fxml/TeacherEditAddQuestion.fxml";

	public static final String TeacherInitializeExamID = "Teacher Initialize Exam";
	public static final String TeacherInitializeExamPath = "/resources/fxml/TeacherInitializeChooseExam.fxml";
	
	public static final String TeacherCheckExamsID = "Teacher Check Exams";
	public static final String TeacherCheckExamsPath = "/resources/fxml/TeacherCheckExams.fxml";
	
	public static final String TeacherActiveExamID = "Activate Exam";
	public static final String TeacherActiveExamPath = "/resources/fxml/TeacherInitializeExam.fxml";
	
	public static final String TeacherTimeChangeRequestID = "Teacher Time Change Request";
	public static final String TeacherTimeChangeRequestPath = "/resources/fxml/TeacherTimeChangeRequest.fxml";
	
	public static final String TeacherBuildNewExamID = "Teacher Build New Exam";
	public static final String TeacherBuildNewExamPath = "/resources/fxml/TeacherBuildNewExam.fxml";

	public static final String TeacherManageExamsID = "Teacher Manage Exams";
	public static final String TeacherManageExamsPath = "/resources/fxml/TeacherManageExams.fxml";

	public static final String TeacherCheckExamID = "Teacher Check Solved Exam";
	public static final String TeacherManageExamPath = "/resources/fxml/TeacherCheckSolvedExam.fxml";



	/*		Teacher Screens End		*/

	public static void handleIOException(IOException e) {
		if(ClientGlobals.kill ) return;
		e.printStackTrace();
		System.out.println("Cause: "+ e.getCause());
		System.out.println("Msg: "+e.getMessage());
		try {
				client.closeConnection();
				System.out.println("trying to reconnect");
				try {
					Thread.sleep(400);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				client.openConnection();
				System.out.println("Client is reconnected:" + client.isConnected());
		} catch (IOException e1) {
			System.out.println("Tried to Reconect and Failed! with Exception");
			e1.printStackTrace();
		}
	}
	
	
}
