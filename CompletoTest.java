import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CompletoTest {

	private Completo comp;
	
	@Before
	public void setUp() throws Exception {
		comp = new Completo("Ronaldo", 35, 33);
	}
	
	@Test
	public void testGetMeasure () {
		int test = comp.getMeasure();
		assertEquals(33, test, 0);
	}

}
