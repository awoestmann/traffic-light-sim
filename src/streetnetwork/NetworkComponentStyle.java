package streetnetwork;

import java.awt.Color;

import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;
import saf.v3d.scene.VSpatial;
import streetnetwork.NetworkComponent.ComponentType;

public class NetworkComponentStyle extends DefaultStyleOGL2D {
	
	@Override
	public Color getColor(Object obj)
	{
		NetworkComponent agent = (NetworkComponent) obj;
		switch (agent.getComponentType()) {
			case TRAFFIC_LIGHT:
				TrafficLight light = (TrafficLight) agent;
				return getTrafficLightColor(light);
			case CAR: 
				Car auto = (Car) agent;
				return getCarColor(auto);
			case TILE: 
				NetworkTile tile = (NetworkTile) agent;
				return getTileColor(tile);
			case CROSSROAD:
				return Color.blue;
			default: return Color.pink;
		}
	}

	private Color getCarColor(Car agent) {
		switch (agent.get_state()) {
			case DRIVING: 
				return Color.white;
			case HALTING:
				return Color.red;
			default:
				return Color.pink;

		}
	}

	private Color getTileColor(NetworkTile tile) {
		switch (tile.getTileType()) {
			case STREET:
				return Color.black;
			case ENDPOINT:
				return Color.cyan;
			case BLOCK:
				return Color.gray;
			default:
				return Color.pink;
		}
	}

	private Color getTrafficLightColor(TrafficLight light) {
		return light.isOpen() ? Color.green: Color.red;
	}
	
	@Override
	public VSpatial getVSpatial(Object agent, VSpatial spatial) {
		
		ComponentType type = ((NetworkComponent) agent).getComponentType();
		
		if (spatial == null) {
			switch (type) {
			case TRAFFIC_LIGHT:
				spatial = shapeFactory.createCircle(7.5f, 8, true);
				break;
			case CAR:
			default:
				spatial = shapeFactory.createRectangle(15, 15);
			}	      
	    }
	    return spatial;
	}
}
