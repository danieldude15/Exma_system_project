package Controllers;

import logic.*;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.filechooser.FileSystemView;
/**
 *  This controller is responsible for all information that has to do with solved exams
 *  it has functionality such as adding/editing solved exams also getting solved exams for informational purpeses.
 * @author Group-12
 *
 */
@SuppressWarnings("unchecked")
public class SolvedExamController {

	/**
	 * Send to server a request to pull the object solved exams from database.
	 * 
	 * @param u - is the user object of the student to get the 
	 * students solved exams or teacher to get the teachers solved exams.
	 * also possible to send an integer with the user id to get his solved exams
	 * 
	 * @return SolvedExam
	 */
	public static ArrayList<SolvedExam> getSolvedExamsByUser(Object u) {
		AESClient client = ClientGlobals.client;
		iMessage msg;
		if(client.isConnected()) {
			if(u instanceof Student)
				msg= new iMessage("getStudentsSolvedExams",(Student)u);
			else if (u instanceof Teacher)
				msg= new iMessage("getTeacherSolvedExams",(Teacher)u);
			else if (u instanceof Integer) {
				msg = new iMessage("getSolvedExamsByID",(Integer) u );
			} else return null;
			try {
				client.sendToServer(msg);
				return (ArrayList<SolvedExam>) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		} 
		return null;
	}

	public static ArrayList<ExamReport> getCompletedExams(Teacher user) {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			try {
				client.sendToServer(new iMessage("getTeacherCompletedExams",user));
				return (ArrayList<ExamReport>) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		} 
		return null;
	}

	public static AesWordDoc getStudentsManulaExam(SolvedExam se) {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			try {
				iMessage msg= new iMessage("GetStudentsManualExam",se);
				client.sendToServer(msg);
				return (AesWordDoc) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
		return null;
	}

	public static int updateSolvedExam(SolvedExam solvedExam) {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			try {
				client.sendToServer(new iMessage("updateSolvedExam",solvedExam));
				return (Integer) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		} 
		return 0;
	}
	

	public static boolean UploadFile(SolvedExam solvedExam)
	{
		AESClient client = ClientGlobals.client;
		if(!client.isConnected()) 
			return false;
		try {
			AesWordDoc studentsFile = new AesWordDoc(solvedExam.examIdToString()+"StudentsFile.doc");
		  
			File examFile = new File(FileSystemView.getFileSystemView().getHomeDirectory()+"/"+studentsFile.getFileName());
			studentsFile.setSize((int) examFile.length());
			studentsFile.initArray((int) examFile.length());
								
			FileInputStream fis = new FileInputStream(examFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			
			bis.read(studentsFile.getMybytearray(),0,(int) examFile.length());
			try {
				iMessage msg = new iMessage("yourExamFile", studentsFile);
				Object[] o=new Object[2];	
				o[0]=(AesWordDoc)studentsFile;
				o[1]=(SolvedExam)solvedExam;
				client.sendToServer(new iMessage("UploadSolvedExam",o));
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
				return false;
			}
		} catch (IOException e) {
			System.err.println("Could not read from file");
			e.printStackTrace();
			return false;
		} 
		return true;
	}
	/**
	 * Send message to the server when the student finished his exam.
	 * @param activeExam
	 * @param solvedExam
	 * @param s
	 */
	public static void SendFinishedSolvedExam(ActiveExam activeExam,SolvedExam solvedExam,Student s)
	{
		Object[] o=new Object[4];
		o[0]=(ActiveExam)activeExam;
		o[1]=(SolvedExam)solvedExam;
		o[2]=(Student)s;
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			try {
				client.sendToServer(new iMessage("FinishedSolvedExam",o));
				client.getResponseFromServer();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		} 
	}
}
