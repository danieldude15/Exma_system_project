package logic;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class AesWordDoc implements Serializable {
	
	private String examId=null;
	private String fileName=null;	
	private int size=0;
	public  byte[] mybytearray;
	
	
	public void initArray(int size)
	{
		mybytearray = new byte [size];	
	}
	
	public AesWordDoc( String pathname) {
		this.fileName = pathname; 
	}
	
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public byte[] getMybytearray() {
		return mybytearray;
	}
	
	public byte getMybytearray(int i) {
		return mybytearray[i];
	}

	public void setMybytearray(byte[] mybytearray) {
		
		for(int i=0;i<mybytearray.length;i++)
		this.mybytearray[i] = mybytearray[i];
	}

	public String getDescription() {
		return examId;
	}

	public void setDescription(String description) {
		examId = description;
	}	
	
	public AesWordDoc CreateWordFile(ActiveExam activeExam,String path) throws IOException
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
	    writer.write("\t\t"+activeExam.getExam().getCourse().getName()+"\n");
	    writer.write("\tField: "+activeExam.getExam().getField().getName()+"\n");
	    writer.write("\tDate: "+activeExam.getDate()+"\n\n");
		
	    ArrayList<QuestionInExam> questionsInExam=activeExam.getExam().getQuestionsInExam();
	    int questionIndex=1;
		for(QuestionInExam qie:questionsInExam)//Sets all questions with their info on screen.
		{
			writer.write(questionIndex+". "+qie.getQuestionString()+" ("+qie.getPointsValue()+" Points)\n");
			if(!qie.getStudentNote().equals(""))
			{
				writer.write("Note:" + qie.getStudentNote()+"\n");
			}
			questionIndex++;
			for(int i=1;i<5;i++) {
				writer.write("\t"+i+". "+qie.getAnswer(i)+"\n");
			}
		}
		writer.write("\n\nGood Luck!");
	    writer.close();
		
		
		
		return new AesWordDoc(path);
	}
}

