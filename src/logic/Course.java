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
	 * this will check if a student is in this course
	 * @param StudentUsername
	 * @return Student object if exists otherwise returns null
	 */
	public Student StudentExist(String StudentUsername)
	{
		/*When principle wants to check if some student is on course/
		for(int i=0;i<students.size();i++)
		{
			if(students.get(i).getUserName()==StudentUsername)
				return students.get(i);
		}
		*/
		return null;
	}
	
	/**
	 * check if a question exists in this course
	 * @param QuestionId is an int representing question id
	 * @return Question if it exists otherwise returns null
	 */
	public Question QuestionExist(int QuestionId)
	{
		/*When principle wants to check if some question is on course/
		for(int i=0;i<questions.size();i++)
		{
			if(questions.get(i).getID()==QuestionId)
				return questions.get(i);
		}
		*/
		return null;
	}
	
	
}
