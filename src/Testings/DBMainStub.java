package Testings;

import java.util.HashMap;

import SQLTools.IDBMain;
import logic.*;
/**
 * This class is for breaking the dependency in our database(for test cases). 
 */
public class DBMainStub implements IDBMain {

	HashMap<String, User> existingUsers;
	
	public DBMainStub() {
		existingUsers = new HashMap<>();
		existingUsers.put("Principal_test",
				new Principal(999,"Principal_test","1234","Principal Test"));
		existingUsers.put("Teacher_test",
				new Teacher(888,"Teacher_test","1234","Teacher Test"));
		existingUsers.put("Student_test",
				new Student(777,"Student_test","1234","Student Test"));
	}
	/**
	 * Instead of searching if the user is on database, the method returns the user if he is on existingUsers HashMap, otherwise return null.
	 */
	@Override
	public User getUserByUserName(String userName) {
		return existingUsers.get(userName);
	}

}
