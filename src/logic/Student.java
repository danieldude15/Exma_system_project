package logic;

public class Student extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3331751756088934090L;
	
	public Student(int id,String userName, String Password, String Name) {
		super(id,userName, Password, Name);
	}
	
	/**
	 * copy constructor
	 * @param student
	 */
	public Student(Student s) {
		super(s.getID(),new String(s.getUserName()),new String(s.getPassword()),new String(s.getName()));
	}

}
