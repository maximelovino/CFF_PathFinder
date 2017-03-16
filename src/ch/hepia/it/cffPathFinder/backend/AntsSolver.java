package ch.hepia.it.cffPathFinder.backend;

public class AntsSolver implements PathFinder {
	private AntsSolver () {

	}

	public AntsSolver getInstance () {
		return new AntsSolver();
	}

	@Override
	public Path shortestPath (Graph g, Vertex v1, Vertex v2) {
		return null;
	}
}
