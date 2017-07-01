
public abstract class Item {
	
	// Component
	
	private String name;
	private double price;
	private String description;
		
	public Item ( String name, double price ) {
		this.name = name;
		this.price = price;
	}
	
	public String getDescription () {
		return description;
	}
	
	public void setDescription (String description) {
		this.description = description;
	}
	
	public String getNameItem () {
		return name;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice (double app) {
		price = app;
	}
	
	public abstract void add (Item i) throws Exception;
	public abstract void remove (Item i) throws Exception;
	public abstract int listSize() throws Exception;
	public abstract void accept(VisitorItem v);
		
}
