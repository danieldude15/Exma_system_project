package Controllers;

import java.io.IOException;

import logic.*;
import ocsf.client.ClientGlobals;

public class ActiveExamController {

	public static void getTeachersActiveExams(User user) {
		if (user instanceof Teacher) {
			iMessage msg = new iMessage("getTeachersActiveExams",user);
			try {
				ClientGlobals.client.sendToServer(msg);
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
			}
		} else {
			//show error alert
		}
		
	}

}
