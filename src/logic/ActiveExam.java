package logic;

import java.sql.Date;
import java.text.SimpleDateFormat;

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
	private Date dateActivated;
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
	public ActiveExam(String code,int type,Date dayActivated,Exam e,Teacher activator)
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
		code= new String(e.getCode());
		type=e.getType();
		dateActivated=new Date(e.getDate().getTime());
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
	public Date getDate() {
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
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yy  (HH:mm)");
		return new String(String.format("Course: %s\nActivated On: %s\nLocks On: %s\nType: %s", getCourse().getName(),DATE_FORMAT.format(getDate()),DATE_FORMAT.format(new Date(getDate().getTime()+getDuration()*60000)) ,examType));
	}

	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31*result + super.hashCode();
		if (getCode()!=null)
			result = 31*result + getCode().hashCode();
		if (getActivator()!=null)
			result = 31*result + getActivator().hashCode();
		return result;
	}

	/**
	 * This function Overrides the Object equals function and checks if the information in the object is identical 
	 */
	@Override
	public boolean equals(Object obj) {
		if(this==obj)return true;
		if(obj != null && obj.getClass() == getClass()) {
			ActiveExam a = (ActiveExam) obj;
			if(!super.equals(a)) return false;
			if (a.getCode()==getCode() && a.getDate()==getDate() && a.getActivator()==getActivator() && a.getType()==getType()) return true;
			if (a.getCode()!=null && !a.getCode().equals(code)) return false;
			if (a.getDate()!=null && !a.getCode().equals(code)) return false;
			if (a.getActivator()!=null && !a.getActivator().equals(activator)) return false;
			if (a.getType()!=type) return false;
			return true;
		}
		return false;
	}
	
	

	
	
}
