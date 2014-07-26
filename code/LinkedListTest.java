package com.laboon;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// You could also do this to make this a little cleaner.  
// import static org.mockito.Mockito.*;

import org.mockito.*;

public class LinkedListTest {

	@SuppressWarnings("unchecked")
	
	@Mock
	LinkedList<Integer> mockedLinkedList = Mockito.mock(LinkedList.class);
	
	@Before
	public void setUp() throws Exception {
		// If you use @Mock, you need to do this
		MockitoAnnotations.initMocks(mockedLinkedList);
		
	}

	@After
	public void tearDown() throws Exception {
		// any necessary teardown
	}	
		
	@Test
	public void testTraverse() {
		LinkedList<Integer> ll = new LinkedList<Integer>();
		Node<Integer> mockedNode1 = Mockito.mock(Node.class);
		Node<Integer> mockedNode2 = Mockito.mock(Node.class);
		ll.addToFront(mockedNode2);
		ll.addToFront(mockedNode1);
		ll.traverse();
	}
	
	// --------------------------------------------------------------
	// CLEAR TESTS
	// --------------------------------------------------------------
	@Test
	public void testZeroList() {
		LinkedList<Integer> ll = new LinkedList<Integer>();
		ll.clear();
		assertNull(ll.getFront());
	}
	
	@Test
	public void testOneList() {
		LinkedList<Integer> ll = new LinkedList<Integer>();
		ll.addToFront(new Node<Integer>(new Integer(7)));
		ll.clear();
		assertNull(ll.getFront());
	}
	
	@Test
	public void testMultiList() {
		LinkedList<Integer> ll = new LinkedList<Integer>();
		for (int j=0; j < 10; j++) {
			ll.addToFront(new Node<Integer>(new Integer(j)));
		}
		ll.clear();
		assertNull(ll.getFront());
	}
	
	
	// --------------------------------------------------------------
	// PRETTY PRINT TESTS
	// --------------------------------------------------------------
	
	public void testNoItemPrint() {
		
	}
	
	public void testOneItemPrint() {
		
	}
	
	public void testMultiplePrint() {
		
	}
	
	// --------------------------------------------------------------
	// ADD TO FRONT TESTS
	// --------------------------------------------------------------
	
	// If no nodes exist, just add. 
	
	// @SuppressWarnings("unchecked")
	@SuppressWarnings("unchecked")
	@Test
	public void testAddToNoItemLL() {
		LinkedList<Integer> ll = new LinkedList<Integer>();
		Node<Integer>[] nodes = new Node[10];
		
		for (int j = 0; j < 10; j++) {
			nodes[j] = Mockito.mock(Node.class);
			ll.addToFront(nodes[j]);
		}
		
		Node<Integer> testNode = Mockito.mock(Node.class);
		ll.addToFront(testNode);
		Mockito.verify(testNode).setNext(Matchers.eq(nodes[9]));
		assertSame(ll.getFront(), testNode);
		
	}
	
	// If one node exists, add before it
	@SuppressWarnings("unchecked")
	@Test
	public void testAddToOneItemLL() {
		LinkedList<Integer> ll = new LinkedList<Integer>();
		Node<Integer> existingNode = Mockito.mock(Node.class);
		Node<Integer> testNode = Mockito.mock(Node.class);
		ll.addToFront(existingNode);
		ll.addToFront(testNode);
		Mockito.verify(testNode).setNext(Matchers.eq(existingNode));
		assertSame(ll.getFront(), testNode);
	}
	
	// --------------------------------------------------------------
	// DELETE FROM FRONT TESTS
	// --------------------------------------------------------------
	
