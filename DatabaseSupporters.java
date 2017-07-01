import java.util.Iterator;
import java.util.LinkedList;

public class DatabaseSupporters {
	
	//Singleton class
	
	private static DatabaseSupporters db = new DatabaseSupporters();
	private LinkedList<Supporter> list;
	
	private DatabaseSupporters() {
		list = new LinkedList<Supporter>();
	}
	
	public static DatabaseSupporters getRef() {
		return db;
	}
	
	public Iterator<Supporter> getIterator() {
		return list.iterator();
	}
	
	public Supporter searchSupporter(String username, String password) {
		Iterator<Supporter> i = list.iterator();
		Supporter s = null;
		while (i.hasNext()) {
			s = i.next();
			if ((username.equals(s.getUsername())) && (password.equals(s.getPassword()))) {
				return s;
			}
		}
		return null;
	}
	
	public void addDb(Supporter s) {
		list.add(s);
	}
	
	public void remove(Supporter s) throws Exception{
		boolean removed = list.remove(s);
		if (!removed)
			throw new IllegalArgumentException();
	}

}
