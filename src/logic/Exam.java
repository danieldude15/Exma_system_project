package logic;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * this class is the Exam entity and is reposible of holding all Exam information.
 * @author Group-12
 *
 */
public class Exam implements Serializable{
	/**
	 * Serializable id give for client server communication
	 */
	private static final long serialVersionUID = -2379808552858049135L;
	/**
	 * exam id
	 */
	private int ID;
	/*
	 * the course of witch this exam is in.
	 */
	private Course course;
	/**
	 * the time allocated for the students to complete this exam
	 */
	private int Duration;
	/**
	 * the auther of this exam (Teacher)
	 */
	private Teacher Author;
	/**
	 * the arrayList of questions in this exam
	 */
	private ArrayList<QuestionInExam> questionsInExam;

	/**
	 * constructor to build this object
	 * @param iD - id of solved exam used to build the exam super class
	 * @param course - the course of this solved exam used to build the exam super class
	 * @param duration - the duration time decided by the teacher initially to solve this exam
	 * 						used to build the exam super class
	 * @param author - the creater of this exam
	 * @param questionsInExam - the arraylist of questions in this exam
	 */
	public Exam(int iD, Course course, int duration, Teacher author, ArrayList<QuestionInExam> questionsInExam) {
		super();
		ID = iD;
		this.course = course;
		Duration = duration;
		Author = author;
		this.questionsInExam = questionsInExam;
	}

	/**
	 * copy constructor
	 * @param exam - the exam we want to copy
	 */
	@SuppressWarnings("unchecked")
	public Exam(Exam exam) {
		super();
		ID = exam.getID();
		course = new Course(exam.getCourse());
		Duration = exam.getDuration();
		Author = new Teacher(exam.getAuthor());
		questionsInExam=(ArrayList<QuestionInExam>) exam.getQuestionsInExam().clone();		
	}

	/**
	 * getID
	 * @return the id of this exam
	 */
	public int getID() {
		return ID;
	}

	/**
	 * getCourse
	 * @return Course of this Exam
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * getField
	 * @return the Field of this Exam
	 */
	public Field getField() {
		return course.getField();
	}

	/**
	 * getDuration
	 * @return the duration time of this exam
	 */
	public int getDuration() {
		return Duration;
	}

	/**
	 * getAutehr
	 * @return the Taecher who wrote this exam
	 */
	public Teacher getAuthor() {
		return Author;
	}

	/**
	 * getQuestionsInExam
	 * @return the arraylist of question in this exam
	 */
	public ArrayList<QuestionInExam> getQuestionsInExam() {
		return questionsInExam;
	}
	
	/**
	 * getExam is a way to copy this exam and return the new copy as a returned value
	 * @return the new exam copy
	 */
	public Exam getExam() {
		return new Exam(this);
	}
	
	/**
	 * returns this exams full ID to string 
	 * exam full id is build of fieldID(2) coursID(2) and ExamID(2)
	 * so for example if this exam id is 1 and course id is 5 and field id is 7
	 * the full id that will be returned will be 070501
	 * @return the full string exam id
	 */
	public String examIdToString() {
		return new String(String.format("%02d%02d%02d", course.getField().getID(),course.getId(),ID));
	}

	/**
	 * convert back from a full string exam id to an array of ints 
	 * @param examid string to convert to ints
	 * @return array of 3 ints. index[0]=fieldid , [1]=courseid, [2]=examid.
	 */
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

	/**
	 * static method used to convert any exam,course,field ids to a string quickly
	 * @param examid - the exam id
	 * @param courseID - the course id
	 * @param fieldID - the field id
	 * @return the string of full exam ID <fieldid><courseid><examid> (2 chars each)
	 */
	public static String examIdToString(int examid, int courseID, int fieldID) {
		return new String(String.format("%02d%02d%02d", fieldID,courseID,examid));
	}

	/**
	 * check if this exam is in a course
	 * @param course to check with
	 * @return true if this exam is in the course otherwise returns false
	 */
	public boolean isInCourse(Course course) {
		return course.equals(getCourse());
	}

}
