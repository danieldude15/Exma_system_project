package ocsf.client;

import javafx.event.ActionEvent;
import java.io.IOException;

import GUI.ClientFrame;
import logic.Globals;
import logic.iMessage;

public class ClientGlobals {
	public static AESClient client = null;
	
	/*
	 * Screen Controllers ID's and Paths!
	 */
	public static final String ClientConnectionScreenPath = "/fxml/ClientGui.fxml";
	public static ClientFrame ClientConnectionController;
	
	public static final String TeacherMainID = "TeacherMain";
	public static final String TeacherMainPath = "/fxml/TeacherMain.fxml";
	
	public static final String LogInID = "LogIn";
	public static final String LogInPath = "/fxml/Login.fxml";

	public static final String TeacherManageQuestionsID = "TeacherManageQuestions";
	public static final String TeacherManageQuestionsPath = "/fxml/TeacherManageQuestions.fxml";
	
	public static final String StudentMainID = "StudentMain";
	public static final String StudentMainPath = "/fxml/StudentMain.fxml";
	
	public static final String StudentStartExamID = "StudentStartExam";
	public static final String StudentStartExamPath = "/fxml/StudentStartExam.fxml";
	

	public static final String StudentViewExamID = "StudentExamView";
	public static final String StudentViewExamPath = "/fxml/StudentExamView.fxml";

	public static final String StudentSolvesExamID = "StudentSolvesExam";
	public static final String StudentSolvesExamPath = "/fxml/StudentSolvesExam.fxml";

		

	//Itzik710@bitbucket.org/Petachok/automatic_exam_system.git


	public static final String TeacherEditAddQuestionID = "TeacherEditAddQuestion";
	public static final String TeacherEditAddQuestionPath = "/fxml/TeacherEditAddQuestion.fxml";
	

	
	public static final String PrincipalMainID = "PrincipalMain";
	public static final String PrincipalMainPath = "/fxml/PrincipalMain";

	public static final String ConfigfileName = "ConnectionConfig.txt";

	public static void handleIOException(IOException e) {
		e.printStackTrace();
		try {
			if (client!=null && !client.isConnected()) {
				System.out.println("trying to reconnect");
				client.openConnection();
			}
		} catch (IOException e1) {
			System.out.println("Tried to Reconect and Failed! with Exception");
		}
		System.out.println("Couse: "+ e.getCause());
		System.out.println("Msg: "+e.getMessage());
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
