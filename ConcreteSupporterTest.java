import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ConcreteSupporterTest {

	private ConcreteSupporter cc;
	
	@Before
	public void setUp () {
		cc = new ConcreteSupporter("Marco", "Rossi", 15, "username", "password");
	}
	
	@Test
	public void testGetNameClient () {
		String test = cc.getNameSupporter();
		assertEquals("Marco",test);
	}
	
	@Test
	public void testGetSurnameClient () {
		String test = cc.getSurnameSupporter();
		assertEquals("Rossi",test);
	}
	
	@Test
	public void testGetEta () {
		int test = cc.getAge();
		assertEquals(15, test, 0);
	}
	
	
}
