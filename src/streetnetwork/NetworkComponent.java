package streetnetwork;

/**
 * Generic simulation component
 *
 */
public abstract class NetworkComponent {

	public static enum ComponentType {AMPEL, AUTO, TILE};
	
	ComponentType componentType;
	
	public ComponentType getComponentType() {
		return componentType;
	}
}
