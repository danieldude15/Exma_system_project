package GUI;

import Controllers.ControlledScreen;
import Controllers.CourseFieldController;
import Controllers.ReportController;
import Controllers.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import logic.*;
import ocsf.client.ClientGlobals;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


/**
 * Manages Reports Gui window of Principal
 */
public class PrincipalReportsFrame implements ControlledScreen {

	/* Fields Start */

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

    private ArrayList<ExamReport> examsReports = null;
    private ArrayList<User> allStudents = null;
    private ArrayList<User> allTeachers = null;
    private ArrayList<Course> allCourse = null;

    private HashMap<Integer, User> m_studentsMap = new HashMap<>();
	private HashMap<Integer, User> m_teachersMap = new HashMap<>();
	private HashMap<Integer, Course> m_coursesMap = new HashMap<>();
	private HashMap<Integer, ExamReport> m_examReportsMap = new HashMap<>();


	/* Fields End */

	/* Constructors Start */

	/**
	 * Sets the 'Principal Reports' screen.
	 * Updating Students list before it is displayed.
	 */
    @Override public void runOnScreenChange() {
    	if (allStudents==null) {
	    	allStudents = UserController.getAllStudents();
			if (allStudents != null) {
				for(User student: allStudents)
					m_studentsMap.put(student.getID(),student);
			}
			ObservableList<User> list = FXCollections.observableArrayList(allStudents);
			m_studentsList.setItems(list);
    	} 
    }

	/* Constructors End */

	/* Methods Start */

	/**
	 * Displays the report selected by the Principal.
	 * User can manually search by entering ID.
	 * @param event - Click on 'View Report' button registered by system.
	 */
    @FXML public void viewReportButtonPressed(ActionEvent event){
    	Tab selected = m_reportsTabPane.getSelectionModel().getSelectedItem();

		if(!m_searchBox.getText().equals("")){
			if(isNumeric(m_searchBox.getText())) {
				if (m_studentsMap.containsKey(Integer.parseInt(m_searchBox.getText()))) {
					Student s = new Student(m_studentsMap.get(Integer.parseInt(m_searchBox.getText())));
					if (s == null) return;
					ViewReportFrame pvrf = (ViewReportFrame) Globals.mainContainer.getController(ClientGlobals.ViewReportID);
					pvrf.setWindowType(ViewReportFrame.type.STUDENT);
					pvrf.setStudent(s);
					pvrf.setMe(ViewReportFrame.user.Principle);
					m_searchBox.setText("");
					Globals.mainContainer.setScreen(ClientGlobals.ViewReportID);
					return;
				} else if (m_teachersMap.containsKey(Integer.parseInt(m_searchBox.getText()))) {
					Teacher t = new Teacher(m_teachersMap.get(Integer.parseInt(m_searchBox.getText())));
					if (t == null) return;
					ViewReportFrame pvrf = (ViewReportFrame) Globals.mainContainer.getController(ClientGlobals.ViewReportID);
					pvrf.setWindowType(ViewReportFrame.type.TEACHER);
					pvrf.setTeacher(t);
					pvrf.setMe(ViewReportFrame.user.Principle);
					m_searchBox.setText("");
					Globals.mainContainer.setScreen(ClientGlobals.ViewReportID);
					return;
				} else if (m_coursesMap.containsKey(Integer.parseInt(m_searchBox.getText()))) {
					Course c = m_coursesMap.get(Integer.parseInt(m_searchBox.getText()));
					if (c == null) return;
					ViewReportFrame pvrf = (ViewReportFrame) Globals.mainContainer.getController(ClientGlobals.ViewReportID);
					pvrf.setWindowType(ViewReportFrame.type.COURSE);
					pvrf.setCourse(c);
					pvrf.setMe(ViewReportFrame.user.Principle);
					m_searchBox.setText("");
					Globals.mainContainer.setScreen(ClientGlobals.ViewReportID);
					return;
				} else if (m_examReportsMap.containsKey(Integer.parseInt(m_searchBox.getText()))) {
					ExamReport ex = m_examReportsMap.get(Integer.parseInt(m_searchBox.getText()));
					if (ex == null) return;
					ViewReportFrame pvrf = (ViewReportFrame) Globals.mainContainer.getController(ClientGlobals.ViewReportID);
					pvrf.setWindowType(ViewReportFrame.type.EXAM);
					pvrf.setExamReport(ex);
					pvrf.setMe(ViewReportFrame.user.Principle);
					m_searchBox.setText("");
					Globals.mainContainer.setScreen(ClientGlobals.ViewReportID);
					return;
				}
			}else
					if (selected.equals(m_studentsTab)) {
    		Student s = new Student(m_studentsList.getSelectionModel().getSelectedItem());
        	if (s==null) return;
        	ViewReportFrame pvrf = (ViewReportFrame) Globals.mainContainer.getController(ClientGlobals.ViewReportID);
        	pvrf.setWindowType(ViewReportFrame.type.STUDENT);
        	pvrf.setStudent(s);
			pvrf.setMe(ViewReportFrame.user.Principle);
			m_searchBox.setText("");
        	Globals.mainContainer.setScreen(ClientGlobals.ViewReportID);
        	return;
    	} else if (selected.equals(m_teachersTab)) {
    		Teacher t = new Teacher(m_teachersList.getSelectionModel().getSelectedItem());
        	if (t==null) return;
        	ViewReportFrame pvrf = (ViewReportFrame) Globals.mainContainer.getController(ClientGlobals.ViewReportID);
        	pvrf.setWindowType(ViewReportFrame.type.TEACHER);
        	pvrf.setTeacher(t);
			pvrf.setMe(ViewReportFrame.user.Principle);
			m_searchBox.setText("");
        	Globals.mainContainer.setScreen(ClientGlobals.ViewReportID);
        	return;
        } else if (selected.equals(m_coursesTab)) {
        	Course c = m_coursesList.getSelectionModel().getSelectedItem();
        	if (c==null) return;
        	ViewReportFrame pvrf = (ViewReportFrame) Globals.mainContainer.getController(ClientGlobals.ViewReportID);
        	pvrf.setWindowType(ViewReportFrame.type.COURSE);
        	pvrf.setCourse(c);
			pvrf.setMe(ViewReportFrame.user.Principle);
			m_searchBox.setText("");
        	Globals.mainContainer.setScreen(ClientGlobals.ViewReportID);
        	return;
        } else if (selected.equals(m_examsTab)) {
        	ExamReport er = m_examsList.getSelectionModel().getSelectedItem();
        	if (er==null) return;
        	ViewReportFrame pvrf = (ViewReportFrame) Globals.mainContainer.getController(ClientGlobals.ViewReportID);
        	pvrf.setWindowType(ViewReportFrame.type.EXAM);
        	pvrf.setExamReport(er);
        	pvrf.setMe(ViewReportFrame.user.Principle);
			m_searchBox.setText("");
        	Globals.mainContainer.setScreen(ClientGlobals.ViewReportID);
        	return;
			} else {
				Globals.popUp(Alert.AlertType.WARNING, "Invalid character" ,"You used Invalid characters, please enter Numerical ID.");
			}
		}
    }

