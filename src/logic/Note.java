package logic;

public class Note {
	private Globals.Type noteType;
	private String note;
	
	public Note(Globals.Type t, String n)/*Constructor/*/
	{
		noteType=t;
		note=n;
	}
	public Note(Note n)/*Copy constructor/*/
	{
		noteType=n.noteType;
		note=n.note;
	}
	
	public Globals.Type getType()
	{
		/*Type getter/*/
		return noteType;
	}
	
	public String getNote()
	{
		/*Note context getter/*/
		return note;
	}
	public void setNote(String n)
	{
		/*Note context setter/*/
		note=n;
	}
}
