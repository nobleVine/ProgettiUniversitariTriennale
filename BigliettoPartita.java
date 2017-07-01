public class BigliettoPartita extends Leaf {
	
	// Leaf3
	
	private String place;
	private String date;
	private String time;
		
	public BigliettoPartita (String name, double price, String place, String date, String time) {
		super(name, price);
		this.place = place;
		this.date = date;
		this.time = time;
	}
	
	public String getPlace () {
		return place;
	}
	
	public String getDate () {
		return date;
	}
	
	public String getTime () {
		return time;
	}
	
	
}
