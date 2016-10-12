import java.awt.Color;
import java.awt.Graphics;

public class WaterPlanet extends Planet {

	private static final long serialVersionUID = 1L;

	public WaterPlanet(Integer locX, Integer locY, String sz, String n) {
		super(locX, locY, sz, "W");
		//this.setName("Water Planet " + n);
		this.setNum(n);
		this.setColors(Color.CYAN, Color.BLACK, Color.BLUE);
	}
	
	public WaterPlanet(ClientController clientController, Integer locX, Integer locY, String sz, String n) {
		super(clientController, locX, locY, sz, "W");
		//this.setName("Water Planet " + n);
		this.setNum(n);
		this.setColors(Color.CYAN, Color.BLACK, Color.BLUE);
	}
	
}
