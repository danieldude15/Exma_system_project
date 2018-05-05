package prototype;

import java.util.Vector;

import javafx.application.Application;
import javafx.stage.Stage;


public class pType extends Application {
	
	public static void main( String args[] ) throws Exception
	   { 
     launch(args);		
	  } // end main
	
	@Override
	public void start(Stage arg0) throws Exception {
		//Vector<Student> questions=new Vector<Student>();				
				SelectQuestionController aFrame = new SelectQuestionController(); // create StudentFrame
				aFrame.start(arg0);
	}


}
