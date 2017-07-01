public class Completo extends Leaf {
	
	// Leaf1
	
	private int measure;
		
	public Completo (String name, double price, int measure ) {
		super(name, price);
		this.measure = measure;
	}
	
	public int getMeasure () {
		return measure;
	}
	
}