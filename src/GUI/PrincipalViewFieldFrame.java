package GUI;

import Controllers.ControlledScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import logic.Course;
import logic.Field;
import logic.Globals;
import logic.Teacher;
import ocsf.client.ClientGlobals;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Manages The screen where the principal views additional data on a field.
 */
public class PrincipalViewFieldFrame implements Initializable, ControlledScreen {

    /* Fields Start */

    @FXML private Label m_fieldIDlbl;
    @FXML private Label m_fieldNamelbl;
    @FXML private ListView<String> m_coursesList;
    @FXML private ListView<String> m_teachersList;

    private Field field;

    /* Fields End */

    /* Constructors Start */

    /**
     * Sets the 'View Field' screen.
     * Updating all the lists and labels present in the screen before it is displayed.
     */
    @Override
    public void runOnScreenChange() {

        if(field != null){
            m_fieldIDlbl.setText("FieldID: " + field.fieldIdToString());
            m_fieldNamelbl.setText("Name: " + field.getName());
            fillTeachersList();
            fillCoursesList();
        }
    }

    /**
     * Initializes the frame disabling clicking on ListViews.
     * @param location - no use in this method.
     * @param resources - no use in this method.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // making list views unclickable
        m_teachersList.addEventFilter(MouseEvent.MOUSE_PRESSED, Event::consume);
        m_coursesList.addEventFilter(MouseEvent.MOUSE_PRESSED, Event::consume);
    }

    /* Constructors End */

    /* Getters and Setters Start */

    public void setField(Field field) {
        this.field = field;
    }

    /* Getters and Setters End */

    /* Methods Start */


    /**
     * Returns to View School Data screen.
     * @param event - Click on 'Back' button.
     */
    @FXML
    public void BackToViewData(ActionEvent event) {
        m_coursesList.getItems().clear();
        m_teachersList.getItems().clear();
        Globals.mainContainer.setScreen(ClientGlobals.PrincipalViewDataID);
    }

    /**
     * Updates the ListView of Teachers by fetching all teachers in the field displayed from database through a query.
     */
    private void fillTeachersList(){

        ArrayList<Teacher> teachersList = field.getTeachersInField();
        ArrayList<String> basicTeacherInfo = new ArrayList<>();

        if (teachersList != null) {
            for (Teacher teacher : teachersList) {
                String teacherInfo = "TeacherID: " + teacher.getID() + " | " + teacher.getName();
                basicTeacherInfo.add(teacherInfo);
            }
        }
        m_teachersList.getItems().clear();
        ObservableList<String> list1 = FXCollections.observableArrayList(basicTeacherInfo);
        m_teachersList.setItems(list1);

    }

    /**
     * Updates the ListView of Courses by fetching all courses in the field displayed from database through a query.
     */
    private void fillCoursesList(){
        ArrayList<Course> coursesList = field.getCoursesInField();
        ArrayList<String> basicCourseInfo = new ArrayList<>();

        if (coursesList != null) {
            for (Course course : coursesList) {
                String teacherInfo = "CourseID: " + course.courseIdToString() + " | " + course.getName();
                basicCourseInfo.add(teacherInfo);
            }
        }
        m_coursesList.getItems().clear();
        ObservableList<String> list1 = FXCollections.observableArrayList(basicCourseInfo);
        m_coursesList.setItems(list1);
    }

    /* Methods End */
}
