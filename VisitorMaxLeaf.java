import java.util.Iterator;

public class VisitorMaxLeaf implements VisitorItem {
	
	// ConcreteVisitor2
	
	private double max = 0;
	
	public double getMax () {
		return max;
	}
	
	public void visitLeaf(Leaf leaf) {
		if ( max < leaf.getPrice()) {
			max = leaf.getPrice();
		}
	}

	public void visitPackage(Package pack) {
		Iterator<Item> i = pack.getIterator();
		while (i.hasNext())
			i.next().accept(this);
	}

}
 