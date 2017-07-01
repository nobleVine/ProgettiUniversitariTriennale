import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class PackageTest {
	
	private Package pack;	
	
	@Before
	public void setUp() {
		pack = new Package("Calcio1");
	}
	
	@Test
	public void testAdd() {
		int firstSize = pack.listSize();
		pack.add(new Leaf("leaf", 100));
		assertEquals(firstSize + 1, pack.listSize(), 0);
	}
	
	private Leaf leafR = new Leaf("leafR",100);
	
	@Test
	public void testRemove() {
		pack.add(leafR);
		int firstSize = pack.listSize();
		pack.remove(leafR);
		assertEquals(firstSize - 1, pack.listSize(), 0);
	}
	
	@Test
	public void testListSize() {
		pack.add(new Leaf("leaf", 100));
		assertEquals(1, pack.listSize());
	}
	
	@Test
	public void testGetPrice() {
		pack.add(new Leaf("leaf", 100));
		pack.add(new Leaf("leaf2", 100));
		assertEquals(200, pack.getPrice(), 0);
	}

}
