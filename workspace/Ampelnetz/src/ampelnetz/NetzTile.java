package ampelnetz;

import ampelnetz.AutoAgent.auto_state;
import repast.simphony.space.continuous.ContinuousSpace;

public class NetzTile extends AmpelnetzComponent{

	public enum tile_type {STREET, ENDPOINT, BLOCK};

	private tile_type tile_type;
	private ContinuousSpace<AmpelnetzComponent> space;
	
	public NetzTile(tile_type o_type, ContinuousSpace<AmpelnetzComponent> o_space)
	{
		space = o_space;
		tile_type = o_type;
		componentType = ComponentType.TILE;
	}

	public tile_type getTileType() {
		return tile_type;
	}
}
