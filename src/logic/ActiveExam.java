package logic;

import java.io.Serializable;
import java.sql.Date;

@SuppressWarnings("serial")
public class ActiveExam implements Serializable{

	private String code;
	private int type;
	private String dateActivated;
	private Teacher activator;
	private Exam exam;
	
	public ActiveExam(String code,int type,String dayActivated,Exam e,Teacher activator)/*Constructor/*/
	{
		this.code=code;
		this.type=type;
		this.dateActivated=dayActivated;
		this.activator=activator;
		this.exam=e;
		
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
		
	}
	
	/**
	 * this function returns if an ActiveExam is currently active or no
	 * @return true for active exam and false for non-active exams
	 */

	private Teacher getActivator() {
		return activator;
	}

	private String getDate() {
		return dateActivated;
	}

	public String getCode() {
		/*Code getter/*/
		return this.code;
	}
	public int getType() {
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
