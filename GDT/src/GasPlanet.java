import java.awt.Color;

public class GasPlanet extends Planet {

	public GasPlanet(Controller ctrl) {
		super(ctrl, 400, 400, 100);

		this.setColors(Color.PINK, Color.BLACK, Color.RED);
		this.defineType("gas");

	}

	public GasPlanet(Controller ctrl, Integer locX, Integer locY, Integer sz, Integer numResources) {
		super(ctrl, locX, locY, sz, numResources);

		this.setColors(Color.PINK, Color.BLACK, Color.RED);
		this.defineType("gas");

	}

	public GasPlanet(Controller ctrl, Integer locX, Integer locY, Integer sz, Integer numResources, String n) {
		super(ctrl, locX, locY, sz, numResources);
		this.setName("Gas Planet " + n);
		this.setColors(Color.PINK, Color.BLACK, Color.RED);
		this.defineType("gas");
	}
}
