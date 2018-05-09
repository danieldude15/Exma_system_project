// This file contains material supporting section 10.9 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com

/*
 * SimpleServer.java   2001-02-08
 *
 * Copyright (c) 2000 Robert Laganiere and Timothy C. Lethbridge.
 * All Rights Reserved.
 *
 */
package pSQLTools.server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

import ocsf.server.*;
import pLogic.Question;
import pSQLTools.DBMain;

/**
* The <code> SimpleServer </code> class is a simple subclass
* of the <code> ocsf.server.AbstractServer </code> class.
* It allows testing of the functionalities offered by the
* OCSF framework. The <code> java.awt.List </code> instance
* is used to display informative messages. This list is red
* when the server is closed, yellow when the server is stopped
* and green when open.
*
* @author Dr. Robert Lagani&egrave;re
* @version February 2001
* @see ocsf.server.AbstractServer
*/
public class PrototypeServer extends AbstractServer
{
  public Vector<Question> questions;
  public PrototypeServer(int port) {
		super(port);
		// TODO Auto-generated constructor stub
	}

/**
   * Handles a command sent from one client to the server.
   *
   * @param msg   the message sent.
   * @param client the connection connected to the client that
   *  sent the message.
   */
  protected void handleMessageFromClient(Object msg, ConnectionToClient client)
  {
	  System.out.println("syso Server says: "+ msg + " " + client);
	  try {
		DBMain sqlcon = new DBMain();
		questions = sqlcon.getQuestions();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  try {
		  for (Question q:questions)
			  client.sendToClient(q);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}
