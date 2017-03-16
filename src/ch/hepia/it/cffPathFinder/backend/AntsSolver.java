package ch.hepia.it.cffPathFinder.backend;

public class AntsSolver implements PathFinder {
	private static AntsSolver instance = new AntsSolver();

	private AntsSolver () {

	}

	public static AntsSolver getInstance () {
		return instance;
	}

	@Override
	public Path shortestPath (Graph g, Vertex v1, Vertex v2) {
		return null;
	}
}
