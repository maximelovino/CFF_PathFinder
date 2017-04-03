package ch.hepia.it.cffPathFinder.backend;

public interface PathFinder {
	
	Path shortestPath (Graph g, Vertex v1, Vertex v2);

	void shortestPath (Graph g, Vertex v1, ViewType viewType);
	
	public enum ViewType {
		NO_VIEW, COST_VIEW, PRECEDENCE_VIEW;
	}
}
