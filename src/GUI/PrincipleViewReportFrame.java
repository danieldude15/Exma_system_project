package GUI;

import java.util.ArrayList;
import java.util.HashMap;

import Controllers.ControlledScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import logic.*;
import ocsf.client.ClientGlobals;

@SuppressWarnings("rawtypes")
public class PrincipleViewReportFrame implements ControlledScreen {
	
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
	@FXML ListView<String> leftListView;
	@FXML BarChart<String, Integer> devBarChart;
	@FXML CategoryAxis xAxis;
	@FXML NumberAxis yAxis;
	
	public enum type {
		STUDENT,TEACHER,COURSE,EXAM
	}
	private type windowType; 
	private Student student =null;
	private Teacher teacher =null;
	private Course course=null;
	private ExamReport examReport=null;
	
	@Override public void runOnScreenChange() {
        Globals.primaryStage.setHeight(620);
        Globals.primaryStage.setWidth(840);
		hideAllLabels();
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
	
	private void hideAllLabels() {
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
	}

	@FXML public void backToReports(ActionEvent event) {
		Globals.mainContainer.setScreen(ClientGlobals.PrincipalReportsID);
	}
	
	private void setupExamReportView() {
		if(examReport==null) {
			backToReports(null);
			return;
		}
		//update left Listview with examReports Solved Exams
		ArrayList<String> solvedList = new ArrayList<>();
		for(SolvedExam se:examReport.getSolvedExams()) {
			solvedList.add("Student:(" +se.getStudent().getID()+")"+se.getStudent().getName()+" Grade:"+se.getScore());
		}
		ObservableList<String> list = FXCollections.observableArrayList(solvedList);
		leftListView.setItems(list);
		
		averageLabel.setText(Float.toString(examReport.getAvg()));
		Median.setText(Integer.toString(examReport.getMedian()));
		
		updateBarChart(examReport.getDeviation());
		
	}
	@SuppressWarnings("unchecked")
	private void updateBarChart(HashMap<Integer, Integer> dev) {
        xAxis.setLabel("Score Range");       
        yAxis.setLabel("precentage");
 
        XYChart.Series[] series = new XYChart.Series[10];
        for(int i=0;i<10;i++) {
        	series[i] = new XYChart.Series();
        	String name = "" + (i*10+10);//dev.get(i)
        	series[i].getData().add(new XYChart.Data(name, i*i+i*1+2));
        	devBarChart.getData().add(series[i]);
        } 
	}

	private void setupCourseView() {
		// TODO Auto-generated method stub
		
	}
	private void setupTeacherView() {
		// TODO Auto-generated method stub
		
	}
	private void setupStudentView() {
		// TODO Auto-generated method stub
		
	}
	public type getWindowType() {
		return windowType;
	}
	public void setWindowType(type windowType) {
		this.windowType = windowType;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public void setExamReport(ExamReport examReport) {
		this.examReport = examReport;
	}
	
	

}
