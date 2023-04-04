import static org.junit.Assert.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.Test;

public class PublicTests {

	@Test
	public void testSimpleAdd() {
		MyHashSet<String> s = new MyHashSet<String>();
		s.add("hello");
		s.add("apple");
		assertEquals(4, s.getCapacity());
		assertEquals(2, s.size());
	}
	
	@Test
	public void testReHash() {
		MyHashSet<String> s = new MyHashSet<String>();
		for (int i = 0; i < 1000; i++) {
			s.add("Entry " + i);
		}
		assertEquals(2048, s.getCapacity());
		assertEquals(1000, s.size());
	}

	@Test
	public void testNoDuplicates() {
		MyHashSet<String> s = new MyHashSet<String>();
		for (int i = 0; i < 10; i++) {
			s.add("hello");
			s.add("apple");
			s.add("cat");
			s.add("last");
		}
		assertEquals(8, s.getCapacity());
		assertEquals(4, s.size());
	}
	@Test
	public void testContains() {
	    MyHashSet<String> s = new MyHashSet<String>();
	    s.add("hello");
	    s.add("apple");
	    s.add("cat");
	    assertTrue(s.contains("hello"));
	    assertTrue(s.contains("apple"));
	    assertTrue(s.contains("cat"));
	    assertFalse(s.contains("dog"));
	}
	@Test
	public void testRemove() {
	    MyHashSet<Integer> set = new MyHashSet<>();
	    set.add(1);
	    set.add(2);
	    set.add(3);
	    set.add(4);
	    
	    // Remove an element that exists in the set
	    boolean removed = set.remove(2);
	    assertTrue(removed);
	    assertEquals(3, set.size());
	    assertFalse(set.contains(2));
	    
	    // Remove an element that doesn't exist in the set
	    removed = set.remove(5);
	    assertFalse(removed);
	    assertEquals(3, set.size());
	    
	    // Remove the last element in the set
	    removed = set.remove(4);
	    assertTrue(removed);
	    assertEquals(2, set.size());
	    assertFalse(set.contains(4));
	    
	    // Remove the only element in the set
	    removed = set.remove(1);
	    assertTrue(removed);
	    assertEquals(1, set.size());
	    assertFalse(set.contains(1));
	    
	    // Remove the last remaining element in the set
	    removed = set.remove(3);
	    assertTrue(removed);
	    assertEquals(0, set.size());
	    assertFalse(set.contains(3));
	}
	@Test
	public void testIterator() {
	    MyHashSet<Integer> set = new MyHashSet<>();
	    set.add(1);
	    set.add(2);
	    set.add(3);

	    Iterator<Integer> iterator = set.iterator();

	    assertTrue(iterator.hasNext());
	    assertEquals(1, (int)iterator.next());
	    assertTrue(iterator.hasNext());
	    assertEquals(2, (int)iterator.next());
	    assertTrue(iterator.hasNext());
	    assertEquals(3, (int)iterator.next());
	    assertFalse(iterator.hasNext());
	}
	/**
	 * Checks to see if my class throws a NullPointerException when attempting to add a null element to the set.
	 */
	@Test(expected = NullPointerException.class)
	public void testAddNullElement() {
	    MyHashSet<Integer> set = new MyHashSet<>();
	    set.add(null);
	}
	/**
	 * Tests if my class properly handles attempting to remove an element that does not exist in the set. 
	 * The method should return false in this case.
	 */
	@Test
	public void testRemoveNonexistentElement() {
	    MyHashSet<Integer> set = new MyHashSet<>();
	    set.add(1);
	    set.add(2);
	    set.add(3);

	    assertFalse(set.remove(4));
	    assertEquals(3, set.size());
	}
	/**
	 * Tests if my class properly handles iterating through an empty set. The hasNext() method should return 
	 * false when there are no elements in the set.
	 */
	
	@Test
	public void testIteratorEmptySet() {
	    MyHashSet<Integer> set = new MyHashSet<>();
	    Iterator<Integer> it = set.iterator();
	    assertFalse(it.hasNext());
	}

	// [REMOVE TEST FAILED SO I'M WORKING LIKE HELL TO FIGURE IT OUT]
	@Test
	public void testRemoveElementReturnsTrueAndRemovesElement() {
	    MyHashSet<String> set = new MyHashSet<>();
	    set.add("apple");
	    set.add("banana");
	    set.add("orange");

	    boolean result = set.remove("banana");
	    assertTrue(result);
	    assertEquals(2, set.size());
	    assertFalse(set.contains("banana"));
	}
	@Test
	public void testRemoveNonExistentElement() {
	    MyHashSet<Integer> set = new MyHashSet<>();
	    set.add(1);
	    set.add(2);
	    set.add(3);

	    assertFalse(set.remove(4)); // 4 is not in the set
	    assertEquals(3, set.size()); // size should be unchanged
	    assertTrue(set.contains(1)); // existing elements should still be in the set
	    assertTrue(set.contains(2));
	    assertTrue(set.contains(3));
	}
	@Test
	public void testRemoveAll() {
	    MyHashSet<Integer> set = new MyHashSet<Integer>();
	    for (int i = 0; i < 10; i++) {
	        set.add(i);
	    }
	    for (int i = 0; i < 10; i++) {
	        set.remove(i);
	    }
	    assertEquals(0, set.size());
	}
	
//	@Test(expected = ConcurrentModificationException.class)
//	public void testConcurrentModification() {
//	    MyHashSet<Integer> set = new MyHashSet<>();
//	    set.add(1);
//	    set.add(2);
//	    set.add(3);
//	    Iterator<Integer> iterator = set.iterator();
//	    while (iterator.hasNext()) {
//	        Integer element = iterator.next();
//	        set.remove(element);
//	    }
//	}










}
