package GUI;


import Controllers.ActiveExamController;
import Controllers.ControlledScreen;
import Controllers.SolvedExamController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import logic.*;
import ocsf.client.ClientGlobals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import javax.swing.filechooser.FileSystemView;

@SuppressWarnings("unchecked")
public class StudentSolvesExamFrame implements ControlledScreen{


	@FXML VBox questionsAndAnswers;
	@FXML Label courseNameLabel;
	@FXML Label timeLeftLabel;
	@FXML Label teacherNameLabel;
	@FXML Button downloadButton;
	@FXML Button submitButton;
	@FXML Button exitButton;
	String timeLeft = "";
	private Long timeSeconds;
	private HashMap<QuestionInExam,ToggleGroup> questionWithAnswers=new HashMap<QuestionInExam,ToggleGroup>();//So we can get the student answer on question.
	private ActiveExam activeExam;
	boolean activeExamIsLocked=false; 
	private final String whiteLabel=new String("whiteLabel");
	private final String blackLabel=new String("blackLabel");
	
	private final Image v = new Image("resources/GoodLuck.jpg");
    private final Background yellowBackground = new Background( new BackgroundFill( Color.web( "#95ab35" ), CornerRadii.EMPTY, Insets.EMPTY ) );
	
	@Override public void runOnScreenChange() {		
		
		questionsAndAnswers.getChildren().clear();
		questionWithAnswers.clear();
		
		activeExam=GetActiveExam();
		courseNameLabel.setId(whiteLabel);
		teacherNameLabel.setId(whiteLabel);
		courseNameLabel.setText(activeExam.getExam().getCourse().getName());
		teacherNameLabel.setText(activeExam.getExam().getAuthor().getName());
		downloadButton.setVisible(false);//This button will show on screen only if the exam is manual.
		
		//Active exam is manual.
		if(activeExam.getType()==0)
			SetDownloadButtonOnScreen();
		
		//Active exam is computerized.
		else
			SetComputerizeExamOnWindowScreen(activeExam);		
		
	}

	/**
	 * Set text on time label(set new time).
	 * @param event
	 */
	@FXML public void refreshTimer(MouseEvent event) {
		timeLeftLabel.setText(timeLeft);
	}
	
	/**
	 * Active exam setter.
	 * @param activeE
	 */
	public void SetActiveExam(ActiveExam activeE) {
		// TODO Auto-generated method stub
		this.activeExam=activeE;
		
	}

