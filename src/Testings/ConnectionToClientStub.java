package Testings;

import java.io.IOException;

import ocsf.server.IConnectionToClient;
/**
 * This class is for breaking the dependency between server and client(for test cases).
 */
public class ConnectionToClientStub implements IConnectionToClient {

	AESClientStub clientMock;
	
	public ConnectionToClientStub(AESClientStub clientMock) {
		this.clientMock=clientMock;
	}
	/**
	 * Replace the real sentToClient method.
	 */
	@Override
	public void sendToClient(Object msg) throws IOException {
		clientMock.handleMessageFromServer(msg);
	}
	/**
	 * Replace the real isAlive method.
	 */
	@Override
	public boolean isAlive() {
		return true;
	}

}
