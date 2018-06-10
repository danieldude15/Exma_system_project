package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import logic.Globals;
import ocsf.client.ClientGlobals;


public class PrincipalViewQuestionFrame {

    @FXML private Button m_backButton;
    @FXML private ListView m_questionInfoList;

    /**
     *  method returns you to ViewData screen
      */
    @FXML
    public void backToViewData(ActionEvent event) {
        Globals.mainContainer.setScreen(ClientGlobals.PrincipalViewDataID);
    }
}
