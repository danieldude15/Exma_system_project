package GUI;

import static ocsf.client.ClientGlobals.PrincipalViewDataID;

import java.util.ArrayList;

import Controllers.ControlledScreen;
import Controllers.TimeChangeController;
import Controllers.UserController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import logic.Principle;
import logic.TimeChangeRequest;
import ocsf.client.ClientGlobals;

/**
 * Frame manages Main Menu Gui window of Principal
 */
public class PrincipalMainFrame implements ControlledScreen {

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
	                  new EventHandler() {
	                    // KeyFrame event handler
	                    public void handle(Event event) {
	                    	if (newRequestArrived) {
	                    		newRequestArrived=false;
	                    		Globals.popUp(AlertType.INFORMATION,"New Time Change Request","A teacher has submitted a new Time Change request for an Active Exam. Please go and handle it!");
	                			refreshRequestListView();
	                    	}
	                    }
	                }));
	        timeline.playFromStart();
        }

        refreshRequestListView();
        
        Principle p = (Principle) ClientGlobals.client.getUser();

        welcome.setText("Welcome: " + p.getName());
        username.setText("UserName: " + p.getUserName());
        userid.setText("UserID: " + p.getID());
        userImage.setStyle("-fx-background-image: url(\"resources/profile/" + p.getID()+".PNG\");"
                + "-fx-background-size: 100px 100px;"
                + "-fx-background-repeat: no-repeat;");
    }

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

    @FXML public void goToReportsScreen(ActionEvent event){
        Globals.mainContainer.setScreen(ClientGlobals.PrincipalReportsID);
    }

    @FXML public void goToSchoolDataScreen(ActionEvent event){
        Globals.mainContainer.setScreen(PrincipalViewDataID);
    }

    @FXML public void logout(ActionEvent event){
        UserController.logout();
    }

    @FXML public void approveRequst(ActionEvent event) {
	   if (selectedTCR!=null) {
		   selectedTCR.setStatus(true);
		   TimeChangeController.sendResponse(selectedTCR);
	   } else {
		 //selectedTCR is null!
	   }
	   refreshRequestListView();
    }
    
    @FXML public void denyRequest(ActionEvent event) {
    	if (selectedTCR!=null) {
 		   selectedTCR.setStatus(false);
 		   TimeChangeController.sendResponse(selectedTCR);
 	   } else {
 		   //selectedTCR is null!
 	   }
    	refreshRequestListView();
    }
    
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

	public void setNewRequestArrived(boolean newRequestArrived) {
		this.newRequestArrived = newRequestArrived;
	}
}
