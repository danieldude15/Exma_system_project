package logic;

import java.util.ArrayList;


public class Teacher extends User{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4127432154155188904L;

	public Teacher(int id,String userName, String Password, String Name) {
		super(id,userName, Password, Name);
	}
	
	/**
	 * copy constructor
	 * @param Teacher to copy
	 */
	public Teacher(Teacher t) {
		super(t.getID(),new String(t.getUserName()),new String(t.getPassword()),new String(t.getName()));
	}

	/**
	 * get Dields
	 * @return all Fields that this teacher is associated with
	 */
	public ArrayList<Field> getFiels() {
		return null;
	}
	
	/**
	 *  get Questions
	 * @return all Questions written by this teacher
	 */
	public ArrayList<Question> getQuestions() {
		return null;
	}

	/**
	 * get Exams
	 * @return all Exams written by this Teacher
	 */
	public ArrayList<Exam> getExams() {
		return null;
	}
	
	/**
	 * check if this teacher is in a field
	 * @param field to check if teacher is part of
	 * @return true if he is in this field otherwise false
	 */
	public boolean fieldExists(Field f) {
		String fieldName = f.getName();
		for(Field field:getFiels()) {
			if(fieldName.equals(field.getName())) return true;
		}
		return false;
	}
	
	/**
	 * checks if a question is wrotten by this teacher
	 * @param question to check
	 * @return true if question is written by this teacher otherwise false
	 */
	public boolean questionExists(Question q) {
		for(Question question:getQuestions()) {
			if (question.equals(q)) return true;
		}
		return false;
	}
}
