public class Pallone extends Leaf {
	
	// Leaf2
	
	private boolean inflated;
	
	public Pallone (String name, double price, boolean inflated) {
		super(name, price);
		this.inflated = inflated;
	}
	
	public boolean getState () {
		return inflated;
	}
	
}
