package prototype;

import java.util.Vector;

import gui.AcademicFrameController;
import javafx.application.Application;
import javafx.stage.Stage;
import logic.Faculty;
import logic.Student;

public class pType extends Application {
	public static Question[] questions=new Question[] {null,null,null,null};
	
	public static void main( String args[] ) throws Exception
	   { 
     launch(args);		
	  } // end main
	
	@Override
	public void start(Stage arg0) throws Exception {
		//Vector<Student> questions=new Vector<Student>();
				DBMain sql = new DBMain("localhost/prototype","root","1234");
				questions = sql.getQuestions();
				
				SelectQuestionController aFrame = new SelectQuestionController(); // create StudentFrame
				  
				aFrame.start(arg0);
	}

}
