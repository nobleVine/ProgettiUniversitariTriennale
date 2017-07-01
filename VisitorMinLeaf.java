import java.util.Iterator;

public class VisitorMinLeaf implements VisitorItem {
	
	// ConcreteVisitor3
	
	private double min;
	
	public double getMin () {
		return min;
	}
	
	public void visitLeaf(Leaf leaf) {
		if ( min < leaf.getPrice()) {
			min = leaf.getPrice();
		}
	}

	public void visitPackage(Package pack) {
		Iterator<Item> i = pack.getIterator();
		while (i.hasNext())
			i.next().accept(this);
	}

}
