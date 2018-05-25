package logic;

import java.sql.Date;

public class ActiveExam {

	private String code;
	private String type;
	private Date dateActivated;
	private Teacher activator;
	private Exam exam;
	private boolean active;
	
	public ActiveExam(String code,String type,Date dayActivated,Exam e,Teacher activator, boolean currentlyactive)/*Constructor/*/
	{
		this.code=code;
		this.type=type;
		this.dateActivated=dayActivated;
		this.activator=activator;
		this.exam=e;
		this.active=currentlyactive;
		
	}
	
	/**
	 *  copy constructor
	 * @param e is the Active Exam we want to copy
	 */
	public ActiveExam(ActiveExam e)/*Copy constructor/*/
	{
		code=e.getCode();
		type=e.getType();
		dateActivated=e.getDate();
		activator=new Teacher(e.getActivator());
		exam=new Exam(e.getExam());
		active=e.isActive();
		
	}
	
	/**
	 * this function returns if an ActiveExam is currently active or no
	 * @return true for active exam and false for non-active exams
	 */
	private boolean isActive() {
		return active;
	}

	private Teacher getActivator() {
		return activator;
	}

	private Date getDate() {
		return dateActivated;
	}

	public String getCode() {
		/*Code getter/*/
		return this.code;
	}
	public String getType() {
		/*Type getter/*/
		return this.type;
	}

	public Exam getExam()
	{
		/*Exam getter/*/
		return this.exam;
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

	
}
