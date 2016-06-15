package hr.fer.zemris.java.tecaj.hw5.collections;

import static org.junit.Assert.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable.TableEntry;

/**
 * Test class for {@link SimpleHashtable} class.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
@SuppressWarnings("javadoc")
public class SimpleHashtableTest {

	SimpleHashtable<String, String> table;

	@Before
	public void setUp() throws Exception {
		table = new SimpleHashtable<>(2);
	}

	@Test
	public void testPut() {
		table.put("1", "prvi");
		assertEquals("prvi", table.get("1"));
		table.put("2", "drugi");
		assertEquals("drugi", table.get("2"));
		table.put("3", "treci");
		assertEquals("treci", table.get("3"));
		table.put("3", "cetvrti");
		assertEquals("cetvrti", table.get("3"));
		table.put("4", null);
		assertNull(table.get("4"));

		assertEquals("prvi", table.get("1"));
		assertEquals("drugi", table.get("2"));
		assertEquals("cetvrti", table.get("3"));
		assertNull(table.get("4"));
	}

	@Test(
		expected = IllegalArgumentException.class)
	public void testPutWrongKey() {
		table.put(null, "prvi");
	}

	@Test
	public void testGetWrongKey() {
		putSomeValues();

		// passing null as key
		assertNull(table.get(null));

		// passing anything other than String as key
		assertNull(table.get(1));
		assertNull(table.get(1.0));
		assertNull(table.get(table));

		// passing non-existing key
		assertNull(table.get("non-existing key"));
	}

	@Test
	public void testSize() {
		assertEquals(0, table.size());

		table.put("1", "prvi");
		assertEquals(1, table.size());

		table.put("2", "drugi");
		assertEquals(2, table.size());

		table.put("3", "treci");
		assertEquals(3, table.size());

		table.put("3", "cetvrti");
		assertEquals(3, table.size());

		table.put("4", null);
		assertEquals(4, table.size());
	}

	@Test
	public void testContainsKey() {
		putSomeValues();

		assertTrue(table.containsKey("1"));
		assertTrue(table.containsKey("2"));
		assertTrue(table.containsKey("3"));

		table.put("4", null);
		assertTrue(table.containsKey("4"));
	}

	@Test
	public void testContainsKeyWrongKey() {
		putSomeValues();

		// passing null as key
		assertFalse(table.containsKey(null));

		// passing anything other than String as key
		assertFalse(table.containsKey(1));
		assertFalse(table.containsKey(1.0));
		assertFalse(table.containsKey(table));

		// passing non-existing key
		assertFalse(table.containsKey("non-existing key"));
	}

	@Test
	public void testContainsValue() {
		putSomeValues();

		assertTrue(table.containsValue("prvi"));
		assertTrue(table.containsValue("drugi"));
		assertTrue(table.containsValue("cetvrti"));

		table.put("4", null);
		assertTrue(table.containsValue(null));
	}

	@Test
	public void testContainsValueWrongValue() {
		putSomeValues();

		// passing null as value and we didn't add null
		assertFalse(table.containsKey(null));

		// passing anything other than String as key
		assertFalse(table.containsKey(1));
		assertFalse(table.containsKey(1.0));
		assertFalse(table.containsKey(table));

		// passing non-existing key
		assertFalse(table.containsKey("non-existing key"));
	}

	@Test
	public void testRemove() {
		putSomeValues();

		table.remove("2");
		assertNull(table.get("2"));
		table.remove("1");
		assertNull(table.get("1"));
		table.remove("3");
		assertNull(table.get("3"));

		assertTrue(table.isEmpty());
	}

	@Test
	public void testRemoveWrongKey() {
		putSomeValues();

		// passing null as key
		table.remove(null);
		assertEquals(3, table.size());

		// passing anything other than String as key
		table.remove(1);
		assertEquals(3, table.size());

		table.remove(1.0);
		assertEquals(3, table.size());

		table.remove(table);
		assertEquals(3, table.size());

		// passing non-existing key
		table.remove("non-existing key");
		assertEquals(3, table.size());
	}

	@Test
	public void testToString() {
		assertEquals("[]", table.toString());

		putSomeValues();
		assertEquals("[1=prvi, 2=drugi, 3=cetvrti]", table.toString());
	}

	@Test
	public void testIterator() {
		putSomeValues();
		Iterator<TableEntry<String, String>> it = table.iterator();

		String str1 = "";
		while (it.hasNext()) {
			str1 += it.next() + " ";
		}

		assertEquals("1=prvi 2=drugi 3=cetvrti ", str1);

		table.forEach(System.out::println);
	}

	@Test
	public void testIteratorRemove() {
		SimpleHashtable<String, Integer> examMarks = examMarks();

		Iterator<TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove();
			}
		}

		assertFalse(examMarks.containsKey("Ivana"));
	}

	@Test(
		expected = IllegalStateException.class)
	public void testIteratorDoubleRemove() {
		SimpleHashtable<String, Integer> examMarks = examMarks();

		Iterator<TableEntry<String, Integer>> iter = examMarks.iterator();

		while (iter.hasNext()) {
			TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove();
				iter.remove();
			}
		}
	}

	@Test(expected = ConcurrentModificationException.class)
	public void testIteratorExternalModification() {
		SimpleHashtable<String, Integer> examMarks = examMarks();

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks
				.iterator();
		
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				examMarks.remove("Ivana");
			}
		}

	}

	private SimpleHashtable<String, Integer> examMarks() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);

		return examMarks;
	}

	private void putSomeValues() {
		table.put("1", "prvi");
		table.put("2", "drugi");
		table.put("3", "treci");
		table.put("3", "cetvrti");
	}
}
