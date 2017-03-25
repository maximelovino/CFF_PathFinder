package ch.hepia.it.cffPathFinder.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class MapReader {

	public static List<Float[]> readMap(String file) {
		List<Float[]> points = new ArrayList<>();
		float maxx = 1, maxy = 1;
		float minx = Float.POSITIVE_INFINITY, miny = Float.POSITIVE_INFINITY;

		try {
			for (String s : Files.readAllLines(new File(file).toPath())) {
				String[] p = s.split(" ");
				Float[] ps = new Float[2];
				ps[0] = Float.parseFloat(p[0]);
				ps[1] = Float.parseFloat(p[1]);
				if(ps[0] > maxx) maxx = ps[0];
				if(ps[1] > maxy) maxy = ps[1];
				if(ps[0] < minx) minx = ps[0];
				if(ps[1] < miny) miny = ps[1];
				points.add(ps);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//normalize points (align at 0,0 and scale in [0,1])
		for (Float[] p : points) {
			p[0] -= minx;
			p[1] -= miny;
			p[0] /= (maxx-minx);
			p[1] /= (maxy-miny);
		}
		return points;
	}
	
}
