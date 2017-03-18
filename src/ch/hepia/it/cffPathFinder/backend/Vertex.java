package ch.hepia.it.cffPathFinder.backend;

public class Vertex {

	private final String name;

	public Vertex(String name) {
		this.name = name;
	}

	public String getName () {
		return name;
	}

	@Override
	public String toString () {
		return name;
	}
}
