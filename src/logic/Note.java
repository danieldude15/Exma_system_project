package logic;

public class Note {
	private Globals.Type noteType;
	private String note;
	
	public Note(Globals.Type t, String n) {
		noteType=t;
		note=n;
	}
	
	public Globals.Type getType()
	{
		return noteType;
	}
	
	public String getNote()
	{
		return note;
	}
	public void setNote(String n)
	{
		note=n;
	}
}
