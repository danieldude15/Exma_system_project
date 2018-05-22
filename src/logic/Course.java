package logic;

import java.util.ArrayList;

public class Course {

	private int id;
	private String name;
	private Field field;
	private ArrayList<Question> questions;
	private ArrayList<Student> students;
	private ArrayList<Exam> exams;
	
	public Course(String name,Field field,ArrayList<Student> students)/*Constructor/*/
	{
		this.name=name;
		this.field=field;
		questions=new ArrayList<Question>();
		students=new ArrayList<Student>();
		this.students=students;
		exams=new ArrayList<Exam>();
		
	}
	
	public Course(Course c)/*Copy constructor/*/
	{
		name=c.name;
		field=c.field;
		questions=c.questions;
		students=c.students;
		exams=new ArrayList<Exam>();
		exams=c.exams;
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
	public ArrayList<Question> getQuestins()
	{
		/*Getter for all questions on course/*/
		return this.questions;
	}
	public void AddQuestion(Question q)
	{
		/*Questions setter(if Teacher create new question we update it on database and add it to the specific course/*/
		this.questions.add(q);
	}
	public ArrayList<Student> getStudents()
	{
		/*Getter for all students on course/*/
		return this.students;
	}
	
	public ArrayList<Exam> GetAllExams()
	{
		/*Getter for all exams in course/*/
		return this.exams;
	}
	
	public ArrayList<SolvedExam> GetAllSolvedExams()
	{
		/*Return all solved exams in course/*/
		ArrayList<SolvedExam> returnSolvedExamsList=new ArrayList<SolvedExam>();
		for(int i=0;i<exams.size();i++)
		{
			if(exams.get(i) instanceof SolvedExam)
				returnSolvedExamsList.add((SolvedExam) exams.get(i));
		}
		return returnSolvedExamsList;
	}
	
	public ArrayList<Exam> GetAllUnsolvedExams()
	{
		/*Return all unsolved exams in course/*/
		ArrayList<Exam> returnExamsList=new ArrayList<Exam>();
		for(int i=0;i<exams.size();i++)
		{
			if(!(exams.get(i) instanceof SolvedExam))
				returnExamsList.add(exams.get(i));
		}
		return returnExamsList;
	}
	
	public void AddExamToExamsList(Exam s)
	{
		/*Add new Solved Exam to the list of SolvedExams/*/
		this.exams.add(s);
	}
	
	public void AddSolvedExamToExamsList(SolvedExam s)
	{
		/*Add new Solved Exam to the list of SolvedExams/*/
		this.exams.add((SolvedExam)s);
	}
	public void AddNewQuestionToQuestionsList(Question q)
	{
		/*Add new question to the list of questions/*/
		this.questions.add(q);
	}
	
	public Student StudentExist(String StudentUsername)
	{
		/*When principle wants to check if some student is on course/*/
		for(int i=0;i<students.size();i++)
		{
			if(students.get(i).getUserName()==StudentUsername)
				return students.get(i);
		}
		return null;
	}
	public Question QuestionExist(int QuestionId)
	{
		/*When principle wants to check if some question is on course/*/
		for(int i=0;i<questions.size();i++)
		{
			if(questions.get(i).getID()==QuestionId)
				return questions.get(i);
		}
		return null;
	}
	
	
}
