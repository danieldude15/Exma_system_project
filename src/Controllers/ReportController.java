package Controllers;

import java.io.IOException;

import logic.Exam;
import logic.ExamReport;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;
import logic.iMessage;

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
	
}
