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
	public final static int SPACE_HEIGHT = 250;
	
	/**
	 * Width of the simulation area
	 */
	public final static int SPACE_WIDTH = 250;
	
	/**
	 * Distance between two horizontal streets
	 */
	public final static int HOR_STREET_DIST = 15;
	
	/**
	 * Distance between two vertical streets
	 */
	public static final int VER_STREET_DIST = 50;

	/**
	 * Time in ticks after which all car positions will be updated
	 */
	public static final float CAR_UPDATE_INTERVAL = 1;
		
	/**
	 * Time in ticks after which a car will be spawned at an endpoint tile
	 */
	public static final int SPAWN_RATE = 5;
	
	/**
	 * Time in ticks after which traffic light will switch
	 */
	private static int SWITCH_TIME = 5;
	
	/**
	 * True if small demo grid shall be used
	 */
	private static boolean DEMO_GRID = false;
	
	/**
	 * True if green wave shall be used
	 */
	private static boolean GREEN_WAVE = true;

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
			
			SWITCH_TIME = (int)p.getValue("SWITCH_TIME");
			DEMO_GRID = (boolean) p.getValue("DEMO_GRID");
			GREEN_WAVE = (boolean) p.getValue("GREEN_WAVE");
		}		
	}
	
	public static int getSwitchTime() {
		readParams();
		return SWITCH_TIME;
	}
	
	public static boolean getDemoGrid() {
		readParams();
		return DEMO_GRID;
	}
	
	public static boolean getGreenWave() {
		readParams();
		return GREEN_WAVE;
	}
	
	private Constants() {}
}
