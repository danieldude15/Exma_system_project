package GUI;

import Controllers.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import logic.Globals;
import ocsf.client.ClientGlobals;

import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalViewDataFrame implements Initializable , ControlledScreen {

    @FXML private TabPane m_dataTabPane;
    @FXML private Tab m_studentsTab;
    @FXML private ListView m_studentsList;
    @FXML private Tab m_teachersTab;
    @FXML private ListView m_teachersList;
    @FXML private Tab m_questionsTab;
    @FXML private ListView m_questionsList;
    @FXML private Tab m_examsTab;
    @FXML private ListView m_examsList;
    @FXML private Tab m_fieldsTab;
    @FXML private ListView m_fieldsList;
    @FXML private Tab m_coursesTab;
    @FXML private ListView m_coursesList;
    @FXML private TextField m_searchBox;
    @FXML private Button m_searchBtn;
    @FXML private Button m_backtoMainBtn;

    @Override
    public void runOnScreenChange() {
        Globals.primaryStage.setHeight(445);
        Globals.primaryStage.setWidth(515);
    }

    @FXML
    public void searchForID(ActionEvent event){

    }

    @FXML
    public void backToMainMenu(ActionEvent event){
        Globals.mainContainer.setScreen(ClientGlobals.PrincipalMainID);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
