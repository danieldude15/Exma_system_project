package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import logic.*;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;

@SuppressWarnings("unchecked")
public class CourseFieldController {
	
	public static ArrayList<Field> getTeacherFields(Teacher t) {
		AESClient client = ClientGlobals.client;
		ArrayList<Field> fields;
		if(client.isConnected()) {
			iMessage msg= new iMessage("getTeachersFields",t);
			try {
				client.sendToServer(msg);
				client.waitForResponse();
				Object o = ClientGlobals.client.getMsg().getObj();
				fields = new ArrayList<Field>();
				ArrayList<Field> TeacherFields = (ArrayList<Field>) o;
				for (Field f: TeacherFields) {
					Field field = new Field(f);
					fields.add(field);
				}
				client.cleanMsg();
				return fields;
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
		
	}

	public static ArrayList<Course> getFieldsCourses(ArrayList<Field> f) {
		AESClient client = ClientGlobals.client;
		ArrayList<Course> courses;
		if(client.isConnected()) {
			iMessage msg= new iMessage("getFieldsCourses",f);
			try {
				client.sendToServer(msg);
				client.waitForResponse();
				Object o = client.getMsg().getObj();
				courses = new ArrayList<Course>();
				if(o instanceof ArrayList) {
					ArrayList<Course> TeacherCourses = (ArrayList<Course>) o;
					for (Course c: TeacherCourses) {
						Course course = new Course(c);
						courses.add(course);
					}
				}
				client.cleanMsg();
				return courses;
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
		
	}
}
