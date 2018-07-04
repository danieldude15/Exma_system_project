package logic;

import java.io.Serializable;


public class Principal extends User implements Serializable{

	
	/**
	 * Serializable id give for client server communication
	 */
	private static final long serialVersionUID = 8749030854308314525L;

	/**
	 * Constructor made to build new User class
	 * @param id - the unique id of the user
	 * @param userName - used to authenticat user credentials
	 * @param Password - used to authenticat user credentials
	 * @param Name - full name string
	 */
	public Principal(int id,String userName, String Password, String Name) {
		super(id,userName, Password, Name);
	}
	
	/**
	 * copy constructor;
	 * @param p - the principle we want to copy
	 */
	public Principal(Principal p) {
		super(p.getID(),new String(p.getUserName()),new String(p.getPassword()),new String(p.getName()));
	}
	
}
