package logic;

public class ActiveExam {

	private int code;
	private String type;
	private String dateTime;
	private int studentTaking;
	private int zeroStudent;
	private Exam exam;
	private TimeChangeRequest timeChangeRequest; //Not in constructor, but only on setter if teacher wants to ask it
	
	public ActiveExam(int code,String type,String dateT,int takingS,int zeroS,Exam e)/*Constructor/*/
	{
		this.code=code;
		this.type=type;
		this.dateTime=dateT;
		this.setStudentTaking(takingS);
		this.setZeroStudent(zeroS);
		this.exam=e;
	}

	public ActiveExam(ActiveExam e)/*Copy constructor/*/
	{
		code=e.code;
		type=e.type;
		dateTime=e.dateTime;
		studentTaking=e.studentTaking;
		zeroStudent=e.zeroStudent;
		exam=e.exam;
		
	}
	public int getCode() {
		/*Code getter/*/
		return this.code;
	}
	public String getType() {
		/*Type getter/*/
		return this.type;
	}

	
	public int getStudentTaking() {
		/*Students who takes the exam getter/*/
		return this.studentTaking;
	}

	public void setStudentTaking(int studentTaking) {
		/*Students who takes the exam setter/*/
		this.studentTaking = studentTaking;
	}


	public int getZeroStudent() {
		/*Students who gets 0 on exam getter/*/
		return zeroStudent;
	}


	public void setZeroStudent(int zeroStudent) {
		/*Students who gets 0 on exam setter/*/
		this.zeroStudent = zeroStudent;
	}

	public Exam getExam()
	{
		/*Exam getter/*/
		return this.exam;
	}


	public TimeChangeRequest getTimeChangeRequest() {
		/*Request for time change request setter/*/
		return timeChangeRequest;
	}


	public void setTimeChangeRequest(TimeChangeRequest timeChangeRequest) {
		/*Time change request setter /*/
		this.timeChangeRequest = timeChangeRequest;
	}
	
}
