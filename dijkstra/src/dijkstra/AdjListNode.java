package dijkstra;

/**
 * class to represent an entry in the adjacency list of a vertex in a graph
 */
public class AdjListNode {

	private int vertexIndex;
	private int distance;

	// could be other fields, for example representing
	// properties of the edge - weight, capacity, ...

	/* creates a new instance */
	public AdjListNode(int n, int d) {
		vertexIndex = n;
		distance = d;
	}

	public int getVertexIndex() {
		return vertexIndex;
	}

	public void setVertexIndex(int n) {
		vertexIndex = n;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int d) {
		distance = d;
	}

}
