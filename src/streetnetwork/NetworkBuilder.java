package streetnetwork;

import java.util.List;
import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.SimpleCartesianAdder;
import streetnetwork.Car.CarState;
import streetnetwork.Constants.Direction;
import streetnetwork.Constants.TrafficLightDirection;
import streetnetwork.NetworkTile.tile_type;

/**
 * Data loader class
 */
public class NetworkBuilder implements ContextBuilder<NetworkComponent>{
	/**
	 * Simulation context
	 */
	private static Context<NetworkComponent> simContext;
	
	/**
	 * Simulation space
	 */
	private static ContinuousSpace<NetworkComponent> simSpace;
	
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
	
	public static NdPoint getCoordinatesOf(NetworkComponent component) {
		if (component == null) {
			return null;
		}
		return simSpace.getLocation(component);
	}

	/**
	 * Build initial simulation
	 * @param context Simulation context
	 */
	public Context<NetworkComponent> build(Context<NetworkComponent> context) {
		Constants.readParams();
		simSpace = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null)
				.createContinuousSpace(
						"streetnetwork_projection_id", context, new SimpleCartesianAdder<NetworkComponent>(),
				new repast.simphony.space.continuous.StrictBorders(),
				Constants.SPACE_WIDTH, Constants.SPACE_HEIGHT);
		simContext = context;

		for (int i = 0; i < Constants.SPACE_WIDTH; i++){
			for (int j = 0; j < Constants.SPACE_HEIGHT; j++){
				NetworkComponent tile;
				if (i == 25 && j == 25) {
					tile = new Crossroad(new Direction[] {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT},
						TrafficLightDirection.HORIZONTAL);
					context.add(tile);
					List<TrafficLight> horLights = ((Crossroad) tile).getHorizontalLights();
					List<TrafficLight> verLights = ((Crossroad) tile).getVerticalLights();
					for (TrafficLight t: horLights) {
						context.add(t);
						if (t.getDirection() == Direction.LEFT) {
							simSpace.moveTo(t, i + 0.5, j + 0.5);
						} else {
							simSpace.moveTo(t, i - 0.5, j - 0.5);
						}
					}
					for (TrafficLight t: verLights) {
						context.add(t);
						if (t.getDirection() == Direction.UP) {
							simSpace.moveTo(t, i + 0.5, j - 0.5);
						} else {
							simSpace.moveTo(t, j - 0.5, i + 0.5);
						}
					}
				} else if (i == 25 || j == 25) {
					tile = new StreetTile();
					context.add(tile);
				} else {
					tile = new NetworkTile();
					context.add(tile);
				}
				simSpace.moveTo(tile, i,j);
				
			}
		}
		NetworkTile t1 = new EndpointTile(Direction.UP);
		context.add(t1);
		simSpace.moveTo(t1, 25,0);
		
		return context;
	}
}