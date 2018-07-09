package Testings;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controllers.UserController;
import GUI.ScreensController;
import logic.Globals;
import logic.User;
import logic.iMessage;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;
import ocsf.server.AESServer;
import ocsf.server.ServerGlobals;

class loginTest {

	static AESClientMock client;
	static AESServerMock server;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		//preventing null pointer exception when trying to change screens
        Globals.mainContainer = new ScreensController();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {}

	@BeforeEach
	void setUp() throws Exception {
		//for each test we want an independent client and server
		client = new AESClientMock();
        server = new AESServerMock(client);
        client.setMockServer(server);
	}

	@AfterEach
	void tearDown() throws Exception {}


    @Test
    void loginPrincipleSuccessfulTest() {

        String username = "Principal_test";
        String password = "1234";
        iMessage expected = new iMessage("Principle",
        		new User(999,"Principal_test","1234","Principal Test"));

        iMessage message = UserController.login(username,password,client);

        if (message != null) {
            assertEquals(expected.toString(),message.toString());
        }
    }

    @Test
    void loginTeacherSuccessfulTest() {

        String username = "Teacher_test";
        String password = "1234";
        iMessage expected = new iMessage("Teacher",
        		new User(888,"Teacher_test","1234","Teacher Test"));

        iMessage message = UserController.login(username,password,client);

        if (message != null) {
            assertEquals(expected.toString(),message.toString());
        }
    }

    @Test
    void loginStudentSuccessfulTest() {

        String username = "Student_test";
        String password = "1234";
        iMessage expected = new iMessage("Student",
        		new User(777,"Student_test","1234","Student Test"));

        iMessage message = UserController.login(username,password,client);

        if (message != null) {
            assertEquals(expected.toString(),message.toString());
        }
    }

    @Test
    void loginPrinciplePasswordIncorrectTest(){

        String username = "Principal_test";
        String password = "123";
        iMessage expected = new iMessage("failedAuth",null);

        iMessage message = UserController.login(username,password,client);

        if (message != null) {
            assertEquals(expected.toString(),message.toString());
        }
    }

    @Test
    void loginPrincipleUserNameIncorrectTest(){

        String username = "Principal_";
        String password = "1234";
        iMessage expected = new iMessage("failedAuth",null);

        iMessage message = UserController.login(username,password,client);

        if (message != null) {
            assertEquals(expected.toString(),message.toString());
        }
    }

    @Test
    void loginPrincipleAgainFailsTest() {

        String username = "Principal_test";
        String password = "1234";
        iMessage expected = new iMessage("AlreadyLoggedIn",null);

        UserController.login(username,password,client);
        iMessage messageFailed = UserController.login(username,password,client);

        if (messageFailed != null) {
            assertEquals(expected.toString(),messageFailed.toString());
        }
    }

    @Test
    void loginEmptyFieldsTest() {

        String username = "";
        String password = "";
        iMessage expected = new iMessage("failedAuth",null);

        iMessage messageFailed = UserController.login(username,password,client);

        if (messageFailed != null) {
            assertEquals(expected.toString(),messageFailed.toString());
        }
    }

}
