package Testings;

import java.io.IOException;

import logic.iMessage;
import ocsf.client.AESClient;
import ocsf.client.IAESClient;

public class AESClientMock implements IAESClient{

	AESServerMock mockServer;
	iMessage message;
	public AESClientMock() {
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

	public AESServerMock getMockServer() {
		return mockServer;
	}

	public void setMockServer(AESServerMock mockServer) {
		this.mockServer = mockServer;
	}

	@Override
	public iMessage getResponseFromServer() {
		return message;
	}
	
	

}
