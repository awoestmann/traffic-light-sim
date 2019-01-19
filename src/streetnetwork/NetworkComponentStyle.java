package streetnetwork;

import java.awt.Color;

import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;
import saf.v3d.scene.VSpatial;

public class NetworkComponentStyle extends DefaultStyleOGL2D {
	
	@Override
	public Color getColor(Object obj)
	{
		NetworkComponent agent = (NetworkComponent) obj;
		switch (agent.getComponentType()) {
			case AMPEL: 
				break;
			case AUTO: 
				Car auto = (Car) agent;
				return getCarColor(auto);
			case TILE: 
				NetworkTile tile = (NetworkTile) agent;
				return getTileColor(tile);
		}
		
		return Color.pink;
		/*
		switch (element.getState()){
		
		case Element.notFlammable:
			return Color.WHITE;		//nicht brennbares Element weiss.
		case Element.tree:
			return Color.GREEN;		//Baum ist gruen.
		case Element.fire:
			return Color.RED;		//Feuer ist rot.
		
		default:*/
		
	}

	private Color getCarColor(Car agent) {
		switch (agent.get_state()) {
			case DRIVING: 
				return Color.green;
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
				return Color.white;
			case BLOCK:
				return Color.gray;
			default:
				return Color.pink;
		}
	}
	
	@Override
	public VSpatial getVSpatial(Object agent, VSpatial spatial) {
		
	    //muss nur 1* aufgerufen werden, wenn noch keine Form festgelegt ist.
		if (spatial == null) {
	      spatial = shapeFactory.createRectangle(15, 15);	//Rechteck mit Groesse
	    }
	    return spatial;
	}
}
