package ch.hepia.it.cffPathFinder.backend;

import java.util.*;

/**
 * Class implementing the Dijkstra algorithm
 */
public class Dijkstra implements PathFinder {
	private static Dijkstra instance = new Dijkstra();
	private static Map<Vertex, Integer> distances;
	private static Map<Vertex, Vertex> precedence;

	/**
	 * Default private constuctor
	 */
	private Dijkstra () {
	}

	/**
	 * @return The instance of the Dijkstra class
	 */
	public static Dijkstra getInstance () {
		return instance;
	}

	/**
	 * Method to get the shortest path between two Vertices using Dijkstra algorithm
	 *
	 * @param g  The Graph we're working on
	 * @param v1 The departure Vertex
	 * @param v2 The arrival Vertex
	 * @return The shortest path between v1 and v2
	 */
	public Path shortestPath (Graph g, Vertex v1, Vertex v2) {
		shortestPath(g, v1, ViewType.NO_VIEW);

		Path p = new Path();
		p.setCost(distances.get(v2));
		while (precedence.get(v2) != null) {
			p.insertAtBeginning(v2);
			v2 = precedence.get(v2);
		}
		p.insertAtBeginning(v2);
		return p;
	}

	/**
	 * Method to get the path to all Vertices from v1
	 *
	 * @param g        The Graph we're working on
	 * @param v1       The departure Vertex
	 * @param viewType The ViewType we want
	 * @return A string with the output formatted according to ViewType
	 */
	@Override
	public String shortestPath (Graph g, Vertex v1, ViewType viewType) {
		distances = new HashMap<>();
		precedence = new HashMap<>();
		Queue<Vertex> queue = new PriorityQueue<>(Comparator.comparing(o -> distances.get(o)));
		for (Vertex v : g.getVertices()) {
			distances.put(v, Integer.MAX_VALUE);
			precedence.put(v, null);
			queue.add(v);
		}
		//distance from source to source = 0
		distances.put(v1, 0);
		queue.remove(v1);
		queue.add(v1);

		while (!queue.isEmpty()) {
			Vertex v = queue.poll();
			if (distances.get(v) == Integer.MAX_VALUE) break;
			for (Edge e : g.edgesFromVertex(v)) {
				Vertex u = e.getOtherVertex(v);
				int alt = distances.get(v) + e.getCost();
				if (alt < distances.get(u)) {
					distances.put(u, alt);
					precedence.put(u, v);
					queue.remove(u);
					queue.add(u);
				}
			}
		}
		String toReturn = "";
		switch (viewType) {
			case COST_VIEW:
				for (Vertex v : g.getVertices()) {
					toReturn += "[" + v.toString() + ":" + distances.get(v) + "] ";
				}
				toReturn += "\n";
				break;
			case PRECEDENCE_VIEW:
				for (Vertex v : g.getVertices()) {
					if (!v.equals(v1)) {
						toReturn += "[" + precedence.get(v).toString() + "<-" + v.toString() + "] ";
					}
				}
				toReturn += "\n";
				break;
			default:
				break;
		}
		return toReturn;
	}

	public Map<Vertex, Integer> getDistances () {
		return distances;
	}

	public Map<Vertex, Vertex> getPrecedence () {
		return precedence;
	}
}
