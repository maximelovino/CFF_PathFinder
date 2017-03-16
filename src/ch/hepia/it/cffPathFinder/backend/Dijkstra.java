package ch.hepia.it.cffPathFinder.backend;

public class Dijkstra implements PathFinder {

	private Dijkstra () {
	}

	public Dijkstra getInstance () {
		return new Dijkstra();
	}

	public Path shortestPath (Graph g, Vertex v1, Vertex v2) {
		return null;
	}
}
