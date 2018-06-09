package GUI;



import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import Controllers.ControlledScreen;
import Controllers.CourseFieldController;
import Controllers.ExamController;
import Controllers.QuestionController;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import logic.*;
import ocsf.client.ClientGlobals;

public class TeacherBuildNewExam implements Initializable, ControlledScreen {
	
	
	HashMap<String,QuestionInExam> questionsinexam = new HashMap<>();
	HashMap<String,Question> questions = new HashMap<>();
	HashMap<String,TextField> scores = new HashMap<>();
	HashMap<String,TextField> NoteTeacherts = new HashMap<>();
	HashMap<String,TextField> NoteStudents = new HashMap<>();
	HashMap<String,CheckBox> AddRemoves = new HashMap<>();
	ArrayList<Field> teachersFields;
	ArrayList<Course> teachersCourses;
	ArrayList<Question> DBquestions;
	@FXML Label TotalScore;
	@FXML TextField duration;
	@FXML ComboBox<Field> fieldComboB;
	@FXML ComboBox<Course> courseComboB;
	@FXML VBox questionsList;
	@FXML Button Cancel;
	@FXML Button Create;
	CheckBox AddRemove;
	Course publicCourse;
	Field publicField;
	
	TextField score;
	TextField NoteTeachert;
	TextField NoteStudent;
     int sum=0;
	@Override
	public void runOnScreenChange() {

		Globals.primaryStage.setHeight(670);
		Globals.primaryStage.setWidth(745);
		sum=0;
		TotalScore.setText("Total Score:");
		questionsList.getChildren().clear();
		questions.clear();
		questionsinexam.clear();
		duration.clear();
		scores.clear();
		NoteTeacherts.clear();
		NoteStudents.clear();
		teacherFieldsLoading();
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
			Field selectefield=fieldComboB.getSelectionModel().getSelectedItem();
			ObservableList<Course> list;
			courseComboB.getItems().clear();
			teachersCourses = CourseFieldController.getFieldCourses(selectefield);
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
			 DBquestions =  QuestionController.getCourseQuestions(courseComboB.getSelectionModel().getSelectedItem());
				if (DBquestions!=null) 
					setQuestionsListInVBox();
		}			 
	 }
	
	private void setQuestionsListInVBox() {
		questionsList.getChildren().clear();
		System.out.println(DBquestions);
		for(Question q:DBquestions) {
			questions.put(q.questionIDToString(),q);
			questionsList.getChildren().add(questionAdder(q));
		}
	
	}
	
	private Node questionAdder(Question q) {
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
				score.setPromptText("score point");
				score.setMaxWidth(80);
				scores.put(q.questionIDToString(),score);
				questionAddRemove.getChildren().add(score);
				
				
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
				
				AddRemove = new CheckBox();
				AddRemove.setId(q.questionIDToString());
				AddRemove.addEventHandler(MouseEvent.MOUSE_PRESSED, new MyAddRemove());
				AddRemoves.put(q.questionIDToString(),AddRemove);
				VBox  CheckBoxAddRemove = new VBox ();
				CheckBoxAddRemove.setStyle("-fx-margin:20px");
				CheckBoxAddRemove.setAlignment(Pos.CENTER);
				CheckBoxAddRemove.getChildren().add(AddRemove);
				
				
				
				hbox.getChildren().addAll(CheckBoxAddRemove,questionInfo,questionAddRemove);
								
				return hbox;
	
	}
	
	private class MyAddRemove implements EventHandler<Event>{ 
	
		 @Override
	        public void handle(Event evt)
	        {
			 
			 
			 if(AddRemoves.get(((Control)evt.getSource()).getId()).isSelected() ==false)
			 {int point;
	        	String notestd;
	        	String notetech;
	        	Question question=questions.get(((Control)evt.getSource()).getId());
	        	if(scores.get(question.questionIDToString()).getText().equals(""))
	        	{
	        		Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Add Confirmation");
					alert.setHeaderText(null);
					alert.setContentText("You mast put score point befor you add the question \\n Please try again");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK)
					{
						AddRemove.setSelected(false);
					}
				
	        	}
	        	
	        	else if(!questionsinexam.containsKey(question.questionIDToString()))
	        	{	
	        	     point = Integer.parseInt(scores.get(question.questionIDToString()).getText());
	        		 notestd = NoteStudents.get(question.questionIDToString()).getText();
	        		 notetech = NoteTeacherts.get(question.questionIDToString()).getText();
	        		questionsinexam.put(question.questionIDToString(),new QuestionInExam (question,point,notetech,notestd));
	        		sum=sum+point;
					TotalScore.setText("Total Score:"+sum);
					if(!questionsinexam.isEmpty()) {
						 fieldComboB.setDisable(true);
						 courseComboB.setDisable(true);
					 }
					
		           		
				}	
	        	else {
	        		if(Integer.parseInt(scores.get(question.questionIDToString()).getText()) != (questionsinexam.get(question.questionIDToString()).getPointsValue()))
	        		{
	        			sum =sum + (Integer.parseInt(scores.get(question.questionIDToString()).getText()))- (questionsinexam.get(question.questionIDToString()).getPointsValue());
	        			TotalScore.setText("Total Score:"+sum);
	        			questionsinexam.get(question.questionIDToString()).setPointsValue(scores.get(question.questionIDToString()).getText());
	        		}
	        		if(( NoteStudents.get(question.questionIDToString()).getText())  != (questionsinexam.get(question.questionIDToString()).getStudentNote()))
	        			questionsinexam.get(question.questionIDToString()).setStudentNote( NoteStudents.get(question.questionIDToString()).getText());
	        		if((NoteTeacherts.get(question.questionIDToString()).getText()!= questionsinexam.get(question.questionIDToString()).getInnerNote()))
	        			questionsinexam.get(question.questionIDToString()).setInnerNote( NoteTeacherts.get(question.questionIDToString()).getText());
	        		
	        		
	        	}
	        	
				    System.out.println("user chose add Question ");
			
	        }
				
			
			 else 
			 {
				 Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Remove Confirmation");
					alert.setHeaderText(null);
					alert.setContentText("Are you sure you want to remove this question?");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK)
					{
						AddRemoves.get(((Control)evt.getSource()).getId()).setSelected(false);
						scores.get(((Control)evt.getSource()).getId()).setText("");
						QuestionInExam question = questionsinexam.get(((Control)evt.getSource()).getId());
						questionsinexam.remove(((Control)evt.getSource()).getId());
						sum=sum-question.getPointsValue();
						TotalScore.setText("Total Score:"+sum);
						scores.remove(((Control)evt.getSource()).getId());
						NoteStudents.remove(((Control)evt.getSource()).getId());
						NoteTeacherts.remove(((Control)evt.getSource()).getId());
						if(questionsinexam.isEmpty())
						 {
							 fieldComboB.setDisable(false);
							 courseComboB.setDisable(false);
						 }
						
						
			           		alert = new Alert(AlertType.INFORMATION);
			        		alert.setTitle("Question remove Succesfully");
			    			alert.setHeaderText("");
			        		alert.setContentText("Question Info:"
			        				+ "\n" + question +""
		    						+ "\n\n Was remove Successfully");
			        		alert.show();
			        		System.out.println("Question Remove!");
			        		
					}
					    System.out.println("user chose CANCEL or closed the dialog ");
				 
				 
			 }
				 
			 }
		 
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
		else if(duration.getText().equals("")) 
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
			questionsIn.addAll(questionsinexam.values());
			ExamController.addExam(new Exam (0,publicCourse,x, (Teacher) ClientGlobals.client.getUser(),questionsIn));
			Globals.mainContainer.setScreen(ClientGlobals.TeacherManageExamsID);
			
		}
	}
	
}
