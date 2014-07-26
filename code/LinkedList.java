package com.laboon;

import java.util.ArrayList;

public class LinkedList<T> {

	Node<T> _head = null;
	
	public LinkedList() {
		
	}
	
	public void traverse() {
		Node<T> ptr = _head;
		while (ptr != null) {
			ptr = ptr.getNext();
		}
	}
	
	public void prettyPrint() {
		Node<T> ptr = _head;
		if (_head == null) {
			System.out.println("<NULL>");
		}
		while (ptr != null) {
			if (ptr.getNext() != null) {
				System.out.print(ptr.getData() + " -> ");
			}  else {
				System.out.println(ptr.getData());
			}
			ptr = ptr.getNext();
		}
	}
	
	public void addToFront(Node<T> toAdd) {
		if (_head == null) {
			_head = toAdd;
		} else {
			Node<T> oldHead = _head;
			_head = toAdd;
			_head.setNext(oldHead);
		}
	}
	
	public void deleteLast() {
		if (_head == null) {
			// Nothing to do!
			return;
		}
		
		if (_head.getNext() == null) {
			_head = null;
			return;
		}
		
		Node<T> ptr = _head;
		Node<T> last = ptr;
		
		while (ptr != null) {
			
			ptr = ptr.getNext();
			if (ptr != null && ptr.getNext() != null) {
				
				last = ptr;
			}
		}
		
		last.setNext(null);
	}
	
	public void deleteFront() {
		if (_head == null) {
			// Nothing to do!
			return;
		}
		
		if (_head.getNext() == null) {
			_head = null;
			return;
		}
		
		_head = _head.getNext();
		
	}
	
	public T getFrontData() {
		if (_head == null) {
			return null;
		} else {
			return _head.getData();
		}
	}
	
	public Node<T> getFront() {
		return _head;
	}
	
	public void addToEnd(Node<T> toAdd) {
		
		toAdd.setNext(null);
		
		if (_head == null) {
			_head = toAdd;
		} else {
			
			Node<T> ptr = _head;
			Node<T> last = ptr;
			while (ptr != null) {
				
				last = ptr;
				ptr = ptr.getNext();
			}
			
			last.setNext(toAdd);
			
		}
	
	}
	
	/**
	 * Delete all duplicate data
	 */
	
	public void deleteDupes() {
		
		// If there are zero or one elements, there can't be dupes
		
		if (_head == null || _head.getNext() == null) {
			return;
		}
		
		ArrayList<T> prevObjs = new ArrayList<T>();
		Node<T> ptr = _head;
		Node<T> last = ptr;
		
		// Traverse list
		
		while (ptr != null) {
			if (prevObjs.contains(ptr.getData())) {
				// delete node - note this will never occur on first pass-through
				last.setNext(ptr.getNext());
			} else {
				// add it to the list of previous data elements
				last = ptr;
				prevObjs.add(ptr.getData());
			}
			
			ptr = ptr.getNext();
		}
		
	}
	
	/**
	 * Will delete first element with that value
	 * @param valToCheck
	 */
	
	public void deleteByVal(T valToCheck) {
		
		if (_head == null) {
			// nothing here, return
			return;
		}
		
		if (_head.getNext() == null) {
			// Only one item
			if (_head.getData() == valToCheck) {
				_head = null;
			} 
			return;
		}
		
		if (_head.getData() == valToCheck) {
			_head = _head.getNext();
		}
		
		Node<T> ptr = _head;
		Node<T> last = ptr;
		boolean foundIt = false;
		while (ptr != null && !(foundIt)) {
			if (ptr.getData() == valToCheck) {
				// Found it!
				foundIt = true;
				last.setNext(ptr.getNext());
			} else {
				last = ptr;
				ptr = ptr.getNext();
			}
		}
	}
	
	public void clear() {
		_head = null;
	}
	
	public T kthToLast(int k) {
		
		if (_head == null) {
			return null;
		}
		Node<T> ptr = _head;
		Node<T> candidate = null;
		int ctr = 0;
		while (ptr != null) {
			
			if (ctr == k) {
				candidate = _head;
			} else if (ctr > k) {
				candidate = candidate.getNext();
			}
			ctr++;
			ptr = ptr.getNext();
		}
		if (candidate == null) {
			return null;
		} else {
			return candidate.getData();
		}
	}
	
