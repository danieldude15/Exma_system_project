package logic;

import java.util.ArrayList;

public class Principle extends User {

	private ArrayList<TimeChangeRequest> timeChangeList;
	
	public Principle(String userName, String Password, String Name,ArrayList<TimeChangeRequest> TimeChangeList) {
		super(userName, Password, Name);
		this.setTimeChangeList(new ArrayList<TimeChangeRequest>());
		this.setTimeChangeList(TimeChangeList);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<TimeChangeRequest> getTimeChangeList() {
		return timeChangeList;
	}

	public void setTimeChangeList(ArrayList<TimeChangeRequest> timeChangeList) {
		this.timeChangeList = timeChangeList;
	}
	
	

}
