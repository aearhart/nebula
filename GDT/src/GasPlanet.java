import java.awt.Color;

public class GasPlanet extends Planet {

	public GasPlanet() {
		super(400, 400, 100);
		// TODO Auto-generated constructor stub
		this.setColors(Color.PINK, Color.BLACK, Color.RED);

	}

	public GasPlanet(Integer locX, Integer locY, Integer sz, Integer numResources) {
		super(locX, locY, sz, numResources);
		// TODO Auto-generated constructor stub
		this.setColors(Color.PINK, Color.BLACK, Color.RED);

	}

	public GasPlanet(Integer locX, Integer locY, Integer sz, Integer numResources, String n) {
		super(locX, locY, sz, numResources);
		this.setName("Gas Planet " + n);
		this.setColors(Color.PINK, Color.BLACK, Color.RED);
	
	}
}
