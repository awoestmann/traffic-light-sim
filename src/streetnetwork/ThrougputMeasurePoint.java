package streetnetwork;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.NdPoint;
import streetnetwork.Car.CarState;
import streetnetwork.NetworkComponent.ComponentType;
/**
 * Class used for measuring the car througput at the given coordinates. 
 */
public class ThrougputMeasurePoint {
	
	private NdPoint coordinates;
	private String name;
	private long carsCounted;
	private long intervalsCounted;

	public ThrougputMeasurePoint(NdPoint coordinates, String name) {
		this.coordinates = coordinates;
		this.name = name;
		this.carsCounted = 0;
		this.intervalsCounted = 0;
	}
	
	public NdPoint getCoordinates() {
		return this.coordinates;
	}
	
	public void printResults() {
		float ratio = ((float) this.carsCounted) / ((float) this.intervalsCounted);
		System.out.println(
				String.format("Measure point \"%s\"(%d-%d): %d cars/%d updates: %d cars per update",
						this.name, this.coordinates.getX(), this.coordinates.getY(),
						this.carsCounted, this.intervalsCounted, ratio));
	}
	
	@ScheduledMethod(start = 1.5, interval = Constants.CAR_UPDATE_INTERVAL)
	public void update() {
		this.intervalsCounted++;
		Iterable<NetworkComponent> objects = Utils.getObjectsAt(this.coordinates);
		for (NetworkComponent cmp: objects) {
			if (cmp.getComponentType() == ComponentType.CAR) {
				Car car = (Car) cmp;
				if (car.getState() == CarState.DRIVING) {
					carsCounted++;
				}
			}
		}
	}
	
	

}
