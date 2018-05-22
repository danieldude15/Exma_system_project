package logic;

import java.util.ArrayList;

public class Field {
	
	private int fID;
	private String fName;
	private ArrayList<Course> courses;
	private ArrayList<Teacher> teachers;
	
	public Field(Field f) {
		
	}

	public String getName() {
		return fName;
	}
	
	public int getID() {
		return fID;
	}
	
	public ArrayList<Course> getCoursesInField(){
		return courses;
	}
	
	public ArrayList<Teacher> getTeachersInField(){
		return teachers;
	}
 
}
