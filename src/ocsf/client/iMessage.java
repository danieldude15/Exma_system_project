package ocsf.client;

public class iMessage {
	
	private String command;
	private Object obj;
	
	public iMessage(String cmd,Object o) {
		command=cmd;
		obj=o;
	}
	
	public Object getObj() {
		return obj;
	}
	
	public String getCommand() {
		return command;
	}

}
