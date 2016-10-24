import java.awt.Color;
import java.awt.Graphics;

public class WaterPlanet extends Planet {

	private static final long serialVersionUID = 1L;

	public WaterPlanet(Integer locX, Integer locY, String sz, String n) {
		super(locX, locY, sz, "W");
		//this.setName("Water Planet " + n);
		this.setNum(n);
		this.setColors(Color.CYAN, Color.BLACK, Color.BLUE);
		setImage("water_planet.png");
	}
	
	public WaterPlanet(ClientController clientController, Integer locX, Integer locY, String sz, String n) {
		super(clientController, locX, locY, sz, "W");
		//this.setName("Water Planet " + n);
		this.setNum(n);
		this.setColors(Color.CYAN, Color.BLACK, Color.BLUE);
		setImage("water_planet.png");
	}
	
	public WaterPlanet(ClientController clientController, String[] s_array, int pos) {
		super(clientController, Integer.parseInt(s_array[pos+4]), Integer.parseInt(s_array[pos+5]), s_array[pos+6], "W");
		update(s_array, pos);
		setImage("water_planet.png");
	}
}
