import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ClientBankTest {
	
	private ClientBank client;

	@Before
	public void setUp() throws Exception {
		client = new ConcreteClientBank(33333, 33333, 5000);
	}

	@Test
	public void testGetBankAccount() {
		assertEquals(33333, client.getBankAccount());
	}

	@Test
	public void testGetCreditCard() {
		assertEquals(33333, client.getCreditCard());
	}

	@Test
	public void testGetCash() {
		assertEquals(5000, client.getCash(), 0);
	}

	@Test
	public void testSetBalance() {
		client.setCash(7000);
		assertEquals(7000, client.getCash(), 0);
	}

}
