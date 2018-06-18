package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import Controllers.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.Exam;
import logic.Globals;
import logic.QuestionInExam;
import ocsf.client.ClientGlobals;


public class TeacherViewExam implements Initializable, ControlledScreen {

	@FXML VBox questionInfo_StudentScoreAndNote;
	@FXML Label examinfo;
	@FXML Label feildinfo;
	@FXML Label courseinfo;
	@FXML Label durationinfo;
	@FXML Button active;
	@FXML Button Back;
	Exam examview=null;	
	@Override
	public void runOnScreenChange() {
		questionInfo_StudentScoreAndNote.getChildren().clear();
		examinfo.setText("Exam id: "+examview.examIdToString());
		feildinfo.setText("Feild id: "+examview.getField().fieldIdToString());
		courseinfo.setText("Course id: "+examview.getCourse().courseIdToString());
		durationinfo.setText("Duration: "+String.valueOf(examview.getDuration()));
		int questionIndex=0;
		for(QuestionInExam qie: examview.getQuestionsInExam() )
		{
			questionIndex++;
			SetQuestionStringAndAnswersOnWindowScreen(qie , questionIndex);
		}
	}
	
	private void SetQuestionStringAndAnswersOnWindowScreen(QuestionInExam qie ,int questionIndex) {
		// TODO Auto-generated method stub
		int answerIndex=1;
		
		Label questionStringAndPointsValue = new Label();
		questionStringAndPointsValue.setId("blackLabel");
		RadioButton answers[];
		HBox answerAndv_xIcon=new HBox(); //here we gonna put the answer RadioButton together with v or x icon.
		answerAndv_xIcon.setSpacing(20);
		
		questionStringAndPointsValue.setText(Integer.toString(questionIndex)+". "+qie.getQuestionString()+" ("+Integer.toString(qie.getPointsValue())+" Points"+")" );
		answers=new RadioButton[] {new RadioButton(qie.getAnswer(1)),new RadioButton(qie.getAnswer(2)),new RadioButton(qie.getAnswer(3)),new RadioButton(qie.getAnswer(4))};
		questionInfo_StudentScoreAndNote.getChildren().add(questionStringAndPointsValue);


		if(qie.getInnerNote() != null && !qie.getInnerNote().isEmpty())//If question contains note for student, we add it at the top of the question.
		{
			Label questionNoteforstudent=new Label();
			questionNoteforstudent.setText("Note for student: "+qie.getStudentNote());
			questionNoteforstudent.setId("blackLabel");
			questionInfo_StudentScoreAndNote.getChildren().add(questionNoteforstudent);
		}
		if(qie.getStudentNote() != null && !qie.getStudentNote().isEmpty())//If question contains note for teacher, we add it at the top of the question.
		{
			Label questionNoteforteacher=new Label();
			questionNoteforteacher.setText("Note for teacher: "+qie.getStudentNote());
			questionNoteforteacher.setId("blackLabel");
			questionInfo_StudentScoreAndNote.getChildren().add(questionNoteforteacher);
		}
		
		/*Set each of the 4 answers RadioButton of the question on window screen./*/
		for(RadioButton r:answers)
		{
			if(answerIndex==qie.getCorrectAnswerIndex())//Save the real correct answer so we can display it to student if he was wrong.
				r.setSelected(true);
			
			answerIndex++;
			r.setDisable(true);
			r.setWrapText(true);
			r.setId("blackLabel");
            questionInfo_StudentScoreAndNote.getChildren().add(r);
			
            
		
		}

	}

	public void setExam(Exam e) {
		examview=e;
		
	}
	@FXML
    public void MyActiveHandler(ActionEvent event) {
              ((TeacherActivateExamFrame)Globals.mainContainer.getController(ClientGlobals.ActiveExamID)).setExam(examview);
               Globals.mainContainer.setScreen(ClientGlobals.ActiveExamID);
        	}
	@FXML
    public void BackButtonPresse(ActionEvent event)
		    {
		     Globals.mainContainer.setScreen(ClientGlobals.InitializeExamID);
		    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
