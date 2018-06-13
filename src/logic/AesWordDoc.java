package logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

public class AesWordDoc extends XWPFDocument implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9193230217538325507L;

	public AesWordDoc() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AesWordDoc(InputStream is) throws IOException {
		super(is);
		// TODO Auto-generated constructor stub
	}

	public AesWordDoc(OPCPackage pkg) throws IOException {
		super(pkg);
		// TODO Auto-generated constructor stub
	}

	public XWPFDocument CreateWordFile(ActiveExam activeExam)
	{
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
				runOnquestionsParagraph.setText((char)(i+96)+". "+qie.getAnswer(i));
				runOnquestionsParagraph.addBreak();
			}
		}
		runOnquestionsParagraph.addBreak();
		runOnquestionsParagraph.addBreak();
				
		//Create good luck paragraph
		XWPFParagraph GoodLuckParagraph=doc.createParagraph();
		XWPFRun runOnGoodLuckParagraph=GoodLuckParagraph.createRun();
		runOnGoodLuckParagraph.setText("Good Luck!");

		return doc;
	}

	
	public void OpenSaveFileDialog(ActiveExam activeExam) throws IOException
	{
		FileChooser saveWindow = new FileChooser();
		saveWindow.setTitle("Save exam");
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("docx files (*.docx)", "*.docx");
        saveWindow.getExtensionFilters().add(extFilter);
         
         //Show save file dialog
         File file = saveWindow.showSaveDialog(Globals.primaryStage);
         if(file != null)
             SaveFile(file, CreateWordFile(activeExam));
         
        
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
 			
 }

	 
		
	 /**
	  * When the student pressed on submit button and the exam is manual,he has to upload his exam back to the system.
	  */
	 	public XWPFDocument OpenUploadWordFileDialog() {
	 		// TODO Auto-generated method stub
	 		XWPFDocument doc;
	  		FileChooser Uploadwindow=new FileChooser();
	  		Uploadwindow.setTitle("Upload your exam");
	  		File file=Uploadwindow.showOpenDialog(Globals.primaryStage);
	  		if(file!=null)
	  			try {
	  				doc=new AesWordDoc(new FileInputStream(file)) ;
	  				return doc;
	  				
	  				//XWPFWordExtractor extract=new XWPFWordExtractor(document);
	  				//System.out.println(extract.getText());
	  			} catch (FileNotFoundException e) {
	  				// TODO Auto-generated catch block
	  				e.printStackTrace();
	  			} catch (IOException e) {
	  				// TODO Auto-generated catch block
	  				e.printStackTrace();
	  			}
	  		return null;
	 	}


	 
	 
}
