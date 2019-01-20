package streetnetwork;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import streetnetwork.Constants.Direction;

/**
 * Utility class containing functions to move, add and remove simulation components.
 */
public final class Utils {
	
	/**
	 * Simulation context
	 */
	private static Context<NetworkComponent> simContext;
	
	/**
	 * Simulation space
	 */
	private static ContinuousSpace<NetworkComponent> simSpace;
	
	private static boolean initialized = false;
	
	/**
	 * Moves a component to a position
	 * @param comp Component to move
	 * @param pos Position to move to
	 */
	public static void moveComponentTo(NetworkComponent comp, NdPoint pos) {
		moveComponentTo(comp, pos.getX(), pos.getY());
	}
	
	/**
	 * Moves a component to a position
	 * @param comp Component to move to
	 * @param x x Coordinate to move to
	 * @param y y Coordinate to move to
	 */
	public static void moveComponentTo(NetworkComponent comp, double x, double y) {
		simSpace.moveTo(comp, x, y);
	}
	
	/**
	 * Remove a component from the simulation.
	 * @param comp Component to remove
	 */
	public static void removeComponent(NetworkComponent comp) {
		simContext.remove(comp);
	}
	
	/**
	 * Returns an iterable of all objects at the given coordinates
	 * @param position Position
	 * @return Iterable of NetworkComponents
	 */
	public static Iterable<NetworkComponent> getObjectsAt(NdPoint position) {
		return getObjectsAt(position.getX(), position.getY());
	}
	
	/**
	 * Returns a iterable of all objects at the given coordinates
	 * @param x X Coordinate
	 * @param y Y Coordinate
	 * @return Iterable of NetworkComponents
	 */
	public static Iterable<NetworkComponent> getObjectsAt(double x, double y) {
		return simSpace.getObjectsAt(x, y);
	}
	
	/**
	 * Add c component at a specific position to the simulation
	 * @param component Component to add
	 * @param position Position
	 */
	public static void addComponent(NetworkComponent component, NdPoint position) {
		if (position != null) {
			addComponent(component, position.getX(), position.getY());
		} else {
			throw new IllegalArgumentException("Invalid position");
		}		
	}
	
	/**
	 * Add a component at specific coordinates to the simulation
	 * @param comp Component to add
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public static void addComponent(NetworkComponent comp, double x, double y) {
		simContext.add(comp);
		simSpace.moveTo(comp, x,y);
	}
	
	/**
	 * Returns coordinates of a component
	 * @param component Component return coordinates of
	 * @return Coordinates as NdPoint
	 */
	public static NdPoint getCoordinatesOf(NetworkComponent component) {
		if (component == null) {
			return null;
		}
		return simSpace.getLocation(component);
	}
	
	/**
	 * Moves a set of coordinates 1 tile in a given direction
	 * @param coord Coordinates to move
	 * @param dir Direction to move to
	 * @return New point as NdPoint
	 */
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
	
	/**
	 * Initialize tools with simulation space and context.
	 * @param context Simulation context
	 * @param space Simulation space
	 */
	public static void init(Context<NetworkComponent> context,
			ContinuousSpace<NetworkComponent> space) {

		if (context != null && space != null && !initialized) {
			simContext = context;
			simSpace = space;
			initialized = true;
		} else {
			throw new IllegalArgumentException("Context or space null");
		}
		
	}

	private Utils() {}

}
