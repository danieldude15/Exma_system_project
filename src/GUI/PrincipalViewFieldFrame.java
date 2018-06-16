package GUI;

import Controllers.ControlledScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import logic.Course;
import logic.Field;
import logic.Globals;
import logic.Teacher;
import ocsf.client.ClientGlobals;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PrincipalViewFieldFrame implements Initializable, ControlledScreen {


    @FXML private Label m_fieldIDlbl;
    @FXML private Label m_fieldNamelbl;
    @FXML private ListView<String> m_coursesList;
    @FXML private ListView<String> m_teachersList;

    private Field field;

    @Override
    public void runOnScreenChange() {

        if(field != null){
            m_fieldIDlbl.setText("FieldID: " + field.fieldIdToString());
            m_fieldNamelbl.setText("Name: " + field.getName());
            fillTeachersList();
            fillCoursesList();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // making list views unclickable
        m_coursesList.setMouseTransparent(true);
        m_coursesList.setFocusTraversable(false);
        m_teachersList.setMouseTransparent(true);
        m_teachersList.setFocusTraversable(false);
    }

    public void setField(Field field) {
        this.field = field;
    }

    @FXML
    public void BackToViewData(ActionEvent event) {
        m_coursesList.getItems().clear();
        m_teachersList.getItems().clear();
        Globals.mainContainer.setScreen(ClientGlobals.PrincipalViewDataID);
    }

    private void fillTeachersList(){

        ArrayList<Teacher> teachersList = field.getTeachersInField();;
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
}
