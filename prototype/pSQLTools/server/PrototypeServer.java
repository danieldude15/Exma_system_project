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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
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
  PreparedStatement updateAnswer;
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
	  System.out.println("Server recieved <msg> from <client>: <"+ msg + "> from <" + client+">");
	  if (msg.getClass().equals(String.class)) {
		  String command = (String)msg;
		  if(command.equals("GetQuestions")) {
	  		try {
	  			questions = this.getQuestions();
	  			for (Question q:questions)
					  client.sendToClient(q);
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
				  this.updateCorrectAnswer(qid,answerIndex);
				  client.sendToClient("Done");
				  return;
			  } catch (Exception e1) {
				  System.out.println(e1.getClass());
				  e1.printStackTrace();
			  }
		  }
	  }
  }
  
	private Vector<Question> getQuestions() throws SQLException {
		DBMain sqlcon = new DBMain();
		Statement stmt = sqlcon.getConn().createStatement();
		ResultSet uprs = stmt.executeQuery("SELECT * FROM Questions");
		Vector<Question> questions = new Vector<Question>();
		
		while(uprs.next())
 		{
			 // Print out the values for debug
			 //System.out.println(uprs.getInt(1)+"  " +uprs.getString(2)+"  " +uprs.getString(3)+"  " +uprs.getString(4)+"  " +uprs.getString(5)+"  " +uprs.getString(6)+"  " +uprs.getString(7)+"  " +uprs.getString(8));
			 String[] answers = new String[]{uprs.getString(4),uprs.getString(5),uprs.getString(6),uprs.getString(7)};
			 questions.add(new Question(uprs.getInt(1),uprs.getInt(2),uprs.getString(3),answers,Integer.parseInt(uprs.getString(8))));
		} 
		return questions;
	}
	
	public void updateCorrectAnswer(int qid, int index) throws SQLException {
		DBMain sqlcon = new DBMain();
		updateAnswer = sqlcon.getConn().prepareStatement("UPDATE questions SET correctindex = ? WHERE idquestions = ?");
		updateAnswer.setInt(1, index);
		updateAnswer.setInt(2, qid);
		updateAnswer.executeUpdate();
	}
}
