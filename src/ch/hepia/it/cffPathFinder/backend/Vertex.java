package ch.hepia.it.cffPathFinder.backend;

public class Vertex {
	private final Stop stop;

	public Vertex (Stop stop) {
		this.stop = stop;
	}

	public Stop getStop () {
		return stop;
	}

	@Override
	public String toString () {
		return stop.toString();
	}
}
