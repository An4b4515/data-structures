package dataStructures.nonLinear;

import java.util.Objects;
import dataStructures.linear.LinearQueue;
import dataStructures.linear.DoublyLinkedList;


public class BST<T extends Comparable<T>> {
	private class Node {
		T item;
		Node left;
		Node right;

		Node(Node left, T item, Node right) {
			this.item  = item;
			this.left  = left;
			this.right = right;
		}

		@Override
		public String toString() {
			return this.item.toString();
		}
	}


// Fields:
	private int count = 0;
	private Node root = null;


// Constructors:
	public BST() { }
	public BST(T rootItem) {
		this.root = new Node(null, rootItem, null);
		++this.count;
	}


// Info Methods:
	public boolean isEmpty() {
		return null == this.root;
	}

	public int size() {
		return this.count;
	}


// Control Methods:
	public void insert(T item) {
		Objects.requireNonNull( item );

		if ( this.isEmpty() ) {
			this.root = new Node(null, item, null);
			++this.count;
			return;
		}

		Node curr = this.root, parent = null;
		while ( curr != null ) {
			parent = curr;

			if ( item.compareTo( curr.item ) < 0 )
				curr = curr.left;
			else
				curr = curr.right;
		}

		if ( item.compareTo( parent.item ) < 0 )
			parent.left = new Node(null, item, null);
		else
			parent.right = new Node(null, item, null);

		++this.count;
	}

	public boolean remove(T item) {
		Objects.requireNonNull( item );

		boolean isLeft = false;
		Node parent = this.root, curr = this.root;

		while ( curr != null ) {
			if ( item.equals(curr.item) ) {
				remove(parent, curr, isLeft);
				return true;
			}
			else if ( item.compareTo( curr.item ) < 0 ) {
				parent = curr;
				curr = curr.left;
				isLeft = true;
			}
			else {
				parent = curr;
				curr = curr.right;
				isLeft = false;
			}
		}

		return false;
	}


// Traversal Methods:
// Depth-First Search (DFS):
	public DoublyLinkedList<T> preOrderDFS() {
		var ls = new DoublyLinkedList<T>();
		preOrderTraverse(this.root, ls);
		return ls;
	}
	private void preOrderTraverse(Node curr, final DoublyLinkedList<T> ls) {
		if ( null == curr )
			return;

		ls.addLast(curr.item);
		preOrderTraverse(curr.left, ls);	// Recursive calls ...
		preOrderTraverse(curr.right, ls);
	}

	public DoublyLinkedList<T> postOrderDFS() {
		var ls = new DoublyLinkedList<T>();
		postOrderTraverse(this.root, ls);
		return ls;
	}
	private void postOrderTraverse(Node curr, final DoublyLinkedList<T> ls) {
		if ( null == curr )
			return;

		postOrderTraverse(curr.left, ls);
		postOrderTraverse(curr.right, ls);
		ls.addLast(curr.item);
	}

	public DoublyLinkedList<T> inOrderDFS() {
		var ls = new DoublyLinkedList<T>();
		inOrderTraverse(this.root, ls);
		return ls;
	}
	private void inOrderTraverse(Node curr, final DoublyLinkedList<T> ls) {
		if ( null == curr )
			return;

		inOrderTraverse(curr.left, ls);
		ls.addLast(curr.item);
		inOrderTraverse(curr.right, ls);
	}

// Breadth-First Search (BFS) / Level Order:
	public DoublyLinkedList<T> levelOrderBFS() {
		var q = new LinearQueue<Node>();
		var ls = new DoublyLinkedList<T>();

		q.enqueue( this.root );
		while ( !q.isEmpty() ) {
			Node n = q.dequeue();
			ls.addLast( n.item );

			if ( n.left != null )
				q.enqueue( n.left );

			if ( n.right != null )
				q.enqueue( n.right );
		}

		return ls;
	}


// Helper Methods:
	private boolean isRoot(Node n) {
		return n == this.root;
	}

	private boolean isBranch(Node n) {
		return (null != n.left) || (null != n.right);
	}

	private boolean isLeaf(Node n) {
		return (null == n.left) && (null == n.right);
	}

	private boolean isFull(Node n) {
		return (n.left != null) && (n.right != null);
	}

	private Node getMinValSuccessor(Node n) {
		Node parent = null;
		while (n.left != null) {
			parent = n;
			n = n.left;
		}

		parent.left = null;
		return n;
	}

	private void remove(Node parent, Node n, boolean isLeft) {	// Method overloading ...
		if ( isLeaf(n) ) {	// Node has no child (is leaf) ...
			if ( isRoot(n) )
				this.root = null;
			else
				if ( isLeft )
					parent.left = null;
				else
					parent.right = null;
		}
		else if ( !isFull(n) ) {	// Node has one child ...
			if ( isRoot(n) )
				this.root = n;

			if ( isLeft )
				parent.left = (n.left != null) ? n.left : n.right;
			else
				parent.right = (n.left != null) ? n.left : n.right;
		}
		else {	// Node has two children (is full) ...
			Node replacement = getMinValSuccessor(n);
				 replacement.left  = n.left;
				 replacement.right = n.right;

			if ( isRoot(n) )
				this.root = replacement;
			else
				if ( isLeft )
					parent.left = replacement;
				else
					parent.right = replacement;
		}

		n.item  = null;	// Help GC ...
		n.left  = null;
		n.right = null;
	}


// Print Method:
	@Override
	public String toString() {
		return this.preOrderDFS().toString();
	}
}

