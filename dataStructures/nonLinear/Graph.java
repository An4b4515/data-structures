package dataStructures.nonLinear;

import java.util.List;
import dataStructures.linear.LinearQueue;
import dataStructures.linear.DoublyLinkedList;


public class Graph<T> {
	/* --------------------- Inner Class --------------------- */
	private class Edge {
		T destinationNode;
		double weight;

		Edge(T toNode, double weight) {
			this.destinationNode = toNode;
			this.weight = weight;
		}

		@Override
		public String toString() {
			return
				String.format (
					"%s:%.2f",
					this.destinationNode,
					this.weight
				);
		}
	}
	/* ------------------ End of Inner Class ------------------ */


// Fields:
	private boolean directed, weighted;
	private HashTable<T, DoublyLinkedList<Edge>> vertices = new HashTable<>(32);


// Constructors:
	public Graph() { }	// No-arg constructor ...

	public Graph(boolean directed) {	// Parameterized constructor ...
		this.directed = directed;
	}


// Info Methods:
	public int size() {
		return this.vertices.size();
	}

	public boolean isEmpty() {
		return this.vertices.isEmpty();
	}

	public boolean contains(T node) {
		return this.vertices.containsKey( node );
	}


// Control Methods:
	public void addEdge(T a, T b, double w) {
		this.vertices.putIfAbsent( a, new DoublyLinkedList<>() );
		this.vertices.putIfAbsent( b, new DoublyLinkedList<>() );

		this.vertices.get(a).addLast( new Edge(b, w) );
		if ( !this.directed )
			this.vertices.get(b).addLast( new Edge(a, w) );
	}

	public void removeEdge(T a, T b) {
		//TODO: code ...
	}


// Helper Methods:
	public DoublyLinkedList<T> bfs(T start) {
		var ls = new DoublyLinkedList<T>();

		var visited = new HashTable<T, Boolean>();
			visited.put(start, true);
		var q = new LinearQueue<T>();
			q.enqueue( start );

		while ( !q.isEmpty() ) {
			T node = q.dequeue();
			ls.addLast(node);

			for ( Edge e : this.vertices.get(node) )
				if ( !visited.containsKey(e.destinationNode) ) {
					q.enqueue( e.destinationNode );
					visited.put(e.destinationNode, true);
				}
		}

		return ls;
	}

  /**
   * Returns the weight of two edges if they are connected,
   * otherwise <b>NaN</b>.
   *
   * @return The weight of the two edges.
  */
	public double getEdgeWeight(T a, T b) {
		for ( Edge e : this.vertices.get(a) )
			if ( e.destinationNode.equals(b) )
				return e.weight;

		return Double.NaN;
	}

  /**
   * Returns the total weight of the provided graph path.
   *
   * <ul>
   *  <li>Provided path must be a <i>connected path</i></li>
   *  <li>The Nodes in the provided list, must exist in the {@code Graph}</li>
   * </ul>
   *
   * @return The total weight of the provided path.
   *
   * @throws {@code IllegalArgumentException} if provided path is disconnected ...
   * @throws {@code NullPointerException} if provided path has Nodes which do not exist in the Graph.
  */
	public double getPathWeight(List<T> path) {
		int n = path.size() - 1;
		double totalWeight = 0.0;

		for (int i = 0; i < n; ++i) {
			double edgeWeight =
				getEdgeWeight (
					path.get(i),
					path.get(i + 1)
				);

			if ( Double.isNaN(edgeWeight) )
				throw new IllegalArgumentException("Path provided is disconnected...");
		}

		return totalWeight;
	}


// Print Method:
	@Override
	public String toString() {
		if (this.isEmpty())
			return "{}";

		var sb = new StringBuilder( this.size() * 6 ).append('{');
		T[] nodes = this.vertices.getKeys();

		for ( T node : nodes ) {
			sb.append('\n')
        .append( node )
			  .append(": ")
			  .append( this.vertices.get(node) )
			  .append(',');
		}

		sb.deleteCharAt( sb.length() - 1 );
		return sb.append('\n').append('}').toString();
	}
}


