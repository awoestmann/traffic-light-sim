package streetnetwork;

import java.util.List;
import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.SimpleCartesianAdder;
import streetnetwork.Constants.Direction;
import streetnetwork.Constants.TrafficLightDirection;

/**
 * Data loader class
 */
public class NetworkBuilder implements ContextBuilder<NetworkComponent>{

	/**
	 * Simulation context
	 */
	private Context<NetworkComponent> simContext;
	
	/**
	 * Simulation space
	 */
	private ContinuousSpace<NetworkComponent> simSpace;

	/**
	 * Grid factory
	 */
	private GridFactory factory;
	
	/**
	 * Build initial simulation
	 * @param context Simulation context
	 */
	public Context<NetworkComponent> build(Context<NetworkComponent> context) {
		Constants.readParams();
		simSpace = ContinuousSpaceFactoryFinder
				.createContinuousSpaceFactory(null)
				.createContinuousSpace(
						"streetnetwork_projection_id",
						context,
						new SimpleCartesianAdder<NetworkComponent>(),
						new repast.simphony.space.continuous.StrictBorders(),
						Constants.SPACE_WIDTH,
						Constants.SPACE_HEIGHT);
		simContext = context;
		//Init utils and grid factory
		Utils.init(simContext, simSpace);
		this.factory = new GridFactory(simContext, simSpace);

		if (Constants.getDemoGrid()) {
			this.factory.createDemoGrid();
		} else {
			this.factory.createGrid();
		}
		
		return context;
	}
}