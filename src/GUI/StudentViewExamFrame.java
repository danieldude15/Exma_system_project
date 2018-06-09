package GUI;

import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import Controllers.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import logic.Globals;
import logic.QuestionInExam;
import logic.SolvedExam;
import ocsf.client.ClientGlobals;

public class StudentViewExamFrame implements ControlledScreen {

	
	
	@FXML Label courseName;
	@FXML Label grade;
	@FXML Label StudentInfo;
	@FXML VBox questionInfo_StudentScoreAndNote;
	
	private SolvedExam solvedExam;
	private HashMap<QuestionInExam, Integer> studentsAnswers;
	private final Image v = new Image("resources/v.png"); 
	private final Image x = new Image("resources/x.png"); 
	private final String blackLabel=new String("blackLabel");
	
	
	@Override
	public void runOnScreenChange() {
		// TODO Auto-generated method stub
		Globals.primaryStage.setHeight(630);
		Globals.primaryStage.setWidth(820);
		questionInfo_StudentScoreAndNote.getChildren().clear();
		courseName.setText(this.GetSolvedExam().getCourse().getName());
		grade.setText(Integer.toString(this.GetSolvedExam().getScore()));
		if(this.GetSolvedExam().getScore()<55)
			grade.setTextFill(javafx.scene.paint.Paint.valueOf("#FF0000"));
		SetQuestionInfo_Answers_StudentScoreAndNoteForQuestion();
		
	}
	
	
	/*Do not delete, I keep it in case I will need it in the future.
	/**
	 * Put the question, points value and answers on Vbox questionInfoAndAnswers, 
	 * and put student's score with note (if exist) for each question on Vbox scoreAndNotes
	 */
	/*/
	private void SetQuestionInfo_Answers_StudentScoreAndNoteForQuestion()
	{
		boolean studentAnswer;
		int questionIndex=1;
		int answerIndex=1;
		Label questionStringAndPointsValue = new Label();
		Label questionNote=new Label();
		Label studentGetsPointsFromQuestion = new Label();
		Label theRightAnswerIs=new Label();
		
		//Build v icon in case that the student's answer is correct, or x icon otherwise. 
		Label v_x_Icon = new Label(null, new ImageView());
		ImageView imageView=new ImageView();
		imageView.setFitHeight(10);
		imageView.setFitWidth(10);
		
		//theRightAnswerIs.setId(BlackLabel);
		studentGetsPointsFromQuestion.setId(blackLabel);
		
		
		RadioButton answers[];
		studentsAnswers=this.GetSolvedExam().getStudentsAnswers();
		for(QuestionInExam qie: studentsAnswers.keySet())
		{

			questionStringAndPointsValue.setText(Integer.toString(questionIndex)+". "+qie.getQuestionString()+" ("+Integer.toString(qie.getPointsValue())+"Points"+")" );
			answers=new RadioButton[] {new RadioButton(qie.getAnswer(1)),new RadioButton(qie.getAnswer(2)),new RadioButton(qie.getAnswer(3)),new RadioButton(qie.getAnswer(4))};
			questionInfo_StudentScoreAndNote.getChildren().add((Label)questionStringAndPointsValue);

			for(RadioButton r:answers)
			{
				if(answerIndex==qie.getCorrectAnswerIndex())//Save the real correct answer so we can display it to student if he was wrong.
					theRightAnswerIs.setText(r.getText());
				if(answerIndex==studentsAnswers.get(qie))//Marked the student answer.
					r.setSelected(true);
				r.setDisable(true);
				r.setWrapText(true);
				questionInfo_StudentScoreAndNote.getChildren().add(r);
				answerIndex++;
			}
			
			if(qie.getCorrectAnswerIndex()!=studentsAnswers.get(qie)) //Student answer's is not correct 
			{
				imageView.setImage(x);
				v_x_Icon.setGraphic(imageView);	
				studentGetsPointsFromQuestion.setText(" Score: "+"0"+"/"+Integer.toString(qie.getPointsValue())+" points  ");
				studentAnswer=false;
			}
			else//Student answer's is correct
			{
			
				imageView.setImage(v);
				v_x_Icon.setGraphic(imageView);
				studentGetsPointsFromQuestion.setText(" Score: "+Integer.toString(qie.getPointsValue())+"/"+Integer.toString(qie.getPointsValue())+" points  ");
				studentAnswer=true;
			}
			
			questionInfo_StudentScoreAndNote.getChildren().add(SetStudentScorePointsAnd_X_V_IconOnScreenWindow(studentGetsPointsFromQuestion,v_x_Icon));
			if(studentAnswer=false)//If student answer is not correct that he gets a note about the real correct answer.
				questionInfo_StudentScoreAndNote.getChildren().add(theRightAnswerIs);
			
			//here we gonna do this line questionNote.setText 
			//here we gonna do this line questionInfo_StudentScoreAndNote.getChildren().add(questionNote)
			questionIndex++;
			answerIndex=1;
		}
	
	}
	
	public HBox SetStudentScorePointsAnd_X_V_IconOnScreenWindow(Label scorePoints,Label v_x_Icon)
	{
		HBox scorePointsAndv_x_Icon = new HBox();
		scorePointsAndv_x_Icon.getChildren().addAll(scorePoints,v_x_Icon);
		return scorePointsAndv_x_Icon;
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
		for(QuestionInExam qie: studentsAnswers.keySet())
		{
			theRightAnswerIs.setText(SetQuestionStringAndAnswersOnWindowScreen(qie,questionIndex));
			studentAnswer=SetScoreFromQuestionAndNoteOnWindowScreen(qie);
			
			if(studentAnswer=false)//If student answer is not correct that he gets a note about the real correct answer.
				questionInfo_StudentScoreAndNote.getChildren().add(theRightAnswerIs);
			
			//here we gonna do this line questionNote.setText 
			//here we gonna do this line questionInfo_StudentScoreAndNote.getChildren().add(questionNote)
			
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
		answers=new RadioButton[] {new RadioButton(qie.getAnswer(1)),new RadioButton(qie.getAnswer(2)),new RadioButton(qie.getAnswer(3)),new RadioButton(qie.getAnswer(4))};
		questionInfo_StudentScoreAndNote.getChildren().add(questionStringAndPointsValue);

		/*Set each of the 4 answers RadioButton of the question on window screen./*/
		for(RadioButton r:answers)
		{
			if(answerIndex==qie.getCorrectAnswerIndex())//Save the real correct answer so we can display it to student if he was wrong.
				theRightAnswerIs=r.getText();
			
			if(answerIndex==studentsAnswers.get(qie))//Marked the student answer, and set the icon to v or x.
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
		
		if(qie.getCorrectAnswerIndex()!=studentsAnswers.get(qie)) //Student answer's is not correct 
		{
			studentGetsPointsFromQuestion.setText(" Score: "+"0"+"/"+Integer.toString(qie.getPointsValue())+" points  ");
			studentAnswer=false;
		}
		else//Student answer's is correct
		{
			studentGetsPointsFromQuestion.setText(" Score: "+Integer.toString(qie.getPointsValue())+"/"+Integer.toString(qie.getPointsValue())+" points  ");
			studentAnswer=true;
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
	
	/**
	 * Go back to student main window
	 * @param event
	 */
	public void StudentPressedBackButton(ActionEvent event)
	{
		Globals.mainContainer.setScreen(ClientGlobals.StudentMainID);
	}
}
