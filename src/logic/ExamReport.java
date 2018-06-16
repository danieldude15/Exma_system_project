package logic;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * This is the Completed Exam entity that hold information on exams that already been completed 
 * by all students in course or locked by the teacher that initiated the active exam.
 * @author Group-12
 *
 */
public class ExamReport extends ActiveExam {
	
	/**
	 *  Serializable id give for client server communication
	 */
	private static final long serialVersionUID = 4657010598883384610L;
	
	public static final int cheatersMaxSimilarMistakeLimit = 1;
	/**
	 * ArrayList of all solved exams of this completed exam
	 */
	private ArrayList<SolvedExam> solvedExams = null;
	/**
	 * 
	 */
	private Integer participatingStudent;
	/**
	 * 
	 */
	private Integer submittedStudents;
	/**
	 * 
	 */
	private Integer notInTimeStudents;
	/**
	 * 
	 */
	private Date timeLocked;
	/**
	 * median value of all solvedExams
	 */
	int median;
	/**
	 * average of all solvedExams
	 */
	float avg;
	/**
	 * deviation value of the report for every 10 points in the solvedExams scores
	 */
	HashMap<Integer, Integer> deviation;
	/**
	 * the hashMap of all potential cheaters in this exam
	 */
	HashMap<Student,Integer> m_cheatingStudents;
		
	/**
	 * this constructor should be called when creating a new exam report before pushing it into the database
	 * because this constructor uses the solved exams to calculate all the average and median and deviation also 
	 * find cheating students and then the object can get pushed into the database
	 * @param code - the activeExam code (4 char)
	 * @param type - the type of exam. {computerized or manual}
	 * @param dayActivated - the date the exam was activated
	 * @param activator - the teacher who activated this exam
	 * @param solvedExams - the ArrayList of all solved exams 
	 * @param notInTimeStudents - the amount of student that did not submit their exam on time and got 0 for it
	 * @param submittedStudents - the amount of student that submitted the exam in time 
	 * @param participatingStudent - the amount of student who participated in the exam 
	 * @param e - this exam of witch this report is made for
	 * @param timeLocked -the time when the exam was locked 
	 * @param dateinitiated -the date that the exam was activated/initiatez 
	 */
	public ExamReport(String code, int type, Date dayActivated, Exam e, Teacher activator,
			ArrayList<SolvedExam> solvedExams, Integer participatingStudent, 
			Integer submittedStudents,Integer notInTimeStudents, Date timeLocked) {
		super(code, type, dayActivated, e, activator);
		this.solvedExams = solvedExams;
		this.participatingStudent = participatingStudent;
		this.submittedStudents = submittedStudents;
		this.notInTimeStudents = notInTimeStudents;
		this.timeLocked = timeLocked;
		this.median = calcMedian(solvedExams);
		this.avg = calcAvg(solvedExams);
		this.deviation = calcDeviation(solvedExams);
		this.m_cheatingStudents = findCheaters(solvedExams);
	}

	/**
	 * getSolvedExams
	 * @return the arraylist of solveExams
	 */
	public ArrayList<SolvedExam> getSolvedExams() {
		return solvedExams;
	}


	public Integer getParticipatingStudent() {
		return participatingStudent;
	}

	public Integer getSubmittedStudents() {
		return submittedStudents;
	}
	
	/**
	 * getMedian
	 * @return the meadian of this report
	 */
	public int getMedian() {
		return median;
	}

	/**
	 * getAvg
	 * @return the avarage ofthis exam
	 */
	public float getAvg() {
		return avg;
	}

	
	/**
	 * get all potential cheaters in this exam
	 * @return the hashmap of potential cheates
	 */
	public HashMap<Student, Integer> getM_cheatingStudents() {
		return m_cheatingStudents;
	}
	

	public Integer getNotInTimeStudents() {
		return notInTimeStudents;
	}

	
	public Date getTimeLocked() {
		return timeLocked;
	}

	
	public HashMap<Integer, Integer> getDeviation() {
		return deviation;
	}

