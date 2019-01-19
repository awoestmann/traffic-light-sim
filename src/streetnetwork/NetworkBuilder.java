package streetnetwork;

import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.SimpleCartesianAdder;
import streetnetwork.Car.CarState;
import streetnetwork.Car.Direction;

/**
 * Data loader class
 */
public class NetworkBuilder implements ContextBuilder<NetworkComponent>{

	public Context<NetworkComponent> build(Context<NetworkComponent> context) {
		
		Parameters p = (Parameters) RunEnvironment.getInstance().getParameters();
		
		int height = 50;
		int width = 50;
		
		double density = (Double) ((repast.simphony.parameter.Parameters) p).getValue("density");
		double speed = (Double) ((repast.simphony.parameter.Parameters) p).getValue("speed");
		
	
		ContinuousSpace<NetworkComponent> spaceBuilder = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null)
				.createContinuousSpace(
						"streetnetwork_id", context, new SimpleCartesianAdder<NetworkComponent>(),
				new repast.simphony.space.continuous.StrictBorders(),
				width, height);

		final int MIN_GAP = 2;
		int _min_gap = MIN_GAP;
		for (int i = 0; i < width; i++){
			for (int j = 0; j < height; j++){
				NetworkTile tile;
				if (i == 25 || j == 25) {
					tile = new NetworkTile(NetworkTile.tile_type.STREET, spaceBuilder);
					context.add(tile);
				} else {
					tile = new NetworkTile(NetworkTile.tile_type.BLOCK, spaceBuilder);
					context.add(tile);
				}
				spaceBuilder.moveTo(tile, i,j);
				
			}
		}
		NetworkTile t1 = new NetworkTile(NetworkTile.tile_type.ENDPOINT, spaceBuilder);
		context.add(t1);
		spaceBuilder.moveTo(t1, 25,0);
		
		Car auto = new Car(CarState.DRIVING, spaceBuilder, Direction.UP); 
		context.add(auto);
		spaceBuilder.moveTo(auto, 25, 0);
					
		
		/*
		for (int i = 0; i < width; i++){
			for (int j = 0; j < height; j++){
				//falls density groesser als Zufallszahl zwischen 0 und 1, dann Waldelement, sonst nicht brennbar.
				AutoAgent auto;
				if (density > RandomHelper.nextDoubleFromTo(0.0, 1.0)){
					auto = new AutoAgent(auto_state.HALTING, spaceBuilder);
					
				}	else {
					auto = new AutoAgent(auto_state.DRIVING, spaceBuilder);
				}
				
				context.add(auto);
				spaceBuilder.moveTo(auto, i, j);
			}
		}*/
		
		return context;
	}
}