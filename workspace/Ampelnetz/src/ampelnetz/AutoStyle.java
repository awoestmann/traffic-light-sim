package ampelnetz;

import java.awt.Color;

import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;
import saf.v3d.scene.VSpatial;

public class AutoStyle extends DefaultStyleOGL2D {
	public Color getColor(Object agent)
	{
		AutoAgent element = (AutoAgent) agent;
		
		return Color.green;
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
	
	@Override
	public VSpatial getVSpatial(Object agent, VSpatial spatial) {
		
	    //muss nur 1* aufgerufen werden, wenn noch keine Form festgelegt ist.
		if (spatial == null) {
	      spatial = shapeFactory.createRectangle(15, 15);	//Rechteck mit Groesse
	    }
	    return spatial;
	}
}
