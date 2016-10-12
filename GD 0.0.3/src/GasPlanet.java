import java.awt.Color;

public class GasPlanet extends Planet {

	public GasPlanet(Integer locX, Integer locY, String sz, String n) {
		super(locX, locY, sz, "G");
		this.setNum(n);
		this.setColors(Color.PINK, Color.BLACK, Color.RED);
	}
	
	public GasPlanet(ClientController clientController, Integer locX, Integer locY, String sz, String n) {
		super(clientController, locX, locY, sz, "G");
		this.setNum(n);
		this.setColors(Color.PINK, Color.BLACK, Color.RED);
	}
}
