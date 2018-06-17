package GUI;


import Controllers.ControlledScreen;
import Controllers.CourseFieldController;
import Controllers.ExamController;
import Controllers.QuestionController;
import GUI.TeacherEditAddQuestion.windowType;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import logic.*;
import ocsf.client.ClientGlobals;

import java.awt.KeyEventPostProcessor;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class TeacherBuildNewExam implements Initializable, ControlledScreen {
	
	enum windowType {
		EDIT,Build
	}
	
	private windowType type = windowType.Build;
	
	HashMap<String,QuestionInExam> questionsinexam = new HashMap<>();
	HashMap<String,Question> questions = new HashMap<>();
	HashMap<String,TextField> scores = new HashMap<>();
	HashMap<String,TextField> NoteTeacherts = new HashMap<>();
	HashMap<String,TextField> NoteStudents = new HashMap<>();
	ArrayList<Field> teachersFields;
	ArrayList<Course> teachersCourses;
	ArrayList<Question> DBquestions;
	Label errorLabel;
	@FXML Label TotalScore;
	@FXML Label labelselectfield;
	@FXML Label labelselectcourse;
	@FXML Label windowTypeid;
	@FXML TextField duration;
	@FXML ComboBox<Field> fieldComboB;
	@FXML ComboBox<Course> courseComboB;
	@FXML 	VBox questionsList;
	@FXML Button Cancel;
	@FXML Button Create;
	Course publicCourse;
	Field publicField;
	TextField score=new TextField();
	TextField NoteTeachert;
	TextField NoteStudent;
    int sum;
    Exam examedit;
    int point;
 	String notestd=null;
 	String notetech=null;
 	Label v_Icon; 
 	ImageView imageView;
 	private final Image v = new Image("resources/v.png"); 
 	
	@Override
	public void runOnScreenChange() {

		//clear the windows of TeacherBuildNewExam
		if(type.equals(windowType.Build))
		{
		TotalScore.setText("Total Score:");
		fieldComboB.setVisible(true);
		courseComboB.setVisible(true);
		fieldComboB.getItems().clear();
		courseComboB.getItems().clear();
		questionsList.getChildren().clear();
		duration.clear();
		windowTypeid.setText("Build New Exam");
		labelselectcourse.setVisible(true);
		labelselectfield.setVisible(true);
		questionsinexam.clear();
		questions.clear();
		scores.clear();
		NoteTeacherts.clear();
		NoteStudents.clear();
		
		sum=0;
		teacherFieldsLoading();
		}
		else
		{
			sum=100;
			questionsList.getChildren().clear();
			TotalScore.setText("Total Score:100");
			fieldComboB.setVisible(false);
			courseComboB.setVisible(false);
			windowTypeid.setText("Edit exam: "+examedit.examIdToString());
			duration.setText(Integer.valueOf(examedit.getDuration()).toString());
			labelselectcourse.setVisible(false);
			labelselectfield.setVisible(false);
			setQuestionsListInVBox();
		}
	}
	
	 @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
			
		}

	private void teacherFieldsLoading() {
		teachersFields = CourseFieldController.getTeacherFields((Teacher) ClientGlobals.client.getUser());
		if(teachersFields==null) 
			teachersFields.add(new Field(-1,"You Have No Assigned Fields..."));
		ObservableList<Field> list = FXCollections.observableArrayList(teachersFields);
		fieldComboB.setItems(list);
	}

	@FXML 
	public void filterByField(ActionEvent event) {
			if(fieldComboB.getSelectionModel().getSelectedItem()!=null) 
			{
			
			publicField=fieldComboB.getSelectionModel().getSelectedItem();
			ObservableList<Course> list;
			courseComboB.getItems().clear();
			teachersCourses = CourseFieldController.getFieldCourses(publicField);
			list = FXCollections.observableArrayList(teachersCourses);
			courseComboB.setItems(list);
		}
		System.out.println(courseComboB.getItems().toString());
		
	}
	
	@FXML 
	public void filterByCourse(ActionEvent event) 
	{
		if(courseComboB.getSelectionModel().getSelectedItem()!=null) 
		{
			publicCourse=courseComboB.getSelectionModel().getSelectedItem();
			 DBquestions =  QuestionController.getCourseQuestions(publicCourse);
				if (DBquestions!=null) 
					setQuestionsListInVBox();
		}			 
	 }
	
	private void setQuestionsListInVBox() {
		if(type.equals(windowType.Build))
		{
		//questionsList.getChildren().clear();
		System.out.println(DBquestions);
		for(Question q:DBquestions) {
			questions.put(q.questionIDToString(),q);
			questionsList.getChildren().add(questionAdder(q));
		}
		}
		else {
			//questionsList.
			for(QuestionInExam q :examedit.getQuestionsInExam()) {
				questionsinexam.put(q.questionIDToString(), q);
				questionsList.getChildren().add(questionInExamAdder(q));
		}
		}
	}
	
	
	private HBox questionAdder(Question q) {
		HBox hbox = new HBox();
		hbox.setStyle("-fx-border-color:black;"
					+ "-fx-border-radius:10px;"
					+ "-fx-padding:10px;");
		
		//This VBox holds the question details
		VBox questionInfo = new VBox();
		Label questionString = new Label("Question: "+q.getQuestionString());
		questionString.setId("blackLabel");
		questionString.setWrapText(true);
		questionInfo.setMinWidth(324);
		questionInfo.setMaxWidth(324);
		Label qid = new Label("QID: "+q.questionIDToString());
		qid.setId("blackLabel");
		questionInfo.getChildren().add(qid);
		questionInfo.getChildren().add(questionString);
		RadioButton answers[] = new RadioButton[] {new RadioButton(q.getAnswer(1)),new RadioButton(q.getAnswer(2)),new RadioButton(q.getAnswer(3)),new RadioButton(q.getAnswer(4))};
		answers[q.getCorrectAnswerIndex()-1].setSelected(true);
		for(RadioButton r:answers) {
			r.setDisable(true);
			r.setWrapText(true);
			r.setId("blackLabel");
			questionInfo.getChildren().add(r);
		}
		// this HBox will hold the AddRemove buttons
				VBox  questionAddRemove = new VBox ();
				questionAddRemove.setStyle("-fx-margin:20px");
				questionAddRemove.setMinWidth(280);
				questionAddRemove.setMaxWidth(280);
				
				score=new TextField();
				score.setId(q.questionIDToString());
				score.setPromptText("score point");
				score.setMaxWidth(80);
				scores.put(q.questionIDToString(),score);
				score.addEventHandler(KeyEvent.KEY_TYPED, new MyAddRemoveEdit());
				questionAddRemove.getChildren().add(score);
				
				
				errorLabel = new Label ("You mast enter onle numbers in the field");
				errorLabel.setTextFill(Color.RED);
				errorLabel.setVisible(false);
				questionAddRemove.getChildren().add(errorLabel);
				NoteStudent=new TextField();
				NoteStudent.setId(q.questionIDToString());
				NoteStudent.setPromptText("NoteForTeacher");
				NoteStudent.setMaxWidth(300);
				NoteStudents.put(q.questionIDToString(),NoteStudent);
				questionAddRemove.getChildren().add(NoteStudent);
				
				NoteTeachert=new TextField();
				NoteTeachert.setId(q.questionIDToString());
				NoteTeachert.setPromptText("NoteForStudent");
				NoteTeachert.setMaxWidth(300);
				NoteTeacherts.put(q.questionIDToString(),NoteTeachert);
				questionAddRemove.getChildren().add(NoteTeachert);
				
				v_Icon = new Label(null, new ImageView());
				imageView=new ImageView();
				imageView.setFitHeight(10);
				imageView.setFitWidth(10);
				
				hbox.getChildren().addAll(imageView,questionInfo,questionAddRemove);
								
				return hbox;
	
	}
	

	private class MyAddRemoveEdit implements EventHandler<KeyEvent>{ 
	
	@Override
	   public void handle(KeyEvent keyEvent) {
		if(keyEvent.getCode() == KeyCode.BACK_SPACE){
			calcNewScore(((Control)keyEvent.getSource()).getId() ,keyEvent.getCharacter().charAt(0),true);
		}
		else if (!Character.isDigit(keyEvent.getCharacter().charAt(0))) {
				keyEvent.consume();
				errorLabel.setVisible(true);
		} else 
				calcNewScore(((Control)keyEvent.getSource()).getId() ,keyEvent.getCharacter().charAt(0),false);
	    }
	}

	private void calcNewScore(String curKey, char c,boolean backspace) {
		String cur ="";
		if (backspace) {
			if(scores.get(curKey).getText().length()>0)
				cur = scores.get(curKey).getText().substring(0, scores.get(curKey).getText().length()-1);
		} else 
			cur = scores.get(curKey).getText()+c;
		int xsum = 0;
		if (!cur.equals(""))
			xsum = Integer.parseInt(cur);
		for (String key : scores.keySet()) {
			if (!key.equals(curKey) && !scores.get(key).getText().equals(""))
				xsum+= Integer.parseInt(scores.get(key).getText());
		}
		sum=xsum;
		TotalScore.setText("Total Score: "+Integer.toString(sum));
	}

    public void CancelButtonPressed(ActionEvent event)
    {
    	
    		Globals.mainContainer.setScreen(ClientGlobals.TeacherManageExamsID);
    }
    
	@FXML
	public void CreatelButtonPressed(ActionEvent event)
	{

		if(sum!=100)
		{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Exam error");
			alert.setHeaderText(null);
			alert.setContentText("The total score of the exam isn't 100 you need to change the exam ");
			 alert.showAndWait();
			
		}
		else if(duration.getText().equals("") || !duration.getText().matches("[0-9]+")) 
		{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Estimated exam time error");
			alert.setHeaderText(null);
			alert.setContentText("Please enter the time to take the exam");
			 alert.showAndWait();
			
		}
		else {
			int x=(Integer.parseInt(duration.getText()));
			ArrayList<QuestionInExam> questionsIn = new ArrayList<QuestionInExam>();
			
			for(String q: scores.keySet())
				if(!scores.get(q).getText().equals(""))
				   questionsIn.add(new QuestionInExam(questions.get(q),Integer.parseInt(scores.get(q).getText()),
						   NoteTeacherts.get(q).getText(),NoteStudents.get(q).getText()));
			if(type.equals(windowType.Build))
			{
			ExamController.addExam(new Exam (0,publicCourse,x, (Teacher) ClientGlobals.client.getUser(),questionsIn));
			Globals.mainContainer.setScreen(ClientGlobals.TeacherManageExamsID);
			}
			else {
				publicCourse=examedit.getCourse();
				ExamController.deleteExam(examedit);
				ExamController.addExam(new Exam (0,publicCourse,x, (Teacher) ClientGlobals.client.getUser(),questionsIn));
				Globals.mainContainer.setScreen(ClientGlobals.TeacherManageExamsID);
		}
		}
	}

	private HBox questionInExamAdder(QuestionInExam q) {
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
		answers[q.getCorrectAnswerIndex()-1].setSelected(true);
		for(RadioButton r:answers) {
			r.setDisable(true);
			r.setWrapText(true);
			r.setId("blackLabel");
			questionInfo.getChildren().add(r);
		}
		// this HBox will hold the AddRemove buttons
				VBox  questionAddRemove = new VBox ();
				questionAddRemove.setStyle("-fx-margin:20px");
				questionAddRemove.setMinWidth(349);
				questionAddRemove.setMaxWidth(349);
				score=new TextField();
				score.setId(q.questionIDToString());
				score.setText(Integer.valueOf(q.getPointsValue()).toString());
				score.setMaxWidth(80);
				scores.put(q.questionIDToString(),score);
				score.addEventHandler(KeyEvent.KEY_TYPED, new MyAddRemoveEdit());
				questionAddRemove.getChildren().add(score);
				errorLabel = new Label ("You mast enter onle numbers in the field");
				errorLabel.setTextFill(Color.RED);
				errorLabel.setVisible(false);
				questionAddRemove.getChildren().add(errorLabel);
				NoteStudent=new TextField();
				NoteStudent.setId(q.questionIDToString());
				NoteStudent.setPromptText("NoteForTeacher");
				NoteStudent.setMaxWidth(300);
				NoteStudents.put(q.questionIDToString(),NoteStudent);
				questionAddRemove.getChildren().add(NoteStudent);
				
				NoteTeachert=new TextField();
				NoteTeachert.setId(q.questionIDToString());
				NoteTeachert.setPromptText("NoteForStudent");
				NoteTeachert.setMaxWidth(300);
				NoteTeacherts.put(q.questionIDToString(),NoteTeachert);
				questionAddRemove.getChildren().add(NoteTeachert);
				
				v_Icon = new Label(null, new ImageView());
				imageView=new ImageView();
				imageView.setFitHeight(10);
				imageView.setFitWidth(10);
				
				hbox.getChildren().addAll(imageView,questionInfo,questionAddRemove);
								
				return hbox;
	
	}
	
	public void setType(windowType type) {
		this.type = type;
		
	}

	public void setExam(Exam exam) {
		examedit=exam;
		
	}
	
	public boolean isValid(String s) {
	    String n = ".*[0-9].*";
	    String A = ".*[A-Z].*";
	    String a = ".*[a-z].*";
	    return s.matches(n) &&( s.matches(a) || s.matches(A));
	}
}
