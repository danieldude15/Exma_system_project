package Testings;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Controllers.UserController;
import GUI.ScreensController;
import logic.Globals;
import logic.User;
import logic.iMessage;

public class LoginTestingV4 {

	static AESClientStub client;
	static AESServerStub server;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Globals.mainContainer = new ScreensController();

        client = new AESClientStub();
        server = new AESServerStub(client);
        client.setMockServer(server);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	/**
	 * Login principal test succeeded.
	 */
	@Test
    public void loginPrincipleSuccessfulTest() {

        String username = "Principal_test";
        String password = "1234";
        iMessage expected = new iMessage("Principle",
        		new User(999,"Principal_test","1234","Principal Test"));

        iMessage message = UserController.login(username,password,client);

        if (message != null) {
            assertEquals(expected.toString(),message.toString());
        }
    }
	/**
	 * Login teacher test succeeded.
	 */
    @Test
    public void loginTeacherSuccessfulTest() {

        String username = "Teacher_test";
        String password = "1234";
        iMessage expected = new iMessage("Teacher",
        		new User(888,"Teacher_test","1234","Teacher Test"));

        iMessage message = UserController.login(username,password,client);

        if (message != null) {
            assertEquals(expected.toString(),message.toString());
        }
    }
    /**
     * Login student test succeeded.
     */
    @Test
    public void loginStudentSuccessfulTest() {

        String username = "Student_test";
        String password = "1234";
        iMessage expected = new iMessage("Student",
        		new User(777,"Student_test","1234","Student Test"));

        iMessage message = UserController.login(username,password,client);

        if (message != null) {
            assertEquals(expected.toString(),message.toString());
        }
    }
    /**
     * Login principle test has been failed because of an incorrect password.
     */
    @Test
    public void loginPrinciplePasswordIncorrectTest(){

        String username = "Principal_test";
        String password = "123";
        iMessage expected = new iMessage("failedAuth",null);

        iMessage message = UserController.login(username,password,client);

        if (message != null) {
            assertEquals(expected.toString(),message.toString());
        }
    }
    /**
     * Login principle test has been failed because of an incorrect username(user does not exist).
     */
    @Test
    public void loginPrincipleUserNameIncorrectTest(){

        String username = "Principal_";
        String password = "1234";
        iMessage expected = new iMessage("failedAuth",null);

        iMessage message = UserController.login(username,password,client);

        if (message != null) {
            assertEquals(expected.toString(),message.toString());
        }
    }
    /**
     * Login in succeed on first try, but has been failed on second try.
     */
    @Test
    public void loginPrincipleAgainFailsTest() {

        String username = "Principal_test";
        String password = "1234";
        iMessage expected = new iMessage("AlreadyLoggedIn",null);

        UserController.login(username,password,client);
        iMessage messageFailed = UserController.login(username,password,client);

        if (messageFailed != null) {
            assertEquals(expected.toString(),messageFailed.toString());
        }
    }
    /**
     * Login failed because that there are an empty fields on username and password textfields.
     */
    @Test
    public void loginEmptyFieldsTest() {

        String username = "";
        String password = "";
        iMessage expected = new iMessage("failedAuth",null);

        iMessage messageFailed = UserController.login(username,password,client);

        if (messageFailed != null) {
            assertEquals(expected.toString(),messageFailed.toString());
        }
    }
	
	

}
