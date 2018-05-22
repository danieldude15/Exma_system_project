package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import logic.*;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;
import ocsf.client.iMessage;

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
				Object o = msg.getObj();
				fields = new ArrayList<Field>();
				if(o instanceof ArrayList) {
					ArrayList<Field> TeacherFields = (ArrayList<Field>) o;
					for (Field f: TeacherFields) {
						Field field = new Field(f);
						fields.add(field);
					}
				}
				client.cleanMsg();
				return fields;
			} catch (IOException e) {
				ClientGlobals.handleIOException();
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
		
	}
}
