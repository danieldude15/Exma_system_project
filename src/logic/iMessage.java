package logic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class iMessage implements Serializable{
	
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
	
	public void setObj(Object o) {
		obj=o;
	}
	
	public void setCommand(String cmd) {
		command = cmd;
	}

	public String toString() {
		return new String("command:"+command + " --- Object:" + obj);
	}
}