	/**
	 * Returns to Main Menu screen.
	 * @param event - Click on 'Back' button.
	 */
	@FXML public void backToMainMenu(ActionEvent event){
        Globals.mainContainer.setScreen(ClientGlobals.PrincipalMainID);
    }

	/**
	 * Updates and displays Students ListView.
	 * @param event - Click on Students tab.
	 */
	@FXML public void StudentsTabSelected(Event event) {
		if(!m_studentsList.getSelectionModel().isEmpty())
			m_studentsList.getSelectionModel().clearSelection();
    }

	/**
	 * Updates and displays Exams ListView.
	 * @param event - Click on Exams tab.
	 */
    @FXML public void examTabSelected(Event event) {
    	if(examsReports == null) {
	    	examsReports = ReportController.getAllExamReport();
			for(ExamReport report: Objects.requireNonNull(examsReports))
				m_examReportsMap.put(Integer.parseInt(report.examIdToString()),report);
    	}
		if(!m_examsList.getSelectionModel().isEmpty())
			m_examsList.getSelectionModel().clearSelection();
    	if(examsReports!=null) {
	    	ObservableList<ExamReport> list = FXCollections.observableArrayList(examsReports);
	    	m_examsList.setItems(list);
    	}
    }

	/**
	 * Updates and displays Courses ListView.
	 * @param event - Click on Courses tab.
	 */
    @FXML public void courseTabSelected(Event event) {
    	if (allCourse==null) {
	    	allCourse = CourseFieldController.getAllCourses();
			for(Course course: Objects.requireNonNull(allCourse))
				m_coursesMap.put(Integer.parseInt(course.courseIdToString()), course);
    	}
		if(!m_coursesList.getSelectionModel().isEmpty())
			m_coursesList.getSelectionModel().clearSelection();
		ObservableList<Course> list = FXCollections.observableArrayList(allCourse);
    	m_coursesList.setItems(list);
    }

	/**
	 * Updates and displays Teachers ListView.
	 * @param event - Click on Teachers tab.
	 */
    @FXML public void teacherTabSelected(Event event) {
    	if (allTeachers==null) {
	    	allTeachers = UserController.getAllTeachers();
			for(User teacher: Objects.requireNonNull(allTeachers))
				m_teachersMap.put(teacher.getID(), teacher);
    	}
		if(!m_teachersList.getSelectionModel().isEmpty())
			m_teachersList.getSelectionModel().clearSelection();
		ObservableList<User> list = FXCollections.observableArrayList(allTeachers);
    	m_teachersList.setItems(list);
    }

	/**
	 * Handles selection of text box in which you enter id to manually search for a report
	 * selection cleared on the current tab.
	 * @param mouseEvent - Click on mouse Event registered by system.
	 */
    @FXML public void onTextBoxMouseClick(MouseEvent mouseEvent) {
		Node tabContent = m_reportsTabPane.getSelectionModel().getSelectedItem().getContent();
		ListView currentListView = (ListView)tabContent;
		currentListView.getSelectionModel().clearSelection();
    }

	/**
	 * Checks if the entered id is a number.
	 * @param str - ID entered manually by the user.
	 * @return True - string is a number, false - string has non-numerical characters in it.
	 */
	private static boolean isNumeric(String str)
	{
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(str, pos);
		return str.length() == pos.getIndex();
	}
}