package Testings;

import java.io.IOException;

import logic.iMessage;
import ocsf.client.AESClient;
import ocsf.client.IAESClient;

public class AESClientStub implements IAESClient{

	AESServerStub mockServer;
	iMessage message;
	public AESClientStub() {
	}

	@Override
	public void handleMessageFromServer(Object ServerMsg) {
		AESClient c = new AESClient("test", 0);
		c.handleMessageFromServer(ServerMsg);
		message = c.getMsg();
		
	}

	@Override
	public void sendToServer(Object msg) throws IOException {
		mockServer.handleMessageFromClient(msg, null);
		
	}

	public AESServerStub getMockServer() {
		return mockServer;
	}

	public void setMockServer(AESServerStub mockServer) {
		this.mockServer = mockServer;
	}

	@Override
	public iMessage getResponseFromServer() {
		return message;
	}
	
	

}
