package ocsf.client;

public class AESClient extends AbstractClient{
	
	
	private iMessage msg;
	public boolean stopWaiting=false;
	public AESClient(String host, int port) {
		super(host, port);
		// TODO Auto-generated constructor stub
	}


	/**
	 * Handles a message sent from the server to this client.
	 *
	 * @param msg   this will always be an iMessage type of object 
	 */
	protected void handleMessageFromServer(Object msg){
		if(!(msg instanceof iMessage)) {
			return;
		}
		iMessage m = (iMessage) msg;
		String cmd = new String(m.getCommand());
		switch(cmd) {
		case "setTeachersQuestions":
			//code here
			break;
		case "setTeachersExams":
			//code here
			break;
		case "setTeachersFields":
			//code here
			break;
			
		default:
			
		}
		
	}
	protected void connectionClosed() {
		System.out.println("connection Closed!");
	}

	public iMessage getMsg() {
		return msg;
	}
	
	public void waitForResponse() {
		int count=0;
		while(!stopWaiting) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count++;
			if(count>=50) {
				ClientGlobals.handleIOException();
				break;
			}
		}
	}


	public void cleanMsg() {
		msg=null;
		stopWaiting=false;
	}
}
