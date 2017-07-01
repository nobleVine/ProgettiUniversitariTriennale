import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class PalloneTest {
	
	private Pallone pallone;
	
	@Before
	public void setUp() throws Exception {
		pallone = new Pallone("ProSoccer", 20, true);
	}
	
	@Test
	public void testGetState () throws Exception {
		boolean test = pallone.getState();
		assertTrue(test);
	}

	

}
