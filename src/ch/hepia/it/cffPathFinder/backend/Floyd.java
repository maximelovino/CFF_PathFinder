package ch.hepia.it.cffPathFinder.backend;

public class Floyd implements PathFinder {
	private static Floyd instance = new Floyd();

	private Floyd () {

	}

	public static Floyd getInstance () {
		return instance;
	}

	@Override
	public Path shortestPath (Graph g, Vertex v1, Vertex v2) {
		return null;
	}
}
