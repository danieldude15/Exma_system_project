package GUI;

import Controllers.ControlledScreen;
import Controllers.QuestionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import logic.Globals;
import logic.Question;
import ocsf.client.ClientGlobals;

import java.net.URL;
import java.util.ArrayList;
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

    private ArrayList<Question> m_questionsToBeDisplayed;

    @Override
    public void runOnScreenChange() {
        Globals.primaryStage.setHeight(445);
        Globals.primaryStage.setWidth(515);
        m_searchBox.setText("Enter ID Here");
        updateQuestionsList();
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

    // method handles selection of text box in which you enter id to manually search for data
    @FXML
    public void onTextBoxMouseClick(MouseEvent mouseEvent) {
        if(m_searchBox.getText().equals("Enter ID Here"))
            m_searchBox.clear();
    }

    @FXML
    public void onQuestionsTabSelection(Event event) {

    }

    private void updateQuestionsList() {
        m_questionsToBeDisplayed = QuestionController.getAllQuestions();
        ArrayList<String> basicQuestionInfo = new ArrayList<>();
        if (m_questionsToBeDisplayed != null) {
            for (Question question : m_questionsToBeDisplayed) {
                String displayedQID;
                int qID = question.getID();
                int fID = question.getField().getID();
                if (fID < 10) {
                    displayedQID = "0" + String.valueOf(fID);
                } else
                    displayedQID = String.valueOf(fID);
                if (qID < 10)
                    displayedQID = displayedQID + "00" + String.valueOf(qID);
                else if (qID < 100 && qID > 9)
                    displayedQID = displayedQID + "0" + String.valueOf(qID);
                else
                    displayedQID = displayedQID + String.valueOf(qID);
                String questionInfo = "QuestionID: " + displayedQID + " | " + question.getQuestionString();
                basicQuestionInfo.add(questionInfo);
            }
            m_questionsList.getItems().clear();
            ObservableList<String> list = FXCollections.observableArrayList(basicQuestionInfo);
            m_questionsList.setItems(list);
        }
    }
}