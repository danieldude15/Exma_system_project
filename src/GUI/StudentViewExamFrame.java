package GUI;

import Controllers.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import logic.Globals;
import logic.QuestionInExam;
import logic.SolvedExam;
import ocsf.client.ClientGlobals;

import java.util.HashMap;

public class StudentViewExamFrame implements ControlledScreen {

	
	
	@FXML Label courseName;
	@FXML Label grade;
	@FXML Label StudentInfo;
	@FXML Label changeScoreByTeacherNote;
	@FXML Button watchExplanationButton;
	@FXML VBox questionInfo_StudentScoreAndNote;
	
	private SolvedExam solvedExam;
	private HashMap<QuestionInExam, Integer> studentsAnswers;
	private final Image v = new Image("resources/v.png"); 
	private final Image x = new Image("resources/x.png"); 
	private final String blackLabel=new String("blackLabel");
	private final String redLabel=new String("redLabel");
	private final String greenLabel=new String("greenLabel");
	
	
	@Override
	public void runOnScreenChange() {
		questionInfo_StudentScoreAndNote.getChildren().clear();
		courseName.setText(this.GetSolvedExam().getCourse().getName());
		grade.setText(Integer.toString(this.GetSolvedExam().getScore()));
		if(this.GetSolvedExam().getScore()<55)
			grade.setTextFill(javafx.scene.paint.Paint.valueOf("#FF0000"));
		
		if((this.GetSolvedExam().getTeachersScoreChangeNote().isEmpty()))//Explanation if the teacher changed the score.
		{
			changeScoreByTeacherNote.setVisible(false);
			watchExplanationButton.setVisible(false);
		}	
	
		
		SetQuestionInfo_Answers_StudentScoreAndNoteForQuestion();
		
	}
	
		

	
	/*/
	/**
	 * Set the info of the question on the window screen.
	 */
	private void SetQuestionInfo_Answers_StudentScoreAndNoteForQuestion()
	{
		
		int questionIndex=1;
		Label theRightAnswerIs=new Label();
		theRightAnswerIs.setId(blackLabel);
		boolean studentAnswer;

		
		studentsAnswers=this.GetSolvedExam().getStudentsAnswers();
		
		/*Set each question and it's data on window screen/*/
		for(QuestionInExam qie: this.GetSolvedExam().getQuestionsInExam())
		{
			theRightAnswerIs.setText(SetQuestionStringAndAnswersOnWindowScreen(qie,questionIndex));
			studentAnswer=SetScoreFromQuestionAndNoteOnWindowScreen(qie);
			
			if(studentAnswer=false)//If student answer is not correct that he gets a note about the real correct answer.
				questionInfo_StudentScoreAndNote.getChildren().add(theRightAnswerIs);
			
			
			questionIndex++;
		}
	}
	
/**
 * Set the question string with it's answers on window screen, 
 * and in addition set v icon next to the student answer if he was right or x icon otherwise.
 * return the right answer of the question.
 * @param qie QuestionInExam
 * @param questionIndex int
 * @return String
 */
	private String SetQuestionStringAndAnswersOnWindowScreen(QuestionInExam qie, int questionIndex) {
		// TODO Auto-generated method stub
		int answerIndex=1;
		boolean visitOnStudentAnswer=false;
		Label questionStringAndPointsValue = new Label();
		Label questionNote=new Label();
		questionStringAndPointsValue.setId(blackLabel);
		String theRightAnswerIs=new String();
		
		
		RadioButton answers[];
		HBox answerAndv_xIcon=new HBox(); //here we gonna put the answer RadioButton together with v or x icon.
		answerAndv_xIcon.setSpacing(20);
		
		//Build v icon in case that the student's answer is correct, or x icon otherwise. 
		Label v_x_Icon = new Label(null, new ImageView());
		ImageView imageView=new ImageView();
		imageView.setFitHeight(10);
		imageView.setFitWidth(10);
				
		if(qie.getStudentNote()!=null)//If question contains note for student, we add it at the top of the question.
		{
			questionNote.setText("Note: "+qie.getStudentNote());
			questionInfo_StudentScoreAndNote.getChildren().add(questionNote);
		}
		questionStringAndPointsValue.setText(Integer.toString(questionIndex)+". "+qie.getQuestionString()+" ("+Integer.toString(qie.getPointsValue())+" Points"+")" );
		answers=new RadioButton[] {new RadioButton((char)(answerIndex+96)+". "+qie.getAnswer(1)),new RadioButton((char)(answerIndex+97)+". "+qie.getAnswer(2)),new RadioButton((char)(answerIndex+98)+". "+qie.getAnswer(3)),new RadioButton((char)(answerIndex+99)+". "+qie.getAnswer(4))};
		questionInfo_StudentScoreAndNote.getChildren().add(questionStringAndPointsValue);

		/*Set each of the 4 answers RadioButton of the question on window screen./*/
		for(RadioButton r:answers)
		{
			if(answerIndex==qie.getCorrectAnswerIndex())//Save the real correct answer so we can display it to student if he was wrong.
				theRightAnswerIs=r.getText();
			
			if(studentsAnswers.get(qie)!=null && answerIndex==studentsAnswers.get(qie))//Marked the student answer, and set the icon to v or x.
			{
				r.setSelected(true);
				if(answerIndex==qie.getCorrectAnswerIndex())
				{
					imageView.setImage(v);
				}
				else
					imageView.setImage(x);
				
				v_x_Icon.setGraphic(imageView);
				visitOnStudentAnswer=true;
			}
			
			r.setDisable(true);
			r.setWrapText(true);
			r.setId(blackLabel);
			if(visitOnStudentAnswer==true)//Set on screen answer RadioButton with v_x icon.
			{
				answerAndv_xIcon.getChildren().addAll(r,v_x_Icon);
				questionInfo_StudentScoreAndNote.getChildren().add(answerAndv_xIcon);
			}
			else//Set on screen answer RadioButton.
				questionInfo_StudentScoreAndNote.getChildren().add(r);
			
			visitOnStudentAnswer=false;
			answerIndex++;
		}
		
		return theRightAnswerIs;
	}

	
	
