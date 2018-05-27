package GUI;

import Controllers.ControlledScreen;
import Controllers.UserController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import logic.Globals;
import logic.iMessage;
import ocsf.client.ClientGlobals;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalMainFrame implements Initializable, ControlledScreen {

    @FXML Button m_reportsB;
    @FXML Button m_examsAndQuestionsB;
    @FXML Button m_usersInfoB;
    @FXML ListView<String> m_timeChangeRquestsList;
    @FXML TabPane m_principalTabPane;
    @FXML Tab m_infoTab;
    @FXML Tab m_reauestsTab;

    @Override
    public void runOnScreenChange() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void goToReportsScreen(ActionEvent event){

    }

    @FXML
    public void goToExamsAndQuestionsScreen(ActionEvent event){

    }

    @FXML
    public void goToUsersInfoScreen(ActionEvent event){

    }

    @FXML
    public void logout(ActionEvent event){
        UserController.logout();
    }
}
