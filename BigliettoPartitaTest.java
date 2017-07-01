import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class BigliettoPartitaTest {

	private BigliettoPartita ticket;
	
	@Before
	public void setUp () throws Exception {
		ticket = new BigliettoPartita("Inter - Roma", 25, "San Siro", "15 Dicembre 2014", "20:45");
	}
	
	@Test
	public void testGetPlace () {
		String test = ticket.getPlace();
		assertEquals("San Siro",test);
	}
	
	@Test
	public void testGetDate () {
		String test = ticket.getDate();
		assertEquals("15 Dicembre 2014",test);
	}
	
	@Test
	public void testGetTime () {
		String test = ticket.getTime();
		assertEquals("20:45",test);
	}
	
}
