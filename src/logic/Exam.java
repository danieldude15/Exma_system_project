package logic;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class Exam implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	private Course course;
	private int Duration;
	private Teacher Author;
	private ArrayList<QuestionInExam> questionsInExam;

	
	public Exam(int iD, Course course, int duration, Teacher author, ArrayList<QuestionInExam> questionsInExam) {
		super();
		ID = iD;
		this.course = course;
		Duration = duration;
		Author = author;
		this.questionsInExam = questionsInExam;
	}

	@SuppressWarnings("unchecked")
	public Exam(Exam exam) {
		super();
		ID = exam.getID();
		course = new Course(exam.getCourse());
		Duration = exam.getDuration();
		Author = new Teacher(exam.getAuthor());
		questionsInExam=(ArrayList<QuestionInExam>) exam.getQuestionsInExam().clone();		
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

	public Course getCourse() {
		return course;
	}

	public Field getFieldid() {
		return course.getField();
	}

	public int getDuration() {
		return Duration;
	}

	public Teacher getAuthor() {
		return Author;
	}

	public ArrayList<QuestionInExam> getQuestionsInExam() {
		return questionsInExam;
	}
	
	public Exam getExam() {
		return new Exam(this);
	}
	
	public String examIdToString() {
		return new String(String.format("%02d%02d%02d", course.getField().getID(),course.getId(),ID));
	}

	public static int[] parseId(String examid) {
		try {
			int[] result = {Integer.parseInt(examid.substring(0, 2)),Integer.parseInt(examid.substring(2,4)),Integer.parseInt(examid.substring(4,6))};
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			int[] res = {-1,-1,-1};
			return res;
		}
	}

	public static String examIdToString(int examid, int id2, int int1) {
		return new String(String.format("%02d%02d%02d", int1,id2,examid));
	}

}
