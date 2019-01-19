package streetnetwork;

import streetnetwork.Constants.Direction;

public class TrafficLight extends NetworkComponent{
	
	private boolean open = false;
	public Direction direction; 
	
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
