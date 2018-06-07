package GUI;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import com.mysql.jdbc.Field;

import Controllers.ActiveExamController;
import Controllers.ControlledScreen;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.ActiveExam;
import logic.Course;
import logic.Exam;
import logic.Globals;
import logic.QuestionInExam;
import logic.SolvedExam;
import logic.Student;
import logic.Teacher;
import ocsf.client.ClientGlobals;
import ocsf.server.ServerGlobals;

public class StudentSolvesExamFrame implements ControlledScreen{


	@FXML VBox questionsAndAnswers;
	@FXML Label courseNameLabel;
	@FXML Label timeLeftLabel;
	@FXML Label teacherNameLabel;
	@FXML Button downloadButton;
	@FXML Button submitButton;
	@FXML Button exitButton;
	
	private HashMap<QuestionInExam,ToggleGroup> questionWithAnswers=new HashMap<QuestionInExam,ToggleGroup>();//So we can get the student answer on question.
	private HashMap<String,Integer> indexOfAnswerInQuestion=new  HashMap<String,Integer>();//So we can mark the index of student answer.
	private ActiveExam activeExam;
	private final String whiteLabel=new String("whiteLabel");
	private final String blackLabel=new String("blackLabel");
	
	
	@Override
	public void runOnScreenChange() {
		// TODO Auto-generated method stub
		Globals.primaryStage.setHeight(630);
		Globals.primaryStage.setWidth(820);
		questionsAndAnswers.getChildren().clear();
		questionWithAnswers.clear();
		indexOfAnswerInQuestion.clear();
		
		activeExam=this.GetActiveExam();
		courseNameLabel.setId(whiteLabel);
		teacherNameLabel.setId(whiteLabel);
		courseNameLabel.setText(activeExam.getExam().getCourse().getName());
		teacherNameLabel.setText(activeExam.getExam().getAuthor().getName());
		downloadButton.setVisible(false);//This button will show on screen only if the exam is manual.
		//ActiveExamController.StudentCheckedInToActiveExam((Student)ClientGlobals.client.getUser(),activeExam);//Student check in to the exam.
		
		//Active exam is manual.
		if(activeExam.getType()==0)
		{
			ManualExam(activeExam);
			/*Here:
			1.Create word file
			2.Download word file
			3.Initialize the timer!
			4.Submit method(check if exam is not locked yet).
			5.Remove from active exam HashMap on server.
			6.Build SolvedExam object.
			7.Upload to database SolvedExam(word file?).
			 /*/
		}
		//Active exam is computerized.
		else
		{
			SetComputerizeExamOnWindowScreen(activeExam);
			/*Here:
			1.Create exam on screen with the questions(Including initialize the timer).
			2.Submit method(check if exam is not locked yet).
			3.Remove from active exam HashMap on server.
			4.Build SolvedExam object.
			5.Upload to database SolvedExam.
			/*/
		}
	}

	public void SetActiveExam(ActiveExam activeE) {
		// TODO Auto-generated method stub
		this.activeExam=activeE;
		
	}

	public ActiveExam GetActiveExam()
	{
		return this.activeExam;
	}


	/**
	 * Sets the computerized exam on window screen.
	 * @param active
	 */
	private void SetComputerizeExamOnWindowScreen(ActiveExam active) {
		// TODO Auto-generated method stub
		int questionIndex=1;
		int aIndex=1;
		Label note=new Label();
		note.setId(blackLabel);
		Label questionString=new Label();
		questionString.setId(blackLabel);
		RadioButton answers[];
		
		ToggleGroup toogleGroup = new ToggleGroup();//Group all answers in ToggleGroup so the student can choose only one option.
		ArrayList<QuestionInExam> questionsInExam=active.getExam().getQuestionsInExam();
		for(QuestionInExam qie:questionsInExam)//Sets all questions with their info on screen.
		{
			if(qie.getStudentNote()!=null)//If there is a student note on this question we add it to the top of the question.
			{
				note.setText(qie.getStudentNote());
				questionsAndAnswers.getChildren().add(note);
			}
			questionString.setText(Integer.toString(questionIndex)+". "+qie.getQuestionString()+" ("+Integer.toString(qie.getPointsValue())+" Points"+")" );
			questionsAndAnswers.getChildren().add(questionString);
			answers=new RadioButton[] {new RadioButton(qie.getAnswer(1)),new RadioButton(qie.getAnswer(2)),new RadioButton(qie.getAnswer(3)),new RadioButton(qie.getAnswer(4))};
			for(RadioButton r:answers)//Sets all answers of the question on window screen.
			{
				r.setWrapText(true);
				r.setToggleGroup(toogleGroup);
				questionsAndAnswers.getChildren().add(r);
				indexOfAnswerInQuestion.put(r.getText(), aIndex);//Add answer and it's index to HashMap(will use us in SubmitButtonPressed method to convert student's answer String to Integer index).
				aIndex++;
			}
			questionWithAnswers.put(qie, toogleGroup);//Add question and it's group of answers to HashMap(will use us in SubmitButtonPressed method to check which answer the user marked for that question).
		
			toogleGroup.getToggles().clear();//Clear toogleGroup for the next question answers.
				
			
			aIndex=1;
			questionIndex++;
		}
		
	}

