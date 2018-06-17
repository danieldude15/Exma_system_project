package logic;

public class Student extends User {
	
	/**
	 * Serializable id give for client server communication
	 */
	private static final long serialVersionUID = 3331751756088934090L;
	
	/**
	 * Constructor made to build new User class, uses super constructor
	 * @param id - the unique id of the user
	 * @param userName - used to authenticat user credentials
	 * @param Password - used to authenticat user credentials
	 * @param Name - full name string
	 */
	public Student(int id,String userName, String Password, String Name) {
		super(id,userName, Password, Name);
	}
	
	/**
	 * copy constructor
	 * @param student
	 */
	public Student(Student s) {
		super(s);
	}
	
	public Student(User u) {
		super(u);
	}

}
