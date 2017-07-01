import java.util.Iterator;

public class VisitorPrintItem implements VisitorItem {

	// ConcreteVisitor1
	
	public void visitLeaf(Leaf leaf) {
		System.out.println(leaf.getNameItem());
	}

	public void visitPackage (Package pack) {
		Iterator<Item> i = pack.getIterator();
		System.out.println("Il pacchetto contiene: ");
		while (i.hasNext()) {
			i.next().accept(this);
		}
	}
	
}
