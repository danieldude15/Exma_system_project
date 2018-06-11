package GUI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import Controllers.ActiveExamController;
import Controllers.ControlledScreen;
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
import javafx.stage.FileChooser;
import logic.ActiveExam;
import logic.Course;
import logic.Exam;
import logic.Globals;
import logic.QuestionInExam;
import logic.SolvedExam;
import logic.Student;
import logic.Teacher;
import logic.TimeChangeRequest;
import ocsf.client.ClientGlobals;

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
		//Thread timer = Globals.createTimer(activeExam,this);
		//timer.start();
		
		//Active exam is manual.
		if(activeExam.getType()==0)
		{
			
			SetDownloadButtonOnScreen();
			
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
//blab

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
			//aIndex=1;
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
		answers=new RadioButton[] {new RadioButton(qie.getAnswer(1)),new RadioButton(qie.getAnswer(2)),new RadioButton(qie.getAnswer(3)),new RadioButton(qie.getAnswer(4))};
		for(RadioButton r:answers)//Sets all answers of the question on window screen.
		{
			r.setWrapText(true);
			r.setToggleGroup(toogleGroup);
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
		/*XWPFDocument doc=ActiveExamController.GetManualExam(activeExam.getCode());//The path where to download it.
		FileOutputStream out= new FileOutputStream("ManualExam.docx");
		doc.write(out);
		out.close();/*/
		
		
		
		
		//Create document
		XWPFDocument doc=new XWPFDocument();
		
		//Create title paragraph
		XWPFParagraph titleParagraph=doc.createParagraph();
		titleParagraph.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun runTitleParagraph=titleParagraph.createRun();
		runTitleParagraph.setBold(true);
		runTitleParagraph.setItalic(true);
		runTitleParagraph.setColor("00FF00");
		runTitleParagraph.setText(activeExam.getExam().getCourse().getName());
		runTitleParagraph.addBreak();
		runTitleParagraph.addBreak();
		
		//Create exam details paragraph
		XWPFParagraph examDetailsParagraph=doc.createParagraph();
		examDetailsParagraph.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun runOnExamDetailsParagraph=examDetailsParagraph.createRun();
		runOnExamDetailsParagraph.setText("Field: "+activeExam.getExam().getField().getName());
		runOnExamDetailsParagraph.addBreak();
		runOnExamDetailsParagraph.setText("Date: "+activeExam.getDate());
		runOnExamDetailsParagraph.addBreak();
		
		//Create question+answers paragraph
		XWPFParagraph questionsParagraph=doc.createParagraph();
		questionsParagraph.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun runOnquestionsParagraph=questionsParagraph.createRun();
		int questionIndex=1;
		ArrayList<QuestionInExam> questionsInExam=activeExam.getExam().getQuestionsInExam();
		for(QuestionInExam qie:questionsInExam)//Sets all questions with their info on screen.
		{
			if(qie.getStudentNote()!=null)
			{
				runOnquestionsParagraph.setText(qie.getStudentNote());
				runOnquestionsParagraph.addBreak();
			}
			runOnquestionsParagraph.setText(questionIndex+". "+qie.getQuestionString()+" ("+qie.getPointsValue()+" Points)");
			runOnquestionsParagraph.addBreak();
			for(int i=1;i<5;i++)
			{
				runOnquestionsParagraph.setText(qie.getAnswer(i));
				runOnquestionsParagraph.addBreak();
			}
		}
		runOnquestionsParagraph.addBreak();
		runOnquestionsParagraph.addBreak();
		
		//Create good luck paragraph
		XWPFParagraph GoodLuckParagraph=doc.createParagraph();
		XWPFRun runOnGoodLuckParagraph=GoodLuckParagraph.createRun();
		runOnGoodLuckParagraph.setText("Good Luck!");

		

	
		//Save file! currently doesn't work.
		FileChooser fileChooser = new FileChooser();
		
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("docx files (*.docx)", "*.docx");
        fileChooser.getExtensionFilters().add(extFilter);
         
         //Show save file dialog
         File file = fileChooser.showSaveDialog(Globals.primaryStage);
         if(file != null)
             SaveFile(file, doc);
         
         
         
	}
	
	/**
	 * Show for the user a save dialog where he can pick his saving path for the word file.
	 * @param file
	 * @param doc
	 * @throws IOException
	 */
	 private void SaveFile(File file, XWPFDocument doc) throws IOException{

            OutputStream outputStream = new FileOutputStream(file);
            doc.write(outputStream);
            outputStream.flush();
            outputStream.close();
            
            Alert alert=new Alert(AlertType.INFORMATION);
 			alert.setTitle("Download Succeed");
 			alert.setHeaderText(null);
 			alert.setContentText("You can open it and start your exam!");
 			alert.showAndWait();
 			
 			submitButton.setDisable(false);
 			downloadButton.setDisable(true);
 }
	
	
	/**
	 * If the Active exam is locked then the student gets a pop-up that say it and get him back to his main window.
	 */
	public boolean isLocked()
	{
		return false;
	}
	
	/**
	 * Submit process(upload to database the student SolvedExam).
	 * @param event
	 */
	public void StudentPressedSubmitButton(ActionEvent event)
	{	
		/*Build solved Exam object/*/
		SolvedExam uploadToDatabase=BuildSolvedExamObject();
		/*Confirmation Dialog/*/
		ConfirmationDialogForSubmitButton(uploadToDatabase);		
	}
	

	/** 
	 * Build SolvedExam object.
	 * @return SolvedExam
	 */
	private SolvedExam BuildSolvedExamObject()
	{
		Exam e=new Exam(activeExam.getExam());
		int examid=e.getID();
		Course course=new Course(e.getCourse());
		int duration=e.getDuration();
		Teacher teacher=e.getAuthor();
		
		Object[] studentAnsersAndScoreForExam=SystemCheckExam();
		HashMap<QuestionInExam,Integer> studentAnswers=(HashMap<QuestionInExam, Integer>) studentAnsersAndScoreForExam[0];
		int score=(int) studentAnsersAndScoreForExam[1];
		
		
		boolean teacherApproved=false;
		Student examSolver=new Student((Student)ClientGlobals.client.getUser());
		String teachersScoreChangeNote=null;
		int CompletedTimeInMinutes=0;//need to take care of it with the timer.
		SolvedExam uploadToDatabase=new SolvedExam(score, teacherApproved, studentAnswers,
				examSolver, teachersScoreChangeNote,null, CompletedTimeInMinutes,activeExam.getCode(),activeExam.getType(), activeExam.getDate(),activeExam.getActivator(),e);

		/*going to replace all in this.
		SolvedExam s=new SolvedExam(e.getID(),new Course(e.getCourse()), e.getDuration(), new Teacher(e.getAuthor())
				, score, false, studentAnswers, 5,
				new Student((Student)ClientGlobals.client.getUser()), null, CompletedTimeInMinutes);
		going to replace all in this./*/
		
		/*//delete it all!
		System.out.println("score= "+score);
		System.out.println("studentAnswersSIZE="+studentAnswers.size());
		
		for (QuestionInExam qie : studentAnswers.keySet()) 
		{
			System.out.println("answer="+studentAnswers.get(qie));
		}
		//delete it all!/*/
		return uploadToDatabase;
	}
	
	
	/*
	/**
	 * Called from BuildSolvedExamObject method and runs all over student's answers for each question in the exam and return an Object[].
	 * Object[0]=HashMap(QuestionInExam,Integer) which contains the question in exam as key and the student's answer index as value. 
	 * Object[1]=Student's grade for exam.
	 * @return Object[] studentAnsersAndScoreForExam
	 */
	/*/
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
/*/




	/**
	* Called from BuildSolvedExamObject method and runs all over student's answers for each question in the exam and return an Object[].
	* Object[0]=HashMap(QuestionInExam,Integer) which contains the question in exam as key and the student's answer index as value. 
	* Object[1]=Student's grade for exam.
	* @return Object[] studentAnsersAndScoreForExam
	*/
	private Object[] SystemCheckExam()
	{
		int score=0;
		Object[] studentAnsersAndScoreForExam=new Object[2];
		RadioButton r=new RadioButton();
		HashMap<QuestionInExam,Integer> studentAnswers=new HashMap<QuestionInExam,Integer>();
		if(activeExam.getType()==1)//Active exam is computerize so we save student answer and check his exam.
		{
			for (QuestionInExam qie : questionWithAnswers.keySet())//Runs all over questions. 
			{
				r=(RadioButton) questionWithAnswers.get(qie).getSelectedToggle();
				if(r.getText()==null)//Student didn't choose any answer.
					studentAnswers.put(qie,0);
				else//Student choose answer.
				{
					for(int i=1;i<5;i++)//Runs all over question's answers.
					{
						if(r.getText().equals(qie.getAnswer(i)))//Student answer equal to answer in index i(1-4).
						{
							studentAnswers.put(qie,i);//Insert the question and student's index of answer to HashMap.
							if(qie.getCorrectAnswerIndex()==i)//Student's answer is correct(he gets all points from the question).
								score+=qie.getPointsValue();
							break;
						}
					}
				}
			}
		}
		else//Active exam is manual so we fabricate student's answers and score.
		{
			ArrayList<QuestionInExam> questionsInExam=activeExam.getExam().getQuestionsInExam();
			for(QuestionInExam qie:questionsInExam)//Sets all questions with their info on screen.
			{
				studentAnswers.put(qie, 1);
			}
			score=100;
		}
		studentAnsersAndScoreForExam[0]=studentAnswers;
		studentAnsersAndScoreForExam[1]=score;
		return studentAnsersAndScoreForExam;
	}





	/**
	 * When student press submit he gets a confirmation dialog and can press ok for submit,
	 *  or cancel to go back to the exam.
	 * @param SolvedExam
	 */
	private void ConfirmationDialogForSubmitButton(SolvedExam uploadToDatabase)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("SubmitExam");
		alert.setContentText("Are you sure you want to submit?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)//User choose ok for submit.
		{
			/*Send message to the server via ActiveExamController to delete Student from ActiveExam list/*/
			//ActiveExamController.StudentCheckedOutFromActiveExam((Student)ClientGlobals.client.getUser(),activeExam);//Student check out from exam.
			
			/*Send message to the server via SolvedExamController to add Student's SolvedExam to database./*/
			//SolvedExamController.UploadSolvedExamToDatabase(uploadToDatabase);
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Submit confirmation");
			alert.setHeaderText(null);
			alert.setContentText("The exam was submitted successfully!");
			alert.showAndWait();
			Globals.mainContainer.setScreen(ClientGlobals.StudentMainID);

		}
		
	}
	
	//don't need it, here for compilation
	public void StudentPressedExitButton(ActionEvent event)
	{
		Globals.mainContainer.setScreen(ClientGlobals.StudentMainID);
	}

	
	public void lockExam() {
		// TODO Auto-generated method stub
		
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
	}

	public Long getTimeSeconds() {
		return timeSeconds;
	}
	
	
	
}
