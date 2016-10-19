import java.awt.Color;

public class GasPlanet extends Planet {

	public GasPlanet(Integer locX, Integer locY, String sz, String n) {
		super(locX, locY, sz, "G");
		this.setNum(n);
		this.setColors(Color.PINK, Color.BLACK, Color.RED);
	}
	
	public GasPlanet(ClientController clientController, Integer locX, Integer locY, String sz, String n) {
		super(clientController, locX, locY, sz, "G");
		this.setNum(n);
		this.setColors(Color.PINK, Color.BLACK, Color.RED);
	}
	
	public GasPlanet(ClientController clientController, String[] s_array, int pos) {
		super(clientController, Integer.parseInt(s_array[pos+4]), Integer.parseInt(s_array[pos+5]), s_array[pos+6], "G");
		update(s_array, pos);
	}
}
