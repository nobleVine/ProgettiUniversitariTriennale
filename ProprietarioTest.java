import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ProprietarioTest {

	private Sponsor s;
	
	@Before
	public void setUp() throws Exception {
		
		s = new ConcreteSponsor("Nike", 100);
		Proprietario.getRef();
		Proprietario.setName("Alessio");
		Proprietario.getRef();
		Proprietario.setSurname("Rossi");
		Proprietario.getRef();
		Proprietario.setCash(50, s);
			
	}
	
	 @Test
	public void testGetName () {
		Proprietario.getRef();
		String test = Proprietario.getName();
		assertEquals("Alessio",test);
	}
	
	@Test
	public void testGetSurname () {
		String test = Proprietario.getSurname();
		assertEquals("Rossi",test);
	}
	
	@Test
	public void testGetCash () {
		double test = Proprietario.getCash();
		assertEquals(150, test, 0);
	}
	
}
