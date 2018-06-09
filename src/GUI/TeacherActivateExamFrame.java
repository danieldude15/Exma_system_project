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


	
public class TeacherActivateExamFrame implements Initializable, ControlledScreen  {

		Exam examview=null;	
		
		@Override
		public void runOnScreenChange() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
			
		}
		public void setExam(Exam e) {
			examview=e;
			
		}

	}


