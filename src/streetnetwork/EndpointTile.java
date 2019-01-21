package streetnetwork;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.NdPoint;
import streetnetwork.Car.CarState;
import streetnetwork.Constants.Direction;

/**
 * Street network tile modeling an endpoint.
 * This tile spawns cars after a predefined interval.
 */
public class EndpointTile extends NetworkTile {

	private NdPoint position;
	private Direction carDirection;
	
	public EndpointTile() {
		super();
		this.tile_type = tile_type.ENDPOINT;
		this.position = Utils.getCoordinatesOf(this);
	}
	
	/**
	 * Constructor.
	 * @param carDirection Driving direction of spawned cars
	 */
	public EndpointTile(Direction carDirection) {
		this();
		this.carDirection = carDirection;
	}
	
	/**
	 * Checks if spawning if blocked. This tile is see as blocked if a car
	 * is already present that has the same driving direction as the car that
	 * is going to be spawned
	 * @return True if tile is blocked
	 */
	private boolean isTileBlocked() {
		this.position = Utils.getCoordinatesOf(this);
		Iterable<NetworkComponent> objects = Utils.getObjectsAt(position);
		boolean blocked = false;
		for (NetworkComponent comp: objects) {
			if (comp.getComponentType() == ComponentType.CAR) {
				Car car = (Car) comp;
				blocked = car.getDirection() == this.carDirection ? 
						true: blocked;
			}
		}
		return blocked;
	}
	
	/**
	 * Spawns a new car
	 */
	@ScheduledMethod(start= 5.0, interval= Constants.SPAWN_RATE)
	public void spawn() {
		this.position = Utils.getCoordinatesOf(this);
		if (!isTileBlocked()) {
			Car newCar = new Car(CarState.DRIVING, this.carDirection);
			TrafficMeasurement.countCar(newCar);
			Utils.addComponent(newCar, this.position);
		}
	}
}