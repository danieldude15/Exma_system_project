package logic;

import java.io.Serializable;
import java.util.ArrayList;


public class Field implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5727608394594462958L;
	private int fID;
	private String fName;
	
	
	public Field(int fID, String fName) {
		super();
		this.fID = fID;
		this.fName = fName;
	}
	
	/**
	 * copy constructior
	 * @param Field object
	 */
	public Field(Field f) {
		super();
		fID=f.getID();
		fName=f.getName();
	}

	public String getName() {
		return fName;
	}
	
	public int getID() {
		return fID;
	}
	
	/**
	 * This function will return all courses in this field
	 * @return ArrayList of courses in field
	 */
	public ArrayList<Course> getCoursesInField(){
		return null;
	}
	
	/**
	 * this function will return all Teachers in this field
	 * @return ArrayList of Teachers in field
	 */
	public ArrayList<Teacher> getTeachersInField(){
		return null;
	}

	@Override
	public String toString() {
		if(getID()<0) return new String(String.format("%s", getName()));
		return new String(String.format("%02d - %s", getID() , getName()));
	}
	
	public String fieldIdToSdting() {
		return new String(String.format("%02d", getID()));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Field) {
			Field field = (Field) obj;
			if (field.getID()==getID() && field.getName().equals(getName())) return true;
		}
		return false;
	}
 
	
}
