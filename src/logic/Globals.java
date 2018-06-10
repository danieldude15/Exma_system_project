package logic;

import java.util.Calendar;
import java.util.GregorianCalendar;

import GUI.ScreensController;
import GUI.StudentSolvesExamFrame;
import javafx.application.Application;
import javafx.stage.Stage;
import ocsf.client.ClientGlobals;
import ocsf.server.ServerGlobals;

/**
 * this class holds global variables that are used globally in the application for easy access and maintanace
 * @author Group-12
 *
 */
public class Globals {
	/**
	 * if im a client or a server
	 */
	public static String application = "";
	/**
	 * this enum will hold visible or hidden types
	 */
	public enum Type {VISIBLE,HIDDEN;}
	/**
	 * this is the primaryStage of the users application used to change size of windows and other adjustments
	 */
	public static Stage primaryStage = null;
	/**
	 * this is the mainContainer that holds all the nodes of contents of all screens and used to pass by different screens information
	 */
	public static ScreensController mainContainer;
	/**
	 * handle a core exception when the program cannot continue to function after this type of exception
	 * this function should show and alert with exception details and than System.exit(1) at the end of it.
	 * @param e the exception thrown
	 */
	public static void handleException(Exception e) {
		
		e.printStackTrace();
		System.exit(1);
	}
	
	public static Thread createTimer(ActiveExam ae, String timeLabel) {
		return new Thread() {
			public void run() {
				Calendar timer = new GregorianCalendar();
				timer.set(Calendar.HOUR, 0);
				timer.set(Calendar.MINUTE, 0);
				timer.set(Calendar.SECOND, 0);
				int timeInMinutes = ae.getDuration();
				int examHourse = timeInMinutes/60;
				int examMinutes = timeInMinutes%60;
				while (true) {
					Calendar diff = new GregorianCalendar();
					diff.set(Calendar.HOUR, examHourse-(diff.get(Calendar.HOUR)));
					diff.set(Calendar.MINUTE, examMinutes-(diff.get(Calendar.MINUTE)));
					diff.set(Calendar.SECOND, 60-diff.get(Calendar.SECOND));
					if(diff.getTimeInMillis()>0) {
						//timeLabel = "" + diff.get(Calendar.HOUR) + ":" + diff.get(Calendar.MINUTE) + ":" + diff.get(Calendar.SECOND);
					} else {
						if (application.equals("client")) {
							StudentSolvesExamFrame sse = (StudentSolvesExamFrame) mainContainer.getController(ClientGlobals.StudentSolvesExamID);
							sse.lockExam();
						} else if (application.equals("server")) {
							ServerGlobals.server.GenerateActiveExamReport(ae);
						}
						break;
					}
					
				}
			}
		};
	}
}
