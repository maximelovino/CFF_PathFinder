package ch.hepia.it.cffPathFinder.backend;

/**
 * A Stop is the Vertex in the case of our project
 */
public class Stop extends Vertex implements Comparable<Stop> {
	private float xCoord;
	private float yCoord;

	/**
	 * Main constructor for Stop
	 *
	 * @param name   The name of the stop
	 * @param xCoord The x coordinate of the Stop
	 * @param yCoord The y coordinate of the Stop
	 */
	public Stop (String name, int xCoord, int yCoord) {
		super(name);
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}


	/**
	 * @return The x coordinate of the Stop
	 */
	public float getxCoord () {
		return xCoord;
	}

	/**
	 * @return The y coordinate of the Stop
	 */
	public float getyCoord () {
		return yCoord;
	}

	/**
	 * @param obj    The object we're comparing to
	 * @return If this Stop is equal to obj (Both Stops with equal names)
	 */
	@Override
	public boolean equals (Object obj) {
		if (obj instanceof Stop) {
			Stop st = (Stop) obj;
			return this.getName().equals(st.getName());
		}
		return false;
	}

	/**
	 * @param o    The Stop we're comparing to
	 * @return    -1 if the Stop is smaller, 0 if equal, 1 otherwise (comparison is made on the names)
	 */
	@Override
	public int compareTo (Stop o) {
		return this.getName().compareTo(o.getName());
	}

	/**
	 * Setter for the x coordinate
	 * @param xCoord    The new x coordinate
	 */
	public void setxCoord(float xCoord) {
		this.xCoord = xCoord;
	}

	/**
	 * Setter for the y coordinate
	 * @param yCoord    The new y coordinate
	 */
	public void setyCoord(float yCoord) {
		this.yCoord = yCoord;
	}
}
