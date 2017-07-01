public class Leaf extends Item {
	
	// Leaf
	
	public Leaf ( String name, double price ) {
		super(name, price);
	}
	
	public void add ( Item i ) throws Exception {
		throw new Exception("Impossibile aggiungere componenti ad una foglia");
	}
	
	public void remove ( Item i) throws Exception {
		throw new Exception("Impossibile rimuovere componenti ad una foglia");
	}
	
	public int listSize () throws Exception {
		throw new Exception("Non ha senso sulle foglie");
	}
	
	public void accept(VisitorItem v) {
		v.visitLeaf(this);
	}
	
}
