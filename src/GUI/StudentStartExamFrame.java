package GUI;


import Controllers.ActiveExamController;
import Controllers.ControlledScreen;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import logic.ActiveExam;
import logic.Globals;
import logic.Student;
import ocsf.client.ClientGlobals;

public class StudentStartExamFrame implements ControlledScreen{

	@FXML TextField examCode;
	@FXML TextField studentId;
	@FXML Label examCodeError;
	@FXML Label idError;
	private Timeline timeline;
	private Integer timeSeconds;
	
	@Override
	public void runOnScreenChange() {
		// TODO Auto-generated method stub
		examCode.clear();
		studentId.clear();
		idError.setText("");
		examCodeError.setText("");

	}
	
	
	/**
	 * When student pressed on start exam button and fields are filled correct the method send the active exam to StudentSolvesExamFrame class.
	 */

	@SuppressWarnings("unchecked")
	@FXML
	public void StartExamButtonPressed(ActionEvent event)
	{
		idError.setText("");
		examCodeError.setText("");
		Alert alert;
		String sid=Integer.toString(ClientGlobals.client.getUser().getID());
		//If some of the fields is empty, then the student get an error.
		if(studentId.getText().isEmpty() || examCode.getText().isEmpty())
		{
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Start exam Failed!");
			alert.setHeaderText(null);
			alert.setContentText("You have missing fields!, please fill those fields and try again..");
			alert.showAndWait();
		}
		//Two fields are filled
		else
		{
			
			ActiveExam active=ActiveExamController.getActiveExam(examCode.getText());
			//If at least one of the fields is wrong.
			if(active==null || !(studentId.getText().equals(sid)))
			{
				//If student entered wrong exam code he gets an error message beneath the exam code Textfield.
				if(active==null)
				{
					examCodeError.setText("**Invalid Exam code!");
					examCodeError.setTextFill(javafx.scene.paint.Paint.valueOf("#FF0000"));
				}
				
				//If student entered wrong id he gets an error message beneath the id Textfield.
				if( !(studentId.getText().equals(sid)) ) 
				{
					idError.setText("**Invalid user Id!");
					idError.setTextFill(javafx.scene.paint.Paint.valueOf("#FF0000"));
				}
			}
			//Student filled Two correct fields 
			else  {
				Boolean canStart = ActiveExamController.StudentCheckedInToActiveExam((Student) ClientGlobals.client.getUser(), active);
				if (canStart) {
					StudentSolvesExamFrame studentsolvesExam = (StudentSolvesExamFrame) Globals.mainContainer.getController(ClientGlobals.StudentSolvesExamID);
					studentsolvesExam.SetActiveExam(active);
					///////////////////
		
					if (timeline != null) {
			            timeline.stop();
			        }
			        timeSeconds = active.getDuration()*60;
			        
			        // update timerLabel
			        studentsolvesExam.updateTimeLabel(timeSeconds);
			        timeline = new Timeline();
			        timeline.setCycleCount(Timeline.INDEFINITE);
			        timeline.getKeyFrames().add(
			                new KeyFrame(Duration.seconds(1),
			                  new EventHandler() {
			                    // KeyFrame event handler
			                    public void handle(Event event) {
			                        timeSeconds--;
			                        // update timerLabel
			                        studentsolvesExam.updateTimeLabel(timeSeconds);
			                        if (timeSeconds <= 0) {
			                            timeline.stop();
			                            System.out.println("TIMER ENDED!");
			                        }
			                      }
			                }));
			        timeline.playFromStart();
			        
			        /////////////////
					Globals.mainContainer.setScreen(ClientGlobals.StudentSolvesExamID);
				} else {
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("CAn Not Start Exam");
					alert.setHeaderText(null);
					alert.setContentText("It seems as you already submitted your exam and cannot start again.");
					alert.showAndWait();
				}
			}
		}
	}
			
	
	/**
	 *When student pressed on home button he goes back to his main window. 
	 */
	@FXML
	public void HomeButtonPressed(ActionEvent event) {
		Globals.mainContainer.setScreen(ClientGlobals.StudentMainID);
	}


	
	
}
