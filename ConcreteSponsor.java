
public class ConcreteSponsor implements Sponsor {

	private String nameS;
	private double investment;
	
	public ConcreteSponsor( String nameS, double investment ) {
		this.nameS = nameS;
		this.investment = investment;
	}
		
	public String getNameS () {
		return nameS;
	}
	
	public double getInvestment () {
		return investment;
	}
	
}
