import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class ConcreteSponsorTest {
	
	private ConcreteSponsor sponsor;
	
	@Before
	public void setUp() throws Exception {
		sponsor = new ConcreteSponsor("Nike", 20000000);
	}
	
	@Test
	public void testGetNameS () {
		String test = sponsor.getNameS();
		assertEquals("Nike",test);
	}
	
	@Test
	public void testGetInvestment () {
		double test = sponsor.getInvestment();
		assertEquals(20000000,test,0);
	}

}