	/**
	 * Active exam getter.
	 * @return
	 */
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
		ArrayList<QuestionInExam> questionsInExam=active.getQuestionsInExam();
		for(QuestionInExam qie:questionsInExam)//Sets all questions with their info on screen.
		{			
			SetQuestionOnWindowScreen(qie,questionIndex);
			questionIndex++;
		}
	}

	/**
	 * Get QuestionInExam and it's index and set it on screen.
	 * @param qie
	 * @param questionIndex
	 */
	private void SetQuestionOnWindowScreen(QuestionInExam qie,int questionIndex)
	{
		ToggleGroup toogleGroup = new ToggleGroup();//Group all answers in ToggleGroup so the student can choose only one option.
		Label note=new Label();
		note.setId(blackLabel);
		Label questionString=new Label();
		questionString.setId(blackLabel);
		RadioButton answers[];
		
		
		if(qie.getStudentNote()!=null)//If there is a student note on this question we add it to the top of the question.
		{
			note.setText("Note:" +qie.getStudentNote());
			questionsAndAnswers.getChildren().add(note);
		}
		questionString.setText(Integer.toString(questionIndex)+". "+qie.getQuestionString()+" ("+Integer.toString(qie.getPointsValue())+" Points"+")" );
		questionsAndAnswers.getChildren().add(questionString);
		
		answers=new RadioButton[] {
				new RadioButton((char)(97)+". "+qie.getAnswer(1)),
				new RadioButton((char)(98)+". "+qie.getAnswer(2)),
				new RadioButton((char)(99)+". "+qie.getAnswer(3)),
				new RadioButton((char)(100)+". "+qie.getAnswer(4))};
		for(int i=0;i<4;i++)//Sets all answers of the question on window screen.
		{
			RadioButton r = answers[i];
			r.setWrapText(true);
			r.setToggleGroup(toogleGroup);
			r.setId(Integer.toString(i+1));
			questionsAndAnswers.getChildren().add(r);
		}
		questionWithAnswers.put(qie, toogleGroup);//Add question and it's group of answers to HashMap(will use us in SubmitButtonPressed method to check which answer the user marked for that question).
	
		toogleGroup.getToggles().clear();//Clear toogleGroup for the next question answers.
		
	}
	
	
	/**
	 * Sets manual exam on screen (Good luck image,download button).
	 * @throws IOException
	 */
	private void SetDownloadButtonOnScreen() {
		downloadButton.setVisible(true);
		downloadButton.setDisable(false);
		VBox manuelExamStringVBox=new VBox();
		Label manuelExamString=new Label("Welcome to the manual exam!, you can press the download button and start your exam.");
		manuelExamString.setId(blackLabel);
		manuelExamStringVBox.getChildren().add(manuelExamString);
		manuelExamStringVBox.setBackground(yellowBackground);
		
		VBox manuelExamImageVBox=new VBox();
		ImageView imageView=new ImageView();
		imageView.setFitHeight(215);
		imageView.setFitWidth(530);
		imageView.setImage(v);
		manuelExamImageVBox.getChildren().add(imageView);		
		questionsAndAnswers.getChildren().addAll(manuelExamStringVBox,manuelExamImageVBox);
		
		
	}



	/**
	 * When the student is pressing on the Download button he gets the manual exam to his desktop.
	 * @param event
	 * @throws IOException
	 */
	@FXML public void StudentPressedDownloadButton(ActionEvent event) throws IOException
	{	
		//getting the File from server
		AesWordDoc recievedFile = ActiveExamController.GetManualExam(activeExam);		
		
		//saving it in the desktop
		File examFile = new File(FileSystemView.getFileSystemView().getHomeDirectory()+"/"+activeExam.examIdToString()+"StudentsFile.doc");
		FileOutputStream fos = new FileOutputStream(examFile);
		fos.write(recievedFile.getMybytearray());
		fos.close();
		
	    Globals.popUp(AlertType.INFORMATION,"Download "+activeExam.getCourse().getName()+" Succeed","The exam is on your desktop, You can open it and start solving!");
		submitButton.setDisable(false);
		downloadButton.setDisable(true);

	}
	
		

	/**
	 * Sets true on activeExamIsLocked.
	 */
	public void lockExam() {
		this.activeExamIsLocked=true;
	}

	 
	 
	 
	/**
	 * If the Active exam is locked then the student gets a pop-up that say it and get him back to his main window.
	 */
	public boolean isLocked()
	{
		return this.activeExamIsLocked;
	}
	
	
	
	/**
	 * Submit process(in case that the student has submitted on time).
	 * @param event
	 */
	@FXML public void StudentPressedSubmitButton(ActionEvent event)
	{	
		submitStudentsExam(true);
	}
	
	/**
	 * Submit process(check solved exam+sending SolvedExam to generate report in server+confirmation dialog for the student)
	 * @param inTime
	 */
	public void submitStudentsExam(boolean inTime){
		/*Build solved Exam object/*/
		SolvedExam sendToGenerateReport=BuildSolvedExamObject(inTime);
		/*Confirmation Dialog/*/
		ConfirmationDialogForSubmitButton(sendToGenerateReport,inTime);		
	}

	/** 
	 * Build SolvedExam object.
	 * @param inTime 
	 * @return SolvedExam
	 */
	private SolvedExam BuildSolvedExamObject(boolean inTime) {
	
		Object[] studentAnsersAndScoreForExam=SystemCheckExam(inTime);
		HashMap<QuestionInExam,Integer> studentAnswers=(HashMap<QuestionInExam, Integer>) studentAnsersAndScoreForExam[0];
		int score=(int) studentAnsersAndScoreForExam[1];
			
		boolean teacherApproved=false;
		Student examSolver=new Student((Student)ClientGlobals.client.getUser());
		String teachersScoreChangeNote=null;
		int CompletedTimeInMinutes=(int) ((activeExam.getDuration()*60-timeSeconds)/60);
		SolvedExam sendToGenerateReport=new SolvedExam(score, teacherApproved, studentAnswers,
				examSolver, teachersScoreChangeNote,null, CompletedTimeInMinutes,activeExam.getCode(),activeExam.getType(), activeExam.getDate(),activeExam.getActivator(),activeExam.getExam());

		return sendToGenerateReport;
	}

	/**
	* Called from BuildSolvedExamObject method and runs all over student's answers for each question in the exam and return an Object[].
	* Object[0]=HashMap(QuestionInExam,Integer) which contains the question in exam as key and the student's answer index as value. 
	* Object[1]=Student's grade for exam.
	 * @param inTime 
	* @return Object[] studentAnsersAndScoreForExam
	*/
	
	private Object[] SystemCheckExam(boolean inTime)
	{
		int score=0;
		Object[] studentAnsersAndScoreForExam=new Object[2];
		RadioButton r=new RadioButton();
		HashMap<QuestionInExam,Integer> studentAnswers=new HashMap<QuestionInExam,Integer>();
		if(inTime)//Student submit the exam before that the time is over.
		{
			
			if(activeExam.getType()==1)//Active exam is computerize so we save student answer and check his exam.
			{
				for (QuestionInExam qie : questionWithAnswers.keySet())//Runs all over questions. 
				{
					//if(questionWithAnswers.get(qie).getSelectedToggle()==null)//Student didn't choose any answer.
						//studentAnswers.put(qie,0);
					
					if(questionWithAnswers.get(qie).getSelectedToggle()!=null)
					//else//Student choose answer.
					{
						r=(RadioButton) questionWithAnswers.get(qie).getSelectedToggle();
						int markedIndex = Integer.parseInt(r.getId());
						if(qie.getCorrectAnswerIndex()==markedIndex)//Student's answer is correct(he gets all points from the question).
							score+=qie.getPointsValue();
						studentAnswers.put(qie,markedIndex);
					}
				}
			}
			else//Active exam is manual so we fabricate student's answers and score.
			{
				ArrayList<QuestionInExam> questionsInExam=activeExam.getExam().getQuestionsInExam();
				for(QuestionInExam qie:questionsInExam)//Sets all questions with their info on screen.
				{
					studentAnswers.put(qie, 0);
				}
				score=0;
			}
		}
		else//Student did not have time to submit his exam.
		{
			for (QuestionInExam qie : questionWithAnswers.keySet())//Runs all over questions. 
			{
				if(questionWithAnswers.get(qie).getSelectedToggle()==null)//Student didn't choose any answer.
					studentAnswers.put(qie,0);
					
				else//Student choose answer.
				{
					r=(RadioButton) questionWithAnswers.get(qie).getSelectedToggle();
					int markedIndex = Integer.parseInt(r.getId());
					studentAnswers.put(qie,markedIndex);

				}
				score=0;//His grade is zero if he didn't submit his exam on time.
			}
		}
		studentAnsersAndScoreForExam[0]=studentAnswers;
		studentAnsersAndScoreForExam[1]=score;
		return studentAnsersAndScoreForExam;
	}

	/**
	 * When student press submit he gets a confirmation dialog and can press ok for submit,
	 *  or cancel to go back to the exam.
	 * @param inTime 
	 * @param SolvedExam
	 */
	private void ConfirmationDialogForSubmitButton(SolvedExam sendToGenerateReport, boolean inTime) {
		if(!inTime) {//Student didn't submit his exam on time.
			SolvedExamController.SendFinishedSolvedExam(this.activeExam,sendToGenerateReport,(Student)ClientGlobals.client.getUser());
			Globals.mainContainer.setScreen(ClientGlobals.StudentMainID);
			return;
		}
		
		//Student Submit his exam on time.
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("SubmitExam");
		alert.setContentText("Are you sure you want to submit?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() != ButtonType.OK) 
			return;
		if(activeExam.getType()!=0)	{
			String popUpTitle="Submit confirmation";
			String popUpContentText="The exam was submitted successfully!";
			Globals.popUp(AlertType.INFORMATION,popUpTitle,popUpContentText);
			SolvedExamController.SendFinishedSolvedExam(this.activeExam,sendToGenerateReport,(Student)ClientGlobals.client.getUser());
			Globals.mainContainer.setScreen(ClientGlobals.StudentMainID);
			return;
		}
		if(SolvedExamController.UploadFile(sendToGenerateReport)) {
			SolvedExamController.SendFinishedSolvedExam(this.activeExam,sendToGenerateReport,(Student)ClientGlobals.client.getUser());
			String popUpTitle="Submit confirmation";
			String popUpContentText="The exam was submitted successfully!";
			Globals.popUp(AlertType.INFORMATION,popUpTitle,popUpContentText);
			Globals.mainContainer.setScreen(ClientGlobals.StudentMainID);
			return;
		}
	}
	
	
	@FXML public void StudentPressedExitButton(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("SubmitExam");
		alert.setContentText("Are you sure you want to Exit without submitting the exam? This means you are submitting an empty exam!");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			SolvedExam sendToGenerateReport=BuildSolvedExamObject(false);
			/*Confirmation Dialog/*/
			ConfirmationDialogForSubmitButton(sendToGenerateReport,false);
		}
	}

	public void updateExamTime(Long extraTimeInMinutes) {
		setTimeSeconds(getTimeSeconds()+extraTimeInMinutes*60);
	}

	public void updateTimeLabel(Long timeInSeconds) {
		synchronized (timeSeconds) {
			String hour = "" + timeInSeconds/60/60;
			String minutes = "" + (timeInSeconds/60)%60;
			String seconds = "" + timeInSeconds%60;
			String time = hour + ":" + minutes + ":" +seconds;
			timeLeftLabel.setText(time);
		}
	}

	public void setTimeSeconds(Long timeSeconds) {
		synchronized (timeSeconds) {
			this.timeSeconds = timeSeconds;
		}
		if (timeSeconds <= 0) {
            String popUpTitle="Exam Over";
			String popUpContentText="The Exam time is up and thus submitted with no answers. next time pay attention to the time.";
			Globals.popUp(AlertType.INFORMATION,popUpTitle,popUpContentText);
			lockExam();
            //Globals.mainContainer.setScreen(ClientGlobals.StudentMainID);
        }
	}

	public Long getTimeSeconds() {
		Long ret;
		synchronized (timeSeconds) {
			ret = timeSeconds;
		}
		return ret;
	}
	
	
	
}
