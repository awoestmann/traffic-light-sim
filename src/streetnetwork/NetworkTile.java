package streetnetwork;

/**
 * A generic tile class, used as a block element
 * @author awoestmann
 *
 */
public class NetworkTile extends NetworkComponent{

	public enum tile_type {STREET, ENDPOINT, BLOCK, CROSSROAD};

	protected tile_type tile_type;	
	public NetworkTile()
	{
		componentType = ComponentType.TILE;
		this.tile_type = tile_type.BLOCK;
	}

	public tile_type getTileType() {
		return tile_type;
	}
}
