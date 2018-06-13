package GUI;


import Controllers.ControlledScreen;
import Controllers.SolvedExamController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import logic.*;
import ocsf.client.ClientGlobals;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import Controllers.ControlledScreen;
import Controllers.SolvedExamController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import logic.*;
import ocsf.client.ClientGlobals;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

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
	
	
	
	@Override
	public void runOnScreenChange() {
		Globals.primaryStage.setHeight(630);
		Globals.primaryStage.setWidth(820);
		
		
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


	@FXML public void refreshTimer(MouseEvent event) {
		timeLeftLabel.setText(timeLeft);
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
		ArrayList<QuestionInExam> questionsInExam=active.getExam().getQuestionsInExam();
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
			note.setText(qie.getStudentNote());
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
	 * The student can download the manual exam from the system.
	 * @throws IOException
	 */
	private void SetDownloadButtonOnScreen() {
		// TODO Auto-generated method stub
		submitButton.setDisable(true);
		downloadButton.setVisible(true);
	}



	/**
	 * When the student pressed on Download button he can download the exam to any path he choose.
	 * @param event
	 * @throws IOException
	 */
	public void StudentPressedDownloadButton(ActionEvent event) throws IOException
	{
		/*AesWordDoc doc=ActiveExamController.GetManualExam(activeExam.getCode());//The path where to download it.
		FileOutputStream out= new FileOutputStream("ManualExam.docx");
		doc.write(out);
		out.close();/*/
		
		/*Open save dialog for the student where he can choose where to save the exam on his computer./*/
		AesWordDoc wordClass=new AesWordDoc();
		wordClass.OpenSaveFileDialog(this.GetActiveExam());
		
		submitButton.setDisable(false);
		downloadButton.setDisable(true);

		//AesWordDoc doc=ActiveExamController.GetManualExam(activeExam);
	}
	
		

		
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
	public void StudentPressedSubmitButton(ActionEvent event)
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
	private SolvedExam BuildSolvedExamObject(boolean inTime)
	{
	
		Object[] studentAnsersAndScoreForExam=SystemCheckExam(inTime);
		//Object[] studentAnsersAndScoreForExam=SolvedExamController.SystemCheckExam(activeExam, inTime, questionWithAnswers);
		HashMap<QuestionInExam,Integer> studentAnswers=(HashMap<QuestionInExam, Integer>) studentAnsersAndScoreForExam[0];
		int score=(int) studentAnsersAndScoreForExam[1];
			
		boolean teacherApproved=false;
		Student examSolver=new Student((Student)ClientGlobals.client.getUser());
		String teachersScoreChangeNote=null;
		int CompletedTimeInMinutes=0;//need to take care of it with the timer.
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
					if(questionWithAnswers.get(qie).getSelectedToggle()==null)//Student didn't choose any answer.
						studentAnswers.put(qie,0);
						
					else//Student choose answer.
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
				score=-1;
			}
		}
		else//Student did not have time to submit his exam
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
	private void ConfirmationDialogForSubmitButton(SolvedExam sendToGenerateReport, boolean inTime)
	{
		AesWordDoc wordClass=new AesWordDoc();
		if(inTime)//Student Submit his exam on time.
		{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setHeaderText("SubmitExam");
			alert.setContentText("Are you sure you want to submit?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK)//User choose ok for submit.
			{
				if(activeExam.getType()==0)//Exam is manual
				{
					XWPFDocument doc=wordClass.OpenUploadWordFileDialog();
					
					//Print the word file.
	 				//XWPFWordExtractor extract=new XWPFWordExtractor(doc);
	 				//System.out.println(extract.getText());
					
					/*Send message to the server to add solved exam to the list,
					so we can generate a report from all solved exams when the active exam will be lock.
					in addition the student is removing from the CheckOut list in server.
					/*/
					
							
				}
				
				SolvedExamController.SendFinishedSolvedExam(this.activeExam,sendToGenerateReport,(Student)ClientGlobals.client.getUser());
				
				String popUpTitle="Submit confirmation";
				String popUpContentText="The exam was submitted successfully!";
				PopUp(popUpTitle,popUpContentText);
				Globals.mainContainer.setScreen(ClientGlobals.StudentMainID);
	
			}
		}
		else//Student didn't submit his exam on time.
		{
			SolvedExamController.SendFinishedSolvedExam(this.activeExam,sendToGenerateReport,(Student)ClientGlobals.client.getUser());		
		}
	
	}
	
	
	//don't need it, here for compilation
	public void StudentPressedExitButton(ActionEvent event)
	{
		Globals.mainContainer.setScreen(ClientGlobals.StudentMainID);
	}


	public void updateExamTime(TimeChangeRequest o) {
		// TODO Auto-generated method stub
		
	}

	public void updateTimeLabel(Long timeInSeconds) {
		String hour = "" + timeInSeconds/60/60;
		String minutes = "" + (timeInSeconds/60)%60;
		String seconds = "" + timeInSeconds%60;
		String time = hour + ":" + minutes + ":" +seconds;
		timeLeftLabel.setText(time);
	}

	public void setTimeSeconds(Long timeSeconds) {
		this.timeSeconds = timeSeconds;
		if (timeSeconds <= 0) {
            String popUpTitle="Exam Over";
			String popUpContentText="The Exam time is up and thus submitted with no answers. next time pay attention to the time.";
            PopUp(popUpTitle,popUpContentText);
			lockExam();
            Globals.mainContainer.setScreen(ClientGlobals.StudentMainID);
        }
	}

	public Long getTimeSeconds() {
		return timeSeconds;
	}
	
	public void PopUp(String title,String contentText)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(contentText);
		alert.show();
	}
	
	
}
