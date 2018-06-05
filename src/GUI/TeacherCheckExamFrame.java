package GUI;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import Controllers.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import logic.Globals;
import logic.QuestionInExam;
import logic.SolvedExam;
import ocsf.client.ClientGlobals;

public class TeacherCheckExamFrame implements Initializable, ControlledScreen {

	SolvedExam solvedExam = null;
	private final Image v = new Image("resources/v.png"); 
	private final Image x = new Image("resources/x.png"); 
	
	@FXML VBox questionsView;
	@FXML TextFlow examInfo;

	@Override public void initialize(URL location, ResourceBundle resources) {

	}

	@Override public void runOnScreenChange() {
		Globals.primaryStage.setHeight(900);
		Globals.primaryStage.setWidth(670);
		
		if (solvedExam!=null) {
			for(QuestionInExam q : solvedExam.getQuestionsInExam()) {
				questionsView.getChildren().add(questionAdder(q,solvedExam.getStudentsAnswers()));
			}
		} else 
			backButton(null);
	}

	private Node questionAdder(QuestionInExam q, HashMap<QuestionInExam, Integer> answersHash) {
		//HBox main question container
		HBox hbox = new HBox();
		hbox.setStyle("-fx-border-color:black;"
					+ "-fx-border-radius:10px;"
					+ "-fx-padding:10px;");
		
		//This VBox holds the question details
		VBox questionInfo = new VBox();
		Label questionString = new Label("Question: "+q.getQuestionString());
		questionString.setId("blackLabel");
		questionString.setWrapText(true);
		questionInfo.setMinWidth(330);
		questionInfo.setMaxWidth(330);
		Label qid = new Label("QID: "+q.questionIDToString());
		qid.setId("blackLabel");
		questionInfo.getChildren().add(qid);
		questionInfo.getChildren().add(questionString);
		RadioButton answers[] = new RadioButton[] {new RadioButton(q.getAnswer(1)),new RadioButton(q.getAnswer(2)),new RadioButton(q.getAnswer(3)),new RadioButton(q.getAnswer(4))};
		answers[answersHash.get(q)-1].setSelected(true);
		for(RadioButton r:answers) {
			r.setDisable(true);
			r.setWrapText(true);
			r.setId("blackLabel");
			questionInfo.getChildren().add(r);
		}
		
		// this HBox will hold the TeacherNote extention
		HBox questionNoteByTeacher = new HBox();
		questionNoteByTeacher.setAlignment(Pos.BOTTOM_LEFT);
		questionNoteByTeacher.setStyle("-fx-margin:20px");
		TextField teacherNote = new TextField("Add Note");
		questionNoteByTeacher.getChildren().add(teacherNote);
		questionInfo.getChildren().add(questionNoteByTeacher);
		
		// this VBox holds the correect/Incorrect icon for the question
		VBox answerCorrection = new VBox();
		ImageView imageView=new ImageView();
		imageView.setFitHeight(10);
		imageView.setFitWidth(10);
		if (answersHash.get(q)==q.getCorrectAnswerIndex()) {
			imageView.setImage(v);
		} else {
			imageView.setImage(x);
		}
		answerCorrection.getChildren().add(imageView);
		
		hbox.getChildren().addAll(questionInfo,answerCorrection);
		
		
		return hbox;
	}

	@FXML public void backButton(ActionEvent event) {
		Globals.mainContainer.setScreen(ClientGlobals.TeacherCheckExamID);
	}
	
	@FXML public void approvedExamClicked(ActionEvent event) {
		
	}
	
	public void setSolvedExam(SolvedExam se) {
		
	}
}
