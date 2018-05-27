package ocsf.client;

import java.io.IOException;

import GUI.ClientFrame;

public class ClientGlobals {
	public static AESClient client = null;
	
	/*
	 * Screen Controllers ID's and Paths!
	 */
	public static final String TeacherMainID = "TeacherMain";
	public static final String TeacherMainPath = "/fxml/TeacherMain.fxml";
	public static final String ClientConnectionScreenPath = "/fxml/ClientGui.fxml";
	public static ClientFrame ClientConnectionController;
	public static final String LogInID = "LogIn";
	public static final String LogInPath = "/fxml/Login.fxml";

	public static final String TeacherManageQuestionsID = "TeacherManageQuestions";
	public static final String TeacherManageQuestionsPath = "/fxml/TeacherManageQuestions.fxml";
	
	public static final String StudentMainID = "StudentMain";
	public static final String StudentMainPath = "/fxml/StudentMain.fxml";
	
	
	public static final String StudentStartExamID = "StudentStartExam";
	public static final String StudentStartExamPath = "/fxml/StudentStartExam.fxml";
	
	public static final String StudentViewExamID = "StudentViewExam";
	public static final String StudentViewExamPath = "/fxml/StudentViewExam.fxml";
	

	
	public static void handleIOException(IOException e) {
		e.printStackTrace();
		try {
			if (client!=null && !client.isConnected()) {
				System.out.println("trying to reconnect");
				client.openConnection();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
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
