import java.awt.Color;

public class MetalPlanet extends Planet {

	public MetalPlanet(Controller ctrl) {
		super(ctrl, 400, 400, 100);
		// TODO Auto-generated constructor stub
		this.setColors(Color.DARK_GRAY, Color.BLACK, Color.GRAY);
		this.defineType("metal");
	}

	public MetalPlanet(Controller ctrl, Integer locX, Integer locY, Integer sz, Integer numResources) {
		super(ctrl, locX, locY, sz, numResources);
		// TODO Auto-generated constructor stub
		this.setColors(Color.DARK_GRAY, Color.BLACK, Color.GRAY);
		this.defineType("metal");
	}

	public MetalPlanet(Controller ctrl, Integer locX, Integer locY, Integer sz, Integer numResources, String n) {
		super(ctrl, locX, locY, sz, numResources);
		this.setName("Metal Planet " + n);
		this.setColors(Color.DARK_GRAY, Color.BLACK, Color.GRAY);
		this.defineType("metal");
	}

}
