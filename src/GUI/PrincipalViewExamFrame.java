package GUI;

import Controllers.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.Exam;
import logic.Globals;
import logic.QuestionInExam;
import ocsf.client.ClientGlobals;

import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalViewExamFrame implements Initializable, ControlledScreen {
	enum user {
		Teacher,Principle
	}

    @FXML private Label m_examIDlbl;
    @FXML private Label m_courseNamelbl;
    @FXML private Label m_fieldNamelbl;
    @FXML private Label m_authorNamelbl;
    @FXML private Label m_examDurationlbl;
    @FXML private VBox questionInfo_StudentScoreAndNote;

    private Exam exam;

    @Override
    public void runOnScreenChange() {

        if(exam != null){
            m_examIDlbl.setText("ExamID: " + exam.examIdToString());
            m_courseNamelbl.setText("Course: " + exam.getCourse().getName());
            m_fieldNamelbl.setText("Field: " + exam.getField().getName());
            m_authorNamelbl.setText("Author: " + exam.getAuthor().getName());
            m_examDurationlbl.setText("Duration: " + exam.getDuration() + " Minutes");

            int questionIndex = 0;
            for(QuestionInExam qie: exam.getQuestionsInExam() )
            {
                SetQuestionStringAndAnswersOnWindowScreen(qie , ++questionIndex);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setExam(Exam m_examToBeDisplayed) {
        exam = m_examToBeDisplayed;
    }

    @FXML
    public void BackToViewData(ActionEvent event) {
        questionInfo_StudentScoreAndNote.getChildren().clear();
        Globals.mainContainer.setScreen(ClientGlobals.PrincipalViewDataID);
    }

    private void SetQuestionStringAndAnswersOnWindowScreen(QuestionInExam qie , int questionIndex) {

        int answerIndex = 1;

        Label questionStringAndPointsValue = new Label();
        questionStringAndPointsValue.setId("blackLabel");
        questionStringAndPointsValue.setPadding(new Insets(5,0,0,5));
        RadioButton answers[];
        HBox answerAndv_xIcon = new HBox(); //here we gonna put the answer RadioButton together with v or x icon.
        answerAndv_xIcon.setSpacing(20);

        questionStringAndPointsValue.setText(Integer.toString(questionIndex)+". "+qie.getQuestionString()+" ("+Integer.toString(qie.getPointsValue())+" Points"+")" );
        answers=new RadioButton[] {new RadioButton(qie.getAnswer(1)),new RadioButton(qie.getAnswer(2)),new RadioButton(qie.getAnswer(3)),new RadioButton(qie.getAnswer(4))};
        questionInfo_StudentScoreAndNote.getChildren().add(questionStringAndPointsValue);

        /*Set each of the 4 answers RadioButton of the question on window screen./*/
        for(RadioButton r:answers)
        {
            if(answerIndex==qie.getCorrectAnswerIndex())//Save the real correct answer so we can display it to student if he was wrong.
                r.setSelected(true);

            answerIndex++;
            r.setDisable(true);
            r.setWrapText(true);
            r.setId("blackLabel");
            r.setPadding(new Insets(0,0,0,2));
            questionInfo_StudentScoreAndNote.getChildren().add(r);
        }
        if(qie.getStudentNote() != null && !qie.getStudentNote().isEmpty())//If question contains note for student, we add it at the top of the question.
        {
            Label questionNoteforstudent = new Label();
            questionNoteforstudent.setText("Note for student: " + qie.getStudentNote());
            questionNoteforstudent.setId("blackLabel");
            questionNoteforstudent.setPadding(new Insets(5,5,5,5));
            questionInfo_StudentScoreAndNote.getChildren().add(questionNoteforstudent);
        }
        if(qie.getInnerNote() != null && !qie.getInnerNote().isEmpty())//If question contains note for teacher, we add it at the top of the question.
        {
            Label questionNoteforteacher = new Label();
            questionNoteforteacher.setText("Note for teacher: "+qie.getInnerNote());
            questionNoteforteacher.setId("blackLabel");
            questionNoteforteacher.setPadding(new Insets(5,5,5,5));
            questionInfo_StudentScoreAndNote.getChildren().add(questionNoteforteacher);
        }

    }
}
