package streetnetwork;

import java.util.List;
import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
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

	public Context<NetworkComponent> build(Context<NetworkComponent> context) {
		Constants.readParams();
		ContinuousSpace<NetworkComponent> spaceBuilder = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null)
				.createContinuousSpace(
						"streetnetwork_projection_id", context, new SimpleCartesianAdder<NetworkComponent>(),
				new repast.simphony.space.continuous.StrictBorders(),
				Constants.SPACE_WIDTH, Constants.SPACE_HEIGHT);

		final int MIN_GAP = 2;
		int _min_gap = MIN_GAP;
		for (int i = 0; i < Constants.SPACE_WIDTH; i++){
			for (int j = 0; j < Constants.SPACE_HEIGHT; j++){
				NetworkComponent tile;
				if (i == 25 && j == 25) {
					tile = new Crossing(tile_type.CROSSING, spaceBuilder,
						new Direction[] {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT},
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
					tile = new NetworkTile(tile_type.STREET, spaceBuilder);
					context.add(tile);
				} else {
					tile = new NetworkTile(tile_type.BLOCK, spaceBuilder);
					context.add(tile);
				}
				spaceBuilder.moveTo(tile, i,j);
				
			}
		}
		NetworkTile t1 = new NetworkTile(tile_type.ENDPOINT, spaceBuilder);
		context.add(t1);
		spaceBuilder.moveTo(t1, 25,0);
		
		Car auto = new Car(CarState.DRIVING, spaceBuilder, Direction.UP); 
		context.add(auto);
		spaceBuilder.moveTo(auto, 25, 0);
		return context;
	}
}