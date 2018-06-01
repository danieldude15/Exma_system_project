package logic;

import java.io.Serializable;


public class Principle extends User implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8749030854308314525L;

	public Principle(int id,String userName, String Password, String Name) /*Constructor/*/
	{
		super(id,userName, Password, Name);
	}
	
	/**
	 * copy constructor;
	 * @param p
	 */
	public Principle(Principle p)/*Copy constructor/*/ 
	{
		super(p.getID(),new String(p.getUserName()),new String(p.getPassword()),new String(p.getName()));
	}
	
}
