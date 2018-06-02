package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import Controllers.ControlledScreen;
import Controllers.QuestionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import logic.Course;
import logic.Field;
import logic.Globals;
import logic.Question;
import logic.Teacher;
import ocsf.client.ClientGlobals;

public class TeacherEditAddQuestion implements ControlledScreen, Initializable {
	enum windowType {
		EDIT,ADD
	}
	
	private windowType type = windowType.ADD;
	
	Question question=null;
	
	ArrayList<Field> teacherFields = new ArrayList<>();
	ArrayList<Course> teacherCourses = new ArrayList<>();
	HashMap<Field, ArrayList<Course>> coursesInField = new HashMap<>();
	
	@FXML Label questionID;
	@FXML Label questionError;
	@FXML Label answersError;
	@FXML Label answerError;
	@FXML Label fieldError;
	@FXML Label courseError;
	@FXML TextArea questionString;
	@FXML ToggleGroup answers;
	@FXML RadioButton answer1;
	@FXML RadioButton answer2;
	@FXML RadioButton answer3;
	@FXML RadioButton answer4;
	@FXML TextField ta1;
	@FXML TextField ta2;
	@FXML TextField ta3;
	@FXML TextField ta4;
	@FXML ComboBox<Field> fields;
	@FXML VBox courseVbox;
	@FXML Button submitB;
	
	@Override public void initialize(URL arg0, ResourceBundle arg1) {

	}

	@Override public void runOnScreenChange() {
		Globals.primaryStage.setHeight(656);
		Globals.primaryStage.setWidth(553);
		organizedFieldsHashMap();
		
		ObservableList<Field> list = FXCollections.observableArrayList(teacherFields);
		fields.setItems(list);
		
		if (type.equals(windowType.ADD) || question==null) {
			questionID.setText("Add New Question");
			questionString.setText("");
			ta1.setText("");
			ta2.setText("");
			ta3.setText("");
			ta4.setText("");
			submitB.setText("Add Question");
		} else {
			questionID.setText("Edit Question: " + question.questionIDToString());
			questionString.setText(question.getQuestionString());
			ta1.setText(question.getAnswer(0));
			ta2.setText(question.getAnswer(1));
			ta3.setText(question.getAnswer(2));
			ta4.setText(question.getAnswer(3));
			int index = question.getCorrectAnswerIndex();
			switch(index) {
			case 0:
				answer1.setSelected(true);
				break;
			case 1:
				answer2.setSelected(true);
				break;
			case 2:
				answer3.setSelected(true);
				break;
			case 3:
				answer4.setSelected(true);
				break;
			}
					
			fields.getSelectionModel().select(question.getField());
			userSetField(null);
			fields.setDisable(true);
			submitB.setText("Update Question");
		}
	}

	@FXML public void backToMenu(ActionEvent event) {
		Globals.mainContainer.setScreen(ClientGlobals.TeacherManageQuestionsID);
	}
	
	@FXML public void userSetField(ActionEvent event) {
		courseVbox.getChildren().clear();
		Field field = fields.getSelectionModel().getSelectedItem();
		ArrayList<Course> arr = coursesInField.get(field);
		if(arr!=null) {
			for (Course c:arr) {
				CheckBox cBox = new CheckBox(c.toString());
				if (type.equals(windowType.EDIT)) {
					cBox.setDisable(true);
					if(question.getCourses().contains(c)) 
						cBox.setSelected(true);
				}
				courseVbox.getChildren().add(cBox);
			}
		}
	}
	
	@FXML public void submitQuestion(ActionEvent event) {
		if (questionString.getText().equals("")) 
			questionError.setVisible(true);;
		if (ta1.getText().equals("") ||
			ta2.getText().equals("") ||
			ta3.getText().equals("") ||
			ta4.getText().equals("")) 
			answersError.setVisible(true);
		if (answers.getSelectedToggle()==null)
			answerError.setVisible(true);
		if (fields.getSelectionModel().getSelectedItem()==null)
			fieldError.setVisible(true);
		if (!fieldError.isVisible() && !answersError.isVisible() && !answerError.isVisible() &&! questionError.isVisible()) {
			int index = 0;
			if(answer1.isSelected()) index =1;
			else if(answer2.isSelected()) index =2;
			else if(answer3.isSelected()) index =3;
			else if(answer4.isSelected()) index =4;
			String[] answers = {ta1.getText(),ta2.getText(),ta3.getText(),ta4.getText()};
			ArrayList<Course> courses = translateCoursesCheckboxes(); 
			question = new Question(0, (Teacher) ClientGlobals.client.getUser(), 
					questionString.getText(), answers, 
					fields.getSelectionModel().getSelectedItem(), index, courses);
			if (type.equals(windowType.ADD)) {
				if (QuestionController.addQuestion(question)>0) {
					System.out.println("ADDED QUESTION!");
					//successfully added question
				} else {
					//failed to add question
				}
			} else {
				if (QuestionController.editQuestion(question)>0) {
					//successfully updated question
				} else {
					//failed to update question
				}
			}
		}
	}

	public windowType getType() {
		return type;
	}

	public void setType(windowType type) {
		this.type = type;
	}

	public void setFieldsAndCourses(ArrayList<Course> teachersCourses, ArrayList<Field> teachersFields) {
		teacherFields=teachersFields;
		teacherCourses=teachersCourses;
	}
	
	public void setQuestion(Question q) {
		question=q;
		System.out.println("Change Question to "+ q);
	}
	
	private void organizedFieldsHashMap() {
		coursesInField.clear();
		for(Course c:teacherCourses) {
			if (coursesInField.get(c.getField()) != null){
				ArrayList<Course> arrayList = coursesInField.get(c.getField());
				arrayList.add(c);
				coursesInField.put(c.getField(), arrayList);
			} else {
				ArrayList<Course> arrayList = new ArrayList<>();
				arrayList.add(c);
				coursesInField.put(c.getField(), arrayList);
			}
		}
	}

	private ArrayList<Course> translateCoursesCheckboxes() {
		// TODO Auto-generated method stub
		return null;
	}
}
