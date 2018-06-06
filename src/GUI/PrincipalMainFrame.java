package GUI;

import Controllers.ControlledScreen;
import Controllers.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import logic.Globals;
import logic.Principle;
import ocsf.client.ClientGlobals;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Frame manages Main Menu Gui window of Principal
 */
public class PrincipalMainFrame implements Initializable, ControlledScreen {

    @FXML private Button m_reportsB;
    @FXML private Button m_SchoolDataB;
    @FXML private Button logoutB;
    @FXML private ListView m_timeChangeRequestsList;
    @FXML private TabPane m_principalTabPane;
    @FXML private Tab m_infoTab;
    @FXML private Tab m_requestsTab;
    @FXML private Label welcome;
    @FXML private Label username;
    @FXML private Label userid;
    @FXML private Pane userImage;

    @Override
    public void runOnScreenChange() {
        Globals.primaryStage.setHeight(535);
        Globals.primaryStage.setWidth(523);


        Principle p = (Principle) ClientGlobals.client.getUser();

        welcome.setText("Welcome: " + p.getName());
        username.setText("UserName: " + p.getUserName());
        userid.setText("UserID: " + p.getID());
        userImage.setStyle("-fx-background-image: url(\"resources/profile/" + p.getID()+".PNG\");"
                + "-fx-background-size: 150px 150px;"
                + "-fx-background-repeat: no-repeat;");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void goToReportsScreen(ActionEvent event){
        Globals.mainContainer.setScreen(ClientGlobals.PrincipalReportsID);
    }

    @FXML
    public void goToSchoolDataScreen(ActionEvent event){

    }

    @FXML
    public void logout(ActionEvent event){
        UserController.logout();
    }
}
