package GUI;

import Controllers.ControlledScreen;
import Controllers.ReportController;
import Controllers.SolvedExamController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import logic.*;
import ocsf.client.ClientGlobals;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages The screen where a Reports is viewed by a teacher or the principal.
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class ViewReportFrame implements ControlledScreen {

	/* Enums Start */

	/**
	 * Defines a types of users.
	 */
	enum user {
		Principle , Teacher
	}

	/**
	 * Defines a types of reports.
	 */
	public enum type {
		STUDENT,TEACHER,COURSE,EXAM
	}

	/* Enums End */

	/* Fields Start */
	
	@FXML Label infoTitle1;
	@FXML Label infoTitle2;
	@FXML Label infoTitle3;
	@FXML Label infoTitle4;
	@FXML Label infoTitle5;
	@FXML Label infoTitle6;
	@FXML Label infoTitle7;
	@FXML Label infoTitle8;
	@FXML Label infoValue1;
	@FXML Label infoValue2;
	@FXML Label infoValue3;
	@FXML Label infoValue4;
	@FXML Label infoValue5;
	@FXML Label infoValue6;
	@FXML Label infoValue7;
	@FXML Label infoValue8;
	@FXML Label averageLabel;
	@FXML Label Median;
	@FXML Label leftListViewLabel;	
	@FXML Label cheatLabel;
	@FXML ListView<String> leftListView;
	@FXML ListView<String> secondLeftListView;
	@FXML BarChart<String, Integer> devBarChart;
	@FXML CategoryAxis xAxis;
	@FXML NumberAxis yAxis;
	
	private user me;
	private type windowType; 
	private Student student =null;
	private Teacher teacher =null;
	private Course course=null;
	private ExamReport examReport=null;

	/* Fields End */

	/* Constructors Start */

	/**
	 * Sets the 'View Report' screen.
	 * Updating all the lists present in the screen before it is displayed.
	 * Generating
	 */
	@Override public void runOnScreenChange() {
		leftListView.addEventFilter(MouseEvent.MOUSE_PRESSED, Event::consume);			// overrides the click on items in the listview
		secondLeftListView.addEventFilter(MouseEvent.MOUSE_PRESSED, Event::consume);
		hideAll();
		switch(windowType) {
		case STUDENT:
			setupStudentView();
			break;
		case TEACHER:
			setupTeacherView();
			break;
		case COURSE:
			setupCourseView();
			break;
		case EXAM:
			setupExamReportView();
			break;
		}

	}

	/**
	 * Hides all Elements in this view.
	 */
	private void hideAll() {
		infoTitle1.setVisible(false);
		infoTitle2.setVisible(false);
		infoTitle3.setVisible(false);
		infoTitle4.setVisible(false);
		infoTitle5.setVisible(false);
		infoTitle6.setVisible(false);
		infoTitle7.setVisible(false);
		infoTitle8.setVisible(false);
		infoValue1.setVisible(false);
		infoValue2.setVisible(false);
		infoValue3.setVisible(false);
		infoValue4.setVisible(false);
		infoValue5.setVisible(false);
		infoValue6.setVisible(false);
		infoValue7.setVisible(false);
		infoValue8.setVisible(false);
		secondLeftListView.setVisible(false);
		cheatLabel.setVisible(false);
	}

	/* Constructors End */

	/* Getters and Setters Start */

	/**
	 * Returns the type of Report displayed.
	 * @return The type of the window.
	 */
	public type getWindowType() {
		return windowType;
	}

	/**
	 * Sets the type of report to be displayed.
	 * @param windowType - The type of window to be shown.
	 */
	public void setWindowType(type windowType) {
		this.windowType = windowType;
	}

	/**
	 * Sets the Student, report of which will be displayed.
	 * @param student - Student to be displayed.
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * Sets the Teacher, report of which will be displayed.
	 * @param teacher - Teacher to be displayed
	 */
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	/**
	 * Sets the Course, report of which will be displayed.
	 * @param course - Course to be displayed.
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

	/**
	 * Sets the examReport to be displayed.
	 * @param examReport - ExamReport to be displayed.
	 */
	public void setExamReport(ExamReport examReport) {
		this.examReport = examReport;
	}

	/**
	 * Returns the user currently viewing the data.
	 * @return me - User logged in.
	 */
	public user getMe() {
		return me;
	}

	/**
	 * Sets the user currently viewing the data.
	 * @param me - User logged in.
	 */
	public void setMe(user me) {
		this.me = me;
	}

	/* Getters and Setters End */

	/* Methods Start */

	/**
	 * Returns to View Reports screen of Principal, or Teacher Check Exam screen of Teacher.
	 * @param event - Click on 'Back' button.
	 */
	@FXML public void backToReports(ActionEvent event) {
		if(getMe()==user.Principle)
			Globals.mainContainer.setScreen(ClientGlobals.PrincipalReportsID);
		else 
			Globals.mainContainer.setScreen(ClientGlobals.TeacherCheckExamsID);
	}

	/**
	 * Sets the screen up according to what is required from a report on an Exam.
	 */
	private void setupExamReportView() {
		if(examReport==null) {
			backToReports(null);
			return;
		}
		cheatLabel.setVisible(true);
		secondLeftListView.setVisible(true);
		//update left Listview with examReports Solved Exams
		ArrayList<String> listViewOfStrings = new ArrayList<>();
		for(SolvedExam se:examReport.getSolvedExams()) {
			listViewOfStrings.add("Student:(" +se.getStudent().getID()+")"+se.getStudent().getName()+" Grade:"+se.getScore());
		}
		ObservableList<String> list = FXCollections.observableArrayList(listViewOfStrings);
		leftListView.setItems(list);
		
		HashMap<Student, Integer> cheaters = examReport.getM_cheatingStudents();
		listViewOfStrings.clear();
		for(Student cheater: cheaters.keySet()) {
			listViewOfStrings.add(cheater.getName()+" - "+cheaters.get(cheater)+"% Matched");
		}
		list = FXCollections.observableArrayList(listViewOfStrings);
		secondLeftListView.setItems(list);
		
		averageLabel.setText(Float.toString(examReport.getAvg()));
		Median.setText(Integer.toString(examReport.getMedian()));
		
		updateBarChart(examReport.getDeviation(),"Solved Exams Deviation");
		
		infoTitle1.setText("Exam Course:");
		infoTitle1.setVisible(true);
		infoTitle2.setText("Date Activated:");
		infoTitle2.setVisible(true);
		infoTitle3.setText("Exam Duration:");
		infoTitle3.setVisible(true);
		infoTitle4.setText("Participants:");
		infoTitle4.setVisible(true);
		infoValue1.setText(examReport.getCourse().getName());
		infoValue1.setVisible(true);
		infoValue2.setText(examReport.getDate().toString());
		infoValue2.setVisible(true);
		infoValue3.setText(examReport.getDurationToString());
		infoValue3.setVisible(true);
		infoValue4.setText(examReport.getParticipatingStudent()+" Students");
		infoValue4.setVisible(true);
		
		leftListViewLabel.setText("Students Participated:");
		
	}

	/**
	 * Sets the screen up according to what is required from a report on a Course.
	 */
	private void setupCourseView() {
		if(course==null) {
			backToReports(null);
			return;
		}
		ArrayList<ExamReport> courseExams = new ArrayList<>();
		int examCounter=0;
		float avgSum=0;
		ArrayList<Integer> medianCalc = new ArrayList<>();
		//update left Listview with examReports Solved Exams
		ArrayList<String> reportist = new ArrayList<>();
		for(ExamReport er:ReportController.getAllExamReport()) {
			if(er.getCourse().equals(course)) {
				reportist.add(er.toString());
				courseExams.add(er);
				examCounter++;
				avgSum+=er.getAvg();
				for(SolvedExam se: er.getSolvedExams())
					medianCalc.add(se.getScore());
			}
		}
		ObservableList<String> list = FXCollections.observableArrayList(reportist);
		leftListView.setItems(list);
		float avg = avgSum/examCounter;
		int median = ExamReport.calcMedianFromInts(medianCalc);
		HashMap<Integer, Integer> dev = ExamReport.calcDeviationFromInts(medianCalc);
		averageLabel.setText(avg+"");
		Median.setText(median+"");
		
		updateBarChart(dev,"Course Exams Deviation");
		
		infoTitle1.setText("Course Name:");
		infoTitle1.setVisible(true);
		infoTitle2.setText("Course ID:");
		infoTitle2.setVisible(true);
		infoTitle3.setText("Field of Course:");
		infoTitle3.setVisible(true);
		infoTitle4.setText("Field ID:");
		infoTitle4.setVisible(true);
		infoValue1.setText(course.getName());
		infoValue1.setVisible(true);
		infoValue2.setText(course.courseIdToString());
		infoValue2.setVisible(true);
		infoValue3.setText(course.getField().getName());
		infoValue3.setVisible(true);
		infoValue4.setText(course.getField().getID()+"");
		infoValue4.setVisible(true);
		
		leftListViewLabel.setText("Exam History:");
	}

	/**
	 * Sets the screen up according to what is required from a report on an Teacher.
	 */
	private void setupTeacherView() {
		ArrayList<ExamReport> allExams = ReportController.getAllExamReport();
		ArrayList<ExamReport> teachersExams = new ArrayList<>();
		float avgSum=0;
		int examCounter=0;
		ArrayList<Integer> medianCalc = new ArrayList<>();
		for(ExamReport er: allExams) {
			if(er.getActivator().equals(teacher)) {
				teachersExams.add(er);
				examCounter++;
				avgSum+=er.getAvg();
				medianCalc.add((int) er.getAvg());
			}
		}
		float avg = avgSum/examCounter;
		int median = ExamReport.calcMedianFromInts(medianCalc);
		HashMap<Integer, Integer> dev = ExamReport.calcDeviationFromInts(medianCalc);
		
		ArrayList<String> leftListStrings = new ArrayList<>();
		for(ExamReport se:teachersExams) {
			leftListStrings.add(se.toString());
		}
		ObservableList<String> list = FXCollections.observableArrayList(leftListStrings);
		leftListView.setItems(list);
		
		averageLabel.setText(Float.toString(avg));
		Median.setText(Integer.toString(median));
		
		updateBarChart(dev,"Teachers Exams Deviation");
		
		infoTitle1.setText("teacher's Name:");
		infoTitle1.setVisible(true);
		infoTitle2.setText("teacher's ID:");
		infoTitle2.setVisible(true);
		infoTitle3.setText("teacher's UserName:");
		infoTitle3.setVisible(true);
		infoValue1.setText(teacher.getName());
		infoValue1.setVisible(true);
		infoValue2.setText(teacher.getID()+"");
		infoValue2.setVisible(true);
		infoValue3.setText(teacher.getUserName());
		infoValue3.setVisible(true);
		leftListViewLabel.setText("Exam History:");		
	}

	/**
	 * Sets the screen up according to what is required from a report on an Student.
	 */
	private void setupStudentView() {
		ArrayList<SolvedExam> studentExams = SolvedExamController.getSolvedExamsByUser(student);
		float avg = ExamReport.calcAvg(studentExams);
		int median = ExamReport.calcMedian(studentExams);
		HashMap<Integer, Integer> dev = ExamReport.calcDeviation(studentExams);
		
		ArrayList<String> leftListStrings = new ArrayList<>();
		for(SolvedExam se:studentExams) {
			leftListStrings.add(se.toString());
		}
		ObservableList<String> list = FXCollections.observableArrayList(leftListStrings);
		leftListView.setItems(list);
		
		averageLabel.setText(Float.toString(avg));
		Median.setText(Integer.toString(median));
		
		updateBarChart(dev,"Students Exams Deviation");
		
		infoTitle1.setText("Student's Name:");
		infoTitle1.setVisible(true);
		infoTitle2.setText("Student's ID:");
		infoTitle2.setVisible(true);
		infoTitle3.setText("Student's UserName:");
		infoTitle3.setVisible(true);
		infoValue1.setText(student.getName());
		infoValue1.setVisible(true);
		infoValue2.setText(student.getID()+"");
		infoValue2.setVisible(true);
		infoValue3.setText(student.getUserName());
		infoValue3.setVisible(true);
		leftListViewLabel.setText("Exam History:");
	}

	/**
	 * Updates the BarChart according to the report's data.
	 * @param dev - data to be shown in the chart
	 * @param seriesName - name of the series to be shown.
	 */
	private void updateBarChart(HashMap<Integer, Integer> dev, String seriesName) {
        xAxis.setLabel("Score Range");    
        xAxis.setId("whiteLabel");
        xAxis.setStyle("-fx-fill-text:white;");
        devBarChart.getData().clear();
        XYChart.Series series = new XYChart.Series();
        for(int i=0;i<10;i++) {
        	String name = (i*10+1) + "-" + (i*10+10);
        	if (i==0)
        		name = "0-10";
        	series.getData().add(new XYChart.Data(name, dev.get(i)));
        	
        } 
        series.setName(seriesName);
        devBarChart.getData().add(series);
	}

	/* Methods End */
}
