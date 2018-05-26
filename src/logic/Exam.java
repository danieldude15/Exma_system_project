package logic;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;
@SuppressWarnings("serial")
public class Exam implements Serializable{
	private int ID;
	private Course course;
	private Field field;
	private int Duration;
	private Teacher Author;
	private ArrayList<Question> questionsInExam;

	
	public Exam(int iD, Course courseid, Field fieldid, int duration, Teacher author, ArrayList<Question> questionsInExam) {
		super();
		ID = iD;
		course = courseid;
		field = fieldid;
		Duration = duration;
		Author = author;
		this.questionsInExam = questionsInExam;
	}

	@SuppressWarnings("unchecked")
	public Exam(Exam exam) {
		super();
		ID = exam.getID();
		course = new Course(exam.getCourseid());
		field = new Field(exam.getFieldid());
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

	public Course getCourseid() {
		return course;
	}

	public Field getFieldid() {
		return field;
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
	
	public String examIdToString() {
		return new String(String.format("%02d%02d%02d", field.getID(),course.getId(),ID));
	}

}
