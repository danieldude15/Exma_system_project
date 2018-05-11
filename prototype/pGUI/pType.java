package pGUI;

import javafx.application.Application;
import javafx.stage.Stage;
import pSQLTools.client.PrototypeClient;


public class pType extends Application {
	
	public static void main( String args[] ) throws Exception
	   { 
     launch(args);		
	  } // end main
	
	@Override
	public void start(Stage arg0) throws Exception {
		gui_globals.client = new PrototypeClient("localhost", 12345);
		gui_globals.client.openConnection();
		SelectQuestionController aFrame = new SelectQuestionController(); // create StudentFrame
		aFrame.start(arg0);
	}


}
