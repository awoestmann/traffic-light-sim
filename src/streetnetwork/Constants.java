package streetnetwork;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;

/**
 * Class handling constants and params.
 */
public final class Constants {

	/**
	 * Possible directions for traffic lights, cars etc.
	 */
	public enum Direction {UP, DOWN, LEFT, RIGHT};
	
	/**
	 * Possible directions a traffic light can be passed
	 */
	public enum TrafficLightDirection {HORIZONTAL, VERTICAL};

	/**
	 * Height of the simulation area
	 */
	public final static int SPACE_HEIGHT = 50;
	
	/**
	 * Width of the simulation area
	 */
	public final static int SPACE_WIDTH = 50;

	/**
	 * Time in ticks after which all car positions will be updated
	 */
	public static final float CAR_UPDATE_INTERVAL = 100;
		
	/**
	 * Time in ticks after which a car will be spawned at an endpoint tile
	 */
	public static final int SPAWN_RATE = 500;
	
	/**
	 * Time in ticks after which traffic light will switch
	 */
	private static int SWITCH_TIME = 5;
	
	/**
	 * True if simulation params have been read
	 */
	private static boolean initialized = false;
	
	/**
	 * Read simulation params and set init bool
	 */
	public static void readParams() {
		if (!initialized) {
			initialized = true;
			Parameters p = (Parameters) RunEnvironment.getInstance().getParameters();
			SWITCH_TIME = (int)((repast.simphony.parameter.Parameters) p).getValue("SWITCH_TIME");
		}		
	}
	
	public static int getSwitchTime() {
		readParams();
		return SWITCH_TIME;
	}
	
	private Constants() {}
}
