package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import Controllers.ControlledScreen;
import Controllers.QuestionController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
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
	
	private windowType type = null;
	
	Question question=null;
	
	ArrayList<Field> teacherFields = new ArrayList<>();
	ArrayList<Course> teacherCourses = new ArrayList<>();
	ArrayList<Course> questionCourses = new ArrayList<>();
	HashMap<Field, ArrayList<Course>> coursesInField = new HashMap<>();
	HashMap<String, Course> availableCourses = new HashMap<>();
	
	
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
		organizedFieldsHashMap();
		
		teacherFields.remove(0);
		ObservableList<Field> list = FXCollections.observableArrayList(teacherFields);
		fields.setItems(list);
		fields.getSelectionModel().clearSelection();
		
		if (question==null) {
			backToMenu(null);
		}
		
		if (type.equals(windowType.ADD)) {
			questionID.setText("Add New Question");
			questionString.setText("");
			ta1.setText("");
			ta2.setText("");
			ta3.setText("");
			ta4.setText("");
			submitB.setText("Add Question");
			fields.setDisable(false);
			courseVbox.getChildren().clear();
			if (answers.getSelectedToggle()!=null)
				answers.getSelectedToggle().setSelected(false);
		} else if (type.equals(windowType.EDIT)){
			questionID.setText("Edit Question: " + question.questionIDToString());
			questionString.setText(question.getQuestionString());
			ta1.setText(question.getAnswer(1));
			ta2.setText(question.getAnswer(2));
			ta3.setText(question.getAnswer(3));
			ta4.setText(question.getAnswer(4));
			int index = question.getCorrectAnswerIndex();
			switch(index) {
			case 1:
				answer1.setSelected(true);
				break;
			case 2:
				answer2.setSelected(true);
				break;
			case 3:
				answer3.setSelected(true);
				break;
			case 4:
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
		availableCourses.clear();
		questionCourses.clear();
		Field field = fields.getSelectionModel().getSelectedItem();
		ArrayList<Course> arr = coursesInField.get(field);
		if(arr!=null) {
			for (Course c:arr) {
				CheckBox cBox = new CheckBox(c.toString());
				cBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
				    @Override
				    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				    	if(newValue) 
				    		questionCourses.add(availableCourses.get(cBox.getText()));
				    	else {
				    		questionCourses.remove(availableCourses.get(cBox.getText()));
				    	}				    		
				    }
				});
				availableCourses.put(c.toString(), c);
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
		if(!checkFormIsFilled()) return;
		else {
			int index = 0;
			if(answer1.isSelected()) index =1;
			else if(answer2.isSelected()) index =2;
			else if(answer3.isSelected()) index =3;
			else if(answer4.isSelected()) index =4;
			String[] answers = {ta1.getText(),ta2.getText(),ta3.getText(),ta4.getText()};
			int questionid = 0;
			if(question!=null) questionid = question.getID();
			question = new Question(questionid, (Teacher) ClientGlobals.client.getUser(), 
					questionString.getText(), answers, 
					fields.getSelectionModel().getSelectedItem(), index, questionCourses);
			Alert alert;
			if (type.equals(windowType.ADD)) {
				if (QuestionController.addQuestion(question)>0) {
					System.out.println("ADDED QUESTION!");
					alert = new Alert(AlertType.INFORMATION);
	        		alert.setTitle("Question Created Succesfully");
	    			alert.setHeaderText("");
	        		alert.setContentText("Question Info:"
	        				+ "\n" + question +""
    						+ "\n\n Was Created Successfully");
	        		alert.show();
	        		backToMenu(null);
				} else {
					alert = new Alert(AlertType.ERROR);
	        		alert.setTitle("Creation Error");
	    			alert.setHeaderText(null);
	        		alert.setContentText("Could not Insert question into Database!\n"
	        				+ "if this happens again please contact DBAdmin. ");
	        		alert.show();
				}
			} else {
				if (QuestionController.editQuestion(question)>0) {
					alert = new Alert(AlertType.INFORMATION);
	        		alert.setTitle("Question Updated Succesfully");
	    			alert.setHeaderText("");
	        		alert.setContentText("Question Info:"
	        				+ "\n" + question +""
    						+ "\n\n Was Updated Successfully");
	        		alert.show();
	        		backToMenu(null);
				} else {
					alert = new Alert(AlertType.ERROR);
	        		alert.setTitle("Update Error");
	    			alert.setHeaderText(null);
	        		alert.setContentText("Could not Update question in Database!\n"
	        				+ "This Is probably because this question is already in use!\n"
	        				+ "If you think this error is wrong\n"
	        				+ "Please contact DBAdmin. ");
	        		alert.show();
				}
			}
		}
	}

	private boolean checkFormIsFilled() {
		if (questionString.getText().equals("")) {
			questionError.setVisible(true);
			return false;
		} else {
			questionError.setVisible(false);
		}
		if (ta1.getText().equals("") ||
			ta2.getText().equals("") ||
			ta3.getText().equals("") ||
			ta4.getText().equals("")) {
			answersError.setVisible(true);
			return false;
		} else {
			answersError.setVisible(false);
		}
		if (answers.getSelectedToggle()==null) {
			answerError.setVisible(true);
			return false;
		} else {
			answerError.setVisible(false);
		}
		if (fields.getSelectionModel().getSelectedItem()==null) {
			fieldError.setVisible(true);
			return false;
		} else {
			fieldError.setVisible(false);
		}
		return true;
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

}
