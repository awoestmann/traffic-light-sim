package streetnetwork;

import java.util.List;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import streetnetwork.Constants.Direction;
import streetnetwork.Constants.TrafficLightDirection;

/**
 * Factory class creating simulation grids
 */
public class GridFactory {
	
	/**
	 * Simulation context
	 */
	private Context<NetworkComponent> simContext;
	
	/**
	 * Simulation space
	 */
	private ContinuousSpace<NetworkComponent> simSpace;

	/**
	 * Creates a small demo grid
	 */
	public void createDemoGrid() {
		for (int i = 0; i < 50; i++){
			for (int j = 0; j < 50; j++){
				NetworkComponent tile;
				if (i == 25 && j == 25) {
					tile = new Crossroad(new Direction[] {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT},
						TrafficLightDirection.HORIZONTAL);
					simContext.add(tile);
					List<TrafficLight> horLights = ((Crossroad) tile).getHorizontalLights();
					List<TrafficLight> verLights = ((Crossroad) tile).getVerticalLights();
					for (TrafficLight t: horLights) {
						simContext.add(t);
						if (t.getDirection() == Direction.LEFT) {
							simSpace.moveTo(t, i + 0.5, j + 0.5);
						} else {
							simSpace.moveTo(t, i - 0.5, j - 0.5);
						}
					}
					for (TrafficLight t: verLights) {
						simContext.add(t);
						if (t.getDirection() == Direction.UP) {
							simSpace.moveTo(t, i + 0.5, j - 0.5);
						} else {
							simSpace.moveTo(t, j - 0.5, i + 0.5);
						}
					}
				} else if (i == 25 || j == 25) {
					tile = new StreetTile();
					simContext.add(tile);
				} else {
					tile = new NetworkTile();
					simContext.add(tile);
				}
				simSpace.moveTo(tile, i,j);
				
			}
		}
		NetworkTile t1 = new EndpointTile(Direction.UP);
		Utils.addComponent(t1, new NdPoint(25, 0));
		
		NetworkTile t2 = new EndpointTile(Direction.DOWN);
		Utils.addComponent(t2, new NdPoint(25, 50));
		
		NetworkTile t3 = new EndpointTile(Direction.LEFT);
		Utils.addComponent(t3, new NdPoint(50, 25));
		
		NetworkTile t4 = new EndpointTile(Direction.RIGHT);
		Utils.addComponent(t4, new NdPoint(0, 25));
		
		
	}
	
	/**
	 * Creates a grid of streets
	 */
	public void createGrid() {
		
	}
	
	/**
	 * Constructor
	 * @param context Context to add grid to
	 * @param space Space to add grid to
	 */
	public GridFactory(Context<NetworkComponent> context,
			ContinuousSpace<NetworkComponent> space) {
		if (context != null && space != null) {
			this.simContext = context;
			this.simSpace = space;
		} else {
			throw new IllegalArgumentException();
		}
	}

}
