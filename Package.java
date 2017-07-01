import java.util.Iterator;
import java.util.LinkedList;

public class Package extends Item {
	
		// Composite
	
		private LinkedList<Item> list;
				
		public Package (String name) {
			 super(name, 0);
			  list = new LinkedList<Item>();
		 }
		
		public Iterator<Item> getIterator() {
			Iterator<Item> i = list.iterator();
			return i;
		}
		
		public void add(Item i) {
			list.add(i);
			double app = getPrice();
			super.setPrice(app);
		}
		
		public void remove(Item i) {
			if ( list.size() > 0) {
				list.remove(i);
				double app = getPrice();
				super.setPrice(app);
			}
		} 	
		
		public int listSize() {
			return list.size();
		}
		
		public double getPrice() {
			double price = 0;
			Iterator<Item> i = getIterator();
			while (i.hasNext()) {
				price += i.next().getPrice();
			}
			return price;
		}
		
		public void accept(VisitorItem v) {
			v.visitPackage(this);
		}
		
	}
