import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MyHashArrTest {
	private HashTableLinearProbe<Integer, Integer> sut;

	@Before
	public void setUp() throws Exception {
		sut = new HashTableLinearProbe<Integer, Integer>();
	}

	@Test
	public void testAdd() {
		assertTrue(sut.insert(11, 22));
	}

	@Test
	public void testGet() {
		sut.insert(11, 22);
		assertEquals(22, sut.find(11).intValue());
	}

	@Test
	public void testGetNotExist() {
		Integer e = (sut.find(1));
		assertNull(e);
	}

	@Test
	public void testAddDuplicate() {
		assertTrue(sut.insert(1, -1));
		assertFalse(sut.insert(1, -4));
		assertEquals(-1, sut.find(1).intValue());
	}

	@Test
	public void testAddMultiple() {
		assertTrue(sut.insert(1, -1));
		assertTrue(sut.insert(4, -4));
		assertEquals(-1, sut.find(1).intValue());
		assertEquals(-4, sut.find(4).intValue());
	}

	@Test
	public void testDel() {
		assertTrue(sut.insert(1, -1));
		boolean dd = sut.delete(1);
		assertTrue(dd);
	}

	@Test
	public void testDelMultiple() {
		assertTrue(sut.insert(4, -4));
		assertTrue(sut.insert(1, -1));
		boolean dd = (sut.delete(1));
		assertTrue(dd);
	}

	@Test
	public void testDelNotExist() {
		boolean dd = (sut.delete(1));
		assertFalse(dd);
	}

	@Test
	public void testRehash() {
		assertTrue(sut.insert(1, -1));
		assertTrue(sut.insert(2, -2));
		assertTrue(sut.insert(3, -3));
		assertTrue(sut.insert(4, -4));
		assertEquals(-1, sut.find(1).intValue());
		assertEquals(-2, sut.find(2).intValue());
		assertEquals(-3, sut.find(3).intValue());
		assertEquals(-4, sut.find(4).intValue());
	}

}
