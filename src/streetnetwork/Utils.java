package streetnetwork;

import repast.simphony.space.continuous.NdPoint;
import streetnetwork.Constants.Direction;

public final class Utils {
	
	public static NdPoint moveCoordinatesInDirection(NdPoint coord, Direction dir) {
		double x = coord.getX();
		double y = coord.getY();
		switch(dir) {
			case UP: y += 1; break;
			case DOWN: y -= 1; break;
			case LEFT: x -= 1; break;
			case RIGHT: x += 1; break;
		}
		return new NdPoint(x, y);
	}

	private Utils() {}

}
