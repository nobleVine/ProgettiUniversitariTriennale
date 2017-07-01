import static org.junit.Assert.*;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;

public class DatabaseSupportersTest {
	
	private DatabaseSupporters db;
	private Supporter sTest;

	@Before
	public void setUp() throws Exception {
		db = DatabaseSupporters.getRef();
		sTest = new ConcreteSupporter("Matteo", "Bianchi", 17, "username", "password");
	}
	
	@Test
	public void testAddDb() {
		int listSize = listSize();
		db.addDb(sTest);
		assertEquals(listSize + 1, listSize());
		try {
			db.remove(sTest);
		} catch (Exception e) {
		}
	}

	@Test
	public void testSearchUser() {		 
		db.addDb(sTest);
		Supporter s = db.searchSupporter("username", "password");
		assertEquals(s, sTest);
		try {
			db.remove(sTest);
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testRemove() throws Exception{
		db.addDb(sTest);
		int firstSize = listSize();
		db.remove(sTest);
		assertEquals(firstSize - 1, listSize());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRemoveWithException() throws Exception{
		db.remove(sTest);
	}
	
	private int listSize() {
		Iterator<Supporter> i = db.getIterator();
		int j = 0;
		while (i.hasNext()) {
			i.next();
			j++;
		}
		return j;
	}

}

