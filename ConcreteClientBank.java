public class ConcreteClientBank implements ClientBank {
	
	private int bankAccount;
	private int creditCard;
	private double cash; 
	
	public ConcreteClientBank(int bankAccount, int creditCard, double cash) {
		this.bankAccount = bankAccount;
		this.creditCard = creditCard;
		this.cash = cash;
	}
	
	public int getBankAccount() {
		return bankAccount;
	}
	
	public int getCreditCard() {
		return creditCard;
	}
	
	public double getCash() {
		return cash;
	}
	
	public void setCash(double cash) {
		this.cash = cash;
	}

}