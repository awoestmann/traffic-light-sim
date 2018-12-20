package ampelnetz;


import ampelnetz.AutoAgent.auto_state;
import ampelnetz.NetzTile.tile_type;
import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.SimpleCartesianAdder;

public class NetzBuilder implements ContextBuilder<AmpelnetzComponent>{

	public Context<AmpelnetzComponent> build(Context<AmpelnetzComponent> context) {
		
		//Parameter, die vor der Simulation im GUI geaendert werden koennen.
		Parameters p = (Parameters) RunEnvironment.getInstance().getParameters();
		
		int height = 50;
		int width = 50;
		
		//int height = (Integer)((repast.simphony.parameter.Parameters) p).getValue("gridHeight");
		//int width = (Integer)((repast.simphony.parameter.Parameters) p).getValue("gridWidth");
		// Wie viele Auto's spawnen
		double density = (Double) ((repast.simphony.parameter.Parameters) p).getValue("density");
		// Wie schnell sind die Autos?
		double speed = (Double) ((repast.simphony.parameter.Parameters) p).getValue("speed");
		// Wie wahrscheinlich eine neue Stra�e erstellt wird beim generieren des Netzwerkes
		//double branching = (Double)  ((repast.simphony.parameter.Parameters) p).getValue("branching");
		//String netzwerktyp = (String)  ((repast.simphony.parameter.Parameters) p).getValue("netzwerktyp");
		
		
		//erstellt das Grid namens "Forest". Grid mit "sticky borders" (an Raendern wie eine Wand). 
		//Keine Doppelbesetzung der Gridzellen moeglich.
		
		ContinuousSpace<AmpelnetzComponent> spaceBuilder = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null)
				.createContinuousSpace(
						"ampelnetzwerk_id", context, new SimpleCartesianAdder<AmpelnetzComponent>(),
				new repast.simphony.space.continuous.StrictBorders(),
				width, height);
		
		//Erstelle Stra�ennetz
		final int MIN_GAP = 2;
		int _min_gap = MIN_GAP;
		for (int i = 0; i < width; i++){
			for (int j = 0; j < height; j++){
				NetzTile tile;
				if (i != 25) {
					tile = new NetzTile(NetzTile.tile_type.BLOCK, spaceBuilder);
					context.add(tile);
				} else {
					tile = new NetzTile(NetzTile.tile_type.STREET, spaceBuilder);
					context.add(tile);
				}
				spaceBuilder.moveTo(tile, i,j);
				
			}
		}
		
		for (int i = 0; i < width; i++){
			for (int j = 0; j < height; j++){
				//falls density groesser als Zufallszahl zwischen 0 und 1, dann Waldelement, sonst nicht brennbar.
				AutoAgent auto;
				if (density > RandomHelper.nextDoubleFromTo(0.0, 1.0)){
					//auto = new AutoAgent(auto_state.HALTING, spaceBuilder);
					
				}	else {
					//auto = new AutoAgent(auto_state.DRIVING, spaceBuilder);
				}
				//context.add(auto);
				//spaceBuilder.moveTo(auto, i, j);
			}
		}
		
		return context;
	}
}
