package logic;

public class User {
	private String userName;
	private String password;
	private String name;
	
	
	public User(String userName,String Password,String Name)/*Constructor/*/
	{
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
}

