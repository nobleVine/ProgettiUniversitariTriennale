import java.util.Iterator;
import java.util.LinkedList;

public class Warehouse {
	
	//Singleton
	
	private static Warehouse w = new Warehouse();
	private LinkedList<Item> list;
	
	private Warehouse() {
		list = new LinkedList<Item>();
	}
	
	public static Warehouse getRef() {
		return w;
	}
	
	public Iterator<Item> getIterator() {
		return list.iterator();
	}
	
	public void add(Item i) {
		list.add(i);
	}
	
	public void remove(Item i) throws Exception{
		boolean removed = list.remove(i);
		if (!removed)
			throw new IllegalArgumentException();
	}

}

