package Testings;

import java.io.IOException;

import ocsf.server.IConnectionToClient;

public class ConnectionToClientStub implements IConnectionToClient {

	AESClientStub clientMock;
	
	public ConnectionToClientStub(AESClientStub clientMock) {
		this.clientMock=clientMock;
	}

	@Override
	public void sendToClient(Object msg) throws IOException {
		clientMock.handleMessageFromServer(msg);
	}

	@Override
	public boolean isAlive() {
		return true;
	}

}
