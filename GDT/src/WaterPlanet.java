import java.awt.Color;
import java.awt.Graphics;

public class WaterPlanet extends Planet {

	private static final long serialVersionUID = 1L;

	public WaterPlanet() {
		super(400, 400, 100);
		// TODO Auto-generated constructor stub
		this.setColors(Color.CYAN, Color.BLACK, Color.BLUE);
		this.repaint();
	}

	public WaterPlanet(Integer locX, Integer locY, Integer sz, Integer numResources) {
		super(locX, locY, sz, numResources);
		// TODO Auto-generated constructor stub
		this.setColors(Color.CYAN, Color.BLACK, Color.BLUE);
		this.repaint();
	}

	public WaterPlanet(Integer locX, Integer locY, Integer sz, Integer numResources, String n) {
		super(locX, locY, sz, numResources);
		this.setName("Water Planet " + n);
		this.setColors(Color.CYAN, Color.BLACK, Color.BLUE);
		this.repaint();
	}
	
}
