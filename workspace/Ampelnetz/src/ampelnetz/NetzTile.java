package ampelnetz;

import ampelnetz.AutoAgent.auto_state;
import repast.simphony.space.continuous.ContinuousSpace;

public class NetzTile {

	public enum tile_type {STREET, ENDPOINT, BLOCK};

	private tile_type type;
	private ContinuousSpace<AutoAgent> space;
	
	public NetzTile(tile_type o_type, ContinuousSpace<AutoAgent> o_space)
	{
		space = o_space;
		type = o_type;
	}
}
