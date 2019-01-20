package streetnetwork;

import java.util.HashMap;
import java.util.Random;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.NdPoint;
import streetnetwork.Constants.Direction;
import streetnetwork.Constants.TrafficLightDirection;

public class Car extends NetworkComponent{

	public enum CarState {DRIVING, HALTING, TURNING};

	/**
	 * Current state
	 */
	CarState state;

	/**
	 * Future state
	 */
	CarState _state;
	
	/**
	 * Direction
	 */
	private Direction direction;
	
	/**
	 * Next direction if encountering a crossroad
	 */
	private Direction nextDirection;
	
	/**
	 * Speed in tiles per update
	 */
	private final float speed = Constants.getSpeed();
	
	public CarState get_state()
	{
		return state;
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public Car(CarState o_state, Direction direction)
	{
		this.state = o_state;
		this._state = o_state;
		this.direction = direction;
		this.nextDirection = null;
		this.componentType = NetworkComponent.ComponentType.CAR;
	}

	/**
	 * Checks if car is allowed to pass a crossing with open direction dir.
	 * @param dir The open direction of the crossing
	 * @return True if allowed to pass
	 */
	private boolean allowedToPass(TrafficLightDirection dir) {
		if (dir == TrafficLightDirection.VERTICAL) {
			return this.direction == Direction.UP || this.direction == Direction.DOWN;
		} else {
			return this.direction == Direction.LEFT || this.direction == Direction.RIGHT;
		}
	}
	
	/**
	 * Returns a list of all objects in one tile distance in front of this car
	 * @return List of objects in front
	 */
	private Iterable<NetworkComponent> getObjectsInFront() {
		NdPoint currentPos = NetworkBuilder.getCoordinatesOf(this);
		double x = currentPos.getX();
		double y = currentPos.getY();
		switch (this.direction) {
			case UP: y+=1; break;
			case DOWN: y -= 1; break;
			case LEFT: x -= 1; break;
			case RIGHT: x += 1; break;
		}
		return NetworkBuilder.getObjectsAt(x, y);
	}
	
	/**
	 * Removes the direction oposite to the current driving direction from the
	 * input array and returns it.
	 * @param possibleCrossroadDirections Possible directions of the crossroad ahead.
	 * @return The directions without the opposite driving direction
	 */
	private Direction[] getPossibleTurningDirections(Direction[] possibleCrossroadDirections) {
		int directionCount = possibleCrossroadDirections.length;
		Direction[] possibleTurningDirections = new Direction[directionCount - 1];
		int turningDirectionIndex = 0;

		for (int i = 0; i < possibleCrossroadDirections.length; i ++) {
			Direction dir = possibleCrossroadDirections[i];
			switch (this.direction) {
			case UP: 
				if (dir != Direction.DOWN) {
					possibleTurningDirections[turningDirectionIndex++] = dir;
				}
				break;
			case DOWN:
				if (dir != Direction.UP) {
					possibleTurningDirections[turningDirectionIndex++] = dir;
				}
				break;
			case LEFT:
				if (dir != Direction.RIGHT) {
					possibleTurningDirections[turningDirectionIndex++] = dir;
				}
				break;
			case RIGHT:
				if (dir != Direction.LEFT) {
					possibleTurningDirections[turningDirectionIndex++] = dir;
				}
				break;
			}
		}
		return possibleTurningDirections;
	}
	
	/**
	 * Picks the next direction at a crossroad and prepares the car for a turn.
	 * @param crossroad Crossroad to turn at
	 */
	private void pickNextDirection(Crossroad crossroad) {
		//Pick a random float between 0 and 1
		Random r = new Random();
		float random = r.nextFloat();
		
		//Get possible turning directions and probabilities
		Direction[] possibleDirections = getPossibleTurningDirections(
				crossroad.getPossibleDirections());
		HashMap<Direction, Float> probs = crossroad.getTurningProbabilities();
		
		//As the current direction had to be removed from the possible directions,
		//add its probability to all other probabilities
		float removedProb = probs.get(this.direction);
		float probAddition = removedProb / (float) possibleDirections.length;

		//Pick the direction matching the random number
		float lastValue = 0.0f;
		for (int i = 0; i < possibleDirections.length; i++) {
			float currentValue = lastValue + probs.get(possibleDirections[i]) + probAddition;
			if (random >= lastValue && random < currentValue) {
				this.nextDirection = possibleDirections[i];
				System.out.println(this.nextDirection);
				return;
			}
			lastValue = currentValue;
		}
	}
	
	/**
	 * Check if this car would be out of bounds with the given coordinates
	 * @param x X Coordinate
	 * @param y y Coordinate
	 * @return True if not out of bounds
	 */
	private boolean isNotOutOfBounds(double x, double y) {
		switch (this.direction) {
			case UP: return y < Constants.SPACE_HEIGHT - 1;
			case DOWN: return y > 1;
			case LEFT: return x > 1;
			case RIGHT: return x < Constants.SPACE_WIDTH - 1;
			default: return false;
		}
	}
	
	/**
	 * Move the car forward if the way is not blocked
	 */
	@ScheduledMethod(start= 1.0, interval= Constants.CAR_UPDATE_INTERVAL)
	public void step(){
		
		NdPoint currentPos = NetworkBuilder.getCoordinatesOf(this);
		double x = currentPos.getX();
		double y = currentPos.getY();
		double _x = x;
		double _y = y;
		
		Iterable<NetworkComponent> obj_in_front = getObjectsInFront();
		boolean allowedToMove = true;
		Crossroad crossroadToTurn = null;
		for(NetworkComponent ac : obj_in_front) {		
			switch (ac.getComponentType()) {
				case CAR:
					if(((Car) ac).get_state() == CarState.HALTING) {
						allowedToMove = false;
					}
					break;
				case CROSSROAD:
					Crossroad crossroad = (Crossroad) ac;
					if (!allowedToPass(crossroad.getOpenDirection())) {
						allowedToMove = false;
					} else {
						crossroadToTurn = crossroad;
					}
					break;
				default: break;
			}
		}
		if (allowedToMove == true) {
			set_state(CarState.DRIVING);
			if (this.nextDirection != null) {
				this.direction = this.nextDirection;
				this.nextDirection = null;
			}
			switch (direction) {
				case UP: 
					_y += speed;
					break;
				case DOWN:
					_y-= speed;
					break;
				case LEFT:
					_x -= speed;
					break;
				case RIGHT:
					_x += speed;
					break;
			}
			if (isNotOutOfBounds(_x, _y)) {
				NetworkBuilder.moveComponentTo(this, _x, _y);
				if (crossroadToTurn != null) {
					pickNextDirection(crossroadToTurn);
				}
			} else {
				NetworkBuilder.removeComponent(this);
			}
		} else {
			set_state(CarState.HALTING);
		}		
	}
	
	@ScheduledMethod(start = 1.5, interval = Constants.CAR_UPDATE_INTERVAL)
	public void update(){
		this.state = this._state;
	}
	
	/**
	 * Set the future state
	 * 
	 * @param _state
	 */
	public void set_state(CarState _state) {
		this._state = _state;
	}
}
