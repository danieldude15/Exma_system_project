package logic;

import java.util.ArrayList;

public class Course {

	private int id;
	private String name;
	private Field field;
	private ArrayList<Question> questions;
	private ArrayList<Student> students;
	
	public Course(String name,Field field,ArrayList<Question> questions,ArrayList<Student> students)
	{
		this.name=name;
		this.field=field;
		questions=new ArrayList<Question>();
		this.questions=questions;
		students=new ArrayList<Student>();
		this.students=students;
	}
	
	public int getId()
	{
		return this.id;
	}
	public String getName()
	{
		return this.name;
	}
	public void SetName(String name)
	{
		this.name=name;
	}
	public Field getField()
	{
		return this.field;
	}
	public ArrayList<Question> getQuestins()
	{
		return this.questions;
	}
	public void setQuestions(ArrayList<Question> questions)
	{
		this.questions=questions
	}
	public ArrayList<Student> getStudents()
	{
		return this.students;
	}
	public void setStudents(ArrayList<Student> students)
	{
		this.students=students;
	}
}
