package GUI;

import Controllers.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import logic.*;
import ocsf.client.ClientGlobals;

import java.net.URL;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class PrincipalViewDataFrame implements Initializable , ControlledScreen {

    @FXML private TabPane m_dataTabPane;
    @FXML private Tab m_studentsTab;
    @FXML private ListView<String> m_studentsList;
    @FXML private Tab m_teachersTab;
    @FXML private ListView<String> m_teachersList;
    @FXML private Tab m_questionsTab;
    @FXML private ListView<String> m_questionsList;
    @FXML private Tab m_examsTab;
    @FXML private ListView<String> m_examsList;
    @FXML private Tab m_fieldsTab;
    @FXML private ListView<String> m_fieldsList;
    @FXML private Tab m_coursesTab;
    @FXML private ListView<String> m_coursesList;
    @FXML private TextField m_searchBox;
    @FXML private Button m_searchBtn;
    @FXML private Button m_backToMainBtn;

    private HashMap<Integer, Question> m_questionsMap;
    private HashMap<Integer, Exam> m_examsMap;
    private HashMap<Integer, Field> m_fieldsMap;
    private HashMap<Integer, User> m_studentsAndTeachersMap;

    @Override
    public void runOnScreenChange() {
    }

    @FXML
    public void viewData(ActionEvent event){

        PrincipalViewQuestionFrame viewQuestionFrame = (PrincipalViewQuestionFrame) Globals.mainContainer.getController(ClientGlobals.PrincipalViewQuestionID);
        PrincipalViewExamFrame viewExamFrame = (PrincipalViewExamFrame) Globals.mainContainer.getController(ClientGlobals.PrincipalViewExamID);
        PrincipalViewFieldFrame viewFieldFrame = (PrincipalViewFieldFrame) Globals.mainContainer.getController(ClientGlobals.PrincipalViewFieldID);

        if (!m_questionsList.getSelectionModel().isEmpty()){
            m_searchBox.setText("");
            String questionToBeDisplayed = m_questionsList.getSelectionModel().getSelectedItem();
            String[] splitedQuestion = questionToBeDisplayed.split(" ");
            viewQuestionFrame.setQuestion(m_questionsMap.get(Integer.parseInt(splitedQuestion[1])));
            Globals.mainContainer.setScreen(ClientGlobals.PrincipalViewQuestionID);
        }else if(!m_examsList.getSelectionModel().isEmpty()){
            m_searchBox.setText("");
            String examToBeDisplayed = m_examsList.getSelectionModel().getSelectedItem();
            String[] splitedExam = examToBeDisplayed.split(" ");
            viewExamFrame.setExam(m_examsMap.get(Integer.parseInt(splitedExam[1])));
            Globals.mainContainer.setScreen(ClientGlobals.PrincipalViewExamID);
        }else if(!m_fieldsList.getSelectionModel().isEmpty()){
            m_searchBox.setText("");
            String examToBeDisplayed = m_fieldsList.getSelectionModel().getSelectedItem();
            String[] splitedExam = examToBeDisplayed.split(" ");
            viewFieldFrame.setField(m_fieldsMap.get(Integer.parseInt(splitedExam[1])));
            Globals.mainContainer.setScreen(ClientGlobals.PrincipalViewFieldID);
        }
        if(!m_searchBox.getText().equals("")){
            if(isNumeric(m_searchBox.getText())){
                if (m_questionsMap.containsKey(Integer.parseInt(m_searchBox.getText()))) {
                    viewQuestionFrame.setQuestion(m_questionsMap.get(Integer.parseInt(m_searchBox.getText())));
                    Globals.mainContainer.setScreen(ClientGlobals.PrincipalViewQuestionID);
                }else if (m_examsMap.containsKey(Integer.parseInt(m_searchBox.getText()))) {
                    viewExamFrame.setExam(m_examsMap.get(Integer.parseInt(m_searchBox.getText())));
                    Globals.mainContainer.setScreen(ClientGlobals.PrincipalViewExamID);
                }else if (m_fieldsMap.containsKey(Integer.parseInt(m_searchBox.getText()))){
                        viewFieldFrame.setField(m_fieldsMap.get(Integer.parseInt(m_searchBox.getText())));
                        Globals.mainContainer.setScreen(ClientGlobals.PrincipalViewFieldID);
                } else {
                	Globals.popUp(Alert.AlertType.WARNING, "not Entry" ,"There is no such Entry in DataBase");
                }
            }else {
            	Globals.popUp(Alert.AlertType.WARNING, "Invalid character" ,"You used Invalid characters, please enter Numerical ID.");
            }
        }
    }

    @FXML
    public void backToMainMenu(ActionEvent event){
        Globals.mainContainer.setScreen(ClientGlobals.PrincipalMainID);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        m_questionsMap = new HashMap<>();
        m_examsMap = new HashMap<>();
        m_fieldsMap = new HashMap<>();
        m_studentsAndTeachersMap = new HashMap<>();
        updateStudentsList();
        updateTeachersList();
        updateQuestionsList();
        updateExamsList();
        updateFieldsList();
        updateCoursesList();
        m_coursesList.setFocusTraversable(false);
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
        try{
            if(m_searchBtn.isDisabled()){
                m_searchBtn.setDisable(false);
            }
        }catch (NullPointerException e){
            System.out.println("No Object Yet");
        }

    }

    @FXML
    public void onTeachersTabSelection(Event event) {
        if(!m_teachersList.getSelectionModel().isEmpty())
            m_teachersList.getSelectionModel().clearSelection();
        try{
            if(m_searchBtn.isDisabled()){
                m_searchBtn.setDisable(false);
            }
        }catch (NullPointerException e){
            System.out.println("No Object Yet");
        }
    }

    @FXML
    public void onQuestionsTabSelection(Event event) {
        if(m_questionsList.getItems().isEmpty())
            updateQuestionsList();
        if(!m_questionsList.getSelectionModel().isEmpty())
            m_questionsList.getSelectionModel().clearSelection();
        try{
            if(m_searchBtn.isDisabled()){
                m_searchBtn.setDisable(false);
            }
        }catch (NullPointerException e){
            System.out.println("No Object Yet");
        }
    }

    @FXML
    public void onExamsTabSelection(Event event) {
        if(m_examsList.getItems().isEmpty())
            updateExamsList();
        if(!m_examsList.getSelectionModel().isEmpty())
            m_examsList.getSelectionModel().clearSelection();
        try{
            if(m_searchBtn.isDisabled()){
                m_searchBtn.setDisable(false);
            }
        }catch (NullPointerException e){
            System.out.println("No Object Yet");
        }
    }

    @FXML
    public void onFieldsTabSelection(Event event) {
        if(m_fieldsList.getItems().isEmpty())
            updateFieldsList();
        if(!m_fieldsList.getSelectionModel().isEmpty())
            m_fieldsList.getSelectionModel().clearSelection();
        try{
            if(m_searchBtn.isDisabled()){
                m_searchBtn.setDisable(false);
            }
        }catch (NullPointerException e){
            System.out.println("No Object Yet");
        }
    }

    @FXML
    public void onCoursesTabSelection(Event event) {
        if(m_coursesList.getItems().isEmpty()){
            updateCoursesList();
        }
        if(!m_coursesList.getSelectionModel().isEmpty())
            m_coursesList.getSelectionModel().clearSelection();
        m_searchBtn.setDisable(true);
    }

    /**
     *  method checks if the entered id is a number
     * @param str - ID entered manually by the user
     * @return True - string is a number, false - string has non-numerical characters in it
     */
    public static boolean isNumeric(String str)
    {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    private void updateStudentsList(){
        ArrayList<User> m_studentsToBeDisplayed = UserController.getAllStudents();
        ArrayList<String> toBeDisplayed = new ArrayList<>();
        if (m_studentsToBeDisplayed != null) {
            for (User student : m_studentsToBeDisplayed) {
                m_studentsAndTeachersMap.put(student.getID(), student);
                toBeDisplayed.add(student.toString());
            }
        }
        m_studentsList.getItems().clear();
        ObservableList<String> list = FXCollections.observableArrayList(toBeDisplayed);
        m_studentsList.setItems(list);
    }

    private void updateTeachersList(){
        ArrayList<User> m_teachersToBeDisplayed = UserController.getAllTeachers();
        ArrayList<String> toBeDisplayed = new ArrayList<>();
        if (m_teachersToBeDisplayed != null) {
            for (User teacher : m_teachersToBeDisplayed) {
                m_studentsAndTeachersMap.put(teacher.getID(), teacher);
                toBeDisplayed.add(teacher.toString());
            }
        }
        m_teachersList.getItems().clear();
        ObservableList<String> list = FXCollections.observableArrayList(toBeDisplayed);
        m_teachersList.setItems(list);
    }

    private void updateQuestionsList() {
        ArrayList<Question> m_questionsToBeDisplayed = QuestionController.getAllQuestions();
        ArrayList<String> basicQuestionInfo = new ArrayList<>();
        if (m_questionsToBeDisplayed != null) {
            for (Question question : m_questionsToBeDisplayed) {
                m_questionsMap.put(Integer.parseInt(question.questionIDToString()), question);
                String questionInfo = "QuestionID: " + question.questionIDToString() + " | " + question.getQuestionString();
                basicQuestionInfo.add(questionInfo);
            }
            m_questionsList.getItems().clear();
            ObservableList<String> list = FXCollections.observableArrayList(basicQuestionInfo);
            m_questionsList.setItems(list);
        }
    }

    private void updateExamsList() {
        ArrayList<Exam> m_examsToBeDisplayed = ExamController.getAllExams();
        ArrayList<String> basicExamInfo = new ArrayList<>();
        if (m_examsToBeDisplayed != null) {
            for (Exam exam : m_examsToBeDisplayed) {
                m_examsMap.put(Integer.parseInt(exam.examIdToString()), exam);
                String questionInfo = "ExamID: " + exam.examIdToString() + " | Course: " + exam.getCourse().getName() + " Field: " + exam.getField().getName();
                basicExamInfo.add(questionInfo);
            }
        }
        m_examsList.getItems().clear();
        ObservableList<String> list1 = FXCollections.observableArrayList(basicExamInfo);
        m_examsList.setItems(list1);
    }

    private void updateFieldsList() {
        ArrayList<Field> m_fieldsToBeDisplayed = CourseFieldController.getAllFields();
        ArrayList<String> basicFieldInfo = new ArrayList<>();
        if (m_fieldsToBeDisplayed != null) {
            for (Field field : m_fieldsToBeDisplayed) {
                m_fieldsMap.put(Integer.parseInt(field.fieldIdToString()), field);
                String fieldInfo = "FieldID: " + field.fieldIdToString() + " | " + field.getName();
                basicFieldInfo.add(fieldInfo);
            }
        }
        m_fieldsList.getItems().clear();
        ObservableList<String> list1 = FXCollections.observableArrayList(basicFieldInfo);
        m_fieldsList.setItems(list1);
    }

    private void updateCoursesList(){
        ArrayList<Course> m_coursesToBeDisplayed = CourseFieldController.getAllCourses();
        ArrayList<String> coursesInfo = new ArrayList<>();
        if (m_coursesToBeDisplayed != null) {
            for (Course course : m_coursesToBeDisplayed) {
                String courseInfo = "CourseID: " + course.courseIdToString() + " | Course Name: " + course.getName() + " | Field: " + course.getField().getName();
                coursesInfo.add(courseInfo);
            }
        }
        m_coursesList.getItems().clear();
        ObservableList<String> list1 = FXCollections.observableArrayList(coursesInfo);
        m_coursesList.setItems(list1);
    }
}