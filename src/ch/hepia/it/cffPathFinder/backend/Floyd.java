package ch.hepia.it.cffPathFinder.backend;

public class Floyd implements PathFinder {

	private Floyd () {

	}

	public Floyd getInstance () {
		return new Floyd();
	}

	@Override
	public Path shortestPath (Graph g, Vertex v1, Vertex v2) {
		return null;
	}
}
