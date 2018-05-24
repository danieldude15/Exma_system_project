package logic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable{
	private int id;
	private String userName;
	private String password;
	private String name;
	
	
	public User(int id,String userName,String Password,String Name)/*Constructor/*/
	{
		this.id = id;
		this.userName=userName;
		this.password=Password;
		this.name=Name;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getName() {
		return name;
	}
	
	public int getID() {
		return id;
	}
	
	public String toString() {
		return new String("ID:"+id+" UserName:"+userName+" Password:"+password+" FullName:"+name);
	}
}

