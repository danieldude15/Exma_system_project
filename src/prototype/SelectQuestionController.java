package prototype;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SelectQuestionController {
	private UpdateAnswerController sfc;	
	private static int itemIndex = 3;
		
	@FXML
	private Button btnExit = null;
		
	public void StudentInfo(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/prototype/UpdateQuestion.fxml").openStream());
		
		UpdateAnswerController UpdateAnswerController = loader.getController();		
		UpdateAnswerController.loadQuestions(pType.students.get(itemIndex));
		
		Scene scene = new Scene(root);			
		
		primaryStage.setScene(scene);		
		primaryStage.show();
	}

	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/prototype/SelectQuestion.fxml"));
				
		Scene scene = new Scene(root);
		primaryStage.setTitle("Question Selection Window");
		primaryStage.setScene(scene);
		
		primaryStage.show();		
	}
	
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit Select Question Window");
		//System.exit(0);			
	}
		
	public void presentQuestionsList() {
		
	}
}
