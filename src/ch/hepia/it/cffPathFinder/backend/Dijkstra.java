package ch.hepia.it.cffPathFinder.backend;

public class Dijkstra implements PathFinder {
	private static Dijkstra instance = new Dijkstra();

	private Dijkstra () {
	}

	public static Dijkstra getInstance () {
		return instance;
	}

	public Path shortestPath (Graph g, Vertex v1, Vertex v2) {
		return null;
	}
}
