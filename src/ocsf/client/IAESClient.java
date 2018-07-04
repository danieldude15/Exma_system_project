package ocsf.client;

import java.io.IOException;

import logic.iMessage;

public interface IAESClient {
	
	public void handleMessageFromServer(Object ServerMsg);

	public void sendToServer(Object msg) throws IOException;

	public iMessage getResponseFromServer();

}
