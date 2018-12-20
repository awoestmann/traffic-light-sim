package ampelnetz;

import java.awt.Color;

import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;
import saf.v3d.scene.VSpatial;

public class AmpelnetzComponentStyle extends DefaultStyleOGL2D {
	
	public Color getColor(AmpelnetzComponent agent)
	{
		switch (agent.getComponentType()) {
			case AMPEL: 
				break;
			case AUTO: 
				AutoAgent auto = (AutoAgent) agent;
				return getAutoColor(auto);
			case TILE: 
				NetzTile tile = (NetzTile) agent;
				break;
		}
		AutoAgent element = (AutoAgent) agent;
		
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

	private Color getAutoColor(AutoAgent agent) {
		switch (agent.get_state()) {
			case DRIVING: 
				return Color.green;
			case HALTING:
				return Color.red;
			default:
				return Color.pink;

		}
	}

	private Color getTileColor(NetzTile tile) {
		switch (tile.getTileType()) {
			case STREET:
			case ENDPOINT:
				return Color.black;
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
