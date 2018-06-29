package logic;

import java.io.Serializable;

/**
 * This class is used to communicate between Client and Server so they can pass information 
 * including a command and an object
 * special command will do special things while others have no meaning and their only importance is the object passing by.
 * @author Group-12
 *
 */
public class iMessage implements Serializable{
	
	/**
	 * Serializable id give for client server communication
	 */
	private static final long serialVersionUID = 1976516783215133976L;
	/**
	 * this holds the command that should explain what object it is holding
	 */
	private String command;
	/**
	 * this obj could be anything from ArrayList to a User or Question
	 */
	private Object obj;
	
	/**
	 * iMessage constructor
	 * @param cmd - the String of command
	 * @param o - the object of the msg
	 */
	public iMessage(String cmd,Object o) {
		command=cmd;
		obj=o;
	}

	/**
	 * getObj
	 * @return the onject this iMessage holds
	 */
	public Object getObj() {
		return obj;
	}
	
	/**
	 * getCommand
	 * @return the String Command of witch this iMessage Holds
	 */
	public String getCommand() {
		return command;
	}
	
	/**
	 * setObj
	 * @param o to set to
	 */
	public void setObj(Object o) {
		obj=o;
	}
	
	/**
	 * setCommand
	 * @param cmd - the command to set to
	 */
	public void setCommand(String cmd) {
		command = cmd;
	}

	/**
	 * toString Override
	 */
	@Override public String toString() {
		return new String("command:"+command + " --- Object:" + obj);
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj instanceof iMessage){
			iMessage message = (iMessage)obj;
			if(!message.getCommand().equals(getCommand()))
				return false;
			if(!message.getObj().equals(getObj()))
				return false;
			return true;
		}
		return false;
	}
}
