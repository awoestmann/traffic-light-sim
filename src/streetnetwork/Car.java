package streetnetwork;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import streetnetwork.Constants.Direction;
import streetnetwork.Constants.TrafficLightDirection;

public class Car extends NetworkComponent{
	public enum CarState {DRIVING, HALTING};
	/**
	 * Current state
	 */
	CarState state;

	/**
	 * Future state
	 */
	CarState _state;
	
	/**
	 * Direction
	 */
	Direction direction;
	
	/**
	 * The space this agent is currently in
	 */
	private ContinuousSpace<NetworkComponent> space;
	
	
	public CarState get_state()
	{
		return state;
	}
	
	public Car(CarState o_state, ContinuousSpace<NetworkComponent> o_space, Direction direction)
	{
		space = o_space;
		state = o_state;
		_state = o_state;
		this.direction = direction;
		componentType = NetworkComponent.ComponentType.CAR;
	}

	/**
	 * Checks if car is allowed to pass a crossing with open direction dir.
	 * @param dir The open direction of the crossing
	 * @return True if allowed to pass
	 */
	private boolean allowedToPass(TrafficLightDirection dir) {
		if (dir == TrafficLightDirection.VERTICAL) {
			return this.direction == Direction.UP || this.direction == Direction.DOWN;
		} else {
			return this.direction == Direction.LEFT || this.direction == Direction.RIGHT;
		}
	}
	
	private Iterable<NetworkComponent> getObjectsInFront() {
		NdPoint currentPos = space.getLocation(this);
		double x = currentPos.getX();
		double y = currentPos.getY();
		switch (this.direction) {
		case UP: y+=1; break;
		case DOWN: y -= 1; break;
		case LEFT: x -= 1; break;
		case RIGHT: x += 1; break;
		}
		return space.getObjectsAt(x, y);
	}
	
	/**
	 * Move the car forward if the way is not blocked
	 */
	@ScheduledMethod(start= 1.0, interval= 1.0)
	public void step(){
		
		NdPoint currentPos = space.getLocation(this);
		double x = currentPos.getX();
		double y = currentPos.getY();
		double _x = x;
		double _y = y;
		
		Iterable<NetworkComponent> obj_in_front = getObjectsInFront();
		boolean allowedToMove = true;
		for(NetworkComponent ac : obj_in_front)
		{
		
			switch(ac.getComponentType())
			{
			case CAR:
				if(((Car) ac).get_state() == CarState.HALTING) {
					allowedToMove = false;
				}
				break;
			case CROSSING:
				Crossing crossing = (Crossing) ac;
				if (!allowedToPass(crossing.getOpenDirection())) {
					allowedToMove = false;
				}
				break;
			case TRAFFIC_LIGHT:
			case TILE:
				break;
			}
		}
		if (allowedToMove == true) {
			set_state(CarState.DRIVING);
			switch (direction) {
				case UP: 
					_y += 1;
					break;
				case DOWN:
					_y-= 1;
					break;
				case LEFT:
					_x -= 1;
					break;
				case RIGHT:
					_x += 1;
					break;
			}
			if (_x < Constants.SPACE_WIDTH && _y < Constants.SPACE_HEIGHT) {
				space.moveTo(this, _x, _y);
			} else {
				NetworkBuilder.removeComponent(this);
			}
		} else {
			set_state(CarState.HALTING);
		}
		
	}
	
	@ScheduledMethod(start = 1.5, interval = 1.0)
	public void update(){
		this.state = this._state;
	}
	
	/**
	 * Set the future state
	 * 
	 * @param _state
	 */
	public void set_state(CarState _state) {
		this._state = _state;
	}
}
