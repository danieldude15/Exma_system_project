package GUI;

import Controllers.ControlledScreen;
import Controllers.QuestionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import logic.Globals;
import logic.Question;
import ocsf.client.ClientGlobals;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
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

    private HashMap<Integer, Question> m_questionsMap;

    @Override
    public void runOnScreenChange() {
        Globals.primaryStage.setHeight(445);
        Globals.primaryStage.setWidth(515);
        m_questionsMap = new HashMap<>();
        updateQuestionsList();
    }

    @FXML
    public void viewQuestion(ActionEvent event){

        PrincipalViewQuestionFrame viewQuestionFrame = (PrincipalViewQuestionFrame) Globals.mainContainer.getController(ClientGlobals.PrincipalViewQuestionID);
        Dialog<String> noSuchQuestionWarning = new Dialog<>();
        noSuchQuestionWarning.setContentText("There is no such Question in the Database");

        if (!m_questionsList.getSelectionModel().isEmpty()){
            String questionToBeDisplayed = (String) m_questionsList.getSelectionModel().getSelectedItem();
            String[] splitedQuestion = questionToBeDisplayed.split(" ");
            viewQuestionFrame.setQuestion(m_questionsMap.get(Integer.parseInt(splitedQuestion[1])));
            Globals.mainContainer.setScreen(ClientGlobals.PrincipalViewQuestionID);
        }
        if(!m_searchBox.getText().equals("")){
            if(m_questionsMap.containsKey(Integer.parseInt(m_searchBox.getText()))) {
                viewQuestionFrame.setQuestion(m_questionsMap.get(Integer.parseInt(m_searchBox.getText())));
                Globals.mainContainer.setScreen(ClientGlobals.PrincipalViewQuestionID);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR,"There is no such Entry in DataBase",ButtonType.OK);
                Optional<ButtonType> result = alert.showAndWait();
            }
        }
    }

    @FXML
    public void backToMainMenu(ActionEvent event){
        Globals.mainContainer.setScreen(ClientGlobals.PrincipalMainID);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // method handles selection of text box in which you enter id to manually search for data ( selection disabled on the current tab )
    @FXML
    public void onTextBoxMouseClick(MouseEvent mouseEvent) {
        Node tabContent = m_dataTabPane.getSelectionModel().getSelectedItem().getContent();
        ListView currentListView = (ListView)tabContent;
        currentListView.getSelectionModel().clearSelection();
    }

    @FXML
    public void onStudentsTabSelection(Event event) {
        if(!m_studentsList.getSelectionModel().isEmpty())
            m_studentsList.getSelectionModel().clearSelection();
    }

    @FXML
    public void onTeachersTabSelection(Event event) {
        if(!m_teachersList.getSelectionModel().isEmpty())
            m_teachersList.getSelectionModel().clearSelection();
    }

    @FXML
    public void onQuestionsTabSelection(Event event) {
        if(!m_questionsList.getSelectionModel().isEmpty())
            m_questionsList.getSelectionModel().clearSelection();
    }

    @FXML
    public void onExamsTabSelection(Event event) {
        if(!m_examsList.getSelectionModel().isEmpty())
            m_examsList.getSelectionModel().clearSelection();
    }

    @FXML
    public void onFieldsTabSelection(Event event) {
        if(!m_fieldsList.getSelectionModel().isEmpty())
            m_fieldsList.getSelectionModel().clearSelection();
    }

    @FXML
    public void onCoursesTabSelection(Event event) {
        if(!m_coursesList.getSelectionModel().isEmpty())
            m_coursesList.getSelectionModel().clearSelection();
    }

    private void updateQuestionsList() {
        ArrayList<Question> m_questionsToBeDisplayed = QuestionController.getAllQuestions();
        ArrayList<String> basicQuestionInfo = new ArrayList<>();
        if (m_questionsToBeDisplayed != null) {
            for (Question question : m_questionsToBeDisplayed) {
                m_questionsMap.put(Integer.parseInt(question.questionIDToString()),question);
                String questionInfo = "QuestionID: " + question.questionIDToString() + " | " + question.getQuestionString();
                basicQuestionInfo.add(questionInfo);
            }
            m_questionsList.getItems().clear();
            ObservableList<String> list = FXCollections.observableArrayList(basicQuestionInfo);
            m_questionsList.setItems(list);
        }
    }
}