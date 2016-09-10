import java.awt.Color;

public class GasPlanet extends Planet {

	public GasPlanet(ClientController ctrl) {
		super(ctrl, 400, 400, 100);
		// TODO Auto-generated constructor stub
		this.setColors(Color.PINK, Color.BLACK, Color.RED);
		this.defineType("G");

	}

	public GasPlanet(ClientController ctrl, Integer locX, Integer locY, Integer sz, Integer numResources) {
		super(ctrl, locX, locY, sz, numResources);
		// TODO Auto-generated constructor stub
		this.setColors(Color.PINK, Color.BLACK, Color.RED);
		this.defineType("G");

	}

	public GasPlanet(ClientController clientController, Integer locX, Integer locY, Integer sz, Integer numResources, String n) {
		super(clientController, locX, locY, sz, numResources);
		this.setName(n);
		this.setColors(Color.PINK, Color.BLACK, Color.RED);
		this.defineType("G");
	}
}
