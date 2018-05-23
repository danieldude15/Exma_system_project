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
				client.waitForResponse();
				Object o = msg.getObj();
				if (o instanceof ExamReport)
					report = new ExamReport((ExamReport)o);
				client.cleanMsg();
				return report;
			} catch (IOException ex) {
				ClientGlobals.handleIOException();
				ex.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}
	
}
