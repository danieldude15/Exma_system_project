package logic;


/**
 * This is the ActiveExam Entitiy that holds information on active exams 
 * @author Group-12
 *
 */
public class ActiveExam  extends Exam {

	/**
	 * Serializable id give for client server communication
	 */
	private static final long serialVersionUID = 4561592102347244137L;
	/**
	 * active exam code is a 4 characted code
	 */
	private String code;
	/**
	 * type of active Exam , computerized of manual
	 */
	private int type;
	/**
	 * date that the exam was activated 
	 */
	private String dateActivated;
	/**
	 * the teacher who activated this exam
	 */
	private Teacher activator;
	
	/**
	 * active Exam cosntructor
	 * @param code - the activeExam code (4 char)
	 * @param type - the type of exam. {computerized or manual}
	 * @param dayActivated - the date thei exam was activated
	 * @param activator - the teacher who activated this exam
	 * @param e - the exam that is active
	 */
	public ActiveExam(String code,int type,String dayActivated,Exam e,Teacher activator)
	{
		super(e);
		this.code=code;
		this.type=type;
		this.dateActivated=dayActivated;
		this.activator=activator;
	}
	
	/**
	 *  copy constructor
	 * @param e is the Active Exam we want to copy
	 */
	public ActiveExam(ActiveExam e)/*Copy constructor/*/
	{
		super(e);
		code=e.getCode();
		type=e.getType();
		dateActivated=e.getDate();
		activator=new Teacher(e.getActivator());
	}
	
	/**
	 * 
	 * @return true for active exam and false for non-active exams
	 */
	public Teacher getActivator() {
		return activator;
	}

	/**
	 * getDate
	 * @return the date when this exam was activated
	 */
	public String getDate() {
		return dateActivated;
	}

	/**
	 * getCode
	 * @return the 4 character code identifier of this active exam
	 */
	public String getCode() {
		return this.code;
	}
	
	/**
	 * getType
	 * @return the type of this exam. either computerized or manual
	 */
	public int getType() {
		return this.type;
	}


	/**
	 * this function should get time change requests from database in case there are any
	 * currently returns null need to implement
	 * @return TimeChangeRequest
	 */
	public TimeChangeRequest getTimeChangeRequest() {
		/*Request for time change request setter/*/
		return null;
	}

	/**
	 * overriding toString method. 
	 * will print the active exam information for listViews
	 */
	@Override public String toString() {
		String examType;
		if (type==1) examType = "Computerized";
		else examType = "Manual";
		return new String(String.format("Activated On: %s\n Code: %s\n ID: %s\n Type: %s", getDate(),getCode(),examIdToString() ,examType));
	}

	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31*result + super.hashCode();
		result = 31*result + getCode().hashCode();
		result = 31*result + getActivator().hashCode();
		return result;
	}

	/**
	 * This function Overrides the Object equals function and checks if the information in the object is identical 
	 */
	@Override
	public boolean equals(Object obj) {
		if(this==obj)return true;
		if(obj instanceof ActiveExam) {
			ActiveExam a = (ActiveExam) obj;
			if (!a.getCode().equals(code) || a.getType()!=type) return false;
			if (a.getDate()!=null && !a.getDate().equals(dateActivated)) return false;
			if (!a.getActivator().equals(activator)) return false;
			return super.equals(a);
		}
		return false;
	}
	
	

	
	
}
