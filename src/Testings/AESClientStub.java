package Testings;

import java.io.IOException;

import logic.iMessage;
import ocsf.client.AESClient;
import ocsf.client.IAESClient;
/**
 * This class is for breaking the dependency between server and client(for test cases).
 *
 */
public class AESClientStub implements IAESClient{

	AESServerStub mockServer;
	iMessage message;
	public AESClientStub() {
	}
	/**
	 * Replace the real handle message from server method.
	 */
	@Override
	public void handleMessageFromServer(Object ServerMsg) {
		AESClient c = new AESClient("test", 0);
		c.handleMessageFromServer(ServerMsg);
		message = c.getMsg();
		
	}
	/**
	 * Replace the real send to server method.
	 */
	@Override
	public void sendToServer(Object msg) throws IOException {
		mockServer.handleMessageFromClient(msg, null);
		
	}
	/**
	 * MockServer getter.
	 * @return
	 */
	public AESServerStub getMockServer() {
		return mockServer;
	}
	/**
	 * MockServer setter.
	 * @param mockServer
	 */
	public void setMockServer(AESServerStub mockServer) {
		this.mockServer = mockServer;
	}

	@Override
	public iMessage getResponseFromServer() {
		return message;
	}
	
	

}
