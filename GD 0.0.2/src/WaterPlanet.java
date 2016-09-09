import java.awt.Color;
import java.awt.Graphics;

public class WaterPlanet extends Planet {

	private static final long serialVersionUID = 1L;

	public WaterPlanet(ClientController ctrl) {
		super(ctrl, 400, 400, 100);
		// TODO Auto-generated constructor stub
		this.setColors(Color.CYAN, Color.BLACK, Color.BLUE);
		this.defineType("water");
	}

	public WaterPlanet(ClientController ctrl, Integer locX, Integer locY, Integer sz, Integer numResources) {
		super(ctrl, locX, locY, sz, numResources);
		// TODO Auto-generated constructor stub
		this.setColors(Color.CYAN, Color.BLACK, Color.BLUE);
		this.defineType("water");
	}

	public WaterPlanet(ClientController clientController, Integer locX, Integer locY, Integer sz, Integer numResources, String n) {
		super(clientController, locX, locY, sz, numResources);
		this.setName("Water Planet " + n);
		this.setColors(Color.CYAN, Color.BLACK, Color.BLUE);
		this.defineType("water");
	}
	
}
