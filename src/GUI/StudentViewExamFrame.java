package GUI;

import java.util.ArrayList;
import java.util.HashMap;

import Controllers.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.Globals;
import logic.QuestionInExam;
import logic.SolvedExam;
import ocsf.client.ClientGlobals;

public class StudentViewExamFrame implements ControlledScreen {

	private SolvedExam solvedExam;
	@FXML Label courseName;
	@FXML Label grade;
	@FXML VBox questionInfoAndAnswers;
	@FXML VBox scoreAndNotes;
	private HashMap<QuestionInExam, Integer> studentsAnswers;
	
	@Override
	public void runOnScreenChange() {
		// TODO Auto-generated method stub
		Globals.primaryStage.setHeight(630);
		Globals.primaryStage.setWidth(820);	
		courseName.setText(this.GetSolvedExam().getCourse().getName());
		grade.setText(Integer.toString(this.GetSolvedExam().getScore()));
		
		questionInfoAndAnswers=new VBox();
		scoreAndNotes=new VBox();
		studentsAnswers=new HashMap<QuestionInExam,Integer>();
		SetQuestionInfoAnswersScoreAndNoteForQuestion();
		
	}
	
	
	
	/**
	 * Put the question, points value and answers on Vbox questionInfoAndAnswers, 
	 * and put student's score with note (if exist) for each question on Vbox scoreAndNotes
	 */
	public void SetQuestionInfoAnswersScoreAndNoteForQuestion()
	{
		Label questionStringAndPointsValue = new Label();
		Label questionNote=new Label();
		Label studentGetsPointsFromQuestion = new Label();
		RadioButton answers[];
		studentsAnswers=this.GetSolvedExam().getStudentsAnswers();
		for(QuestionInExam qie: studentsAnswers.keySet())
		{
			questionStringAndPointsValue.setText(qie.getQuestionString()+" ("+Integer.toString(qie.getPointsValue())+"Points"+")" );
			answers=new RadioButton[] {new RadioButton(qie.getAnswer(0)),new RadioButton(qie.getAnswer(1)),new RadioButton(qie.getAnswer(2)),new RadioButton(qie.getAnswer(3))};
			questionInfoAndAnswers.getChildren().add((Label)questionStringAndPointsValue);
			for(RadioButton r:answers)
			{
				r.setDisable(true);
				r.setWrapText(true);
				questionInfoAndAnswers.getChildren().add(r);
				
			}
			if(qie.getCorrectAnswerIndex()!=studentsAnswers.get(qie)) //Student answer's is not correct 
			{
				studentGetsPointsFromQuestion.setText("Score: "+"0");
			}
			else//Student answer's is correct
			{
				studentGetsPointsFromQuestion.setText("Score: "+Integer.toString(qie.getPointsValue()));
			}
			scoreAndNotes.getChildren().add(studentGetsPointsFromQuestion);
			//here we gonna do this line questionNote.setText 
			//here we gonna do this line scoreAndNotes.getChildren().add(questionNote
			
		}
	
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
	
	
	public void StudentPressedBackButton(ActionEvent event)
	{
		Globals.mainContainer.setScreen(ClientGlobals.StudentMainID);
	}
}
