package GUI;



import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import Controllers.ControlledScreen;
import Controllers.CourseFieldController;
import Controllers.QuestionController;
import GUI.TeacherEditAddQuestion.windowType;

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
	
	
	HashMap<String,QuestionInExam> questions = new HashMap<>();
	ArrayList<Field> teachersFields;
	ArrayList<Course> teachersCourses;
	ArrayList<Question> DBquestions;
	@FXML Label TotalScoreLabel =new Label("Total Score:") ;
	@FXML ComboBox<Field> fieldComboB;
	@FXML ComboBox<Course> courseComboB;
	@FXML VBox questionsList;
	@FXML Button Cancel;
	@FXML Button Create;
	TextField score=new TextField("score point");
	TextField NoteTeachert=new TextField("NoteForTeacher");
	TextField NoteStudent=new TextField("NoteForStudent");
	private int sum=0;
	@Override
	public void runOnScreenChange() {

		Globals.primaryStage.setHeight(700);
		Globals.primaryStage.setWidth(570);				
		teacherFieldsLoading();
		
	}
	
	
	private void teacherFieldsLoading() {
		teachersFields = CourseFieldController.getTeacherFields((Teacher) ClientGlobals.client.getUser());
		if(teachersFields==null) 
			teachersFields.add(new Field(-1,"You Have No Assigned Fields..."));
		 else {
			teachersFields.add(0,new Field(-1,"All"));
		}
		ObservableList<Field> list = FXCollections.observableArrayList(teachersFields);
		fieldComboB.setItems(list);
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
				HBox questionAddRemove = new HBox();
				questionAddRemove.setAlignment(Pos.BOTTOM_LEFT);
				questionAddRemove.setStyle("-fx-margin:20px");
				Button Add = new Button("Add");
				Add.setId(q.questionIDToString());
				Add.addEventHandler(MouseEvent.MOUSE_CLICKED, new Myselectselect());
				Button Remove = new Button ("Remove");
				Remove.addEventHandler(MouseEvent.MOUSE_CLICKED, new RemoveQuestions());
				Remove.setId(q.questionIDToString());
				questionAddRemove.getChildren().add(score);
				questionAddRemove.getChildren().add(NoteStudent);
				questionAddRemove.getChildren().add(NoteTeachert);
				questionAddRemove.getChildren().add(Add);
				questionAddRemove.getChildren().add(Remove);
				questionInfo.getChildren().add(questionAddRemove);
				hbox.getChildren().add(questionInfo);
				
				return hbox;
	
	}
	
	private class RemoveQuestions implements EventHandler<Event>{ 
	
		 @Override
	        public void handle(Event evt)
	        {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Remove Confirmation");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure you want to remove this question?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					QuestionInExam question = questions.get(((Control)evt.getSource()).getId());
					TotalScoreLabel.setText("Total Score:"+( sum-=question.getPointsValue()));
					questions.remove(((Control)evt.getSource()).getId());
		           		alert = new Alert(AlertType.INFORMATION);
		        		alert.setTitle("Question remove Succesfully");
		    			alert.setHeaderText("");
		        		alert.setContentText("Question Info:"
		        				+ "\n" + question +""
	    						+ "\n\n Was remove Successfully");
		        		alert.show();
		        		runOnScreenChange();
		        		System.out.println("Question Remove!");
				}
				    System.out.println("user chose CANCEL or closed the dialog ");
      }
	}
	private class Myselectselect implements EventHandler<Event>{
        @Override
        public void handle(Event evt)
        {
        	
        	Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Add Confirmation");
			alert.setHeaderText(null);
			alert.setContentText("To complete the process of adding a question to the exam, enter the score: ");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				
			
				QuestionInExam question = questions.get(((Control)evt.getSource()).getId());
				TotalScoreLabel.setText("Total Score:"+( sum-=question.getPointsValue()));
				questions.remove(((Control)evt.getSource()).getId());
	           		alert = new Alert(AlertType.INFORMATION);
	        		alert.setTitle("Question remove Succesfully");
	    			alert.setHeaderText("");
	        		alert.setContentText("Question Info:"
	        				+ "\n" + question +""
    						+ "\n\n Was remove Successfully");
	        		alert.show();
	        		runOnScreenChange();
	        		System.out.println("Question Remove!");
			}	 
			    System.out.println("user chose Remove Question ");
		
        
        }
	
        
	}
       
	
            
    public void CancelButtonPressed(ActionEvent event)
    {
    		Globals.mainContainer.setScreen(ClientGlobals.TeacherManageExamsID);
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
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
			 DBquestions =  QuestionController.getCourseQuestions(courseComboB.getSelectionModel().getSelectedItem());
				if (DBquestions!=null) 
					setQuestionsListInVBox();
		}			 
	 }
	
		
	private void setQuestionsListInVBox() {
		questionsList.getChildren().clear();
		System.out.println(DBquestions);
		for(Question q:DBquestions) {
			questions.put(q.questionIDToString(),new QuestionInExam (q,0,"",""));
			questionsList.getChildren().add(questionAdder(q));
		}
	
	}
	@FXML
	public void CreatelButtonPressed(ActionEvent event)
	{
	}
	
	@FXML
	public void GetTotalScore(MouseEvent event) {
		
	}
	
}

