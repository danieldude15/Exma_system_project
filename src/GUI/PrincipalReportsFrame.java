package GUI;

import java.util.ArrayList;

import Controllers.ControlledScreen;
import Controllers.CourseFieldController;
import Controllers.ReportController;
import Controllers.UserController;
import GUI.PrincipleViewReportFrame.type;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import logic.ExamReport;
import logic.*;
import ocsf.client.ClientGlobals;


/**
 * Frame manages Reports Gui window of Principal
 */
public class PrincipalReportsFrame implements ControlledScreen {

    @FXML private TabPane m_reportsTabPane;
    @FXML private Tab m_studentsTab;
    @FXML private ListView<User> m_studentsList;
    @FXML private Tab m_teachersTab;
    @FXML private ListView<User> m_teachersList;
    @FXML private Tab m_coursesTab;
    @FXML private ListView<Course> m_coursesList;
    @FXML private Tab m_examsTab;
    @FXML private ListView<ExamReport> m_examsList;
    @FXML private TextField m_searchBox;
    @FXML private Button m_searchBtn;
    @FXML private Button m_backtoMainBtn;

    ArrayList<ExamReport> examsReports = null;
    ArrayList<User> allStudents = null;
    ArrayList<User> allTeachers = null;
    ArrayList<Course> allCourse = null;
    
    @Override public void runOnScreenChange() {
        Globals.primaryStage.setHeight(445);
        Globals.primaryStage.setWidth(515);
        m_reportsTabPane.getSelectionModel().select(m_studentsTab);;
        StudentsTabSelected(null);
    }

    @FXML public void viewReportButtonPressed(ActionEvent event){
    	Tab selected = m_reportsTabPane.getSelectionModel().getSelectedItem();
    	if (selected.equals(m_studentsTab)) {
    		Student s = new Student(m_studentsList.getSelectionModel().getSelectedItem());
        	if (s==null) return;
        	PrincipleViewReportFrame pvrf = (PrincipleViewReportFrame) Globals.mainContainer.getController(ClientGlobals.PrincipalViewReportID);
        	pvrf.setWindowType(PrincipleViewReportFrame.type.STUDENT);
        	pvrf.setStudent(s);
        	Globals.mainContainer.setScreen(ClientGlobals.PrincipalViewReportID);
        	return;
    	} else if (selected.equals(m_teachersTab)) {
    		Teacher t = new Teacher(m_teachersList.getSelectionModel().getSelectedItem());
        	if (t==null) return;
        	PrincipleViewReportFrame pvrf = (PrincipleViewReportFrame) Globals.mainContainer.getController(ClientGlobals.PrincipalViewReportID);
        	pvrf.setWindowType(PrincipleViewReportFrame.type.TEACHER);
        	pvrf.setTeacher(t);
        	Globals.mainContainer.setScreen(ClientGlobals.PrincipalViewReportID);
        	return;
        } else if (selected.equals(m_coursesTab)) {
        	Course c = m_coursesList.getSelectionModel().getSelectedItem();
        	if (c==null) return;
        	PrincipleViewReportFrame pvrf = (PrincipleViewReportFrame) Globals.mainContainer.getController(ClientGlobals.PrincipalViewReportID);
        	pvrf.setWindowType(PrincipleViewReportFrame.type.COURSE);
        	pvrf.setCourse(c);
        	Globals.mainContainer.setScreen(ClientGlobals.PrincipalViewReportID);
        	return;
        } else if (selected.equals(m_examsTab)) {
        	ExamReport er = m_examsList.getSelectionModel().getSelectedItem();
        	if (er==null) return;
        	PrincipleViewReportFrame pvrf = (PrincipleViewReportFrame) Globals.mainContainer.getController(ClientGlobals.PrincipalViewReportID);
        	pvrf.setWindowType(PrincipleViewReportFrame.type.EXAM);
        	pvrf.setExamReport(er);
        	Globals.mainContainer.setScreen(ClientGlobals.PrincipalViewReportID);
        	return;
        }
    }

    @FXML public void backToMainMenu(ActionEvent event){
        Globals.mainContainer.setScreen(ClientGlobals.PrincipalMainID);
    }

    @FXML public void StudentsTabSelected(Event event) {
    	if (allStudents==null) {
	    	allStudents = UserController.getAllStudents(); 
    	} 
		ObservableList<User> list = FXCollections.observableArrayList(allStudents);
    	m_studentsList.setItems(list);
    }
    
    @FXML public void examTabSelected(Event event) {
    	if(examsReports==null) {
	    	examsReports = ReportController.getAllExamReport(); 
    	}
    	if(examsReports!=null) {
	    	ObservableList<ExamReport> list = FXCollections.observableArrayList(examsReports);
	    	m_examsList.setItems(list);
    	}
    }
    
    @FXML public void courseTabSelected(Event event) {
    	if (allCourse==null) {
	    	allCourse = CourseFieldController.getAllCourses(); 
    	} 
		ObservableList<Course> list = FXCollections.observableArrayList(allCourse);
    	m_coursesList.setItems(list);
    }
    
    @FXML public void teacherTabSeleted(Event event) {
    	if (allTeachers==null) {
	    	allTeachers = UserController.getAllTeachers(); 
    	} 
		ObservableList<User> list = FXCollections.observableArrayList(allTeachers);
    	m_teachersList.setItems(list);
    }
    
    // method handles selection of text box in which you enter id to manually search for data
    @FXML public void onTextBoxMouseClick(MouseEvent mouseEvent) {

    }
}