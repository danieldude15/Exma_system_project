package logic;

import java.io.Serializable;
import java.util.ArrayList;


public class iMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1976516783215133976L;
	private String command;
	private Object obj;
	
	public iMessage(String cmd,Object o) {
		command=cmd;
		obj=o;
	}
	
	public iMessage(Object obj) {
		super();
		if(obj instanceof iMessage) {
			command = new String(((iMessage)obj).getCommand());
			Object o = ((iMessage)obj).getObj();
			if (o instanceof ArrayList<?>) {
				this.obj = ((ArrayList<?>) o).clone();
			}
		}
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
