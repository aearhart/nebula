import java.awt.Color;
import java.awt.Graphics;

public class WaterPlanet extends Planet {

	private static final long serialVersionUID = 1L;

	public WaterPlanet(Integer locX, Integer locY, Integer sz, Integer numResources, String n) {
		super(locX, locY, sz);
		//this.setName("Water Planet " + n);
		waterResource = numResources;
		this.setNum(n);
		this.setColors(Color.CYAN, Color.BLACK, Color.BLUE);
		this.setType("W");
	}
	
	public WaterPlanet(ClientController clientController, Integer locX, Integer locY, Integer sz, Integer numResources, String n) {
		super(clientController, locX, locY, sz);
		//this.setName("Water Planet " + n);
		waterResource = numResources;
		this.setNum(n);
		this.setColors(Color.CYAN, Color.BLACK, Color.BLUE);
		this.setType("W");
	}
	
}
