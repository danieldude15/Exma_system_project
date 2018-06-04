package Controllers;

import java.io.IOException;
import java.util.ArrayList;


import logic.*;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;

@SuppressWarnings("unchecked")
public class CourseFieldController {
	
	/**
	 * this function will give us all the fields of a teacher.
	 * @param Teacher object of witch we want to know what fields he belongs to
	 * @return ArrayList of Fields
	 */
	public static ArrayList<Field> getTeacherFields(Teacher t) {
		if(t==null) return null;
		AESClient client = ClientGlobals.client;
		ArrayList<Field> fields;
		if(client.isConnected()) {
			iMessage msg= new iMessage("getTeachersFields",t);
			try {
				client.sendToServer(msg);
				Object o = client.getResponseFromServer().getObj();
				if(o==null) {
					return null;
				}
				fields = new ArrayList<Field>();
				ArrayList<Field> TeacherFields = (ArrayList<Field>) o;
				for (Field f: TeacherFields) {
					Field field = new Field(f);
					fields.add(field);
				}
				return fields;
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
		return null;
		
	}

	/**
	 * This function will get from database all courses that are part of any of the fields given in the arraylist
	 * @param ArrayList<Field> fields, the list of fields we want to get courses of
	 * @return ArrayList<Course> courses , an arraylist of all the courses in @param fields.
	 */
	public static ArrayList<Course> getFieldsCourses(ArrayList<Field> f) {
		if(f==null) return null;
		AESClient client = ClientGlobals.client;
		ArrayList<Course> courses;
		if(client.isConnected()) {
			iMessage msg= new iMessage("getFieldsCourses",f);
			try {
				client.sendToServer(msg);
				Object o = client.getResponseFromServer().getObj();
				courses = new ArrayList<Course>();
				if(o!=null && o instanceof ArrayList) {
					ArrayList<Object> TeacherCourses = (ArrayList<Object>) o;
					if (TeacherCourses.size()==0) return null;
					Object a = TeacherCourses.get(0);
					if(a instanceof Course) {
						ArrayList<Course> tc = (ArrayList<Course>)o;
						for (Course c: tc) {
							Course course = new Course(c);
							courses.add(course);
						}
						return courses;
					} else {
						System.out.println("Get Field Courses got " +o.getClass()+" objects instead of Course objects");
					}
				}
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
			}
		} 
		return null;
	}
	public static ArrayList<Course> getFieldCourses(Field f) {
		if(f==null) return null;
		AESClient client = ClientGlobals.client;
		ArrayList<Course> courses;
		if(client.isConnected()) {
			iMessage msg= new iMessage("getFieldCourses",f);
			try {
				client.sendToServer(msg);
				Object o = client.getResponseFromServer().getObj();
				courses = new ArrayList<Course>();
				if(o!=null && o instanceof ArrayList) {
					ArrayList<Object> TeacherCourses = (ArrayList<Object>) o;
					if (TeacherCourses.size()==0) return null;
					Object a = TeacherCourses.get(0);
					if(a instanceof Course) {
						ArrayList<Course> tc = (ArrayList<Course>)o;
						for (Course c: tc) {
							Course course = new Course(c);
							courses.add(course);
						}
						return courses;
					} else {
						System.out.println("Get Field Courses got " +o.getClass()+" objects instead of Course objects");
					}
				}
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
			}
		} 
		return null;
	}
}
