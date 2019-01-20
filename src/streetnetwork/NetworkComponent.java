package streetnetwork;

/**
 * Generic simulation component
 *
 */
public abstract class NetworkComponent {

	public static enum ComponentType {TRAFFIC_LIGHT, CAR, TILE, CROSSROAD};
	
	ComponentType componentType;
	
	public ComponentType getComponentType() {
		return componentType;
	}
}
