package logic;

import Controllers.CourseFieldController;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this entity Field is the Subject of any group of courses for example mathematics or physiscs.
 * this hold information on the field and is used in many other objects as a variable.
 * @author Group-12
 *
 */
public class Field implements Serializable{
	
	/**
	 * Serializable id give for client server communication
	 */
	private static final long serialVersionUID = 5727608394594462958L;
	/**
	 * field id int value
	 */
	private int fID;
	/**
	 * field name string
	 */
	private String fName;
	
	/**
	 * class constructor
	 * @param fID - field id int
	 * @param fName - field name string
	 */
	public Field(int fID, String fName) {
		super();
		this.fID = fID;
		this.fName = fName;
	}
	
	/**
	 * copy constructior
	 * @param f object
	 */
	public Field(Field f) {
		super();
		fID = f.getID();
		fName = new String(f.getName());
	}

	/**
	 * getName
	 * @return the name of this field
	 */
	public String getName() {
		return fName;
	}
	
	/**
	 * getID
	 * @return the id of this field
	 */
	public int getID() {
		return fID;
	}
	
	/**
	 * This function will return all courses in this field
	 * @return ArrayList of courses in field
	 */
	public ArrayList<Course> getCoursesInField(){
		return CourseFieldController.getFieldCourses(this);
	}
	
	/**
	 * this function will return all Teachers in this field
	 * @return ArrayList of Teachers in field
	 */
	public ArrayList<Teacher> getTeachersInField(){
		return CourseFieldController.getFieldTeachers(this);
	}

	/**
	 * override of toString method for Field class
	 */
	@Override public String toString() {
		if(getID()<0) return new String(String.format("%s", getName()));
		return new String(String.format("%02d - %s", getID() , getName()));
	}
	
	/**
	 * method to convert field id to a string always max length of 2 chars
	 * @return the string value of the id
	 */
	public String fieldIdToString() {
		return new String(String.format("%02d", getID()));
	}

	/**
	 * override the equals object to accuratly difierentiate berween field objects
	 */
	@Override public boolean equals(Object obj) {
		if(this==obj)return true;
		if (obj instanceof Field) {
			Field field = (Field) obj;
			return field.getID() == getID() && field.getName().equals(getName());
		}
		return false;
	}
 
	/**
	 * overriding hashcode method to use HashMap in case of need
	 */
	@Override public int hashCode() {
        int result = 17;
        result = 31 * result + fID;
        result = 31 * result + fName.hashCode();
        return result;
    }
}
