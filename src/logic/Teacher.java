package logic;

public class Teacher extends User{
	
	/**
	 * Serializable id give for client server communication
	 */
	private static final long serialVersionUID = 4127432154155188904L;

	/**
	 * Constructor made to build new User class uses the super constructor
	 * @param id - the unique id of the user
	 * @param userName - used to authenticat user credentials
	 * @param Password - used to authenticat user credentials
	 * @param Name - full name string
	 */
	public Teacher(int id,String userName, String Password, String Name) {
		super(id,userName, Password, Name);
	}
	
	/**
	 * copy constructor
	 * @param Teacher to copy
	 */
	public Teacher(Teacher t) {
		super(t);
	}
	
	public Teacher(User u) {
		super(u);
	}
}
