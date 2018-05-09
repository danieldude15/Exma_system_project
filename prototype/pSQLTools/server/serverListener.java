package pSQLTools.server;

import java.io.IOException;

public class serverListener {

	public static void main(String args[]) throws IOException {
		PrototypeServer server = new PrototypeServer(12345);
		server.listen();
	}
}
