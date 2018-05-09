// This file contains material supporting section 10.9 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com

/*
 * SimpleClient.java   2001-02-08
 *
 * Copyright (c) 2001 Robert Laganiere and Timothy C. Lethbridge.
 * All Rights Reserved.
 *
 */
package pSQLTools.client;

import java.awt.List;
import java.io.IOException;
import java.util.Vector;

import javafx.stage.Stage;

import java.awt.Color;
import ocsf.client.*;
import pGUI.SelectQuestionController;
import pLogic.Question;

/**
* The <code> SimpleClient </code> class is a simple subclass
* of the <code> ocsf.server.AbstractClient </code> class.
* It allows testing of the functionalities offered by the
* OCSF framework. The <code> java.awt.List </code> instance
* is used to display informative messages.
* This list is
* pink when the connection has been closed, red
* when an exception is received,
* and green when connected to the server.
*
* @author Dr. Robert Lagani&egrave;re
* @version February 2001
* @see ocsf.server.AbstractServer
*/
public class PrototypeClient extends AbstractClient
{
	public Vector<Question> questions = new Vector<Question>();
  public PrototypeClient(String host, int port) {
		super(host, port);
		// TODO Auto-generated constructor stub
	}


  /**
   * Handles a message sent from the server to this client.
   *
   * @param msg   the message sent.
   */
  protected void handleMessageFromServer(Object msg)
  {
    System.out.println("Client got from server: "+msg);
    if(msg.getClass().equals(Question.class)) {
    	questions.add((Question) msg);
    }
    if(msg.getClass().equals(String.class) && ((String)msg).equals("DONE")) {
    	try {
			this.closeConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
  }
}
