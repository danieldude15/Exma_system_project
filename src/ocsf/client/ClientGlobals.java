package ocsf.client;

import GUI.ClientFrame;

import java.io.IOException;

public class ClientGlobals {
	public static AESClient client = null;
	
	/*
	 * Screen Controllers ID's and Paths!
	 */
	public static final String ClientConnectionScreenPath = "/resources/fxml/ClientGui.fxml";
	public static ClientFrame ClientConnectionController;

	public static boolean kill = false;
	
	
	
	public static final String LogInID = "LogIn";
	public static final String LogInPath = "/resources/fxml/Login.fxml";

	public static final String TeacherManageQuestionsID = "TeacherManageQuestions";
	public static final String TeacherManageQuestionsPath = "/resources/fxml/TeacherManageQuestions.fxml";
	
	public static final String StudentMainID = "StudentMain";
	public static final String StudentMainPath = "/resources/fxml/StudentMain.fxml";
	
	public static final String StudentStartExamID = "StudentStartExam";
	public static final String StudentStartExamPath = "/resources/fxml/StudentStartExam.fxml";
	

	public static final String StudentViewExamID = "StudentExamView";
	public static final String StudentViewExamPath = "/resources/fxml/StudentExamView.fxml";

	public static final String StudentSolvesExamID = "StudentSolvesExam";
	public static final String StudentSolvesExamPath = "/resources/fxml/StudentSolvesExam.fxml";

	/*		Principal Screens Start		*/

	public static final String PrincipalMainID = "PrincipalMain";
	public static final String PrincipalMainPath = "/resources/fxml/PrincipalMain.fxml";

	public static final String PrincipalReportsID = "PrincipalReports";
	public static final String PrincipalReportsPath = "/resources/fxml/PrincipalReports.fxml";

	public static final String PrincipalViewDataID = "PrincipalViewData";
	public static final String PrincipalViewDataPath = "/resources/fxml/PrincipalViewData.fxml";

	public static final String PrincipalViewQuestionID = "PrincipalViewQuestion";
	public static final String PrincipalViewQuestionPath ="/resources/fxml/PrincipalViewQuestion.fxml";

	public static final String PrincipalViewExamID = "PrincipalViewExam";
	public static final String PrincipalViewExamPath = "/resources/fxml/PrincipalViewExam.fxml";

	public static final String PrincipalViewFieldID = "PrincipalViewField";
	public static final String PrincipalViewFieldPath = "/resources/fxml/PrincipalViewField.fxml";

	/*		Principal Screens End		*/

	public static final String ConfigfileName = "ConnectionConfig.txt";
	
	/*		Teacher Screens Start		*/
	
	public static final String TeacherMainID = "TeacherMain";
	public static final String TeacherMainPath = "/resources/fxml/TeacherMain.fxml";
	
	public static final String TeacherEditAddQuestionID = "TeacherEditAddQuestion";
	public static final String TeacherEditAddQuestionPath = "/resources/fxml/TeacherEditAddQuestion.fxml";

	public static final String InitializeExamID = "Initialize Exam";
	public static final String InitializeExamPath = "/resources/fxml/Initialize Exam.fxml";
	
	public static final String TeacherCheckExamsID = "TeacherCheckExams";
	public static final String TeacherCheckExamsPath = "/resources/fxml/TeacherCheckExams.fxml";
	
	public static final String TeacherViewExamID = "TeacherViewExam";
	public static final String TeacherViewExamPath = "/resources/fxml/TeacherViewExam.fxml";
	
	public static final String ActiveExamID = "ActiveExam";
	public static final String ActiveExamPath = "/resources/fxml/ActiveExam.fxml";
	
	public static final String TeacherTimeChangeRequestID = "TeacherTimeChangeRequest";
	public static final String TeacherTimeChangeRequestPath = "/resources/fxml/TeacherTimeChangeRequest.fxml";
	
	public static final String TeacherBuildNewExamID = "TeacherBuildNewExam";
	public static final String TeacherBuildNewExamPath = "/resources/fxml/TeacherBuildNewExam.fxml";

	public static final String TeacherManageExamsID = "TeacherManageExams";
	public static final String TeacherManageExamsPath = "/resources/fxml/TeacherManageExams.fxml";

	public static final String TeacherCheckExamID = "TeacherCheckSolvedExam";
	public static final String TeacherManageExamPath = "/resources/fxml/TeacherCheckSolvedExam.fxml";

	/*		Teacher Screens End		*/

	public static void handleIOException(IOException e) {
		if(ClientGlobals.kill ) return;
		e.printStackTrace();
		System.out.println("Cause: "+ e.getCause());
		System.out.println("Msg: "+e.getMessage());
		try {
				client.closeConnection();
				//client.stopWaiting();
				System.out.println("trying to reconnect");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				client.openConnection();
				System.out.println("Client is reconnected:" + client.isConnected());
		} catch (IOException e1) {
			System.out.println("Tried to Reconect and Failed! with Exception");
		}
	}
	
	
	
	/*
	#####################   NIV's Globals!    #########################
	*/
	
	
	
	
	/*
	#####################  END OF NIV's Globals!    #########################
	*/
	
	
	
	
	
	/*
	#####################   ITZIKS's Globals!    #########################
	*/




	/*
	#####################  END OF ITZIKS's Globals!    #########################
	*/
	
	
	
	
	
	/*
	#####################   NATHAN's Globals!    #########################
	*/


	/*
	#####################  END OF NATHAN's Globals!    #########################
	*/
	
	
	
	
	
	
	/*
	#####################   DANIEL's Globals!    #########################
	*/




	/*
	#####################  END OF DANIEL's Globals!    #########################
	*/
	
	
}
