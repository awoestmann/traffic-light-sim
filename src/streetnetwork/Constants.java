package streetnetwork;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;

public final class Constants {
	public enum Direction {UP, DOWN, LEFT, RIGHT};
	public enum TrafficLightDirection {HORIZONTAL, VERTICAL};	
	public final static int SPACE_HEIGHT = 50;
	public final static int SPACE_WIDTH = 50;
	private static int SWITCH_TIME = 5;
	
	public static void readParams() {
		Parameters p = (Parameters) RunEnvironment.getInstance().getParameters();
		SWITCH_TIME = (int)((repast.simphony.parameter.Parameters) p).getValue("SWITCH_TIME");
		
		//double density = (Double) ((repast.simphony.parameter.Parameters) p).getValue("density");
		//double speed = (Double) ((repast.simphony.parameter.Parameters) p).getValue("speed");
	}
	
	public static int getSwitchTime() {
		return SWITCH_TIME;
	}
}
