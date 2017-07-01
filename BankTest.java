import static org.junit.Assert.*;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;

public class BankTest {
	
	private Bank bank;
	private ClientBank client;

	@Before
	public void setUp() throws Exception {
		bank = Bank.getRef();
		client = new ConcreteClientBank(22222, 22222, 500);
	}

	@Test
	public void testGetRef() {
		assertTrue(bank instanceof Bank);
	}

	@Test
	public void testAdd() {
		int listSize = listSize();
		bank.add(client);
		assertEquals(listSize + 1, listSize());
		try {
			bank.remove(client);
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testRemove() throws Exception{
		bank.add(client);
		int firstSize = listSize();
		bank.remove(client);
		assertEquals(firstSize - 1, listSize());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRemoveWithException() throws Exception{
		bank.remove(client);
	}
	
	@Test
	public void testListSize() {
		assertEquals(0, listSize());
	}

	@Test
	public void testVerifyCardPayment() {
		bank.add(client);
		ClientBank temp = bank.verifyCardPayment(22222);
		assertNotNull(temp);
		try {
			bank.remove(client);
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testVerifyCardPaymentFalse() {
		bank.add(client);
		ClientBank temp = bank.verifyCardPayment(11111);
		assertNull(temp);
		try {
			bank.remove(client);
		} catch (Exception e) {
		};
	}

	@Test
	public void testVerifyAccountPayment() {
		bank.add(client);
		ClientBank temp = bank.verifyAccountPayment(22222);
		assertNotNull(temp);
		try {
			bank.remove(client);
		} catch (Exception e) {
		};
	}
	
	@Test
	public void testVerifyAccountPaymentFalse() {
		bank.add(client);
		ClientBank temp = bank.verifyAccountPayment(11111);
		assertNull(temp);
		try {
			bank.remove(client);
		} catch (Exception e) {
		}
	}
	
	private int listSize() {
		Iterator<ClientBank> i = bank.getIterator();
		int j = 0;
		while (i.hasNext()) {
			i.next();
			j++;
		}
		return j;
	}

}