	@Test
	public void testDeleteFrontNoItem() {
		LinkedList<Integer> ll = new LinkedList<Integer>();
		ll.deleteFront();
		assertEquals(ll.getFront(), null);
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteFrontOneItem() {
		LinkedList<Integer> ll = new LinkedList<Integer>();
		ll.addToFront(Mockito.mock(Node.class));
		ll.deleteFront();
		assertEquals(ll.getFront(), null);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteFrontMultipleItems() {
		LinkedList<Integer> ll = new LinkedList<Integer>();
		Node<Integer>[] nodes = new Node[10];
		
		for (int j = 0; j < 10; j++) {
			nodes[j] = new Node<Integer>(new Integer(1));
			ll.addToFront(nodes[j]);
		}
		
		ll.deleteFront();
		
		assertSame(ll.getFront(), nodes[8]);
	}
	
	// --------------------------------------------------------------
	// EQUALITY TESTS
	// --------------------------------------------------------------
	
	//  0. A LL should always equal itself	
	@Test 
	public void testEqualsSelf() {
		LinkedList<Integer> ll = new LinkedList<Integer>();
		assertEquals(ll, ll);
	}
	
	//	1. Two 0-element LL's should be equal
	@Test
	public void testEquals0Elems() {
		LinkedList<Integer> ll01 = new LinkedList<Integer>();
		LinkedList<Integer> ll02 = new LinkedList<Integer>();
		assertEquals(ll01, ll02);
	}
	
	//	2. No instantiated LL should equal null
	@Test
	public void testNotEqualsNull() {
		LinkedList<Integer> ll01 = new LinkedList<Integer>();
		assertFalse(ll01.equals(null));
	}
	
	//  3. No LL should equal a non-LinkedList, e.g. Object
	@Test
	public void testNotEqualsRegularObject() {
		LinkedList<Integer> ll01 = new LinkedList<Integer>();
		Object obj = new Object();
		assertFalse(ll01.equals(obj));
	}
	
	//  4. Two LLs with the same Node value with a single node should be equal	
	@Test
	public void testEqualsOneNodeSameVals() {
		LinkedList<Integer> ll11 = new LinkedList<Integer>();
		LinkedList<Integer> ll12 = new LinkedList<Integer>();
		ll11.addToFront(new Node<Integer>(new Integer(1)));
		ll12.addToFront(new Node<Integer>(new Integer(1)));
		assertEquals(ll11, ll12);
	}
	
	//  5. Two LL with different Node values with a single node should NOT be equal	
	@Test
	public void testEqualsOneNodeDiffVals() {
		LinkedList<Integer> ll11 = new LinkedList<Integer>();
		LinkedList<Integer> ll2 = new LinkedList<Integer>();
		ll11.addToFront(new Node<Integer>(new Integer(1)));
		ll2.addToFront(new Node<Integer>(new Integer(2)));
		assertFalse(ll11.equals(ll2));
	}
	
	//  6. Two LLs with different sizes should never be equal
	@Test
	public void testNotEqualsDiffSizes() {
		LinkedList<Integer> ll11 = new LinkedList<Integer>();
		LinkedList<Integer> ll_3elems = new LinkedList<Integer>();
		
		ll11.addToFront(new Node<Integer>(new Integer(1)));
		ll_3elems.addToFront(new Node<Integer>(new Integer(3)));
		ll_3elems.addToFront(new Node<Integer>(new Integer(2)));
		ll_3elems.addToFront(new Node<Integer>(new Integer(1)));
		
		assertFalse(ll_3elems.equals(ll11));
	}

	//  7. An LL which is just a reference to another instance of itself should equal itself
	@Test
	public void testEqualsRef() {
		LinkedList<Integer> ll11 = new LinkedList<Integer>();
		ll11.addToFront(new Node<Integer>(new Integer(1)));
		LinkedList<Integer> ll11_new = ll11;
		assertSame(ll11, ll11_new);
	}
	
	//  8. LLs with different data should not equal each other	
	@Test
	public void testNotEqualsDiffData() {
		LinkedList<Integer> ll_3elems = new LinkedList<Integer>();
		LinkedList<Integer> ll_321 = new LinkedList<Integer>();
		ll_3elems.addToFront(new Node<Integer>(new Integer(3)));
		ll_3elems.addToFront(new Node<Integer>(new Integer(2)));
		ll_3elems.addToFront(new Node<Integer>(new Integer(1)));
		
		ll_321.addToFront(new Node<Integer>(new Integer(1)));
		ll_321.addToFront(new Node<Integer>(new Integer(2)));
		ll_321.addToFront(new Node<Integer>(new Integer(3)));
		assertFalse(ll_321.equals(ll_3elems));
	}
	
	//  9. LLs with the same data should equal each other
	@Test
	public void testEqualsSameData() {
		LinkedList<Integer> ll_321 = new LinkedList<Integer>();
		LinkedList<Integer> ll_321_2 = new LinkedList<Integer>();
		
		ll_321.addToFront(new Node<Integer>(new Integer(1)));
		ll_321.addToFront(new Node<Integer>(new Integer(2)));
		ll_321.addToFront(new Node<Integer>(new Integer(3)));
		
		ll_321_2.addToFront(new Node<Integer>(new Integer(1)));
		ll_321_2.addToFront(new Node<Integer>(new Integer(2)));
		ll_321_2.addToFront(new Node<Integer>(new Integer(3)));
		
		assertTrue(ll_321.equals(ll_321_2));
		
		// ???
	}

	
	
}
