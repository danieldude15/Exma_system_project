package Testings;


import java.io.IOException;

import logic.iMessage;
import ocsf.server.AESServer;
import ocsf.server.ConnectionToClient;
import ocsf.server.IAESServer;

public class AESServerStub implements IAESServer {

	AESClientStub clientMock;
	AESServer realServer;
	
	public AESServerStub(AESClientStub cm) {
		clientMock = cm;
		realServer = new AESServer();
	}

	@Override
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		System.out.println("Mock Server Got msg:" + msg);
		if(!(msg instanceof iMessage)) {
			System.out.println("msg from client is not of type iMessage!");
			return;
		}
		iMessage m = (iMessage) msg;
		String cmd = new String(m.getCommand());
		Object o = m.getObj();
		switch(cmd) {
			case "login":
				try {
					realServer.loginFunctionality(new ConnectionToClientStub(clientMock),new DBMainStub(), o);
				} catch (IOException e) {
					// this should never catch because we are not really sending IO to anyone.
					// this is a test.
				}
				break;
		} 
	}

}
