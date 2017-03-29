package ch.hepia.it.cffPathFinder.backend;

public interface PathFinder {
	int NO_VIEW = 0;
	Path shortestPath (Graph g, Vertex v1, Vertex v2);

	void shortestPath (Graph g, Vertex v1, int viewType);
}
