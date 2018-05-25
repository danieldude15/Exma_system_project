package logic;

import java.sql.Time;

public class TimeChangeRequest {

	private int id;
	private Time newTime;
	private String reasonForTimeChange;
	private boolean status;//approved or not
	private ActiveExam activeExam;
	private Teacher teacher;
	
	public TimeChangeRequest(int id,Time newTime,String reason,boolean status,ActiveExam activeExam,Teacher t)
	{
		this.id=id;
		this.newTime=newTime;
		this.reasonForTimeChange=reason;
		this.status=status;
		this.activeExam=activeExam;
		this.teacher=t;
	}
	
	/**
	 * copy constructor
	 * @param time change request to copy
	 */
	public TimeChangeRequest(TimeChangeRequest t)/*Copy constructor/*/
	{
		id=t.id;
		newTime=t.newTime;
		reasonForTimeChange=new String(t.reasonForTimeChange);
		status=t.status;
		activeExam=new ActiveExam(t.activeExam);
		teacher=new Teacher(t.teacher);
	}
	
	public int getId()
	{
		return this.id;
	}
	public Time getNewTime()
	{
		return this.newTime;
	}
	public String getReasonForTimeChange()
	{
		return this.reasonForTimeChange;
	}
	public boolean getStatus()
	{
		return this.status;
	}
	public ActiveExam getActiveExam()
	{
		return this.activeExam;
	}
	public Teacher getTeacher()
	{
		return this.teacher;
	}
}
