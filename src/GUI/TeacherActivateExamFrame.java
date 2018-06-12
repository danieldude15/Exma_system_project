package GUI;    


    import java.net.URL;
	import java.util.ArrayList;

import java.util.HashMap;
	import java.util.Optional;
	import java.util.ResourceBundle;//JFoenix
	import java.sql.Date;
import Controllers.ActiveExamController;
import Controllers.ControlledScreen;
	import Controllers.CourseFieldController;
	import Controllers.ExamController;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
	import javafx.scene.input.KeyEvent;
	import javafx.scene.input.MouseEvent;
	import javafx.scene.layout.HBox;
	import javafx.scene.layout.VBox;
	import javafx.scene.text.Text;
	import javafx.scene.text.TextFlow;
	import logic.*;
	import ocsf.client.ClientGlobals;


	
public class TeacherActivateExamFrame implements Initializable, ControlledScreen  {
	
		@FXML Label ExamId;
		@FXML Label Durationid;
		@FXML Label Field;
		@FXML Label CourseId;
		@FXML TextField Examcode;
		@FXML RadioButton ComputerizedExamId;
		@FXML RadioButton MonualExamId;
		@FXML Button Cancel;
		@FXML Button Active;
		//@FXML JFXDate Date;
		Exam examview;	
		int type;
		@Override
		public void runOnScreenChange() {
			Globals.primaryStage.setHeight(670);
			Globals.primaryStage.setWidth(745);
			ToggleGroup SelectType = new ToggleGroup();
			ExamId.setText("Exam id : "+examview.examIdToString());
			Durationid.setText("Duration: "+ examview.getDuration());
			Field.setText("Field: "+ examview.getField().getName());
			CourseId.setText("Course: "+ examview.getCourse().getName());
			ComputerizedExamId.setSelected(true);
			ComputerizedExamId.setToggleGroup(SelectType);
			MonualExamId.setToggleGroup(SelectType);
		}

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
			
		}
		public void setExam(Exam e) {
			examview=e;
			
		}
		@FXML
		public void ActiveButtonPressed(ActionEvent event)
		{
			String code=new String(Examcode.getText());
			if(Examcode.getText()==null)
			{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Exam code error");
			alert.setHeaderText(null);
			alert.setContentText("You must put exam code in the right place!! ");
			alert.showAndWait();
			}
			else if(Examcode.getText().length()!=4)
			{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Exam code error");
				alert.setHeaderText(null);
				alert.setContentText("You must assign a 4-digit code");
				alert.showAndWait();
				
			}
			else
			{
				if(isValid(Examcode.getText()) && ActiveExamController.getActiveExam(Examcode.getText())==null)
				{
					if(ComputerizedExamId.isSelected())
					{
						type=1;
						ActiveExam activeExam = new ActiveExam(code,type,new Date(new java.util.Date().getTime()),examview,(Teacher) ClientGlobals.client.getUser());
						ActiveExamController.InitializeActiveExam(activeExam);
					}
						
					else
					{
						type=0;
						ActiveExam activeExam = new ActiveExam(code,type,new Date(new java.util.Date().getTime()),examview,(Teacher) ClientGlobals.client.getUser());
						ActiveExamController.InitializeActiveExam(activeExam);
						ActiveExamController.CreateDocFile(activeExam);
					}
					
					 Globals.mainContainer.setScreen(ClientGlobals.TeacherMainID);
					
				}
				else
				{
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Exam code error");
					alert.setHeaderText(null);
					alert.setContentText("You must change the exam code\n" 
					+"because the code exists or the code does not contain letters or numbers");
					alert.showAndWait();
					
				}
			}
			
		}
		public boolean isValid(String s) {
		    String n = ".*[0-9].*";
		    String A = ".*[A-Z].*";
		    String a = ".*[a-z].*";
		    return s.matches(n) &&( s.matches(a) || s.matches(A));
		}
		
		@FXML
	    public void CancelButtonPressed(ActionEvent event)
			    {
			     Globals.mainContainer.setScreen(ClientGlobals.TeacherMainID);
			    }
	

		

	}


