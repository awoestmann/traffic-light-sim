package streetnetwork;

/**
 * A generic tile class, used as a block element
 * @author awoestmann
 *
 */
public class NetworkTile extends NetworkComponent{

	public enum TileType {STREET, ENDPOINT, BLOCK, CROSSROAD};

	protected TileType tile_type;	
	public NetworkTile()
	{
		componentType = ComponentType.TILE;
		this.tile_type = TileType.BLOCK;
	}

	public TileType getTileType() {
		return tile_type;
	}
}
