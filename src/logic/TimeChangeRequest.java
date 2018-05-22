package logic;

import java.sql.Time;

public class TimeChangeRequest {

	private int id;
	private Time newTime;
	private String reasonForTimeChange;
	private boolean status;//approved or not
	private ActiveExam activeExam;
	
	public TimeChangeRequest(int id,Time newTime,String reason,boolean status,ActiveExam activeExam)
	{
		this.id=id;
		this.newTime=newTime;
		this.reasonForTimeChange=reason;
		this.status=status;
		this.activeExam=activeExam;
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
}
