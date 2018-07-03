package ocsf.server;

public interface IAESServer {
	
	public void handleMessageFromClient(Object msg, ConnectionToClient client);
	
}
