package GUI;


import Controllers.ControlledScreen;
import Controllers.CourseFieldController;
import Controllers.ExamController;
import Controllers.QuestionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.*;
import ocsf.client.ClientGlobals;
import java.util.ArrayList;
import java.util.HashMap;


public class TeacherBuildNewExam implements ControlledScreen {
	
	enum windowType {
		EDIT,Build
	}
	
	private windowType type = windowType.Build;
	
	
	HashMap<String,Question> questions = new HashMap<>();
	HashMap<String,TextField> scores = new HashMap<>();
	HashMap<String,TextField> noteTeacherts = new HashMap<>();
	HashMap<String,TextField> noteStudents = new HashMap<>();
	HashMap<String, ImageView> imageViews = new HashMap<>();
	ArrayList<Field> teachersFields;
	ArrayList<Course> teachersCourses;
	ArrayList<Question> DBquestions;
	@FXML Label totalScore;
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
	TextField noteTeachert;
	TextField noteStudent;
    int sum;
    Exam examedit;
    int point;
 	String notestd=null;
 	String notetech=null;
 	ImageView imageView;
 	private final Image v = new Image(this.getClass().getResourceAsStream("/resources/v.png")); 
 	
	@Override
	public void runOnScreenChange() {

		//clear the windows of TeacherBuildNewExam
		if(type.equals(windowType.Build)) {
			totalScore.setText("Total Score:");
			sum=0;
			courseComboB.setDisable(false);
			fieldComboB.setDisable(false);
			fieldComboB.setVisible(true);
			courseComboB.setVisible(true);
			fieldComboB.getItems().clear();
			courseComboB.getItems().clear();
			questionsList.getChildren().clear();
			duration.clear();
			windowTypeid.setText("Build New Exam");
			labelselectcourse.setVisible(true);
			labelselectfield.setVisible(true);
			
			questions.clear();
			scores.clear();
			noteTeacherts.clear();
			noteStudents.clear();
			teacherFieldsLoading();
		} else {
			sum=100;
			questionsList.getChildren().clear();
			totalScore.setText("Total Score: 100");
			fieldComboB.setVisible(false);
			courseComboB.setVisible(false);
			windowTypeid.setText("Edit exam: "+examedit.examIdToString());
			duration.setText(Integer.valueOf(examedit.getDuration()).toString());
			labelselectcourse.setVisible(false);
			labelselectfield.setVisible(false);
			setQuestionsListInVBox();
		}
	}

/**
 * Loads all the teacher's fields
 */
	private void teacherFieldsLoading() {
		teachersFields = CourseFieldController.getTeacherFields((Teacher) ClientGlobals.client.getUser());
		if(teachersFields==null) 
			teachersFields.add(new Field(-1,"You Have No Assigned Fields..."));
		ObservableList<Field> list = FXCollections.observableArrayList(teachersFields);
		fieldComboB.setItems(list);
	}

	/**
	 * 
	 * @param event- When we choosing a field we put all the courses in combo box
	 */
	@FXML 
	public void filterByField(ActionEvent event) {
		if(fieldComboB.getSelectionModel().getSelectedItem()!=null) {
			questionsList.getChildren().clear();
			publicField=fieldComboB.getSelectionModel().getSelectedItem();
			ObservableList<Course> list;
			courseComboB.getItems().clear();
			teachersCourses = CourseFieldController.getFieldCourses(publicField);
			list = FXCollections.observableArrayList(teachersCourses);
			courseComboB.setItems(list);
		}
		System.out.println(courseComboB.getItems().toString());
		
	}
	/**
	 * 
	 * @param event- When we choosing a course we bring all the question 
	 */
	
	@FXML 
	public void filterByCourse(ActionEvent event) 
	{
		if(courseComboB.getSelectionModel().getSelectedItem()!=null) {
			questionsList.getChildren().clear();
			publicCourse=courseComboB.getSelectionModel().getSelectedItem();
			 DBquestions =  QuestionController.getCourseQuestions(publicCourse);
				if (DBquestions!=null) 
					setQuestionsListInVBox();
		}			 
	 }
	
