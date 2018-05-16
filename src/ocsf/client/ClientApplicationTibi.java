package ocsf.client;

import java.io.IOException;

import Controllers.ScreensController;
import Controllers.pScreensController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pClient.PrototypeClientApp;
import pGUI.pClientGlobals;

public class ClientApplicationTibi extends Application {

	public static String TeacherMainID = "TeacherMain";
	public static String TeacherMainPath = "TeacherMain.fxml";
	public static ScreensController mainContainer = new ScreensController();
	
	public static void main(String args[]) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		if (!ClientApplicationTibi.mainContainer.loadScreen(ClientApplicationTibi.TeacherMainID, ClientApplicationTibi.TeacherMainPath)) {
        	System.out.println("failed to load "+ ClientApplicationTibi.TeacherMainID);
        	return;
        }
		mainContainer.setScreen(ClientApplicationTibi.TeacherMainID);
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(closeUpdate ->
	    {
	        System.exit(0);
	    });
        primaryStage.show();
	}

}
