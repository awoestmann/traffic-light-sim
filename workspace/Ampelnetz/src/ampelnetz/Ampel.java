package ampelnetz;

public class Ampel extends AmpelnetzComponent{

	public enum ampel_direction {HORIZONTAL, VERTICAL, INVERSE};
	
	ampel_direction dir;
	ampel_direction _dir;
	
	public void set_Schaltung(ampel_direction o_dir)
	{
		if(o_dir == ampel_direction.INVERSE)
		{
			if(dir == ampel_direction.HORIZONTAL)
				_dir = ampel_direction.VERTICAL;
			else
				_dir = ampel_direction.HORIZONTAL;
		}else
			_dir = o_dir;
	}
	
	public Ampel() {
		componentType = ComponentType.AMPEL;
	}
}
