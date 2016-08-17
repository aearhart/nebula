import java.awt.Color;

public class MetalPlanet extends Planet {

	public MetalPlanet() {
		super(400, 400, 100);
		// TODO Auto-generated constructor stub
		this.setColors(Color.DARK_GRAY, Color.BLACK, Color.GRAY);

	}

	public MetalPlanet(Integer locX, Integer locY, Integer sz, Integer numResources) {
		super(locX, locY, sz, numResources);
		// TODO Auto-generated constructor stub
		this.setColors(Color.DARK_GRAY, Color.BLACK, Color.GRAY);

	}

	public MetalPlanet(Integer locX, Integer locY, Integer sz, Integer numResources, String n) {
		super(locX, locY, sz, numResources);
		this.setName("Metal Planet " + n);
		this.setColors(Color.DARK_GRAY, Color.BLACK, Color.GRAY);

	}

}
