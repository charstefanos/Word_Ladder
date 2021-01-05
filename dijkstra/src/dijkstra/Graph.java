package dijkstra;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * class to represent an undirected graph using adjacency lists
 */
public class Graph {

	private Vertex[] vertices; // the (array of) vertices
	private int numVertices = 0; // number of vertices

	// possibly other fields representing properties of the graph

	/**
	 * creates a new instance of Graph with n vertices
	 */
	public Graph(int n) {
		numVertices = n;
		vertices = new Vertex[n];
		for (int i = 0; i < n; i++)
			vertices[i] = new Vertex(i);
	}

	public int size() {
		return numVertices;
	}

	public Vertex getVertex(int i) {
		return vertices[i];
	}

	public void setVertex(int i) {
		vertices[i] = new Vertex(i);
	}

	/**
	 * visit vertex v, with predecessor index p, during a depth first traversal of
	 * the graph
	 */
	private void Visit(Vertex v, int p) {
		v.setVisited(true);
		v.setPredecessor(p);
		LinkedList<AdjListNode> L = v.getAdjList();
		for (AdjListNode node : L) {
			int n = node.getVertexIndex();
			if (!vertices[n].getVisited()) {
				Visit(vertices[n], v.getIndex());
			}
		}
	}

	/**
	 * carry out a depth first search/traversal of the graph
	 */
	public void dfs() {
		for (Vertex v : vertices)
			v.setVisited(false);
		for (Vertex v : vertices)
			if (!v.getVisited())
				Visit(v, -1);
	}

	/**
	 * dijkstra search with a vert vertex as the start and a destination vertex as
	 * the end
	 */
	public void dijkstra(Vertex vert, Vertex destination) {

		ArrayList<Vertex> q = new ArrayList<>();

		for (Vertex v : vertices) {
			v.setVisited(false);
			v.setDistance(Integer.MAX_VALUE);
			v.setPredecessor(-1);
		}

		vert.setDistance(0);
		q.add(vert);

		while (!q.isEmpty() && !destination.getVisited()) {
			//gets the vertex closest to the vert from the array list
			Vertex v = q.get(0);
			int minD = v.getDistance();
			for (int i = 0; i < q.size(); i++) {
				Vertex w = q.get(i);
				if (w.getDistance() < minD) {
					minD = w.getDistance();
					v = w;
				}
			}

			q.remove(v);
			v.setVisited(true);
			int distance = v.getDistance();

			for (AdjListNode x : v.getAdjList()) {
				Vertex w = getVertex(x.getVertexIndex());
				if (!w.getVisited() && !q.contains(w)) {
					q.add(w);
					int tempD = distance + x.getDistance();
					if (tempD < w.getDistance()) {
						w.setDistance(tempD);
						w.setPredecessor(v.getIndex());
					}
				}
			}
		}
	}

}
