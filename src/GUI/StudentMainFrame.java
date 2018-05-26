package GUI;

import java.awt.event.ActionEvent;
import java.io.IOException;

import Controllers.ControlledScreen;
import logic.Globals;
import logic.iMessage;
import ocsf.client.ClientGlobals;

public class StudentMainFrame implements ControlledScreen {

	
	
	@Override
	public void runOnScreenChange() {
		// TODO Auto-generated method stub
		
	}
	
	public void TakeExamButtonPressed(ActionEvent event)
	{
		
		
	}
	
	public void ViewExamButtonPressed(ActionEvent event)
	{
		
	}
	
	public void LogoutButtonPressed(ActionEvent event)
	{
		
		try {
			if (ClientGlobals.client!=null)
				ClientGlobals.client.sendToServer(new iMessage("logout",ClientGlobals.client.getUser()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Globals.mainContainer.setScreen(ClientGlobals.LogInID);
	}

	
}
