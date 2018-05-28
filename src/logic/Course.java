package logic;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Course implements Serializable{

	private int id;
	private String name;
	private Field field;
	
	public Course(int id,String name,Field field)/*Constructor/*/
	{
		this.id=id;
		this.name=name;
		this.field=field;		
	}
	
	public Course(Course c)/*Copy constructor/*/
	{
		name=c.getName();
		field=c.getField();
		id=c.getId();
	}
	public int getId()
	{
		/*Id getter/*/
		return this.id;
	}
	public String getName()
	{
		/*Name getter/*/
		return this.name;
	}
	public Field getField()
	{
		/*Field getter/*/
		return this.field;
	}
	/**
	 * this function will get all questions in databse that belong to this course
	 * currently returns null needs implementation
	 * @return
	 */
	public ArrayList<Question> getQuestins()
	{
		return null;
	}
	
	/**
	 * this function will return all students that belong to this course
	 * @return ArrayList of Students that belong to course
	 */
	public ArrayList<Student> getStudents()
	{
		/*Getter for all students on course/*/
		return null;
	}
	
	/**
	 * this function will return all exams that belong to this course
	 * @return arraylist of exams in this course
	 */
	public ArrayList<Exam> GetAllExams()
	{
		return null;
	}
	/**
	 * returns all solved exams in this course
	 * @return array list of solved exams
	 */
	public ArrayList<SolvedExam> GetAllSolvedExams()
	{
		/*Return all solved exams in course/
		ArrayList<SolvedExam> returnSolvedExamsList=new ArrayList<SolvedExam>();
		for(int i=0;i<exams.size();i++)
		{
			if(exams.get(i) instanceof SolvedExam)
				returnSolvedExamsList.add((SolvedExam) exams.get(i));
		}
		return returnSolvedExamsList;
		*/
		return null;
	}
	
	/**
	 * inserts an exam to this course
	 * @param s the exam to be inserted
	 */
	public void insertExam(Exam s)
	{
		
	}
	
	/**
	 * this function will get a String containing the ID of a course and it will be translated to ints
	 * @param id
	 * @return an array of 2 int the first is fieldID second is CourseID
	 */
	public static int[] parseID(String id) {
		int[] res = {Integer.parseInt(id.substring(0, 2)),Integer.parseInt(id.substring(2,4))};
		return res;
	}

	@Override
	public String toString() {
		return new String(String.format("%s - %s", courseIdToString() , getName()));
	}
	
	@Override
	public boolean equals(Object c) {
		if (c instanceof Course) {
			Course course = (Course) c;
			if(course.getId()==getId() && course.getName().equals(getName()) && course.getField().equals(getField())) return true;
		}
		return false;
	}
	public String courseIdToString() {
		return new String(String.format("%02d%02d", getField().getID(),getId()));
	}
	
	
}
