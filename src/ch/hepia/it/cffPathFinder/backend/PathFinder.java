package ch.hepia.it.cffPathFinder.backend;

public interface PathFinder {
	Path shortestPath (Graph g, Vertex v1, Vertex v2);
}