	/**
	 * we take the question and and send in questionAdder and we get hbox and put in questionlist
	 */
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
				questions.put(q.questionIDToString(), q);
				questionsList.getChildren().add(questionAdder(q));
		}
		}
	}
	
	/**
	 * They take all the details about the question and create hbox
	 * @param q - Question
	 * @return h
	 */
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
		
		noteStudent=new TextField();
		noteStudent.setId(q.questionIDToString());
		noteStudent.setPromptText("Note for student");
		noteStudent.setMaxWidth(300);
		noteStudents.put(q.questionIDToString(),noteStudent);
		questionAddRemove.getChildren().add(noteStudent);
		
		noteTeachert=new TextField();
		noteTeachert.setId(q.questionIDToString());
		noteTeachert.setPromptText("Note for teachert");
		noteTeachert.setMaxWidth(300);
		noteTeacherts.put(q.questionIDToString(),noteTeachert);
		questionAddRemove.getChildren().add(noteTeachert);
		
		
		imageView=new ImageView();
		imageView.setFitHeight(10);
		imageView.setFitWidth(10);
		imageView.setImage(v);
		imageView.setVisible(false);
		imageViews.put(q.questionIDToString(), imageView);
		hbox.getChildren().addAll(imageView,questionInfo,questionAddRemove);
		
		if(q instanceof QuestionInExam) {
			QuestionInExam qie = (QuestionInExam) q;
			score.setText(qie.getPointsValue()+"");
			if(qie.getStudentNote() !=null && !qie.getStudentNote().equals("") )
				noteStudent.setText(qie.getStudentNote());
			else
				noteStudent.setPromptText("Note for student");
			if(qie.getInnerNote() !=null && !qie.getInnerNote().equals("") )
				noteTeachert.setText(qie.getInnerNote());
			else
				noteTeachert.setPromptText("Note for teacher");
			imageView.setVisible(true);
		}
		
		return hbox;
	
	}
	
/**
 * Once we change the field of score we call this function that updates the score
 * @author Niv Mizrahi
 *
 */
	private class MyAddRemoveEdit implements EventHandler<KeyEvent>{ 
	
	@Override
	   public void handle(KeyEvent keyEvent) {
			String questionId = ((Control)keyEvent.getSource()).getId();
			char c = keyEvent.getCharacter().charAt(0);
			String curText = scores.get(questionId).getText();
			if( c == '\b'){
				calcNewScore(questionId,c,true);
				if(curText.isEmpty()) {
					imageViews.get(questionId).setVisible(false);
				}
			} else if (!Character.isDigit(c) || new String(curText+c).equals("0")) {
				keyEvent.consume();
			} else {
				calcNewScore(questionId ,c,false);
				imageViews.get(questionId).setVisible(true);
			}
			if(sum==0) {
				courseComboB.setDisable(false);
				fieldComboB.setDisable(false);
			} else {
				courseComboB.setDisable(true);
				fieldComboB.setDisable(true);
			}
	    }
	}

	/**
	 * Calculates in real time the amount
	 * @param curKey -questionId
	 * @param c -the char of the event
	 * @param backspace -if the event is result of press on backspace
	 */
	private void calcNewScore(String curKey, char c,boolean backspace) {
		String cur ="";
		if (backspace) {
				cur = scores.get(curKey).getText();
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
		totalScore.setText("Total Score: "+Integer.toString(sum));
	}

	/**
	 * Return to the previous window when you press the Back button
	 * @param event
	 */
    public void CancelButtonPressed(ActionEvent event)
    {
    	
    		Globals.mainContainer.setScreen(ClientGlobals.TeacherManageExamsID);
    }
     /**
      * Create an exam when you click the Create button
      * @param event
      */
	@FXML
	public void CreatelButtonPressed(ActionEvent event)
	{

		if(sum!=100)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Exam error");
			alert.setHeaderText(null);
			alert.setContentText("The total score of the exam isn't 100 you need to change the exam ");
			 alert.showAndWait();
			
		}
		else if(duration.getText().equals("") || !duration.getText().matches("[0-9]+")) 
		{
			Alert alert = new Alert(AlertType.ERROR);
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
						   noteTeacherts.get(q).getText(),noteStudents.get(q).getText()));
			if(type.equals(windowType.Build))
			{
			ExamController.addExam(new Exam (0,publicCourse,x, (Teacher) ClientGlobals.client.getUser(),questionsIn));
			Globals.mainContainer.setScreen(ClientGlobals.TeacherManageExamsID);
			}
			else {
				publicCourse=examedit.getCourse();
				ExamController.deleteExam(examedit);
				ExamController.addExam(new Exam (0,publicCourse,x, (Teacher) ClientGlobals.client.getUser(),questionsIn));
				sum=0;
				fieldComboB.setVisible(true);
				courseComboB.setVisible(true);
				Globals.mainContainer.setScreen(ClientGlobals.TeacherManageExamsID);
		}
		}
	}
 /**
  * This function distinguishes between which window we want to build or edit
  * @param type - build or edit
  */
	public void setType(windowType type) {
		this.type = type;
		
	}
/**
 * When we want to change an exam this function passes the exam that we want to edit
 * @param exam
 */
	public void setExam(Exam exam) {
		examedit=exam;
		
	}
	
}
