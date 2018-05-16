package pServer;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

public class PrototypeServerApp extends Application {

	public static void main(String args[]) throws IOException {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		pServerGlobals.server = new PrototypeServer(12345);
		try {
			pServerGlobals.server.listen();
			System.out.println("Server Listening.");
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}
