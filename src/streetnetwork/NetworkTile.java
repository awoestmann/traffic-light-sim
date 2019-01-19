package streetnetwork;

import repast.simphony.space.continuous.ContinuousSpace;
import streetnetwork.NetworkComponent.ComponentType;

public class NetworkTile extends NetworkComponent{

	public enum tile_type {STREET, ENDPOINT, BLOCK, CROSSING};

	private tile_type tile_type;
	private ContinuousSpace<NetworkComponent> space;
	
	public NetworkTile(tile_type o_type, ContinuousSpace<NetworkComponent> o_space)
	{
		space = o_space;
		tile_type = o_type;
		componentType = ComponentType.TILE;
	}

	public tile_type getTileType() {
		return tile_type;
	}
}
