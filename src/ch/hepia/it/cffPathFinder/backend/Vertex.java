package ch.hepia.it.cffPathFinder.backend;

/**
 * Class implementing a Vertex in a Graph
 */
public class Vertex {
	private final String name;

	/**
	 * Main constructor for Vertex
	 *
	 * @param name The name of the Vertex
	 */
	public Vertex(String name) {
		this.name = name;
	}

	/**
	 * @return The name of the Vertex
	 */
	public String getName () {
		return name;
	}

	/**
	 * @return String representation of the Vertex
	 */
	@Override
	public String toString () {
		return name;
	}

	/**
	 * @param obj The object we're comparing to
	 * @return If the Vertex is equal to obj (Two vertices with equal name)
	 */
	@Override
	public boolean equals (Object obj) {
		if (obj instanceof Vertex) {
			Vertex v = (Vertex) obj;
			return this.name.equals(v.getName());
		}
		return false;
	}
}
