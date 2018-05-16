package Controllers;

import java.util.Vector;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import pClient.PrototypeClient;
import pGUI.pClientGlobals;
import pLogic.pQuestion;

public class pQuestionsController {

	public static Vector<pQuestion> getQuestions() {
		PrototypeClient client = pClientGlobals.client;
		Vector<pQuestion> questions = new Vector<pQuestion>();
		try {
			client.sendToServer("GetQuestions");
			while(!client.msgSent) {
				Thread.sleep(10);
			}
			client.msgSent=false;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		if (client.questions.size()==0) {
			System.out.println("No Questions pulled from database clients.questions=" + client.questions);
		}
		questions = pQuestion.clone(client.questions);
		client.questions.clear();
		return questions;

	}
	
	public void updateQuestionIndex(pQuestion question) {
		PrototypeClient client = pClientGlobals.client;
		try {
			client.sendToServer("UpdateQuestionAnswer "+question.getID()+" "+question.getCorrectAnswerIndex());
			while (!client.msgSent) {
				Thread.sleep(10);
			}
			client.msgSent=false;
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Update Question Successfull");
			alert.setHeaderText(null);
			alert.setContentText("QuestionID:"+question.getID()+"\nWas updated with answer: "+question.getCorrectAnswerIndex());

			alert.showAndWait();
		} catch (Exception e) {
			System.out.println(e.getClass().getName());
			e.printStackTrace();
			return;
		}
	}

}
