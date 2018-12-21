package ampelnetz;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.graph.Network;

public class AutoAgent extends AmpelnetzComponent{
	
	public enum auto_state {DRIVING, HALTING};
	public enum auto_direction {UP, DOWN, LEFT, RIGHT};

	
	// Jetztiger Zustand
	auto_state state;
	// Zuk�nftiger Zustand
	auto_state _state;
	
	auto_direction direction;
	
	
	
	
	public auto_state get_state()
	{
		return state;
	}
	
	private ContinuousSpace<AmpelnetzComponent> space;
	
	public AutoAgent(auto_state o_state, ContinuousSpace<AmpelnetzComponent> o_space, auto_direction direction)
	{
		space = o_space;
		state = o_state;
		_state = o_state;
		this.direction = direction;
		componentType = AmpelnetzComponent.ComponentType.AUTO;
	}
	
	@ScheduledMethod(start= 1.0, interval= 1.0)
	public void step(){
		NdPoint currentPos = space.getLocation(this);
		double x = currentPos.getX();
		double y = currentPos.getY();
		
		switch (direction) {
		case UP: 
			y += 1;
			break;
		case DOWN:
			y-= 1;
			break;
		case LEFT:
			x -= 1;
			break;
		case RIGHT:
			x += 1;
			break;
		}
		space.moveTo(this, x, y);
		
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
	public void set_state(auto_state _state) {
		this._state = _state;
	}
}
