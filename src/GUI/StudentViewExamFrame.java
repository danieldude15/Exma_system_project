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
	private final String BlackLabel=new String("blackLabel");
	
	
	@Override
	public void runOnScreenChange() {
		// TODO Auto-generated method stub
		Globals.primaryStage.setHeight(630);
		Globals.primaryStage.setWidth(820);
		//questionInfo_StudentScoreAndNote.getChildren().clear();
		courseName.setText(this.GetSolvedExam().getCourse().getName());
		grade.setText(Integer.toString(this.GetSolvedExam().getScore()));
		if(this.GetSolvedExam().getScore()<55)
			grade.setTextFill(javafx.scene.paint.Paint.valueOf("#FF0000"));
		SetQuestionInfo_Answers_StudentScoreAndNoteForQuestion();
		
	}
	
	
	
	/**
	 * Put the question, points value and answers on Vbox questionInfoAndAnswers, 
	 * and put student's score with note (if exist) for each question on Vbox scoreAndNotes
	 */
	
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
			
			questionInfo_StudentScoreAndNote.getChildren().add(GetStudentScorePointsAnd_X_V_Icon(studentGetsPointsFromQuestion,v_x_Icon));
			//if(studentAnswer=false)//If student answer is not correct that he gets a note about the real correct answer.
				questionInfo_StudentScoreAndNote.getChildren().add(theRightAnswerIs);
			
			//here we gonna do this line questionNote.setText 
			//here we gonna do this line questionInfo_StudentScoreAndNote.getChildren().add(questionNote)
			questionIndex++;
			answerIndex=1;
		}
	
	}
	
	public HBox GetStudentScorePointsAnd_X_V_Icon(Label scorePoints,Label v_x_Icon)
	{
		HBox scorePointsAndv_x_Icon = new HBox();
		scorePointsAndv_x_Icon.getChildren().addAll(scorePoints,v_x_Icon);
		return scorePointsAndv_x_Icon;
	}
		
	/**
	 * Set Student's chosen solved exam for window View Exam.
	 * @param s SolvedExam  
	 */

	
	public HBox GetStudentScorePointsAnd_X_V_Icon(Label scorePoints,Pane v_x_Icon)
	{
		HBox scorePointsAndv_x_Icon = new HBox();
		scorePointsAndv_x_Icon.getChildren().addAll(scorePoints,v_x_Icon);
		return scorePointsAndv_x_Icon;
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
