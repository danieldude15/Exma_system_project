package pGUI;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pLogic.Question;
import pSQLTools.DBMain;

public class SelectQuestionController {
	
	private Vector<Question> questions;
	public static Question selectedQuestion = null;
	
	@FXML private ListView<String> questionsListView;
	@FXML private Label chooseLabel1;
	@FXML private Button continueButton1;
	@FXML private Button cancelButton1;
	
	
	public void moveOnToUpdateQuestionAnswer(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("\\prototype\\UpdateAnswer.fxml").openStream());
		
		UpdateAnswerController UAC= loader.getController();		
		UAC.loadQuestion(selectedQuestion);
		Scene scene = new Scene(root);			
		
		primaryStage.setScene(scene);		
		primaryStage.show();
	}

	public void start(Stage primaryStage) throws Exception {	
		File tmpDir = new File("\\prototype\\SelectQuestion.fxml");
		boolean exists = tmpDir.exists();
		Parent root = null;
		if (exists) {
		root = FXMLLoader.load(getClass().getResource("\\prototype\\SelectQuestion.fxml"));
		} else {
			System.out.println("File does not exist in "+tmpDir);
			throw new Exception();
		}
		Scene scene = new Scene(root);
		primaryStage.setTitle("Question Selection Window");
		primaryStage.setScene(scene);
		
		primaryStage.show();		
	}
	
	public void initialize() {
		try {
			DBMain sql = new DBMain();
			questions = sql.getQuestions();
		} catch (Exception e) {
			System.out.println("shit!");
			return;
		}
		ArrayList<String> al = new ArrayList<String>();	
		for(Question q:questions){
			al.add("Question ID: "+q.getID()+" "+q.getQuestionString());
		}
		
		
		ObservableList<String> list = FXCollections.observableArrayList(al);
		questionsListView.setItems(list);
	}
	
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit Select Question Window");
		System.exit(0);			
	}
		
	@FXML 
	public void handleMouseClick(MouseEvent arg0) {
	   String selectedItemID = questionsListView.getSelectionModel().getSelectedItem().split(" ")[2];
	   int id = Integer.parseInt(selectedItemID);
	   selectedQuestion = findQuestion(id);
	}
	
	private Question findQuestion(int ID) {
		for(Question q:questions){
			if (q.getID()==ID)
				return q;
		}
		return null;
	}
}
