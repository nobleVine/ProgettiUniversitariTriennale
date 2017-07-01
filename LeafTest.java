import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class LeafTest {
	
	private Leaf leaf;
	
	@Before
	public void setUp() throws Exception {
		leaf = new Leaf("Pallone", 20);
	}
	
	@Test
	public void testGetNameItem () {
		String test = leaf.getNameItem();
		assertEquals("Pallone",test);
	}
	
	@Test
	public void testGetPrice () {
		double test = leaf.getPrice();
		assertEquals(20, test, 0);
	}
	
	@Test(expected=Exception.class)
	public void testAdd() throws Exception{
		leaf.add(null);
	}
	
}
