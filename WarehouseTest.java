import static org.junit.Assert.*;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;

public class WarehouseTest {
	
	private Warehouse w;
	private Leaf leaf;

	@Before
	public void setUp() throws Exception {
		w = Warehouse.getRef();
		leaf = new Leaf("leaf", 100);
	}

	@Test
	public void testAdd() {
		int firstSize = listSize();
		w.add(leaf);
		assertEquals(firstSize + 1, listSize(), 0);
		try {
			w.remove(leaf);
		} catch (Exception e) {
		}
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRemoveWithException() throws Exception{
		w.remove(leaf);
	}
	
	@Test
	public void testRemove() throws Exception{
		w.add(leaf);
		int firstSize = listSize();
		w.remove(leaf);
		assertEquals(firstSize - 1, listSize(), 0);
	}
	
	private int listSize() {
		Iterator<Item> i = w.getIterator();
		int j = 0;
		while (i.hasNext()) {
			i.next();
			j++;
		}
		return j;
	}
	
}
