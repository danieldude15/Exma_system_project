package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import logic.TimeChangeRequest;
import logic.iMessage;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;

public class TimeChangeController {

	@SuppressWarnings("unchecked")
	public static ArrayList<TimeChangeRequest> getAllRequests() {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			try {
				iMessage msg= new iMessage("getAllTimeChangeRequest",null);
				client.sendToServer(msg);
				return (ArrayList<TimeChangeRequest>) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
		return null;
	}


	public static void requestNewTimeChangeForActiveExam(TimeChangeRequest tcr) {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			try {
				iMessage msg= new iMessage("newTimeChangeRequest",tcr);
				client.sendToServer(msg);
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
		return;
	}


	public static void sendResponse(TimeChangeRequest TCR) {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			try {
				iMessage msg= new iMessage("timeChangeRequestResponse",TCR);
				client.sendToServer(msg);
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
		return;
	}
	
}
