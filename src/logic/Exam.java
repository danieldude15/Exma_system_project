package logic;
import java.util.ArrayList;
import java.util.Vector;
public class Exam 
{
 private int ID;
 private int Duration;
 private Teacher Auther;
 private ArrayList<Question> Questions;
	
 
 public Exam(int id,int duration,Teacher autherid)
 {
 setID(id);
 setDuration(duration);
 setAuther(autherid);
 }

	public Exam(Exam exam) {
		ID = exam.ID;
		Auther = exam.Auther;
		
	}
	
	public static Vector<Exam> clone(Vector<Exam> exam) {
		Vector<Exam> ExamRes = new Vector<Exam>();
		for (Exam e:exam) {
			 ExamRes.add(new Exam(e));
		}
		return  ExamRes;
	}

public int getID() {
	return ID;
}


public void setID(int iD) {
	ID = iD;
}


public int getDuration() {
	return Duration;
}


public void setDuration(int duration) {
	Duration = duration;
}


public Teacher getAuther() {
	return Auther;
}


public void setAuther(Teacher auther) {
	Auther = auther;
}


public ArrayList<Question> getQuestions() {
	return Questions;
}


public void setQuestions(ArrayList<Question> questions) {
	Questions = questions;
}
}
