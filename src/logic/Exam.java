package logic;
import java.util.ArrayList;
import java.util.Vector;
public class Exam {
	private int ID;
	private int courseid;
	private int fieldid;
	private int Duration;
	private Teacher Author;
	private ArrayList<Question> questionsInExam;

	
	public Exam(int iD, int courseid, int fieldid, int duration, Teacher author, ArrayList<Question> questionsInExam) {
		super();
		ID = iD;
		this.courseid = courseid;
		this.fieldid = fieldid;
		Duration = duration;
		Author = author;
		this.questionsInExam = questionsInExam;
	}

	@SuppressWarnings("unchecked")
	public Exam(Exam exam) {
		super();
		ID = exam.getID();
		courseid = exam.getCourseid();
		fieldid = exam.getFieldid();
		Duration = exam.getDuration();
		Author = new Teacher(exam.getAuthor());
		questionsInExam=(ArrayList<Question>) exam.getQuestionsInExam().clone();		
	}
	
	public static Vector<Exam> clone(Vector<Exam> exam) {
		Vector<Exam> ExamRes = new Vector<Exam>();
		for (Exam e:exam) {
			 ExamRes.add(new Exam(e));
		}
		return  ExamRes;
	}

	public int getID() {
		return ID;
	}

	public int getCourseid() {
		return courseid;
	}

	public int getFieldid() {
		return fieldid;
	}

	public int getDuration() {
		return Duration;
	}

	public Teacher getAuthor() {
		return Author;
	}

	public ArrayList<Question> getQuestionsInExam() {
		return questionsInExam;
	}
	
	public Exam getExam() {
		return new Exam(this);
	}

}
