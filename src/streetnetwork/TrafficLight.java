package streetnetwork;

import streetnetwork.Constants.Direction;

/**
 * A class modeling a traffic light.
 * Note that this class is just for the visual representation of the light.
 * The actual functionality is in the crossroad class.
 */
public class TrafficLight extends NetworkComponent{
	
	/**
	 * True if traffic light is currently green
	 */
	private boolean open = false;
	
	/**
	 * Direction cars can pass this light
	 */
	public Direction direction; 
	
	/**
	 * Constructor
	 * @param open True if light is green
	 * @param direction Direction cars can pass
	 */
	public TrafficLight(boolean open, Direction direction) {
		this.open = open;
		this.direction = direction;
		componentType = ComponentType.TRAFFIC_LIGHT;
	}
	
	public Direction getDirection() {
		return this.direction;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
}
