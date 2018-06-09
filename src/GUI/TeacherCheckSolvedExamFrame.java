package GUI;

import java.net.URL;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.ResourceBundle;

import javax.lang.model.util.SimpleElementVisitor6;

import Controllers.ControlledScreen;
import Controllers.SolvedExamController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import logic.Globals;
import logic.QuestionInExam;
import logic.SolvedExam;
import ocsf.client.ClientGlobals;

public class TeacherCheckSolvedExamFrame implements Initializable, ControlledScreen {

	private SolvedExam solvedExam = null;
	private HashMap<QuestionInExam, TextField> teacherNotesH = new HashMap<>();
	private final Image v = new Image("resources/v.png"); 
	private final Image x = new Image("resources/x.png"); 
	
	@FXML VBox questionsView;
	@FXML TextField newScore;
	@FXML TextArea changeNote;
	@FXML Label errorLabel;
	@FXML Label noteErrorLabel;
	@FXML Label studentName;
	@FXML Label score;
	@FXML Label timeCompleted;
	@FXML ImageView doneImage;	
	
	@Override public void initialize(URL location, ResourceBundle resources) {

	}

	@Override public void runOnScreenChange() {
		Globals.primaryStage.setHeight(770);
		Globals.primaryStage.setWidth(770);
		questionsView.getChildren().clear();
		errorLabel.setVisible(false);
		newScore.setText("");
		changeNote.setText("");
		noteErrorLabel.setVisible(false);
		studentName.setText(solvedExam.getStudent().getName());
		score.setText(Integer.toString(solvedExam.getScore()));
		newScore.setDisable(false);
		changeNote.setDisable(false);
		timeCompleted.setText(solvedExam.getCompletedTimeInMinutes() + " Min");
		doneImage.setVisible(false);
		if (solvedExam.isTeacherApproved()) {
			newScore.setText(Integer.toString(solvedExam.getScore()));
			newScore.setDisable(true);
			changeNote.setText(solvedExam.getTeachersScoreChangeNote());
			changeNote.setDisable(true);
			doneImage.setVisible(true);
		}
		if (solvedExam!=null) {
			for(QuestionInExam q : solvedExam.getQuestionsInExam()) {
				questionsView.getChildren().add(questionAdder(q,solvedExam.getStudentsAnswers()));
			}
		} else 
			backButton(null);
	}

	@FXML public void testDigitOnly(KeyEvent keyEvent) {
		if (!Character.isDigit(keyEvent.getCharacter().charAt(0)) && 
				keyEvent.getCode()!=KeyCode.BACK_SPACE) {
			errorLabel.setVisible(true);
			keyEvent.consume();
		} else 
			errorLabel.setVisible(false);
		System.out.println(keyEvent.getCharacter().charAt(0));
		try {
			Integer score = Integer.parseInt(newScore.getText() + keyEvent.getCharacter().charAt(0));
			if (score>100 || score <0) {
				errorLabel.setVisible(true);
				keyEvent.consume();
			} else {
				errorLabel.setVisible(false);
			}
		} catch (NumberFormatException e) {
			
		}
		
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
		questionInfo.setMaxWidth(600);
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
		Object teacherNote;
		if (solvedExam.isTeacherApproved()) {
			teacherNote = new Label("Written Note: "+solvedExam.getQuestionNoteOnHash().get(q));
			((Label)teacherNote).setId("blackLabel");
		} else {
			teacherNote = new TextField();
			((TextField)teacherNote).setPromptText("Note for student");
			teacherNotesH.put(q, (TextField)teacherNote);
		}
		questionNoteByTeacher.getChildren().add((Node) teacherNote);
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
		Globals.mainContainer.setScreen(ClientGlobals.TeacherCheckExamsID);
	}
	
	@FXML public void submitButton(ActionEvent event) {
		if (!newScore.getText().equals("") && changeNote.getText().equals("")) {
			noteErrorLabel.setVisible(true);
		} else {
			if (!newScore.getText().equals("")) {
					solvedExam.setScore(Integer.parseInt(newScore.getText()));
					solvedExam.setTeachersScoreChangeNote(changeNote.getText());
					HashMap<QuestionInExam, String> teacherNotes = new HashMap<>();
					for(QuestionInExam qie: teacherNotesH.keySet()) {
						teacherNotes.put(qie, teacherNotesH.get(qie).getText());
					}
					solvedExam.setQuestionNoteOnHash(teacherNotes);
			}
			solvedExam.setTeacherApproved(true);
			if (SolvedExamController.insertSolvedExam(solvedExam)>0) {
				//successfull insertion
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Exam Check Is Updated Successfully");
				alert.setHeaderText(null);
				alert.setContentText("The exam was updated into the system.");
				alert.showAndWait();
				doneImage.setVisible(true);
				score.setText(Integer.toString(solvedExam.getScore()));
			} else {
				//Failed To insert!
			}
		}
	}
	
	@FXML public void approvedExamClicked(ActionEvent event) {
		
	}
	
	public void setSolvedExam(SolvedExam se) {
		solvedExam= se;
	}
}
