package GUI;

import java.util.HashMap;

import Controllers.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import logic.Globals;
import logic.QuestionInExam;
import logic.SolvedExam;
import ocsf.client.ClientGlobals;

public class StudentViewExamFrame implements ControlledScreen {

	private SolvedExam solvedExam;
	@FXML Label courseName;
	@FXML Label grade;
	@FXML Label StudentInfo;
	@FXML VBox questionInfoAndAnswers;
	@FXML VBox scoreAndNotes;
	private HashMap<QuestionInExam, Integer> studentsAnswers;
	
	
	@Override
	public void runOnScreenChange() {
		// TODO Auto-generated method stub
		Globals.primaryStage.setHeight(630);
		Globals.primaryStage.setWidth(820);
		//questionInfoAndAnswers.getChildren().clear();
		//scoreAndNotes.getChildren().clear();
		courseName.setText(this.GetSolvedExam().getCourse().getName());
		grade.setText(Integer.toString(this.GetSolvedExam().getScore()));
		SetQuestionInfoAnswersScoreAndNoteForQuestion();
		
	}
	
	
	
	/**
	 * Put the question, points value and answers on Vbox questionInfoAndAnswers, 
	 * and put student's score with note (if exist) for each question on Vbox scoreAndNotes
	 */
	
	private void SetQuestionInfoAnswersScoreAndNoteForQuestion()
	{
		boolean studentAnswer;
		int questionIndex=1;
		int answerIndex=1;
		Label questionStringAndPointsValue = new Label();
		Label questionNote=new Label();
		Label studentGetsPointsFromQuestion = new Label();
		Label theRightAnswerIs=new Label();
		//Label scoreAndNotesCursorNextPosition=new Label("\n\n\n\n\n\n\n");
		RadioButton answers[];
		studentsAnswers=this.GetSolvedExam().getStudentsAnswers();
		for(QuestionInExam qie: studentsAnswers.keySet())
		{
			questionStringAndPointsValue.setText(Integer.toString(questionIndex)+". "+qie.getQuestionString()+" ("+Integer.toString(qie.getPointsValue())+"Points"+")" );
			answers=new RadioButton[] {new RadioButton(qie.getAnswer(1)),new RadioButton(qie.getAnswer(2)),new RadioButton(qie.getAnswer(3)),new RadioButton(qie.getAnswer(4))};
			questionInfoAndAnswers.getChildren().add((Label)questionStringAndPointsValue);
			for(RadioButton r:answers)
			{
				if(answerIndex==qie.getCorrectAnswerIndex())//Save the real correct answer so we can display it to student if he was wrong.
					theRightAnswerIs.setText(r.getText());
				if(answerIndex==studentsAnswers.get(qie))//Marked the student answer.
					r.setSelected(true);
				r.setDisable(true);
				r.setWrapText(true);
				questionInfoAndAnswers.getChildren().add(r);
				answerIndex++;
			}
			if(qie.getCorrectAnswerIndex()!=studentsAnswers.get(qie)) //Student answer's is not correct 
			{
				studentGetsPointsFromQuestion.setText("Score: "+"0");
				studentAnswer=false;
			}
			else//Student answer's is correct
			{
				studentGetsPointsFromQuestion.setText("Score: "+Integer.toString(qie.getPointsValue()));
				studentAnswer=true;
			}
			scoreAndNotes.getChildren().add(studentGetsPointsFromQuestion);
			if(studentAnswer=false)//If student answer is not correct that he gets a note about the real correct answer.
				scoreAndNotes.getChildren().add(theRightAnswerIs);
			//scoreAndNotes.getChildren().add(scoreAndNotesCursorNextPosition);
			
			//here we gonna do this line questionNote.setText 
			//here we gonna do this line scoreAndNotes.getChildren().add(questionNote)
			questionIndex++;
			answerIndex=1;
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
