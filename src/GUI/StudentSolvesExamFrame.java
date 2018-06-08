package GUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.mysql.jdbc.Field;

import Controllers.ActiveExamController;
import Controllers.ControlledScreen;
import Controllers.SolvedExamController;
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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
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
		
		/*Student check in to the Active exam./*/
		//ActiveExamController.StudentCheckedInToActiveExam((Student)ClientGlobals.client.getUser(),activeExam);
		
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
		int aIndex=1;
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
			indexOfAnswerInQuestion.put(r.getText(), aIndex);//Add answer and it's index to HashMap(will use us in SubmitButtonPressed method to convert student's answer String to Integer index).
			aIndex++;
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
	 * When student pressed on Download button he can download the exam to any path he choose.
	 * @param event
	 * @throws IOException
	 */
	public void StudentPressedDownloadButton(ActionEvent event) throws IOException
	{
		XWPFDocument doc=ActiveExamController.GetManualExam(activeExam.getCode());//The path where to download it.
		FileOutputStream out= new FileOutputStream("ManualExam.docx");
		doc.write(out);
		out.close();
		
		
		/*
		FileChooser fs=new FileChooser();
		fs.setTitle("Save Manual exam");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fs.getExtensionFilters().add(extFilter);
		fs.showSaveDialog(null);
		File fi = fs.showOpenDialog(null);
		FileOutputStream out= new FileOutputStream(fi.getPath());
		doc.write(out);
		out.close();
		/*/
		/*Create document
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
		examDetailsParagraph.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun runOnExamDetailsParagraph=examDetailsParagraph.createRun();
		runOnExamDetailsParagraph.setText("Field: "+activeExam.getExam().getField().getName());
		runOnExamDetailsParagraph.addBreak();
		runOnExamDetailsParagraph.setText("Date: "+activeExam.getDate());
		runOnExamDetailsParagraph.addBreak();
		
		//Create question+answers paragraph
		XWPFParagraph questionsParagraph=doc.createParagraph();
		questionsParagraph.setAlignment(ParagraphAlignment.LEFT);
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
			for(int i=0;i<4;i++)
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

		

		
		String s=new String();
		
		List<XWPFParagraph> list=doc.getParagraphs();
		for(XWPFParagraph p:list)
		{
			s=s+p.getText();
		}
		
	     
		FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        
        //Show save file dialog
        File file = fileChooser.showSaveDialog(null);
        if(file != null){
        	try {
                FileWriter fileWriter = null;
                
                fileWriter = new FileWriter(file);
                fileWriter.write(s);
                fileWriter.close();
            } catch (IOException ex) {
                
            }
        }
        /*/
	}
	
	
	/**
	 * If the Active exam is locked then the student gets a pop-up that say it and get him back to his main window.
	 */
	public void isLocked()
	{
		
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
		int examReportId=5;//need to take care of it.
		Student examSolver=new Student((Student)ClientGlobals.client.getUser());
		String teachersScoreChangeNote=null;
		int CompletedTimeInMinutes=0;//need to take care of it with the timer.
		SolvedExam uploadToDatabase=new SolvedExam(examid, course, duration, teacher
				, score, teacherApproved, studentAnswers, examReportId,
				examSolver, teachersScoreChangeNote, CompletedTimeInMinutes);

		/*going to replace all in this.
		SolvedExam s=new SolvedExam(e.getID(),new Course(e.getCourse()), e.getDuration(), new Teacher(e.getAuthor())
				, score, false, studentAnswers, 5,
				new Student((Student)ClientGlobals.client.getUser()), null, CompletedTimeInMinutes);
		/*/
		
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
	
	
	/**
	 * Called from BuildSolvedExamObject method and run all over student's answers for each question in the exam and return an Object[].
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
	
}
