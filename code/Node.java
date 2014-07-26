package com.laboon;

public class Node<T> {

	private Node<T> _nextNode = null;
	private T _data = null;
	
	public Node(T data) {
		_data = data;
	}
	
	public void setData(T data) {
		_data = data;
	}
	
	public T getData() {
		return _data;
	}
	
	public Node<T> getNext() {
		return _nextNode;
	}
	
	public void setNext(Node<T> newNext) {
		_nextNode = newNext;
	}
	
	public String toString() {
		if (_nextNode != null) {
			return "(" + _data + ", " + _nextNode.hashCode() + ")";
		} else {
			return "(" + _data + ", " + null + ")";
		}
	}
}
