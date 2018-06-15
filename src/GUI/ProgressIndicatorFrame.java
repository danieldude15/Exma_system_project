package GUI;

import Controllers.ControlledScreen;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import logic.Globals;

public class ProgressIndicatorFrame implements ControlledScreen {

	@FXML ProgressIndicator pin;

	@Override public void runOnScreenChange() {		
		pin.setLayoutX(Globals.mainContainer.getWidth()/2);
		pin.setLayoutY(Globals.mainContainer.getHeight()/2);

	}

}
