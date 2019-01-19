package streetnetwork;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;

public class Car extends NetworkComponent{
	
	public enum CarState {DRIVING, HALTING};
	public enum Direction {UP, DOWN, LEFT, RIGHT};

	
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
	
	
	
	
	public CarState get_state()
	{
		return state;
	}
	
	private ContinuousSpace<NetworkComponent> space;
	
	public Car(CarState o_state, ContinuousSpace<NetworkComponent> o_space, Direction direction)
	{
		space = o_space;
		state = o_state;
		_state = o_state;
		this.direction = direction;
		componentType = NetworkComponent.ComponentType.AUTO;
	}
	
	@ScheduledMethod(start= 1.0, interval= 1.0)
	public void step(){
		
		NdPoint currentPos = space.getLocation(this);
		double x = currentPos.getX();
		double y = currentPos.getY();
		double _x = x;
		double _y = y;
		
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
		
		Iterable<NetworkComponent> obj_in_front = space.getObjectsAt(_x,_y);
		
		for(NetworkComponent ac : obj_in_front)
		{
		
			switch(ac.componentType)
			{
			case AMPEL:
				// Frag Ampel ob man durch kann
				break;
			case AUTO:
				if(((Car) ac).get_state() == CarState.HALTING)
				{
					set_state(CarState.HALTING);
					_x = x;
					_y = y;
				}else
				{
					set_state(CarState.DRIVING);
				}
				break;
			case TILE:
				break;
			}
		}
		space.moveTo(this, _x, _y);
		
	}
	
	@ScheduledMethod(start = 1.5, interval = 1.0)
	public void update(){
		this.state = this._state;
	}
	
	/**
	 * Schreibt nur auf den zuk�nftigen Zustand!
	 * 
	 * @param _state
	 */
	public void set_state(CarState _state) {
		this._state = _state;
	}
}
