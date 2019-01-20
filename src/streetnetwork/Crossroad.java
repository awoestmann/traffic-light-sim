package streetnetwork;

import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;

import repast.simphony.engine.schedule.ScheduledMethod;
import streetnetwork.Constants.Direction;
import streetnetwork.Constants.TrafficLightDirection;

/**
 * Class modeling a crossing with a traffic light in each direction.
 */
public class Crossroad extends NetworkTile {
	
	/**
	 * All traffic lights for the horizontal part of the crossing
	 */
	private List<TrafficLight> verticalLights;
	
	/**
	 * All traffic light of the vertical part of the crossing
	 */
	private List<TrafficLight> horizontalLights;
	/**
	 * The direction on which cars are currently allowed to pass
	 */
	private TrafficLightDirection direction;
	
	/**
	 * Direction of all streets of this crossing
	 */
	private Direction[] possibleDirections;
	
	/**
	 * The probabilities of turning at this crossroad
	 */
	private HashMap<Direction, Float> turningProbabilities;
	
	/**
	 * Ticks since the traffic lights have been switched the last time
	 */
	private long ticksSinceLastSwitch = 0;	

	/**
	 * Constructor. Create a traffic light for every direction for which
	 * passing is possible for a car agent.
	 * @param o_type
	 * @param o_space
	 * @param possibleDirections
	 * @param direction
	 * @param turningProbabilities The probability to turn in each possible direction
	 */
	public Crossroad(Direction[] possibleDirections, TrafficLightDirection direction, float[] turningProbabilities) {
		super();
		this.componentType = ComponentType.CROSSROAD;
		this.possibleDirections = possibleDirections;
		this.direction = direction;		

		this.turningProbabilities = new HashMap<>();
		
		verticalLights = new LinkedList<TrafficLight>();
		horizontalLights = new LinkedList<TrafficLight>();

		for (Direction d : this.possibleDirections) {
			if (d == Direction.UP || d == Direction.DOWN) {
				this.verticalLights.add(new TrafficLight(isTrafficLightOpen(d), d));
			} else {
				this.horizontalLights.add(new TrafficLight(isTrafficLightOpen(d), d));
			}
		}
	}
	
	/**
	 * Constructor. Create a traffic light for every direction for which
	 * passing is possible for a car agent.
	 * @param o_type
	 * @param o_space
	 * @param possibleDirections
	 * @param direction
	 */
	public Crossroad(Direction[] possibleDirections, TrafficLightDirection direction) {
		this(possibleDirections, direction, new float[possibleDirections.length]);
		float probability = 1.0f / (float) possibleDirections.length;
		HashMap<Direction, Float> probs = new HashMap<>();
		for (int i = 0; i < possibleDirections.length; i++) {
			probs.put(possibleDirections[i], probability);
		}
		this.turningProbabilities = probs;
	}
	
	public List<TrafficLight> getVerticalLights() {
		return this.verticalLights;
	}
	
	public List<TrafficLight> getHorizontalLights() {
		return this.horizontalLights;
	}
	
	public HashMap<Direction, Float> getTurningProbabilities() {
		return this.turningProbabilities;
	}
	
	public Direction[] getPossibleDirections() {
		return this.possibleDirections;
	}
	
	/**
	 * Checks if a traffic light with direction tlDirection is open at the
	 * current state of the crossing
	 * @param tlDirection Direction of the traffic light
	 * @return True if open;
	 */
	private boolean isTrafficLightOpen(Direction tlDirection) {
		if (this.direction == TrafficLightDirection.HORIZONTAL) {
			return tlDirection == Direction.LEFT || tlDirection == Direction.RIGHT;
		} else {
			return tlDirection == Direction.UP || tlDirection == Direction.DOWN;
		}
	}
	
	/**
	 * Returns the current open direction
	 * @return The direction
	 */
	public TrafficLightDirection getOpenDirection() {
		return this.direction;
	}
	
	/**
	 * Toggles open direction
	 */
	private void toggle()  {
		if (this.direction == TrafficLightDirection.VERTICAL) {
			this.direction = TrafficLightDirection.HORIZONTAL;
		} else {
			this.direction = TrafficLightDirection.VERTICAL;
		}
	}
	
	/**
	 * Step method, toggles traffic lights after a constant number ticks
	 */
	@ScheduledMethod(start= 1.0, interval= 1.0)
	public void update(){
		ticksSinceLastSwitch++;
		if (ticksSinceLastSwitch == Constants.getSwitchTime()) {
			ticksSinceLastSwitch = 0;
			toggle();
			boolean vertical = this.direction == TrafficLightDirection.VERTICAL;
			for (TrafficLight t : verticalLights) {
				t.setOpen(vertical);
			}
			for (TrafficLight t: horizontalLights) {
				t.setOpen(!vertical);
			}
		}
	}
}
