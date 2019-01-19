package streetnetwork;

public class NetworkTile extends NetworkComponent{

	public enum tile_type {STREET, ENDPOINT, BLOCK, CROSSING};

	private tile_type tile_type;	
	public NetworkTile(tile_type o_type)
	{
		componentType = ComponentType.TILE;
		this.tile_type = o_type;
	}

	public tile_type getTileType() {
		return tile_type;
	}
}
