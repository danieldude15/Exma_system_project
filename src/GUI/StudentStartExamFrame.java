package GUI;


import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import Controllers.ActiveExamController;
import Controllers.ControlledScreen;
import Controllers.UserController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import logic.ActiveExam;
import logic.Globals;
import logic.Student;
import ocsf.client.ClientGlobals;
@SuppressWarnings("unchecked")
public class StudentStartExamFrame implements ControlledScreen{

	@FXML TextField examCode;
	@FXML TextField studentId;
	@FXML Label examCodeError;
	@FXML Label idError;
	public Timeline timeline;
	private Long timeSeconds;
	
	@Override
	public void runOnScreenChange() {
		examCode.clear();
		studentId.clear();
		idError.setText("");
		examCodeError.setText("");

	}
	
	
	/**
	 * When the student pressed on start exam button and fields are filled correct the method send the active exam to StudentSolvesExamFrame class.
	 */
	@FXML public void StartExamButtonPressed(ActionEvent event)
	{
		idError.setText("");
		examCodeError.setText("");
		String sid=Integer.toString(ClientGlobals.client.getUser().getID());
		//If some of the fields is empty, then the student get an error.
		if(studentId.getText().isEmpty() || examCode.getText().isEmpty())
		{
			Globals.popUp(AlertType.INFORMATION,"Start exam Failed!","You have missing fields!, please fill those fields and try again..");
			return;
		}
		//Two fields are filled
		ActiveExam active=ActiveExamController.getActiveExam(examCode.getText());
		//If at least one of the fields is wrong.
		if(active==null) {
			//If student entered wrong exam code he gets an error message beneath the exam code Textfield.
			examCodeError.setText("**Invalid Exam code!");
			examCodeError.setTextFill(javafx.scene.paint.Paint.valueOf("#FF0000"));
			return;
		}
		//If student entered wrong id he gets an error message beneath the id Textfield.
		if( !(studentId.getText().equals(sid)) ) {
			idError.setText("**Invalid user Id!");
			idError.setTextFill(javafx.scene.paint.Paint.valueOf("#FF0000"));
			return;
		}
		
		if (!(UserController.getStudentsInCourse(active.getCourse()).contains((Student)ClientGlobals.client.getUser()))) {
			Globals.popUp(AlertType.INFORMATION,"Not Signed In To Course","You are not signed in to this course:" + active.getCourse().getName() + "\n You may not participate in an Exam that you are not signed in to!");
			return;
		} 
		Boolean canStart = ActiveExamController.StudentCheckedInToActiveExam((Student) ClientGlobals.client.getUser(), active);
		if (canStart) {
			startStudentsExam(active);
			Globals.mainContainer.setScreen(ClientGlobals.StudentSolvesExamID);
		} else {
			Globals.popUp(AlertType.INFORMATION,"Can Not Start Exam","It seems as you already submitted your exam and cannot start again.");
		}
		
	}
			
	/**
	 * Initiate Timer for active exam.
	 * @param active
	 */
	@SuppressWarnings("rawtypes")
	private void startStudentsExam(ActiveExam active) {
		StudentSolvesExamFrame studentsolvesExam = (StudentSolvesExamFrame) Globals.mainContainer.getController(ClientGlobals.StudentSolvesExamID);
		studentsolvesExam.SetActiveExam(active);
		java.util.Date now = new java.util.Date();
		Date timeActivate = active.getDate();
		Date timeToComplete = new Date(timeActivate.getTime()+(active.getDuration()*60*1000)-now.getTime());
		Date examDuration = new Date(active.getDuration()*60*1000);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		System.out.println("time Activate:" + timeActivate + " -> " + dateFormat.format(timeActivate)  + " float: " + timeActivate.getTime());
		System.out.println("now: " +now + " -> " + dateFormat.format(now)  + " float: " + now.getTime());
		System.out.println("examDuration:" + examDuration + " -> " + dateFormat.format(examDuration)  + " float: " + examDuration.getTime());
		System.out.println("timeToComplete:" + timeToComplete + " -> " + dateFormat.format(timeToComplete)  + " float: " + timeToComplete.getTime());
		studentsolvesExam.setTimeSeconds(timeToComplete.getTime()/1000);
		timeSeconds = studentsolvesExam.getTimeSeconds();
		if (timeline != null) {
            timeline.stop();
        }
		
        // update timerLabel
        studentsolvesExam.updateTimeLabel(timeSeconds);
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                  new EventHandler() {
                    // KeyFrame event handler
                    public void handle(Event event) {
                    	StudentSolvesExamFrame sse = (StudentSolvesExamFrame) Globals.mainContainer.getController(ClientGlobals.StudentSolvesExamID);
                        studentsolvesExam.setTimeSeconds(studentsolvesExam.getTimeSeconds()-1);
                        // update timerLabel
                        studentsolvesExam.updateTimeLabel(studentsolvesExam.getTimeSeconds());
                        if (studentsolvesExam.getTimeSeconds() <= 0 || studentsolvesExam.isLocked()) {
                            timeline.stop();
                            Globals.popUp(AlertType.INFORMATION, "Exam Locked", "Exam was locked remotlly");
                            studentsolvesExam.submitStudentsExam(false);
                        }
                        if (sse.timeChanged!=0) {
                        	Globals.popUp(AlertType.INFORMATION, "Exam Time Changed", "Exam Time Changed remotlly");
                        	sse.timeChanged=(long)0;
                        }
                      }
                }));
        timeline.playFromStart();
        
		
	}


	/**
	 *When student pressed on home button he goes back to his main window. 
	 */
	@FXML
	public void HomeButtonPressed(ActionEvent event) {
		Globals.mainContainer.setScreen(ClientGlobals.StudentMainID);
	}


	
	
}
