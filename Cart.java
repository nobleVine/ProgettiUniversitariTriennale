
import java.util.Iterator;
import java.util.LinkedList;

public class Cart{
	
	//Singleton
	
	private static Cart cart = new Cart();	
	private LinkedList<Item> list;
	
	private Cart() {
		list = new LinkedList<Item>();
	}
	
	public static Cart getRef() {
		return cart;
	}
	
	public Iterator<Item> getIterator() {
		return list.iterator();
	}
	
	public void add(Item i) {
		list.add(i);
	}
	
	public void remove(Item i) {
		boolean removed = list.remove(i);
		if (!removed)
			throw new IllegalArgumentException();
	}
	
	public double getValue() {
		double value = 0;
		Iterator<Item> i = getIterator();
		while (i.hasNext()) {		
			value += i.next().getPrice();
		}
		return value;
	}
	
	public void removeAll() {
		list.clear();
	}
	
}