	@Override
	public boolean equals(Object rhs) {
		
		// Trivial cases - if exact same object, return true,
		// if not of correct class or if null, return false
		
		if (this == rhs) return true;
		else if (!(rhs instanceof LinkedList) || rhs == null) return false;
		LinkedList other = (LinkedList) rhs;
		boolean toReturn = true;
		Node ptr1 = this.getFront();
		Node ptr2 = other.getFront();
		boolean cont = true;
		while (ptr1 != null && ptr2 != null && cont == true) {
			// System.out.println("Ptr1 = " + ptr1.getData() + " / ptr2 = " + ptr2.getData());
			if (ptr1.getData().equals(ptr2.getData())) {
				ptr1 = ptr1.getNext();
				ptr2 = ptr2.getNext();
			} else {
				// System.out.println("Data not equal, setting to false");
				toReturn = false;
				cont = false;
			}
		}
		if (ptr1 == null ^ ptr2 == null) {
			// System.out.println("XOR failure - lists are diff sizes");
			toReturn = false;
		} 
		
		return toReturn;
	}
	
	@Override
	public int hashCode() {
		return 1;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		LinkedList<Integer> ll = new LinkedList<Integer>();
		Node<Integer> node0 = new Node<Integer>(0);
		Node<Integer> node1 = new Node<Integer>(1);
		Node<Integer> node2 = new Node<Integer>(2);
		Node<Integer> node3 = new Node<Integer>(3);
		Node<Integer> node4 = new Node<Integer>(4);
		Node<Integer> node5 = new Node<Integer>(5);
		ll.addToEnd(node1);
		ll.addToEnd(node2);
		ll.addToEnd(node3);
		ll.addToEnd(node4);
		ll.addToEnd(node5);
		ll.addToFront(node0);
		
		System.out.println("Original..");
		ll.traverse();
		
		System.out.println("Pretty...");
		ll.prettyPrint();
		
		System.out.println("Delete front..");
		ll.deleteFront();
		ll.traverse();
		
		System.out.println("Delete end...");
		ll.deleteLast();
		ll.traverse();

		System.out.println("Delete non-existent 1000..");
		ll.deleteByVal(1000);
		ll.traverse();
		
		System.out.println("Delete 2..");
		ll.deleteByVal(2);
		ll.traverse();
		
		System.out.println("Delete 1..");
		ll.deleteByVal(1);
		ll.traverse();
		
		System.out.println("Delete 4..");
		ll.deleteByVal(4);
		ll.traverse();
		
		System.out.println("Delete 3..");
		ll.deleteByVal(3);
		ll.traverse();
		
		System.out.println("Delete 1 on null..");
		ll.deleteByVal(1);
		ll.traverse();
		
		
		ll.clear();
		System.out.println("Delete dupes NULL: orig");
		ll.prettyPrint();
		ll.deleteDupes();
		System.out.println("Delete dupes NULL: after");
		ll.prettyPrint();
		ll.addToEnd(new Node<Integer>(new Integer(1)));
		ll.addToEnd(new Node<Integer>(new Integer(1)));
		ll.addToEnd(new Node<Integer>(new Integer(2)));
		ll.addToEnd(new Node<Integer>(new Integer(3)));
		ll.addToEnd(new Node<Integer>(new Integer(3)));
		ll.addToEnd(new Node<Integer>(new Integer(2)));
		ll.addToEnd(new Node<Integer>(new Integer(1)));
		ll.addToEnd(new Node<Integer>(new Integer(3)));
		ll.addToEnd(new Node<Integer>(new Integer(2)));
		ll.addToEnd(new Node<Integer>(new Integer(1)));
		System.out.println("Delete dupes: orig");
		ll.prettyPrint();
		System.out.println("Delete dupes: after");
		ll.deleteDupes();
		ll.prettyPrint();
		
		System.out.println("Long ordered list");
		ll.clear();
		ll.addToEnd(new Node<Integer>(new Integer(1)));
		ll.addToEnd(new Node<Integer>(new Integer(2)));
		ll.addToEnd(new Node<Integer>(new Integer(3)));
		ll.addToEnd(new Node<Integer>(new Integer(4)));
		ll.addToEnd(new Node<Integer>(new Integer(5)));
		ll.addToEnd(new Node<Integer>(new Integer(6)));
		ll.addToEnd(new Node<Integer>(new Integer(7)));
		ll.addToEnd(new Node<Integer>(new Integer(8)));
		ll.addToEnd(new Node<Integer>(new Integer(9)));
		ll.prettyPrint();
		for (int j=0; j<10; j++) {
			Integer n = ll.kthToLast(j);
			if (n == null) {
				System.out.println("Returned null");
			} else {
				System.out.println(j + "[st/nd/th] to last = " + n.intValue());
			}
		}
		*/
	}

}
