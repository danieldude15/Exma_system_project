package logic;

public class User {
	private String userName;
	private String password;
	private String name;
	
	
	public User(String userName,String Password,String Name)
	{
		this.userName=userName;
		this.password=Password;
		this.name=Name;
	}
	
	public String getUserName() {
		return this.userName;
	}
}

