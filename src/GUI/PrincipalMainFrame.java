package GUI;

import Controllers.ControlledScreen;
import Controllers.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import logic.Globals;
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

    @Override
    public void runOnScreenChange() {
        Globals.primaryStage.setHeight(535);
        Globals.primaryStage.setWidth(523);
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
