package streetnetwork;

import java.util.List;
import java.util.LinkedList;

import repast.simphony.engine.schedule.ScheduledMethod;
import streetnetwork.Constants.Direction;
import streetnetwork.Constants.TrafficLightDirection;

public class Crossing extends NetworkComponent {
	
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
	 */
	public Crossing(Direction[] possibleDirections, TrafficLightDirection direction) {
		
		this.componentType = ComponentType.CROSSING;
		this.possibleDirections = possibleDirections;
		this.direction = direction;
		
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
	
	public List<TrafficLight> getVerticalLights() {
		return this.verticalLights;
	}
	
	public List<TrafficLight> getHorizontalLights() {
		return this.horizontalLights;
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
