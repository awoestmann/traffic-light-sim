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
				// �berspringe Nicht Randpl�tze
				if((i > 0 && i < width) || (j > 0 && j < height))
					continue;
				
				if(_min_gap > 0)
				{
					_min_gap--;
				}else
				{
					_min_gap = MIN_GAP;
					NetzTile tile = new NetzTile(tile_type.ENDPOINT, spaceBuilder);
					
					boolean found_end = false;
					
					// 0 = Oben , 1 = Rechts, 2 = Unten, 3 = Links
					int prev_direction = 0;
					if(i == 0)
						prev_direction = 3;
					else if(i == width-1)
						prev_direction = 1;
					else if(j == 0)
						prev_direction = 0;
					else
						prev_direction = 2;
					
					int new_direction = prev_direction;
					
					NetzTile prev_tile = tile;
					context.add(tile);
					
					while(!found_end)
					{
						while(new_direction == prev_direction)
							new_direction = RandomHelper.nextIntFromTo(0, 3);
						switch(new_direction)
						{
							case 0:
							{
								//if(spaceBuilder.getObjectAt(spaceBuilder.getLocation(prev_tile).getX(), spaceBuilder.getLocation(prev_tile).getY() + 1).get_state() == tile_type.STREET)
								break;
							}
						}
						found_end = true;
					}
				}
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
