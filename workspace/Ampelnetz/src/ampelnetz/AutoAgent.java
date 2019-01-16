package ampelnetz;

import java.util.Iterator;

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
		
		Iterable<AmpelnetzComponent> obj_in_front = space.getObjectsAt(_x,_y);
		
		for(AmpelnetzComponent ac : obj_in_front)
		{
		
			switch(ac.componentType)
			{
			case AMPEL:
				// Frag Ampel ob man durch kann
				break;
			case AUTO:
				if(((AutoAgent) ac).get_state() == auto_state.HALTING)
				{
					set_state(auto_state.HALTING);
					_x = x;
					_y = y;
				}else
				{
					set_state(auto_state.DRIVING);
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
	public void set_state(auto_state _state) {
		this._state = _state;
	}
}
