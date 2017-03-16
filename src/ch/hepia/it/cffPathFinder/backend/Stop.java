package ch.hepia.it.cffPathFinder.backend;

public class Stop {
	private final String name;
	private final int xCoord;
	private final int yCoord;

	public Stop (String name, int xCoord, int yCoord) {
		this.name = name;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}

	public String getName () {
		return name;
	}

	public int getxCoord () {
		return xCoord;
	}

	public int getyCoord () {
		return yCoord;
	}

	@Override
	public String toString () {
		return "Stop{" +
				"name='" + name + '\'' +
				", xCoord=" + xCoord +
				", yCoord=" + yCoord +
				'}';
	}
}
