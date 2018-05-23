package logic;

public class User {
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
}

