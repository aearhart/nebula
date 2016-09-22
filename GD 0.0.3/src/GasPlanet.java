import java.awt.Color;

public class GasPlanet extends Planet {

	public GasPlanet(ClientController clientController, Integer locX, Integer locY, Integer sz, Integer numResources, String n) {
		super(clientController, locX, locY, sz, numResources);
		this.setName(n);
		this.setColors(Color.PINK, Color.BLACK, Color.RED);
		this.setType("G");
	}
}