	/**
	 * Set the student score from question, and return true if student was right, otherwise return false.
     * @param qie QuestionInExam
	 * @return boolean 
	 */
	private boolean SetScoreFromQuestionAndNoteOnWindowScreen(QuestionInExam qie)
	{
		boolean studentAnswer;
		Label studentGetsPointsFromQuestion=new Label();
		studentGetsPointsFromQuestion.setId(blackLabel);
		Label answerNote=new Label();
		answerNote.setId(blackLabel);
		Separator separator=new Separator();
		separator.setMinWidth(690);
		separator.setMaxHeight(100);
		
		if(studentsAnswers.get(qie)!=null)//Student answer on that question
		{
			if(qie.getCorrectAnswerIndex()!=studentsAnswers.get(qie)) //Student answer's is not correct 
			{
				//studentGetsPointsFromQuestion.setId(redLabel);
				studentGetsPointsFromQuestion.setId(redLabel);
				studentGetsPointsFromQuestion.setText(" Score: "+"0"+"/"+Integer.toString(qie.getPointsValue())+" points  ");
				studentAnswer=false;
			}
			else//Student answer's is correct
			{
				studentGetsPointsFromQuestion.setText(" Score: "+Integer.toString(qie.getPointsValue())+"/"+Integer.toString(qie.getPointsValue())+" points  ");
				studentAnswer=true;
			}
		}
		
		else//Student didn't answer on that question
		{
			studentGetsPointsFromQuestion.setId(redLabel);
			studentGetsPointsFromQuestion.setText(" Score: "+"0"+"/"+Integer.toString(qie.getPointsValue())+" points  "+"\nYou didn't answer for that question!");
			studentAnswer=false;
			
		}
		
		if(this.GetSolvedExam().getQuestionNoteOnHash().get(qie)!=null)//There is a note for that answer.
			answerNote.setText("Note: "+this.GetSolvedExam().getQuestionNoteOnHash().get(qie));
		
		if(answerNote.getText()==null)
			questionInfo_StudentScoreAndNote.getChildren().addAll(studentGetsPointsFromQuestion,separator);
		else
			questionInfo_StudentScoreAndNote.getChildren().addAll(studentGetsPointsFromQuestion,answerNote,separator);
		
		return studentAnswer;
	}
	
	

	
	
	

	/**
	 * Set Student's chosen solved exam for window View Exam.
	 * @param s SolvedExam  
	 */
	public void SetSolvedExam(SolvedExam s)
	{
		this.solvedExam=s;
	}
	/**
	 * Get Student's chosen solved Exam from main window.
	 * @return SolvedExam
	 */
	public SolvedExam GetSolvedExam()
	{
		return this.solvedExam;
	}
	
	public void WatchScoreChangeExplanationByTheTeacher(ActionEvent event)
	{
		//changeScoreByTeacherNote.setVisible(true);
		//watchExplanationButton.setVisible(true);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Teacher's explenation for grade change");
		alert.setHeaderText(null);
		alert.setContentText(this.GetSolvedExam().getTeachersScoreChangeNote());
		alert.showAndWait();
	}
	
	/**
	 * Go back to student main window
	 * @param event
	 */
	public void StudentPressedBackButton(ActionEvent event)
	{
		Globals.mainContainer.setScreen(ClientGlobals.StudentMainID);
	}
}
