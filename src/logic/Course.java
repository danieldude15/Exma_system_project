package logic;

import java.io.Serializable;

/**
 * this is the Course Entity that holds information on a course in the college
 * @author Group-12
 *
 */
public class Course implements Serializable{

	/**
	 * Serializable id give for client server communication
	 */
	private static final long serialVersionUID = 7414573568989175063L;
	/**
	 * the course id
	 */
	private int id;
	/**
	 * the course name
	 */
	private String name;
	/**
	 * the course's field it belongs to
	 */
	private Field field;
	
	/**
	 * object constructor will build a new Course
	 * @param id - course id
	 * @param name - name of course
	 * @param field - field of course
	 */
	public Course(int id,String name,Field field)
	{
		this.id=id;
		this.name=name;
		this.field=field;		
	}
	
	/**
	 * copy constructor
	 * @param course - the course we want to copy
	 */
	public Course(Course course) {
		name=course.getName();
		field=course.getField();
		id=course.getId();
	}
	
	/**
	 * getID
	 * @return the id of this course
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * getName
	 * @return the name of this course
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * getField
	 * @return the Field this Course belongs to
	 */
	public Field getField() {
		return this.field;
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
	
	/**
	 * couresIDtoString
	 * @return the complete course ID <fieldID><CourseID>
	 */
	public String courseIdToString() {
		return new String(String.format("%02d%02d", getField().getID(),getId()));
	}

	/**
	 * overriding the toString method for Courses
	 */
	@Override public String toString() {
		if(getField() != null) {
			return new String(String.format("%s - %s", courseIdToString() , getName()));
		} else {
			return new String(String.format("%s", getName()));
		}
	}
	
	@Override public boolean equals(Object c) {
		if(c==this) return true;
		if (c instanceof Course) {
			Course course = (Course) c;
			return course.getId()==getId() && course.getName().equals(getName()) && course.getField()!=null && course.getField().equals(getField());
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + id;
		if (name!=null) result = 31 * result + name.hashCode();
		if (field!=null) result = 31*result + field.hashCode();
		return result;
	}
	
	
	
	
}