	/**
	 * this function will be responsible for check witch students cheated and return the hashmap of these students
	 * this is a private method used in the constructor if examReport to genereate these potential cheating students 
	 * @param solvedExams 
	 * @return hashmap of potential cheating students
	 */
	public static HashMap<Student, Integer> findCheaters(ArrayList<SolvedExam> solvedExams) {
		HashMap<Student, Integer> cheaters = new HashMap<>();
		for(int i=0;i<solvedExams.size();i++) {
			for (int j=i+1;j<solvedExams.size();j++) {
				//first we create a hashmap of the uncorrect answers of student i
				HashMap<QuestionInExam, Integer> studentiAnswers = solvedExams.get(i).getStudentsAnswers();
				HashMap<QuestionInExam, Integer> studentiMistakes = new HashMap<>();
				for(QuestionInExam qie: studentiAnswers.keySet()) {
					if(studentiAnswers.get(qie)!=qie.getCorrectAnswerIndex()) {
						studentiMistakes.put(qie, studentiAnswers.get(qie));
					}
				}
				int similarCounter=0;
				HashMap<QuestionInExam, Integer> studentjAnswers = solvedExams.get(j).getStudentsAnswers();
				for(QuestionInExam qie: studentiMistakes.keySet()) {
					if(studentjAnswers.get(qie)!=null && studentjAnswers.get(qie)==studentiMistakes.get(qie)) {
						similarCounter++;
					}
				}
				if(similarCounter>=cheatersMaxSimilarMistakeLimit) {
					Student cheateri = solvedExams.get(i).getStudent();
					Integer cheatCount = cheaters.get(cheateri);
					if((cheatCount!=null && cheatCount<similarCounter) ||
							cheatCount==null) {
						cheaters.put(cheateri, similarCounter);
					}
					Student cheaterj = solvedExams.get(j).getStudent();
					cheatCount = cheaters.get(cheaterj);
					if((cheatCount!=null && cheatCount<similarCounter) ||
							cheatCount==null) {
						cheaters.put(cheaterj, similarCounter);
					}
				}
			}
		}
		return cheaters;
	}
	

	public static int calcMedian(ArrayList<SolvedExam> solvedExams2) {
		ArrayList<Integer> scores = new ArrayList<>();
		for(SolvedExam se: solvedExams2) {
			scores.add(se.getScore());
		}
		return calcMedianFromInts(scores);
	}
	
	public static int calcMedianFromInts(ArrayList<Integer> values) {
		Collections.sort(values);
		if (values.size()>0)
			return values.get(values.size()/2);
		else 
			return 0;
	}

	public static float calcAvg(ArrayList<SolvedExam> solvedExams2) {
		int sum =0;
		for(SolvedExam se: solvedExams2) {
			sum+=se.getScore();
		}
		if (solvedExams2.size()==0)
			return 0;
		else 
			return (float) ((1.0*sum)/solvedExams2.size());
	}

	public static HashMap<Integer, Integer> calcDeviation(ArrayList<SolvedExam> solvedExams2) {
		ArrayList<Integer> values = new ArrayList<>();
		for(SolvedExam se: solvedExams2) {
			values.add(se.getScore());
		}
		return calcDeviationFromInts(values);
	}
	
	public static HashMap<Integer, Integer> calcDeviationFromInts(ArrayList<Integer> values) {
		Integer[] devValues = new Integer[] {0,0,0,0,0,0,0,0,0,0};
		for(Integer num: values) {
			if (num>90)
				devValues[9]++;
			else if (num>80)
				devValues[8]++;
			else if (num>70)
				devValues[7]++;
			else if (num>60)
				devValues[6]++;
			else if (num>50)
				devValues[5]++;
			else if (num>40)
				devValues[4]++;
			else if (num>30)
				devValues[3]++;
			else if (num>20)
				devValues[2]++;
			else if (num>10)
				devValues[1]++;
			else 
				devValues[0]++;
		}
		HashMap<Integer, Integer> deviation = new HashMap<>();
		for(int i=0; i<10 ;i++) {
			deviation.put(i, devValues[i]);
		}
		return deviation;
	}

	
	/**
	 * overriding toString method
	 */
	@Override public String toString() {
		return new String(String.format("Course: %s \nActivated On: %s \nAverage: %.2f", getCourse().getName(),getDate(),getAvg()));
	}

	public String getDurationToString() {
		int minutes = getDuration()%60;
		int hours = getDuration()/60;
		if (hours!=0 && minutes!=0) {
			return hours + " Hours and " + minutes +" Minutes";
		} else if (hours!=0 && minutes==0) {
			return hours + " Hours";
		} else if (hours==0 && minutes!=0) {
			return minutes + " Minutes";
		} else {
			return "0 Minutes";
		}
	}

	
}
