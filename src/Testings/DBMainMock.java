package Testings;

import java.util.HashMap;

import SQLTools.IDBMain;
import logic.*;

public class DBMainMock implements IDBMain {

	HashMap<String, User> existingUsers;
	
	public DBMainMock() {
		existingUsers = new HashMap<>();
		existingUsers.put("Principal_test",
				new Principal(999,"Principal_test","1234","Principal Test"));
		existingUsers.put("Teacher_test",
				new Teacher(888,"Teacher_test","1234","Teacher Test"));
		existingUsers.put("Student_test",
				new Student(777,"Student_test","1234","Student Test"));
	}

	@Override
	public User getUserByUserName(String userName) {
		return existingUsers.get(userName);
	}

}
