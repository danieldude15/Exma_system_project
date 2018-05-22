package logic;
import java.util.ArrayList;
import java.util.Vector;
public class Exam 
{
 private int ID;
 private int Duration;
 private Teacher Author;
 private ArrayList<QuestionInExam> questionsInExam;
	
 
 public Exam(int id,int duration,Teacher authorid)
 {
	 setID(id);
	 setDuration(duration);
	 setAuther(authorid);
 }

	public Exam(Exam exam) {
		ID = exam.ID;
		Author = exam.Author;
		
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
	return this.Author;
}


public void setAuther(Teacher auther) {
	this.Author = auther;
}


public ArrayList<QuestionInExam> getQuestions() {
	return this.questionsInExam;
}


public void AddQuestionToExam(QuestionInExam q) {
	/*Add new question to exam/*/
	int PointsInExam=0;
	for(int i=0;i<questionsInExam.size();i++)
	{
		PointsInExam+=questionsInExam.get(i).getPointsValue();
	}
	if(PointsInExam+q.getPointsValue()<=100)
		this.questionsInExam.add(q);
}


}
