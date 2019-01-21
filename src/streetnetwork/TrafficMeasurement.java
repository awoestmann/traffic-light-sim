package streetnetwork;

import java.util.LinkedList;

import repast.simphony.engine.schedule.ScheduledMethod;

public final class TrafficMeasurement {
		
	private static LinkedList<CarCount> carCount = new LinkedList<CarCount>();
	private static int currentDriving = 0;
	private static int currentHalting = 0;

	/**
	 * Add a new counting entry between two ticks
	 */
	@ScheduledMethod(start = 0.5, interval = Constants.CAR_UPDATE_INTERVAL)
	public static void switchTick() {
		carCount.add(new CarCount());
	}

	public static LinkedList<CarCount> getCarCount() {
		return carCount;
	}
	
	public static void countCar(Car car) {
		CarCount current = carCount.getLast();
		switch (car.getState()) {
		case DRIVING:
			currentDriving++;
			current.addDriving();
			break;
		case HALTING:
			currentHalting++;
			current.addHalting();
			break;
		}
	}
	
	public static void removeCar(Car car) {
		CarCount current = carCount.getLast();
		switch (car.getState()) {
		case DRIVING:
			currentDriving--;
			current.removeDriving();
			break;
		case HALTING:
			currentHalting--;
			current.removeHalting();
			break;
		}
	}
	
	public static int getDrivingCars() {
		return currentDriving;
	}
	
	public static int getHaltingCars() {
		return currentHalting;
	}

	private TrafficMeasurement() {}
	
	/**
	 * Class storing the amount of driving and halting cars in a tick 
	 */
	static class CarCount {
		  private int driving;
		  private int halting;
		  public CarCount() {
			  this.driving = 0;
			  this.halting = 0;
		  }
		  public int getDriving() {
			  return this.driving;
		  }
		  public int getHalting() {
			  return this.halting;
		  }
		  public void addDriving() {
			  this.driving++;
		  }
		  public void removeDriving() {
			  this.driving--;
		  }
		  public void addHalting() {
			  this.halting++;
		  }
		  public void removeHalting() {
			  this.halting--;
		  }
	}

}
