import java.util.Iterator;
import java.util.LinkedList;

public class Bank {
	
	//Singleton
	
	private LinkedList<ClientBank> list;
	private static Bank bank = new Bank();
	
	private Bank() {
		list = new LinkedList<ClientBank>();
	}
	
	public static Bank getRef() {
		return bank;
	}
	
	public Iterator<ClientBank> getIterator() {
		return list.iterator();
	}
	
	public void add(ClientBank client) {
		list.add(client);
	}
	
	public void remove(ClientBank client) throws Exception{
		boolean removed = list.remove(client);
		if (!removed)
			throw new IllegalArgumentException();
	}
	
	public ClientBank verifyCardPayment(int card) {
		Iterator<ClientBank> i = list.iterator();
		ClientBank client = null;
		double cartValue = Cart.getRef().getValue();
		while (i.hasNext()) {
			client = i.next();
			if ((client.getCreditCard() == card) && (client.getCash()>=cartValue)) {
				client.setCash(client.getCash()-cartValue);
				return client;
			}				
		}
		return null;
	}
	
	public ClientBank verifyAccountPayment(int account) {
		Iterator<ClientBank> i = list.iterator();
		ClientBank client = null;
		double cartValue = Cart.getRef().getValue();
		while (i.hasNext()) {
			client = i.next();
			if ((client.getBankAccount() == account) && (client.getCash()>=cartValue)) {
				client.setCash(client.getCash()-cartValue);
				return client;
			}				
		}
		return null;
	}

}
