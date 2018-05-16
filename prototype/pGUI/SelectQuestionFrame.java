package pGUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

import Controllers.pControlledScreen;
import Controllers.pQuestionsController;
import Controllers.pScreensController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import pClient.PrototypeClientApp;
import pLogic.pQuestion;

public class SelectQuestionFrame implements Initializable, pControlledScreen{
	
	private Vector<pQuestion> questions  = new Vector<pQuestion>();
	pScreensController myController;
	pQuestionsController questionsController;
	
	UpdateAnswerFrame questionFrame;
	
	@FXML private ListView<String> questionsListView;
	@FXML private Label chooseLabel1;
	@FXML private Button continueButton1;
	@FXML private Button cancelButton1;
	
	
	public void moveOnToUpdateQuestionAnswer(ActionEvent event) throws Exception {
		
	    if(questionFrame.getQuestion()!=null) {
			myController.setScreen(PrototypeClientApp.UpdateAnswerScreenID);
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("No Question Selected");
			alert.setHeaderText(null);
			alert.setContentText("You must select question in order to continue to next screen");

			alert.showAndWait();
		}
	}
	
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit Select Question Window");
		pClientGlobals.client.closeConnection();
		System.exit(0);			
	}
		
	@FXML 
	public void handleMouseClick(MouseEvent arg0) {
		if (questionsListView.hasProperties()) {
		   String selectedItemID = questionsListView.getSelectionModel().getSelectedItem().split(" ")[1];
		   int id = Integer.parseInt(selectedItemID);
		   questionFrame.setQuestion(findQuestion(id));
		}
	}
	
	private pQuestion findQuestion(int ID) {
		for(pQuestion q:questions){
			if (q.getID()==ID)
				return q;
		}
		return null;
	}
	@Override
	public void setScreenParent(pScreensController screenParent) {
		myController = screenParent;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		questionsController = new pQuestionsController();
	}

	@Override
	public void runOnScreenChange() {
		questionFrame = (UpdateAnswerFrame) myController.getController(PrototypeClientApp.UpdateAnswerScreenID);
		questions = pQuestionsController.getQuestions();
		 
		if (questions.size()==0) {
			System.out.println("No Questions pulled from database clients.questions=" + questions);
			ArrayList<String> al = new ArrayList<String>();
			al.add("No Questions pulled from database questions= " + questions);
			ObservableList<String> list = FXCollections.observableArrayList(al);
			questionsListView.setItems(list);
			return;
		}
		
		ArrayList<String> al = new ArrayList<String>();	
		for (pQuestion q : questions) {
			al.add("QuestionID: "+ q.getID()+ " " + q.getQuestionString());
		}
		
		questionsListView.getItems().clear();
		ObservableList<String> list = FXCollections.observableArrayList(al);
		questionsListView.setItems(list);
		
	}
}
