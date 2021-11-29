import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class HashTableLinearProbeTest {

	private HashTableLinearProbe<Integer, Integer> systemInt;
	private HashTableLinearProbe<String, String> systemStr;
	private HashTableLinearProbe<Double, Double> systemDouble;
	private HashTableLinearProbe<Object, Object> systemObject;

	@Before
	public void setUp() throws Exception {
		systemInt = new HashTableLinearProbe();
		systemStr = new HashTableLinearProbe();
		systemDouble = new HashTableLinearProbe();
		systemObject = new HashTableLinearProbe();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInsertNullKey() {
		systemInt.insert(null, 2);
	}

	@Test  	
	public void testInsertDouble() {
		assertTrue(systemDouble.insert(2.2, 1.2));
	}

	@Test	
	public void testInsertObject() {
		String a = "a";
		Integer b = 1;
		Integer two = 2;
		//boolean cc = systemObject.insert(b,2);		//true
		//boolean bb = systemObject.insert(a, two);		//true
		assertTrue(systemObject.insert(b, 2));
		assertTrue(systemObject.insert(a, two));
	}

	@Test
	public void testInsertDuplicateKey() {
		assertTrue(systemInt.insert(1, 2));
		assertFalse(systemInt.insert(1, 3));
		assertFalse(systemInt.insert(1, 4));
		assertFalse(systemInt.insert(1, 3));
		assertFalse(systemInt.insert(1, 4));
		assertFalse(systemInt.insert(1, 3));
		assertFalse(systemInt.insert(1, 4));
	}

	@Test
	public void testInsert() {
		assertTrue(systemInt.insert(1, 2));
		assertTrue(systemInt.insert(2, 3));
		assertTrue(systemInt.insert(3, 3));
	}

	@Test
	public void testInsertNegKey() {
		assertTrue(systemInt.insert(1, 2));
		assertTrue(systemInt.insert(-2, 3));
		assertTrue(systemInt.insert(-3, 3));
	}

	@Test
	public void testInsertDeleteInsert() {
		assertTrue(systemInt.insert(1, 2));
		assertTrue(systemInt.delete(1));
		assertTrue(systemInt.insert(1, 3));
	}

	@Test
	public void testFindWhenDeleted() {
		assertTrue(systemInt.insert(1, 2));
		assertTrue(systemInt.insert(2, 3));
		assertTrue(systemInt.insert(3, 3));
		assertTrue(systemInt.delete(2));
		assertEquals(systemInt.find(2), null);
	}

	@Test
	public void testFind() {
		assertTrue(systemInt.insert(1, 2));
		assertTrue(systemInt.insert(2, 3));
		assertTrue(systemInt.insert(3, 3));
		assertEquals(systemInt.find(2), (Integer) 3);
		assertNull(systemInt.find(4));
	}

	@Test
	public void testDelete() {
		assertTrue(systemInt.insert(1, 2));
		assertTrue(systemInt.insert(2, 3));
		assertTrue(systemInt.insert(3, 3));
		assertTrue(systemInt.delete(2));
		assertFalse(systemInt.delete(4));
	}

	@Test
	public void testGetHashValue() {
		assertTrue(systemInt.insert(0, 2));
		assertTrue(systemInt.insert(1, 3));
		assertTrue(systemInt.insert(2, 3));
		assertEquals(2, systemInt.getHashValue(2));
		assertEquals(-1, systemInt.getHashValue(4));
	}

	@Test
	public void testGetHashWhenDeleted() {
		assertTrue(systemInt.insert(1, 2));
		assertTrue(systemInt.insert(2, 3));
		assertTrue(systemInt.insert(3, 3));
		assertTrue(systemInt.delete(2));
		assertEquals(systemInt.getHashValue(2), -1);
	}

	@Test
	public void testInsertOrder1() {
		assertTrue(systemInt.insert(0, 2));
		assertTrue(systemInt.insert(1, 3));
		assertTrue(systemInt.insert(4, 3));
		assertEquals(1, systemInt.getHashValue(1));
		assertEquals(2, systemInt.getHashValue(4));
	}

	@Test
	public void testRehash() {
		assertTrue(systemInt.insert(0, 2));
		assertTrue(systemInt.insert(1, 3));
		assertTrue(systemInt.insert(4, 3));
		assertFalse(systemInt.insert(3, 3));
		assertEquals(0, systemInt.getHashValue(0));
		assertEquals(1, systemInt.getHashValue(1));
		assertEquals(3, systemInt.getHashValue(3));
		assertEquals(4, systemInt.getHashValue(4));
	}

	@Test
	public void testRehashThenDelete() {
		assertTrue(systemInt.insert(0, 2));
		assertTrue(systemInt.insert(1, 3));
		assertTrue(systemInt.insert(4, 3));
		assertFalse(systemInt.insert(3, 3));
		assertTrue(systemInt.insert(8, 3));
		assertTrue(systemInt.insert(5, 3));
		assertTrue(systemInt.delete(3));
		assertEquals(0, systemInt.getHashValue(0));
		assertEquals(1, systemInt.getHashValue(1));
		assertEquals(2, systemInt.getHashValue(8));
		assertEquals(5, systemInt.getHashValue(5));
		assertEquals(-1, systemInt.getHashValue(3));
		assertEquals(4, systemInt.getHashValue(4));
	}

	@Test
	public void testDeleteThenRehash() {
		assertTrue(systemInt.insert(0, 2));
		assertTrue(systemInt.insert(1, 3));
		assertTrue(systemInt.delete(1));
		assertTrue(systemInt.insert(4, 3));
		assertFalse(systemInt.insert(3, 3));
		assertTrue(systemInt.insert(8, 3));
		assertTrue(systemInt.insert(5, 3));
		assertEquals(0, systemInt.getHashValue(0));
		assertEquals(-1, systemInt.getHashValue(1));
		assertEquals(2, systemInt.getHashValue(8));
		assertEquals(5, systemInt.getHashValue(5));
		assertEquals(3, systemInt.getHashValue(3));
		assertEquals(4, systemInt.getHashValue(4));
	}

	@Test
	public void testRehash2() {
		assertTrue(systemInt.insert(1, 0));
		assertTrue(systemInt.insert(2, 1));
		assertTrue(systemInt.insert(3, 2));
		assertFalse(systemInt.insert(4, 3));
	}

	// Below are testing methods for string (as opposed to Integers above)
	@Test(expected = IllegalArgumentException.class)
	public void testInsertNullKeyStr() {
		systemStr.insert(null, new String("hello"));
	}

	@Test
	public void testInsertDuplicateKeyStr() {
		String abc = "abc";
		assertTrue(systemStr.insert(abc, new String("gibberish")));
		assertFalse(systemStr.insert(abc, new String("trash")));
		assertFalse(systemStr.insert(abc, new String("useless")));
		assertFalse(systemStr.insert(abc, new String("no meaning")));
		assertFalse(systemStr.insert(abc, new String("01010100101")));
		assertFalse(systemStr.insert(abc, new String("<3<3,hhh")));
		assertFalse(systemStr.insert(abc, new String("wer13w")));
	}

	@Test
	public void testInsertStr() {
		assertTrue(systemStr.insert(new String("abc"), new String("JOHN")));
		assertTrue(systemStr.insert(new String("bcd"), new String("ROBERT")));
		assertTrue(systemStr.insert(new String("cde"), new String("JOE")));
	}

	@Test
	public void testInsertDeleteInsertStr() {
		String abc = "abc";
		assertTrue(systemStr.insert(abc, new String("oldval")));
		assertTrue(systemStr.delete(abc));
		assertTrue(systemStr.insert(abc, new String("freshval")));
	}

	@Test
	public void testFindWhenDeletedStr() {
		String abc = "abc";
		assertTrue(systemStr.insert(abc, new String("firstString")));
		assertTrue(systemStr.insert(new String("bcd"), new String("secondString")));
		assertTrue(systemStr.insert(new String("def"), new String("thirdString")));
		assertTrue(systemStr.delete(abc));
		assertEquals(systemStr.find(abc), null);
	}

	@Test
	public void testFindStr() {
		String bcd = "bcd";
		assertTrue(systemStr.insert(new String("abc"), new String("firstString")));
		assertTrue(systemStr.insert(bcd, new String("second")));
		assertTrue(systemStr.insert(new String("cde"), new String("third")));
		assertEquals(systemStr.find(bcd), new String("second"));
		assertNull(systemStr.find(new String("efg")));
	}

	@Test
	public void testDeleteStr() {
		String bcd = "bcd";
		assertTrue(systemStr.insert(new String("abc"), new String("firstString")));
		assertTrue(systemStr.insert(bcd, new String("secondString")));
		assertTrue(systemStr.insert(new String("def"), new String("thirdString")));
		assertTrue(systemStr.delete(bcd));
		assertFalse(systemStr.delete(new String("ChrisPratt")));
	}

	@Test
	public void testGetHashValueStr() {
		String b = "b";
		assertTrue(systemStr.insert(new String("a"), new String("61")));
		assertTrue(systemStr.insert(b, new String("62")));
		assertTrue(systemStr.insert(new String("c"), new String("63")));
		assertEquals(2, systemStr.getHashValue(b));
		assertEquals(-1, systemStr.getHashValue(new String("hhhhhh")));
	}

	@Test
	public void testGetHashWhenDeletedStr() {
		String b = "b";
		assertTrue(systemStr.insert(new String("a"), new String("61")));
		assertTrue(systemStr.insert(b, new String("62")));
		assertTrue(systemStr.insert(new String("c"), new String("63")));
		assertTrue(systemStr.delete(b));
		assertEquals(systemStr.getHashValue(b), -1);
	}

	@Test
	public void testInsertOrder1Str() {
		String d = "d";
		String g = "g";
		assertTrue(systemStr.insert(new String("c"), new String("63")));
		assertTrue(systemStr.insert(d, new String("64")));
		assertTrue(systemStr.insert(g, new String("67")));
		assertEquals(1, systemStr.getHashValue(d));
		assertEquals(2, systemStr.getHashValue(g));
	}

	@Test
	public void testRehashStr() {
		String c = "c";
		String d = "d";
		String f = "f";
		String g = "g";
		assertTrue(systemStr.insert(c, new String("63")));
		assertTrue(systemStr.insert(d, new String("64")));
		assertTrue(systemStr.insert(g, new String("67")));
		assertFalse(systemStr.insert(f, new String("66")));
		assertEquals(3, systemStr.getHashValue(c));
		assertEquals(4, systemStr.getHashValue(d));
		assertEquals(0, systemStr.getHashValue(f));
		assertEquals(1, systemStr.getHashValue(g));
	}

	@Test
	public void testRehashThenDeleteStr() {
		// (key.hashCode() % size)
		String c = "c";
		String d = "d";
		String f = "f";
		String g = "g";
		String k = "k";
		String h = "h";
		assertTrue(systemStr.insert(c, new String("63")));
		assertTrue(systemStr.insert(d, new String("64")));
		assertTrue(systemStr.insert(g, new String("67")));
		assertFalse(systemStr.insert(f, new String("66")));
		assertTrue(systemStr.insert(k, new String("71")));
		assertTrue(systemStr.insert(h, new String("68")));
		assertTrue(systemStr.delete(f));
		assertEquals(3, systemStr.getHashValue(c));
		assertEquals(4, systemStr.getHashValue(d));
		assertEquals(5, systemStr.getHashValue(k));
		assertEquals(2, systemStr.getHashValue(h));
		assertEquals(-1, systemStr.getHashValue(f));
		assertEquals(1, systemStr.getHashValue(g));
	}

	@Test
	public void testDeleteThenRehashStr() {
		String c = "c";
		String d = "d";
		String f = "f";
		String g = "g";
		String k = "k";
		String h = "h";
		assertTrue(systemStr.insert(c, new String("63")));
		assertTrue(systemStr.insert(d, new String("64")));
		assertTrue(systemStr.delete(d));
		assertTrue(systemStr.insert(g, new String("67")));
		assertFalse(systemStr.insert(f, new String("66")));
		assertTrue(systemStr.insert(k, new String("71")));
		assertTrue(systemStr.insert(h, new String("68")));
		assertEquals(3, systemStr.getHashValue(c));
		assertEquals(-1, systemStr.getHashValue(d));
		assertEquals(5, systemStr.getHashValue(k));
		assertEquals(2, systemStr.getHashValue(h));
		assertEquals(0, systemStr.getHashValue(f));
		assertEquals(1, systemStr.getHashValue(g));
	}

	@Test
	public void testRehash2Str() {
		assertTrue(systemStr.insert(new String("d"), new String("64")));
		assertTrue(systemStr.insert(new String("e"), new String("65")));
		assertTrue(systemStr.insert(new String("f"), new String("66")));
		assertFalse(systemStr.insert(new String("g"), new String("67")));
	}
}
