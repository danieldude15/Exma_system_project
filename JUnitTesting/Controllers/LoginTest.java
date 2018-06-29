package Controllers;

import GUI.ScreensController;
import logic.Globals;
import logic.User;
import logic.iMessage;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;
import ocsf.server.AESServer;
import ocsf.server.ServerGlobals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *  Creating a Test for Login process
 */
class LoginTest {

    @BeforeEach
    void setUp() {

        Globals.mainContainer = new ScreensController();

        AESServer server = new AESServer("localhost/aes", "aesUser","QxU&v&HMm0t&",5555);
        AESClient client = new AESClient("localhost",5555);

        ServerGlobals.server = server;
        ClientGlobals.client = client;

        try {
            server.listen();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            client.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        ClientGlobals.client.closeConnection();
        ServerGlobals.server.close();
    }

    @Test
    void loginPrincipleSuccessfulTest() {

        String username = "nathan";
        String password = "1234";
        iMessage expected = new iMessage("Principle",new User(307975052,"nathan","1234","nathan khutorskoy"));

        iMessage message = UserController.login(username,password);

        if (message != null) {
            assertEquals(expected.toString(),message.toString());
        }
    }

    @Test
    void loginTeacherSuccessfulTest() {

        String username = "daniel";
        String password = "tibi";
        iMessage expected = new iMessage("Teacher",new User(302218136,"daniel","tibi","Daniel Tibi"));

        iMessage message = UserController.login(username,password);

        if (message != null) {
            assertEquals(expected.toString(),message.toString());
        }
    }

    @Test
    void loginStudentSuccessfulTest() {

        String username = "itzik";
        String password = "1234";
        iMessage expected = new iMessage("Student",new User(305112732,"itzik","1234","itzik mizrahi"));

        iMessage message = UserController.login(username,password);

        if (message != null) {
            assertEquals(expected.toString(),message.toString());
        }
    }

    @Test
    void loginPrinciplePasswordIncorrectTest(){

        String username = "nathan";
        String password = "123";
        iMessage expected = new iMessage("failedAuth",null);

        iMessage message = UserController.login(username,password);

        if (message != null) {
            assertEquals(expected.toString(),message.toString());
        }
    }

    @Test
    void loginPrincipleUserNameIncorrectTest(){

        String username = "natha";
        String password = "1234";
        iMessage expected = new iMessage("failedAuth",null);

        iMessage message = UserController.login(username,password);

        if (message != null) {
            assertEquals(expected.toString(),message.toString());
        }
    }

    @Test
    void loginPrincipleAgainFailsTest() {

        String username = "nathan";
        String password = "1234";
        iMessage expected = new iMessage("AlreadyLoggedIn",null);

        UserController.login(username,password);
        iMessage messageFailed = UserController.login(username,password);

        if (messageFailed != null) {
            assertEquals(expected.toString(),messageFailed.toString());
        }
    }

    @Test
    void loginEmptyFieldsTest() {

        String username = "";
        String password = "";
        iMessage expected = new iMessage("failedAuth",null);

        UserController.login(username,password);
        iMessage messageFailed = UserController.login(username,password);

        if (messageFailed != null) {
            assertEquals(expected.toString(),messageFailed.toString());
        }
    }
}