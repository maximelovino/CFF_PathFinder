package ch.hepia.it.cffPathFinder.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class implementing the Floyd algorithm
 */
public class Floyd implements PathFinder {
	private static Floyd instance = new Floyd();
	private static int[][] distances;
	private static Vertex[][] precedences;
	private static Map<Vertex, Integer> inverseCorrespondance;

	/**
	 * Default private constuctor
	 */
	private Floyd () {

	}

	/**
	 * @return The instance of the Floyd class
	 */
	public static Floyd getInstance () {
		return instance;
	}

	/**
	 * Method to get the shortest path between two Vertices using Floyd algorithm
	 *
	 * @param g  The Graph we're working on
	 * @param v1 The departure Vertex
	 * @param v2 The arrival Vertex
	 * @return The shortest path between v1 and v2
	 */
	@Override
	public Path shortestPath (Graph g, Vertex v1, Vertex v2) {
		shortestPath(g, v1, ViewType.NO_VIEW);
		Path p = new Path();
		int cost = distances[inverseCorrespondance.get(v1)][inverseCorrespondance.get(v2)];
		p.setCost(cost);
		while (v2 != null) {
			p.insertAtBeginning(v2);
			v2 = precedences[inverseCorrespondance.get(v2)][inverseCorrespondance.get(v1)];
		}
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
		inverseCorrespondance = new HashMap<>();
		List<Vertex> vertices = g.getVertices();

		distances = new int[vertices.size()][vertices.size()];
		precedences = new Vertex[vertices.size()][vertices.size()];
		for (int i = 0; i < vertices.size(); i++) {
			for (int j = 0; j < vertices.size(); j++) {
				distances[i][j] = Integer.MAX_VALUE;
				precedences[i][j] = null;
			}
		}

		for (int i = 0; i < vertices.size(); i++) {
			inverseCorrespondance.put(vertices.get(i), i);
			distances[i][i] = 0;
		}
		for (Edge e : g.getEdges()) {
			int vTemp1 = inverseCorrespondance.get(e.getV1());
			int vTemp2 = inverseCorrespondance.get(e.getV2());
			distances[vTemp1][vTemp2] = e.getCost();
			distances[vTemp2][vTemp1] = e.getCost();
			precedences[vTemp1][vTemp2] = e.getV2();
			precedences[vTemp2][vTemp1] = e.getV1();
		}

		for (int intermediate = 0; intermediate < vertices.size(); intermediate++) {
			for (int start = 0; start < vertices.size(); start++) {
				for (int end = 0; end < vertices.size(); end++) {
					if (!(distances[start][intermediate] == Integer.MAX_VALUE || distances[intermediate][end] == Integer.MAX_VALUE) && distances[start][end] > (distances[start][intermediate] + distances[intermediate][end])) {
						distances[start][end] = distances[start][intermediate] + distances[intermediate][end];
						distances[end][start] = distances[end][intermediate] + distances[intermediate][start];
						precedences[start][end] = precedences[start][intermediate];
						precedences[end][start] = precedences[end][intermediate];
					}
				}
			}
		}
		String toReturn = "";
		switch (viewType) {
			case COST_VIEW:
				for (int i = 0; i < distances.length; i++) {
					for (int j = 0; j < distances[i].length; j++) {
						toReturn += distances[i][j] == Integer.MAX_VALUE ? "inf" : distances[i][j];
						toReturn += "\t";
					}
					toReturn += "\n";
				}
				break;
			case PRECEDENCE_VIEW:
				for (int i = 0; i < precedences.length; i++) {
					for (int j = 0; j < precedences[i].length; j++) {
						toReturn += precedences[i][j] == null ? -1 : inverseCorrespondance.get(precedences[i][j]);
						toReturn += "\t";
					}
					toReturn += "\n";
				}
				break;
			default:
				break;
		}
		return toReturn;
	}
}
