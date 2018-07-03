package GUI;

import Controllers.ControlledScreen;
import Controllers.TimeChangeController;
import Controllers.UserController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import logic.Globals;
import logic.Principal;
import logic.TimeChangeRequest;
import ocsf.client.ClientGlobals;

import java.util.ArrayList;

import static ocsf.client.ClientGlobals.PrincipalViewDataID;

/**
 * Manages Main Menu GUI window of Principal
 */
public class PrincipalMainFrame implements ControlledScreen {

	/* Fields Start */

    @FXML private Button m_reportsB;
    @FXML private Button m_SchoolDataB;
    @FXML private Button logoutB;
    @FXML private ListView<TimeChangeRequest> m_timeChangeRequestsList;
    @FXML private Label welcome;
    @FXML private Label requester;
    @FXML private Label courseRequest;
    @FXML private Label timeExtention;
    @FXML private Label reasonForTimeChange;
    @FXML private Label username;
    @FXML private Label userid;
    @FXML private Pane userImage;
    @FXML private HBox requestInfo;
    
    private TimeChangeRequest selectedTCR=null;
    private boolean newRequestArrived=false;
    private boolean oneKeyFrame = true;
    private ArrayList<TimeChangeRequest> requests = new ArrayList<TimeChangeRequest>();

	/* Fields End */

	/* Constructors Start */

	/**
	 * Sets the 'Principal Main Menu' screen,
	 * Creating a {@link Timeline} that listens to time change requests generated by teachers.
	 * Setting Principal's info in appropriate fields.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void runOnScreenChange() {   
    	requestInfo.setVisible(false);
    	
        if (oneKeyFrame) {
        	oneKeyFrame=false;
        	Timeline timeline = new Timeline();
	        timeline.setCycleCount(Timeline.INDEFINITE);
	        timeline.getKeyFrames().add(
	                new KeyFrame(Duration.seconds(1),
							(EventHandler) event -> {
								// KeyFrame event handler
								if (newRequestArrived) {
									newRequestArrived = false;
									Globals.popUp(AlertType.INFORMATION,"New Time Change Request","A teacher has submitted a new Time Change request for an Active Exam. Please go and handle it!");
									refreshRequestListView();
								}
							}));
	        timeline.playFromStart();
        }

        refreshRequestListView();
        
        Principal p = (Principal) ClientGlobals.client.getUser();

        welcome.setText("Welcome: " + p.getName());
        username.setText("UserName: " + p.getUserName());
        userid.setText("UserID: " + p.getID());
        userImage.setStyle("-fx-background-image: url(\"resources/profile/" + p.getID()+".PNG\");"
                + "-fx-background-size: 100px 100px;"
                + "-fx-background-repeat: no-repeat;");
    }

	/* Constructors End */

	/* Methods Start */

	/**
	 * Refreshes the Time Change Request ListView.
	 */
    private void refreshRequestListView() {
		requests = TimeChangeController.getAllRequests();
		if (requests!=null) {
			ObservableList<TimeChangeRequest> list = FXCollections.observableArrayList(requests);
			m_timeChangeRequestsList.setItems(list);//Insert all student's solved exams(courseName+grade) into the ListView "solvedExamList"
			if (requests.size()==0) {
				requestInfo.setVisible(false);
			}
		}
	}

	/**
	 * Changes the current screen to 'Reports' screen.
	 * @param event - Mouse clicked on 'Reports' button.
	 */
    @FXML public void goToReportsScreen(ActionEvent event){
        Globals.mainContainer.setScreen(ClientGlobals.PrincipalReportsID);
    }

	/**
	 * Changes the current screen to 'School Data' screen.
	 * @param event - Mouse clicked on 'School Data' button.
	 */
	@FXML public void goToSchoolDataScreen(ActionEvent event){
        Globals.mainContainer.setScreen(PrincipalViewDataID);
    }

	/**
	 * Logs the Principle out of the system.
	 * @param event - Mouse clicked on 'Logout' button.
	 */
	@FXML public void logout(ActionEvent event){
        UserController.logout();
    }

	/**
	 * Sets the status of the Time Change Request to true, which means it is approved.
	 * @param event - Mouse clicked on 'Approve' button in the Box with additional info about the Time Change Request.
	 */
	@FXML public void approveRequst(ActionEvent event) {
	   if (selectedTCR!=null) {
		   selectedTCR.setStatus(true);
		   TimeChangeController.sendResponse(selectedTCR);
	   } else {
		 //selectedTCR is null!
	   }
	   refreshRequestListView();
    }

	/**
	 * Sets the status of the Time Change Request to false, which means it is denied.
	 * @param event - Mouse clicked on 'Deny' button in the Box with additional info about the Time Change Request.
	 */
	@FXML public void denyRequest(ActionEvent event) {
    	if (selectedTCR!=null) {
 		   selectedTCR.setStatus(false);
 		   TimeChangeController.sendResponse(selectedTCR);
 	   } else {
 		   //selectedTCR is null!
 	   }
    	refreshRequestListView();
    }

	/**
	 * Opens a Box with additional info about the request, with buttons to approve or deny it.
	 * @param event - Mouse clicked on one of the requests in TIme Change Requests listview.
	 */
	@FXML public void timeChangeSelected(MouseEvent event) {
    	TimeChangeRequest tChangeRequest = m_timeChangeRequestsList.getSelectionModel().getSelectedItem();
    	if (tChangeRequest!=null) {
    		requestInfo.setVisible(true);
    		requester.setText(tChangeRequest.getTeacher().getName());;
    	    courseRequest.setText(tChangeRequest.getActiveExam().getCourse().getName() + " in " + tChangeRequest.getActiveExam().getField().getName());
    	    timeExtention.setText(Long.toString(tChangeRequest.getNewTime()));
    	    reasonForTimeChange.setText(tChangeRequest.getReasonForTimeChange());
    	    selectedTCR = tChangeRequest;
    	}
    }
    
	public boolean isNewRequestArrived() {
		return newRequestArrived;
	}

	/**
	 * Sets the status of a new time change request for an exam by a teacher.
	 * @param newRequestArrived - True - if new time change request generated by a teacher, false - otherwise.
	 */
	public void setNewRequestArrived(boolean newRequestArrived) {
		this.newRequestArrived = newRequestArrived;
	}

	/* Methods End */
}
