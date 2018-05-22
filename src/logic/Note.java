package logic;

public class Note {
	public enum Type {
		VISIBLE,HIDDEN;
	}
	private Type noteType;
	private String note;
	
	public Note(Type t, String n) {
		noteType=t;
		note=n;
	}
	
	public Type getType()
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
