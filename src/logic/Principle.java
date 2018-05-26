package logic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Principle extends User implements Serializable{

	
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
