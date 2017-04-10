package ch.hepia.it.cffPathFinder.backend;

/**
 * Interface providing definitions of methods that should be implemented by a PathFinder
 */
public interface PathFinder {
	/**
	 * Method to return the shortest path between two vertices
	 *
	 * @param g  The Graph we're working on
	 * @param v1 The departure Vertex
	 * @param v2 The arrival Vertex
	 * @return
	 */
	Path shortestPath (Graph g, Vertex v1, Vertex v2);

	/**
	 * Method that computes the shortest path for all Vertices from a departure Vertex
	 * Displays the results according to a ViewType
	 *
	 * @param g        The Graph we're working on
	 * @param v1       The departure Vertex
	 * @param viewType The ViewType we want
	 * @return A string with the output formatted according to ViewType
	 */
	String shortestPath (Graph g, Vertex v1, ViewType viewType);

	/**
	 * Enumeration of the ViewTypes available
	 */
	enum ViewType {
		NO_VIEW, COST_VIEW, PRECEDENCE_VIEW;
	}
}
