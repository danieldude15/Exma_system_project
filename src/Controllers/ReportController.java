package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import logic.Exam;
import logic.ExamReport;
import logic.iMessage;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;

@SuppressWarnings("unchecked")
public class ReportController {
	
	public static ExamReport getExamReport(Exam e){
		AESClient client = ClientGlobals.client;
		ExamReport report = null;
		if(client.isConnected()) {
			iMessage msg = new iMessage("getExamReport",e);
			try {
				client.sendToServer(msg);
				return (ExamReport) client.getResponseFromServer().getObj();
			} catch (IOException ex) {
				ClientGlobals.handleIOException(ex);
			}
		}
			return null;
	}
	
	/**
	 * Sending to server a request to get all exams report.
	 * @return
	 */
	public static ArrayList<ExamReport> getAllExamReport(){
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			iMessage msg = new iMessage("getAllExamReport",null);
			try {
				client.sendToServer(msg);
				return (ArrayList<ExamReport>) client.getResponseFromServer().getObj();
			} catch (IOException ex) {
				ClientGlobals.handleIOException(ex);
			}
		}
		return null;
	}
	
}
