package prototype;

public class Question {
	private int ID;
	private int AutherID;
	private String question;
	private String[] Answers;
	private int CorrectAnswerIndex;
	
	public Question(int id, int autherid, String question, String[] answers) {
		ID=id;
		AutherID=autherid;
		this.question = new String(question);
		Answers = answers;
		CorrectAnswerIndex=-1;
	}

	public boolean updateQuestion(int qid, int correctIndex) {
		
	}

	public String getQuestionString() {
		// TODO Auto-generated method stub
		return question;
	}
}