	private void ManualExam(ActiveExam active) {
		// TODO Auto-generated method stub
		
	}



	
	public void StudentPressedDownloadButton(ActionEvent event)
	{
		
	}
	
	
	public void StudentPressedSubmitButton(ActionEvent event)
	{	
			//if(exam is locked)
			//{
				//1.popup
				//2.go to student main window
			//}
		//else exam is not locked
 		//{
		
		/*Build solved Exam object/*/
			//int score=0;
			Exam e=new Exam(activeExam.getExam());
			int examid=e.getID();
			Course course=new Course(e.getCourse());
			int duration=e.getDuration();
			Teacher teacher=e.getAuthor();
			
			
			
			Object[] studentAnsersAndScoreForExam=SystemCheckExam();
			HashMap<QuestionInExam,Integer> studentAnswers=(HashMap<QuestionInExam, Integer>) studentAnsersAndScoreForExam[0];
			int score=(int) studentAnsersAndScoreForExam[1];
			
			boolean teacherApproved=false;
			int examReportId=5;//need to take care of it.
			Student examSolver=new Student((Student)ClientGlobals.client.getUser());
			String teachersScoreChangeNote=null;
			int CompletedTimeInMinutes=0;//need to take care of it with the timer.
			SolvedExam uploadToDatabase=new SolvedExam(e.getID(), course, e.getDuration(), teacher
					, score, teacherApproved, studentAnswers, examReportId,
					examSolver, teachersScoreChangeNote, CompletedTimeInMinutes);
			
			
			
			//Confirmation Dialog
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setHeaderText("SubmitExam");
			alert.setContentText("Are you sure you want to submit?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK)//User choose ok
			{
				//ActiveExamController.StudentCheckedOutFromActiveExam((Student)ClientGlobals.client.getUser(),activeExam);//Student check out from exam.

			}
			
			
			
			/*//delete it all!
			System.out.println("score= "+score);
			System.out.println("studentAnswersSIZE="+studentAnswers.size());
			
			for (QuestionInExam qie : studentAnswers.keySet()) 
			{
				System.out.println("answer="+studentAnswers.get(qie));
			}
			//delete it all!/*/
		//}
	}
	
	
	
	/**
	 * Run all over student's answers for each question in the exam and return an Object[].
	 * Object[0]=HashMap(QuestionInExam,Integer) which contains the question in exam as key and the student's answer index as value. 
	 * Object[1]=Student's grade for exam.
	 * @return Object[] studentAnsersAndScoreForExam
	 */
	private Object[] SystemCheckExam() {
		// TODO Auto-generated method stub
		int score=0;
		Object[] studentAnsersAndScoreForExam=new Object[2];
		HashMap<QuestionInExam,Integer> studentAnswers=new HashMap<QuestionInExam,Integer>();
		RadioButton r=new RadioButton();
		for (QuestionInExam qie : questionWithAnswers.keySet()) 
		{
			if(questionWithAnswers.get(qie).getSelectedToggle()!=null)
			{
				r=(RadioButton) questionWithAnswers.get(qie).getSelectedToggle();
				studentAnswers.put(qie,indexOfAnswerInQuestion.get(r.getText()));//Insert the question and student's index of answer to HashMap.
				if(studentAnswers.get(qie)==qie.getCorrectAnswerIndex())//If student's answer for that question is correct he gets all points from it.
					score+=qie.getPointsValue();
			}
			else//If student's answer for that question is not correct he gets 0 points from it.
				studentAnswers.put(qie,0);
		}
		
		studentAnsersAndScoreForExam[0]=studentAnswers;
		studentAnsersAndScoreForExam[1]=score;
		return studentAnsersAndScoreForExam;
	}

	
	
	public void StudentPressedExitButton(ActionEvent event)
	{
		Globals.mainContainer.setScreen(ClientGlobals.StudentMainID);
	}
	
}
