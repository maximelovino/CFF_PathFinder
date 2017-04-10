package ch.hepia.it.cffPathFinder.backend;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a Path in our Graph
 */
public class Path {

	private List<Vertex> path;
	private int cost;

	/**
	 * Default constructor for a Path, will create an empty path of cost 0
	 */
	public Path () {
		this.path = new ArrayList<>();
		this.cost = 0;
	}

	/**
	 * Method to insert a Vertex at the beginning of the Path
	 *
	 * @param v The Vertex to insert
	 */
	public void insertAtBeginning (Vertex v) {
		this.path.add(0, v);
	}

	/**
	 * Method to insert a Vertex at the end of the Path
	 *
	 * @param v The Vertex to insert
	 */
	public void insertAtEnd (Vertex v) {
		this.path.add(v);
	}

	/**
	 * @param v vertex to check
	 * @return true if the path contains the vertex, false otherwise
	 */
	public boolean contains (Vertex v) {
		return this.path.contains(v);
	}

	/**
	 * Setter for the cost of the Path
	 *
	 * @param cost The new cost
	 */
	public void setCost (int cost) {
		this.cost = cost;
	}

	/**
	 * @return The cost of the Path
	 */
	public int getCost () {
		return cost;
	}

	/**
	 * @return The list of Vertices forming the path
	 */
	public List<Vertex> getPath () {
		return path;
	}

	/**
	 * @return String representation of the Path
	 */
	@Override
	public String toString () {
		if (path.size() == 0) return "[]";
		String out = "[";
		for (int i = 0; i < path.size(); i++) {
			out += path.get(i).toString() + (i != path.size() - 1 ? ":" : "]");
		}
		return out;
	}
}
