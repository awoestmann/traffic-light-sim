package streetnetwork;

import java.util.List;
import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.space.continuous.ContinuousSpace;
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
	
	/**
	 * Remove a component from the simulation.
	 * @param comp Component to remove
	 */
	public static void removeComponent(NetworkComponent comp) {
		simContext.remove(comp);
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
	 * Build initial simulation
	 * @param context Simulation context
	 */
	public Context<NetworkComponent> build(Context<NetworkComponent> context) {
		Constants.readParams();
		ContinuousSpace<NetworkComponent> spaceBuilder = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null)
				.createContinuousSpace(
						"streetnetwork_projection_id", context, new SimpleCartesianAdder<NetworkComponent>(),
				new repast.simphony.space.continuous.StrictBorders(),
				Constants.SPACE_WIDTH, Constants.SPACE_HEIGHT);
		simContext = context;

		for (int i = 0; i < Constants.SPACE_WIDTH; i++){
			for (int j = 0; j < Constants.SPACE_HEIGHT; j++){
				NetworkComponent tile;
				if (i == 25 && j == 25) {
					tile = new Crossing(new Direction[] {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT},
						TrafficLightDirection.HORIZONTAL);
					context.add(tile);
					List<TrafficLight> horLights = ((Crossing) tile).getHorizontalLights();
					List<TrafficLight> verLights = ((Crossing) tile).getVerticalLights();
					for (TrafficLight t: horLights) {
						context.add(t);
						if (t.getDirection() == Direction.LEFT) {
							spaceBuilder.moveTo(t, i + 0.5, j + 0.5);
						} else {
							spaceBuilder.moveTo(t, i - 0.5, j - 0.5);
						}
					}
					for (TrafficLight t: verLights) {
						context.add(t);
						if (t.getDirection() == Direction.UP) {
							spaceBuilder.moveTo(t, i + 0.5, j - 0.5);
						} else {
							spaceBuilder.moveTo(t, j - 0.5, i + 0.5);
						}
					}
				} else if (i == 25 || j == 25) {
					tile = new NetworkTile(tile_type.STREET);
					context.add(tile);
				} else {
					tile = new NetworkTile(tile_type.BLOCK);
					context.add(tile);
				}
				spaceBuilder.moveTo(tile, i,j);
				
			}
		}
		NetworkTile t1 = new NetworkTile(tile_type.ENDPOINT);
		context.add(t1);
		spaceBuilder.moveTo(t1, 25,0);
		
		Car c1 = new Car(CarState.DRIVING, spaceBuilder, Direction.UP); 
		context.add(c1);
		spaceBuilder.moveTo(c1, 25, 1);
		Car c2 = new Car(CarState.HALTING, spaceBuilder, Direction.UP); 
		context.add(c2);
		spaceBuilder.moveTo(c2, 25, 5);
		return context;
	}
}