import java.awt.Color;
import java.awt.Graphics;

public class WaterPlanet extends Planet {

	private static final long serialVersionUID = 1L;

	public WaterPlanet(Controller ctrl) {
		super(ctrl, 400, 400, 100);
		// TODO Auto-generated constructor stub
		this.setColors(Color.CYAN, Color.BLACK, Color.BLUE);

	}

	public WaterPlanet(Controller ctrl, Integer locX, Integer locY, Integer sz, Integer numResources) {
		super(ctrl, locX, locY, sz, numResources);
		// TODO Auto-generated constructor stub
		this.setColors(Color.CYAN, Color.BLACK, Color.BLUE);

	}

	public WaterPlanet(Controller ctrl, Integer locX, Integer locY, Integer sz, Integer numResources, String n) {
		super(ctrl, locX, locY, sz, numResources);
		this.setName("Water Planet " + n);
		this.setColors(Color.CYAN, Color.BLACK, Color.BLUE);

	}
	
}
