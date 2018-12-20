package ampelnetz;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.graph.Network;

public class AutoAgent extends AmpelnetzComponent{
	
	public enum auto_state {DRIVING, HALTING};

	
	// Jetztiger Zustand
	auto_state state;
	// Zuk�nftiger Zustand
	auto_state _state;
	
	
	public auto_state get_state()
	{
		return state;
	}
	
	private ContinuousSpace<AmpelnetzComponent> space;
	
	public AutoAgent(auto_state o_state, ContinuousSpace<AmpelnetzComponent> o_space)
	{
		space = o_space;
		state = o_state;
		_state = o_state;
		componentType = AmpelnetzComponent.ComponentType.AUTO;
	}
	
	@ScheduledMethod(start= 1.0, interval= 1.0)
	public void step(){
	
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
