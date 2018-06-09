package GUI;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.ControlledScreen;
import javafx.fxml.Initializable;
import logic.Exam;
import logic.Question;

public class TeacherViewExam implements Initializable, ControlledScreen {

	Exam examview=null;	
	@Override
	public void runOnScreenChange() {
		// TODO Auto-generated method stub
		
	}

	public void setExam(Exam e) {
		examview=e;
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
