package logic;

import java.io.Serializable;

/**
 * This Class is the Basic User Class it is used to store a users information
 * @author Group-12
 *
 */
public class User implements Serializable{
	/**
	 * Serializable id give for client server communication
	 */
	private static final long serialVersionUID = -8730837249109057228L;
	/**
	 * user ID usually should have a SSN value or unique ID kept in database
	 */
	private int id;
	/**
	 * UserName is the String of witch the user types in to login to the system
	 */
	private String userName;
	/**
	 * password string kept fo authentication when user loggs in
	 */
	private String password;
	/**
	 * Users Full name String variable
	 */
	private String name;
	
	/**
	 * Constructor made to build new User class
	 * @param id - the unique id of the user
	 * @param userName - used to authenticat user credentials
	 * @param Password - used to authenticat user credentials
	 * @param Name - full name string
	 */
	public User(int id,String userName,String Password,String Name)
	{
		this.id = id;
		
		if (userName==null) 
			this.userName="";
		else 
			this.userName=userName;
		
		if (Password==null)
			this.password="";
		else 
			this.password=Password;
		
		if (Name==null)
			this.name="";
		else 
			this.name=Name;
	}
	
	/**
	 * Copy Constructor
	 * @param u - the User we want to copy
	 */
	public User(User u) {
		this.id = u.getID();
		this.userName=new String(u.getUserName());
		this.password=new String(u.getPassword());
		this.name=new String(u.getName());
	}

	/**
	 * this function returns the Users Username
	 * @return userName String
	 */
	public String getUserName() {
		return this.userName;
	}
	
	/**
	 * this function returns the users Password as a String
	 * @return the String password of the this user.
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * This function will return the full name as a string of this user
	 * @return full name as a string of this user
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * returns user id
	 * @return int of user id
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Override toString function of this object Student
	 * will print "ID: <id>  UserName:<userName> FullName:<name>
	 */
	@Override public String toString() {
		return new String("ID:"+id+" UserName:"+userName+" FullName:"+name);
	}
	
	/**
	 * This function Overrides the Object equals function and checks if the information in the object is identical 
	 */
	@Override public boolean equals(Object obj) {
		if (this==obj) return true;
		if(obj instanceof User) {
			User user = (User) obj;
			if(user.getID()!=id || 
					!user.getName().equals(name) || 
					!user.getPassword().equals(password) ||
					!user.getUserName().equals(userName))
				return false;
		} else return false;
		return true;
	}
	
	@Override public int hashCode() {
		int result = 17;
        result = 31 * result + id;
        result = 31 * result + userName.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + password.hashCode();
        return result;
	}
	
}

