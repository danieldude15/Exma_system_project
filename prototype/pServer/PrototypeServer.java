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
package pServer;

import java.sql.SQLException;
import java.util.Vector;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import pLogic.pQuestion;
import pSQLTools.pDBMain;

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
	pDBMain sqlcon;
	public Vector<pQuestion> questions;
  public PrototypeServer(int port) {
		super(port);
		try {
			sqlcon = new pDBMain();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
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
	  System.out.println("Server recieved <msg> from <client>: <"+ msg + "> from <" + client+">");
	  if (msg.getClass().equals(String.class)) {
		  String command = (String)msg;
		  if(command.equals("GetQuestions")) {
	  		try {
	  			questions = sqlcon.getQuestions();
	  			for (pQuestion q:questions) {
					  client.sendToClient(q);
					  System.out.println("sendint client " + client + " " + q);
	  			}
	  			client.sendToClient("Done");
			  return;
			} catch (Exception e1) {
				System.out.println(e1.getClass());
				e1.printStackTrace();
			}
		  } else if (command.contains("UpdateQuestionAnswer")) {
			  String[] updateQuestionCommand = command.split(" ");
			  int qid = Integer.parseInt(updateQuestionCommand[1]);
			  int answerIndex = Integer.parseInt(updateQuestionCommand[2]);
			  try {
				  sqlcon.updateCorrectAnswer(qid,answerIndex);
				  client.sendToClient("Done");
				  return;
			  } catch (Exception e1) {
				  System.out.println(e1.getClass());
				  e1.printStackTrace();
			  }
		  }
	  }
  }
  
}
