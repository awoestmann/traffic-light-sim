package streetnetwork;

import java.util.HashMap;
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
		Utils.addComponent(t2, new NdPoint(25, 49));
		
		NetworkTile t3 = new EndpointTile(Direction.LEFT);
		Utils.addComponent(t3, new NdPoint(49, 25));
		
		NetworkTile t4 = new EndpointTile(Direction.RIGHT);
		Utils.addComponent(t4, new NdPoint(0, 25));		
	}
	
	/**
	 * Creates an endpoint tile for the given coordinate
	 * @param x x Coordinate
	 * @param y y Coordinate
	 * @return EntpointTile instance
	 */
	private EndpointTile getEndpointTile(double x, double y) {
		if (x == 0) {
			return new EndpointTile(Direction.RIGHT);
		}
		if (x == Constants.SPACE_WIDTH - 1) {
			return new EndpointTile(Direction.LEFT);
		}
		if (y == 0) {
			return new EndpointTile(Direction.UP);
		}
		if (y == Constants.SPACE_HEIGHT -1) {
			return new EndpointTile(Direction.DOWN);
		}		
		return null;
	}
	
	/**
	 * Adds traffic lights to a given tile at given coordinates
	 * @param tile Crossroad tile
	 * @param coord Coordinates
	 */
	private void addTrafficLights(Crossroad tile, NdPoint coord) {
		double x = coord.getX();
		double y = coord.getY();
		List<TrafficLight> horLights = ((Crossroad) tile).getHorizontalLights();
		List<TrafficLight> verLights = ((Crossroad) tile).getVerticalLights();
		for (TrafficLight t: horLights) {
			if (t.getDirection() == Direction.LEFT) {
				Utils.addComponent(t, x + 2.5, y + 2.5);
			} else {
				Utils.addComponent(t, x - 2.5, y - 2.5);
			}
		}
		for (TrafficLight t: verLights) {
			if (t.getDirection() == Direction.UP) {
				Utils.addComponent(t, x + 2.5, y - 2.5);
			} else {
				Utils.addComponent(t, x - 2.5, y + 2.5);
			}
		}
	}
	
	/**
	 * Creates a grid of streets
	 */
	public void createGrid() {
		for (int x = 0; x < Constants.SPACE_WIDTH; x++) {
			for (int y = 0; y < Constants.SPACE_HEIGHT; y++) {
				boolean horStreet = y % Constants.HOR_STREET_DIST == 0 && y != 0;
				boolean vertStreet = x % Constants.VER_STREET_DIST == 0 && x != 0;
				boolean endpoint = x == 0 || x == Constants.SPACE_WIDTH - 1
						|| y == 0 || y == Constants.SPACE_HEIGHT - 1;

				NetworkTile tile = null;
				if (horStreet && vertStreet) {
					tile = new Crossroad(
							new Direction[] {
									Direction.UP, Direction.DOWN,
									Direction.LEFT, Direction.RIGHT},
							TrafficLightDirection.HORIZONTAL);
					addTrafficLights((Crossroad) tile, new NdPoint(x, y));
				} else if (horStreet ^ vertStreet) {
					if (endpoint) {
						tile = getEndpointTile(x, y);
					}else {
						tile = new StreetTile();
					}
				} else {
					tile = new NetworkTile();
				}
				Utils.addComponent(tile, new NdPoint(x, y));
			}
		}
		addCenterThroughputMeasurePoint("Vertical point");
	}
	
	/**
	 * Creates a grid including a "green wave" for all streets following the
	 * given direction.
	 * @param dir Direction to set the green wave up
	 */
	public void createGreenWaveGrid(TrafficLightDirection dir) {
		//Create a map to count the already set up lights for each wave
		HashMap<Integer, Integer> waves = new HashMap<Integer, Integer>();
		for (int x = 0; x < Constants.SPACE_WIDTH; x++) {
			for (int y = 0; y < Constants.SPACE_HEIGHT; y++) {
				boolean horStreet = y % Constants.HOR_STREET_DIST == 0 && y != 0;
				boolean vertStreet = x % Constants.VER_STREET_DIST == 0 && x != 0;
				boolean endpoint = x == 0 || x == Constants.SPACE_WIDTH - 1
						|| y == 0 || y == Constants.SPACE_HEIGHT - 1;

				NetworkTile tile = null;
				if (horStreet && vertStreet) {
					tile = new Crossroad(
							new Direction[] {
									Direction.UP, Direction.DOWN,
									Direction.LEFT, Direction.RIGHT},
							TrafficLightDirection.HORIZONTAL);
					int delay = 0;
					int key = -1;
					if (dir == TrafficLightDirection.HORIZONTAL && horStreet) {
						delay = Constants.VER_STREET_DIST;
						key = y;
					}					
					if (dir == TrafficLightDirection.VERTICAL && vertStreet) {
						delay = Constants.HOR_STREET_DIST;
						key = x;
					}
					Integer count = waves.get(key);
					if (count == null) {
						count = 0;
						waves.put(key, 0);
					}
					((Crossroad) tile).setDelay(count * delay);
					addTrafficLights((Crossroad) tile, new NdPoint(x, y));
					waves.computeIfPresent(key, (k, v) -> v + 1);
				} else if (horStreet ^ vertStreet) {
					if (endpoint) {
						tile = getEndpointTile(x, y);
					}else {
						tile = new StreetTile();
					}
				} else {
					tile = new NetworkTile();
				}
				Utils.addComponent(tile, new NdPoint(x, y));
			}
		}
		addCenterThroughputMeasurePoint("Green wave");
	}
	
	/**
	 * Adds a measure point at a predefined central point.
	 * @param name Name of the point
	 */
	public void addCenterThroughputMeasurePoint(String name) {
		NdPoint vertCoord = Constants.VER_MEASURE_POINT;
		ThroughputMeasurePoint vertPoint = new ThroughputMeasurePoint(vertCoord, name);
		Utils.addComponent(vertPoint, vertCoord);		
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
