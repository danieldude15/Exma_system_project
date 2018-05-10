package pGUI;

import java.io.InputStream;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pLogic.Question;
import pSQLTools.client.PrototypeClient;

public class SelectQuestionController {
	
	private Vector<Question> questions = new Vector<Question>();
	public static Question selectedQuestion = null;
	
	@FXML private ListView<String> questionsListView;
	@FXML private Label chooseLabel1;
	@FXML private Button continueButton1;
	@FXML private Button cancelButton1;
	
	PrototypeClient client;
	
	public void moveOnToUpdateQuestionAnswer(ActionEvent event) throws Exception {
		//((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
	    try
	    {
	    	Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			String sceneFile = "UpdateAnswer.fxml";
	        InputStream url  = getClass().getResource( sceneFile ).openStream();
	        Parent root = loader.load( url );
	        //System.out.println( "  fxmlResource = " + sceneFile );
	        UpdateAnswerController UAC= loader.getController();		
			UAC.loadQuestion(selectedQuestion);
			Scene scene = new Scene(root);			
			scene.getStylesheets().add(getClass().getResource(sceneFile).toExternalForm());
			primaryStage.setScene(scene);		
			primaryStage.show();
			((Node)(event.getSource())).getScene().getWindow().hide();
	    }
	    catch ( Exception ex )
	    {
	        System.out.println( "Exception on FXMLLoader.load()" );
	        System.out.println( "  * " + ex );
	        System.out.println( "    ----------------------------------------\n" );
	        throw ex;
	    }
		
	}

	public void start(Stage primaryStage) throws Exception {	
		/**
		 * this code opens the .fxml file that hold the GUI properties. 
		 * we make sure to understand the error in case the file does not open for any reason.
		 */
		String sceneFile = "SelectQuestion.fxml";
	    Parent root = null;
	    URL    url  = null;
	    try
	    {
	        url  = getClass().getResource( sceneFile );
	        root = FXMLLoader.load( url );
	        System.out.println( "  fxmlResource = " + sceneFile );
	    }
	    catch ( Exception ex )
	    {
	        System.out.println( "Exception on FXMLLoader.load()" );
	        System.out.println( "  * url: " + url );
	        System.out.println( "  * " + ex );
	        System.out.println( "    ----------------------------------------\n" );
	        throw ex;
	    }
		Scene scene = new Scene(root);
		primaryStage.setTitle("Question Selection Window");
		primaryStage.setScene(scene);
		
		primaryStage.show();		
	}
	
	public void initialize() {
		try {
			client = new PrototypeClient("localhost", 12345);
			client.openConnection();
			client.sendToServer("GetQuestions");
			while(!client.msgSent) {
				Thread.sleep(10);
			}
		} catch (Exception e) {
			System.out.println(e);
			return;
		}
		ArrayList<String> al = new ArrayList<String>();	
		for(Question q:client.questions){
			al.add("Question ID: "+q.getID()+" "+q.getQuestionString());
			questions.add(q);
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
