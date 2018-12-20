package ampelnetz;

public abstract class AmpelnetzComponent {
	public static enum ComponentType {AMPEL, AUTO, TILE};
	ComponentType componentType;
	public ComponentType getComponentType() {
		return componentType;
	}

}
