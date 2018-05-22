package logic;

public class QuestionInExam {

	private int PointsValue;
	private Exam exam;
	private Question question;
	
	public QuestionInExam(int pointsValue,Exam e,Question q)/*Constructor/*/
	{
		this.PointsValue=pointsValue;
		this.exam=e;
		this.question=q;
	}

	public int getPointsValue() {
		/*Pointsvalue getter/*/
		return PointsValue;
	}

	public Exam getExam() {
		/*Exam getter/*/
		return exam;
	}


	public Question getQuestion() {
		/*Question getter/*/
		return question;
	}

	
}
