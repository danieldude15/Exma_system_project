package pGUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import pLogic.pQuestion;
import pSQLTools.client.PrototypeClient;

public class SelectQuestionController implements Initializable, pControlledScreen{
	
	private Vector<pQuestion> questions  = new Vector<pQuestion>();
	pScreensController myController;
	
	@FXML private ListView<String> questionsListView;
	@FXML private Label chooseLabel1;
	@FXML private Button continueButton1;
	@FXML private Button cancelButton1;
	
	
	public void moveOnToUpdateQuestionAnswer(ActionEvent event) throws Exception {
		if (gui_globals.selectedQuestion != null) {
			myController.setScreen(pType.UpdateAnswerScreenID);
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
		gui_globals.client.closeConnection();
		System.exit(0);			
	}
		
	@FXML 
	public void handleMouseClick(MouseEvent arg0) {
		if (questionsListView.hasProperties()) {
		   String selectedItemID = questionsListView.getSelectionModel().getSelectedItem().split(" ")[2];
		   int id = Integer.parseInt(selectedItemID);
		   gui_globals.selectedQuestion = findQuestion(id);
		} else {
			gui_globals.selectedQuestion = null;
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
		PrototypeClient client = gui_globals.client;
		questions.clear();
		questionsListView.getItems().clear();
		try {
			client.sendToServer("GetQuestions");
			while(!client.msgSent) {
				Thread.sleep(10);
			}
			client.msgSent=false;
		} catch (Exception e) {
			System.out.println(e);
			return;
		}
		ArrayList<String> al = new ArrayList<String>();	
		if (client.questions.size()==0) {
			System.out.println("No Questions pulled from database clients.questions=" + client.questions);
		}
		for(pQuestion q:client.questions){
			al.add("Question ID: "+q.getID()+" "+q.getQuestionString());
			questions.add(q);
		}
		client.questions.clear();
		
		
		ObservableList<String> list = FXCollections.observableArrayList(al);
		questionsListView.setItems(list);
	}

	@Override
	public void runOnScreenChange() {
		initialize(null, null);
		
	}
}
