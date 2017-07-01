import static org.junit.Assert.*;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;

public class CartTest {
	
	private Cart cart;
	private Item leaf1, leaf2, leaf3;
	private Package pack1;

	@Before
	public void setUp() throws Exception {
		cart = Cart.getRef();
		leaf1 = new Leaf("leaf1", 100);
		leaf2 = new Leaf("leaf2", 200);
		pack1 = new Package("pack1");
		pack1.add(leaf2);
		cart.add(pack1);
		cart.add(leaf1);
	}
	
	@Test
	public void testAdd() {
		int firstSize = listSize();
		cart.add(leaf1);
		assertEquals(firstSize + 1, listSize());
	}
	
	@Test
	public void testRemoveAll() {
		cart.removeAll();
		assertEquals(0, listSize());
	}

	@Test
	public void testGetValue() {
		cart.removeAll();
		cart.add(leaf1);
		cart.add(pack1);
		assertEquals(300, cart.getValue(), 0);	
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRemoveWithException() throws Exception{
		leaf3 = new Leaf("leaf3", 0);
		cart.remove(leaf3);
	}
	
	@Test
	public void testRemove() throws Exception{
		cart.add(leaf1);
		int firstSize = listSize();
		cart.remove(leaf1);
		assertEquals(firstSize - 1, listSize());
	}
	
	private int listSize() {
		Iterator<Item> i = cart.getIterator();
		int j = 0;
		while (i.hasNext()) {
			i.next();
			j++;
		}
		return j;
	}
	
}